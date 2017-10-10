package de.dilello.www.snappypark.services

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import de.dilello.www.snappypark.R
import de.dilello.www.snappypark.activities.MapsActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by timothy on 8/29/17.
 */

const val NOTIFICATION_CHANNEL_PARK_ID = "de.dilello.www.snappypark.park_notification"
const val NOTIFICATION_ACTION_UNPARK_KEY = "de.dilello.www.snappypark.NOTIFICATION_ACTION_UNPARK"
const val NOTIFICATION_ACTION_NAVIGATE_KEY = "de.dilello.www.snappypark.NOTIFICATION_ACTION_NAVIGATE"

class NotificationService(val context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun createParkingNotification() {
        val currentTimeString = getCurrentTimeString()

        // Set Notification Click behaviour and color
        val notificationIntent = Intent(context, MapsActivity::class.java)
        val notificationColor = context.getColor(R.color.colorPrimary)

        // Constuct Pending Intent for the "Unpark" Notification Action
        val notificationUnparkActionIntent = Intent(context, NotificationIntentService::class.java)
        notificationUnparkActionIntent.setAction(NOTIFICATION_ACTION_UNPARK_KEY)
        val pendingNotificationUnparkActionIntent = PendingIntent.getService(context,
                0,
                notificationUnparkActionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationNavigateActionIntent = Intent(context, NotificationIntentService::class.java)
        notificationNavigateActionIntent.setAction(NOTIFICATION_ACTION_NAVIGATE_KEY)
        val pendingNotificationNavigateActionIntent = PendingIntent.getService(context,
                0,
                notificationNavigateActionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_PARK_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(context.getString(R.string.notification_title)
                        + " "
                        + currentTimeString
                        + " "
                        + context.getString(R.string.notification_title_suffix)
                )
                .setContentText(context.getString(R.string.notification_text))
                .setColor(notificationColor)
                .setOngoing(true)
                .addAction(R.drawable.logo, context.getString(R.string.notification_action_unpark), pendingNotificationUnparkActionIntent)
                .addAction(R.drawable.ic_directions, context.getString(R.string.notification_action_navigate), pendingNotificationNavigateActionIntent)

        // Set Notification to top of notification stack
        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(MapsActivity::class.java)
        taskStackBuilder.addNextIntent(notificationIntent)

        val notificationPendingIntent = taskStackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        notificationBuilder.setContentIntent(notificationPendingIntent)

        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun  getCurrentTimeString(): String {
        val calendar = Calendar.getInstance()
        var currentHour = calendar.get(Calendar.HOUR_OF_DAY).toString()
        var currentMinute = calendar.get(Calendar.MINUTE).toString()

        if (currentHour.toInt() < 10) {
            currentHour = "0" + currentHour
        }

        if (currentMinute.toInt() < 10) {
            currentMinute = "0" + currentMinute
        }

        return currentHour + ":" + currentMinute
    }

    fun dismissNotification() {
        notificationManager.cancelAll()
    }

    @TargetApi(26)
    fun createNotificationChannel() {
        val channelNameParking = context.getString(R.string.notification_channel_parking_name)
        val channelDescription = context.getString(R.string.notification_channel_parking_description)
        val channelImportanceParking = NotificationManager.IMPORTANCE_HIGH
        val notificationChannelParking = NotificationChannel(
                NOTIFICATION_CHANNEL_PARK_ID,
                channelNameParking,
                channelImportanceParking)
        notificationChannelParking.description = channelDescription

        notificationManager.createNotificationChannel(notificationChannelParking)
    }
}