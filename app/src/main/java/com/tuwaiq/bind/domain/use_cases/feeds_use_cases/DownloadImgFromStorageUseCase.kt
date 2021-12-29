package com.tuwaiq.bind.domain.use_cases.feeds_use_cases

import android.net.Uri
import com.tuwaiq.bind.domain.repos.FeedsRepo
import javax.inject.Inject

class DownloadImgFromStorageUseCase @Inject constructor(
    private val feedsRepo: FeedsRepo
) {
    suspend operator fun invoke(filename:String)=
        feedsRepo.downloadImgFromStorage(filename)
}