package vinova.intern.best_trip.forgotPassword

import com.google.firebase.auth.FirebaseAuth

class ForgetPassPresenter (view:ForgetPassInterface.View) :ForgetPassInterface.Presenter{
    var mView: ForgetPassInterface.View? = view
    init {
        mView?.setPresenter(this)
    }
    override fun sendPasswordResetEmail(email: String) {
        mView?.showLoading(true)
        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mView?.showLoading(false)
                        mView?.success()
                    } else {
                        mView?.showLoading(false)
                        mView?.showError("No user found with this email.")
                    }
                }
    }

}