package de.dilello.www.snappypark.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import de.dilello.www.snappypark.R
import de.dilello.www.snappypark.services.NotificationService
import de.dilello.www.snappypark.services.PreferencesService

class SplashScreen : AppCompatActivity() {
    lateinit var preferencesService: PreferencesService
    lateinit var notificationService: NotificationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        preferencesService = PreferencesService(this)
        notificationService = NotificationService(this)

        openNextActivity()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationService.createNotificationChannel()
        }
    }

    /**
     * Start correct activity based on if onboarding was completed or not
     */
    private fun openNextActivity() {
        if (preferencesService.isOnboardingCompleted == false) {
            val welcomeIntent = Intent(this, WelcomeActivity::class.java)
            startActivity(welcomeIntent)
        } else {
            val mapsIntent = Intent(this, MapsActivity::class.java)
            startActivity(mapsIntent)
        }
    }
}
