package com.test.dogapp.data.repository

import android.util.Log
import com.google.gson.Gson
import com.test.dogapp.data.model.BreedImage
import com.test.dogapp.data.model.DogBreed
import com.test.dogapp.data.network.APIClient
import com.test.dogapp.data.network.APIInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RepositoryImpl: Repository{

    private val api = APIClient.client.create(APIInterface::class.java)

    ///hard coded apikey because of no login flow.
    private var key = "118c1323-377e-45a1-9678-d7b647f917e7"

    override suspend fun getBreeds(): Flow<List<DogBreed>> {
        return flow {
            val response = api.getBreeds(apiKey = key)
            if (response.isSuccessful && response.code() == 200 && response.body() != null){
                emit(response.body()!!)
            }else{
                throw Throwable("An error occurred")
            }
        }
    }

    override suspend fun getBreedImages(breedId: String): Flow<List<BreedImage>> {
        return flow {
            val response = api.getBreedImages(
                limit = 102, breedId = breedId, apiKey = key)
            if (response.isSuccessful && response.code() == 200 && response.body() != null){
                emit(response.body()!!)
            }else{
                throw Throwable("An error occurred")
            }
        }
    }
}