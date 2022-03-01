package com.example.myapplication.ui.main.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.Cart_DATA
import kotlinx.android.synthetic.main.cart_list_item_layout.view.*

class CartListAdapter(
    private val cartData: ArrayList<Cart_DATA>


) : RecyclerView.Adapter<CartListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cartData: Cart_DATA) {

            itemView.recyclerview.setAdapter(
                CartFileListAdapter(
                    cartData.employee!!.toSet().toList()
                )
            )
            itemView.service_recyclerview.setAdapter(
                CartServiceListAdapter(
                    cartData.service!!.toSet().toList()
                )
            )



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cart_list_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = cartData.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(cartData [position])


}


