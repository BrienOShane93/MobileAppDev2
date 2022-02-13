package org.wit.parolymplus.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.parolymplus.R
import org.wit.parolymplus.adapters.ExerciseAdapter
import org.wit.parolymplus.adapters.ExerciseListener
import org.wit.parolymplus.databinding.ActivityParolymplusListBinding
import org.wit.parolymplus.main.MainApp
import org.wit.parolymplus.models.ExerciseModel

class ParolymplusListActivity : AppCompatActivity(), ExerciseListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityParolymplusListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParolymplusListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadExercises()

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ParolymplusActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onExerciseClick(exercise: ExerciseModel) {
        val launcherIntent = Intent(this, ParolymplusActivity::class.java)
        launcherIntent.putExtra("exercise_edit", exercise)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadExercises() }
    }

    private fun loadExercises() {
        showExercises(app.exercises.findAll())
    }

    fun showExercises (exercises: List<ExerciseModel>) {
        binding.recyclerView.adapter = ExerciseAdapter(exercises, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}