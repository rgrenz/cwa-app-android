package testhelpers

import dagger.Module
import de.rki.coronawarnapp.bugreporting.DebugLogTestModule
import de.rki.coronawarnapp.bugreporting.DebugLogUploadTestModule
import de.rki.coronawarnapp.covidcertificate.person.ui.details.PersonDetailsFragmentTestModule
import de.rki.coronawarnapp.covidcertificate.person.ui.overview.PersonOverviewFragmentTestModule
import de.rki.coronawarnapp.covidcertificate.recovery.ui.RecoveryCertificateDetailsFragmentTestModule
import de.rki.coronawarnapp.covidcertificate.test.ui.CovidCertificateDetailsFragmentTestModule
import de.rki.coronawarnapp.covidcertificate.vaccination.ui.details.VaccinationDetailsFragmentTestModule
import de.rki.coronawarnapp.ui.contactdiary.ContactDiaryDayFragmentTestModule
import de.rki.coronawarnapp.ui.contactdiary.ContactDiaryEditLocationsFragmentTestModule
import de.rki.coronawarnapp.ui.contactdiary.ContactDiaryEditPersonsFragmentTestModule
import de.rki.coronawarnapp.ui.contactdiary.ContactDiaryLocationListFragmentTestModule
import de.rki.coronawarnapp.ui.contactdiary.ContactDiaryOnboardingFragmentTestModule
import de.rki.coronawarnapp.ui.contactdiary.ContactDiaryOverviewFragmentTestModule
import de.rki.coronawarnapp.ui.contactdiary.ContactDiaryPersonListFragmentTestModule
import de.rki.coronawarnapp.ui.coronatest.rat.profile.create.RATProfileCreateFragmentTestModule
import de.rki.coronawarnapp.ui.coronatest.rat.profile.onboarding.RATProfileOnboardingFragmentTestModule
import de.rki.coronawarnapp.ui.coronatest.rat.profile.qrcode.RATProfileQrCodeFragmentTestModule
import de.rki.coronawarnapp.ui.eventregistration.organizer.CreateEventTestModule
import de.rki.coronawarnapp.ui.eventregistration.organizer.QrCodeDetailFragmentTestModule
import de.rki.coronawarnapp.ui.eventregistration.organizer.TraceLocationsFragmentTestModule
import de.rki.coronawarnapp.ui.main.home.HomeFragmentTestModule
import de.rki.coronawarnapp.ui.onboarding.OnboardingAnalyticsFragmentTestModule
import de.rki.coronawarnapp.ui.onboarding.OnboardingDeltaInteroperabilityFragmentTestModule
import de.rki.coronawarnapp.ui.onboarding.OnboardingFragmentTestModule
import de.rki.coronawarnapp.ui.onboarding.OnboardingNotificationsTestModule
import de.rki.coronawarnapp.ui.onboarding.OnboardingPrivacyTestModule
import de.rki.coronawarnapp.ui.onboarding.OnboardingTestFragmentModule
import de.rki.coronawarnapp.ui.onboarding.OnboardingTracingFragmentTestModule
import de.rki.coronawarnapp.ui.statistics.StatisticsExplanationFragmentTestModule
import de.rki.coronawarnapp.ui.submission.SubmissionConsentFragmentTestModule
import de.rki.coronawarnapp.ui.submission.SubmissionContactTestModule
import de.rki.coronawarnapp.ui.submission.SubmissionDispatcherTestModule
import de.rki.coronawarnapp.ui.submission.SubmissionQRScanFragmentModule
import de.rki.coronawarnapp.ui.submission.SubmissionSymptomCalendarFragmentTestModule
import de.rki.coronawarnapp.ui.submission.SubmissionSymptomIntroFragmentTestModule
import de.rki.coronawarnapp.ui.submission.SubmissionTanTestModule
import de.rki.coronawarnapp.ui.submission.SubmissionTestResultConsentGivenTestModule
import de.rki.coronawarnapp.ui.submission.SubmissionTestResultNoConsentModel
import de.rki.coronawarnapp.ui.submission.SubmissionTestResultTestAvailableModule
import de.rki.coronawarnapp.ui.submission.SubmissionTestResultTestModule
import de.rki.coronawarnapp.ui.submission.SubmissionTestResultTestNegativeModule
import de.rki.coronawarnapp.ui.submission.SubmissionYourConsentFragmentTestModule
import de.rki.coronawarnapp.ui.submission.covidcertificate.RequestCovidCertificateFragmentTestModule
import de.rki.coronawarnapp.ui.tracing.TracingDetailsFragmentTestTestModule
import de.rki.coronawarnapp.ui.vaccination.CovidCertificateInfoFragmentTestModule

@Module(
    includes = [
        HomeFragmentTestModule::class,

        // -------- Onboarding --------
        OnboardingFragmentTestModule::class,
        OnboardingDeltaInteroperabilityFragmentTestModule::class,
        OnboardingNotificationsTestModule::class,
        OnboardingPrivacyTestModule::class,
        OnboardingTestFragmentModule::class,
        OnboardingTracingFragmentTestModule::class,
        OnboardingAnalyticsFragmentTestModule::class,

        // -------- Submission --------
        SubmissionDispatcherTestModule::class,
        SubmissionTanTestModule::class,
        SubmissionTestResultTestModule::class,
        SubmissionTestResultTestNegativeModule::class,
        SubmissionTestResultTestAvailableModule::class,
        SubmissionTestResultNoConsentModel::class,
        SubmissionTestResultConsentGivenTestModule::class,
        SubmissionSymptomIntroFragmentTestModule::class,
        SubmissionContactTestModule::class,
        SubmissionQRScanFragmentModule::class,
        SubmissionConsentFragmentTestModule::class,
        SubmissionYourConsentFragmentTestModule::class,
        SubmissionSymptomCalendarFragmentTestModule::class,
        SubmissionQRScanFragmentModule::class,

        // -------- Tracing --------
        TracingDetailsFragmentTestTestModule::class,

        // -------- Contact Diary --------
        ContactDiaryOnboardingFragmentTestModule::class,
        ContactDiaryOverviewFragmentTestModule::class,
        ContactDiaryDayFragmentTestModule::class,
        ContactDiaryPersonListFragmentTestModule::class,
        ContactDiaryLocationListFragmentTestModule::class,
        ContactDiaryEditLocationsFragmentTestModule::class,
        ContactDiaryEditPersonsFragmentTestModule::class,

        // -------- Statistics --------
        StatisticsExplanationFragmentTestModule::class,

        // -------- Bugreporting --------
        DebugLogUploadTestModule::class,
        DebugLogTestModule::class,

        // -------- Presence tracing --------
        CreateEventTestModule::class,
        TraceLocationsFragmentTestModule::class,
        QrCodeDetailFragmentTestModule::class,

        // -------- Certificates --------
        VaccinationDetailsFragmentTestModule::class,
        RecoveryCertificateDetailsFragmentTestModule::class,
        CovidCertificateInfoFragmentTestModule::class,
        RequestCovidCertificateFragmentTestModule::class,
        CovidCertificateDetailsFragmentTestModule::class,
        PersonOverviewFragmentTestModule::class,
        PersonDetailsFragmentTestModule::class,

        // -------- RAT profile ------------
        RATProfileCreateFragmentTestModule::class,
        RATProfileOnboardingFragmentTestModule::class,
        RATProfileQrCodeFragmentTestModule::class,
    ]
)
class FragmentTestModuleRegistrar
