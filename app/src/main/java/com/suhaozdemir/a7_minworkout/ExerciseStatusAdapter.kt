package com.suhaozdemir.a7_minworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suhaozdemir.a7_minworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val exercices : ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

        class ViewHolder(binding: ItemExerciseStatusBinding) :
            RecyclerView.ViewHolder(binding.root){
                val tvItem = binding.tvItem
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model : ExerciseModel = exercices[position]
        holder.tvItem.text = model.id.toString()
    }

    override fun getItemCount(): Int {
        return exercices.size
    }
}