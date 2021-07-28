package models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.droidassist.R

class InternalsAdapter(val context: Context, val internalScoreList: List<InternalAssessment>): RecyclerView.Adapter<InternalsAdapter.InternalsViewHolder>() {

    class InternalsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val iaSubjectMarks: TextView = itemView.findViewById(R.id.iaSubjectMarks)
        val iaSubjectCode: TextView = itemView.findViewById(R.id.iaSubjectCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InternalsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_ia, parent, false)
        return InternalsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InternalsViewHolder, position: Int) {
        val currentInternalItem = internalScoreList[position]
        holder.iaSubjectMarks.text = currentInternalItem.score
        holder.iaSubjectCode.text = currentInternalItem.subjectCode
    }

    override fun getItemCount(): Int {
        return internalScoreList.size
    }

}