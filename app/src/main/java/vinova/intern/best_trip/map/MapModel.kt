package vinova.intern.best_trip.map

import vinova.intern.best_trip.api.Api
import vinova.intern.best_trip.api.CallApi


class MapModel:MapInterface.Model {

	private var api : Api = CallApi.createService()
	var mPresenter : MapInterface.Presenter? = null


	override fun drawRoute(ori: String, desti: String) {
		api.getDirection(ori,desti,CallApi.Api_key)
	}
}