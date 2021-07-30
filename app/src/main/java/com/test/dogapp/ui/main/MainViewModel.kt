package com.test.dogapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.dogapp.data.model.BreedImage
import com.test.dogapp.data.model.DogBreed
import com.test.dogapp.data.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private var repository = RepositoryImpl()

    val isLoading = MutableLiveData<Boolean>()
    val errorMsg = MutableLiveData<String>()
    var breeds = MutableLiveData<List<DogBreed>>()
    var images = MutableLiveData<List<BreedImage>>()


    //function to get breed from repositiory
    fun getBreeds(){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBreeds().handleError {
                errorMsg.postValue(it)
                isLoading.postValue(false)
            }.collect {
                breeds.postValue(it)
                isLoading.postValue(false)
            }
        }
    }

    //function to get breed images from repositroy
    fun getImages(breedId: String){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBreedImages(breedId).handleError {
                errorMsg.postValue(it)
                isLoading.postValue(false)
            }.collect {
                images.postValue(it)
                isLoading.postValue(false)
            }
        }
    }
}


//Simple function to handle errors thrown from the repository flow
inline fun <T> Flow<T>.handleError(crossinline action: (value: String) -> Unit): Flow<T> =
    catch { e -> action(e.localizedMessage ?: "") }