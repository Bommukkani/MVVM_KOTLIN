package com.example.myapplication.ui.main.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.ApiClient
import com.example.myapplication.data.api.ApiHelper
import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.Datum
import com.example.myapplication.data.repository.MainRepository
import com.example.myapplication.ui.base.MyViewModelFactory
import com.example.myapplication.ui.main.adapter.MainAdapter
import com.example.myapplication.ui.main.viewmodel.MainViewModel
import com.example.myapplication.utils.Shared_Common_Pref
import com.google.gson.Gson


class ServiceFragment : Fragment(), MainAdapter.ItemClickListner {
    private val TAG = "ServiceFragment"
    lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView


    lateinit var sharedCommonPref: Shared_Common_Pref
    val apiInterface: ApiService = ApiClient.getClient().create(ApiService::class.java)
    lateinit var adapter: MainAdapter
    lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(MainRepository(ApiHelper(apiInterface)))

        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedCommonPref= Shared_Common_Pref(requireContext())
        recyclerView = view.findViewById(R.id.recyclerview)
        frameLayout = view.findViewById(R.id.employee_container)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainAdapter(arrayListOf(),sharedCommonPref)

        recyclerView.adapter = adapter
        adapter.setItemClickListner(this)
        viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "onCreate: ${it.responseCode}")

            adapter.addData(it.data!!)
        })

        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getAllData()
    }

    override fun onItemClicked(user: Datum) {
        sharedCommonPref.save("service_data", Gson().toJson(user))

        val args = Bundle()
        args.putString("Username", user.name)
        args.putString("service_data", Gson().toJson(user))
        val employeeFragment = EmployeeFragment()
        val fragmentSimpleName = employeeFragment::class.java.simpleName as String
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        employeeFragment.arguments = args
        transaction.replace(R.id.employee_container, employeeFragment)
            .addToBackStack(null).commit()
    }


}