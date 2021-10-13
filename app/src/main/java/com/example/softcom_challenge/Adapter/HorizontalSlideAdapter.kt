package br.com.francisco.projeto_final_PDMO.MyAdapter

import android.graphics.BitmapFactory
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softcom_challenge.Models.Sectors
import com.example.softcom_challenge.R

class OperacoesAdapter(private val dataSet: ArrayList<Sectors>):
    RecyclerView.Adapter<OperacoesAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        var image: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            title = view.findViewById(R.id.TextView_Card)
            image = view.findViewById(R.id.ImageView_Card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.button_slide_sectors, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].title
        holder.image.setImageResource(dataSet[position].image)
    }
}