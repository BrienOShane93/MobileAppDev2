package ie.wit.parolymplus.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.parolymplus.models.ExerciseManager
import ie.wit.parolymplus.models.ExerciseModel

class FormViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addExercise(exercise: ExerciseModel) {
        status.value = try {
            ExerciseManager.create(exercise)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}