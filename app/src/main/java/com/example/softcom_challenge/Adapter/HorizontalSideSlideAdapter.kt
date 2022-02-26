package com.example.softcom_challenge.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.francisco.projeto_final_PDMO.MyAdapter.OperacoesAdapter
import com.example.softcom_challenge.Models.Category_lists
import com.example.softcom_challenge.Models.SearchResult_list
import com.example.softcom_challenge.Models.Sectors
import com.example.softcom_challenge.Models.Sectors_List
import com.example.softcom_challenge.R
import com.example.softcom_challenge.ViewModels.ExposedDB
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class HorizontalSideSlideAdapter(private val dataSet: ExposedDB.Sectors, val rootView: View) :
        RecyclerView.Adapter<OperacoesAdapter.ViewHolder>(){

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val title: TextView
        var image: ImageView
        var buttonSlide: CardView = view.findViewById(R.id.button_slide)
        var context = view.context
        lateinit var filterAdapter: FilterAdapter
        var recyclerView = view.findViewById<RecyclerView>(R.id.RecyclerView_Nested)
        lateinit var scrollStateHolder: ScrollStateHolder

        init {
            title = view.findViewById(R.id.TextView_Card)
            image = view.findViewById(R.id.ImageView_Card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperacoesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.button_slide_sectors, parent, false)
        return OperacoesAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OperacoesAdapter.ViewHolder, position: Int) {

        holder.title.text = Sectors_List[position].title
        holder.image.setImageResource(Sectors_List[position].image)
        holder.buttonSlide.setOnClickListener { name ->

            SearchResult_list.clear()
            Category_lists.forEach {
                if (it.title == Sectors_List[position].title) {
                    SearchResult_list.addAll(it.products)
                }
                holder.recyclerView = rootView.findViewById(R.id.RecyclerView_Nested)
                holder.filterAdapter = FilterAdapter(holder.context)
                holder.recyclerView.adapter = holder.filterAdapter
                holder.recyclerView.layoutManager = GridLayoutManager(holder.context, 2)
                holder.filterAdapter.setItem(SearchResult_list)
                holder.filterAdapter.notifyDataSetChanged()
            }

        }
    }

    override fun getItemCount(): Int {
        return Sectors_List.size
    }

}