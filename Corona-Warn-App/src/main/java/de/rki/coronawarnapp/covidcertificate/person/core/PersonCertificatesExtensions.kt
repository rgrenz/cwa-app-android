package de.rki.coronawarnapp.covidcertificate.person.core

import de.rki.coronawarnapp.covidcertificate.common.certificate.CwaCovidCertificate
import de.rki.coronawarnapp.covidcertificate.recovery.core.RecoveryCertificate
import de.rki.coronawarnapp.covidcertificate.test.core.TestCertificate
import de.rki.coronawarnapp.covidcertificate.vaccination.core.VaccinationCertificate
import de.rki.coronawarnapp.util.TimeAndDateExtensions.toLocalDateUserTz
import de.rki.coronawarnapp.util.TimeAndDateExtensions.toLocalDateUtc
import org.joda.time.Days
import org.joda.time.Duration
import org.joda.time.Instant
import timber.log.Timber

/*
    The list items shall be sorted descending by the following date attributes depending on the type of the DGC:
    for Vaccination Certificates (i.e. DGC with v[0]): the date of the vaccination v[0].dt
    for Test Certificates (i.e. DGC with t[0]): the date of the sample collection t[0].sc
    for Recovery Certificates (i.e. DGC with r[0]): the date of begin of the validity r[0].df
 */
fun Collection<CwaCovidCertificate>.toCertificateSortOrder(): List<CwaCovidCertificate> {
    return this.sortedByDescending {
        when (it) {
            is VaccinationCertificate -> it.vaccinatedOn
            is TestCertificate -> it.sampleCollectedAt.toLocalDateUserTz()
            is RecoveryCertificate -> it.validFrom
            else -> throw IllegalStateException("Can't sort $it")
        }
    }
}

/**
 * 1
 * PCR Test Certificate <= 48 hours
 * Find Test Certificates (i.e. DGC with t[0]) where t[0].tt is set to LP6464-4 and the time difference between the
 * time represented by t[0].sc and the current device time is <= 48 hours, sorted descending by t[0].sc
 * (i.e. latest first).
 * If there is one or more certificates matching these requirements,
 * the first one is returned as a result of the operation.
 */
private fun Collection<CwaCovidCertificate>.rule1FindRecentPcrCertificate(
    nowUtc: Instant
): CwaCovidCertificate? = this
    .filterIsInstance<TestCertificate>()
    .filter { it.rawCertificate.test.testType == "LP6464-4" }
    .filter { Duration(it.rawCertificate.test.sampleCollectedAt, nowUtc) <= Duration.standardHours(48) }
    .maxByOrNull { it.rawCertificate.test.sampleCollectedAt }

/**
 * 2
 * RAT Test Certificate <= 24 hours
 * Find Test Certificates (i.e. DGC with t[0]) where t[0].tt is set to LP217198-3 and the time difference between
 * the time represented by t[0].sc and the current device time is <= 24 hours, sorted descending by t[0].sc
 * (i.e. latest first).
 * If there is one or more certificates matching these requirements,
 * the first one is returned as a result of the operation.
 */
private fun Collection<CwaCovidCertificate>.rule2FindRecentRaCertificate(
    nowUtc: Instant
): CwaCovidCertificate? = this
    .filterIsInstance<TestCertificate>()
    .filter { it.rawCertificate.test.testType == "LP217198-3" }
    .filter { Duration(it.rawCertificate.test.sampleCollectedAt, nowUtc) <= Duration.standardHours(24) }
    .maxByOrNull { it.rawCertificate.test.sampleCollectedAt }

/**
 * 3
 * Series-completing Vaccination Certificate > 14 days:
 * Find Vaccination Certificates (i.e. DGC with v[0]) where v[0].dn equal to v[0].sd and the time difference
 * between the time represented by v[0].dt and the current device time is > 14 days, sorted descending by v[0].dt
 * (i.e. latest first).
 * If there is one or more certificates matching these requirements,
 * the first one is returned as a result of the operation.
 */
