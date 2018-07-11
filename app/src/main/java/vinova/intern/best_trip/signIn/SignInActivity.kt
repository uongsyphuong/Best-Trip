package vinova.intern.best_trip.signIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import vinova.intern.best_trip.R
import vinova.intern.best_trip.forgotPassword.ForgetPassActivity
import vinova.intern.best_trip.map.MapActivity

class SignInActivity : Fragment(),SignInInterface.View{

	var mPresenter : SignInInterface.Presenter = SignInPresenter(this)
	var imgbtn: Button? = null
	var emailBtn : Button? = null
	var user : EditText? = null
	var pass : EditText? = null
	var forgetBtn : TextView? = null
	var layout : ConstraintLayout? = null
	var forgetLayout : FrameLayout? = null
	var face : LoginButton?=null
	var callBackManager : CallbackManager? = null

	override fun signInSuccess() {
		goToMapActivity()
		activity?.finish()
 	}

	override fun setPresenter(presenter: SignInInterface.Presenter) {
		this.mPresenter = presenter
	}

	override fun showLoading(isShow: Boolean) {

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
		forgetLayout = view.findViewById(R.id.forget_frag)
		imgbtn = view.findViewById(R.id.logInFacebook)
		emailBtn = view.findViewById(R.id.btnEmail)
		user = view.findViewById(R.id.email)
		pass = view.findViewById(R.id.password)
		forgetBtn = view.findViewById(R.id.forgot_password)
		face = view.findViewById(R.id.faceLogin)
		fragmentManager?.beginTransaction()?.replace(R.id.forget_frag,ForgetPassActivity())?.addToBackStack(null)?.commit()
		setListener()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		callBackManager?.onActivityResult(requestCode,resultCode,data)
	}

	fun goToMapActivity(){
		val intent = Intent(context, MapActivity::class.java)
		startActivity(intent)
	}

	fun setListener(){
		imgbtn?.setOnClickListener {
			face?.fragment = this
			callBackManager = CallbackManager.Factory.create()
			face?.setReadPermissions("email")
            face?.fragment = this
			face?.registerCallback(callBackManager,object :FacebookCallback<LoginResult>{
				override fun onSuccess(result: LoginResult) {
					mPresenter.handleFacebookAccessToken(result)
				}

				override fun onCancel() {
				}

				override fun onError(error: FacebookException?) {
				}

			})
			face?.performClick()
		}
		emailBtn?.setOnClickListener{
			val user_name = user?.text.toString()
			val password = pass?.text.toString()
			mPresenter.loginUser(user_name,password)
		}
		forgetBtn?.setOnClickListener {
			layout?.visibility = View.GONE
			forgetLayout?.visibility = View.VISIBLE
		}
	}

}