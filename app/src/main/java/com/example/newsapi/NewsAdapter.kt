package com.example.newsapi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapi.databinding.NewsStructureBinding

class NewsAdapter(private val context: Context) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: NewsStructureBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var article: List<Article>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
                   NewsStructureBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articles = article[position]
        holder.binding.apply {
            val media = articles.urlToImage?.toUri()
            newsTitle.text = articles.description
            newsSource.text = articles.source?.name
            Glide.with(context).load(media).into(newsImage)
        }
        holder.itemView.setOnClickListener {
            val image = articles.urlToImage
            val title = articles.title
            val source = articles.source
            val time = articles.publishedAt
            val content = articles.content
            val url = articles.url
            val intent = Intent(context,NewsDetailsActivity::class.java)
            intent.apply {
                putExtra("image",image)
                putExtra("title",title)
                putExtra("source",source?.name)
                putExtra("time",time)
                putExtra("content",content)
                putExtra("url",url)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return article.size
    }

}
