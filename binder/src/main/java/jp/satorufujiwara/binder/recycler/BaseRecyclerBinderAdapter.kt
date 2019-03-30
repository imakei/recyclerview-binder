package jp.satorufujiwara.binder.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.satorufujiwara.binder.Binder
import jp.satorufujiwara.binder.ViewType

open class BaseRecyclerBinderAdapter<V : ViewType, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private val lock = Any()
    private val binders = mutableListOf<Binder<V, VH>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return getItemByViewType(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)!!.onBindViewHolder(holder, position)
    }

    override fun onViewRecycled(holder: VH) {
        val position = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            val item = getItem(position)
            item?.onViewRecycled(holder)
        }
    }

    override fun getItemCount(): Int {
        return binders.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item?.viewType?.viewType() ?: RecyclerView.INVALID_TYPE
    }

    fun getItem(position: Int): Binder<V, VH>? {
        return if (binders.size <= position) {
            null
        } else binders[position]
    }

    private fun getItemByViewType(viewType: Int): Binder<V, VH> {
        for (item in binders) {
            if (item.viewType.viewType() == viewType) {
                return item
            }
        }
        throw IllegalStateException("binder doesn't exist in list.")
    }

    fun insert(`object`: Binder<V, VH>, index: Int) {
        synchronized(lock) {
            binders.add(index, `object`)
        }
    }

    fun remove(`object`: Binder<V, VH>) {
        synchronized(lock) {
            `object`.onRemoved()
            binders.remove(`object`)
        }
    }

    internal open fun clear() {
        synchronized(lock) {
            for (item in binders) {
                item.onRemoved()
            }
            binders.clear()
        }
    }

}
