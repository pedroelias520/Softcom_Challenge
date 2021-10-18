
import android.graphics.Color
import android.graphics.Paint
import android.media.Image
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.softcom_challenge.Models.Product
import com.example.softcom_challenge.R
import org.w3c.dom.Text
import java.math.BigDecimal
import java.math.RoundingMode


class ChildAdapter : RecyclerView.Adapter<ChildAdapter.VH>() {

    private var items = listOf<Product>()

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

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {

        private val textView: TextView = view.findViewById(R.id.name_textview)
        private val priceView: TextView = view.findViewById(R.id.price_textview)
        private val oldPriceView: TextView = view.findViewById(R.id.oldPrice_textview)
        private val DiscountView: TextView = view.findViewById(R.id.discount_textview)
        private val deView:TextView = view.findViewById(R.id.de_textview)
        private val porView:TextView = view.findViewById(R.id.por_textview2)
        private val imageView:ImageView = view.findViewById(R.id.image_view_product)
        private val shopButton:ImageButton = view.findViewById(R.id.imageButton)
        init {
            view.setOnClickListener {
                it.isSelected = !it.isSelected
            }
        }

        fun bind(item: Product) {
            imageView.setImageResource(item.image)
            textView.text = item.name
            priceView.text = item.price.toString()
            oldPriceView.text = item.oldPrice.toString()
            oldPriceView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            oldPriceView.setTextColor(Color.parseColor("#FF0000"))
            val discount = ((item.oldPrice - item.price) * 100.0) / item.oldPrice

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
        }

    }
}

