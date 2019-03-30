package jp.satorufujiwara.binder.recycler


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import jp.satorufujiwara.binder.Binder
import jp.satorufujiwara.binder.ViewType

abstract class RecyclerBinder<V : ViewType> protected constructor(context: Context, override val viewType: V) : Binder<V, RecyclerView.ViewHolder> {
    var context: Context? = null
        private set

    init {
        this.context = context
    }

    @LayoutRes
    abstract fun layoutResId(): Int

    abstract fun onCreateViewHolder(v: View?): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return onCreateViewHolder(LayoutInflater.from(context).inflate(layoutResId(), parent, false))
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        // no op
    }

    override fun onRemoved() {
        context = null
    }

}
