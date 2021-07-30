package com.test.dogapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.dogapp.R
import com.test.dogapp.data.model.BreedImage
import com.test.dogapp.data.model.DogBreed
import com.test.dogapp.ui.main.adapters.ImageAdapter


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private val model: MainViewModel by viewModels()
    private var adapter : ArrayAdapter<DogBreed>? = null
    private var breeds = ArrayList<DogBreed>()
    private val images = ArrayList<BreedImage>()
    private var imageAdapter: ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setup adapter


        //setup spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = this@MainActivity

        //setup Recyclerview
        findViewById<RecyclerView>(R.id.imagesRecyclerView).run {
            // set a GridLayoutManager with default vertical orientation and 3 number of columns
            val gridLayoutManager = GridLayoutManager(this@MainActivity, 3)
            this.layoutManager = gridLayoutManager // set LayoutManager to RecyclerView

            //  call the constructor of ImageAdapter to send the reference and data to Adapter
            imageAdapter = ImageAdapter(images)
            this.adapter = imageAdapter
        }



        model.isLoading.observe(this, {
            findViewById<ProgressBar>(R.id.progressBar).visibility =
                if (it) View.VISIBLE else View.GONE
        })

        model.getBreeds()

        model.breeds.observe(this, {
            adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        })

        model.images.observe(this, {
            it.forEach {
                Log.e("Dog", it.id)
            }
            images.clear()
            images.addAll(it)
            imageAdapter?.notifyDataSetChanged()
        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        model.breeds.value?.get(position)?.let { model.getImages(it.id) }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}