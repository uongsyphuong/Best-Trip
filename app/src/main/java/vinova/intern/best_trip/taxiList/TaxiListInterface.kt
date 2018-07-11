package vinova.intern.best_trip.taxiList

import vinova.intern.best_trip.adapter.DataAdapter
import vinova.intern.best_trip.baseInterface.BasePresenter
import vinova.intern.best_trip.baseInterface.BaseView
import vinova.intern.best_trip.model.Taxi

interface TaxiListInterface {
    interface View: BaseView<Presenter> {
        fun setMatches(boolean: Boolean)
        fun getListTaxiSuccess(listTaxi: MutableList<Taxi?>)

    }
    interface Presenter: BasePresenter {
        fun getListTaxi()
        fun searchData(search:String, adapter: DataAdapter)
    }
}