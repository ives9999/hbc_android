package tw.com.bluemobile.hbc.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.setImage
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
    val adapter: BaseAdapter<T, U>

    init {
        //recyclerView = findViewById<RecyclerView>(resource)
        adapter = BaseAdapter<T, U>(cellResource, viewHolderConstructor, didSelect, selected)
        recyclerView.adapter = adapter
    }

    fun setRows(rows: ArrayList<U>, isRefresh: Boolean = true) {
        adapter.rows = rows
        if (isRefresh) {
            adapter.notifyDataSetChanged()
        }
    }
}

open class BaseAdapter<T: BaseViewHolder<U>, U: BaseModel> (
    private val resource: Int,
    private val viewHolderConstructor: (Context, View, didSelectClosure<U>, selectedClosure<U>)-> T,
    private val didSelect: didSelectClosure<U>,
    private val selected: selectedClosure<U> /* = ((U) -> kotlin.Boolean)? */
) : RecyclerView.Adapter<T>() {

    var rows: ArrayList<U> = arrayListOf()

    var onEditClick: ((Int) -> Unit)? = null

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val row: U = rows[position]

        holder.bind(row, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(resource, parent, false)
        //return T(parent.context, viewHolder, list1CellDelegate)
        val viewHolder = viewHolderConstructor(parent.context, view, didSelect, selected)
        viewHolder.onEditClick = onEditClick
        return viewHolder
    }
}

open class BaseViewHolder<U: BaseModel>(
    val context: Context,
    val view: View,
    val didSelect: didSelectClosure<U>,
    val selected: selectedClosure<U>
) : RecyclerView.ViewHolder(view) {

    var onEditClick: ((Int) -> Unit)? = null

    open fun bind(row: U, idx: Int) {

        view.setOnClickListener {
            didSelect?.let { it1 -> it1(row, idx) }
            //list2CellDelegate?.cellClick(row)
        }

//        val isSelected = selected?.let { it(row) } == true
//        if (isSelected) {
//            val color: Int = ContextCompat.getColor(context, R.color.CELL_SELECTED)
//            //viewHolder.backgroundColor = color
//        }
    }

    fun setIV(res: Int, name: String) {
        view.findViewById<ImageView>(res) ?. let {
            it.setImage(name)
        }
    }

    fun setTV(res: Int, value: String) {
        view.findViewById<TextView>(res) ?. let {
            it.setText(value)
        }
    }
}