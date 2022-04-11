package ie.wit.parolymplus.ui.form

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import ie.wit.parolymplus.R
import ie.wit.parolymplus.databinding.FragmentFormBinding
import ie.wit.parolymplus.models.ExerciseModel
import ie.wit.parolymplus.ui.auth.LoggedInViewModel
import ie.wit.parolymplus.ui.list.ListViewModel

class FormFragment : Fragment() {

    var totalDonated = 0
    private var _fragBinding: FragmentFormBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    private lateinit var formViewModel: FormViewModel
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

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
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.exerciseError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentFormBinding) {
        layout.btnAdd.setOnClickListener {
            /*val title = if (layout.exerciseTitle.text.isNotEmpty())
                layout.exerciseTitle.text.toString() else Snackbar.make(it,R.string.enter_exercise_title, Snackbar.LENGTH_LONG).show()
            formViewModel.addExercise(loggedInViewModel.liveFirebaseUser,
                ExerciseModel(title = title,
                    email = loggedInViewModel.liveFirebaseUser.value?.email!!))*/
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_form, menu)
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
        listViewModel.observableExercisesList.observe(viewLifecycleOwner, Observer {

        })
    }
}