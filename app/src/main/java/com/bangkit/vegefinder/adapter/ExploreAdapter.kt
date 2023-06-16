package com.bangkit.vegefinder.adapter

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
import com.bangkit.vegefinder.ui.vegetable.VegetableActivity
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.data.model.Vegetable


class ExploreAdapter (private val context: Context) :  RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {

    private val vegetableList = ArrayList<Vegetable>()

    @SuppressLint("NotifyDataSetChanged")
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
                .placeholder(R.drawable.vegefinder)
                .into(imageView)

            nameTextView.text = item.name
            descTextView.text = item.description
        }

        override fun onClick(view: View) {
            val intent = Intent(context, VegetableActivity::class.java)
            
            intent.putExtra("id", vegetableList[adapterPosition].id)
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
            intent.putExtra("isSaved", vegetableList[adapterPosition].isSaved)


            context.startActivity(intent)
        }
    }
}