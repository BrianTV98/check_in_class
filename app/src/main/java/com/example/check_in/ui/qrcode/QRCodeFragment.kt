package com.example.check_in.ui.qrcode


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.check_in.BR
import com.example.check_in.R
import com.example.check_in.databinding.FragmentQrcodeBinding
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_qrcode.*

/**
 * A simple [Fragment] subclass.
 */
class QRCodeFragment : Fragment(), QRCodeListenner {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

         var  binding: FragmentQrcodeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_qrcode,container,false)

        binding.setVariable(BR.listenner,this)

        return binding.root
    }

    private fun initScan() {
        IntentIntegrator(activity).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data)

        if(result!=null){
            if(result.contents!=null){
                Toast.makeText(context, "this is text of scan is empty", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context,"Co data nha", Toast.LENGTH_LONG).show()
                qr_edit_text.text = result.contents
            }
        }

        //super.onActivityResult(requestCode, resultCode, data)

    }

    override fun btnScanQRcode(view: View) {
        initScan()
    }

    override fun btnGotoCapture(view: View) {
        //Toast.makeText(context, "Okkk ko sao ca ", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_QRCodeFragment_to_checkInFragment)
    }


}