private fun Collection<CwaCovidCertificate>.rule3FindRecentLastShot(
    nowUtc: Instant
): CwaCovidCertificate? = this
    .filterIsInstance<VaccinationCertificate>()
    .filter {
        with(it.rawCertificate.vaccination) { doseNumber == totalSeriesOfDoses }
    }
    .filter {
        Days.daysBetween(it.rawCertificate.vaccination.vaccinatedOn, nowUtc.toLocalDateUtc()).days > 14
    }
    .maxByOrNull { it.rawCertificate.vaccination.vaccinatedOn }

/**
 * 4
 * Recovery Certificate <= 180 days
 * Find Recovery Certificates (i.e. DGC with r[0]) where the time difference between the time
 * represented by r[0].df and the current device time is <= 180 days, sorted descending by r[0].df
 * i.e. latest first).
 * If there is one or more certificates matching these requirements,
 * the first one is returned as a result of the operation.
 */
private fun Collection<CwaCovidCertificate>.rule4findRecentRecovery(
    nowUtc: Instant
): CwaCovidCertificate? = this
    .filterIsInstance<RecoveryCertificate>()
    .filter {
        Days.daysBetween(it.rawCertificate.recovery.validFrom, nowUtc.toLocalDateUtc()).days <= 180
    }.maxByOrNull { it.rawCertificate.recovery.validFrom }

/**
 * 5
 * Series-completing Vaccination Certificate <= 14 days
 * Find Vaccination Certificates (i.e. DGC with v[0]) where v[0].dn equal to v[0].sd and the time difference
 * between the time represented by v[0].dt and the current device time is <= 14 days,
 * sorted descending by v[0].dt (i.e. latest first).
 * If there is one or more certificates matching these requirements,
 * the first one is returned as a result of the operation.
 */
private fun Collection<CwaCovidCertificate>.rule5findTooRecentFinalShot(
    nowUtc: Instant
): CwaCovidCertificate? = this
    .filterIsInstance<VaccinationCertificate>()
    .filter {
        with(it.rawCertificate.vaccination) { doseNumber == totalSeriesOfDoses }
    }
    .filter {
        Days.daysBetween(it.rawCertificate.vaccination.vaccinatedOn, nowUtc.toLocalDateUtc()).days <= 14
    }
    .maxByOrNull { it.rawCertificate.vaccination.vaccinatedOn }

/**
 * 6
 * Other Vaccination Certificate
 * Find Vaccination Certificates (i.e. DGC with v[0])sorted descending by v[0].dt (i.e. latest first).
 * If there is one or more certificates matching these requirements,
 * the first one is returned as a result of the operation.
 */
private fun Collection<CwaCovidCertificate>.rule6findOtherVaccinations(): CwaCovidCertificate? = this
    .filterIsInstance<VaccinationCertificate>()
    .maxByOrNull { it.rawCertificate.vaccination.vaccinatedOn }

/**
 * 7
 * Recovery Certificate > 180 days
 * Find Recovery Certificates (i.e. DGC with r[0]) where the time difference between the time represented by r[0].df
 * and the current device time is > 180 days, sorted descending by r[0].df (i.e. latest first).
 * If there is one or more certificates matching these requirements,
 * the first one is returned as a result of the operation.
 */
private fun Collection<CwaCovidCertificate>.rule7FindOldRecovery(
    nowUtc: Instant
): CwaCovidCertificate? = this
    .filterIsInstance<RecoveryCertificate>()
    .filter {
        Days.daysBetween(it.rawCertificate.recovery.validFrom, nowUtc.toLocalDateUtc()).days > 180
    }
    .maxByOrNull { it.rawCertificate.recovery.validFrom }

/**
 * 8
 * PCR Test Certificate > 48 hours
 * Find Test Certificates (i.e. DGC with t[0]) where t[0].tt is set to LP6464-4 and the time difference between
 * the time represented by t[0].sc and the current device time is > 48 hours,
 * sorted descending by t[0].sc (i.e. latest first).
 * If there is one or more certificates matching these requirements,
 * the first one is returned as a result of the operation.
 */
