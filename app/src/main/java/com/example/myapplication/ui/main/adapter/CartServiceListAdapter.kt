package com.example.myapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.Datum
import kotlinx.android.synthetic.main.item_layout.view.*


class CartServiceListAdapter(
        val servicelist:List<Datum>


) : RecyclerView.Adapter<CartServiceListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(service: Datum) {

            itemView.tvName.text = service.name
            itemView.tvPrice.text = "$"+service.price.toString()+".0"



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.cart_service_list_layout, parent,
                            false
                    )
            )

    override fun getItemCount(): Int = servicelist.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
            holder.bind(servicelist[position])



}

