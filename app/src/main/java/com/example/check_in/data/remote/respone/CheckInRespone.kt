package com.example.check_in.data.remote.respone

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckInRespone(
    @SerializedName("Message")
    @Expose
    val Message : String
)