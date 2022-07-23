package com.example.invwhttp

data class ItemInfo(
    val price: Float?,
    val stock: Int?,
    val sell_price: Float?
)

data class CreateItemResponse(
    val message: String
)
