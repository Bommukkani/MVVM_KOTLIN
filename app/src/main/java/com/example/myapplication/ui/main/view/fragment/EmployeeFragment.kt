package com.example.myapplication.ui.main.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.api.ApiClient
import com.example.myapplication.data.api.ApiHelper
import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.Cart_DATA
import com.example.myapplication.data.model.Datum
import com.example.myapplication.data.repository.MainRepository
import com.example.myapplication.ui.base.MyViewModelFactory
import com.example.myapplication.ui.main.adapter.CartAdapter
import com.example.myapplication.ui.main.viewmodel.MainViewModel
import com.example.myapplication.utils.Shared_Common_Pref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class EmployeeFragment : Fragment(), CartAdapter.ItemSelectedListner {

    private val TAG = "EmployeeFragment"
    private var current_list = ArrayList<Datum>()
    private var current_service_list = ArrayList<Datum>()
    private var old_employee_list = ArrayList<Datum>()
    private var cart_list_data = ArrayList<Cart_DATA>()

    lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var ivImg: ImageView

    lateinit var profileName: String
    lateinit var service_data: String
    lateinit var tvAddCart: TextView
    lateinit var tvAddCartParent: CardView

    lateinit var sharedCommonPref: Shared_Common_Pref

    val apiInterface: ApiService = ApiClient.getClient().create(ApiService::class.java)
    lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            profileName = getArguments()?.getString("Username").toString()
            service_data = getArguments()?.getString("service_data").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        recyclerView = view.findViewById(R.id.recyclerview)
        ivImg = view.findViewById(R.id.imageViewAvatar)

        tvAddCart = view.findViewById(R.id.tvAddCart)
        tvAddCartParent = view.findViewById(R.id.tvAddCart_parent)
        tvAddCart.isEnabled = false
        sharedCommonPref = Shared_Common_Pref(requireContext())


        Glide.with(this)
            .load(profileName)
            .into(ivImg)

        tvAddCart.setOnClickListener() {

            val gson = Gson()

            val type1 = object : TypeToken<Datum>() {
            }.type

            val serEmpType = object : TypeToken<ArrayList<Datum>>() {
            }.type


            Log.v("cart_data:", gson.toJson(current_list.toSet().toList()))


            current_service_list.clear()
            current_service_list.add(
                gson.fromJson(
                    sharedCommonPref.getvalue("service_data"),
                    type1
                )
            )


            if (sharedCommonPref.getvalue("cart_group_data").equals("")) {
                cart_list_data.add(Cart_DATA(current_service_list, current_list))

                sharedCommonPref.save("cart_group_data", gson.toJson(cart_list_data))

            } else {
                val json = sharedCommonPref.getvalue("cart_group_data")
                val type = object : TypeToken<ArrayList<Cart_DATA>>() {
                }.type
                cart_list_data = gson.fromJson(json, type)
                var isSelected: Boolean? = false
                for (grp in cart_list_data.indices) {
                    if (Gson().toJson(cart_list_data.get(grp).employee)
                            .equals(Gson().toJson(current_list))
                    ) {

                        isSelected = true
                        current_service_list =
                            cart_list_data.get(grp).service as ArrayList<Datum>

                        current_service_list.add(
                            gson.fromJson(
                                sharedCommonPref.getvalue("service_data"),
                                type1
                            )
                        );

                        val distinct_ser = Gson().toJson(current_service_list.toSet().toList())
                        val distinct_emp = Gson().toJson(current_list.toSet().toList())


                        cart_list_data.set(
                            grp,
                            Cart_DATA(
                                gson.fromJson(distinct_ser, serEmpType),
                                gson.fromJson(distinct_emp, serEmpType)
                            )
                        )


                    }
                }

                if (isSelected == false) {
                    for (grp in cart_list_data.indices) {
                        if (Gson().toJson(cart_list_data.get(grp).service)
                                .equals(Gson().toJson(current_service_list))
                        ) {

                            old_employee_list = (current_list)
                            isSelected = true
                            current_list =
                                cart_list_data.get(grp).employee as ArrayList<Datum>


                            for (grp in old_employee_list.indices) {
                                current_list.add(old_employee_list.get(grp))
                            }


                            val distinct_ser = Gson().toJson(current_service_list.toSet().toList())
                            val distinct_emp = Gson().toJson(current_list.toSet().toList())


                            cart_list_data.set(
                                grp,
                                Cart_DATA(
                                    gson.fromJson(distinct_ser, serEmpType),
                                    gson.fromJson(distinct_emp, serEmpType)
                                )
                            )


                        }
                    }
                }

                if (isSelected == false) {
                    cart_list_data.add(Cart_DATA(current_service_list, current_list))
                }

                sharedCommonPref.save("cart_group_data", gson.toJson(cart_list_data))

            }

            Log.v("group_data:", sharedCommonPref.getvalue("cart_group_data"))
            Toast.makeText(requireContext(), "Item Added to Cart!!", Toast.LENGTH_LONG).show()

        }



        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(MainRepository(ApiHelper(apiInterface)))
        ).get(MainViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(
            arrayListOf(), requireActivity(), current_list, sharedCommonPref,
            service_data!!
        )
        recyclerView.adapter = adapter
        adapter.itemSelectedListner = this
        viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "onCreate: ${it.responseCode}")

            adapter.addData(it.data!!)
        })

        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getEmployeeData()
    }

    override fun onItemSelected(user: Datum, isSlected: Boolean) {

        if (isSlected) {
            current_list.add(user)
        } else {
            current_list.remove(user)
        }

        if (current_list.isEmpty()) {
            tvAddCart.isEnabled = false
            tvAddCartParent.setBackgroundColor(resources.getColor(R.color.grey_400))
        } else {
            tvAddCart.isEnabled = true
            tvAddCartParent.setBackgroundColor(resources.getColor(R.color.green))
        }
        val distinct = current_list.toSet().toList()


        val gson = Gson()

        sharedCommonPref.save("CART_DATA", gson.toJson(distinct))
    }

}