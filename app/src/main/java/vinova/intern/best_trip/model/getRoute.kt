package vinova.intern.best_trip.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Bounds (
		@SerializedName("northeast")
		@Expose
		var northeast: Northeast,
		@SerializedName("southwest")
		@Expose
		var southwest: Southwest
)

data class Distance (@SerializedName("text")
                     @Expose
                     var text: String,
                     @SerializedName("value")
					@Expose
					var value: Int )

data class Distance_ (	@SerializedName("text")
	                     @Expose
	                     var text: String,
                         @SerializedName("value")
	                     @Expose
	                     var value: Int )

data class Duration (
		@SerializedName("text")
		@Expose
		var text: String,
		@SerializedName("value")
		@Expose
		var value: Int
)

data class Duration_ (
		@SerializedName("text")
		@Expose
		var text: String,
		@SerializedName("value")
		@Expose
		var value: Int
)

data class EndLocation (
		@SerializedName("lat")
       @Expose
       var lat: Double,
       @SerializedName("lng")
		@Expose
		var lng: Double
)

data class EndLocation_ (
		@SerializedName("lat")
		@Expose
		var lat: Double,
		@SerializedName("lng")
		@Expose
		var lng: Double
)

class GeocodedWaypoint (
		@SerializedName("geocoder_status")
		@Expose
		var geocoderStatus: String,
		@SerializedName("place_id")
		@Expose
		var placeId: String,
		@SerializedName("types")
		@Expose
		var types: List<String>
)

data class GetLocation (
		@SerializedName("geocoded_waypoints")
		@Expose
		var geocodedWaypoints: List<GeocodedWaypoint>,
		@SerializedName("routes")
		@Expose
		var routes: List<Route>,
		@SerializedName("status")
		@Expose
		var status: String
)

class Leg (
		@SerializedName("distance")
	@Expose
	var distance: Distance,
		@SerializedName("duration")
	@Expose
	var duration: Duration,
		@SerializedName("end_address")
	@Expose
	var endAddress: String,
		@SerializedName("end_location")
	@Expose
	var endLocation: EndLocation,
		@SerializedName("start_address")
	@Expose
	var startAddress: String,
		@SerializedName("start_location")
	@Expose
	var startLocation: StartLocation,
		@SerializedName("steps")
	@Expose
	var steps: List<Step>,
		@SerializedName("traffic_speed_entry")
	@Expose
	var trafficSpeedEntry: List<Any>,
		@SerializedName("via_waypoint")
	@Expose
	var viaWaypoint: List<Any>
)

data class Northeast (

	@SerializedName("lat")
	@Expose
	var lat: Double,
	@SerializedName("lng")
	@Expose
	var lng: Double
)

data class OverviewPolyline (
	@SerializedName("points")
	@Expose
	var points: String
)

data class Polyline (

	@SerializedName("points")
	@Expose
	var points: String

)

data class Route (

		@SerializedName("bounds")
	@Expose
	var bounds: Bounds,
		@SerializedName("copyrights")
	@Expose
	var copyrights: String,
		@SerializedName("legs")
	@Expose
	var legs: List<Leg>,
		@SerializedName("overview_polyline")
	@Expose
	var overviewPolyline: OverviewPolyline,
		@SerializedName("summary")
	@Expose
	var summary: String,
		@SerializedName("warnings")
	@Expose
	var warnings: List<Any>,
		@SerializedName("waypoint_oder")
	@Expose
	var waypointOrder: List<Any>
)

data class Southwest (

	@SerializedName("lat")
	@Expose
	var lat: Double,
	@SerializedName("lng")
	@Expose
	var lng: Double
)

data class StartLocation (

	@SerializedName("lat")
	@Expose
	var lat: Double,
	@SerializedName("lng")
	@Expose
	var lng: Double
)

data class StartLocation_ (

		@SerializedName("lat")
		@Expose
		var lat: Double,
		@SerializedName("lng")
		@Expose
		var lng: Double
)

data class Step (

		@SerializedName("distance")
	@Expose
	var distance: Distance_,
		@SerializedName("duration")
	@Expose
	var duration: Duration_,
		@SerializedName("end_location")
	@Expose
	var endLocation: EndLocation_,
		@SerializedName("html_instructions")
	@Expose
	var htmlInstructions: String,
		@SerializedName("polyline")
	@Expose
	var polyline: Polyline,
		@SerializedName("start_location")
	@Expose
	var startLocation: StartLocation_,
		@SerializedName("travel_mode")
	@Expose
	var travelMode: String,
		@SerializedName("maneuver")
	@Expose
	var maneuver: String

)



















