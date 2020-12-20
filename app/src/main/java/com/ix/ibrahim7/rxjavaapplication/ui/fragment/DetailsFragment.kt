package com.ix.ibrahim7.rxjavaapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.GenresAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentAllListBinding
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentDetailsBinding
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.DetailsViewModel
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import util.Constant
import util.Constant.IMAGE_URL
import util.Constant.MOVIE_ID
import util.Constant.setImage
import util.Resource


class DetailsFragment : Fragment() {

    lateinit var mBinding: FragmentDetailsBinding


    private val viewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    private val genres_adapter by lazy {
        GenresAdapter(ArrayList())
    }

    private val getMovieID by lazy {
        requireArguments().getInt(MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        viewModel.getMovieDetails(getMovieID)

        mBinding.genresList.apply {
            adapter=genres_adapter
        }

        viewModel.dataDetailsLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { movie ->
                        mBinding.apply {
                            setImage(
                                requireContext(),
                                IMAGE_URL + movie.posterPath,
                                tvImage,
                                R.drawable.ic_launcher_background
                            )
                            genres_adapter.data.addAll(movie.genres!!)
                            genres_adapter.notifyDataSetChanged()
                            tvMovieName.text = movie.title
                            movieRating.rating = (movie.voteAverage!! / 2).toFloat()
                            tvMovieDescription.text = movie.overview
                        }
                        Constant.dialog.dismiss()
                    }
                }
                is Resource.Error -> {
                    Log.e("eeee Error", it.message.toString())
                    Constant.dialog.dismiss()
                }
                is Resource.Loading -> {
                    Constant.showDialog(requireActivity())
                }
            }
        })

    }


}