package com.example.check_in.ui.login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.check_in.BR
import com.example.check_in.DataBinderMapperImpl
import com.example.check_in.R
import com.example.check_in.databinding.FragmentLoginBinding
import com.example.check_in.until.checkValidIDStudent
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), LoginListenner {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
            binding.setVariable(BR.listener,this)
        return binding.root
    }

    override fun btnLogin(view: View) {
        findNavController().navigate(R.id.action_loginFragment_to_QRCodeFragment)
    }

}
