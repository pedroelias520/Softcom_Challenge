package br.com.francisco.projeto_final_PDMO.MyAdapter

import ParentAdapter
import android.graphics.BitmapFactory
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softcom_challenge.Adapter.FilterAdapter
import com.example.softcom_challenge.Adapter.ScrollStateHolder
import com.example.softcom_challenge.Models.Category
import com.example.softcom_challenge.Models.Category_lists
import com.example.softcom_challenge.Models.SearchResult_list
import com.example.softcom_challenge.Models.Sectors
import com.example.softcom_challenge.R
import com.example.softcom_challenge.ViewModels.Functions
import com.example.softcom_challenge.Views.HomeScreen
import java.util.*
import kotlin.collections.ArrayList

class OperacoesAdapter(private val dataSet: ArrayList<Sectors>) :
    RecyclerView.Adapter<OperacoesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        var image: ImageView
        var buttonSlide: CardView = view.findViewById(R.id.button_slide)
        var context = view.context
        lateinit var filterAdapter: FilterAdapter
        lateinit var recyclerView:RecyclerView
        lateinit var scrollStateHolder: ScrollStateHolder

        init {
            try {
                recyclerView = view.findViewById<RecyclerView>(R.id.RecyclerView_Nested)!!
            } catch (e: Exception) {
                println("ERROR: FAILED TO GET RECYCLER VIEW: ${e}")
            }
            title = view.findViewById(R.id.TextView_Card)
            image = view.findViewById(R.id.ImageView_Card)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.button_slide_sectors, parent, false)
        try {
            ViewHolder(view).recyclerView = view.findViewById(R.id.RecyclerView_Nested)
        }catch (e:Exception){

        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].title
        holder.image.setImageResource(dataSet[position].image)
        holder.buttonSlide.setOnClickListener { name ->


            Category_lists.forEach {

                if (it.title == dataSet[position].title) {
                    SearchResult_list.addAll(it.products)
                }
                holder.filterAdapter = FilterAdapter(holder.context)
                holder.recyclerView.adapter = holder.filterAdapter
                holder.recyclerView.layoutManager = GridLayoutManager(holder.context, 2)
                holder.filterAdapter.setItem(SearchResult_list)
                holder.filterAdapter.notifyDataSetChanged()
            }

        }

    }


}