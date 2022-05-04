package ie.wit.parolymplus.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import ie.wit.parolymplus.R
import ie.wit.parolymplus.databinding.CardExerciseBinding
import ie.wit.parolymplus.models.ExerciseModel
import ie.wit.parolymplus.utils.customTransformation

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

            Picasso.get().load(exercise.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onExerciseClick(exercise) }
            binding.executePendingBindings()
        }
    }
}