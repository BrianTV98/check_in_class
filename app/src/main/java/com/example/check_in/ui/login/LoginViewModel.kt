package com.example.check_in.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.check_in.until.checkValidIDStudent
import com.example.check_in.until.checkValidPassword

class LoginViewModel : ViewModel(){

    enum class AuthenticationState{
        UNAUTHENTICATED,
        AUTHENTICATED,
        INVALID_AUTHENTICATION
    }

    val authenticated =  MutableLiveData<AuthenticationState>()
    var userName : String= ""


    init {
        authenticated.value =AuthenticationState.UNAUTHENTICATED
    }

    fun checkValid(username : String, password : String  ) : Boolean{
        if(username.checkValidIDStudent()&& password.checkValidPassword())
            return true
        return false
    }

    fun authenticate(username : String, password: String){

        if(passwordIsForUserName(username, password)){
            this.userName = userName
            authenticated.value= AuthenticationState.AUTHENTICATED
        }
        else{
            authenticated.value = AuthenticationState.INVALID_AUTHENTICATION
        }

    }

    private fun passwordIsForUserName(username: String, password: String): Boolean {
        //
        return true
    }


}