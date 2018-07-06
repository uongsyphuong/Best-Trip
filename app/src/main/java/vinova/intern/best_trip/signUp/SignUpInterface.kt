package vinova.intern.best_trip.signUp

import vinova.intern.best_trip.baseInterface.BaseView

interface SignUpInterface {
    interface View:BaseView<Presenter>{
        fun signUpSuccess()
    }
    interface Presenter{
        fun createNewAccount(username:String, email:String, pass:String)
        }

}