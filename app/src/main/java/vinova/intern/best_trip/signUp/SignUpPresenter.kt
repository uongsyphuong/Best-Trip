package vinova.intern.best_trip.signUp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpPresenter(view: SignUpInterface.View) :SignUpInterface.Presenter {

    var mView: SignUpInterface.View? = view
    lateinit var mAuth: FirebaseAuth

    init {
        mView?.setPresenter(this)
    }


    override fun createNewAccount(username:String,email: String, pass: String) {
        val mDatabaseReference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        mAuth= FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val userId = mAuth.currentUser!!.uid

                        //Verify Email
                        val mUser = mAuth.currentUser
                        mUser?.sendEmailVerification()?.addOnCompleteListener { task1 ->
                            if (task1.isSuccessful) {
                                mView?.signUpSuccess()
                            } else {
                                mView?.showError("Failed to verify")

                            }
                        }
                        //update user profile information
                        val currentUserDb = mDatabaseReference!!.child("user").child(userId)
                        currentUserDb.child("username").setValue(username)
                        currentUserDb.child("email").setValue(email)
                    } else {
                        // If sign in fails, display a message to the user.
                        mView?.showError("Authentication failed")
                    }
                }
    }



}