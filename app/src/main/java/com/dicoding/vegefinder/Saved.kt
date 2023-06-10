package com.dicoding.vegefinder

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.ExploreAdapter
import com.dicoding.vegefinder.Adapter.SavedAdapter
import com.dicoding.vegefinder.Adapter.SavedData
import com.dicoding.vegefinder.databinding.FragmentSavedBinding
import com.dicoding.vegefinder.viewmodel.SavedViewModel
import com.dicoding.vegefinder.viewmodel.VegetableViewModel

class Saved : Fragment() {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
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


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedBinding.bind(view)


        savedAdapter.notifyDataSetChanged()

        binding.apply {
            rvSaved.setHasFixedSize(true)
            rvSaved.layoutManager = LinearLayoutManager(activity)
            rvSaved.adapter = savedAdapter
        }
        savedViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SavedViewModel::class.java]
        savedViewModel.setVegetable()
        savedViewModel.getVegetableResponse().observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.isEmpty()) {
                    binding.tvNosaved.visibility = View.VISIBLE
                    binding.rvSaved.visibility = View.GONE
                } else {
                    binding.tvNosaved.visibility = View.GONE
                    binding.rvSaved.visibility = View.VISIBLE
                    savedAdapter.setVegetableList(it)
                }
            }
        }
    }

}