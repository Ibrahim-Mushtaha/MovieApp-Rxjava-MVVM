package com.ix.ibrahim7.rxjavaapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.ItemCategoryBinding
import com.ix.ibrahim7.rxjavaapplication.model.details.Genre
import kotlinx.android.synthetic.main.item_category.view.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant


class GenresAdapter(
    var data: ArrayList<Genre>
) :
    RecyclerView.Adapter<GenresAdapter.MyViewHolder>() {

    private var on_attach = true
    var DURATION: Long = 350
    class MyViewHolder(item: ItemCategoryBinding) : RecyclerView.ViewHolder(item.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: ItemCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category, parent, false
        )
        return MyViewHolder(
            itemView_layout
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.apply {
            Constant.setAnimation(this, position,on_attach,DURATION)
            tv_category_title.text=data[position].name
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


}
