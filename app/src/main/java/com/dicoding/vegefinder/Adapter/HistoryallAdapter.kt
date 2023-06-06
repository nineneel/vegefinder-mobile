package com.dicoding.vegefinder.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Activity.DetailHistoryActivity
import com.dicoding.vegefinder.R

    class HistoryallAdapter (private val historyallList: List<History>, private val context: Context) :
    RecyclerView.Adapter<HistoryallAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_historyall, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historyallList[position])
    }

    override fun getItemCount(): Int {
        return historyallList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.hty_image)
        private val typeTextView: TextView = itemView.findViewById(R.id.hty_type)
        private val nameTextView: TextView = itemView.findViewById(R.id.hty_name)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: History) {
            imageView.setImageResource(item.image)
            typeTextView.text = item.type
            nameTextView.text = item.name
        }

        override fun onClick(view: View) {
            val intent = Intent(context, DetailHistoryActivity::class.java)
            intent.putExtra("image", historyallList[adapterPosition].image)
            intent.putExtra("type", historyallList[adapterPosition].type)
            intent.putExtra("name", historyallList[adapterPosition].name)

            context.startActivity(intent)
        }
    }
}