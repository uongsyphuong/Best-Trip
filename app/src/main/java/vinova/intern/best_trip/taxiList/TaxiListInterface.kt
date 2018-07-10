package vinova.intern.best_trip.taxiList

import vinova.intern.best_trip.adapter.DataAdapter
import vinova.intern.best_trip.baseInterface.BasePresenter
import vinova.intern.best_trip.baseInterface.BaseView

interface TaxiListInterface {
    interface View: BaseView<Presenter> {
    }
    interface Presenter: BasePresenter {
        fun getListTaxi(adapter: DataAdapter)
        //fun updateDataListView(search:String)
    }
}