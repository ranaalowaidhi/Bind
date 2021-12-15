package com.tuwaiq.bind.app.feeds

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.bind.data.remote.PostData
import com.tuwaiq.bind.data.repos.LocationRepoImpl
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.AddPostUseCase
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val addPostUseCase: AddPostUseCase,
    private val getLocationUseCase: GetLocationUseCase
):ViewModel(){

    fun addPost(postData: PostData){
        viewModelScope.launch(Dispatchers.IO) {
            addPostUseCase(postData)
        }
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