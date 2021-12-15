package com.tuwaiq.bind.data.remote

import android.location.Location
import androidx.lifecycle.LiveData

data class PostData(
    var userID:String = "",
    var postText:String = "",
    var postPhoto:String = "",
    var postTime:String ="",
    var postLocation:Location
){

}
