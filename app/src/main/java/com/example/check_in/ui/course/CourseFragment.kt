package com.example.check_in.ui.course

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.check_in.R
import com.example.check_in.data.model.Student
import com.example.check_in.data.remote.ApiService
import com.example.check_in.data.remote.respone.CourseRespone
import com.example.check_in.databinding.FragmentCheckInBinding
import com.example.check_in.databinding.FragmentCourseBinding
import com.example.check_in.databinding.FragmentLoginBinding
import com.example.check_in.ui.login.LoginFragment
import com.example.check_in.ui.login.LoginViewModel
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CourseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CourseFragment : Fragment()  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: ItemCourseAdapter
    lateinit var binding: FragmentCourseBinding
    lateinit var  viewModelLogin : LoginViewModel
    var listCourse : ArrayList<CourseRespone> = ArrayList()
    companion object{
        var student : Student? =null
        var name = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding= DataBindingUtil.inflate(layoutInflater, R.layout.fragment_course, container, false)
        viewModelLogin = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getSinhVien(LoginFragment.username!!)
        getAPI()

    }

    private fun getAPI() {
        val dataApiService = ApiService.getService()
        val callBack = dataApiService.getListCourse(LoginFragment.username!!)
        callBack.enqueue(object : retrofit2.Callback<List<CourseRespone>>{
            override fun onFailure(call: Call<List<CourseRespone>>, t: Throwable) {
                Toast.makeText(context, "Fail",Toast.LENGTH_SHORT).show()
                Log.d("MyCourse","Login Fail ${t.toString()}")
            }

            override fun onResponse(call: Call<List<CourseRespone>>, response: Response<List<CourseRespone>>) {
                when(response.code()){
                    200 ->{
                        listCourse = response.body() as ArrayList<CourseRespone>
                        adapter= ItemCourseAdapter(requireParentFragment(),  listCourse)
                        binding.rcvListCourse.layoutManager = GridLayoutManager(requireContext(), 2)
                        binding.rcvListCourse.adapter = adapter
                        Log.d("MyCourse", "Login Successed 200 ${listCourse.size} ${response.body()} ${CourseFragment.name}" )
                    }
                    else -> Log.d("MyCourse","Login Succsed ${listCourse.size} ${response.code()} ${response.body()} ${LoginFragment.username}")
                }
            }
        })
    }


    private fun getSinhVien(username: String) {
        val dataApiService = ApiService.getService()
        val callBack = dataApiService.getStudent(username)
        callBack.enqueue(object : retrofit2.Callback<List<Student>> {
            override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                Log.d("GetSinhVien", "Get Sinh Vien That Bai")
            }

            override fun onResponse(
                call: Call<List<Student>>,
                response: Response<List<Student>>
            ) {
                if(response.code()==200){
                    if(response.body()!=null){
                        CourseFragment.student = response.body()!!.first()
                    }
                    Log.d("GetSinhVien", "Get Nhan Vien Thanh Cong")
                }
                else   Log.d("GetSinhVien", "${response.code()} ${username}")
            }

        })
    }
}