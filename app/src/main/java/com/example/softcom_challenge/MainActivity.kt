package com.example.softcom_challenge

import ParentAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.francisco.projeto_final_PDMO.MyAdapter.OperacoesAdapter
import com.example.softcom_challenge.Adapter.ScrollStateHolder
import com.example.softcom_challenge.Models.Category
import com.example.softcom_challenge.Models.Product
import com.example.softcom_challenge.Models.Sectors


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ParentAdapter
    private fun hideSystemUI() {
        // Ativa o modo imersivo de tela
        // Para o modo "recuar", remova SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Esconde barra de status e barra de navegação
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN

                )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideSystemUI()

        var recyclerView_List: RecyclerView
        var scrollStateHolder: ScrollStateHolder
        val HorizontalList = findViewById<RecyclerView>(R.id.horizontal_recycler)
        val mLayoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)

        var Sectors_List:ArrayList<Sectors> = ArrayList()
        val Comedouros = Sectors(title = "Comedouros", image = R.drawable.food_icon)
        val Camas = Sectors(title = "Camas", image = R.drawable.bed_icon)
        val House = Sectors(title = "Casas", image = R.drawable.house_icon)
        val Toy = Sectors(title = "Brinquedos", image = R.drawable.toy_icon)

        Sectors_List.add(Toy)
        Sectors_List.add(Comedouros)
        Sectors_List.add(Camas)
        Sectors_List.add(House)

        HorizontalList.adapter = OperacoesAdapter(Sectors_List)
        HorizontalList.setLayoutManager(mLayoutManager)

        recyclerView_List = findViewById<RecyclerView>(R.id.RecyclerView_List)
        scrollStateHolder = ScrollStateHolder(savedInstanceState)
        adapter = ParentAdapter(scrollStateHolder)
        recyclerView_List.adapter = adapter
        recyclerView_List.layoutManager = LinearLayoutManager(this)
        LoadItems()

    }

    private fun LoadItems(){
        val Comedouros:ArrayList<Product> = ArrayList()
        val Brinquedos:ArrayList<Product> = ArrayList()
        val Casa:ArrayList<Product> = ArrayList()
        val Camas:ArrayList<Product> = ArrayList()

        val Category_lists = arrayListOf<Category>()
        val Cama_1 = Product("Caminha de cachorro", R.drawable.toy_icon,45.0,0.0,"Uma caminha perfeita para o seu cãozinho")
        val Cama_2 = Product("Caminha de cachorro",R.drawable.food_icon,45.50,67.47,"Uma caminha perfeita para o seu cãozinho")

        repeat(2){Comedouros.add(Cama_1)}
        repeat(2){Comedouros.add(Cama_2)}
        repeat(5){Brinquedos.add(Cama_1)}
        repeat(5){Casa.add(Cama_1)}
        repeat(5){Camas.add(Cama_1)}

        Category_lists.add(Category("Comedouros",Comedouros))
        Category_lists.add(Category("Brinquedos",Brinquedos))
        Category_lists.add(Category("Casa",Casa))
        Category_lists.add(Category("Camas",Camas))

        adapter.setItems(Category_lists)

    }
}