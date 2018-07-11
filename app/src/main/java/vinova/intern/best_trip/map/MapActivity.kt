package vinova.intern.best_trip.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_log_screen.*
import kotlinx.android.synthetic.main.app_bar_map.*
import kotlinx.android.synthetic.main.content_map.*
import kotlinx.android.synthetic.main.nav_header_log_screen.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.log_in_out.LogScreenActivity
import vinova.intern.best_trip.taxiList.TaxiListActivity


class MapActivity : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener,MapInterface.View {
	var mapPresenter : MapInterface.Presenter = MapPresenter(this)

	private lateinit var mMap: GoogleMap
	private val LOCATION_PERMISSION_REQUEST_CODE = 1

	private var slideUp : Animation? = null
	private var slideDown : Animation? = null
	var change = true

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_map)
		setSupportActionBar(toolbar)
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		setMyLocationButton()
		setNavigationDrawer()
		animation()

		val autocompleteFragment : PlaceAutocompleteFragment = (PlaceAutocompleteFragment())
		supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)

		autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
			override fun onPlaceSelected(place : Place) {
				mMap.addMarker(MarkerOptions().position(place.latLng).title(place.name.toString()))
				mMap.moveCamera(CameraUpdateFactory.newLatLng(place.latLng))
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 12.0f))
			}

			override fun onError(p0: Status?) {

			}
		})
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
		mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener)
		mMap.setOnMyLocationClickListener(onMyLocationClickListener)
		enableMyLocationIfPermitted()

		mMap.setMinZoomPreference(11f)
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
		}
	}

	private val onMyLocationButtonClickListener = GoogleMap.OnMyLocationButtonClickListener {
		mMap.setMinZoomPreference(15f)
		false
	}

	private val onMyLocationClickListener = GoogleMap.OnMyLocationClickListener { location ->
		mMap.setMinZoomPreference(12f)

//		val circleOptions = CircleOptions()
//		circleOptions.center(LatLng(location.latitude,
//				location.longitude))
//
//		circleOptions.radius(200.0)
//		circleOptions.fillColor(Color.RED)
//		circleOptions.strokeWidth(6f)
//		mMap.addCircle(circleOptions)

		mMap.addMarker(MarkerOptions().position(LatLng(location.latitude,
				location.longitude)))
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
		val toggle = ActionBarDrawerToggle(
				this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer_layout.addDrawerListener(toggle)
		toggle.syncState()
		nav_view.setNavigationItemSelectedListener(this)
	}

	private fun setListener(){
		image_profile.setOnClickListener {

		}
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.log_screen,menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when(item?.itemId){
			R.id.toMap -> {
				if (change){
					showResult.startAnimation(slideUp)
					showResult.visibility = View.GONE
					change = !change
				}
				else{
					showResult.visibility = View.VISIBLE
					showResult.startAnimation(slideDown)
					change = !change
				}
			}
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		drawer_layout.closeDrawer(GravityCompat.START)
		when (item.itemId) {
			R.id.home -> {
				// Handle the camera action
			}
			R.id.taxi -> {
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

	override fun showDestination(lat: Double, long: Double) {

	}


}
