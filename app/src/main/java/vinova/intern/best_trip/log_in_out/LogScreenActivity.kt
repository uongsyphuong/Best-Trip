package vinova.intern.best_trip.log_in_out

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_log_screen.*
import kotlinx.android.synthetic.main.app_bar_log_screen.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.adapter.PageAdapter

class LogScreenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_log_screen)
		setSupportActionBar(toolbar)
		val toggle = ActionBarDrawerToggle(
				this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer_layout.addDrawerListener(toggle)
		toggle.syncState()
		nav_view.setNavigationItemSelectedListener(this)
	}

	override fun onBackPressed() {
		if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
			drawer_layout.closeDrawer(GravityCompat.START)
		} else {
			super.onBackPressed()
		}
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		// Handle navigation view item clicks here.
		when (item.itemId) {
			R.id.home -> {
				// Handle the camera action
			}
			R.id.taxi -> {

			}
			R.id.out -> {

			}
		}
		drawer_layout.closeDrawer(GravityCompat.START)
		return true
	}

	fun changeTab(){
		var manager:FragmentManager = supportFragmentManager
		var adapter : PageAdapter = PageAdapter(manager)
//		viewPager.adapter = adapter
	}
}
