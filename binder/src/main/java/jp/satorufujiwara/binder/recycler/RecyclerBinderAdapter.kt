package jp.satorufujiwara.binder.recycler

import androidx.recyclerview.widget.RecyclerView

import jp.satorufujiwara.binder.Binder
import jp.satorufujiwara.binder.Section
import jp.satorufujiwara.binder.SectionBinderHolder
import jp.satorufujiwara.binder.ViewType

open class RecyclerBinderAdapter<S : Section, V : ViewType> : BaseRecyclerBinderAdapter<V, RecyclerView.ViewHolder>() {

    private val mSectionBinderHolder = SectionBinderHolder<S, V, RecyclerView.ViewHolder>()
    private val mLock = Any()
    private var notifyOnChange = true

    fun getSectionPosition(section: S): Int {
        return mSectionBinderHolder.getSectionIndex(section)
    }

    fun getSectionSize(section: S): Int {
        return mSectionBinderHolder.getSectionSize(section)
    }

    fun <B : Binder<V, RecyclerView.ViewHolder>> add(section: S, item: B) {
        insert(section, item, mSectionBinderHolder.getSectionSize(section))
    }

    fun <B : Binder<V, RecyclerView.ViewHolder>> addAll(section: S,
                                                        items: List<B>?) {
        insertAll(section, items, mSectionBinderHolder.getSectionSize(section))
    }

    fun <B : Binder<V, RecyclerView.ViewHolder>> addIfEmpty(section: S,
                                                            item: B) {
        if (mSectionBinderHolder.isEmpty(section)) {
            insert(section, item, mSectionBinderHolder.getSectionSize(section))
        }
    }

    fun <B : Binder<V, RecyclerView.ViewHolder>> insert(section: S, item: B,
                                                        index: Int) {
        synchronized(mLock) {
            mSectionBinderHolder.insert(section, item, index)
            insert(item, mSectionBinderHolder.getSectionIndex(section) + index)
            if (notifyOnChange) {
                notifyDataSetChanged()
            }
        }
    }

    fun <B : Binder<V, RecyclerView.ViewHolder>> insertAll(section: S,
                                                           items: List<B>?, index: Int) {
        synchronized(mLock) {
            val sectionIndex = mSectionBinderHolder.getSectionIndex(section)
            var insertPosition = index
            items?.forEach {
                mSectionBinderHolder.insert<Binder<V, RecyclerView.ViewHolder>>(section, it, index)
                insert(it, sectionIndex + insertPosition)
                insertPosition++
            }
            if (notifyOnChange) {
                notifyDataSetChanged()
            }
        }
    }

    fun <B : Binder<V, RecyclerView.ViewHolder>> remove(section: S,
                                                        item: B) {
        synchronized(mLock) {
            mSectionBinderHolder.remove(section, item)
            remove(item)
            if (notifyOnChange) {
                notifyDataSetChanged()
            }
        }
    }

    fun <B : Binder<V, RecyclerView.ViewHolder>> removeAll(section: S,
                                                           items: List<B>) {
        synchronized(mLock) {
            for (item in items) {
                mSectionBinderHolder.remove<Binder<V, RecyclerView.ViewHolder>>(section, item)
                remove(item)
            }
            if (notifyOnChange) {
                notifyDataSetChanged()
            }
        }
    }

    fun removeAll(section: S) {
        synchronized(mLock) {
            val removeItems = mSectionBinderHolder
                    .getAllItem(section)
            for (removeItem in removeItems) {
                remove(removeItem)
            }
            mSectionBinderHolder.clear(section)
            if (notifyOnChange) {
                notifyDataSetChanged()
            }
        }
    }

    fun <B : Binder<V, RecyclerView.ViewHolder>> replaceAll(section: S,
                                                            item: B) {
        synchronized(mLock) {
            val removeItems = mSectionBinderHolder
                    .getAllItem(section)
            for (removeItem in removeItems) {
                remove(removeItem)
            }
            removeItems.clear()
            val moduleIndex = mSectionBinderHolder.getSectionIndex(section)
            mSectionBinderHolder.add(section, item)
            insert(item, moduleIndex)
            if (notifyOnChange) {
                notifyDataSetChanged()
            }
        }
    }

    fun <B : Binder<V, RecyclerView.ViewHolder>> replaceAll(section: S,
                                                            items: List<B>) {
        synchronized(mLock) {
            val removeItems = mSectionBinderHolder
                    .getAllItem(section)
            for (removeItem in removeItems) {
                remove(removeItem)
            }
            removeItems.clear()
            val sectionIndex = mSectionBinderHolder.getSectionIndex(section)
            var insertPosition = 0
            for (item in items) {
                mSectionBinderHolder.insert<Binder<V, RecyclerView.ViewHolder>>(section, item, insertPosition)
                insert(item, sectionIndex + insertPosition)
                insertPosition++
            }
            if (notifyOnChange) {
                notifyDataSetChanged()
            }
        }
    }

    fun getAllItem(section: S): List<Binder<V, RecyclerView.ViewHolder>> {
        return mSectionBinderHolder.getAllItem(section)
    }

    fun getItem(section: S, index: Int): Binder<V, RecyclerView.ViewHolder>? {
        return mSectionBinderHolder.getItem(section, index)
    }

    fun isEmpty(section: S): Boolean {
        return mSectionBinderHolder.isEmpty(section)
    }

    public override fun clear() {
        super.clear()
        mSectionBinderHolder.clear()
    }

    fun setNotifyOnChange(notifyOnChange: Boolean) {
        this.notifyOnChange = notifyOnChange
    }
}
