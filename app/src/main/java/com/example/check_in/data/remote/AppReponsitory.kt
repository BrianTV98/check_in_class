package com.example.check_in.data.remote

import com.example.check_in.data.model.Student
import com.example.check_in.data.model.fake.ScheduleFake
import com.example.check_in.data.remote.respone.CourseRespone
import com.example.check_in.data.remote.respone.ResultRespone
import retrofit2.Call
import retrofit2.http.*

interface AppReponsitory {

    @POST("api/ModulesApp/layKhoaHocCuaSV")
    fun getListCourse(@Query("masv") masv: String): Call<List<CourseRespone>>

    @POST("api/Modules/CheckLoginSV")
    fun login(
        @Query("loginname") loginname: String,
        @Query("password") password: String
    ): Call<ResultRespone>

    @GET("api/SinhVien/Get")
    fun getListStudent(): Call<List<Student>>

    @GET("api/SinhVien/Get")
    fun getStudent(@Query("id") id: String): Call<List<Student>>

    @POST("api/ModulesApp/ThoiKhoaBieuChinhCuaSinhVien")
    fun getSchedule(@Body student: Student) : Call<List<ScheduleFake>>


    @POST("api/ModulesApp/KiemTraQuetMaQR")
    fun postCheckInt(@Query("maNhan") maNhan: String,
                     @Query("maSV") maSV : String,
                     @Query("maBH") maBH : String
    ) : Call<ResultRespone>


    @POST("api/ModulesApp/ThoiKhoaBieuCuaSinhVienTrongNgayHomNay")
    fun getScheduleToday(@Body student: Student?) : Call<List<ScheduleFake>>

}

data class Login(val loginname: String, val password: String)