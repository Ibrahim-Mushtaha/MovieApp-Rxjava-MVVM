package com.ix.ibrahim7.rxjavaapplication.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.ItemPupulerBinding
import com.ix.ibrahim7.rxjavaapplication.databinding.ItemUpcomingBinding
import com.ix.ibrahim7.rxjavaapplication.model.pupular.Pupular
import com.ix.ibrahim7.rxjavaapplication.model.pupular.Result
import kotlinx.android.synthetic.main.item_pupuler.view.*
import kotlinx.android.synthetic.main.item_pupuler.view.tv_image
import kotlinx.android.synthetic.main.item_pupuler.view.tv_title
import kotlinx.android.synthetic.main.item_upcoming.view.*
import util.Constant
import util.Constant.IMAGE_URL
import util.Constant.setImage


class PupularAdapter(
    var activity:Activity, var data: ArrayList<Result>,val type :Int, val itemclick: onClick
) :
        RecyclerView.Adapter<PupularAdapter.MyViewHolder>() {

    private var on_attach = true
    var DURATION: Long = 350
    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (type == 1) {
            return MyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pupuler, parent, false)
            )
        }else{
            return MyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_upcoming, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }




    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = data[position]


        holder.itemView.apply {

            if (type == 1) {
                Constant.setAnimation(this, position,on_attach,DURATION)
                setImage(
                    context,
                    IMAGE_URL + currentItem.posterPath,
                    tv_image,
                    R.drawable.ic_launcher_background
                )
                tv_title.text = currentItem.title
                tv_price.text = currentItem.id.toString()

            }else{
                Constant.setAnimation(this, position,on_attach,DURATION)
                setImage(
                    context,
                    IMAGE_URL + currentItem.posterPath,
                    tv_image_upcoming,
                    R.drawable.ic_launcher_background
                )
                tv_title.text = currentItem.title
                tv_description.text = currentItem.overview.toString()
                tv_release_day.text = currentItem.releaseDate.toString()
            }
            setOnClickListener {
                itemclick.onClickItem(data[position],position,1)
            }
        }

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Log.d("eee", "onScrollStateChanged: Called $newState")
                on_attach = false
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        super.onAttachedToRecyclerView(recyclerView)
    }

    interface onClick {
        fun onClickItem(content: Result,position: Int, type: Int)
    }




}
