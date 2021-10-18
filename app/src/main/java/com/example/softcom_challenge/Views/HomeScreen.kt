package com.example.softcom_challenge.Views

import ParentAdapter
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.francisco.projeto_final_PDMO.MyAdapter.OperacoesAdapter
import com.example.softcom_challenge.Adapter.FilterAdapter
import com.example.softcom_challenge.Adapter.ScrollStateHolder
import com.example.softcom_challenge.Models.*
import com.example.softcom_challenge.R
import com.example.softcom_challenge.ViewModels.Functions
import java.util.*


@Suppress("UNREACHABLE_CODE")
class HomeScreen : Fragment() {
    lateinit var parentAdapter: ParentAdapter
    lateinit var filterAdapter: FilterAdapter
    private lateinit var HorizontalList: RecyclerView
    private lateinit var SearchViewBar: SearchView
    lateinit var scrollStateHolder: ScrollStateHolder
    lateinit var recyclerView_List: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scrollStateHolder = ScrollStateHolder(savedInstanceState)
        parentAdapter = ParentAdapter(scrollStateHolder)
        filterAdapter = FilterAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var v = inflater.inflate(R.layout.fragment_home_screen, container, false)
        try {
            HorizontalList = v.findViewById<RecyclerView>(R.id.RecyclerView_Horizontal)
            SearchViewBar = v.findViewById<SearchView>(R.id.SearchView)
        } catch (e: Exception) {
            println("Error in catch of Category recylerView  ${e}")
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_List = view?.findViewById<RecyclerView>(R.id.RecyclerView_Nested)!!
        recyclerView_List.adapter = parentAdapter
        recyclerView_List.layoutManager = LinearLayoutManager(context)
        Functions().LoadItemsRecycler(parentAdapter)

        Functions().LoadSectorsRecycler()
        HorizontalList.adapter = OperacoesAdapter(Sectors_List)
        HorizontalList.setLayoutManager(mLayoutManager)

        //Can I put this into a function?
        SearchViewBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                parentAdapter.setItems(Category_lists)
                parentAdapter!!.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                SearchResult_list.clear()
                val searchText = newText!!.toLowerCase(locale = Locale.getDefault())
                if(searchText.isNotEmpty()){
                    Category_lists.forEach {

                        for (i in it.products){
                            if(i.name.toLowerCase().contains(searchText)){
                                SearchResult_list.add(i)
                            }
                        }

                    }
                    recyclerView_List.adapter = filterAdapter
                    recyclerView_List.layoutManager = GridLayoutManager(context,2)
                    filterAdapter.setItem(SearchResult_list)
                    filterAdapter.notifyDataSetChanged()
                }else{
                    parentAdapter.setItems(Category_lists)
                    parentAdapter!!.notifyDataSetChanged()
                }
                return false
            }

        })
    }


}


