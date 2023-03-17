package com.david.hackro.androidlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    private val coroutineViewModel: CoroutineViewModel by viewModels()
    private val coldAndHotDataViewModel: ColdAndHotDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //coroutineViewModel.coroutines()
        coldAndHotDataViewModel.coldAndHotData()
    }

}