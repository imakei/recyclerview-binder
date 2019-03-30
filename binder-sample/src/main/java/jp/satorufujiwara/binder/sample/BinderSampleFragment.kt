package jp.satorufujiwara.binder.sample


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife
import butterknife.InjectView
import jp.satorufujiwara.binder.Section
import jp.satorufujiwara.binder.recycler.RecyclerBinderAdapter

class BinderSampleFragment : Fragment() {

    @InjectView(R.id.recyclerView)
    internal var recyclerView: RecyclerView? = null

    private val adapter = RecyclerBinderAdapter<BinderSampleSection, BinderSampleViewType>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_binder_sample, container, false)
        ButterKnife.inject(this, v)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = adapter

        adapter.add<Binder<BinderSampleViewType, ViewHolder>>(BinderSampleSection.SECTION_1, DataBinder1(activity, "in Section1"))
        adapter.add<Binder<BinderSampleViewType, ViewHolder>>(BinderSampleSection.SECTION_1, DataBinder2(activity, "in Section1"))
        adapter.add<Binder<BinderSampleViewType, ViewHolder>>(BinderSampleSection.SECTION_1, DataBinder3(activity, "in Section1"))

        adapter.add<Binder<BinderSampleViewType, ViewHolder>>(BinderSampleSection.SECTION_3, DataBinder2(activity, "in Section3"))
        adapter.add<Binder<BinderSampleViewType, ViewHolder>>(BinderSampleSection.SECTION_3, DataBinder1(activity, "in Section3"))

        adapter.add<Binder<BinderSampleViewType, ViewHolder>>(BinderSampleSection.SECTION_2, DataBinder3(activity, "in Section2"))
        adapter.add<Binder<BinderSampleViewType, ViewHolder>>(BinderSampleSection.SECTION_2, DataBinder2(activity, "in Section2"))
        adapter.add<Binder<BinderSampleViewType, ViewHolder>>(BinderSampleSection.SECTION_2, DataBinder1(activity, "in Section2"))
    }

    internal enum class BinderSampleSection : Section {

        SECTION_1,
        SECTION_2,
        SECTION_3;


        override fun position(): Int {
            return ordinal
        }
    }

    companion object {

        fun newInstance(): BinderSampleFragment {
            return BinderSampleFragment()
        }
    }
}
