package com.example.softcom_challenge.Models

class Request (val id:String, val product: Product,val totalPrice: Double,val observation:String,val qtd:Int,val date:String)

var requestList = ArrayList<Request>()