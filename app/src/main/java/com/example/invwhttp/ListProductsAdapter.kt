package com.example.invwhttp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListProductsAdapter(val products: GlobalResponse) :
    RecyclerView.Adapter<ListProductsViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClickView(position: Int)
        fun onBtnDelClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProductsViewHolder {
        val layoutinflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ListProductsViewHolder(
            layoutinflater.inflate(
                R.layout.products_layout,
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: ListProductsViewHolder, position: Int) {
        val product: ClaseResponse = products.Products[position]

        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.Products.size
    }
}