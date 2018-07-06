package vinova.intern.best_trip.signIn

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import androidx.annotation.NonNull
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider


class SignInPresenter:SignInInterface.Presenter{
    var mView: SignInInterface.View? = null

    fun SignInPresenter(view: SignInInterface.View)  {
        this.mView = view
        mView!!.setPresenter(this)
    }

    override fun loginUser(mAuth: FirebaseAuth, email:String, pass:String) {
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mView!!.signInSuccess()
                    }
                    else {
                        mView!!.showError("Authentication failed")
                    }
                }
    }

    override fun handleFacebookAccessToken(loginResult: LoginResult, mAuth: FirebaseAuth) {
        val token = loginResult.accessToken
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
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