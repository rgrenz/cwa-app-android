package de.rki.coronawarnapp.covidcertificate.validation.ui.validationresult.common

import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.covidcertificate.validation.core.DccValidation
import de.rki.coronawarnapp.databinding.CovidCertificateValidationResultFragmentsBinding

fun CovidCertificateValidationResultFragmentsBinding.setHeaderForState(state: DccValidation.State) = when (state) {
    DccValidation.State.PASSED -> {
        toolbar.setTitle(R.string.validation_rules_result_valid_header)
        headerImage.setImageResource(R.drawable.covid_certificate_validation_passed_header)
    }
    DccValidation.State.OPEN -> {
        toolbar.setTitle(R.string.validation_open_title)
        headerImage.setImageResource(R.drawable.covid_certificate_validation_open_header)
    }
    DccValidation.State.TECHNICAL_FAILURE,
    DccValidation.State.FAILURE -> {
        toolbar.setTitle(R.string.validation_failed_title)
        headerImage.setImageResource(R.drawable.covid_certificate_validation_failed_header)
    }
}
