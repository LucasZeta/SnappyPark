package de.dilello.www.snappypark.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.quicksettings.TileService
import android.view.View
import android.widget.Toast
import de.dilello.www.snappypark.R
import de.dilello.www.snappypark.services.PERMISSION_LOCATION_KEY
import es.dmoral.toasty.Toasty

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
    }

    fun onButtonPermissionClick(view: View) {
        val permissionArray = Array<String>(1) {Manifest.permission.ACCESS_FINE_LOCATION}
        this.requestPermissions(
                permissionArray,
                PERMISSION_LOCATION_KEY
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_LOCATION_KEY -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val tilesIntent = Intent(this, TileOnboardingActivity::class.java)
                    startActivity(tilesIntent)
                } else {
                    Toasty.error(
                            this,
                            getString(R.string.toast_permission_denied),
                            Toast.LENGTH_LONG)
                            .show()
                }
            }
        }
    }
}
