package com.example.softcom_challenge.Adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softcom_challenge.MainActivity
import com.example.softcom_challenge.Models.Product
import com.example.softcom_challenge.Models.Request
import com.example.softcom_challenge.Models.requestList
import com.example.softcom_challenge.R
import com.example.softcom_challenge.Views.HomeScreen
import com.example.softcom_challenge.Views.SelectScreen
import java.math.BigDecimal
import java.math.RoundingMode

class RequestAdapter(var context: Context): RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    var dataList = mutableListOf<Request>()
    var fragmentManager: FragmentManager = object : FragmentManager() {

    }
    fun setItem(data:ArrayList<Request>){
        this.dataList = data
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val titleTextView: TextView
        val totalPriceTextView: TextView
        val requestProductImageView: ImageView
        val removeButton: ImageView
        val dateTextView:TextView
        val totalQtdTextView:TextView
        init {
            titleTextView = view.findViewById(R.id.titleTextView)
            totalPriceTextView = view.findViewById(R.id.totalPriceTextView)
            requestProductImageView = view.findViewById(R.id.requestProdutctImageView)
            removeButton = view.findViewById(R.id.removeButton)
            dateTextView = view.findViewById(R.id.dateTextView)
            totalQtdTextView = view.findViewById(R.id.totalQtdTextView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.request_adapter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestAdapter.ViewHolder, position: Int) {
        var data = dataList[position]
        holder.titleTextView.text = data.product.name
        holder.totalPriceTextView.text = data.totalPrice.toString()
        holder.requestProductImageView.setImageResource(data.product.image)
        holder.dateTextView.text =  data.date
        holder.totalQtdTextView.text =  data.qtd.toString()

        holder.removeButton.setOnClickListener {
            requestList.remove(data)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun selectItem(item: Product, holder: RequestAdapter.ViewHolder) {
        val bundle = Bundle()
        bundle.putString("Name",item.name)
        bundle.putInt("Image",item.image)
        bundle.putDouble("Price",item.price)
        bundle.putDouble("Old Price",item.oldPrice)
        bundle.putString("Description",item.Description)
        val discount = ((item.oldPrice - item.price) * 100.0) / item.oldPrice
        bundle.putDouble("Discount",discount)

        val transitionFragment = SelectScreen()
        transitionFragment.arguments = bundle
        try {
            //HomeScreen().replaceFragment(transitionFragment)
            val manager = (holder.requestProductImageView.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,transitionFragment)
            manager.commit()
            //fragmentManager.beginTransaction().replace(R.id.fragmentContainer, HomeScreen()).commit()
            //MainActivity().replaceFragment(MainActivity().selectScreen)
        }catch (e:Exception){
            println("ERROR: Page is not found| ${e}")
        }
    }
}