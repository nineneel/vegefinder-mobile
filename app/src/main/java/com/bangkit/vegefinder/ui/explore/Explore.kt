package com.bangkit.vegefinder.ui.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.adapter.ExploreAdapter
import com.bangkit.vegefinder.data.preferences.SessionManager
import com.bangkit.vegefinder.helper.ToastDisplay
import com.bangkit.vegefinder.viewmodel.VegetableViewModel

class Explore : Fragment() {

    private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var vegetableViewModel: VegetableViewModel
    private lateinit var sessionManager: SessionManager

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val exploreView = inflater.inflate(R.layout.fragment_explore, container, false)


        sessionManager = SessionManager(requireContext())
        exploreAdapter = ExploreAdapter(requireContext())
        exploreAdapter.notifyDataSetChanged()

        val recyclerViewExplore = exploreView.findViewById<RecyclerView>(R.id.rv_explore)
        recyclerViewExplore.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerViewExplore.adapter = exploreAdapter

        vegetableViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[VegetableViewModel::class.java]

        val explploreProgressBar: ProgressBar = exploreView.findViewById(R.id.pb_explore)
        showLoading(explploreProgressBar, true)
        vegetableViewModel.setVegetable()
        vegetableViewModel.getVegetableResponse().observe(viewLifecycleOwner) { response ->
            showLoading(explploreProgressBar, false)
            if (response != null) {
                exploreAdapter.setVegetableList(response)
            } else {
                ToastDisplay.displayToastWithError(requireContext())
            }
        }

        return exploreView
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        vegetableViewModel.setVegetable()
        vegetableViewModel.getVegetableResponse().observe(viewLifecycleOwner) { response ->
            if (response != null) {
                exploreAdapter.setVegetableList(response)
            } else {
                ToastDisplay.displayToastWithError(requireContext())
            }
        }

    }

    private fun showLoading(progressBar: ProgressBar, state: Boolean) {
        progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}