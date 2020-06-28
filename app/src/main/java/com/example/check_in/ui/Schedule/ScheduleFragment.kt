package com.example.check_in.ui.Schedule

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.check_in.R
import com.example.check_in.data.model.DayOfWeek
import com.example.check_in.data.model.Schedule
import com.example.check_in.data.model.SessionSchdule
import com.example.check_in.data.model.Student
import com.example.check_in.data.model.fake.ScheduleFake
import com.example.check_in.data.remote.ApiService
import com.example.check_in.databinding.FragmentScheduleBinding
import com.example.check_in.ui.login.LoginFragment
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


//val idStudent ="N16DCCN004";
class ScheduleFragment : Fragment() {
//    private var param1: String? = null
//    private var param2: String? = null

    lateinit var binding : FragmentScheduleBinding

    private lateinit var viewModel: ScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_schedule, container, false)
        viewModel= ViewModelProviders.of(this).get(ScheduleViewModel::class.java)

//        viewModel.listStudent.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(context, "${it.size}", Toast.LENGTH_SHORT).show()
//        })
        //fake data

//        val listData = ArrayList<Schedule>();
//        val sesion : SessionSchdule = SessionSchdule("Lập trình hướng đối tương", "2E35","CQCN01","7h30", "11h30")
//        listData.add(Schedule(DayOfWeek.Hai,sesion,sesion));
//        listData.add(Schedule(DayOfWeek.Ba,sesion, sesion));
//        listData.add(Schedule(DayOfWeek.Tu,sesion,sesion));
//        listData.add(Schedule(DayOfWeek.Nam,sesion, sesion));
//        listData.add(Schedule(DayOfWeek.Sau,sesion,sesion));
//        listData.add(Schedule(DayOfWeek.Bay,sesion,sesion));
//        listData.add(Schedule(DayOfWeek.CN,sesion,sesion));
//        val adapter = ScheduleRecycleViewAdapter(this,listData);
//        binding.rcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
//        binding.rcv.adapter =adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getAPI(LoginFragment.username!!)
    }

    private fun getAPI(maSV: String)  {

//        if(listStudent!=null){
//            listStudent.clear()
//        }
        val dataApiService = ApiService.getService()
        val callBack = dataApiService.getListStudent()
        callBack.enqueue(object : retrofit2.Callback<List<Student>>{
            override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                Toast.makeText(context, "Fail",Toast.LENGTH_SHORT).show()
                Log.d("Schedule","get Student Fail ${t.toString()}")
            }

            override fun onResponse(call: Call<List<Student>>, response: Response<List<Student>>) {
                when(response.code()){
                    200 ->{
                        val listStudent = response.body() as ArrayList<Student>
                        var student : Student? =null
                        listStudent.forEach {
                            if (it.MaSV == maSV) {
                                student = it
                            }
                        }

                        // fake retrofix do thang api viet nguu qua... lam tao phai ngu theo... chu thich o dau de mai ghi nho  :((
                        val dataApiService123 = ApiService.getService()
                        val callBack123 = dataApiService123.getSchedule(student!!)
                        callBack123.enqueue(object : retrofit2.Callback<List<ScheduleFake>>{
                            override fun onFailure(call: Call<List<ScheduleFake>>, t: Throwable) {
                                Log.d("Tab2","Fail")
                            }

                            @RequiresApi(Build.VERSION_CODES.O)
                            override fun onResponse(
                                call: Call<List<ScheduleFake>>,
                                response: Response<List<ScheduleFake>>
                            ) {
                                val listscheduleFake = response.body() as ArrayList<ScheduleFake>
                                val listData = ArrayList<Schedule>();
//                                val sesion : SessionSchdule = SessionSchdule("Lập trình hướng đối tương", "2E35","CQCN01","7h30", "11h30")
                                var sesionS2 : SessionSchdule? =null
                                var sesionC2 : SessionSchdule?=null
                                var sesionS3 : SessionSchdule?=null
                                var sesionC3 : SessionSchdule?=null
                                var sesionS4 : SessionSchdule?=null
                                var sesionC4 : SessionSchdule?=null
                                var sesionS5 : SessionSchdule?=null
                                var sesionC5 : SessionSchdule? =null
                                var sesionS6 : SessionSchdule? =null
                                var sesionC6 : SessionSchdule? =null
                                var sesionS7 : SessionSchdule? =null
                                var sesionC7 : SessionSchdule? =null
                                var sesionS8 : SessionSchdule? =null
                                var sesionC8 : SessionSchdule? =null

                                listscheduleFake.forEach {
                                    // xy ly thu
                                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                                    val date: Date = sdf.parse(it.ThoiGianDBHoc.substring(0,10))
                                    val cal = Calendar.getInstance()
                                    cal.time = date
                                    val day = cal[Calendar.DAY_OF_WEEK]
                                    Log.d("ABC", day.toString())

                                    // xu ly buoi hoc
                                    val time :Int= it.ThoiGianDBHoc.substring(11,13).toInt()
                                    // thu 2
                                    if(day==Calendar.MONDAY&& time<12){
                                       sesionS2= SessionSchdule(it.MaBH,it.PhongHoc,it.TenLop, it.ThoiGianDBHoc.substring(11,16),"11:30")
                                    }
                                    if(day==Calendar.MONDAY&& time>=12){
                                        sesionC2= SessionSchdule(it.MaBH,it.PhongHoc,it.TenLop ,it.ThoiGianDBHoc.substring(11,16),"4:30")
                                    }
                                    // thu 3
                                    if(day==Calendar.TUESDAY&& time<12){
//                                        sesionS3= SessionSchdule(it.MaBH,it.PhongHoc,it.TenLop ,it.ThoiGianDBHoc.substring(11,16),"4:30")
                                    }
                                    if(day==Calendar.TUESDAY&& time>=12){
                                        sesionC3= SessionSchdule(it.MaBH,it.PhongHoc,it.TenLop ,it.ThoiGianDBHoc.substring(11,16),"4:30")
                                    }

                                    // thu 4
                                    if(day==Calendar.WEDNESDAY&& time<12){
                                        sesionS4= SessionSchdule(it.MaBH,it.PhongHoc, it.TenLop,it.ThoiGianDBHoc.substring(11,15),"4:30")
                                    }
                                    if(day==Calendar.WEDNESDAY&& time>=12){
                                        sesionC4= SessionSchdule(it.MaBH,it.PhongHoc,it.TenLop,it.ThoiGianDBHoc.substring(11,16),"4:30")
                                    }

                                    //thu 5
                                    if(day==Calendar.THURSDAY&& time<12){
                                        sesionS5= SessionSchdule(it.MaBH,it.PhongHoc,it.TenLop,it.ThoiGianDBHoc.substring(11,16),"4:30")
                                    }
                                    if(day==Calendar.THURSDAY&& time>=12){
                                        sesionC5= SessionSchdule(it.MaBH,it.PhongHoc,it.TenLop,it.ThoiGianDBHoc.substring(11,16),"4:30")
                                    }

                                    //thu 6
                                    if(day==Calendar.FRIDAY&& time<12){
                                        sesionS6= SessionSchdule(it.MaBH,it.PhongHoc, it.TenLop,it.ThoiGianDBHoc.substring(11,16),"4:30")
                                    }
                                    if(day==Calendar.FRIDAY&& time>=12){
                                        sesionC6= SessionSchdule(it.MaBH,it.PhongHoc, it.TenLop,it.ThoiGianDBHoc.substring(11,16),"4:30")
                                    }

                                    //Thu 7
                                    if(day==Calendar.SATURDAY&& time<12){
                                        sesionS7= SessionSchdule(it.MaBH,it.PhongHoc,it.TenLop,time.toString(),"4:30")
                                    }
                                    if(day==Calendar.SATURDAY&& time>=12){
                                        sesionC7= SessionSchdule(it.MaBH,it.PhongHoc, it.TenLop,time.toString(),"4:30")
                                    }

                                    //Chu Nhat
                                    if(day==Calendar.SUNDAY&& time<12){
                                        sesionS8= SessionSchdule(it.MaBH,it.PhongHoc, it.TenLop,time.toString(),"4:30")
                                    }
                                    if(day==Calendar.SUNDAY&& time>=12){
                                        sesionC8= SessionSchdule(it.MaBH,it.PhongHoc, it.TenLop,time.toString(),"4:30")
                                    }

                                }

                                listData.add(Schedule(DayOfWeek.Hai,sesionS2, sesionC2))
                                listData.add(Schedule(DayOfWeek.Ba,sesionS3, sesionC3))
                                listData.add(Schedule(DayOfWeek.Tu,sesionS4, sesionC4))
                                listData.add(Schedule(DayOfWeek.Nam,sesionS5, sesionC5))
                                listData.add(Schedule(DayOfWeek.Sau,sesionS6, sesionC6))
                                listData.add(Schedule(DayOfWeek.Bay,sesionS7, sesionC7))
                                listData.add(Schedule(DayOfWeek.CN,sesionS8, sesionC8))

                                val adapter = ScheduleRecycleViewAdapter(requireParentFragment(),listData);
                                binding.rcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                                binding.rcv.adapter =adapter
                                Log.d("Tab2","${response.body()}")

                            }

                        })




                        Log.d("Schedule", "get Student Successed 200 ${response.body().toString()}" )
                        return
                    }
                    else -> Log.d("Schedule","get Student Succsed ${response.code()}")
                }

            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}