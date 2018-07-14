package vinova.intern.best_trip.map

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import vinova.intern.best_trip.baseInterface.BaseView
import vinova.intern.best_trip.model.GetLocation
import vinova.intern.best_trip.model.Taxi
import vinova.intern.best_trip.model.User

interface MapInterface {
	interface View : BaseView<Presenter>{
		fun goToLogScreen()
		fun setImg(bitmap: Bitmap)
		fun drawRoute(getLocation : GetLocation?)
		fun getListTaxiAndPriceSuccess(listTaxiFour :MutableList<Taxi?>,listTaxiSeven :MutableList<Taxi?>)
		fun getUserSuccess (user: User?)
	}
	interface Presenter{
		fun signOut()
		fun selectImg(data : Intent , contentResolver: ContentResolver)
		fun takePhoto(data : Intent, contentResolver: ContentResolver)
		fun drawRoute(ori : String,desti : String)
		fun calcPrice(distance: Float)
		fun getUser(uid: String?)
	}
}