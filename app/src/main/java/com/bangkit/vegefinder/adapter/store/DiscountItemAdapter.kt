package com.bangkit.vegefinder.adapter.store

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.data.model.Product
import com.bangkit.vegefinder.ui.store.activity.DetailProductActivity
import com.bumptech.glide.Glide

class DiscountItemAdapter(private val productList: List<Product>, private val context: Context) :
    RecyclerView.Adapter<DiscountItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_discount, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        private val descTextView: TextView = itemView.findViewById(R.id.tv_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Product) {
            Glide.with(itemView)
                .asBitmap()
                .load(item.image)
                .centerCrop()
                .placeholder(R.drawable.vegefinder)
                .into(imageView)

            nameTextView.text = item.name
            descTextView.text = item.price
        }

        override fun onClick(view: View) {
            val intent = Intent(context, DetailProductActivity::class.java)

            intent.putExtra("image", "https://unsplash.com")
            intent.putExtra("price", productList[adapterPosition].price)
            intent.putExtra("name", productList[adapterPosition].name)
            intent.putExtra("description", productList[adapterPosition].description)

            context.startActivity(intent)
        }
    }
}