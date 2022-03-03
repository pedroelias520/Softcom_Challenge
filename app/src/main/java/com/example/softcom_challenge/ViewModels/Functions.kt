package com.example.softcom_challenge.ViewModels

import ParentAdapter
import android.graphics.Color
import android.view.Gravity
import android.view.View
import com.example.softcom_challenge.Adapter.FilterAdapter
import com.example.softcom_challenge.Models.*
import com.example.softcom_challenge.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import android.view.LayoutInflater
import android.widget.*
import androidx.core.view.setMargins
import androidx.fragment.app.FragmentActivity
import com.example.softcom_challenge.MainActivity
import com.example.softcom_challenge.Views.RequestScreen
import com.example.softcom_challenge.Views.SelectScreen
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class Functions {

    fun LoadSectorsRecycler(){
        try {
            ExposedDB.Sectors.selectAll().forEach {
                Sectors_List.add(Sectors(title=it[ExposedDB.Sectors.title], image = it[ExposedDB.Sectors.image]))
            }

        }catch (e:Exception){
            println("Eror in loading horizontal recycler: ${e}")
        }
    }
    fun LoadItemsRecycler(adapter:ParentAdapter){
         try {
             Category_lists.add(Category("Comedouros", ExposedDB().loadProductsRecycler("Comedouros")))
             TempCategoryLists.addAll(Category_lists)
             adapter.setItems(Category_lists)
         }catch (e:Exception){
             println("Eror in loading nested recycler: ${e}")
         }
    }

    fun SearchItemsFilter(adapter: ParentAdapter,filter:String){
        for(i in 0..Category_lists.size){
            if (Category_lists[i].title != filter){
                Category_lists.removeAt(i)
            }
        }
        adapter.setItems(Category_lists)
    }

    fun LoadFilterRecycler(adapter: FilterAdapter, list:ArrayList<Product>){
        try {
            adapter.setItem(list)
        }catch (e:Exception){
            println("Error into load filter recycler view: ${e}")
        }
    }

    fun getTotalPriceWishes(): Double {
        var totalValue: Double = 0.0
        if(requestList.size > 0){
            for (i in 0..(requestList.size-1)){
                totalValue =+ requestList[i].totalPrice
            }
        }
        return totalValue
    }

    fun getRandomString() : String {
        val allowedChars = ('0'..'9')
        val one = (1..4).map {allowedChars.random()}.joinToString("")
        val two = (1..4).map {allowedChars.random()}.joinToString("")
        val three = (1..4).map {allowedChars.random()}.joinToString("")
        val four = (1..4).map {allowedChars.random()}.joinToString("")
        val id = one +" "+two+" "+three+" "+four

        if(requestList.size != 0 ){
            for (i in 0..(requestList.size-1)){
                if(id == requestList[i].id){
                    getRandomString()
                }
            }
        }

        return id
    }

    fun showSnackBar(view: View, price:String, holder:ChildAdapter.VH){
        val inflater: LayoutInflater = LayoutInflater.from(view.context)

        val snackbar = Snackbar.make(view.findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
        val height = 150

        val snackView: View = inflater.inflate(R.layout.custom_snack_bar, null)
        val snackBarTextView = snackView.findViewById<TextView>(R.id.PriceSnackBarText)
        val snackBarButton = snackView.findViewById<TextView>(R.id.SeeShopCard)

        snackBarTextView.text = price
        snackBarButton.setOnClickListener {
            val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, RequestScreen())
            manager.commit()
        }


        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarView = snackbar.view as Snackbar.SnackbarLayout
        val parentParams = snackBarView.layoutParams as FrameLayout.LayoutParams



        parentParams.bottomMargin = 100
        parentParams.gravity = Gravity.BOTTOM
        parentParams.height = height
        parentParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        snackBarView.layoutParams = parentParams
        snackBarView.addView(snackView, 1)
        snackbar.show()
    }

    fun showSnackBarEndShop(view: View, price:String){
        val inflater: LayoutInflater = LayoutInflater.from(view.context)

        val snackbar = Snackbar.make(view.findViewById(R.id.RequestRecyclerView), "", Snackbar.LENGTH_LONG)
        val marginFromSides = 0
        val height = 150

        val snackView: View = inflater.inflate(R.layout.custom_snack_bar, null)
        val snackBarTextView = snackView.findViewById<TextView>(R.id.PriceSnackBarText)
        val snackBarButton = snackView.findViewById<TextView>(R.id.SeeShopCard)
        snackBarButton.text = price
        snackBarTextView.text = ""

        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarView = snackbar.view as Snackbar.SnackbarLayout
        val parentParams = snackBarView.layoutParams as FrameLayout.LayoutParams
        parentParams.setMargins(marginFromSides, marginFromSides, marginFromSides, marginFromSides)

        parentParams.height = height
        parentParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        //snackBarView.layoutParams = parentParams

        snackBarView.addView(snackView, 1)
        snackbar.show()
    }

    fun showSnackBarSelectItem(view: View, price:String){
        val inflater: LayoutInflater = LayoutInflater.from(view.context)

        val snackbar = Snackbar.make(view.findViewById(R.id.SelectScreenLayout), "", Snackbar.LENGTH_LONG)
        val marginFromSides = 50
        val height = 150

        val snackView: View = inflater.inflate(R.layout.custom_snack_bar, null)
        val snackBarTextView = snackView.findViewById<TextView>(R.id.PriceSnackBarText)
        val snackBarButton = snackView.findViewById<TextView>(R.id.SeeShopCard)
        snackBarButton.text = price
        snackBarTextView.text = ""

        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarView = snackbar.view as Snackbar.SnackbarLayout
        val parentParams = snackBarView.layoutParams as FrameLayout.LayoutParams
        parentParams.setMargins(0, 0, 0, marginFromSides)

        parentParams.height = height
        parentParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        //snackBarView.layoutParams = parentParams

        snackBarView.addView(snackView, 1)
        snackbar.show()
    }

}

