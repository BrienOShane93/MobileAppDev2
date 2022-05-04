package ie.wit.parolymplus.main

import android.app.Application
import timber.log.Timber

class MainApp : Application() {

    //lateinit var exercisesStore: ExerciseStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //  exercisesStore = ExerciseManager()
        Timber.i("Parolymplus Application Started")
    }
}