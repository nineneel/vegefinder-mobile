package com.dicoding.vegefinder.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.Activity.DetailExploreActivity
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.data.model.Vegetable


class ExploreAdapter (private val context: Context) :  RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {

    private val vegetableList = ArrayList<Vegetable>()

    fun setVegetableList(vegetables: ArrayList<Vegetable>){
        vegetableList.clear()
        vegetableList.addAll(vegetables)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_explore, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(vegetableList[position])
    }

    override fun getItemCount(): Int {
        return vegetableList.size
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
                .placeholder(R.drawable.kangkung)
                .into(imageView)

            nameTextView.text = item.name
            descTextView.text = item.description
        }

        override fun onClick(view: View) {
            val intent = Intent(context, DetailExploreActivity::class.java)
            intent.putExtra("name", vegetableList[adapterPosition].name)
            intent.putExtra("typesName", vegetableList[adapterPosition].types.map { it.name } as ArrayList<String>)
            intent.putExtra("typesGroupsName", vegetableList[adapterPosition].types.map { it.typeGroups.id } as ArrayList<Int>)
            intent.putExtra("description", vegetableList[adapterPosition].description)
            intent.putExtra("descriptionSource", vegetableList[adapterPosition].descriptionSource)
            intent.putExtra("thumbnail", vegetableList[adapterPosition].thumbnail)
            intent.putExtra("images", vegetableList[adapterPosition].images)
            intent.putExtra("howToPlant", vegetableList[adapterPosition].howToPlant)
            intent.putExtra("howToPlantSource", vegetableList[adapterPosition].howToPlantSource)
            intent.putExtra("plantCare", vegetableList[adapterPosition].plantCare)
            intent.putExtra("plantCareSource", vegetableList[adapterPosition].plantCareSource)
            intent.putExtra("plantDisease", vegetableList[adapterPosition].plantDisease)
            intent.putExtra("plantDiseaseSource", vegetableList[adapterPosition].plantDiseaseSource)

            context.startActivity(intent)
        }
    }
}