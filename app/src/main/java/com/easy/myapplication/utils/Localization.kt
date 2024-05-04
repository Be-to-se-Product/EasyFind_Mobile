package com.easy.myapplication.utils

import LatandLong
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices


interface LocationCallback {
    fun onSuccess(latitude: Double, longitude: Double)
    fun onError(message: String?)
}
fun getLatLong(context: Context, callback: LocationCallback){
    val locationProvider = LocationServices.getFusedLocationProviderClient(context)
    var localization:LatandLong? = null 

    if (!(ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    ) {
        locationProvider.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    val lat = location.latitude
                    val long = location.longitude
                    callback.onSuccess(lat,long)
                }

            }
            .addOnFailureListener {
                callback.onError(it.message)
                Log.e("Location_error", "${it.message}")
            }
    }

}
