package models

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.droidassist.R

class ResourcesAdapter(val context: Context, val resourcesList: List<Resources>):RecyclerView.Adapter<ResourcesAdapter.ResourcesViewHolder>() {
    class ResourcesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvResourcesSubjectCode: TextView = itemView.findViewById(R.id.tvResourcesSubjectCode)
        val imgBtnResourcesVideos: ImageButton = itemView.findViewById(R.id.imgBtnResourcesVideos)
        val btnResourcesLinks: Button = itemView.findViewById(R.id.btnResourcesLinks)
        val btnResourcesNotes: Button = itemView.findViewById(R.id.btnResourcesNotes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourcesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_resource,parent,false)
        return ResourcesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResourcesViewHolder, position: Int) {
        val currentResourceItem = resourcesList[position]
        holder.tvResourcesSubjectCode.text = currentResourceItem.subjectCode
        holder.imgBtnResourcesVideos.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentResourceItem.youtubePlaylistLink))
            context.startActivity(intent)
        }
        holder.btnResourcesLinks.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentResourceItem.subjectClassLink))
            context.startActivity(intent)
        }
        holder.btnResourcesNotes.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentResourceItem.subjectNotesLink))
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return resourcesList.size
    }


}