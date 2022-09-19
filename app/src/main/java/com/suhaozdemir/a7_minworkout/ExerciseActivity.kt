package com.suhaozdemir.a7_minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.suhaozdemir.a7_minworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding

    private var restTimer : CountDownTimer? = null
    private var exerciseTimer : CountDownTimer? = null
    private var restProgress = 0
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var exerciseCurrentPosition = -1 //Because zeroth index is the first index of an ArrayList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList = Constants.defaultExerciseList()

        binding.toolbarExercise.setNavigationOnClickListener{
            onBackPressed()
        }

        setupRestView()
    }

    private fun setRestProgressBar(){
        binding.progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,
                    getString(R.string.starting_your_exercise),
                    Toast.LENGTH_SHORT
                ).show()
                exerciseCurrentPosition++
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.exerciseProgressBar.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000, 1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding.exerciseProgressBar.progress = 30 - exerciseProgress
                binding.tvExerciseTimer.text = (30 - exerciseProgress).toString()
            }
            override fun onFinish() {
                if (exerciseCurrentPosition < exerciseList?.size!! - 1)
                    setupRestView()
                else
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulations, you've completed the 7 minutes workout!",
                        Toast.LENGTH_LONG,
                    ).show()
            }
        }.start()
    }

    private fun setupRestView(){
        binding.flProgressBar.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvExercise.visibility = View.INVISIBLE
        binding.flExerciseProgressBar.visibility = View.INVISIBLE
        binding.ivImage.visibility = View.INVISIBLE

        reset()
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        binding.flProgressBar.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.INVISIBLE
        binding.tvExercise.visibility = View.VISIBLE
        binding.flExerciseProgressBar.visibility = View.VISIBLE
        binding.ivImage.visibility = View.VISIBLE

        reset()

        exerciseList?.get(exerciseCurrentPosition)?.name.let { binding.tvExercise.text = it }
        exerciseList?.get(exerciseCurrentPosition)?.let { binding.ivImage.setImageResource(it.img) }

        setExerciseProgressBar()
    }


    override fun onDestroy() {
        super.onDestroy()
        reset()
    }

    private fun reset(){
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
    }

}