package com.suhaozdemir.a7_minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.suhaozdemir.a7_minworkout.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaultView()
        listeners()
    }

    private fun setDefaultView(){
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarBmiActivity)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = getString(R.string.calculate_bmi)
        }
    }

    private fun listeners(){
        binding.toolbarBmiActivity.setNavigationOnClickListener{
            onBackPressed()
        }

        binding.btnCalculateUnits.setOnClickListener{
            if (validateMetricUnits()){
                val heightValue : Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue : Float = binding.etMetricUnitWeight.text.toString().toFloat()

                val bmi = weightValue / (heightValue*heightValue)

                displayBMIResults(bmi)
            }else{
                Toast.makeText(this@BMIActivity
                    , "Please enter valid values.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMIResults(bmi : Float){

        val bmiLabel : String
        val bmiDesc : String

        when{
            bmi.compareTo(15f) <= 0 ->{
                bmiLabel = getString(R.string.bmi_vsu)
                bmiDesc = getString(R.string.bmi_eatmore)
            }
            bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0 -> {
                bmiLabel = getString(R.string.bmi_su)
                bmiDesc = getString(R.string.bmi_eatmore)
            }
            bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0 -> {
                bmiLabel = getString(R.string.bmi_uw)
                bmiDesc = getString(R.string.bmi_eatmore)
            }
            bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0 -> {
                bmiLabel = getString(R.string.bmi_n)
                bmiDesc = getString(R.string.bmi_congrats)
            }
            bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0 -> {
                bmiLabel = getString(R.string.bmi_ow)
                bmiDesc = getString(R.string.bmi_workout)
            }
            bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0 -> {
                bmiLabel = getString(R.string.bmi_obese1)
                bmiDesc = getString(R.string.bmi_workout)
            }
            bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 -> {
                bmiLabel = getString(R.string.bmi_obese2)
                bmiDesc = getString(R.string.bmi_actnow)
            }
            else ->{
                bmiLabel = getString(R.string.bmi_obese3)
                bmiDesc = getString(R.string.bmi_actnow)
            }
        }

        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.llDisplayBMIResult.visibility = View.VISIBLE
        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDesc

    }

    private fun validateMetricUnits():Boolean{
        return binding.etMetricUnitWeight.text.toString().isNotEmpty() &&
                binding.etMetricUnitHeight.text.toString().isNotEmpty()
    }
}