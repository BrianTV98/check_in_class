package com.example.check_in.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.check_in.R
import com.example.check_in.data.remote.respone.CourseRespone
import com.example.check_in.databinding.ItemCourseLayoutBinding

class ItemCourseAdapter(val fragment: Fragment, val listCourse : ArrayList<CourseRespone>) : RecyclerView.Adapter<ItemCourseAdapter.ViewHolder>(){

    inner class ViewHolder(view: ItemCourseLayoutBinding) : RecyclerView.ViewHolder(view.root){
        var itemCourseLayoutBinding: ItemCourseLayoutBinding? = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(fragment.context)
        val itemCourseLayoutBinding = DataBindingUtil.inflate<ItemCourseLayoutBinding>(inflater, R.layout.item_course_layout, parent, false);
        return ViewHolder(itemCourseLayoutBinding)
    }

    override fun getItemCount(): Int {
        return  listCourse.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemCourseLayoutBinding?.course = listCourse[position]
        holder.itemView.setOnClickListener {
            val action = CourseFragmentDirections.actionCourseFragmentToDetailCourseFragment(position)
            CourseFragment.name = listCourse[position].TenKH
            findNavController(fragment).navigate(action)
        }
    }

}