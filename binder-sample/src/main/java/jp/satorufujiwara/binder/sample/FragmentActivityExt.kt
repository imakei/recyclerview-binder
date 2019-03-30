package jp.satorufujiwara.binder.sample

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object FragmentActivityExt {

    fun setContentFragment(ext: FragmentActivity, containerViewId: Int,
                           fragment: Fragment): Fragment {
        val f = ext.supportFragmentManager.findFragmentById(containerViewId)
        if (f != null) {
            return f
        }
        ext.supportFragmentManager.beginTransaction().add(containerViewId, fragment).commit()
        return fragment
    }

}//extension
