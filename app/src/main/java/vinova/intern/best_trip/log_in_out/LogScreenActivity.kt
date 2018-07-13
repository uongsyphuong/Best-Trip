package vinova.intern.best_trip.log_in_out

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_log_screen.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.adapter.PageAdapter


class LogScreenActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_log_screen)
		changeTab()
	}

	override fun onBackPressed() {
		if(findViewById<ConstraintLayout>(R.id.signIn).visibility == View.GONE) {
			findViewById<ConstraintLayout>(R.id.signIn)?.visibility = View.VISIBLE
			findViewById<FrameLayout>(R.id.forget_frag)?.visibility = View.GONE
		}
		else {
			super.onBackPressed()
		}
	}

	fun changeTab(){
		val manager:FragmentManager = supportFragmentManager
		val adapter = PageAdapter(manager)
		view_pager.adapter = adapter
		tab_layout.setupWithViewPager(view_pager)
		view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
	}

}
