package vinova.intern.best_trip.map

import vinova.intern.best_trip.baseInterface.BaseView

interface MapInterface {
	interface View : BaseView<Presenter>{
		fun goToLogScreen()
		fun showDestination(lat:Double,long:Double)
	}
	interface Presenter{
		fun signOut()
		fun search(address: String?)
	}
}