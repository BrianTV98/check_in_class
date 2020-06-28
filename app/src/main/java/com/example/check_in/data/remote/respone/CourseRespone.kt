package com.example.check_in.data.remote.respone

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CourseRespone(
    @SerializedName("MaKH")
    @Expose
    val MaKH: String,
    @SerializedName("TenKH")
    @Expose
    val TenKH : String
){

}