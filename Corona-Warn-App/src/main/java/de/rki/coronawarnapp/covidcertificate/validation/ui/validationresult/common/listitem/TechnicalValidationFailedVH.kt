package de.rki.coronawarnapp.covidcertificate.validation.ui.validationresult.common.listitem

import android.view.ViewGroup
import androidx.core.view.isGone
import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.covidcertificate.validation.core.DccValidation
import de.rki.coronawarnapp.covidcertificate.validation.ui.validationresult.common.listitem.TechnicalValidationFailedVH.Item
import de.rki.coronawarnapp.databinding.CovidCertificateValidationResultTechnicalFailedItemBinding
import de.rki.coronawarnapp.util.TimeAndDateExtensions.toShortDayFormat
import de.rki.coronawarnapp.util.TimeAndDateExtensions.toShortTimeFormat
import de.rki.coronawarnapp.util.lists.diffutil.HasPayloadDiffer

class TechnicalValidationFailedVH(
    parent: ViewGroup
) : BaseValidationResultVH<Item, CovidCertificateValidationResultTechnicalFailedItemBinding>(
    R.layout.covid_certificate_validation_result_rule_container,
    parent
) {

    override val viewBinding = lazy {
        CovidCertificateValidationResultTechnicalFailedItemBinding.inflate(
            layoutInflater,
            itemView.findViewById(R.id.container),
            true
        )
    }

    override val onBindData: CovidCertificateValidationResultTechnicalFailedItemBinding.(
        item: Item,
        payloads: List<Any>,
    ) -> Unit = { item, payloads ->
        val curItem = payloads.filterIsInstance<Item>().singleOrNull() ?: item
        with(curItem.validation) {
            groupDateFormat.isGone = jsonSchemaCheckPassed
            groupSignature.isGone = signatureCheckPassed
            groupDateExpired.isGone = expirationCheckPassed
            divider.isGone = jsonSchemaCheckPassed || signatureCheckPassed
            divider2.isGone = signatureCheckPassed || expirationCheckPassed

            textExpiredDate.text = context.getString(
                R.string.validation_rule_technical_error_date_expired_format,
                certificateExpiredAt.toShortDayFormat(),
                certificateExpiredAt.toShortTimeFormat(),
            )
        }
    }

    data class Item(
        val validation: DccValidation
    ) : ValidationResultItem, HasPayloadDiffer {
        override val stableId: Long = Item::class.java.name.hashCode().toLong()

        override fun diffPayload(old: Any, new: Any): Any? = if (old::class == new::class) new else null
    }
}
