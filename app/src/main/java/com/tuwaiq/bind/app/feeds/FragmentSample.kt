package com.tuwaiq.bind.app.feeds

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tuwaiq.bind.R
import io.ak1.pix.helpers.*
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import lv.chi.photopicker.PhotoPickerFragment

private const val TAG = "FragmentSample"
class FragmentSample : AppCompatActivity(){

    private val resultsFragment = ResultsFragment {
        showCameraFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_sample)
        setupScreen()
        supportActionBar?.hide()
        showResultsFragment()
//        openPicker()
    }

//    override fun onImagesPicked(photos: ArrayList<Uri>) {
//        val uri = Uri.parse(photos.joinToString(separator = "\n") { it.toString() })
//        Log.e(TAG,"helloooo $uri")
//    }
//
//    private fun openPicker() {
//        PhotoPickerFragment.newInstance(
//            multiple = true,
//            allowCamera = true,
//            maxSelection = 5,
//            theme = R.style.ChiliPhotoPicker_Dark
//        ).show(supportFragmentManager, "picker")
//    }

    val options = Options().apply{
        ratio = Ratio.RATIO_AUTO
        count = 5
        spanCount = 3
        path = "Pix/Camera"
        isFrontFacing = false
        videoDurationLimitInSeconds = 10
        mode = Mode.All
        flash = Flash.Auto
        preSelectedUrls = ArrayList<Uri>()
    }

    private fun showCameraFragment() {
        addPixToActivity(R.id.container, options) {
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> {
                    showResultsFragment()
                    it.data.forEach {
                        Log.e(TAG, "showCameraFragment: ${it.path}")
                    }
                    resultsFragment.setList(it.data)
                }
                PixEventCallback.Status.BACK_PRESSED -> {
                    supportFragmentManager.popBackStack()

                }
            }

        }
    }

    private fun showResultsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, resultsFragment).commit()
    }

    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.container)
        if (f is ResultsFragment)
            super.onBackPressed()
        else
            PixBus.onBackPressedEvent()
        Log.e(TAG , "back pressed")
    }

}

class ResultsFragment(private val clickCallback: View.OnClickListener) : Fragment() {
    private val customAdapter = Adapter()
    fun setList(list: List<Uri>) {
        customAdapter.apply {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = fragmentBody(requireActivity(), customAdapter, clickCallback)
}
