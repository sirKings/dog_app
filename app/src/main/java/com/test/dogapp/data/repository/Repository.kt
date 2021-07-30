package com.test.dogapp.data.repository

import com.test.dogapp.data.model.BreedImage
import com.test.dogapp.data.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getBreeds(): Flow<List<DogBreed>>
    suspend fun getBreedImages(breedId: String): Flow<List<BreedImage>>
}