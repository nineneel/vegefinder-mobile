package com.bangkit.vegefinder.ui.store.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.bangkit.vegefinder.adapter.adapter
//import com.bangkit.vegefinder.adapter.store.BenihAdapter
import com.bangkit.vegefinder.adapter.store.ProductItemAdapter
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.adapter.store.DiscountItemAdapter
//import com.bangkit.vegefinder.data.model.Benih
import com.bangkit.vegefinder.data.model.Product

//import com.bangkit.vegefinder.data.model.Pupuk


class DashboardFragment : Fragment() {
    private val benihList = listOf(
        Product(
            image = "https://storage.googleapis.com/vegefinder-bucket/store/benih/Group%20153-1.png",
            name = "Benih Bayam Hijau",
            price = "Rp 5.000",
            description = "Benih bayam hijau yang unggul dengan pertumbuhan cepat dan daun yang lezat. Cocok untuk ditanam di kebun atau dalam pot."
        ),
        Product(
            image = "https://storage.googleapis.com/vegefinder-bucket/store/benih/Group%20153.png",
            name = "Benih Tomat Merah",
            price = "Rp 8.000",
            description = "Benih tomat merah yang berkualitas tinggi dengan rasa manis dan buah yang besar. Cocok untuk ditanam di kebun atau dalam wadah."
        ),
        Product(
            image = "https://storage.googleapis.com/vegefinder-bucket/store/benih/image%208-1.png",
            name = "Benih Cabai Merah",
            price = "Rp 7.000",
            description = "Benih cabai merah unggul yang menghasilkan cabai pedas dengan tingkat kejutuan tinggi. Cocok untuk ditanam di berbagai kondisi iklim."
        ),
        Product(
            image = "https://storage.googleapis.com/vegefinder-bucket/store/benih/image%208-1.png",
            name = "Benih Wortel Orange",
            price = "Rp 6.000",
            description = "Benih wortel oranye yang menghasilkan wortel manis dan renyah. Cocok untuk ditanam di kebun atau dalam wadah."
        ),

    )

    private val pupukList = listOf(
        Product(
            image = "https://storage.googleapis.com/vegefinder-bucket/store/pupuk-(1).png",
            name = "Pupuk Organik",
            price = "Rp 50.000",
            description = "Pupuk organik yang diformulasikan khusus untuk meningkatkan pertumbuhan tanaman secara alami dan menjaga keseimbangan nutrisi tanah. Cocok untuk berbagai jenis tanaman hortikultura dan pertanian."
        ),
        Product(
            image = "https://storage.googleapis.com/vegefinder-bucket/store/pupuk-(2).png",
            name = "Pupuk Kandang",
            price = "Rp 20.000",
            description = "Pupuk kandang organik yang terbuat dari bahan-bahan alami seperti kotoran hewan. Mengandung nutrisi penting untuk pertumbuhan tanaman dan meningkatkan kesuburan tanah."
        ),
        Product(
            image = "https://storage.googleapis.com/vegefinder-bucket/store/pupuk-(3).png",
            name = "Pupuk NPK",
            price = "Rp 15.000",
            description = "Pupuk NPK yang mengandung nitrogen, fosfor, dan kalium dalam proporsi yang tepat. Memberikan nutrisi penting untuk pertumbuhan tanaman, pembentukan akar, dan pengembangan buah."
        ),
        Product(
            image = "https://storage.googleapis.com/vegefinder-bucket/store/pupuk-(4).png",
            name = "Pupuk Hayati",
            price = "Rp 25.000",
            description = "Pupuk hayati yang mengandung mikroorganisme bermanfaat seperti bakteri dan jamur. Membantu meningkatkan kualitas tanah, mengendalikan penyakit tanaman, dan meningkatkan daya tahan tanaman terhadap stres lingkungan."
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store_dashboard, container, false)

        val recyclerViewBenih = view.findViewById<RecyclerView>(R.id.rv_benih)
        recyclerViewBenih.layoutManager =
            LinearLayoutManager(requireContext(),  LinearLayoutManager.HORIZONTAL, false)
        recyclerViewBenih.adapter = ProductItemAdapter(benihList, requireContext())

        val recyclerViewPupuk = view.findViewById<RecyclerView>(R.id.rv_pupuk)
        recyclerViewPupuk.layoutManager = LinearLayoutManager(requireContext(),  LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPupuk.adapter = ProductItemAdapter(pupukList, requireContext())

        val recyclerViewDiscount = view.findViewById<RecyclerView>(R.id.rv_discount)
        recyclerViewDiscount.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewDiscount.adapter = DiscountItemAdapter(benihList, requireContext())

//        viewTextView = view.findViewById(R.id.view_all)
//        viewTextView.setOnClickListener {
//            val intent = Intent(requireContext(), DetailActivity::class.java)
//            startActivity(intent)
//        }

        return view
    }
}