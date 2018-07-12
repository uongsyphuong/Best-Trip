package vinova.intern.best_trip.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vinova.intern.best_trip.model.GetLocation

interface Api {
	@GET("/maps/api/directions/json")
	fun getDirection(@Query("origin") ori : String,
	                 @Query("destination") des : String,
	                 @Query("key") key : String)
			: Call<GetLocation>

}