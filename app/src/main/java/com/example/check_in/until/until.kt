package com.example.check_in.until

fun String.checkValidPassword(): Boolean{
    if(this.length<6) return false
    return true
}

fun String.checkValidIDStudent() : Boolean{
    if(this.length!=10) return false
    return true
}