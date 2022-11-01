package tw.com.bluemobile.hbc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.models.BaseModel


//class BaseList<T: BaseViewHolder<U>, U: BaseModel>(
//    recyclerView: RecyclerView,
//    cellResource: Int,
//    viewHolderConstructor: viewHolder<T>
//) {
//    val adapter: BaseAdapter<T, U>
//
//    init {
//        adapter = BaseAdapter<T, U>(cellResource, viewHolderConstructor)
//        recyclerView.adapter = adapter
//    }
//
//    fun setRows(rows: ArrayList<U>, isRefresh: Boolean = true) {
//        adapter.setRows(rows)
//        if (isRefresh) {
//            adapter.notifyDataSetChanged()
//        }
//    }
//}

typealias viewHolder<T> = (Context, View)-> T

open class BaseAdapter<T: BaseViewHolder<U>, U: BaseModel> (
    open val resource: Int,
    open val viewHolderConstructor: viewHolder<T>
) : RecyclerView.Adapter<T>() {

    private var rows: ArrayList<U> = arrayListOf()

    var onRowClick: ((Int) -> Unit)? = null

    var onEditClick: ((Int) -> Unit)? = null
    var onDeleteClick: ((Int) -> Unit)? = null
    var onRefreshClick: (() -> Unit)? = null

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val row: U = rows[position]

        holder.bind(row, position)

        holder.setOnRowClickListener(position, onRowClick)

        holder.setOnEditClickListener(position, onEditClick)
        holder.setOnDeleteClickListener(position, onDeleteClick)
        holder.setOnRefreshClickListener(onRefreshClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(resource, parent, false)
        val viewHolder = viewHolderConstructor(parent.context, view)
        return viewHolder
    }

    fun setRows(rows: ArrayList<U>) {
        this.rows = rows
    }
}

open class BaseViewHolder<U: BaseModel>(
    val context: Context,
    val view: View,
) : RecyclerView.ViewHolder(view) {

    open fun bind(row: U, idx: Int) {

        setTV(R.id.noTV, (idx + 1).toString())
        setTV(R.id.typeTV, row.name)

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

    fun setOnRowClickListener(idx: Int, onRowClickListener: ((Int) -> Unit)?) {
        view.setOnClickListener {
            onRowClickListener?.invoke(idx)
        }
    }

    fun setOnEditClickListener(idx: Int, onEditClick: ((Int) -> Unit)?) {
        view.findViewById<ImageView>(R.id.editIV) ?. let {
            it.setOnClickListener {
                onEditClick?.invoke(idx)
            }
        }
    }

    fun setOnDeleteClickListener(idx: Int, onDeleteClick: ((Int) -> Unit)?) {
        view.findViewById<ImageView>(R.id.deleteIV) ?. let {
            it.setOnClickListener {
                onDeleteClick?.invoke(idx)
            }
        }
    }

    fun setOnRefreshClickListener(onRefreshClick: (() -> Unit)?) {
        view.findViewById<ImageView>(R.id.refreshIV) ?. let {
            it.setOnClickListener {
                onRefreshClick?.invoke()
            }
        }
    }
}