package com.example.myapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.model.Datum
import kotlinx.android.synthetic.main.cart_item_layout.view.*


class CartFileListAdapter(
        val imgList:List<Datum>


) : RecyclerView.Adapter<CartFileListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imgList: Datum) {

            Glide.with(itemView.ivIcon.context)
                    .load(imgList.image)
                    .into(itemView.ivIcon)



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.cart_file_layout, parent,
                            false
                    )
            )

    override fun getItemCount(): Int = imgList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
            holder.bind(imgList[position])



}

