package com.example.invwhttp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductsAdapter(val items: List<ItemsResponse>) : RecyclerView.Adapter<ProductsViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onBtnModifyTap(position: Int)
        fun onBtnDelTap(position: Int)
        fun onBtnAddTap(position: Int, value: String)
        fun onBtnSubTap(position: Int, value: String)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener = listener

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(
            layoutInflater.inflate(R.layout.item_layout, parent, false),
            mListener
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item: ItemsResponse = items[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }


}