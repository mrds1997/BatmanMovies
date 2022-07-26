package com.batmanapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batmanapp.R
import com.batmanapp.data.db.entities.MovieEntity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_movie.view.*
import kotlin.collections.ArrayList

class MovieAdapter(private val items: ArrayList<MovieEntity>, private val onItemSelected: OnItemSelected) :RecyclerView.Adapter<MovieAdapter.DataObjectHolder>() {


    companion object {
        val TYPE_HEADER = 1
        val TYPE_NONE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataObjectHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {
        val context = holder.itemView.context
        val itemPosition = position
        val viewType = getItemViewType(position)
        val item = items[itemPosition]

        Glide.with(context).load(item.poster).into(holder.itemView.imgMovie)
        holder.itemView.tvTitle.text = item.title
        holder.itemView.tvYear.text = item.year
        holder.itemView.layoutMain.setOnClickListener {
            onItemSelected.onSelect(item.imdbId, item.title)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    class DataObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemSelected {
        fun onSelect(id: String?, title: String?)
    }
}