package de.dilello.www.snappypark.services

import android.content.Context
import com.google.android.gms.maps.model.LatLng

/**
 * Created by timothy on 20.08.17.
 */
const val SHARED_PREFERENCES_FILENAME = "de.dilello.www.snappypark"
const val SHARED_PREFERENCES_ONBOARDING_KEY = "snappyParkOnboardingKey"
const val SHARED_PREFERENCES_LONGITUDE_KEY = "snappyParkLongitudeKey"
const val SHARED_PREFERENCES_LATITUDE_KEY = "snappyParkLatitudeKey"
const val SHARED_PREFERENCES_PARKED_KEY = "snappyParkParkedKey"
const val SHARED_PREFERENCES_LEGAL_KEY = "snappyParkLegalKey"

class PreferencesService(val context: Context) {
    val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILENAME, Context.MODE_PRIVATE)
    val preferencesEditor = sharedPreferences.edit()

    var isOnboardingCompleted: Boolean
        get() {
            return sharedPreferences.getBoolean(SHARED_PREFERENCES_ONBOARDING_KEY, false)
        }
        set(value: Boolean) {
            preferencesEditor.putBoolean(SHARED_PREFERENCES_ONBOARDING_KEY, value)
            preferencesEditor.commit()
        }

    var currentLocation: LatLng
        get() {
            val longitude = sharedPreferences.getString(SHARED_PREFERENCES_LONGITUDE_KEY, null).toDouble()
            val latitude = sharedPreferences.getString(SHARED_PREFERENCES_LATITUDE_KEY, null).toDouble()

            return LatLng(latitude, longitude)
        }
        set(value) {
            val longitude = value.longitude
            val latitude = value.latitude
            preferencesEditor.putString(SHARED_PREFERENCES_LONGITUDE_KEY, longitude.toString())
            preferencesEditor.putString(SHARED_PREFERENCES_LATITUDE_KEY, latitude.toString())
            preferencesEditor.commit()
        }

    var isCarParked: Boolean
        get() = sharedPreferences.getBoolean(SHARED_PREFERENCES_PARKED_KEY, false)
        set(value) {
            preferencesEditor.putBoolean(SHARED_PREFERENCES_PARKED_KEY, value)
            preferencesEditor.commit()
        }

    var isLegalRead: Boolean
        get() = sharedPreferences.getBoolean(SHARED_PREFERENCES_LEGAL_KEY, false)
        set(value) {
            preferencesEditor.putBoolean(SHARED_PREFERENCES_LEGAL_KEY, value)
            preferencesEditor.commit()
        }
}