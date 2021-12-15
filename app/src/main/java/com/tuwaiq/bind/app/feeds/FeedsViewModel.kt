package com.tuwaiq.bind.app.feeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.okhttp.Dispatcher
import com.tuwaiq.bind.data.remote.PostData
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.AddPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(
):ViewModel(){


}