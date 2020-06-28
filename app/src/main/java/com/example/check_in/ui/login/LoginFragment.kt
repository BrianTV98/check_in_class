package com.example.check_in.ui.login


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.check_in.BR
import com.example.check_in.R
import com.example.check_in.data.remote.ApiService
import com.example.check_in.data.remote.respone.ResultRespone
import com.example.check_in.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), LoginListenner {
    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginViewModel
    lateinit var sharedPref : SharedPreferences
    val KEY_NAME: String ="LOGIN_NAME"
    companion object{
        var username : String?=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val PREFS_NAME = "kotlincodes"
        sharedPref = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        username=getValueString(KEY_NAME)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel= ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.authenticated.observe(viewLifecycleOwner, Observer {
            if(it == LoginViewModel.AuthenticationState.AUTHENTICATED){
                findNavController().navigate(R.id.action_loginFragment_to_courseFragment)
            }
        })

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.setVariable(BR.listener, this)

//        if (!username.isNullOrBlank()){
//            viewModel.authenticated.value = LoginViewModel.AuthenticationState.AUTHENTICATED
//        }

        binding.loLoginMain.setOnTouchListener { v, event ->
            if (event != null && event.action == MotionEvent.ACTION_MOVE) {
                val imm =
                    requireActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                var isHide = imm!!.isAcceptingText()
                if (isHide) {
                    imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
                }
                true
            }
            false
        }

        return binding.root
    }

    override fun btnLogin(view: View) {
        val useNameLayout = binding.loginEdtUsername
        val passwordLayout = binding.loEditPw
        if (useNameLayout.editText?.length()!! < 1) {
            useNameLayout.error = getString(R.string.err_username)
            return
        }

        if (passwordLayout.editText?.length()!! < 6) {
            passwordLayout.editText?.error = getString(R.string.err_password)
            return
        }

        checValidate(
                useNameLayout.editText!!.text.toString().toUpperCase(),
                passwordLayout.editText!!.text.toString()
            )


    }

    private fun checValidate(username: String, password: String) {
        val dataApiService = ApiService.getService()
        var loginResponse : ResultRespone  =ResultRespone(false)
        val callBack = dataApiService.login(username, password)
        callBack.enqueue(object : Callback<ResultRespone> {

            override fun onFailure(call: Call<ResultRespone>, t: Throwable) {
                Log.d("LoginState","Login Fail ${t.toString()}")
            }

            override fun onResponse(call: Call<ResultRespone>, response: Response<ResultRespone>) {
                Log.d("Login", response.code().toString());
                 val result =response.body() as ResultRespone;
                Log.d("Login", result.state.toString());
                if(result.state){
                    LoginFragment.username =username
                    save(KEY_NAME, username)
                    viewModel.authenticated.value= LoginViewModel.AuthenticationState.AUTHENTICATED;
                }
                if(response.code()!=200){
                    Toast.makeText(context, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    fun save(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_NAME, value)

        editor.commit()
    }
    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

}
