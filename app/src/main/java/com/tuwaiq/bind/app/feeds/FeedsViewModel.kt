package com.tuwaiq.bind.app.feeds

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.bind.domain.models.PostComment
import com.tuwaiq.bind.domain.models.PostData
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.AddCommentUseCase
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetCommentsUseCase
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetLocationUseCase
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FeedsViewModel"
@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val addCommentUseCase:AddCommentUseCase,
    private val getCommentsUseCase: GetCommentsUseCase
):ViewModel(){

    suspend fun getLocation(): LiveData<Location> =
        getLocationUseCase()

    fun addComment(postComment: PostComment){
        viewModelScope.launch(Dispatchers.IO) {
            addCommentUseCase(postComment)
        }
    }
    
    suspend fun getPost(userLat:Double,userLon:Double):LiveData<List<PostData>> =
        getPostUseCase(userLat, userLon)

    suspend fun getComments(postId:String):LiveData<List<PostComment>> =
        getCommentsUseCase(postId)

}