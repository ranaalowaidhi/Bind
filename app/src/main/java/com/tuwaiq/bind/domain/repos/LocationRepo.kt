package com.tuwaiq.bind.domain.repos

import android.location.Location
import androidx.lifecycle.LiveData

interface LocationRepo {

    suspend fun getUserLocation():Location

}