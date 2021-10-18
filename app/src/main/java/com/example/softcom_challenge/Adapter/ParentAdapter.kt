

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softcom_challenge.Adapter.ScrollStateHolder
import com.example.softcom_challenge.Models.Category
import com.example.softcom_challenge.R



class ParentAdapter(private val scrollStateHolder: ScrollStateHolder) :
    RecyclerView.Adapter<ParentAdapter.VH>() {

    private var items = listOf<Category>()

    fun setItems(list: List<Category>) {
        this.items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.nested_adapter_list,
            parent, false
        )
        val vh = VH(view, scrollStateHolder)
        vh.onCreated()
        return vh
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBound(items[position])
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
    }

    class VH(view: View, private val scrollStateHolder: ScrollStateHolder) :
        RecyclerView.ViewHolder(view), ScrollStateHolder.ScrollStateKeyProvider {

        private val titleTextView: TextView = view.findViewById(R.id.nestedTextView)
        private val recyclerView: RecyclerView = view.findViewById(R.id.nestedRecyclerView)
        private val layoutManager = LinearLayoutManager(
            view.context,
            RecyclerView.HORIZONTAL, false
        )
        private val adapter = ChildAdapter()

        private var currentItem: Category? = null

        override fun getScrollStateKey(): String? = currentItem?.title

        fun onCreated() {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator?.changeDuration = 0
            scrollStateHolder.setupRecyclerView(recyclerView, this)
        }

        fun onBound(item: Category) {
            currentItem = item
            titleTextView.text = item.title
            adapter.setItems(item.products)
            scrollStateHolder.restoreScrollState(recyclerView, this)
        }

        fun onRecycled() {
            scrollStateHolder.saveScrollState(recyclerView, this)
            currentItem = null
        }

    }
}

