
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

import androidx.recyclerview.widget.RecyclerView
import com.example.softcom_challenge.Adapter.FilterAdapter
import com.example.softcom_challenge.MainActivity
import com.example.softcom_challenge.Models.Product
import com.example.softcom_challenge.Models.Request
import com.example.softcom_challenge.Models.requestList
import com.example.softcom_challenge.R
import com.example.softcom_challenge.ViewModels.Functions
import com.example.softcom_challenge.Views.HomeScreen
import com.example.softcom_challenge.Views.SelectScreen
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


class ChildAdapter() : RecyclerView.Adapter<ChildAdapter.VH>() {

    private var items = listOf<Product>()
    private var functions = Functions()
    fun setItems(list: List<Product>) {
        this.items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        return VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.nested_adapter_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    //Carrega um item por vez
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position], holder)

        holder.itemClickable.setOnClickListener {
            holder.selectItem(items[position], holder)
        }

    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {

        var fragmentManager: FragmentManager = object : FragmentManager() {

        }
        private val textView: TextView = view.findViewById(R.id.name_textview)
        private val priceView: TextView = view.findViewById(R.id.price_textview)
        private val oldPriceView: TextView = view.findViewById(R.id.oldPrice_textview)
        private val DiscountView: TextView = view.findViewById(R.id.discount_textview)
        private val deView:TextView = view.findViewById(R.id.de_textview)
        private val porView:TextView = view.findViewById(R.id.por_textview2)
        private val imageView:ImageView = view.findViewById(R.id.image_view_product)
        private val shopButton:ImageButton = view.findViewById(R.id.imageButton)
        var itemClickable:View = view

        init {

        }

        fun selectItem(item: Product, holder: VH) {
            val bundle = Bundle()
            bundle.putString("Name",item.name)
            bundle.putInt("Image",item.image)
            bundle.putDouble("Price",item.price)
            bundle.putDouble("Old Price",item.oldPrice)
            bundle.putString("Description",item.Description)
            val discount = ((item.oldPrice - item.price) * 100.0) / item.oldPrice
            bundle.putDouble("Discount",discount)

            val transitionFragment = SelectScreen()
            transitionFragment.arguments = bundle
            try {

                    val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, transitionFragment)
                    manager.commit()

            }catch (e:Exception){
                println("ERROR: Page is not found| ${e}")
            }
        }
        //Pega o array e distribui os valores para os items
        fun bind(item: Product, holder: VH) {
            imageView.setImageResource(item.image)
            textView.text = item.name
            priceView.text = item.price.toString()
            oldPriceView.text = item.oldPrice.toString()
            oldPriceView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            oldPriceView.setTextColor(Color.parseColor("#FF0000"))
            val discount = ((item.oldPrice - item.price) * 100.0) / item.oldPrice

            val itemSelected = Product(item.name,item.image,item.price,item.oldPrice,item.Description)




            if (discount.isNaN() or discount.isInfinite()){
                DiscountView.isVisible = false
                deView.isVisible = false
                oldPriceView.isVisible = false
                porView.text = "Por"
            }
           else {
                deView.text = "De"
                porView.text = "Por"
                val discount_view = BigDecimal(discount).setScale(0,RoundingMode.HALF_EVEN)
                DiscountView.text = ("${discount_view.toString()}% OFF")
            }

            shopButton.setOnClickListener {
                val productSelected: Product = Product(item.name,item.image,item.price,item.oldPrice,item.Description)
                val totalPriceRequest = item.price * 1
                val dateNow = SimpleDateFormat("dd/M/yyyy").format(Date())
                val itemRequest: Request = Request(Functions().getRandomString(),productSelected,totalPriceRequest, "None",1,dateNow)
                requestList.add(itemRequest)

                Functions().showSnackBar(it.rootView, item.price.toString(), holder)
            }
        }


    }
}

