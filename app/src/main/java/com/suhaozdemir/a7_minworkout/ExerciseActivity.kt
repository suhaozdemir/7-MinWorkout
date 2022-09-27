package com.suhaozdemir.a7_minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.suhaozdemir.a7_minworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding
    private var textToSpeech: TextToSpeech? = null

    private var restTimer : CountDownTimer? = null
    private var exerciseTimer : CountDownTimer? = null
    private var restProgress = 0
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var exerciseCurrentPosition = -1 //Because zeroth index is the first index of an ArrayList

    private var exerciseStatusAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList = Constants.defaultExerciseList()

        textToSpeech = TextToSpeech(this, this)

        binding.toolbarExercise.setNavigationOnClickListener{
            onBackPressed()
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    private fun setupExerciseStatusRecyclerView(){
        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseStatusAdapter = exerciseList?.let { ExerciseStatusAdapter(it) }
        binding.rvExerciseStatus.adapter = exerciseStatusAdapter
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
                exerciseList?.get(exerciseCurrentPosition)?.isSelected = true
                exerciseStatusAdapter?.notifyItemChanged(exerciseCurrentPosition)
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

                exerciseList?.get(exerciseCurrentPosition)?.isSelected = false
                exerciseList?.get(exerciseCurrentPosition)?.isCompleted = true
                exerciseStatusAdapter?.notifyItemChanged(exerciseCurrentPosition)

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
        binding.flRestView.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvExercise.visibility = View.INVISIBLE
        binding.flExerciseView.visibility = View.INVISIBLE
        binding.ivImage.visibility = View.INVISIBLE
        binding.tvUpcomingExercise.visibility = View.VISIBLE
        binding.tvUpcomingExerciseName.visibility = View.VISIBLE

        reset()

        exerciseList?.get(exerciseCurrentPosition + 1)?.name?.let {
            binding.tvUpcomingExerciseName.text = it
            speakOut(binding.tvUpcomingExercise.text.toString() + it)
        }

        setRestProgressBar( )
    }

    private fun setupExerciseView(){
        binding.flRestView.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.INVISIBLE
        binding.tvExercise.visibility = View.VISIBLE
        binding.flExerciseView.visibility = View.VISIBLE
        binding.ivImage.visibility = View.VISIBLE
        binding.tvUpcomingExercise.visibility = View.INVISIBLE
        binding.tvUpcomingExerciseName.visibility = View.INVISIBLE

        reset()

        exerciseList?.get(exerciseCurrentPosition)?.name?.let {
            binding.tvExercise.text = it
            speakOut(it)}
        exerciseList?.get(exerciseCurrentPosition)?.let { binding.ivImage.setImageResource(it.img) }

        setExerciseProgressBar()
    }


    override fun onDestroy() {

        if (textToSpeech != null){
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        reset()
        super.onDestroy()
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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = textToSpeech?.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The Language specified is not supported!")
            }else{
                Log.e("TTS", "Initialization Failed!")
            }
        }
    }

    private fun speakOut(text:String){
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}