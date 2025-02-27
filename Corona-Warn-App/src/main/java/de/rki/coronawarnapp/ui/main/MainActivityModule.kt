package de.rki.coronawarnapp.ui.main

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import de.rki.coronawarnapp.covidcertificate.DigitalCovidCertificateUIModule
import de.rki.coronawarnapp.datadonation.analytics.ui.AnalyticsUIModule
import de.rki.coronawarnapp.release.NewReleaseInfoFragment
import de.rki.coronawarnapp.release.NewReleaseInfoFragmentModule
import de.rki.coronawarnapp.statistics.ui.stateselection.FederalStateSelectionModule
import de.rki.coronawarnapp.tracing.ui.details.TracingDetailsFragmentModule
import de.rki.coronawarnapp.ui.coronatest.rat.profile.RATProfileUIModule
import de.rki.coronawarnapp.ui.information.InformationFragmentModule
import de.rki.coronawarnapp.ui.interoperability.InteroperabilityConfigurationFragment
import de.rki.coronawarnapp.ui.interoperability.InteroperabilityConfigurationFragmentModule
import de.rki.coronawarnapp.ui.main.home.HomeFragmentModule
import de.rki.coronawarnapp.ui.onboarding.OnboardingDeltaAnalyticsModule
import de.rki.coronawarnapp.ui.onboarding.OnboardingDeltaInteroperabilityModule
import de.rki.coronawarnapp.ui.presencetracing.PresenceTracingUIModule
import de.rki.coronawarnapp.ui.qrcode.fullscreen.QrCodeFullScreenFragment
import de.rki.coronawarnapp.ui.qrcode.fullscreen.QrCodeFullScreenFragmentModule
import de.rki.coronawarnapp.ui.settings.SettingFragmentsModule
import de.rki.coronawarnapp.ui.settings.SettingsResetFragment
import de.rki.coronawarnapp.ui.settings.SettingsResetModule
import de.rki.coronawarnapp.ui.submission.viewmodel.SubmissionFragmentModule
import de.rki.coronawarnapp.util.viewmodel.CWAViewModel
import de.rki.coronawarnapp.util.viewmodel.CWAViewModelFactory
import de.rki.coronawarnapp.util.viewmodel.CWAViewModelKey

@Module(
    includes = [
        OnboardingDeltaAnalyticsModule::class,
        OnboardingDeltaInteroperabilityModule::class,
        HomeFragmentModule::class,
        TracingDetailsFragmentModule::class,
        SettingFragmentsModule::class,
        FederalStateSelectionModule::class,
        SubmissionFragmentModule::class,
        InformationFragmentModule::class,
        NewReleaseInfoFragmentModule::class,
        AnalyticsUIModule::class,
        PresenceTracingUIModule::class,
        RATProfileUIModule::class,
        DigitalCovidCertificateUIModule::class,
    ]
)
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [InteroperabilityConfigurationFragmentModule::class])
    abstract fun intertopConfigScreen(): InteroperabilityConfigurationFragment

    @ContributesAndroidInjector(modules = [SettingsResetModule::class])
    abstract fun settingsResetScreen(): SettingsResetFragment

    @ContributesAndroidInjector(modules = [NewReleaseInfoFragmentModule::class])
    abstract fun newReleaseInfoScreen(): NewReleaseInfoFragment

    @ContributesAndroidInjector(modules = [QrCodeFullScreenFragmentModule::class])
    abstract fun qrCodeFullScreenFragment(): QrCodeFullScreenFragment

    @Binds
    @IntoMap
    @CWAViewModelKey(MainActivityViewModel::class)
    abstract fun mainActivityViewModel(
        factory: MainActivityViewModel.Factory
    ): CWAViewModelFactory<out CWAViewModel>
}
