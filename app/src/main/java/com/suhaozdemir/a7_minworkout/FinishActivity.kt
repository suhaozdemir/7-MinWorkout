package com.suhaozdemir.a7_minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.suhaozdemir.a7_minworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFinishBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setDefaultView()
        listeners()
    }

    private fun setDefaultView(){
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFinishActivity)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun listeners(){
        binding.toolbarFinishActivity.setNavigationOnClickListener{
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }
    }
}