package com.example.check_in.ui.course

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.check_in.data.model.Student

class CourseViewModel : ViewModel(){
    val student = MutableLiveData<Student>()
}