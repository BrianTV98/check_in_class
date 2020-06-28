package com.example.check_in.ui.qrcode


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.example.check_in.BR
import com.example.check_in.R
import com.example.check_in.data.model.fake.ScheduleFake
import com.example.check_in.data.remote.ApiService
import com.example.check_in.data.remote.respone.ResultRespone
import com.example.check_in.databinding.FragmentQrcodeBinding
import com.example.check_in.ui.course.CourseFragment
import com.example.check_in.ui.login.LoginFragment
import com.example.check_in.ui.login.LoginViewModel
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */


class QRCodeFragment : Fragment(), QRCodeListenner {

    private lateinit var codeScanner: CodeScanner
    lateinit var binding: FragmentQrcodeBinding

    companion object {
        var monHocHomNay: ScheduleFake? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qrcode, container, false)

        binding.setVariable(BR.listenner, this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, binding.scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                getAPI(it.text.trim())

                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
            }
        }
        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onStart() {
        super.onStart()
        getListMonHocHomNay()
    }

    private fun getListMonHocHomNay() {
        val viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val dataApiService = ApiService.getService()
        Log.d("QR", "${CourseFragment.student!!.MaSV}")
        val callBack = dataApiService.getScheduleToday(CourseFragment.student);
        callBack.enqueue(object : retrofit2.Callback<List<ScheduleFake>> {
            override fun onFailure(call: Call<List<ScheduleFake>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<List<ScheduleFake>>,
                response: Response<List<ScheduleFake>>
            ) {
                if (response.code() == 200) {
                    val listMonHocHomNay = response.body() as ArrayList<ScheduleFake>
                    listMonHocHomNay.forEach {
                        val time: Int = it.ThoiGianDBHoc.substring(11, 13).toInt()
                        var sesion: String = ""
                        if (time < 12) sesion = "S" else sesion = "C"
                        val sdf = SimpleDateFormat("HH")
                        val currentTime = sdf.format(Date()).toInt()
                        var sesion2 = ""
                        if (currentTime < 12) sesion2 = "S" else sesion2 = "C"
                        if (sesion == sesion2) QRCodeFragment.monHocHomNay = it
                    }
//                    Log.d("QR", "${monHocHomNay!!.MaBH}")
                } else Log.d("QR", "${response.code()}")
            }

        })
    }

    private fun getAPI(code: String) {
        val dataApiService = ApiService.getService()
        if (monHocHomNay == null)
            Toast.makeText(context, "Hôm nay bạn ko có môn học nào??", Toast.LENGTH_SHORT).show()
        else {
            val callBack = dataApiService.postCheckInt(code, LoginFragment.username!!, monHocHomNay!!.MaBH)
            Log.d("Debug", "${code} ${LoginFragment.username!!} ${monHocHomNay!!.MaBH}")
            callBack.enqueue(object : retrofit2.Callback<ResultRespone> {
                override fun onFailure(call: Call<ResultRespone>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<ResultRespone>,
                    response: Response<ResultRespone>
                ) {
                    if (response.code() == 200) {
                        val resultRespone = response.body()
                        if(resultRespone?.state==true){
                            showAlertDiagLog()
//                            Toast.makeText(requireContext(), "Check in Thành Công", Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(requireContext(), "Check in thất bại", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    fun showAlertDiagLog(){
        val builder = AlertDialog.Builder(requireActivity())

        // Set the alert dialog title
        builder.setTitle("Thông Báo")

        // Display a message on alert dialog
        builder.setMessage("Bạn đã điểm danh thành công")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("OK"){dialog, which ->
            // Do something when user press the positive button
//            Toast.makeText(context,"Ok, we chan.",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.detailCourseFragment)
            // Change the app background color
//            root_layout.setBackgroundColor(Color.RED)
        }


//        // Display a negative button on alert dialog
//        builder.setNegativeButton("No"){dialog,which ->
////            Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()
//        }
//
//
//        // Display a neutral button on alert dialog
//        builder.setNeutralButton("Cancel"){_,_ ->
////            Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
//        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun btnScanQRcode(view: View) {
        TODO("Not yet implemented")
    }

    override fun btnGotoCapture(view: View) {
        TODO("Not yet implemented")
    }

}
