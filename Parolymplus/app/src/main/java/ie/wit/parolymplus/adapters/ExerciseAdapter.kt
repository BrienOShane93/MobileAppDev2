package ie.wit.parolymplus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.parolymplus.R
import ie.wit.parolymplus.databinding.CardExerciseBinding
import ie.wit.parolymplus.models.ExerciseModel

interface ExerciseClickListener {
    fun onExerciseClick(exercise: ExerciseModel)
}

class ExerciseAdapter constructor(private var exercises: ArrayList<ExerciseModel>,
                                  private val listener: ExerciseClickListener,
                                  private val readOnly: Boolean)
    : RecyclerView.Adapter<ExerciseAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val exercise = exercises[holder.adapterPosition]
        holder.bind(exercise,listener)
    }

    fun removeAt(position: Int) {
        exercises.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = exercises.size

    inner class MainHolder(val binding : CardExerciseBinding, private val readOnly : Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(exercise: ExerciseModel, listener: ExerciseClickListener) {
            binding.root.tag = exercise
            binding.exercise = exercise
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onExerciseClick(exercise) }
            binding.executePendingBindings()
        }
    }
}