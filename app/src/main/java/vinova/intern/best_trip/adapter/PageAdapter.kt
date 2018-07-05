package vinova.intern.best_trip.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import vinova.intern.best_trip.signIn.SignInActivity
import vinova.intern.best_trip.signUp.SignUpActivity

class PageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

	override fun getItem(position: Int): Fragment {
		val frag : Fragment
		when(position){
			0 -> frag = SignInActivity()
			1 -> frag = SignUpActivity()
			else -> frag = SignInActivity()
		}
		return frag
	}

	override fun getCount(): Int {
		return 2
	}

	override fun getPageTitle(position: Int): CharSequence? {
		var title : String
		when(position){
			0 -> title = "Sign In"
			1 -> title = "Sign Up"
			else -> title = "Sign In"
		}
		return title
	}
}