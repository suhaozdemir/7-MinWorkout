package com.suhaozdemir.a7_minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.suhaozdemir.a7_minworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val flStartButton : FrameLayout = findViewById(R.id.flStart)
        binding.flStart.setOnClickListener{
            Toast.makeText(this, "Here we go", Toast.LENGTH_LONG).show()
        }
    }
}