package ie.wit.parolymplus.ui.form

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.parolymplus.main.MainApp
import ie.wit.parolymplus.R
import ie.wit.parolymplus.databinding.FragmentFormBinding
import ie.wit.parolymplus.helpers.showImagePicker
import ie.wit.parolymplus.models.ExerciseModel
import ie.wit.parolymplus.ui.list.ListViewModel
import timber.log.Timber.Forest.i

class FormFragment : Fragment() {

    lateinit var app: MainApp
    var totalDonated = 0
    private var _fragBinding: FragmentFormBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    //lateinit var navController: NavController
    private lateinit var formViewModel: FormViewModel

    var exercise = ExerciseModel()
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragBinding = FragmentFormBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        formViewModel = ViewModelProvider(this).get(FormViewModel::class.java)
        formViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        setButtonListener(fragBinding)
        return root;
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.exerciseError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentFormBinding) {
        layout.btnAdd.setOnClickListener() {
            exercise.title = layout.exerciseTitle.text.toString()
            exercise.duration = layout.exerciseDuration.text.toString()
            exercise.set = layout.exerciseSet.text.toString()
            if (exercise.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_exercise_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                app.exercisesStore.create(exercise.copy())
            }
            i("add Button Pressed: $exercise")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        val listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.observableExerciseList.observe(viewLifecycleOwner, Observer {

        })
    }
}