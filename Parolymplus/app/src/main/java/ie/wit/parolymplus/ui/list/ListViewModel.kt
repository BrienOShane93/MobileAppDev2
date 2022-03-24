package ie.wit.parolymplus.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.parolymplus.models.ExerciseManager
import ie.wit.parolymplus.models.ExerciseModel

class ListViewModel : ViewModel() {

    private val exerciseList = MutableLiveData<List<ExerciseModel>>()

    val observableExerciseList: LiveData<List<ExerciseModel>>
        get() = exerciseList

    init {
        load()
    }

    fun load() {
        exerciseList.value = ExerciseManager.findAll()
    }
}