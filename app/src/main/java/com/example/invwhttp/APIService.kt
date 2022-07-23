package com.example.invwhttp

import retrofit2.Response
import retrofit2.http.*

interface APIService {


    @GET
    suspend fun getProductsByName(@Url url: String): Response<GlobalResponse>

    @GET
    suspend fun getProductByName(@Url url: String): Response<ClaseResponse>

    @POST
    suspend fun createProduct(@Url url: String)

    @Headers("Content-Type: application/json")
    @POST
    suspend fun createItem(@Url url: String, @Body itemInfo: ItemInfo): Response<CreateItemResponse>

    @Headers("Content-Type: application/json")
    @PUT
    suspend fun modifiyItem(
        @Url url: String,
        @Body itemInfo: ItemInfo
    ): Response<CreateItemResponse>


    @DELETE
    suspend fun deleteProduct(@Url url: String)
}