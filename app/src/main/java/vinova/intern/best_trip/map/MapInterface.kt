package vinova.intern.best_trip.map

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import vinova.intern.best_trip.baseInterface.BaseView
import vinova.intern.best_trip.model.GetLocation

interface MapInterface {
	interface View : BaseView<Presenter>{
		fun goToLogScreen()
		fun setImg(bitmap: Bitmap)
		fun drawRoute(getLocation : GetLocation?)
	}
	interface Presenter{
		fun signOut()
		fun selectImg(data : Intent , contentResolver: ContentResolver)
		fun takePhoto(data : Intent, contentResolver: ContentResolver)
		fun drawRoute(ori : String,desti : String)
	}
}