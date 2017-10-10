package de.dilello.www.snappypark.services

import android.service.quicksettings.TileService
import android.util.Log

/**
 * Created by timothy on 8/22/17.
 */
class QuickSettingsService: TileService() {

    override fun onClick() {
        val parkingService = ParkingService(baseContext)
        parkingService.parkCar()
    }
}