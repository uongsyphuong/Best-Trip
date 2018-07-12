package vinova.intern.best_trip.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.navigation.NavigationView
import vinova.intern.best_trip.R
import vinova.intern.best_trip.log_in_out.LogScreenActivity
import vinova.intern.best_trip.model.GetLocation
import vinova.intern.best_trip.taxiList.TaxiListActivity


class MapActivity : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener,MapInterface.View
{



	private var mapPresenter : MapInterface.Presenter = MapPresenter(this,this)

	private lateinit var mMap: GoogleMap
	private val LOCATION_PERMISSION_REQUEST_CODE = 1
	private val CAMERA_REQUEST = 1888
	private val MY_CAMERA_PERMISSION_CODE = 100
	private val PLACE_AUTO_COMPLETE = 2

	private var slideUp : Animation? = null
	private var slideDown : Animation? = null
	private var show = false
	var camera : TextView? = null
	var gallery : TextView? = null

	var startLat:Double? = 0.0
	var startLong : Double? = 0.0
	var endLat : Double = 10.8778458
	var endLong : Double = 106.8072295

	var toolbar : Toolbar? = null
	var nav_view : NavigationView? = null
	var search_place : EditText? = null
	var drawer_layout: DrawerLayout? = null

	private lateinit var mLocationRequest: LocationRequest

	private val UPDATE_INTERVAL = (10 * 1000).toLong()  /* 10 secs */
	private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */


