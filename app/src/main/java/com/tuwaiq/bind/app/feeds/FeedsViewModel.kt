package com.tuwaiq.bind.app.feeds

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.bind.data.remote.PostDataDto
import com.tuwaiq.bind.domain.models.PostData
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetLocationUseCase
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val getLocationUseCase: GetLocationUseCase
):ViewModel(){



    fun getPost(userLat:Double,userLon:Double):LiveData<List<PostData>>{
        var tempList:List<PostData> = emptyList()
        val postLiveData:MutableLiveData<List<PostData>> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            tempList = getPostUseCase(userLat, userLon)
        }.invokeOnCompletion {
            viewModelScope.launch {
                postLiveData.value = tempList
            }
        }
        return postLiveData
    }

    lateinit var userLocation:Location
    fun getLocation(): LiveData<Location> {
        val locationLiveData:MutableLiveData<Location> = MutableLiveData()
        viewModelScope.launch {
            userLocation = getLocationUseCase()
        }.invokeOnCompletion {
            viewModelScope.launch {
                locationLiveData.value = userLocation
            }
        }
        return locationLiveData
    }

}