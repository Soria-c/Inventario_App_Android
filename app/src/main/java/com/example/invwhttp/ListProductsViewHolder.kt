package com.example.invwhttp

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.invwhttp.databinding.ProductsLayoutBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.security.AccessController.getContext


class ListProductsViewHolder(view: View, listener: ListProductsAdapter.onItemClickListener) :
    RecyclerView.ViewHolder(view) {
    private val binding = ProductsLayoutBinding.bind(view)

    init {
        itemView.setOnClickListener {
            listener.onItemClickView(adapterPosition)
        }
        binding.btnDelete.setOnClickListener {
            listener.onBtnDelClick(adapterPosition)
        }
    }

    fun bind(productt: ClaseResponse) {
        binding.tvProductName.text = productt.name
        binding.tvPriceTotal.text = "Precio total: ${productt.total_class_price}"
        binding.tvStockTotal.text = "Stock total: ${productt.total_class_stock}"
        binding.tvSellTotal.text = "Venta total: ${productt.total_class_sell_price}"
        binding.tvEarningTotal.text = "Ganancia total: ${productt.total_class_earnings}"

        Picasso.get().load(productt.img).into(binding.ivProduct)
        //itemView.setOnClickListener {
        //     val intent:Intent = Intent(binding.tvPriceTotal.context, ResultActivity::class.java)
        //      intent.putExtra("ITEMS", productt as Serializable)
        //        startActivity(binding.tvPriceTotal.context, intent, null)
        //  }

    }


}