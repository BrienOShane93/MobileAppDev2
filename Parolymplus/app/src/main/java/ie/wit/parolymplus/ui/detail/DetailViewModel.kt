package ie.wit.parolymplus.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.parolymplus.models.ExerciseManager
import ie.wit.parolymplus.models.ExerciseModel

class DetailViewModel : ViewModel() {

    private val exercise = MutableLiveData<ExerciseModel>()

    val observableExercise: LiveData<ExerciseModel>
        get() = exercise

    fun getExercise(id: Long) {
        exercise.value = ExerciseManager.findById(id)
    }
}