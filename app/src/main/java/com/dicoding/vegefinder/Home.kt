package com.dicoding.vegefinder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.vegefinder.Activity.HistoryActivity
import com.dicoding.vegefinder.Activity.TypesActivity
import com.dicoding.vegefinder.Adapter.HistoryAdapter
import com.dicoding.vegefinder.Adapter.ImageSlideAdapter
import com.dicoding.vegefinder.Adapter.VegetableTypeAdapter
import com.dicoding.vegefinder.data.model.Vegetable
import com.dicoding.vegefinder.data.model.VegetableType
import com.dicoding.vegefinder.viewmodel.HistoryViewModel
import com.dicoding.vegefinder.viewmodel.HomeHistoryViewModel
import com.dicoding.vegefinder.viewmodel.HomeTypeViewModel
import com.dicoding.vegefinder.data.Result
import kotlinx.coroutines.delay
import org.w3c.dom.Text

class Home : Fragment() {

    private lateinit var historyViewAllTextView: TextView
    private lateinit var typeViewAllTextView: TextView
    private lateinit var homeTypeViewModel: HomeTypeViewModel
    private lateinit var homeHistoryViewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var vegetableTypeAdapter: VegetableTypeAdapter
    private lateinit var sessionManager: SessionManager

    private lateinit var noHistoryTextView: TextView
    private lateinit var historyProgressBar: ProgressBar


    private lateinit var imageSlideAdapter: ImageSlideAdapter
    private lateinit var imageDotsLinearLayout: LinearLayout
    private lateinit var imageViewPager: ViewPager2
    private val imageList = ArrayList<String>()
    private lateinit var dots: ArrayList<TextView>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        sessionManager = SessionManager(requireContext())
//
        imageViewPager = view.findViewById(R.id.view_pager)
        imageDotsLinearLayout = view.findViewById(R.id.dots_indicator)

        imageList.add("https://img.freepik.com/premium-psd/vegetable-grocery-delivery-promotion-web-banner-facebook-cover-instagram-template_502896-109.jpg")
        imageList.add("https://img.freepik.com/premium-psd/fresh-vegetable-banner-template_88281-5245.jpg?w=2000")
        imageList.add("https://img.lovepik.com/free-template/20211027/lovepik-healthy-organic-vegetable-banner-image_2527060_list.jpg!/fw/431/clip/0x300a0a0")

        imageSlideAdapter = ImageSlideAdapter(imageList)
        imageViewPager.adapter = imageSlideAdapter

        vegetableTypeAdapter = VegetableTypeAdapter(requireContext())
        vegetableTypeAdapter.notifyDataSetChanged()

        historyAdapter = HistoryAdapter(requireContext())
        historyAdapter.notifyDataSetChanged()

        val recyclerViewHistories = view.findViewById<RecyclerView>(R.id.rv_history)
        recyclerViewHistories.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewHistories.adapter = historyAdapter


        val recyclerViewType = view.findViewById<RecyclerView>(R.id.rv_jenis)
        recyclerViewType.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewType.adapter = vegetableTypeAdapter

        homeTypeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeTypeViewModel::class.java]
        homeHistoryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HistoryViewModel::class.java]


        val vegetableTypeProgressBar: ProgressBar = view.findViewById(R.id.pb_jenis)
        showLoading(vegetableTypeProgressBar, true)
        homeTypeViewModel.setVegetableType()
        homeTypeViewModel.getVegetableTypeResponse().observe(viewLifecycleOwner) { response ->
            showLoading(vegetableTypeProgressBar, false)
            vegetableTypeAdapter.setList(
                response.shuffled().slice(1..4) as ArrayList<VegetableType>
            )
        }

        noHistoryTextView = view.findViewById(R.id.tv_nohistory)
        historyProgressBar = view.findViewById(R.id.pb_history)
        showLoading(historyProgressBar, true)
        homeHistoryViewModel.setHistory()
        homeHistoryViewModel.getHistoryResponse().observe(viewLifecycleOwner) { response ->
            showLoading(historyProgressBar, false)
            if (response.size > 0) {
                historyAdapter.setVegetableList(response.slice(0..if (response.size < 5) response.size - 1 else 4) as ArrayList<Vegetable>)
            } else {
                noHistoryTextView.visibility = View.VISIBLE
            }
        }

        historyViewAllTextView = view.findViewById(R.id.view_all)
        historyViewAllTextView.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        typeViewAllTextView = view.findViewById(R.id.view_all_type)
        typeViewAllTextView.setOnClickListener {
            val intent = Intent(requireContext(), TypesActivity::class.java)
            startActivity(intent)
        }

        dots = ArrayList()
        setIndicator()

        imageViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                super.onPageSelected(position)
            }
        })

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        historyAdapter.notifyDataSetChanged()
        homeHistoryViewModel.setHistory()
        homeHistoryViewModel.getHistoryResponse().observe(viewLifecycleOwner) { response ->
            if (response.size > 0) {
                historyAdapter.setVegetableList(response.slice(0..if (response.size < 5) response.size - 1 else 4) as ArrayList<Vegetable>)
            } else {
                noHistoryTextView.visibility = View.VISIBLE
            }
        }

    }

    private fun setIndicator() {
        for (i in 0 until imageList.size) {
            dots.add(TextView(requireContext()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dots[i].text = Html.fromHtml("&#9679")
            }
            dots[i].textSize = 18f
            imageDotsLinearLayout.addView(dots[i])
        }
    }


    private fun selectedDot(position: Int) {
        for (i in 0 until imageList.size) {
            if (i == position)
                dots[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            else
                dots[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))

        }
    }

    private fun showLoading(progressBar: ProgressBar, state: Boolean) {
        progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}