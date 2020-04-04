package com.example.check_in.ui.check_in

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Point
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.util.Rational
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.example.check_in.BR
import com.example.check_in.MainActivity
import com.example.check_in.R
import com.example.check_in.databinding.FragmentCheckInBinding
import com.example.check_in.databinding.FragmentCheckInBindingImpl
import kotlinx.android.synthetic.main.fragment_check_in.*
import java.io.File
import java.nio.ByteBuffer
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */

private const val REQUEST_CODE_PERMISSIONS = 10

//danh sach tat ca permission specified in mainfest
private val REQUEST_PERMISSTION = arrayOf(
    Manifest.permission.INTERNET,
    Manifest.permission.CAMERA,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

class CheckInFragment : Fragment() , LifecycleOwner{

    val lensFacing = CameraX.LensFacing.FRONT

    //
    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
    }

    var location: Location? = null


    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding : FragmentCheckInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_in, container,false)
//        binding.setVariable(BR.listenner, this)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // camera and location

        if (allPermisstionGranted()) {
            view_finder.post { startCamera() }
        } else {
            requestPermissions(REQUEST_PERMISSTION, REQUEST_CODE_PERMISSIONS)
        }
        //gps
        var manager: LocationManager =
            context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager;
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }
    }

    private fun startCamera() {

        //tao thiet lap doi tuong cho viewfinder user case

        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val width = size.x

        val metrics = DisplayMetrics().also { view_finder.display.getRealMetrics(it) }
        //val screenSize = Size(metrics.widthPixels, metrics.heightPixels)
        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)
        Log.d("TestApp", metrics.widthPixels.toString() + metrics.heightPixels.toString())
        val previewConfig = PreviewConfig.Builder().apply {
            //setTargetAspectRatioCustom(screenAspectRatio)
            setTargetRotation(view_finder.display.rotation)
            setLensFacing(lensFacing)
        }.build()
        // xay dung viewfinder cho nguoi dung
        val preview = AutoFitPreviewBuilder.build(previewConfig, view_finder)
        //val preview =AutoFitPreviewBuilder(previewConfig,view_finder)
        // tinh toan lai bo cuc khi update

        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            setLensFacing(lensFacing)
            //setTargetResolution(Size(view_finder.measuredWidth, view_finder.height))

            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
           // setTargetAspectRatioCustom(screenAspectRatio)
            setTargetRotation(view_finder.display.rotation)
            // tuy thich do phan giai
        }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)

        // chup anh
        btn_check_in.setOnClickListener {

            /*
             * check gps
             * else capture
             */
            val file: File = File(
                MainActivity.getOutputDirectory(requireContext()),
                FILENAME +
                        PHOTO_EXTENSION
            )

            var gps: GPStracker = GPStracker(context!!)
            location = gps.getLocation()
            if (location != null) {

                Log.d(
                    "CheckinFragment",
                    "Latitude = ${location!!.latitude}  Longitude =${location!!.longitude}"
                )
            }


            imageCapture.let {

                imageCapture.takePicture(
                    file,
                    executor,
                    object : ImageCapture.OnImageSavedListener {
                        override fun onImageSaved(file: File) {
                            var msg = "Photo captuer succeeded :${file.absoluteFile}"
                            Log.d("CameraXApp", msg)
                            view_finder.post {
                                Toast.makeText(context, "Check in thanh cong", Toast.LENGTH_SHORT)
                                    .show()

                                if (file.exists()) {

                                    val myBitmap: Bitmap =
                                        BitmapFactory.decodeFile(file.absolutePath)
                                    val matrix: Matrix = Matrix()
                                    matrix.postRotate(270f);
                                    imvView.setImageBitmap(
                                        Bitmap.createBitmap(
                                            myBitmap,
                                            0,
                                            0,
                                            myBitmap.width,
                                            myBitmap.height,
                                            matrix,
                                            true
                                        )
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Null as nha",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }
                        }

                        override fun onError(
                            imageCaptureError: ImageCapture.ImageCaptureError,
                            message: String,
                            cause: Throwable?
                        ) {
                            val msg = "Photo capture failed :$message"
                            Log.e("CameraXApp", msg, cause)
                            view_finder.post {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }


            // Location

        }

        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            setImageReaderMode(
                ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE
            )
            setTargetResolution(Size(720, width))
            setLensFacing(lensFacing)
        }.build()
        val analyzerUserCase = ImageAnalysis(analyzerConfig).apply {
            setAnalyzer(executor, LuminostiyAnalyzer())
        }
        CameraX.bindToLifecycle(this, preview, imageCapture, analyzerUserCase)


    }

    private fun allPermisstionGranted() = REQUEST_PERMISSTION.all {
        ContextCompat.checkSelfPermission(context!!, it) == PackageManager.PERMISSION_GRANTED
    }

    fun buildAlertMessageNoGps() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, which ->
                //startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS));
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context!!.startActivity(intent)
            }
            .setNegativeButton("No") { dialog, which ->
                Toast.makeText(context, " chua Kich hoat", Toast.LENGTH_LONG).show()
            }

        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private class LuminostiyAnalyzer : ImageAnalysis.Analyzer {
        private var lastAnalyzedTimestamp = 0L

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()
            val data = ByteArray(remaining())
            get(data)
            return data
        }

        override fun analyze(image: ImageProxy?, rotationDegrees: Int) {
            val currencyTimestamp = System.currentTimeMillis()
            if (currencyTimestamp - lastAnalyzedTimestamp >= TimeUnit.SECONDS.toMillis(1)) {
                val buffer = image?.planes?.get(0)?.buffer
                val data = buffer?.toByteArray()
                val pixels = data?.map { it.toInt() and 0xFF }

                val luma = pixels?.average()

                Log.d("CameraXApp", "Averager luminostity : $luma")

                lastAnalyzedTimestamp = currencyTimestamp
            }
        }

    }

//    override fun btncheckIn(view: View) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
}