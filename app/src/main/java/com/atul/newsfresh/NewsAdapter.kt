package com.atul.newsfresh

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listner: NewsOnClick): RecyclerView.Adapter<NewsViewHolder>() {

    private val items:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listner.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val curretItem = items[position]
        Glide.with(holder.itemView.context).load(curretItem.image).into(holder.image)
        holder.title.text = curretItem.title
        holder.author.text = curretItem.author
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title:TextView = itemView.findViewById(R.id.title)
    val author:TextView = itemView.findViewById(R.id.author)
    val image:ImageView = itemView.findViewById(R.id.image)

}

interface NewsOnClick{
    fun onItemClicked(item: News)
}