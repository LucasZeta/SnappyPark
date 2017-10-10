package de.dilello.www.snappypark.services

import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.widget.Toast
import de.dilello.www.snappypark.R
import es.dmoral.toasty.Toasty
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.config.LocationParams

/**
 * Created by timothy on 20.08.17.
 */
class LocationService(val context: Context) {

    val preferencesService = PreferencesService(context)

    fun launchNavigationToCar() {
        val carLocation = preferencesService.currentLocation
        val mapsUri: Uri = Uri.parse(
                "google.navigation:q=" // Launch google navigation intent
                        + carLocation.latitude
                        + ","
                        + carLocation.longitude
                        + "&mode=w") // Set starting mode to "walking"

        val navigationIntent = Intent(Intent.ACTION_VIEW, mapsUri)
        context.startActivity(navigationIntent)
    }

    fun updateLocation(listener: OnLocationUpdatedListener) {
        if (PermissionService.hasLocationPermission(context) == false) {
            Toasty.error(context, context.getString(R.string.toast_error_permission_open_app), Toast.LENGTH_LONG)
                    .show()
            return
        }

        val smartLocation: SmartLocation = SmartLocation.with(context)

        // Open Settings and show toast when locationSettings are not enabled
        if (smartLocation.location().state().isAnyProviderAvailable() == false) {
            Toasty.info(
                    context,
                    context.getString(R.string.toast_no_gps),
                    Toast.LENGTH_LONG)
                    .show()
        }

        // Inform user about location fixing process
        Toasty.info(context, context.getString(R.string.toast_info_fixing_location), Toast.LENGTH_SHORT)
                .show()

        smartLocation.location()
                .oneFix()
                .config(LocationParams.NAVIGATION)
                .start(listener)
    }
}