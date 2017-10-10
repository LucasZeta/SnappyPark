package de.dilello.www.snappypark.services

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

/**
 * Created by timothy on 8/21/17.
 */
const val PERMISSION_LOCATION_KEY = 1000
class PermissionService {
    companion object {
        fun hasLocationPermission(context: Context): Boolean {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true
            }

            return false
        }
    }
}