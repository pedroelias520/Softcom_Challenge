package com.example.softcom_challenge.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.softcom_challenge.Models.Product
import com.example.softcom_challenge.Models.Request
import com.example.softcom_challenge.Models.requestList
import com.example.softcom_challenge.R
import com.example.softcom_challenge.ViewModels.Functions
import org.w3c.dom.Text
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


class SelectScreen : Fragment() {

    lateinit var functions : Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        functions = Functions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_screen, container, false)
        val titleView:TextView = view.findViewById(R.id.titleView)
        val imageView:ImageView = view.findViewById(R.id.productView)
        val discountView:TextView = view.findViewById(R.id.discountCardView)
        val recyclerDescriptionView:TextView = view.findViewById(R.id.descriptionRecycler)
        val addButton: Button = view.findViewById(R.id.addButton)
        val minusButton: ImageView = view.findViewById(R.id.minusButton)
        val plusutton: ImageView = view.findViewById(R.id.plusButton)
        val qtdTextView: TextView = view.findViewById(R.id.qtdTextView)
        val obsEditText: EditText= view.findViewById(R.id.ObsEditView)
        var QtdNumber:Int = 1


        val args = this.arguments
        val nameProduct = args?.getString("Name")
        val imageProduct = args?.getInt("Image")
        val priceProduct = args?.getDouble("Price")
        val oldpriceProduct = args?.getDouble("Old Price")
        val descriptionProduct = args?.getString("Description")
        val discountProduct = args?.getDouble("Discount")
        val categoryProduct = args?.getDouble("Category")
        qtdTextView.text = QtdNumber.toString()

        titleView.text = nameProduct.toString()
        if (imageProduct != null) {
            imageView.setImageResource(imageProduct)
        }
        addButton.text = "ADICIONAR ${priceProduct.toString()}"
        if (discountProduct != null) {
            if (discountProduct.isNaN() or discountProduct.isInfinite()){
                discountView.isVisible = false
            }
            else {
                val discount_view = BigDecimal(discountProduct).setScale(0, RoundingMode.HALF_EVEN)
                discountView.text = ("${discount_view.toString()}% OFF")
            }
        }
        recyclerDescriptionView.text = descriptionProduct.toString()

        plusutton.setOnClickListener {
            QtdNumber = QtdNumber + 1
            qtdTextView.text = QtdNumber.toString()
        }

        minusButton.setOnClickListener {
            if(QtdNumber>1){
                QtdNumber = QtdNumber - 1
                qtdTextView.text = QtdNumber.toString()
            }

        }

        addButton.setOnClickListener {
            val productSelected: Product = Product(nameProduct.toString(),imageProduct.toString().toInt(),categoryProduct.toString(),priceProduct.toString().toDouble(),oldpriceProduct.toString().toDouble(),descriptionProduct.toString())
            val totalPriceRequest = priceProduct.toString().toDouble() * qtdTextView.text.toString().toDouble()
            val dateNow = SimpleDateFormat("dd/M/yyyy").format(Date())
            val itemRequest: Request = Request(functions.getRandomString(),productSelected,totalPriceRequest, obsEditText.text.toString(),qtdTextView.text.toString().toInt(),dateNow)
            requestList.add(itemRequest)
            Functions().showSnackBarSelectItem(view.rootView,"Adicionado ao carrinho!")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}