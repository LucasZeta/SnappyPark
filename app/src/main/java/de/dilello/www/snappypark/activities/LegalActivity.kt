package de.dilello.www.snappypark.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import de.dilello.www.snappypark.R
import de.dilello.www.snappypark.services.LocationService
import de.dilello.www.snappypark.services.PermissionService
import de.dilello.www.snappypark.services.PreferencesService

class LegalActivity : AppCompatActivity() {
    lateinit var preferencesService: PreferencesService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legal)

        preferencesService = PreferencesService(baseContext)
    }

    fun onButtonReadClick(view: View) {
        if (PermissionService.hasLocationPermission(baseContext) == false) {
            val permissionIntent = Intent(this, PermissionActivity::class.java)
            startActivity(permissionIntent)
        } else {
            val tileIntent = Intent(this, TileOnboardingActivity::class.java)
            startActivity(tileIntent)
        }

        preferencesService.isLegalRead = true
    }

    fun onButtonPrivacyClick(view: View) {
        val privacyIntent = Intent(this, PrivacyActivity::class.java)
        startActivity(privacyIntent)
    }

    fun onButtonTacClick(view: View) {
        val termsIntent = Intent(this, TermsActivity::class.java)
        startActivity(termsIntent)
    }
}
