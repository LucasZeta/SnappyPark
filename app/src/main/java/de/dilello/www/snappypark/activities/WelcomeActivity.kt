package de.dilello.www.snappypark.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import de.dilello.www.snappypark.R
import de.dilello.www.snappypark.services.PermissionService
import de.dilello.www.snappypark.services.PreferencesService

class WelcomeActivity : AppCompatActivity() {
    lateinit var preferencesService: PreferencesService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        preferencesService = PreferencesService(baseContext)
    }

    fun onButtonNextClick(view: View) {
        if (preferencesService.isLegalRead == false) {
            val legalIntent = Intent(this, LegalActivity::class.java)
            startActivity(legalIntent)
        } else if (PermissionService.hasLocationPermission(baseContext) == false) {
            val permissionIntent = Intent(this, PermissionActivity::class.java)
            startActivity(permissionIntent)
        } else {
            val tilesIntent = Intent(this, TileOnboardingActivity::class.java)
            startActivity(tilesIntent)
        }
    }

}
