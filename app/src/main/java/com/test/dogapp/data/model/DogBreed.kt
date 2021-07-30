package com.test.dogapp.data.model


data class DogBreed(
    var id: String,
    var name: String,
){
    override fun toString(): String{
        return name
    }
}