private fun Collection<CwaCovidCertificate>.rule8FindOldPcrTest(
    nowUtc: Instant
): CwaCovidCertificate? = this
    .filterIsInstance<TestCertificate>()
    .filter { it.rawCertificate.test.testType == "LP6464-4" }
    .filter { Duration(it.rawCertificate.test.sampleCollectedAt, nowUtc) > Duration.standardHours(48) }
    .maxByOrNull { it.rawCertificate.test.sampleCollectedAt }

/**
 * 9
 * RAT Test Certificate > 24 hours
 * Find Test Certificates (i.e. DGC with t[0]) where t[0].tt is set to LP217198-3 and the time difference between
 * the time represented by t[0].sc and the current device time is > 24 hours,
 * sorted descending by t[0].sc (i.e. latest first).
 * If there is one or more certificates matching these requirements,
 * the first one is returned as a result of the operation.
 */
private fun Collection<CwaCovidCertificate>.rule9FindOldRaTest(
    nowUtc: Instant
): CwaCovidCertificate? = this
    .filterIsInstance<TestCertificate>()
    .filter { it.rawCertificate.test.testType == "LP217198-3" }
    .filter { Duration(it.rawCertificate.test.sampleCollectedAt, nowUtc) > Duration.standardHours(24) }
    .maxByOrNull { it.rawCertificate.test.sampleCollectedAt }

@Suppress("ReturnCount", "ComplexMethod")
fun Collection<CwaCovidCertificate>.findHighestPriorityCertificate(
    nowUtc: Instant = Instant.now()
): CwaCovidCertificate = this
    .also { Timber.v("findHighestPriorityCertificate(nowUtc=%s): %s", nowUtc, this) }
    .run {
        val valid = filter {
            it.getState() is CwaCovidCertificate.State.Valid || it.getState() is CwaCovidCertificate.State.ExpiringSoon
        }
        val expired = filter { it.getState() is CwaCovidCertificate.State.Expired }
        val invalid = filter { it.getState() is CwaCovidCertificate.State.Invalid }
        if (this.size != (valid.size + expired.size + invalid.size)) {
            throw IllegalStateException("State grouping failure, certificate count does not match.")
        }
        listOf(valid, expired, invalid)
    }
    .map { certsForState ->
        certsForState.rule1FindRecentPcrCertificate(nowUtc)?.let {
            Timber.d("Rule 1 match (PCR Test Certificate <= 48 hours): %s", it)
            return@map it
        }

        certsForState.rule2FindRecentRaCertificate(nowUtc)?.let {
            Timber.d("Rule 2 match (RA Test Certificate <= 24 hours): %s", it)
            return@map it
        }

        certsForState.rule3FindRecentLastShot(nowUtc)?.let {
            Timber.d("Rule 3 match (Series-completing Vaccination Certificate > 14 days): %s", it)
            return@map it
        }

        certsForState.rule4findRecentRecovery(nowUtc)?.let {
            Timber.d("Rule 4 match (Recovery Certificate <= 180 days): %s", it)
            return@map it
        }

        certsForState.rule5findTooRecentFinalShot(nowUtc)?.let {
            Timber.d("Rule 5 match (Series-completing Vaccination Certificate <= 14 days): %s", it)
            return@map it
        }

        certsForState.rule6findOtherVaccinations()?.let {
            Timber.d("Rule 6 match (Other Vaccination Certificate): %s", it)
            return@map it
        }

        certsForState.rule7FindOldRecovery(nowUtc)?.let {
            Timber.d("Rule 7 match (Recovery Certificate > 180 days): %s", it)
            return@map it
        }

        certsForState.rule8FindOldPcrTest(nowUtc)?.let {
            Timber.d("Rule 8 match (PCR Test Certificate > 48 hours): %s", it)
            return@map it
        }

        certsForState.rule9FindOldRaTest(nowUtc)?.let {
            Timber.d("Rule 9 match (RAT Test Certificate > 24 hours): %s", it)
            return@map it
        }

        null
    }
    .firstOrNull()
    ?: first().also {
        /**
         * Fallback: return the first DGC from the set.
         * Note that this fallback should never apply in a real scenario.
         */
        Timber.e("No priority match, this should not happen: %s", this)
    }
