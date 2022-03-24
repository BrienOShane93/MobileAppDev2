package ie.wit.parolymplus.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ie.wit.parolymplus.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private val args by navArgs<DetailFragmentArgs>()
    private var _fragBinding: FragmentDetailBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.observableExercise.observe(viewLifecycleOwner, Observer { render() })
        return root
    }

    private fun render() {
        fragBinding.editTitle.setText("A Title")
        fragBinding.editSet.setText("A Set")
        fragBinding.editDuration.setText("A Set")
        fragBinding.detailvm = detailViewModel
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getExercise(args.exerciseid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}