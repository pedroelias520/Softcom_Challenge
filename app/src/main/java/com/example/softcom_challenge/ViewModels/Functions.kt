package com.example.softcom_challenge.ViewModels

import ParentAdapter
import com.example.softcom_challenge.Adapter.FilterAdapter
import com.example.softcom_challenge.Models.*
import com.example.softcom_challenge.R

class Functions {

    fun LoadSectorsRecycler(){
        try {
            Sectors_List.add(Toy)
            Sectors_List.add(Comedouros)
            Sectors_List.add(Camas)
            Sectors_List.add(House)
            Sectors_List.add(Remedios)
        }catch (e:Exception){
            println("Eror in loading horizontal recycler: ${e}")
        }
    }
    fun LoadItemsRecycler(adapter:ParentAdapter){
        val Racao = Product("Ração Br4Dogs 20Kg Adulto", R.drawable.br4dogs,45.0,0.0,"Uma caminha perfeita para o seu cãozinho")
        val Cama = Product("Caminha de cachorro Br4dogs", R.drawable.br4dogs_bed,45.50,67.47,"Uma caminha perfeita para o seu cãozinho")
        val Brinquedo = Product("Bolinha de morder", R.drawable.br4dogs_mordedor,129.90,199.99,"Bolinha anti-stress")
        val Casa = Product("Casinha de cachorro azul", R.drawable.br4dogs_casa,399.99,349.99,"Casa tamanho médio")
        val Remedio = Product("Ivermectina 3mg", R.drawable.br4dogs_remedio,23.50,0.0,"Ivermectina 3mg")

        repeat(5){ Comedouros_list.add(Racao)}
        repeat(5){ Brinquedos_list.add(Brinquedo)}
        repeat(5){ Casa_list.add(Casa)}
        repeat(5){ Camas_list.add(Cama)}
        repeat(5){ Remedios_list.add(Remedio)}

         try {
             Category_lists.add(Category("Comedouros", Comedouros_list))
             Category_lists.add(Category("Brinquedos", Brinquedos_list))
             Category_lists.add(Category("Casa", Casa_list))
             Category_lists.add(Category("Camas", Camas_list))
             Category_lists.add(Category("Remedios", Remedios_list))
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
}

