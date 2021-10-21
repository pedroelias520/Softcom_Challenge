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
import com.example.softcom_challenge.R
import com.example.softcom_challenge.Views.HomeScreen
import com.example.softcom_challenge.Views.SelectScreen
import java.math.BigDecimal
import java.math.RoundingMode

class FilterAdapter(var context: Context): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    var dataList = mutableListOf<Product>()
    var fragmentManager: FragmentManager = object : FragmentManager() {

    }
    fun setItem(data:ArrayList<Product>){
        this.dataList = data
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView
        val priceView: TextView
        val oldPriceView: TextView
        val DiscountView: TextView
        val deView:TextView
        val porView:TextView
        var imageView:ImageView
        val shopButton:ImageButton
        val itemClickable: View = view
        init {
            textView = view.findViewById(R.id.name_textview)
            priceView = view.findViewById(R.id.price_textview)
            oldPriceView = view.findViewById(R.id.oldPrice_textview)
            DiscountView = view.findViewById(R.id.discount_textview)
            deView= view.findViewById(R.id.de_textview)
            porView = view.findViewById(R.id.por_textview2)
            imageView = view.findViewById(R.id.image_view_product)
            shopButton = view.findViewById(R.id.imageButton)


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.nested_adapter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        var data = dataList[position]
        holder.textView.text = data.name
        holder.priceView.text = data.price.toString()
        holder.oldPriceView.text = data.oldPrice.toString()
        holder.deView.text = "De"
        holder.porView.text =  "Por"
        holder.imageView.setImageResource(data.image)
        val discount = ((data.oldPrice - data.price) * 100) / data.oldPrice

        if(discount.isNaN() or discount.isInfinite()){
            holder.DiscountView.isVisible = false
            holder.deView.isVisible = false
            holder.oldPriceView.isVisible = false
            holder.porView.text = "Por"
        }else {
            holder.deView.text = "De"
            holder.porView.text = "Por"
            val discount_view = BigDecimal(discount).setScale(0,RoundingMode.HALF_EVEN)
            holder.DiscountView.text = ("${discount_view.toString()}% OFF")
        }

        holder.itemClickable.setOnClickListener {
            selectItem(dataList[position],holder)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun selectItem(item: Product, holder: FilterAdapter.ViewHolder) {
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
            val manager = (holder.imageView.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,transitionFragment)
            manager.commit()
            //fragmentManager.beginTransaction().replace(R.id.fragmentContainer, HomeScreen()).commit()
            //MainActivity().replaceFragment(MainActivity().selectScreen)
        }catch (e:Exception){
            println("ERROR: Page is not found| ${e}")
        }
    }
}