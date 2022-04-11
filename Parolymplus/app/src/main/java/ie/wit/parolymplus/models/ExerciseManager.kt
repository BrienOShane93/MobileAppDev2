package ie.wit.parolymplus.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import ie.wit.parolymplus.api.ExerciseClient
import ie.wit.parolymplus.api.ExerciseWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object ExerciseManager : ExerciseStore {

    override fun findAll(exercisesList: MutableLiveData<List<ExerciseModel>>) {

        val call = ExerciseClient.getApi().findall()

        call.enqueue(object : Callback<List<ExerciseModel>> {
            override fun onResponse(call: Call<List<ExerciseModel>>,
                                    response: Response<List<ExerciseModel>>
            ) {
                exercisesList.value = response.body() as ArrayList<ExerciseModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<ExerciseModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findAll(email: String, exercisesList: MutableLiveData<List<ExerciseModel>>) {

        val call = ExerciseClient.getApi().findall(email)

        call.enqueue(object : Callback<List<ExerciseModel>> {
            override fun onResponse(call: Call<List<ExerciseModel>>,
                                    response: Response<List<ExerciseModel>>
            ) {
                exercisesList.value = response.body() as ArrayList<ExerciseModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<ExerciseModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findById(email: String, id: String, exercise: MutableLiveData<ExerciseModel>)   {

        val call = ExerciseClient.getApi().get(email,id)

        call.enqueue(object : Callback<ExerciseModel> {
            override fun onResponse(call: Call<ExerciseModel>, response: Response<ExerciseModel>) {
                exercise.value = response.body() as ExerciseModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<ExerciseModel>, t: Throwable) {
                Timber.i("Retrofit findById() Error : $t.message")
            }
        })
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, exercise: ExerciseModel) {

        val call = ExerciseClient.getApi().post(exercise.email,exercise)
        Timber.i("Retrofit ${call.toString()}")

        call.enqueue(object : Callback<ExerciseWrapper> {
            override fun onResponse(call: Call<ExerciseWrapper>,
                                    response: Response<ExerciseWrapper>
            ) {
                val exerciseWrapper = response.body()
                if (exerciseWrapper != null) {
                    Timber.i("Retrofit ${exerciseWrapper.message}")
                    Timber.i("Retrofit ${exerciseWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<ExerciseWrapper>, t: Throwable) {
                        Timber.i("Retrofit Error : $t.message")
                        Timber.i("Retrofit create Error : $t.message")
            }
        })
    }

    override fun delete(email: String,id: String) {

        val call = ExerciseClient.getApi().delete(email,id)

        call.enqueue(object : Callback<ExerciseWrapper> {
            override fun onResponse(call: Call<ExerciseWrapper>,
                                    response: Response<ExerciseWrapper>
            ) {
                val exerciseWrapper = response.body()
                if (exerciseWrapper != null) {
                    Timber.i("Retrofit Delete ${exerciseWrapper.message}")
                    Timber.i("Retrofit Delete ${exerciseWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<ExerciseWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }

    override fun update(email: String, id: String, exercise: ExerciseModel) {

        val call = ExerciseClient.getApi().put(email,id,exercise)

        call.enqueue(object : Callback<ExerciseWrapper> {
            override fun onResponse(call: Call<ExerciseWrapper>,
                                    response: Response<ExerciseWrapper>
            ) {
                val exerciseWrapper = response.body()
                if (exerciseWrapper != null) {
                    Timber.i("Retrofit Update ${exerciseWrapper.message}")
                    Timber.i("Retrofit Update ${exerciseWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<ExerciseWrapper>, t: Throwable) {
                Timber.i("Retrofit Update Error : $t.message")
            }
        })
    }
}