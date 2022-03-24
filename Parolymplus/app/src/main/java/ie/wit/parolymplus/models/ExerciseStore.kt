package ie.wit.parolymplus.models

interface ExerciseStore {
    fun findAll(): List<ExerciseModel>
    fun findById(id: Long) : ExerciseModel?
    fun create(exercise: ExerciseModel)
    fun update(exercise: ExerciseModel)
    fun delete(exercise: ExerciseModel)
}