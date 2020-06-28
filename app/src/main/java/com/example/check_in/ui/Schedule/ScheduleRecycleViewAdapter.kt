package com.example.check_in.ui.Schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.check_in.R
import com.example.check_in.data.model.Schedule
import com.example.check_in.databinding.ItemCourseLayoutBinding
import com.example.check_in.databinding.ItemDayOfScheduleLayoutBinding

class ScheduleRecycleViewAdapter (val fragment : Fragment, private val list : ArrayList<Schedule>) : RecyclerView.Adapter<ScheduleRecycleViewAdapter.ViewHolder>(){
    class ViewHolder(view: ItemDayOfScheduleLayoutBinding): RecyclerView.ViewHolder(view.root) {
        val item : ItemDayOfScheduleLayoutBinding = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(fragment.context)
        val itemDayOfScheduleLayoutBinding = DataBindingUtil.inflate<ItemDayOfScheduleLayoutBinding>(inflater, R.layout.item_day_of_schedule_layout, parent, false);
        return ViewHolder(itemDayOfScheduleLayoutBinding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.schedule = list[position]
    }

}