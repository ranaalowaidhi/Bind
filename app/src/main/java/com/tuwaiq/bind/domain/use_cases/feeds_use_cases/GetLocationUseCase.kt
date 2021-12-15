package com.tuwaiq.bind.domain.use_cases.feeds_use_cases

import android.location.Location
import androidx.lifecycle.LiveData
import com.tuwaiq.bind.domain.repos.LocationRepo
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val repo:LocationRepo
) {
    suspend operator fun invoke():Location = repo.getUserLocation()
}