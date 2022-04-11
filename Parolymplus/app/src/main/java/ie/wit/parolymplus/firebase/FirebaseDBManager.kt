package ie.wit.parolymplus.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.parolymplus.models.ExerciseModel
import ie.wit.parolymplus.models.ExerciseStore
import timber.log.Timber

var database: DatabaseReference = FirebaseDatabase.getInstance().reference

object FirebaseDBManager : ExerciseStore {
    override fun findAll(exercisesList: MutableLiveData<List<ExerciseModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, exercisesList: MutableLiveData<List<ExerciseModel>>) {

        database.child("user-exercises").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Exercise error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ExerciseModel>()
                    val children = snapshot.children
                    children.forEach {
                        val exercise = it.getValue(ExerciseModel::class.java)
                        localList.add(exercise!!)
                    }
                    database.child("user-exercises").child(userid)
                        .removeEventListener(this)

                    exercisesList.value = localList
                }
            })
    }

    override fun findById(userid: String, exerciseid: String, exercise: MutableLiveData<ExerciseModel>) {

        database.child("user-exercises").child(userid)
            .child(exerciseid).get().addOnSuccessListener {
                exercise.value = it.getValue(ExerciseModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, exercise: ExerciseModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("exercises").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        exercise.uid = key
        val exerciseValues = exercise.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/exercises/$key"] = exerciseValues
        childAdd["/user-exercises/$uid/$key"] = exerciseValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, exerciseid: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/exercises/$exerciseid"] = null
        childDelete["/user-exercises/$userid/$exerciseid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, exerciseid: String, exercise: ExerciseModel) {

        val exerciseValues = exercise.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["exercises/$exerciseid"] = exerciseValues
        childUpdate["user-exercises/$userid/$exerciseid"] = exerciseValues

        database.updateChildren(childUpdate)
    }
}