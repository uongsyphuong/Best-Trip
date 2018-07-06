package vinova.intern.best_trip.logo

interface LogoInterface {
    interface View {
        fun setPresenter(presenter:Presenter)
        fun wasLogined()
    }
    interface Presenter{
        fun checkAutologin():Boolean
    }

}