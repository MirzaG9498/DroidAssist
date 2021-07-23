package models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.droidassist.R

class TimeTableDayAdapter(val context: Context, val timeTableDayList: List<TimeTableDay>):RecyclerView.Adapter<TimeTableDayAdapter.TimeTableDayViewHolder>() {
    class TimeTableDayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTimetableSubjectCode: TextView = itemView.findViewById(R.id.tvTimetableSubjectCode)
        val tvTimetableSlot: TextView = itemView.findViewById(R.id.tvTimetableSlot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeTableDayViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_time_table,parent, false)
        return TimeTableDayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimeTableDayViewHolder, position: Int) {
        val currentTimeTableDayItem = timeTableDayList[position]
        holder.tvTimetableSubjectCode.text = currentTimeTableDayItem.subjectCode
        holder.tvTimetableSlot.text = currentTimeTableDayItem.slot
    }

    override fun getItemCount(): Int {
        return timeTableDayList.size
    }
}