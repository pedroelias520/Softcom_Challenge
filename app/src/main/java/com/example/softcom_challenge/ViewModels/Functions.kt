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
        val Cama_1 = Product("Ração BomDog", R.drawable.ic_baseline_toys,45.0,0.0,"Uma caminha perfeita para o seu cãozinho")
        val Cama_2 = Product("Caminha de cachorro", R.drawable.ic_baseline_king_bed,45.50,67.47,"Uma caminha perfeita para o seu cãozinho")

        repeat(2){ Comedouros_list.add(Cama_1)}
        repeat(2){ Comedouros_list.add(Cama_2)}
        repeat(5){ Brinquedos_list.add(Cama_1)}
        repeat(5){ Casa_list.add(Cama_1)}
        repeat(5){ Camas_list.add(Cama_1)}
        repeat(5){ Remedios_list.add(Cama_1)}

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
}

