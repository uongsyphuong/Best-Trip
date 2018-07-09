package vinova.intern.best_trip.map

import com.google.firebase.auth.FirebaseAuth




class MapPresenter(view : MapInterface.View):MapInterface.Presenter {

	var mView: MapInterface.View? = view
	init {
		mView?.setPresenter(this)
	}

	override fun signOut() {
		FirebaseAuth.getInstance().signOut()
		mView?.goToLogScreen()
	}

	override fun search(address: String?) {

	}

}