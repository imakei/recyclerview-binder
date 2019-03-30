package jp.satorufujiwara.binder

import android.view.ViewGroup

interface Binder<V : ViewType, VH> {

    val viewType: V

    fun onCreateViewHolder(parent: ViewGroup): VH

    fun onBindViewHolder(viewHolder: VH, position: Int)

    fun onViewRecycled(holder: VH)

    fun onRemoved()

}
