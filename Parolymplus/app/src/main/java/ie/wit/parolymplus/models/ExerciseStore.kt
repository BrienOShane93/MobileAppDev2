package ie.wit.parolymplus.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface ExerciseStore {
    fun findAll(exercisesList:
                MutableLiveData<List<ExerciseModel>>)
    fun findAll(userid:String,
                exercisesList:
                MutableLiveData<List<ExerciseModel>>)
    fun findById(userid:String, exerciseid: String,
                 exercise: MutableLiveData<ExerciseModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, exercise: ExerciseModel)
    fun delete(userid:String, exerciseid: String)
    fun update(userid:String, exerciseid: String, exercise: ExerciseModel)
}
