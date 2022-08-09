package com.suhaozdemir.a7_minworkout

data class ExerciseModel(
     var id: Int,
     var name : String,
     var img : Int,
     var isCompleted : Boolean = false,
     var isSelected : Boolean = false
)
