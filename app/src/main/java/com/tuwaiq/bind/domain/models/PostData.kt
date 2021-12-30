package com.tuwaiq.bind.domain.models

import android.net.Uri

data class PostData(
    var postOwner:String = "",
    var postText:String = "",
    var postPhoto: String = "",
    var postTime:String ="",
    var postLat:String = "",
    var postLan:String = "",
    var userName:String = ""
)
