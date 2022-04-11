package ie.wit.parolymplus.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.parolymplus.firebase.FirebaseDBManager
import ie.wit.parolymplus.models.ExerciseManager
import ie.wit.parolymplus.models.ExerciseModel
import timber.log.Timber
import java.lang.Exception

class DetailViewModel : ViewModel() {
    private val exercise = MutableLiveData<ExerciseModel>()

    var observableExercise: LiveData<ExerciseModel>
         get() = exercise
         set(value) {exercise.value = value.value}


//    //@InverseMethod("setAmount")
//    //fun getAmount() : Int {return exercise.value!!.amount  }
//    //fun setAmount(amount: Int) {exercise.value!!.amount = amount }
//
//    fun getMessage() : String { return exercise.value!!.message  }
//    //@InverseMethod("setMessage")
//    fun setMessage(message: String) {exercise.value!!.message = message }
//

    fun getExercise(userid:String, id: String) {
        try {
            //ExerciseManager.findById(email, id, exercise)
            FirebaseDBManager.findById(userid, id, exercise)
            Timber.i("Detail getExercise() Success : ${
                exercise.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getExercise() Error : $e.message")
        }
    }

    fun updateExercise(userid:String, id: String,exercise: ExerciseModel) {
        try {
            //ExerciseManager.update(email, id, exercise)
            FirebaseDBManager.update(userid, id, exercise)
            Timber.i("Detail update() Success : $exercise")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}

