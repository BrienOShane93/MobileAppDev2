package ie.wit.parolymplus.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextWatcher
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.parolymplus.R
import ie.wit.parolymplus.adapters.ExerciseAdapter
import ie.wit.parolymplus.adapters.ExerciseClickListener
import ie.wit.parolymplus.databinding.FragmentListBinding
import ie.wit.parolymplus.models.ExerciseModel
import ie.wit.parolymplus.ui.auth.LoggedInViewModel
import ie.wit.parolymplus.utils.*
import timber.log.Timber


class ListFragment : Fragment(), ExerciseClickListener {

    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader : AlertDialog
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

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
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToFormFragment()
            findNavController().navigate(action)
        }
        showLoader(loader, "Downloading Exercises")
        listViewModel.observableExercisesList.observe(viewLifecycleOwner, Observer { exercises ->
            exercises?.let {
                render(exercises as ArrayList<ExerciseModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting Exercise")
                val adapter = fragBinding.recyclerView.adapter as ExerciseAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                listViewModel.delete(
                    listViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as ExerciseModel).uid!!
                )
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onExerciseClick(viewHolder.itemView.tag as ExerciseModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)

        val item = menu.findItem(R.id.toggleAnimals) as MenuItem
        item.setActionView(R.layout.toggle_button)
        val toggleExercises: SwitchCompat = item.actionView.findViewById(R.id.toggleButton)
        toggleExercises.isChecked = false

        toggleExercises.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) listViewModel.loadAll()
            else listViewModel.load()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun render(exercisesList: ArrayList<ExerciseModel>) {
        fragBinding.recyclerView.adapter = ExerciseAdapter(exercisesList, this,
            listViewModel.readOnly.value!!)
        if (exercisesList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.exercisesNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.exercisesNotFound.visibility = View.GONE
        }
    }

    override fun onExerciseClick(exercise: ExerciseModel) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(exercise.uid!!)

        if(!listViewModel.readOnly.value!!)
            findNavController().navigate(action)
    }

    private fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading Exercises")
            if(listViewModel.readOnly.value!!)
                listViewModel.loadAll()
            else
                listViewModel.load()
        }
    }

    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading Exercises")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                listViewModel.liveFirebaseUser.value = firebaseUser
                listViewModel.load()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}