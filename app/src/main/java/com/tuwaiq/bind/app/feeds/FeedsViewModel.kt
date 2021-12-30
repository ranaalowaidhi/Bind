package com.tuwaiq.bind.app.feeds

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuwaiq.bind.domain.models.PostData
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetLocationUseCase
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "FeedsViewModel"
@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val getLocationUseCase: GetLocationUseCase,
):ViewModel(){

    suspend fun getLocation(): LiveData<Location> =
        getLocationUseCase()

    suspend fun getPost(userLat:Double,userLon:Double):LiveData<List<PostData>> =
        getPostUseCase(userLat, userLon)

}