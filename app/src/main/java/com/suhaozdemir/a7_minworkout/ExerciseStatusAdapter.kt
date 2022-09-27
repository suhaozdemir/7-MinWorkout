package com.suhaozdemir.a7_minworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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


        when{
            model.isSelected -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_primary_color_thin_bg)
                holder.tvItem.setTextColor(Color.parseColor("#20C0FA"))
            }
            model.isCompleted ->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_primary_color_bg)
                holder.tvItem.setTextColor(Color.parseColor("#383838"))
            }
            else -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_secondary_color_bg)
                holder.tvItem.setTextColor(Color.parseColor("#20C0FA"))
            }
        }


    }

    override fun getItemCount(): Int {
        return exercices.size
    }
}