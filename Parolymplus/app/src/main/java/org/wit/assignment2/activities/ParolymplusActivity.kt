package org.wit.parolymplus.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.parolymplus.R
import org.wit.parolymplus.databinding.ActivityParolymplusBinding
import org.wit.parolymplus.helpers.showImagePicker
import org.wit.parolymplus.main.MainApp
import org.wit.parolymplus.models.ExerciseModel
import timber.log.Timber.i

class ParolymplusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParolymplusBinding
    var exercise = ExerciseModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityParolymplusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Parolymplus Activity started...")

        if (intent.hasExtra("exercise_edit")) {
            edit = true
            exercise = intent.extras?.getParcelable("exercise_edit")!!
            binding.exerciseTitle.setText(exercise.title)
            binding.description.setText(exercise.description)
            binding.btnAdd.setText(R.string.save_exercise)
            Picasso.get()
                .load(exercise.image)
                .into(binding.exerciseImage)
            if (exercise.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_exercise_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            exercise.title = binding.exerciseTitle.text.toString()
            exercise.description = binding.description.text.toString()
            if (exercise.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_exercise_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.exercises.update(exercise.copy())
                } else {
                    app.exercises.create(exercise.copy())
                }
            }
            i("add Button Pressed: $exercise")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_exercise, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.exercises.delete(exercise)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            exercise.image = result.data!!.data!!
                            Picasso.get()
                                .load(exercise.image)
                                .into(binding.exerciseImage)
                            binding.chooseImage.setText(R.string.change_exercise_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}