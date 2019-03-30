package jp.satorufujiwara.binder.sample


import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

import butterknife.ButterKnife
import butterknife.InjectView
import jp.satorufujiwara.binder.recycler.RecyclerBinder

class DataBinder2(activity: Activity, private val text: String) : RecyclerBinder<BinderSampleViewType>(activity, BinderSampleViewType.VIEW_TYPE_2) {

    override fun layoutResId(): Int {
        return R.layout.binder_data_2
    }

    override fun onCreateViewHolder(v: View): RecyclerView.ViewHolder {
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as ViewHolder
        holder.textSection!!.text = text
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @InjectView(R.id.text_section)
        internal var textSection: TextView? = null

        init {
            ButterKnife.inject(this, view)
        }
    }
}
