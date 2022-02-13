package org.wit.parolymplus.main

import android.app.Application
import org.wit.parolymplus.models.ExerciseJSONStore
import org.wit.parolymplus.models.ExerciseStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var exercises: ExerciseStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        exercises = ExerciseJSONStore(applicationContext)
        i("Exercise started")
    }
}