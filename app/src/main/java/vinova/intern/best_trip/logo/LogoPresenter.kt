package vinova.intern.best_trip.logo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LogoPresenter(view: LogoInterface.View) :LogoInterface.Presenter {

    var mView: LogoInterface.View? = view
    init {
        mView?.setPresenter(this)
    }

    override fun checkAutologin():Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}