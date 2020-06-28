package com.example.check_in.ui.Schedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.check_in.data.model.Student

class ScheduleViewModel : ViewModel() {
    val listStudent = MutableLiveData<ArrayList<Student>>();
}