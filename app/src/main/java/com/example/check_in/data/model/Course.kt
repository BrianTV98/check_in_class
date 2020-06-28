package com.example.check_in.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Course(
   @SerializedName("LOPs")
   @Expose
   val LOPs : ArrayList<String>?,

   @SerializedName("MaKH")
   @Expose
   val MaKH : String,

   @SerializedName("TenKH")
   @Expose
   var TenKH : String,

   @SerializedName("SoBuoi")
   @Expose
   val SoBuoi: Int,

   @SerializedName("NgayBD")
   @Expose
   val NgayBD: String,

   @SerializedName("NgayKT")
   @Expose
   val NgayKT: String,

   @SerializedName("Anh")
   @Expose
   val Anh : String
) {

}