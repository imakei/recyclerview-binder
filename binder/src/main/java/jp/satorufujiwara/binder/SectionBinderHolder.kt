package jp.satorufujiwara.binder

import java.util.*

class SectionBinderHolder<S : Section, V : ViewType, VH> {

    private val sectionItemsList = mutableListOf<MutableList<Binder<V, VH>>>()

    fun getSectionIndex(section: S): Int {
        initSections(section)
        val sectionPosition = section.position()
        if (sectionPosition == 0) {
            return 0
        }
        var index = 0
        for (items in sectionItemsList
                .subList(0, sectionPosition)) {
            index += items.size
        }
        return index
    }

    fun getSectionSize(section: S): Int {
        initSections(section)
        return getSectionSize(section.position())
    }

    fun <B : Binder<V, VH>> add(section: S, item: B) {
        initSections(section)
        sectionItemsList[section.position()].add(item)
    }

    fun <B : Binder<V, VH>> insert(section: S, item: B,
                                   index: Int) {
        initSections(section)
        sectionItemsList[section.position()].add(index, item)
    }

    fun <B : Binder<V, VH>> remove(section: S, item: B) {
        initSections(section)
        sectionItemsList[section.position()].remove(item)
    }

    fun clear(section: S) {
        initSections(section)
        sectionItemsList[section.position()].clear()
    }

    fun getAllItem(section: S): MutableList<Binder<V, VH>> {
        initSections(section)
        return sectionItemsList[section.position()]
    }

    fun getItem(section: S, index: Int): Binder<V, VH>? {
        initSections(section)
        val list = sectionItemsList[section.position()]
        return if (list.size <= index) {
            null
        } else list[index]
    }

    fun isEmpty(section: S): Boolean {
        initSections(section)
        val list = sectionItemsList[section.position()]
        return list.isEmpty()
    }

    fun clear() {
        for (items in sectionItemsList) {
            items.clear()
        }
        sectionItemsList.clear()
    }

    private fun initSections(section: S) {
        val sectionPosition = section.position()
        if (sectionPosition < sectionItemsList.size) {
            return
        }
        for (i in 0..sectionPosition) {
            sectionItemsList.add(ArrayList())
        }
    }

    private fun getSectionSize(sectionPosition: Int): Int {
        return sectionItemsList[sectionPosition].size
    }


}
