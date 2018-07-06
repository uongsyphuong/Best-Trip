package vinova.intern.best_trip.signIn

import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import vinova.intern.best_trip.baseInterface.interfaceBase

interface SignInInterface {
    interface View:interfaceBase<Presenter>{
        fun signInSuccess()
    }
    interface Presenter{
        fun loginUser(mAuth:FirebaseAuth, email:String, pass:String)
        fun handleFacebookAccessToken(loginResult:LoginResult, mAuth: FirebaseAuth)
    }

}