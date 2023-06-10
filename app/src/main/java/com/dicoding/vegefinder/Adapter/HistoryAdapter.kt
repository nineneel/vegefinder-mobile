package com.dicoding.vegefinder.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.Activity.DetailExploreActivity
import com.dicoding.vegefinder.Activity.DetailHistoryActivity
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.data.model.Vegetable

class HistoryAdapter (private val context: Context) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val historyList = ArrayList<Vegetable>()

    @SuppressLint("NotifyDataSetChanged")
    fun setVegetableList(vegetables: ArrayList<Vegetable>){
        historyList.clear()
        historyList.addAll(vegetables)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_explore, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.exp_image)
        private val nameTextView: TextView = itemView.findViewById(R.id.exp_name)
        private val descTextView: TextView = itemView.findViewById(R.id.exp_desc)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Vegetable) {
            Glide.with(itemView)
                .asBitmap()
                .load("https://storage.googleapis.com/vegefinder-bucket/${item.thumbnail}")
                .centerCrop()
                .placeholder(R.drawable.vegefinder)
                .into(imageView)

            nameTextView.text = item.name
            descTextView.text = item.description
        }

        override fun onClick(view: View) {
            val intent = Intent(context, DetailExploreActivity::class.java)

            intent.putExtra("id", historyList[adapterPosition].id)
            intent.putExtra("name", historyList[adapterPosition].name)
            intent.putExtra("typesName", historyList[adapterPosition].types.map { it.name } as ArrayList<String>)
            intent.putExtra("typesGroupsName", historyList[adapterPosition].types.map { it.typeGroups.id } as ArrayList<Int>)
            intent.putExtra("description", historyList[adapterPosition].description)
            intent.putExtra("descriptionSource", historyList[adapterPosition].descriptionSource)
            intent.putExtra("thumbnail", historyList[adapterPosition].thumbnail)
            intent.putExtra("images", historyList[adapterPosition].images)
            intent.putExtra("howToPlant", historyList[adapterPosition].howToPlant)
            intent.putExtra("howToPlantSource", historyList[adapterPosition].howToPlantSource)
            intent.putExtra("plantCare", historyList[adapterPosition].plantCare)
            intent.putExtra("plantCareSource", historyList[adapterPosition].plantCareSource)
            intent.putExtra("plantDisease", historyList[adapterPosition].plantDisease)
            intent.putExtra("plantDiseaseSource", historyList[adapterPosition].plantDiseaseSource)
            intent.putExtra("isSaved", historyList[adapterPosition].isSaved)

            context.startActivity(intent)
        }
    }
}