package tw.com.bluemobile.hbc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.models.BaseModel
import java.lang.reflect.Type

typealias viewHolder<T, U> = (Context, View, didSelectClosure<U>, selectedClosure<U>)-> T
typealias didSelectClosure<U> = ((U, idx: Int) -> Unit)?
typealias selectedClosure<U> = ((U) -> Boolean)?

class BaseList<T: BaseViewHolder<U>, U: BaseModel>(
    recyclerView: RecyclerView,
    cellResource: Int,
    viewHolderConstructor: viewHolder<T, U>,
    didSelect: didSelectClosure<U>,
    private val selected: selectedClosure<U>
) {
    //val adapter: BaseAdapter<T, U>

    init {
        //recyclerView = findViewById<RecyclerView>(resource)
        //adapter = BaseAdapter<T, U>(cellResource, viewHolderConstructor, didSelect, selected)
        //recyclerView.adapter = adapter
    }
}

open class BaseAdapter<T: BaseViewHolder<U>, U: BaseModel> (
    private val resource: Int,
    private val viewHolderConstructor: (Context, View)-> T
//    private val didSelect: didSelectClosure<U>,
//    private val selected: selectedClosure<U> /* = ((U) -> kotlin.Boolean)? */
) : RecyclerView.Adapter<T>() {

    var items: ArrayList<U> = arrayListOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val row: U = items[position]

        holder.bind(row, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewHolder: View = inflater.inflate(resource, parent, false)
        //return T(parent.context, viewHolder, list1CellDelegate)
        return viewHolderConstructor(parent.context, viewHolder)
    }
}

open class BaseViewHolder<U: BaseModel>(
    val context: Context,
    val viewHolder: View
//    val didSelect: didSelectClosure<U>,
//    val selected: selectedClosure<U>
) : RecyclerView.ViewHolder(viewHolder) {

    open fun bind(row: U, idx: Int) {

        viewHolder.setOnClickListener {
            //didSelect?.let { it1 -> it1(row, idx) }
            //list2CellDelegate?.cellClick(row)
        }

//        val isSelected = selected?.let { it(row) } == true
//        if (isSelected) {
//            val color: Int = ContextCompat.getColor(context, R.color.CELL_SELECTED)
//            //viewHolder.backgroundColor = color
//        }
    }
}