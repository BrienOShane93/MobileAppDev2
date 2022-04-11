package ie.wit.parolymplus.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.wit.parolymplus.databinding.FragmentDetailBinding
import ie.wit.parolymplus.ui.auth.LoggedInViewModel
import ie.wit.parolymplus.ui.list.ListViewModel
import timber.log.Timber

class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private val args by navArgs<DetailFragmentArgs>()
    private var _fragBinding: FragmentDetailBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val listViewModel : ListViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.observableExercise.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.editExerciseButton.setOnClickListener {
            detailViewModel.updateExercise(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.exerciseid, fragBinding.exercisevm?.observableExercise!!.value!!)
            //Force Reload of list to guarantee refresh
            listViewModel.load()
            findNavController().navigateUp()
            //findNavController().popBackStack()

        }

        fragBinding.deleteExerciseButton.setOnClickListener {
            listViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                detailViewModel.observableExercise.value?.uid!!)
            findNavController().navigateUp()
        }
        return root
    }

    private fun render() {
        fragBinding.exercisevm = detailViewModel
        Timber.i("Retrofit fragBinding.exercisevm == $fragBinding.exercisevm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getExercise(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.exerciseid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}