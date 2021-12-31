package com.example.newsapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException
import java.util.*

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var query: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        query = ""
        setupRecyclerView(query)
        val text = binding.searchEt.text
        binding.searchButton.setOnClickListener {
            query=text.toString()
            setupRecyclerView(query)
        }
    }

    private fun setupRecyclerView(query:String) = binding.newsList.apply {
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetroInstance.api.getDataFromApi(q =query)
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

        newsAdapter = NewsAdapter(this@MainActivity)
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}