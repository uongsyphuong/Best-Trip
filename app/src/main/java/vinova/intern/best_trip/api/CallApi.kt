package vinova.intern.best_trip.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class CallApi {
	lateinit var api :Api
	companion object {
		val Base_URL : String = "https://maps.googleapis.com"
		val Api_key : String = "AIzaSyBdsE3_CUMeIiWB2gxKQpjL2fpM6lo0gPs"
		fun builder(): Retrofit {
			return Retrofit.Builder()
					.addConverterFactory(GsonConverterFactory.create())
					.baseUrl(Base_URL)
					.build()
		}
		fun createService(): Api {
			return builder().create(Api::class.java)
		}
	}

}