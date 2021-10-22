package com.example.softcom_challenge.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softcom_challenge.Adapter.RequestAdapter
import com.example.softcom_challenge.Models.requestList
import com.example.softcom_challenge.R
import com.example.softcom_challenge.ViewModels.Functions

class RequestScreen : Fragment() {
    lateinit var requestAdapter: RequestAdapter
    lateinit var functions: Functions
    private lateinit var requestRecyclerView:RecyclerView
    lateinit var endShopButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestAdapter = RequestAdapter(requireContext())
        functions = Functions()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_request_screen, container, false)
        try{
            requestRecyclerView = view.findViewById(R.id.RequestRecyclerView)
            endShopButton = view.findViewById(R.id.endShopButton)
        }catch (e:Exception){
            println("ERROR: Recycler View not founded")
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestRecyclerView.adapter = requestAdapter
        requestRecyclerView.layoutManager = LinearLayoutManager(context)
        requestAdapter.setItem(requestList)
        requestAdapter.notifyDataSetChanged()

        val priceTextView:TextView = view.findViewById(R.id.priceTextView)
        if(functions.getTotalPriceWishes() == 0.0){
            endShopButton.isEnabled = false
            priceTextView.text = functions.getTotalPriceWishes().toString()
        }
        else{
            priceTextView.text = functions.getTotalPriceWishes().toString()
        }

    }

}