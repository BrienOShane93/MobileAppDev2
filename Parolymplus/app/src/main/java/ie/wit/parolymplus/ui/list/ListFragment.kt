package ie.wit.parolymplus.ui.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.parolymplus.adapters.ExerciseAdapter
import ie.wit.parolymplus.main.MainApp
import ie.wit.parolymplus.R
import ie.wit.parolymplus.adapters.ExerciseListener
import ie.wit.parolymplus.databinding.FragmentListBinding
import ie.wit.parolymplus.models.ExerciseModel

class ListFragment : Fragment(), ExerciseListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var listViewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.observableExerciseList.observe(viewLifecycleOwner, Observer {
                exercises ->
            exercises?.let { render(exercises) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = ListFragmentDirections.actionNavListToNavForm()
            findNavController().navigate(action)
        }
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(exerciseList: List<ExerciseModel>) {
        fragBinding.recyclerView.adapter = ExerciseAdapter(exerciseList, this)
        if (exerciseList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.exercisesNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.exercisesNotFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        listViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onExerciseClick(exercise: ExerciseModel) {
        val action = ListFragmentDirections.actionNavListToNavDetail(exercise.id)
        findNavController().navigate(action)
    }
}