package vamsee.application.freshnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vamsee.application.freshnews.modal.News

class NewsListAdapter(private val listener: newItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private var items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentPos = items[position]
        holder.titleView.text = currentPos.title
        holder.author.text = currentPos.author
        Glide.with(holder.itemView.context).load(currentPos.urlToImage).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }

    fun setData(newList: News){
        items.add(newList)
        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView : TextView = itemView.findViewById(R.id.title)
    val image : ImageView = itemView.findViewById(R.id.img)
    val author: TextView = itemView.findViewById(R.id.author)
}



interface newItemClicked{
    fun onItemClicked(item: News)
}