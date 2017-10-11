package de.dilello.www.snappypark.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import de.dilello.www.snappypark.R

/**
 * Created by timothy on 8/22/17.
 */
class QuickSettingsService: TileService() {
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onClick() {
        val parkingService = ParkingService(baseContext)
        val preferencesService = PreferencesService(baseContext)

        if (preferencesService.isCarParked == true) {
            parkingService.unparkCar()
        } else {
            parkingService.parkCar()
        }
    }

    override fun onStartListening() {
        super.onStartListening()

        val preferencesService = PreferencesService(this)
        setTileActivity(preferencesService.isCarParked)

        broadcastReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    UNPARK_BROADCAST_KEY -> setTileActivity(false)
                    PARK_BROADCAST_KEY -> setTileActivity(true)
                }
            }
        }

        val parkingIntentFilter = IntentFilter(PARK_BROADCAST_KEY)
        parkingIntentFilter.addAction(UNPARK_BROADCAST_KEY)

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastReceiver, parkingIntentFilter)
    }

    override fun onStopListening() {
        super.onStopListening()
        setTileActivity(false)

        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(broadcastReceiver)
    }

    fun setTileActivity(active: Boolean) {
        if (active == true) {
            qsTile.state = Tile.STATE_ACTIVE
            qsTile.label = getString(R.string.quicksettings_label_active)
        } else {
            qsTile.state = Tile.STATE_INACTIVE
            qsTile.label = getString(R.string.quicksettings_label_inactive)
        }

        qsTile.updateTile()
    }
}