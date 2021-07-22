package models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.droidassist.R

class AttendanceAdapter(private val context: Context, private val attendanceList: List<Attendance>): RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    class AttendanceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvAttendancePercentage: TextView = itemView.findViewById(R.id.tvAttendancePercentage)
        val tvAttendanceSubjectCode: TextView = itemView.findViewById(R.id.tvAttendanceSubjectCode)
        val tvAttendedClassesCount: TextView = itemView.findViewById(R.id.tvAttendedClassesCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance, parent, false)
        return AttendanceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val currentAttendanceItem = attendanceList[position]
        holder.tvAttendancePercentage.text = currentAttendanceItem.percentage.toString() + "%"
        holder.tvAttendanceSubjectCode.text = currentAttendanceItem.subjectCode
        holder.tvAttendedClassesCount.text = "${currentAttendanceItem.attendedClasses}/${currentAttendanceItem.totalClasses}"
    }

    override fun getItemCount(): Int {
        return attendanceList.size
    }

}