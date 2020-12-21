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
import com.ix.ibrahim7.rxjavaapplication.adapter.ImageSliderAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.MovieAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentHomeBinding
import com.ix.ibrahim7.rxjavaapplication.model.Movie.Content
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import util.Constant
import util.Constant.TYPE
import util.Resource
import util.ZoomAnimation


class HomeFragment : Fragment(),MovieAdapter.onClick {

    lateinit var mBinding:FragmentHomeBinding


    private val image_adapter by lazy {
        ImageSliderAdapter(requireActivity(),ArrayList())
    }

    private val pupular_adapter by lazy {
        MovieAdapter(ArrayList(),1,this)
    }


    private val upcoming_adapter by lazy {
        MovieAdapter(ArrayList(),2,this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    val array = ArrayList<Content>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewpager()


        mBinding.btnClick.setOnClickListener {
            viewModel.getPupular()
        }

        mBinding.btnClick2.setOnClickListener {
            Log.e("eee allData",array.toString())
        }

        mBinding.btnMorePupuler.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(TYPE,1)
            }
            findNavController().navigate(R.id.action_homeFragment_to_allListFragment,bundle)
        }

        mBinding.btnMoreUpcoming.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(TYPE,2)
            }
            findNavController().navigate(R.id.action_homeFragment_to_allListFragment,bundle)
        }

        mBinding.pupularList.apply {
            adapter = pupular_adapter
        }

        mBinding.upcomingList.apply {
            adapter = upcoming_adapter
        }

        viewModel.dataPupularLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    Log.e("eee data",it.data.toString())
                    pupular_adapter.data.clear()
                    pupular_adapter.data.addAll(it.data!!.contents!!)
                    pupular_adapter.notifyDataSetChanged()
                    Constant.dialog.dismiss()
                }
                is Resource.Error -> {
                    Log.e("eeee Error",it.message.toString())
                    Constant.dialog.dismiss()
                }
                is Resource.Loading -> {
                    Constant.showDialog(requireActivity())
                }
            }
        })


        viewModel.dataUpcomingLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    Log.e("eee data",it.data.toString())
                    upcoming_adapter.data.clear()
                    upcoming_adapter.data.addAll(it.data!!.contents!!)
                    upcoming_adapter.notifyDataSetChanged()
                 //   Constant.dialog.dismiss()
                }
                is Resource.Error -> {
                    Log.e("eeee Error",it.message.toString())
                  //  Constant.dialog.dismiss()
                }
                is Resource.Loading -> {
                  //  Constant.showDialog(requireActivity())
                }
            }
        })
    }



    private fun setUpViewpager() {
        image_adapter.data.clear()
        image_adapter.data.add(R.drawable.ic_slider1)
        image_adapter.data.add(R.drawable.ic_slider2)
        view_pager.apply {
            if (image_adapter.data.size == 0) {
                view_pager.visibility = View.GONE
                indicator.visibility = View.GONE
            }
            adapter = image_adapter
            setPageTransformer(ZoomAnimation())
            indicator.setViewPager2(view_pager)
        }
    }

    override fun onClickItem(content: Content, position: Int, type: Int) {
        when(type){
            1->{
                val bundle = Bundle().apply {
                    putInt(Constant.MOVIE_ID,content.id!!.toInt())
                }
                findNavController().navigate(R.id.action_homeFragment_to_detailsFragment,bundle)
            }
        }
    }

}