package com.example.newsapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.HttpException
import java.io.IOException
import java.util.*

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter
    private var query = ""
    private var sources = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        responseAuthentication(query, sources)
        binding.searchButton.setOnClickListener {
            onClickingSearchButton()
        }
        binding.fabBtn.setOnClickListener {
            onClickingFabButton()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    private fun onClickingFabButton() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.bottom_sheet_layout, findViewById<LinearLayout>(R.id.bottom_sheet)
        )
        bottomSheetView.findViewById<View>(R.id.btnApply).setOnClickListener {
            val techCrunch = bottomSheetView.findViewById<CheckBox>(R.id.cb_techcrunch)
            println(techCrunch)
            val talkSport = bottomSheetView.findViewById<CheckBox>(R.id.cb_talk_sport)
            val theHindu = bottomSheetView.findViewById<CheckBox>(R.id.cb_the_hindu)
            val cnn = bottomSheetView.findViewById<CheckBox>(R.id.cb_cnn)
            val businessInsider = bottomSheetView.findViewById<CheckBox>(R.id.cb_business_insider)
            val bbcSport = bottomSheetView.findViewById<CheckBox>(R.id.cb_bbc_sport)
            when(true) {
                techCrunch.isChecked ->responseAuthentication(q="", source = "techcrunch")
                talkSport.isChecked ->responseAuthentication(q="", source = "talksport")
                theHindu.isChecked ->  responseAuthentication(q="", source ="the-hindu" )
                cnn.isChecked ->  responseAuthentication(q="", source = "cnn")
                businessInsider.isChecked ->  responseAuthentication(q="", source = "business-insider" )
                bbcSport.isChecked ->  responseAuthentication(q="", source = "bbc-sport")
                else -> {
                    Toast.makeText(this, "No results Found", Toast.LENGTH_LONG).show()
                }
            }
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun onClickingSearchButton() {
        val text = binding.searchEt.text
        query = text.toString()
        responseAuthentication(q = query, "")
    }

    private fun responseAuthentication(q: String, source: String) {
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                if (source != "") {
                    RetroInstance.api.getDataFromApiSources(sources = source)
                } else {
                    RetroInstance.api.getDataFromApiCountry(q = q)
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException,you might not have Internet Connection")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException,unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                newsAdapter.article = response.body()!!.articles
            } else {
                Log.e(TAG, "Response not successful")
            }
            binding.progressBar.isVisible = false
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() = binding.newsList.apply {
        newsAdapter = NewsAdapter(this@MainActivity)
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

}