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
import com.dicoding.vegefinder.Activity.DetailJenisActivity
import com.dicoding.vegefinder.R

class ExploreAdapter (private val exploreList: List<ExploreData>, private val context: Context) :
    RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_explore, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exploreList[position])
    }

    override fun getItemCount(): Int {
        return exploreList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.exp_image)
        private val nameTextView: TextView = itemView.findViewById(R.id.exp_name)
        private val descTextView: TextView = itemView.findViewById(R.id.exp_desc)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: ExploreData) {
            imageView.setImageResource(item.image)
            nameTextView.text = item.name
            descTextView.text = item.desc
        }

        override fun onClick(view: View) {
            val intent = Intent(context, DetailExploreActivity::class.java)
            intent.putExtra("image", exploreList[adapterPosition].image)
            intent.putExtra("name", exploreList[adapterPosition].name)
            intent.putExtra("desc", exploreList[adapterPosition].desc)

            context.startActivity(intent)
        }
    }
}