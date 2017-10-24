package de.dilello.www.snappypark.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import de.dilello.www.snappypark.R
import de.dilello.www.snappypark.services.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var carMarker: Marker
    private lateinit var preferencesService: PreferencesService
    private lateinit var parkingService: ParkingService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        preferencesService = PreferencesService(baseContext)
        parkingService = ParkingService(baseContext)

        if (preferencesService.isCarParked == false) {
            setFabToNavigate(false)
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        broadcastReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    UNPARK_BROADCAST_KEY -> removeMarker()
                    PARK_BROADCAST_KEY -> setMarkerToCurrentPosition()
                }
            }
        }

        val parkingIntentFilter = IntentFilter(PARK_BROADCAST_KEY)
        parkingIntentFilter.addAction(UNPARK_BROADCAST_KEY)

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastReceiver, parkingIntentFilter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.maps_menu, menu)

        return true
    }

    fun navigateToMarker(view: View) {
        parkingService.openNavigationToCar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_park -> {
                if (preferencesService.isCarParked == false) {
                    parkingService.parkCar()
                } else {
                    parkingService.unparkCar()
                }
                return true
            }

            R.id.menu_locate -> {
                if (preferencesService.isCarParked == true) {
                    moveCameraToMarker()
                }
                return true
            }

            R.id.menu_privacy -> {
                val privacyIntent = Intent(this, PrivacyActivity::class.java)
                startActivity(privacyIntent)
                return true
            }

            R.id.menu_terms -> {
                val termsIntent = Intent(this, TermsActivity::class.java)
                startActivity(termsIntent)
                return true
            }

            R.id.menu_about -> {
                val aboutIntent = Intent(this, AboutActivity::class.java)
                startActivity(aboutIntent)
                return true
            }
        }

        return false
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    /**
     * Creates a marker from currently saved position and adds it to the map. Then moves camera to
     * the new marker.
     */
    private fun setMarkerToCurrentPosition() {
        val location = preferencesService.currentLocation
        val carLocation = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
                .position(carLocation)
                .title(getString(R.string.map_marker_name))
        carMarker = mMap.addMarker(markerOptions)

        setFabToNavigate(true)

        moveCameraToMarker()
    }

    fun onParkFabClick(view: View) {
        parkingService.parkCar()
    }

    /**
     * Removes the marker on the map
     */
    private fun removeMarker() {
        carMarker.remove()
        setFabToNavigate(false)
    }

    /**
     * Moves the camera to the currently placed marker
     */
    private fun moveCameraToMarker() {
        if (preferencesService.isCarParked == false) {
            return
        }

        val latLng = carMarker.position
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18.0f)
        mMap.animateCamera(cameraUpdate)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Disable "Navigate" and "Open in maps" buttons that show when selecting the marker
        mMap.getUiSettings().setMapToolbarEnabled(false)

        if (preferencesService.isCarParked == true) {
            setMarkerToCurrentPosition()
        }
    }

    private fun setFabToNavigate(shouldNavigate: Boolean) {
        val navigationFab = findViewById<FloatingActionButton>(R.id.fab_directions)
        val parkFab = findViewById<FloatingActionButton>(R.id.fab_park)

        if (shouldNavigate == true) {
            parkFab.visibility = View.GONE
            navigationFab.visibility = View.VISIBLE
        } else {
            parkFab.visibility = View.VISIBLE
            navigationFab.visibility = View.GONE
        }
    }
}
