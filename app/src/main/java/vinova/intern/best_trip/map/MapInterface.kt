package vinova.intern.best_trip.map

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import vinova.intern.best_trip.baseInterface.BaseView

interface MapInterface {
	interface View : BaseView<Presenter>{
		fun goToLogScreen()
		fun showDestination(lat:Double,long:Double)
		fun setImg(bitmap: Bitmap)
	}
	interface Presenter{
		fun signOut()
		fun selectImg(data : Intent , contentResolver: ContentResolver)
		fun takePhoto(data : Intent, contentResolver: ContentResolver)
		fun drawRoute(ori : String,desti : String)
	}
	interface Model{
		fun drawRoute(ori : String,desti : String)
	}
}