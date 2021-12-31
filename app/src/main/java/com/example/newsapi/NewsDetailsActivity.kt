package com.example.newsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.newsapi.databinding.ActivityNewsDetailsBinding

class NewsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val image = intent.getStringExtra("image")?.toUri()
        val title = intent.getStringExtra("title")
        val source = intent.getStringExtra("source")
        val time = intent.getStringExtra("time")
        val content = intent.getStringExtra("content")
        val url = intent.getStringExtra("url")

        binding.apply {
            newsSource.text = source.toString()
            Glide.with(this@NewsDetailsActivity).load(image).into(ivNewsDetailPoster)
            newsHeading.text = title
            newsTime.text = time
            newsContent.text = content
            newsUrl.text = url

        }
    }
}