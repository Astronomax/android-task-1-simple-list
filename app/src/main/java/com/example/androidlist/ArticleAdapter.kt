package com.example.androidlist
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter(context: Context) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    var mArticles: MutableList<Article> = mutableListOf()
    private val mExpandedPositions: MutableSet<Int> = mutableSetOf()
    private val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = mArticles[position]
        val isExpanded = mExpandedPositions.contains(position)
        holder.details.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.arrow.rotation = if(isExpanded) 180F else 0F
        holder.itemView.isActivated = isExpanded
        holder.itemView.setOnClickListener {
            if (mExpandedPositions.contains(position))
                mExpandedPositions.remove(position)
            else mExpandedPositions.add(position)
            notifyItemChanged(position)
        }
        holder.url.setOnClickListener {
            val uri = Uri.parse("https://${article.domain}")
            mContext.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        holder.title.text = article.title
        holder.comments.text = "${article.commentsCount} comments"
    }

    override fun getItemCount(): Int {
        return mArticles.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.titleTextView)!!
        val comments = itemView.findViewById<TextView>(R.id.commentsTextView)!!
        val url = itemView.findViewById<ImageView>(R.id.urlImageButton)!!
        val details = itemView.findViewById<View>(R.id.details)!!
        val arrow = itemView.findViewById<ImageView>(R.id.arrow)!!
    }
}
