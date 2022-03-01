package com.example.myapplication.ui.main.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.model.Datum
import com.example.myapplication.utils.Shared_Common_Pref
import kotlinx.android.synthetic.main.cart_item_layout.view.*


class CartAdapter(
    private val users: ArrayList<Datum>,
    var activity: Activity,
    var current_list: ArrayList<Datum> = ArrayList<Datum>(),
    var sharedCommonPref: Shared_Common_Pref,
    val service_data: String


) : RecyclerView.Adapter<CartAdapter.DataViewHolder>() {

    lateinit var itemSelectedListner: ItemSelectedListner

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            user: Datum,
            activity: Activity,
            current_list: ArrayList<Datum>,
            sharedCommonPref: Shared_Common_Pref,
            service_data: String
        ) {


            itemView.textViewUserName.text = user.name
            Glide.with(itemView.ivIcon.context)
                .load(user.image)
                .into(itemView.ivIcon)

            itemView.llcartParent.setBackgroundColor(
                if (user.isSelected()) activity.resources.getColor(
                    R.color.greentrans
                ) else Color.WHITE
            )
            itemView.llcartParent.setOnClickListener() {

                user.setSelected(!user.isSelected())
                itemView.llcartParent.setBackgroundColor(
                    if (user.isSelected()) activity.resources.getColor(
                        R.color.greentrans
                    ) else Color.WHITE
                )
                itemSelectedListner.onItemSelected(user, user.isSelected())
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cart_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position], activity, current_list, sharedCommonPref, service_data)

    fun addData(list: List<Datum>) {
        users.addAll(list)
        notifyDataSetChanged()
    }


    interface ItemSelectedListner {
        fun onItemSelected(user: Datum, isSlected: Boolean)
    }

}

