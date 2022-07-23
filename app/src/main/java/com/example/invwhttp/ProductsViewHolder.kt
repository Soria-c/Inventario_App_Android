package com.example.invwhttp

import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.invwhttp.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso

class ProductsViewHolder(view: View, listener: ProductsAdapter.onItemClickListener) :
    RecyclerView.ViewHolder(view) {

    private val binding = ItemLayoutBinding.bind(view)

    init {
        binding.btnItemModify.setOnClickListener {
            listener.onBtnModifyTap(adapterPosition)
        }
        binding.btnItemDel.setOnClickListener {
            listener.onBtnDelTap(adapterPosition)
        }

        binding.btnItemAdd.setOnClickListener {
            listener.onBtnAddTap(adapterPosition, binding.etModStock.text.toString())
        }

        binding.btnItemSub.setOnClickListener {
            listener.onBtnSubTap(adapterPosition, binding.etModStock.text.toString())
        }
    }


    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
    fun bind(item: ItemsResponse) {
        binding.tvProductName.text = item.name
        binding.tvPrice.text = "Precio: ${item.price}"
        binding.tvStock.text = "Stock: ${item.stock}"
        binding.tvISell.text = "Venta: ${item.sell_price}"
        binding.tvTotalPrice.text = "Precio T: ${item.total_price}"
        binding.tvTotalSell.text = "Venta T: ${item.total_sell_price}"
        binding.tvUnitEarning.text = "G U: ${item.unit_earning}"
        binding.tvGlobalEarning.text = "Gan T: ${item.total_earning}"



        Picasso.get().load(item.img).into(binding.ivItem)

    }
}