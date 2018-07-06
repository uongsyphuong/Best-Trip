package vinova.intern.best_trip.forgotPassword

import com.google.firebase.auth.FirebaseAuth

class ForgetPassPresenter (view:ForgetPassInterface.View) :ForgetPassInterface.Presenter{
    var mView: ForgetPassInterface.View? = view
    init {
        mView?.setPresenter(this)
    }
    override fun sendPasswordResetEmail(email: String) {

        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mView?.success()
                    } else {
                        mView?.showError("No user found with this email.")
                    }
                }
    }

}