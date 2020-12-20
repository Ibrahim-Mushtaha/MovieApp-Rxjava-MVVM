package com.ix.ibrahim7.rxjavaapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.PupularAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentAllListBinding
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentHomeBinding
import com.ix.ibrahim7.rxjavaapplication.model.pupular.Result
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_all_list.*
import util.Constant
import util.Constant.MOVIE_ID
import util.Resource


class AllListFragment : Fragment(),PupularAdapter.onClick {

    lateinit var mBinding: FragmentAllListBinding


    private val list_adapter by lazy {
        PupularAdapter(ArrayList(),3,this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAllListBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.btnBack.setOnClickListener {
        findNavController().navigateUp()
        }

        mBinding.listItem.apply {
            adapter=list_adapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(),R.anim.recyclerview_layout_animation)
        }


        viewModel.dataPupularLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    Log.e("eee data",it.data.toString())
                    list_adapter.data.clear()
                    list_adapter.data.addAll(it.data!!.results!!)
                    list_adapter.notifyDataSetChanged()
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

    }


    override fun onClickItem(content: Result, position: Int, type: Int) {
        when(type){
            1->{
                val bundle = Bundle().apply {
                    putInt(MOVIE_ID,content.id!!.toInt())
                }
                findNavController().navigate(R.id.action_allListFragment_to_detailsFragment,bundle)
            }
        }
    }


}