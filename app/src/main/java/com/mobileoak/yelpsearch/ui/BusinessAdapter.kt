package com.mobileoak.yelpsearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobileoak.yelpsearch.R
import com.mobileoak.yelpsearch.network.Business

class BusinessAdapter: RecyclerView.Adapter<BusinessAdapter.ViewHolder>() {

    private var dataset: List<Business> = emptyList()

    class ViewHolder(layout: LinearLayout): RecyclerView.ViewHolder(layout) {
        val name: TextView = layout.findViewById(R.id.business_name)
        val rating: TextView = layout.findViewById(R.id.rating)
        val image: ImageView = layout.findViewById(R.id.business_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.business_view, parent, false) as LinearLayout
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = dataset[position].name
        val rating = dataset[position].rating
        val imageURL = dataset[position].imageUrl

        holder.name.text = name
        holder.rating.text = "Rating: $rating" // given more time I'd use AndroidResources for this

        Glide.with(holder.itemView.context).load(imageURL).into(holder.image)
    }

    fun updateData(dataset: List<Business>) {
        this.dataset = dataset
    }

    override fun getItemCount() = dataset.size
}