	lateinit var mFusedLocationProviderClient : FusedLocationProviderClient


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_map)
		toolbar = findViewById(R.id.toolbar)
		setSupportActionBar(toolbar)
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		setMyLocationButton()
		setNavigationDrawer()
		animation()

		setListener()
		startLocationUpdates()
	}

	@SuppressLint("MissingPermission")
	private fun startLocationUpdates() {

    // Create the location request to start receiving updates
    mLocationRequest =  LocationRequest();
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    mLocationRequest.setInterval(UPDATE_INTERVAL);
    mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

    // Create LocationSettingsRequest object using location request
    val builder  =  LocationSettingsRequest.Builder()
    builder.addLocationRequest(mLocationRequest)
    val locationSettingsRequest  = builder.build()

    // Check whether location settings are satisfied
    // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
    val settingsClient = LocationServices.getSettingsClient(this)
    settingsClient.checkLocationSettings(locationSettingsRequest);

    // new Google API SDK v11 uses getFusedLocationProviderClient(this)
    getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest,object : LocationCallback() {
	    override fun onLocationResult(p0: LocationResult) {
		    onLocationChanged(p0.lastLocation)
	    }
    }, Looper.myLooper())
}
	var first_time = true
	 fun onLocationChanged(location : Location) {
		 startLat = location.latitude
		 startLong = location.longitude

		 val latLng = LatLng(location.latitude, location.longitude)
		 mMap.addCircle(CircleOptions().center(latLng))
		 if (first_time){
			 mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
			 mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f))
			 first_time = false
		 }

	}
	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered once the user has
	 * installed Google Play services and returned to the app.
	 */
	override fun onMapReady(googleMap: GoogleMap) {
		mMap = googleMap
		enableMyLocationIfPermitted()

		mMap.setOnMyLocationButtonClickListener(object : GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {
			override fun onMyLocationClick(p0: Location) {
				startLat = p0.latitude
				startLong = p0.longitude
				mMap.addMarker(MarkerOptions().position(LatLng(p0.latitude,
						p0.longitude)))
			}

			override fun onMyLocationButtonClick(): Boolean {
				mMap.setMinZoomPreference(15f)
				return false
			}
		})
		mMap.setOnMyLocationClickListener { p0 ->
			startLat = p0.latitude
			startLong = p0.longitude
			mMap.addMarker(MarkerOptions().position(LatLng(p0.latitude,
					p0.longitude)))
		}

		mMap.setMinZoomPreference(11f)
		mMap.setMaxZoomPreference(100f)
	}

	private fun enableMyLocationIfPermitted() {
		if (ContextCompat.checkSelfPermission(this,
						Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
					LOCATION_PERMISSION_REQUEST_CODE)
		} else if (mMap != null) {
			mMap.isMyLocationEnabled = true
		}
	}

	private fun showDefaultLocation() {
		Toast.makeText(this, "Location permission not granted, " + "showing default location",
				Toast.LENGTH_SHORT).show()
		val redmond = LatLng(47.6739881, -122.121512)
		mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond))
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
	                                        grantResults: IntArray) {
		when (requestCode) {
			LOCATION_PERMISSION_REQUEST_CODE -> {
				if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					enableMyLocationIfPermitted()
				} else {
					showDefaultLocation()
				}
				return
			}
			MY_CAMERA_PERMISSION_CODE ->{
				if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
					val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
					startActivityForResult(cameraIntent, CAMERA_REQUEST)
				} else {
					Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
				}
			}
		}
	}

	private fun animation(){
		slideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up)
		slideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down)
	}

	private fun setMyLocationButton(){
		val mapFragment = supportFragmentManager
				.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)
		val locationButton :View = (mapFragment.view?.findViewById<View>(Integer.parseInt("1"))?.parent as View)
				.findViewById<View>(Integer.parseInt("2"))
		val rlp = locationButton.layoutParams as RelativeLayout.LayoutParams
		rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
		rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
		rlp.setMargins(0, 0, 30, 30)
	}

	private fun setNavigationDrawer(){
		drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
		nav_view = findViewById(R.id.nav_view)
		val toggle = ActionBarDrawerToggle(
				this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer_layout?.addDrawerListener(toggle)
		toggle.syncState()
		nav_view?.setNavigationItemSelectedListener(this)
	}

	private fun setListener(){
		gallery = findViewById<NavigationView>(R.id.nav_view).getHeaderView(0).findViewById<TextView>(R.id.gallery)
		camera = findViewById<NavigationView>(R.id.nav_view).getHeaderView(0).findViewById<TextView>(R.id.camera)

		findViewById<NavigationView>(R.id.nav_view).getHeaderView(0).findViewById<ImageView>(R.id.image_profile).
				setOnClickListener {
					if (show){
						camera?.visibility = View.GONE
						gallery?.visibility = View.GONE
						show = false
					}
					else{
						show = true
						camera?.visibility = View.VISIBLE
						gallery?.visibility = View.VISIBLE
					}
				}

		gallery?.setOnClickListener {
					val intent = Intent()
					intent.type = "image/*"
					intent.action = Intent.ACTION_GET_CONTENT
					startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
				}

		camera?.setOnClickListener{
			if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

				requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)

			}
			else {
				val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
				startActivityForResult(cameraIntent, CAMERA_REQUEST)
			}
		}

		search_place = findViewById(R.id.search_place)

		search_place?.setOnClickListener{
			try {
				val inten = PlaceAutocomplete.IntentBuilder(
						PlaceAutocomplete.MODE_OVERLAY
				).build(this)
				startActivityForResult(inten,PLACE_AUTO_COMPLETE)
			}catch (e : GooglePlayServicesNotAvailableException){

			}catch (e : GooglePlayServicesRepairableException){

			}
		}

	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == 1 && data!= null){
			mapPresenter.selectImg(data,contentResolver)
		}
		if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data!=null ) {
            mapPresenter.takePhoto(data,contentResolver)
		}
		if (requestCode == PLACE_AUTO_COMPLETE && resultCode == Activity.RESULT_OK){
			val place = PlaceAutocomplete.getPlace(this,data)
			mMap.clear()
			mapPresenter.drawRoute("$startLat,$startLong","${place.latLng.latitude},${place.latLng.longitude}")
			mMap.addMarker(MarkerOptions().position(place.latLng).title(place.name.toString()))
			mMap.moveCamera(CameraUpdateFactory.newLatLng(place.latLng))
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 12.0f))
			search_place?.setText(place.name)
		}
	}


	override fun setImg(bitmap: Bitmap) {
		val imageView : ImageView =  findViewById<NavigationView>(R.id.nav_view).getHeaderView(0)
				.findViewById(vinova.intern.best_trip.R.id.image_profile)
		imageView.setImageBitmap(bitmap)
		camera?.visibility = View.GONE
		gallery?.visibility = View.GONE
		show = false
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.log_screen,menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when(item?.itemId){
			R.id.toMap -> { }
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		drawer_layout?.closeDrawer(GravityCompat.START)
		when (item.itemId) {
			R.id.home -> {
				// Handle the camera action
			}
			R.id.taxi -> {
				val intent =Intent ( this, TaxiListActivity::class.java)
				startActivity(intent)
			}
			R.id.out -> {
				mapPresenter.signOut()
			}
		}
		return true
	}

	override fun goToLogScreen() {
		startActivity(Intent(this, LogScreenActivity::class.java))
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
		finish()
	}

	override fun setPresenter(presenter: MapInterface.Presenter) {
		this.mapPresenter = presenter
	}

	override fun showLoading(isShow: Boolean) {

	}

	override fun showError(message: String) {

	}

	override fun drawRoute(getLocation: GetLocation?) {
		var points : ArrayList<LatLng> = ArrayList()
		var polyline : PolylineOptions = PolylineOptions()
		if (getLocation!= null )
			if( getLocation.routes.isNotEmpty()){
				for (step in getLocation.routes[0].legs[0].steps){
					points.addAll(decodePoly(step.polyline.points))
				}
				for (point in points)
					polyline.add(point)
			}
		mMap.addPolyline(polyline.width(10f).color(R.color.colorAqua))
	}

	private fun decodePoly(encoded: String): List<LatLng> {
		val poly = ArrayList<LatLng>()
		var index = 0
		val len = encoded.length
		var lat = 0
		var lng = 0

		while (index < len) {
			var b: Int
			var shift = 0
			var result = 0
			do {
				b = encoded[index++].toInt() - 63
				result = result or (b and 0x1f shl shift)
				shift += 5
			} while (b >= 0x20)
			val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
			lat += dlat

			shift = 0
			result = 0
			do {
				b = encoded[index++].toInt() - 63
				result = result or (b and 0x1f shl shift)
				shift += 5
			} while (b >= 0x20)
			val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
			lng += dlng

			val p = LatLng(lat.toDouble() / 1E5,
					lng.toDouble() / 1E5)
			poly.add(p)
		}

		return poly
	}

}