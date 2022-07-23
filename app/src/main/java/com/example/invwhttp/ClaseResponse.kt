package com.example.invwhttp

import java.io.Serializable

data class GlobalResponse(
    val global_price: Float,
    val global_stock: Int,
    val global_sell_price: Float,
    val global_earnings: Float,
    val Products: List<ClaseResponse>
)


data class ClaseResponse(
    val class_id: Int,
    val name: String,
    val total_class_price: Float,
    val total_class_stock: Int,
    val total_class_sell_price: Float,
    val total_class_earnings: Float,
    val img: String,
    val items: List<ItemsResponse>
) : Serializable

data class ItemsResponse(
    val item_id: Int,
    val name: String,
    val price: Float,
    val sell_price: Float,
    val total_price: Float,
    val total_sell_price: Float,
    val unit_earning: Float,
    val total_earning: Float,
    val stock: Int,
    val img: String,
    val class_id: Int
) : Serializable
