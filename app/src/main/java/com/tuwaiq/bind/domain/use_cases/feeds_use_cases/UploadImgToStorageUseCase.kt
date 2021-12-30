package com.tuwaiq.bind.domain.use_cases.feeds_use_cases

import android.net.Uri
import androidx.lifecycle.LiveData
import com.tuwaiq.bind.domain.repos.FeedsRepo
import javax.inject.Inject

class UploadImgToStorageUseCase @Inject constructor(
    private val feedsRepo: FeedsRepo
){

    operator fun invoke(filename:String,uri:Uri):LiveData<String> =
        feedsRepo.uploadImgToStorage(filename,uri)

}