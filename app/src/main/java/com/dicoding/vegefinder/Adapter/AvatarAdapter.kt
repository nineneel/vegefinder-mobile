package com.dicoding.vegefinder.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.data.model.Avatar
import com.dicoding.vegefinder.data.model.Vegetable
import com.google.android.material.card.MaterialCardView


class AvatarAdapter(private val onAvatarClickListener: (avatarId: Int) -> Unit) : RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {

    private val avatarList = ArrayList<Avatar>()
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    @SuppressLint("NotifyDataSetChanged")
    fun setAvatarList(avatars: ArrayList<Avatar>){
        avatarList.clear()
        avatarList.addAll(avatars)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return avatarList.size
    }

    override fun onBindViewHolder(holder: AvatarAdapter.ViewHolder, position: Int) {
        holder.bind(avatarList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.iv_avatar)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(avatar: Avatar) {
            Log.d("AVATAR", "Avatar: $avatar")
            Glide.with(avatarImageView)
                .asBitmap()
                .load("https://storage.googleapis.com/vegefinder-bucket/${avatar.fileName}")
                .centerCrop()
                .placeholder(R.drawable.kangkung)
                .into(avatarImageView)

            if (selectedPosition == adapterPosition) {
                cardView.strokeWidth = 6
            } else {
                cardView.strokeWidth = 0
            }
        }

        override fun onClick(view: View) {
            // Set the selected position to the clicked position
            val previousSelectedPosition = selectedPosition
            selectedPosition = adapterPosition

            // Notify the adapter that the data has changed
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)

            onAvatarClickListener(avatarList[adapterPosition].id)
        }
    }
}
