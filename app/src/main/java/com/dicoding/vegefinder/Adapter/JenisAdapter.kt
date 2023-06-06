package com.dicoding.vegefinder.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Activity.DetailJenisActivity
import com.dicoding.vegefinder.R

class JenisAdapter (private val itemList: List<Jenis>, private val context: Context) :
    RecyclerView.Adapter<JenisAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jenis, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.item_image)
        private val typeTextView: TextView = itemView.findViewById(R.id.item_type)
        private val typeVegeTextView: TextView = itemView.findViewById(R.id.item_typeVege)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Jenis) {
            imageView.setImageResource(item.image)
            typeTextView.text = item.type
            typeVegeTextView.text = item.typeVege
        }

        override fun onClick(view: View) {
            val intent = Intent(context, DetailJenisActivity::class.java)
            intent.putExtra("image", itemList[adapterPosition].image)
            intent.putExtra("type", itemList[adapterPosition].type)
            intent.putExtra("typeVege", itemList[adapterPosition].typeVege)
            intent.putExtra("desc", itemList[adapterPosition].desc)

            context.startActivity(intent)
        }
    }
}