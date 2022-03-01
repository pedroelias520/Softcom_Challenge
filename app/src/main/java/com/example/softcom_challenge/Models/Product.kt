package com.example.softcom_challenge.Models

import android.widget.ImageView

class Product(
    val name:String, val image: Int,val category: String,
    val price:Double, val oldPrice:Double, val Description:String)


var Comedouros_list:ArrayList<Product> = ArrayList()
var Brinquedos_list:ArrayList<Product> = ArrayList()
var Casa_list:ArrayList<Product> = ArrayList()
var Camas_list:ArrayList<Product> = ArrayList()
var Remedios_list:ArrayList<Product> = ArrayList()
var SearchResult_list: ArrayList<Product> = ArrayList()