package com.dicoding.vegefinder.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Activity.DetailExploreActivity
import com.dicoding.vegefinder.R
import com.google.android.material.card.MaterialCardView


class TypeTagAdapter(private val typeList: ArrayList<String>, private val typeGroupList: ArrayList<Int>, private val isDetail: Boolean) : RecyclerView.Adapter<TypeTagAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeTagAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(if(isDetail) R.layout.item_type else R.layout.item_saved_type , parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    override fun onBindViewHolder(holder: TypeTagAdapter.ViewHolder, position: Int) {
        holder.bind(typeList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)

        fun bind(name: String) {
            if(!isDetail) cardView.strokeColor = getColorForBaseColor(typeGroupList[adapterPosition])
            cardView.setCardBackgroundColor(getColorForTintColor(typeGroupList[adapterPosition]))
            nameTextView.setTextColor(getColorForBaseColor(typeGroupList[adapterPosition]))
            nameTextView.text = name
        }

        private fun getColorForBaseColor(typeGroup: Int): Int {
            return when (typeGroup) {
                // Define your color mappings for each typeGroup
                1 -> Color.parseColor("#FFA129") // Example color for TypeGroup1
                2 -> Color.parseColor("#9AAEFD") // Example color for TypeGroup2
                3 -> Color.parseColor("#A559D9") // Example color for TypeGroup3
                else -> Color.parseColor("#A559D9") // Default color
            }
        }

        private fun getColorForTintColor(typeGroup: Int): Int {
            return when (typeGroup) {
                // Define your color mappings for each typeGroup
                1 -> Color.parseColor("#FCF1E3") // Example color for TypeGroup1
                2 -> Color.parseColor("#E6EAFA") // Example color for TypeGroup2
                3 -> Color.parseColor("#F8E8F8") // Example color for TypeGroup3
                else -> Color.parseColor("#F8E8F8") // Default color
            }
        }

    }
}

