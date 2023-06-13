package com.dicoding.vegefinder.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.Activity.DetailExploreActivity
import com.dicoding.vegefinder.Activity.DetailJenisActivity
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.data.model.VegetableType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class TypeAllAdapter (private val context: Context) :
    RecyclerView.Adapter<TypeAllAdapter.ViewHolder>() {
        private val itemList = ArrayList<VegetableType>()

        @SuppressLint("NotifyDataSetChanged")
        fun setVegetableTypeList(vegetables: ArrayList<VegetableType>){
            itemList.clear()
            itemList.addAll(vegetables)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_jenis, parent, false)
            return ViewHolder(view)
        }

        @RequiresApi(Build.VERSION_CODES.O)
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
//            private val typeRecyclerView: RecyclerView = itemView.findViewById(R.id.rv_types)

            init {
                itemView.setOnClickListener(this)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            fun bind(item: VegetableType) {
                Glide.with(itemView)
                    .asBitmap()
                    .load("https://storage.googleapis.com/vegefinder-bucket/${item.thumbnail}")
                    .centerCrop()
                    .into(imageView)

                typeTextView.text = item.name
                typeVegeTextView.text = item.typeGroups.name
            }

            override fun onClick(view: View) {
                val intent = Intent(context, DetailJenisActivity::class.java)


                intent.putExtra("image", itemList[adapterPosition].thumbnail)
                intent.putExtra("type", itemList[adapterPosition].name)
                intent.putExtra("typeVege", itemList[adapterPosition].typeGroups.name)
                intent.putExtra("desc", itemList[adapterPosition].description)

                context.startActivity(intent)
            }
        }
}