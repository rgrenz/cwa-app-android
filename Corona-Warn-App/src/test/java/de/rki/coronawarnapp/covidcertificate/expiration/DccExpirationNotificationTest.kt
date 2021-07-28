package de.rki.coronawarnapp.covidcertificate.expiration

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.navigation.NavDeepLinkBuilder
import de.rki.coronawarnapp.CoronaWarnApplication
import de.rki.coronawarnapp.covidcertificate.common.notification.DigitalCovidCertificateNotifications
import de.rki.coronawarnapp.covidcertificate.common.repository.VaccinationCertificateContainerId
import de.rki.coronawarnapp.main.CWASettings
import de.rki.coronawarnapp.util.device.ForegroundState
import de.rki.coronawarnapp.util.notifications.NavDeepLinkBuilderFactory
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.jupiter.api.BeforeEach
import testhelpers.BaseTest

class DccExpirationNotificationTest : BaseTest() {

    @MockK(relaxed = true) lateinit var context: Context
    @MockK lateinit var foregroundState: ForegroundState
    @MockK(relaxed = true) lateinit var navDeepLinkBuilder: NavDeepLinkBuilder
    @MockK lateinit var pendingIntent: PendingIntent
    @MockK lateinit var deepLinkBuilderFactory: NavDeepLinkBuilderFactory
    @MockK lateinit var notificationManager: NotificationManager
    @MockK lateinit var notificationHelper: DigitalCovidCertificateNotifications
    @MockK lateinit var cwaSettings: CWASettings

    val containerId = VaccinationCertificateContainerId("Rollkuchen")

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        mockkObject(CoronaWarnApplication)

        every { CoronaWarnApplication.getAppContext() } returns context
        every { context.getSystemService(Context.NOTIFICATION_SERVICE) } returns notificationManager
        every { navDeepLinkBuilder.createPendingIntent() } returns pendingIntent
        every { cwaSettings.isNotificationsTestEnabled.value } returns true

        every { notificationHelper.newBaseBuilder() } returns mockk(relaxed = true)
        every {
            notificationHelper.sendNotification(
                notificationId = any(),
                notification = any()
            )
        } just Runs
    }

    fun createInstance() = DccExpirationNotification(
        context = context,
        foregroundState = foregroundState,
        deepLinkBuilderFactory = deepLinkBuilderFactory,
        notificationHelper = notificationHelper,
        cwaSettings = cwaSettings
    )

//    @Test
//    fun `show expires soon notification`() = runBlockingTest {
//        // TODO
//    }
//
//    @Test
//    fun `show expired notification`() = runBlockingTest {
//       // TODO
//    }
}
