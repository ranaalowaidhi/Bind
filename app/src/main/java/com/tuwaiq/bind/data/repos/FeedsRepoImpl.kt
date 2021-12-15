package com.tuwaiq.bind.data.repos

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.bind.data.remote.PostData
import com.tuwaiq.bind.domain.repos.FeedsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



class FeedsRepoImpl:FeedsRepo {
    var postCollectionRef:CollectionReference = Firebase.firestore.collection("post")

    override suspend fun addPost(postData: PostData) {
        postCollectionRef.add(postData).await()
    }

}