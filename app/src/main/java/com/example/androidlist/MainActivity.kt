package com.example.androidlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.articlesRecyclerView)
        val articleAdapter = ArticleAdapter(this)
        articleAdapter.mArticles = articles.toMutableList()
        recyclerView.adapter = articleAdapter
        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)!!
        pullToRefresh.setOnRefreshListener {
            articleAdapter.mArticles.removeAt(0)
            articleAdapter.notifyItemRemoved(0)
            pullToRefresh.isRefreshing = false;
        }
        articleAdapter.notifyItemRangeInserted(0, articleAdapter.itemCount)
    }
}