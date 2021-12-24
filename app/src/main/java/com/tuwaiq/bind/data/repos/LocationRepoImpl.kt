package com.tuwaiq.bind.data.repos

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.tuwaiq.bind.domain.repos.LocationRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "LocationRepoImpl"
class LocationRepoImpl @Inject constructor(
   private val context: Context
) :LocationRepo{

    @SuppressLint("MissingPermission")
    override suspend fun getUserLocation(): Location {
        var userLocation:Location
        val fusedLocationClient = LocationServices
            .getFusedLocationProviderClient(context.applicationContext)
        userLocation = fusedLocationClient.lastLocation.await()
        return userLocation
    }
}