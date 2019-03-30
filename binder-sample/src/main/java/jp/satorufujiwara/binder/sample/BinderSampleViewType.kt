package jp.satorufujiwara.binder.sample

import jp.satorufujiwara.binder.ViewType

enum class BinderSampleViewType : ViewType {

    VIEW_TYPE_1,
    VIEW_TYPE_2,
    VIEW_TYPE_3;

    override fun viewType(): Int {
        return ordinal
    }

}
