package com.tuwaiq.bind.data.repos

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.android.gms.location.LocationServices
import com.tuwaiq.bind.domain.repos.LocationRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "LocationRepoImpl"
class LocationRepoImpl @Inject constructor(
   private val context: Context
) :LocationRepo{

    @SuppressLint("MissingPermission")
    override suspend fun getUserLocation(): LiveData<Location> {
        return  liveData {
            val fusedLocationClient = LocationServices
                .getFusedLocationProviderClient(context.applicationContext)
            val userLocation = fusedLocationClient.lastLocation.await()
            emit(userLocation)
        }

    }
}