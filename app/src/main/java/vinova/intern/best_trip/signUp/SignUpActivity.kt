package vinova.intern.best_trip.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import vinova.intern.best_trip.R

class SignUpActivity : Fragment(),SignUpInterface.View {
	var mPresenter : SignUpInterface.Presenter = SignUpPresenter(this)
	var user_ : EditText? = null
	var email_ : EditText? = null
	var pass_ : EditText? = null
	var btnSignup : Button? = null

	override fun signUpSuccess() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun setPresenter(presenter: SignUpInterface.Presenter) {
		this.mPresenter = presenter
	}

	override fun showLoading(isShow: Boolean) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun showError(message: String) {
		Toast.makeText(context,message,Toast.LENGTH_LONG).show()
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
		setListener()
	}
	fun setListener(){
		btnSignup?.setOnClickListener {
			mPresenter.createNewAccount(user_?.text.toString(),email_?.text.toString(),pass_?.text.toString())
		}
	}

}