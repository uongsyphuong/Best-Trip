package vinova.intern.best_trip.taxiList

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import vinova.intern.best_trip.adapter.DataAdapter
import vinova.intern.best_trip.baseInterface.BasePresenter
import vinova.intern.best_trip.baseInterface.BaseView
import vinova.intern.best_trip.model.Taxi

interface TaxiListInterface {
    interface View: BaseView<Presenter> {
        fun setMatches(boolean: Boolean)
        fun getListTaxiSuccess(listTaxi: MutableList<Taxi?>)
        fun goToLogScreen()
        fun setImg(bitmap: Bitmap)
    }
    interface Presenter: BasePresenter {
        fun getListTaxi()
        fun searchData(search:String, adapter: DataAdapter)
        fun signOut()
        fun selectImg(data : Intent, contentResolver: ContentResolver)
        fun takePhoto(data : Intent, contentResolver: ContentResolver)
    }
}