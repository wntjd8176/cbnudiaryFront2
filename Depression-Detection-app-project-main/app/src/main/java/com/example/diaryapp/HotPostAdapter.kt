package com.example.diaryapp

import com.example.diaryapp.HotPostItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log

class PostItemsAdapter(
    private val hotPostItems: List<HotPostItem>,
    private val context: Context,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PostItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.hot_post_list, parent, false)
        return ViewHolder(view)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotPostItem = hotPostItems[position]
        holder.postTitle.text = hotPostItem.ptitle
        holder.username.text = hotPostItem.userID
        holder.writeDate.text = hotPostItem.postType

        Log.d("PostAdapter", "Binding position $position: Title: ${hotPostItem.ptitle}, User: ${hotPostItem.userID}, Date: ${hotPostItem.postType}")



        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return hotPostItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postTitle: TextView = itemView.findViewById(R.id.hot_title)
        var username: TextView = itemView.findViewById(R.id.hot_userId)
        var writeDate: TextView = itemView.findViewById(R.id.hot_date)
    }
}
