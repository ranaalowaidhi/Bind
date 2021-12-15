package com.tuwaiq.bind.data.repos

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tuwaiq.bind.domain.repos.LocationRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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