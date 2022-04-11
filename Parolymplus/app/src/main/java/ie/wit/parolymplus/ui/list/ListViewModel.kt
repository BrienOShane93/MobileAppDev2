package ie.wit.parolymplus.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.parolymplus.firebase.FirebaseDBManager
import ie.wit.parolymplus.models.ExerciseManager
import ie.wit.parolymplus.models.ExerciseModel
import timber.log.Timber
import java.lang.Exception

class ListViewModel : ViewModel() {

    private val exercisesList =
        MutableLiveData<List<ExerciseModel>>()

    val observableExercisesList: LiveData<List<ExerciseModel>>
        get() = exercisesList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init { load() }

    fun load() {
        try {
            //ExerciseManager.findAll(liveFirebaseUser.value?.email!!, ExercisesList)
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                exercisesList)
            Timber.i("Report Load Success : ${exercisesList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            //DonationManager.delete(userid,id)
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}

