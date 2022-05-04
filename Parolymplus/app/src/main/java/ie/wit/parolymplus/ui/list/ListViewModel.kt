package ie.wit.parolymplus.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.parolymplus.firebase.FirebaseDBManager
import ie.wit.parolymplus.models.ExerciseModel
import timber.log.Timber
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ListViewModel : ViewModel() {

    private val exercisesList =
        MutableLiveData<List<ExerciseModel>>()

    val observableExercisesList: LiveData<List<ExerciseModel>>
        get() = exercisesList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var readOnly = MutableLiveData(false)

    var searchResults = ArrayList<ExerciseModel>()

    init { load() }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                exercisesList)
            Timber.i("List Load Success : ${exercisesList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("List Load Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(exercisesList)
            Timber.i("List LoadAll Success : ${exercisesList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("List LoadAll Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("List Delete Success")
        }
        catch (e: Exception) {
            Timber.i("List Delete Error : $e.message")
        }
    }
}