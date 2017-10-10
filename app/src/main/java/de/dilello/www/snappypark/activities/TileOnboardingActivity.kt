package de.dilello.www.snappypark.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import de.dilello.www.snappypark.R
import de.dilello.www.snappypark.services.PreferencesService

class TileOnboardingActivity : AppCompatActivity() {
    lateinit var preferencesService: PreferencesService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tile_onboarding)

        preferencesService = PreferencesService(baseContext)
    }

    fun onButtonStartClick(view: View) {
        preferencesService.isOnboardingCompleted = true

        val mapsIntent = Intent(this, MapsActivity::class.java)
        startActivity(mapsIntent)
    }
}