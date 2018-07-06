package vinova.intern.best_trip.signIn

import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import vinova.intern.best_trip.baseInterface.BasePresenter
import vinova.intern.best_trip.baseInterface.BaseView

interface SignInInterface {
    interface View:BaseView<Presenter>{
        fun signInSuccess()
    }
    interface Presenter{
        fun loginUser(email:String, pass:String)
        fun handleFacebookAccessToken(loginResult:LoginResult)
    }

}