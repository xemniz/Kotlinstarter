package ru.xmn.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import ru.xmn.common.extensions.inflate
import ru.xmn.common.ui.adapter.AutoUpdatableAdapter
import kotlin.properties.Delegates


class BaseAdapter : RecyclerView.Adapter<BaseAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var items: List<Item> by Delegates.observable(emptyList()) { _, oldValue, newValue ->
        autoNotify<Item>(oldValue, newValue, { item1, item2 -> item1.compare(item2) })
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(viewType))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getInstanceForPosition(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    private fun getLayoutIdForPosition(position: Int) = getInstanceForPosition(position).layoutId()

    private fun getInstanceForPosition(position: Int): Item = items[position]

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(instanceForPosition: Item) {
            instanceForPosition.bindOn(view)
        }
    }

    abstract class Item {
        abstract fun bindOn(view: View)
        abstract fun layoutId(): Int
        abstract fun compare(anotherItem: Item): Boolean
    }
}