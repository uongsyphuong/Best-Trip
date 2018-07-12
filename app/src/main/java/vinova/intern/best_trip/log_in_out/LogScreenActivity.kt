package vinova.intern.best_trip.log_in_out

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_log_screen.*
import kotlinx.android.synthetic.main.content_log_screen.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.adapter.PageAdapter


class LogScreenActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_log_screen)
		changeTab()
	}

	override fun onBackPressed() {
		if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
			drawer_layout.closeDrawer(GravityCompat.START)
		}
		else if(findViewById<ConstraintLayout>(R.id.signIn).visibility == View.GONE) {
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
		viewPager.adapter = adapter
		tabLayout.setupWithViewPager(viewPager)
		viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
	}

}
