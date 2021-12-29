package com.tuwaiq.bind.app.feeds

import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.bind.data.remote.PostDataDto
import com.tuwaiq.bind.domain.models.PostData
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.AddPostUseCase
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetLocationUseCase
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.UploadImgToStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AddPostViewModel"
@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val addPostUseCase: AddPostUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val uploadImgToStorageUseCase:UploadImgToStorageUseCase
):ViewModel(){

    fun addPost(postData: PostData){
        viewModelScope.launch(Dispatchers.IO) {
            addPostUseCase(postData)
        }
    }

    fun uploadImgToStorage(filename:String,uri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            uploadImgToStorageUseCase(filename,uri)
            Log.e(TAG,"uploading")
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