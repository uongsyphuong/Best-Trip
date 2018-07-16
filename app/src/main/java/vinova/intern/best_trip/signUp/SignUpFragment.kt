package vinova.intern.best_trip.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import vinova.intern.best_trip.R
import vinova.intern.best_trip.utils.NetworkUtils

class SignUpFragment : Fragment(),SignUpInterface.View {
	var mPresenter : SignUpInterface.Presenter = SignUpPresenter(this)
	var user_ : EditText? = null
	var email_ : EditText? = null
	var pass_ : EditText? = null
	var btnSignup : Button? = null
	var progressBar : ProgressBar? = null
    val net = NetworkUtils()

	override fun signUpSuccess() {
		user_?.setText("", TextView.BufferType.EDITABLE)
		email_?.setText("", TextView.BufferType.EDITABLE)
		pass_?.setText("", TextView.BufferType.EDITABLE)
        activity?.findViewById<ViewPager>(R.id.view_pager)?.setCurrentItem(0,true)
	}

	override fun setPresenter(presenter: SignUpInterface.Presenter) {
		this.mPresenter = presenter
	}

	override fun showLoading(isShow: Boolean) {
		if(isShow)
			progressBar?.visibility = View.VISIBLE
		else
			progressBar?.visibility = View.GONE
	}

	override fun showError(message: String) {
		Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.sign_up_fragment,container,false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		user_ = view.findViewById(R.id.username)
		email_ = view.findViewById(R.id.email)
		pass_ = view.findViewById(R.id.password)
		btnSignup = view.findViewById(R.id.btnSignUp)
		progressBar = view.findViewById(R.id.progress_bar)
		setListener()
	}

	fun setListener(){
		btnSignup?.setOnClickListener {
            if (!net.isNetworkAvailable(activity) || !net.isOnline()){
                Toast.makeText(activity,"No internet connection",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
			mPresenter.createNewAccount(user_?.text.toString(),email_?.text.toString(),pass_?.text.toString())
		}
	}

}