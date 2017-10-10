package de.dilello.www.snappypark.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * Created by Jessica on 26.09.17.
 */
const val NOTIFICATION_INTENT_SERVICE_NAME = "de.dilello.www.snappypark.NotificationIntentService"

class NotificationIntentService: IntentService(NOTIFICATION_INTENT_SERVICE_NAME) {
    override fun onHandleIntent(intent: Intent) {
        val action = intent.action

        when (action) {
            NOTIFICATION_ACTION_UNPARK_KEY -> {
                val parkingService = ParkingService(baseContext)
                parkingService.unparkCar()
            }

            NOTIFICATION_ACTION_NAVIGATE_KEY -> {
                val parkingService = ParkingService(baseContext)
                parkingService.openNavigationToCar()
            }
        }
    }
}