package vinova.intern.best_trip.signIn


import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth


class SignInPresenter(view: SignInInterface.View) :SignInInterface.Presenter{
    var mView: SignInInterface.View? = view
    lateinit var mAuth: FirebaseAuth


    init {
        mView?.setPresenter(this)
    }

    override fun loginUser(email:String?, pass:String?) {
        if (email!=null && pass!=null)
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            mView!!.signInSuccess()
                        }
                        else {
                            mView!!.showError("Authentication failed")
                        }
                    }
        else
            mView?.showError("You must fill up xuser and password")
    }

    override fun handleFacebookAccessToken(loginResult: LoginResult) {
        val token = loginResult.accessToken
        val credential = FacebookAuthProvider.getCredential(token.token)
        FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //val user = mAuth.currentUser
                        mView!!.signInSuccess()
                    } else {
                        mView!!.showError("Failed to send verification email.")
                    }
                }
    }


}