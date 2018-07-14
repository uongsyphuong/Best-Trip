package vinova.intern.best_trip.logo

import vinova.intern.best_trip.model.User

interface LogoInterface {
    interface View {
        fun setPresenter(presenter:Presenter)
        fun wasLogined()
        fun getUserSuccess (user: User?)
    }
    interface Presenter{
        fun getUser(uid: String?)
        fun checkAutologin():Boolean
    }

}