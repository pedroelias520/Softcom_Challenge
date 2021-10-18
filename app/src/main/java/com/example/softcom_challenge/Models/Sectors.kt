package com.example.softcom_challenge.Models

import android.graphics.BitmapFactory
import android.media.Image
import android.widget.ImageView
import com.example.softcom_challenge.R

class Sectors (
    var title:String,
    var image: Int
)
var Sectors_List:ArrayList<Sectors> = ArrayList()
var Comedouros = Sectors(title = "Comedouros", image = R.drawable.food_icon)
var Camas = Sectors(title = "Camas", image = R.drawable.bed_icon)
var House = Sectors(title = "Casinhas", image = R.drawable.house_icon)
var Toy = Sectors(title = "Brinquedos", image = R.drawable.toy_icon)
var Remedios = Sectors(title = "Remédios", image = R.drawable.ic_baseline_healing)