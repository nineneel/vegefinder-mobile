package com.bangkit.vegefinder.ui.saved

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.adapter.SavedAdapter
import com.bangkit.vegefinder.helper.ToastDisplay
import com.bangkit.vegefinder.viewmodel.SavedViewModel

class SavedFragment : Fragment() {
    private lateinit var recyclerViewSaved: RecyclerView
    private lateinit var savedAdapter: SavedAdapter
    private lateinit var savedViewModel: SavedViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)


        savedAdapter = SavedAdapter(requireContext())
        savedAdapter.notifyDataSetChanged()

        recyclerViewSaved = view.findViewById(R.id.rv_saved)
        recyclerViewSaved.layoutManager = LinearLayoutManager(activity)
        recyclerViewSaved.adapter = savedAdapter

        savedViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SavedViewModel::class.java]


        val noSavedTextView : TextView = view.findViewById(R.id.tv_nosaved)
        val savedProgressBar: ProgressBar = view.findViewById(R.id.pb_saved)
        showLoading(savedProgressBar, true)
        savedViewModel.setVegetable()
        savedViewModel.getVegetableResponse().observe(viewLifecycleOwner){vegetableList ->
            showLoading(savedProgressBar, false)
            if(vegetableList != null){
                if(vegetableList.size > 0){
                    savedAdapter.setVegetableList(vegetableList)
                }else{
                    noSavedTextView.visibility = View.VISIBLE
                }
            }else{
                ToastDisplay.displayToastWithFetchError(requireContext(), "saved")
            }

        }

        return view
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        savedAdapter.notifyDataSetChanged()

        savedViewModel.setVegetable()
        savedViewModel.getVegetableResponse().observe(viewLifecycleOwner) { response ->
            if(response != null){
                savedAdapter.setVegetableList(response)
            }else{
                ToastDisplay.displayToastWithFetchError(requireContext(), "saved")
            }
        }

    }

    private fun showLoading(progressBar: ProgressBar, state: Boolean){
        progressBar.visibility = if(state) View.VISIBLE else View.GONE
    }
}