package vinova.intern.best_trip.signIn


import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import vinova.intern.best_trip.utils.NetworkUtils


class SignInPresenter(view: SignInInterface.View) :SignInInterface.Presenter{
    var mView: SignInInterface.View? = view
    lateinit var mAuth: FirebaseAuth


    init {
        mView?.setPresenter(this)
    }

    override fun loginUser(email:String?, pass:String?) {
        mView?.showLoading(true)
        if (email!=null && pass!=null)
            if (email != "" && pass != "")
                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email,pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                mView!!.signInSuccess()
                            }
                            else {
                                mView?.showLoading(false)
                                mView!!.showError("Authentication failed")
                            }
                        }
            else {
                mView?.showLoading(false)
                mView?.showError("You must fill up all field")
            }
    }

    override fun handleFacebookAccessToken(loginResult: LoginResult) {
        val token = loginResult.accessToken
        val credential = FacebookAuthProvider.getCredential(token.token)
        FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mAuth= FirebaseAuth.getInstance()
                        val user = mAuth.currentUser
                        val id = user?.uid
                        val username = user?.displayName.toString()
                        val photoUrl = user?.photoUrl.toString()
                        val email = user?.email.toString()
                        val mRef:DatabaseReference = FirebaseDatabase.getInstance().reference.child("user")
                        mRef.child(id.toString()).child("email").setValue(email)
                        mRef.child(id.toString()).child("username").setValue(username)
                        mRef.child(id.toString()).child("image").setValue(photoUrl)
                        mView!!.signInSuccess()


                    } else {
                        mView!!.showError("Failed")
                    }
                }
    }


}