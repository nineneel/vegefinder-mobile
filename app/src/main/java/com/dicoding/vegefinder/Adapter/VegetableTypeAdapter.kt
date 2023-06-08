package com.dicoding.vegefinder.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.Activity.DetailJenisActivity
import com.dicoding.vegefinder.R

class JenisAdapter(private val context: Context) : RecyclerView.Adapter<JenisAdapter.ViewHolder>() {

    private val itemList = ArrayList<Jenis>()

    fun setList(types: ArrayList<Jenis>){
        itemList.clear()
        itemList.addAll(types)
        notifyDataSetChanged()
    }

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
//            imageView.setImageResource(item.thumbnail)
            Glide.with(itemView)
                .asBitmap()
                .load("https://storage.googleapis.com/vegefinder-bucket/${item.thumbnail}")
                .centerCrop()
                .into(imageView)

            typeTextView.text = item.name
            typeVegeTextView.text = item.typeGroups.name
        }

        override fun onClick(view: View) {
            Log.v("adapter", "test masuk cuy")

            val intent = Intent(context, DetailJenisActivity::class.java)
            intent.putExtra("image", itemList[adapterPosition].thumbnail)
            intent.putExtra("type", itemList[adapterPosition].name)
            intent.putExtra("typeVege", itemList[adapterPosition].typeGroups.name)
            intent.putExtra("desc", itemList[adapterPosition].description)
//
            context.startActivity(intent)
        }
    }
}