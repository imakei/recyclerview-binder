package jp.satorufujiwara.binder.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BinderSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)
        FragmentActivityExt
                .setContentFragment(this, R.id.container, BinderSampleFragment.newInstance())
    }
}
