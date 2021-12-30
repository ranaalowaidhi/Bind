package com.tuwaiq.bind.domain.repos

import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.maps.model.LatLng

interface LocationRepo {

    suspend fun getUserLocation():LiveData<Location>


}