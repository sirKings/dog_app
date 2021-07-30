package com.test.dogapp.data.network

import com.test.dogapp.data.model.BreedImage
import com.test.dogapp.data.model.DogBreed
import retrofit2.Response
import retrofit2.http.*


interface APIInterface {

    @GET("breeds")
    suspend fun getBreeds(@Header("x-api-key") apiKey: String): Response<List<DogBreed>>

    @GET("images/search")
    suspend fun getBreedImages(
        @Header("x-api-key") apiKey: String,
        @Query("breed_id") breedId: String,
        @Query("limit") limit: Int
    ): Response<List<BreedImage>>
}