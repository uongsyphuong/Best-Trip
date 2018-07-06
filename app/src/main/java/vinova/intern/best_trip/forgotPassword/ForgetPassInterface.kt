package vinova.intern.best_trip.forgotPassword

import vinova.intern.best_trip.baseInterface.BasePresenter
import vinova.intern.best_trip.baseInterface.BaseView

interface ForgetPassInterface {
    interface View : BaseView<Presenter> {
        fun success()
    }

    interface Presenter : BasePresenter {
        fun sendPasswordResetEmail(email: String)

    }
}