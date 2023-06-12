package com.dicoding.vegefinder.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.data.model.Vegetable
import com.dicoding.vegefinder.databinding.ItemSlideBinding

class ImageSlideAdapter(private val items: List<String>) : RecyclerView.Adapter<ImageSlideAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: ItemSlideBinding) : RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView
        fun bind(data: String){
            Log.d("SLIDE", "TEST $data")
            with(binding){
                Glide.with(itemView)
                    .load("https://storage.googleapis.com/vegefinder-bucket/$data")
                    .placeholder(R.drawable.vegefinder)
                    .into(ivThumbnail)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemSlideBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }
}