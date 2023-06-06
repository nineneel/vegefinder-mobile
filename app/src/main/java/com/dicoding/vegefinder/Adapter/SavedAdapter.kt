package com.dicoding.vegefinder.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Activity.DetailExploreActivity
import com.dicoding.vegefinder.R

class SavedAdapter (private val savedList: List<SavedData>, private val context: Context) :
    RecyclerView.Adapter<SavedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_saved, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(savedList[position])
    }

    override fun getItemCount(): Int {
        return savedList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.svd_image)
        private val nameTextView: TextView = itemView.findViewById(R.id.svd_name)
        private val typeTextView: TextView = itemView.findViewById(R.id.svd_type)
        private val descTextView: TextView = itemView.findViewById(R.id.svd_desc)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: SavedData) {
            imageView.setImageResource(item.image)
            nameTextView.text = item.name
            typeTextView.text = item.type
            descTextView.text = item.desc
        }

        override fun onClick(view: View) {
            val intent = Intent(context, DetailExploreActivity::class.java)
            intent.putExtra("image", savedList[adapterPosition].image)
            intent.putExtra("name", savedList[adapterPosition].name)
            intent.putExtra("type", savedList[adapterPosition].type)
            intent.putExtra("desc", savedList[adapterPosition].desc)

            context.startActivity(intent)
        }
    }
}