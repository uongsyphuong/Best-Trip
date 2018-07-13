package vinova.intern.best_trip.logo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import vinova.intern.best_trip.model.User


class LogoPresenter(view: LogoInterface.View) :LogoInterface.Presenter {

    var mView: LogoInterface.View? = view
    init {
        mView?.setPresenter(this)
    }

    override fun checkAutologin():Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    override fun getUser(uid: String){
        //mView?.showLoading(true)
        val mDatabaseReference = FirebaseDatabase.getInstance().reference.child("user").child(uid)
        mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                //mView?.showLoading(false)
                mView?.getUserSuccess(user)
            }

            override fun onCancelled(p0: DatabaseError) {
                // Failed to read value
            }
        })
    }
}