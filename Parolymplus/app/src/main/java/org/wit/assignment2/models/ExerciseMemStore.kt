package org.wit.parolymplus.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ExerciseMemStore : ExerciseStore {

    val exercises = ArrayList<ExerciseModel>()

    override fun findAll(): List<ExerciseModel> {
        return exercises
    }

    override fun create(exercise: ExerciseModel) {
        exercise.id = getId()
        exercises.add(exercise)
        logAll()
    }

    override fun update(exercise: ExerciseModel) {
        var foundExercise: ExerciseModel? = exercises.find { p -> p.id == exercise.id }
        if (foundExercise != null) {
            foundExercise.title = exercise.title
            foundExercise.description = exercise.description
            foundExercise.image = exercise.image
            logAll()
        }
    }

    override fun delete(exercise: ExerciseModel) {
        exercises.remove(exercise)
    }

    private fun logAll() {
        exercises.forEach { i("$it") }
    }
}