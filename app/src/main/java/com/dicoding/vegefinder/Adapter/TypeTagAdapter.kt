package com.dicoding.vegefinder.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.R


class TypeTagAdapter(private val typeList: ArrayList<String>, private val context: Context) : RecyclerView.Adapter<TypeTagAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeTagAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_type, parent, false)
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
        private val cardView: CardView = itemView.findViewById(R.id.card_view)

        fun bind(name: String) {
//            cardView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            nameTextView.setTextColor(Color.parseColor("#FFA129"))
            nameTextView.text = name
        }
    }
}

