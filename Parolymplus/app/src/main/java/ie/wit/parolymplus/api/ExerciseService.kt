package ie.wit.parolymplus.api

import ie.wit.parolymplus.models.ExerciseModel
import retrofit2.Call
import retrofit2.http.*


interface ExerciseService {
    @GET("/exercises")
    fun findall(): Call<List<ExerciseModel>>

    @GET("/exercises/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<ExerciseModel>>

    @GET("/exercises/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<ExerciseModel>

    @DELETE("/exercises/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<ExerciseWrapper>

    @POST("/exercises/{email}")
    fun post(@Path("email") email: String?,
             @Body exercise: ExerciseModel)
            : Call<ExerciseWrapper>

    @PUT("/exercises/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body exercise: ExerciseModel
    ): Call<ExerciseWrapper>
}

