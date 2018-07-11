package vinova.intern.best_trip.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Bounds (
		@SerializedName("northeast")
		@Expose
		var northeast: Northeast? = null,
		@SerializedName("southwest")
		@Expose
		var southwest: Southwest? = null
)

data class Distance (@SerializedName("text")
                     @Expose
                     var text: String? = null,
                     @SerializedName("value")
					@Expose
					var value: Int? = null )

data class Distance_ (	@SerializedName("text")
	                     @Expose
	                     var text: String? = null,
                         @SerializedName("value")
	                     @Expose
	                     var value: Int? = null )

data class Duration (
		@SerializedName("text")
		@Expose
		var text: String? = null,
		@SerializedName("value")
		@Expose
		var value: Int? = null
)

data class Duration_ (
		@SerializedName("text")
		@Expose
		var text: String? = null,
		@SerializedName("value")
		@Expose
		var value: Int? = null
)

data class EndLocation (
		@SerializedName("lat")
       @Expose
       var lat: Double? = null,
       @SerializedName("lng")
		@Expose
		var lng: Double? = null
)

data class EndLocation_ (
		@SerializedName("lat")
		@Expose
		var lat: Double? = null,
		@SerializedName("lng")
		@Expose
		var lng: Double? = null
)

class GeocodedWaypoint (
		@SerializedName("geocoder_status")
		@Expose
		var geocoderStatus: String? = null,
		@SerializedName("place_id")
		@Expose
		var placeId: String? = null,
		@SerializedName("types")
		@Expose
		var types: List<String>? = null
)

data class GetLocation (
		@SerializedName("geocoded_waypoints")
		@Expose
		var geocodedWaypoints: List<GeocodedWaypoint>? = null,
		@SerializedName("routes")
		@Expose
		var routes: List<Route>? = null,
		@SerializedName("status")
		@Expose
		var status: String? = null
)

class Leg (
		@SerializedName("distance")
	@Expose
	var distance: Distance? = null,
		@SerializedName("duration")
	@Expose
	var duration: Duration? = null,
		@SerializedName("end_address")
	@Expose
	var endAddress: String? = null,
		@SerializedName("end_location")
	@Expose
	var endLocation: EndLocation? = null,
		@SerializedName("start_address")
	@Expose
	var startAddress: String? = null,
		@SerializedName("start_location")
	@Expose
	var startLocation: StartLocation? = null,
		@SerializedName("steps")
	@Expose
	var steps: List<Step>? = null,
		@SerializedName("traffic_speed_entry")
	@Expose
	var trafficSpeedEntry: List<Any>? = null,
		@SerializedName("via_waypoint")
	@Expose
	var viaWaypoint: List<Any>? = null
)

data class Northeast (

	@SerializedName("lat")
	@Expose
	var lat: Double? = null,
	@SerializedName("lng")
	@Expose
	var lng: Double? = null
)

data class OverviewPolyline (
	@SerializedName("points")
	@Expose
	var points: String? = null
)

data class Polyline (

	@SerializedName("points")
	@Expose
	var points: String? = null

)

data class Route (

		@SerializedName("bounds")
	@Expose
	var bounds: Bounds? = null,
		@SerializedName("copyrights")
	@Expose
	var copyrights: String? = null,
		@SerializedName("legs")
	@Expose
	var legs: List<Leg>? = null,
		@SerializedName("overview_polyline")
	@Expose
	var overviewPolyline: OverviewPolyline? = null,
		@SerializedName("summary")
	@Expose
	var summary: String? = null,
		@SerializedName("warnings")
	@Expose
	var warnings: List<Any>? = null,
		@SerializedName("waypoint_oder")
	@Expose
	var waypointOrder: List<Any>? = null
)

data class Southwest (

	@SerializedName("lat")
	@Expose
	var lat: Double? = null,
	@SerializedName("lng")
	@Expose
	var lng: Double? = null
)

data class StartLocation (

	@SerializedName("lat")
	@Expose
	var lat: Double? = null,
	@SerializedName("lng")
	@Expose
	var lng: Double? = null
)

data class StartLocation_ (

		@SerializedName("lat")
		@Expose
		var lat: Double? = null,
		@SerializedName("lng")
		@Expose
		var lng: Double? = null
)

data class Step (

		@SerializedName("distance")
	@Expose
	var distance: Distance_? = null,
		@SerializedName("duration")
	@Expose
	var duration: Duration_? = null,
		@SerializedName("end_location")
	@Expose
	var endLocation: EndLocation_? = null,
		@SerializedName("html_instructions")
	@Expose
	var htmlInstructions: String? = null,
		@SerializedName("polyline")
	@Expose
	var polyline: Polyline? = null,
		@SerializedName("start_location")
	@Expose
	var startLocation: StartLocation_? = null,
		@SerializedName("travel_mode")
	@Expose
	var travelMode: String? = null,
		@SerializedName("maneuver")
	@Expose
	var maneuver: String? = null

)



















