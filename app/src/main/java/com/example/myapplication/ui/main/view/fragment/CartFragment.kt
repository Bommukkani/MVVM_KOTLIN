package com.example.myapplication.ui.main.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.ApiClient
import com.example.myapplication.data.api.ApiHelper
import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.Cart_DATA
import com.example.myapplication.data.repository.MainRepository
import com.example.myapplication.ui.base.MyViewModelFactory
import com.example.myapplication.ui.main.adapter.CartListAdapter
import com.example.myapplication.ui.main.viewmodel.MainViewModel
import com.example.myapplication.utils.Shared_Common_Pref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CartFragment : Fragment() {


    lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView

    lateinit var sharedCommonPref: Shared_Common_Pref


    val apiInterface: ApiService = ApiClient.client!!.create(ApiService::class.java)
    lateinit var adapter: CartListAdapter

    lateinit var values: ArrayList<Cart_DATA>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedCommonPref = Shared_Common_Pref(requireContext())

        recyclerView = view.findViewById(R.id.recyclerview)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(MainRepository(ApiHelper(apiInterface)))

        ).get(MainViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val gson = Gson()
        val json = sharedCommonPref.getvalue("cart_group_data")
        val type = object : TypeToken<ArrayList<Cart_DATA>>() {
        }.type

        if (json == null)
            values = ArrayList()
        else
            values = gson.fromJson(json, type)


        adapter = CartListAdapter(values)
        recyclerView.adapter = adapter
    }


}