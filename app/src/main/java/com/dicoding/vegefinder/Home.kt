package com.dicoding.vegefinder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Activity.HistoryActivity
import com.dicoding.vegefinder.Adapter.History
import com.dicoding.vegefinder.Adapter.HistoryAdapter
import com.dicoding.vegefinder.Adapter.Jenis
import com.dicoding.vegefinder.Adapter.JenisAdapter

class Home : Fragment() {

    private val historyList = listOf(
        History(
            R.drawable.kangkung,
            "Bayam",
            "Daun",
            "Nnanananan"
        ),
        History(
            R.drawable.bayam,
            "Bayam Hijau",
            "Daun",
            "Nnanananan"
        )

    )
        private val itemList = listOf(
        Jenis(
            R.drawable.leaf,
            "Yang dikonsumsi",
            "Daun",
            "Sayuran yang dimanfaatkan daunnya merupakan jenis sayuran yang dapat dikonsumsi pada bagian daunnya. Sayuran daun memiliki kualitas bagus jika bagian daunnya utuh, tidak berlubang, dan tidak busuk, serta batang dan daun berwarna segar.",
        ),
        Jenis(
            R.drawable.biji,
            "Yang dikonsumsi",
            "Biji",
            "Sayuran yang dimanfaatkan daunnya adalah bagian batang dati timbuhan yang dikonsumsi berupa buku dan ruasnya. Biasanya, sayuran jenis ini berumur muda, berwarna segar, dan bersih.",
        ),
        Jenis(
            R.drawable.flower,
            "Yang dikonsumsi",
            "Bunga",
            "Sayuran yang satu ini berasal dari organ generatif. Sayuran bunga dikatakan memiliki kualitas yang baik jika tersusun secara kompak, berukuran besar, berwarna segar, dan utuh tidak digigit hama.",
        ),
        Jenis(
            R.drawable.umbi,
            "Yang dikonsumsi",
            "Umbi",
            "Bagian paling bawah dalam tanaman sayuran yang dapat dimanfaatkan adalah akar dan umbinya yang biasanya terdapat di dalam tanah, tidak beruas dan berbuku. Sayuran jenis ini biasanya ditanam untuk dikonsumsi pada bagian akar atau umbinya.",
        ),
        Jenis(
            R.drawable.batang,
            "Yang dikonsumsi",
            "Batang",
            "Sayuran yang dimanfaatkan daunnya adalah bagian batang dati timbuhan yang dikonsumsi berupa buku dan ruasnya. Biasanya, sayuran jenis ini berumur muda, berwarna segar, dan bersih.",
        ),
        Jenis(
            R.drawable.buah,
            "Yang dikonsumsi",
            "Buah",
            "Sayuran ini dihasilkan dari penyerbukan dan pertumbuhan yang diawali dari bunga hingga menjadi buah. Sayuran yang satu ini memiliki kualitas yang baik dengan umur yang tidak terlalu muda dan tidak terlalu tua, berukuran besar, dan berwarna segar.",
        ),
        Jenis(
            R.drawable.pot,
            "Media Tanam",
            "Pot/wadah",
            "Akuaponik atau mina tani adalah sistem pertanian berkelanjutan yang mengkombinasikan akuakultur dan hidroponik dalam lingkungan yang bersifat simbiotik. Dalam akuakultur yang normal, ekskresi dari hewan yang dipelihara akan terakumulasi di air dan meningkatkan toksisitas air jika tidak dibuang."
        ),
        Jenis(
            R.drawable.aqua,
            "Media Tanam",
            "Aquaponik",
            "Akuaponik atau mina tani adalah sistem pertanian berkelanjutan yang mengkombinasikan akuakultur dan hidroponik dalam lingkungan yang bersifat simbiotik. Dalam akuakultur yang normal, ekskresi dari hewan yang dipelihara akan terakumulasi di air dan meningkatkan toksisitas air jika tidak dibuang."
        ),
        Jenis(
            R.drawable.hidro,
            "Media Tanam",
            "Hidroponik",
            "Hidroponik atau tirta tani adalah salah satu metode dalam budidaya menanam dengan memanfaatkan air tanpa menggunakan media tanah dengan menekankan pada pemenuhan kebutuhan hara nutrisi bagi tanaman. Kebutuhan air pada hidroponik lebih sedikit daripada kebutuhan air pada budidaya dengan tanah.",
        ),
        Jenis(
            R.drawable.aero,
            "Media Tanam",
            "Aeroponik",
            "Aeroponik adalah sebuah sistem yang menyuplai segala kebutuhan tanaman yang tidak bermedia tanah maupun air untuk tumbuh dan berkembang. pada aeroponik, suplai nutrisi dipenuhi dengan menyemprotkan air bernutrisi ke akar tanaman secara langsung. pada media tanam tanah dan air, suplai oksigen dan air menjadi faktor yang membatasi pertumbuhan tanaman. pada sistem aeropoik ini, kebutuhan oksigen dan air diberikan secara efisien, sehingga tidak lagi menjadi faktor penghambat.",
        ),
        Jenis(
            R.drawable.carrot,
            "Media Tanam",
            "Veltikultur",
            "Vertikultur merupakan cara bertanam yang dilakukan dengan menempatkan media tanam dalam wadah-wadah yang disusun secara vertikal, atau dapat dikatakan bahwa vertikultur merupakan upaya pemanfaatan ruang ke arah vertikal. Teknik ini berawal dari ide vertical garden yang dilontarkan oleh sebuah perusahaan benih di Swiss pada tahun 1944. Popularitas bertanam dengan dimensi vertikal ini selanjutnya berkembang pesat dinegara Eropa yang beriklim subtropis. Awalnya, sistem vertikultur digunakan untuk memamerkan tanaman ditanam umum, kebun, atau didalam rumah kaca (green house).",
        ),
        Jenis(
            R.drawable.tanah,
            "Media Tanam",
            "Tanah",
            "Akuaponik atau mina tani adalah sistem pertanian berkelanjutan yang mengkombinasikan akuakultur dan hidroponik dalam lingkungan yang bersifat simbiotik. Dalam akuakultur yang normal, ekskresi dari hewan yang dipelihara akan terakumulasi di air dan meningkatkan toksisitas air jika tidak dibuang."
        ),
        Jenis(
            R.drawable.tropis,
            "Iklim",
            "Tropis",
            "Iklim tropis menurut ilmu meteorologi, merupakan suatu iklim yang memiliki suhu rata-rata di atas 18 derajat celcius dengan curah hujan yang tinggi selama setengah tahun.\n" +
                    "Wilayah yang termasuk iklim tropis dibagi menjadi dua, yaitu tropis kering dan tropis lembap. Tropis kering ini seperti savanna, stepa, dan gurun pasir. Sedangkan lembab seperti savanna lembab, hutan hujan tropis, dan daerah musim basah.",
        ),
        Jenis(
            R.drawable.subtropis,
            "Iklim",
            "Sub-tropis",
            "Iklim subtropis adalah kawasan bumi yang berada di bagian utara dan selatan. Iklim tropis berada jauh di garis khatulistiwa. Wilayah subtropis ini terdiri dari 4 musim yaitu musim semi, musim panas, musim gugur, dan musim dingin.",
        ),
        Jenis(
            R.drawable.panas,
            "Iklim",
            "Musim panas",
            "Musim panas adalah salah satu musim di negara berhawa sedang. Tergantung letak sebuah negara, musim panas dapat terjadi pada waktu yang berbeda-beda (menurut Wikipedia). Musim panas adalah musim sesudah musim gugur, terdapat di daerah yang memiliki empat musim (menurut KBBI). Musim panas terjadi pada 21 Juni sampai dengan 23 September (di belahan Bumi utara) dan 21 Desember sampai dengan 21 Maret (di belahan Bumi selatan).",
        ),
        Jenis(
            R.drawable.dingin,
            "Iklim",
            "Musim dingin",
            "Dingin adalah syarat suhu paling dingin suatu daerah pada antara waktu-saat yg lain. musim ini hanya terjadi pada daerah yang mempunyai iklim subtropis hingga sedang, artinya hanya beberapa negara saja yang mengalami atau hanya negara yang memiliki empat isu terkini. Musim dingin terjadi pada periode 21 Desember sampai 23 Maret di bagian utara dan 21 Juni sampai 23 September di bagian selatan.",
        )
    )

    private lateinit var viewTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val b1: Button = view.findViewById(R.id.btn_scan)
        b1.setOnClickListener {
            val i = Intent(activity, CameraActivity::class.java)
            startActivity(i)
        }

        val recyclerViewHistory = view.findViewById<RecyclerView>(R.id.rv_history)
        recyclerViewHistory.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerViewHistory.adapter = HistoryAdapter(historyList, requireContext())

        val recyclerViewJenis = view.findViewById<RecyclerView>(R.id.rv_jenis)
        recyclerViewJenis.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewJenis.adapter = JenisAdapter(itemList, requireContext())

        viewTextView = view.findViewById(R.id.view_all)
        viewTextView.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}