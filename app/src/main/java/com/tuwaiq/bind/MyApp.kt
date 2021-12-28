package com.tuwaiq.bind

import android.app.Application
import android.util.Log
import com.tuwaiq.bind.app.feeds.GlideImageLoader
import dagger.hilt.android.HiltAndroidApp
import lv.chi.photopicker.ChiliPhotoPicker

private const val TAG = "MyApp"
@HiltAndroidApp
class MyApp:Application(){
    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"im in the app")
        ChiliPhotoPicker.init(
            loader = GlideImageLoader(),
            authority = "lv.chi.sample.fileprovider"
        )
    }
}