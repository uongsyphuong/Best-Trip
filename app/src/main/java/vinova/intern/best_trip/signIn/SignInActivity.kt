package vinova.intern.best_trip.signIn

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import vinova.intern.best_trip.R
import vinova.intern.best_trip.forgotPassword.ForgetPassActivity
import vinova.intern.best_trip.map.MapActivity

class SignInActivity : Fragment(),SignInInterface.View {
	var mPresenter : SignInInterface.Presenter = SignInPresenter(this)
	var imgbtn: ImageButton? = null
	var emailBtn : Button? = null
	var user : EditText? = null
	var pass : EditText? = null
	var forgetBtn : TextView? = null
	var layout : ConstraintLayout? = null
	override fun signInSuccess() {
		goToMapActivity()
	}

	override fun setPresenter(presenter: SignInInterface.Presenter) {
		this.mPresenter = presenter
	}

	override fun showLoading(isShow: Boolean) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun showError(message: String) {
		Toast.makeText(context,message,Toast.LENGTH_LONG).show()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.sign_in_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		layout = view.findViewById(R.id.signIn)
		imgbtn = view.findViewById(R.id.logInFacebook)
		emailBtn = view.findViewById(R.id.btnEmail)
		user = view.findViewById(R.id.email)
		pass = view.findViewById(R.id.password)
		forgetBtn = view.findViewById(R.id.forgot_password)

		setListener()
	}
	fun goToMapActivity(){
		val intent = Intent(context, MapActivity::class.java)
		startActivity(intent)
	}
	fun setListener(){
		imgbtn?.setOnClickListener {
//			mPresenter.han
		}
		emailBtn?.setOnClickListener{
			val user_name = user?.text.toString()
			val password = pass?.text.toString()
			mPresenter.loginUser(user_name,password)
		}
		forgetBtn?.setOnClickListener {
			val frag = ForgetPassActivity()
			layout?.visibility = View.GONE
			fragmentManager?.beginTransaction()?.replace(R.id.forget_frag,frag)?.addToBackStack(null)?.commit()
		}
	}
}