package com.bangkit.vegefinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.databinding.ItemSlideBannerBinding
import com.bangkit.vegefinder.databinding.ItemSlideBinding

class ImageSlideBannerAdapter(private val items: List<String>) : RecyclerView.Adapter<ImageSlideBannerAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: ItemSlideBannerBinding) : RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView
        fun bind(data: String){
            with(binding){
                Glide.with(itemView)
                    .load(data)
                    .placeholder(R.drawable.vegefinder)
                    .into(ivThumbnail)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemSlideBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }
}