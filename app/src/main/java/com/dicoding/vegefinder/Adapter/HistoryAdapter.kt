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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.Activity.DetailExploreActivity
import com.dicoding.vegefinder.Activity.DetailHistoryActivity
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.data.model.Vegetable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.Instant
import java.time.ZoneOffset


class HistoryAdapter (private val context: Context) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val historyList = ArrayList<Vegetable>()

    @SuppressLint("NotifyDataSetChanged")
    fun setVegetableList(vegetables: ArrayList<Vegetable>){
        historyList.clear()
        historyList.addAll(vegetables)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        private val descTextView: TextView = itemView.findViewById(R.id.tv_date)

        init {
            itemView.setOnClickListener(this)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Vegetable) {
            Glide.with(itemView)
                .asBitmap()
                .load("https://storage.googleapis.com/vegefinder-bucket/${item.thumbnail}")
                .centerCrop()
                .placeholder(R.drawable.vegefinder)
                .into(imageView)

            val instant = Instant.parse(item.createdAt)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
            val formatter = DateTimeFormatter.ofPattern("EEEE, dd/MMMM/yyyy - HH:mm:ss")

            nameTextView.text = item.name
            descTextView.text = dateTime.format(formatter)
        }

        override fun onClick(view: View) {
            val intent = Intent(context, DetailExploreActivity::class.java)

            intent.putExtra("id", historyList[adapterPosition].id)
            intent.putExtra("name", historyList[adapterPosition].name)
            intent.putExtra("typesName", historyList[adapterPosition].types.map { it.name } as ArrayList<String>)
            intent.putExtra("typesGroupsName", historyList[adapterPosition].types.map { it.typeGroups.id } as ArrayList<Int>)
            intent.putExtra("description", historyList[adapterPosition].description)
            intent.putExtra("descriptionSource", historyList[adapterPosition].descriptionSource)
            intent.putExtra("thumbnail", historyList[adapterPosition].thumbnail)
            intent.putExtra("images", historyList[adapterPosition].images)
            intent.putExtra("howToPlant", historyList[adapterPosition].howToPlant)
            intent.putExtra("howToPlantSource", historyList[adapterPosition].howToPlantSource)
            intent.putExtra("plantCare", historyList[adapterPosition].plantCare)
            intent.putExtra("plantCareSource", historyList[adapterPosition].plantCareSource)
            intent.putExtra("plantDisease", historyList[adapterPosition].plantDisease)
            intent.putExtra("plantDiseaseSource", historyList[adapterPosition].plantDiseaseSource)
            intent.putExtra("isSaved", historyList[adapterPosition].isSaved)

            context.startActivity(intent)
        }
    }
}