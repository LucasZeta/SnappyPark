package de.dilello.www.snappypark.services

import android.content.Context
import android.content.Intent
import android.location.Location
import android.support.v4.content.LocalBroadcastManager
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import es.dmoral.toasty.Toasty
import io.nlopez.smartlocation.OnLocationUpdatedListener

/**
 * Created by timothy on 8/29/17.
 */
const val UNPARK_BROADCAST_KEY = "de.dilello.www.snappypark.UNPARK_BROADCAST"
const val PARK_BROADCAST_KEY = "de.dilello.www.snappypark.PARK_BROADCAST"

class ParkingService(val context: Context): OnLocationUpdatedListener {
    val notificationService = NotificationService(context)
    val locationService = LocationService(context)
    val preferencesService = PreferencesService(context)

    fun parkCar() {
        locationService.updateLocation(this)
    }

    fun unparkCar() {
        preferencesService.isCarParked = false
        notificationService.dismissNotification()
        
        val unparkIntent = Intent(UNPARK_BROADCAST_KEY)
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(unparkIntent)
    }

    fun openNavigationToCar() {
        locationService.launchNavigationToCar()
    }

    override fun onLocationUpdated(location: Location) {
        val location = LatLng(location.latitude, location.longitude)
        preferencesService.currentLocation = location
        preferencesService.isCarParked = true

        notificationService.createParkingNotification()

        val parkIntent = Intent(PARK_BROADCAST_KEY)
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(parkIntent)
    }
}