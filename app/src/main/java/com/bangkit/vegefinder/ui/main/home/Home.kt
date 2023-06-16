package com.bangkit.vegefinder.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.adapter.HistoryAdapter
import com.bangkit.vegefinder.adapter.ImageSlideBannerAdapter
import com.bangkit.vegefinder.adapter.VegetableTypeAdapter
import com.bangkit.vegefinder.data.preferences.SessionManager
import com.bangkit.vegefinder.helper.ToastDisplay
import com.bangkit.vegefinder.ui.history.HistoryActivity
import com.bangkit.vegefinder.ui.store.StoreActivity
import com.bangkit.vegefinder.ui.type.TypesActivity
import com.bangkit.vegefinder.viewmodel.HomeHistoryViewModel
import com.bangkit.vegefinder.viewmodel.HomeTypeViewModel

class Home : Fragment() {

    private lateinit var historyViewAllTextView: TextView
    private lateinit var typeViewAllTextView: TextView
    private lateinit var homeTypeViewModel: HomeTypeViewModel
    private lateinit var homeHistoryViewModel: HomeHistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var vegetableTypeAdapter: VegetableTypeAdapter
    private lateinit var sessionManager: SessionManager

    private lateinit var noHistoryTextView: TextView
    private lateinit var historyProgressBar: ProgressBar
    private lateinit var vegeStoreImageButton: ImageButton

    private lateinit var imageSlideAdapter: ImageSlideBannerAdapter
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

        imageViewPager = view.findViewById(R.id.view_pager)
        imageDotsLinearLayout = view.findViewById(R.id.dots_indicator)
        vegeStoreImageButton = view.findViewById(R.id.vegestore_feature_button)

        vegeStoreImageButton.setOnClickListener {
            startActivity(Intent(requireContext(), StoreActivity::class.java))
        }

        imageList.add("https://storage.googleapis.com/vegefinder-bucket/banner/VegeLife.png")
        imageList.add("https://storage.googleapis.com/vegefinder-bucket/banner/VegeLife-1.png")
        imageList.add("https://storage.googleapis.com/vegefinder-bucket/banner/VegeLife-2.png")

        imageSlideAdapter = ImageSlideBannerAdapter(imageList)
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
        )[HomeHistoryViewModel::class.java]


        val vegetableTypeProgressBar: ProgressBar = view.findViewById(R.id.pb_jenis)
        showLoading(vegetableTypeProgressBar, true)
        homeTypeViewModel.setVegetableType()
        homeTypeViewModel.getVegetableTypeResponse().observe(viewLifecycleOwner) { response ->
            showLoading(vegetableTypeProgressBar, false)
            if (response != null) {
                vegetableTypeAdapter.setList(response)
            } else {
                ToastDisplay.displayToastWithError(requireContext())
            }
        }

        noHistoryTextView = view.findViewById(R.id.tv_nohistory)
        historyProgressBar = view.findViewById(R.id.pb_history)
        showLoading(historyProgressBar, true)
        homeHistoryViewModel.setHistory()
        homeHistoryViewModel.getHistoryResponse().observe(viewLifecycleOwner) { response ->
            showLoading(historyProgressBar, false)
            if (response != null) {
                if (response.size > 0) {
                    historyAdapter.setVegetableList(response)
                } else {
                    noHistoryTextView.visibility = View.VISIBLE
                }
            } else {
                ToastDisplay.displayToastWithError(requireContext())
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
            showLoading(historyProgressBar, false)
            if (response != null) {
                if (response.size > 0) {
                    historyAdapter.setVegetableList(response)
                    noHistoryTextView.visibility = View.GONE
                }
            } else {
                ToastDisplay.displayToastWithError(requireContext())
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