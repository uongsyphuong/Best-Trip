package vinova.intern.best_trip.taxiList

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_taxi_list.*
import kotlinx.android.synthetic.main.content_list_taxi.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.adapter.DataAdapter
import vinova.intern.best_trip.log_in_out.LogScreenActivity
import vinova.intern.best_trip.map.MapActivity
import vinova.intern.best_trip.model.Taxi
import vinova.intern.best_trip.model.User
import vinova.intern.best_trip.utils.NetworkUtils

class TaxiListActivity: AppCompatActivity(), TaxiListInterface.View, NavigationView.OnNavigationItemSelectedListener{

    var mPresenter : TaxiListInterface.Presenter = TaxiListPresenter(this)
    lateinit var adapter: DataAdapter
    var toolbar : Toolbar? = null
    var nav_view : NavigationView? = null
    var drawer_layout: DrawerLayout? = null

    private var show = false
    private var camera : TextView? = null
    private var gallery : TextView? = null
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100

    private val END_SCALE = 0.7f
    val net = NetworkUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_taxi)
        toolbar = findViewById(R.id.toolbar_detail)
        setSupportActionBar(toolbar)
        val mLayoutManager = LinearLayoutManager(this)
        list_taxi.layoutManager = mLayoutManager
        adapter = DataAdapter(this)

        list_taxi.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        swipeRefresh.setOnRefreshListener {
            adapter.clearData()
            mPresenter.getListTaxi()
        }

        if (!net.isNetworkAvailable(this) || !net.isOnline()){
            Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show()
            return
        }
        else
            mPresenter.getListTaxi()

        setListener()

        setNavigationDrawer()

        setUser()
    }

    override fun getListTaxiSuccess(listTaxi: MutableList<Taxi?>) {
        swipeRefresh.isRefreshing = false
        adapter.setData(listTaxi)
    }

    override fun setMatches(boolean: Boolean) {
        if(boolean) tvNoMatches.visibility = View.VISIBLE
        else tvNoMatches.visibility = View.GONE

    }

    override fun setPresenter(presenter: TaxiListInterface.Presenter) {
        this.mPresenter = presenter
    }

    override fun showLoading(isShow: Boolean) {
        pro_bar_list_taxi.visibility = if (isShow) View.VISIBLE else View.GONE

    }

    override fun showError(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        nav_view?.menu?.getItem(1)?.isChecked = true
        drawer_layout?.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.home -> {
                startActivity(Intent(this,MapActivity::class.java))
                finish()
            }
            R.id.taxi -> {

            }
            R.id.out -> {
                mPresenter.signOut()
            }
        }
        return true
    }

    override fun goToLogScreen() {
        startActivity(Intent(this, LogScreenActivity::class.java))
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        finish()
    }


    private fun setListener(){
        edt_search_taxi.setOnClickListener { edt_search_taxi.setCursorVisible(true) }

        edt_search_taxi.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                if (edt_search_taxi.text.isNotEmpty()) {
                    if (motionEvent.rawX >= edt_search_taxi.right - edt_search_taxi.compoundDrawables[2].bounds.width()) {
                        edt_search_taxi.setText("")
                        edt_search_taxi.isCursorVisible = true
                        val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        view.requestFocus()
                        inputMethodManager.showSoftInput(view, 0)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })

        edt_search_taxi.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                edt_search_taxi.isCursorVisible = false
                if (this.currentFocus != null) {
                    val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
                }
                return@OnEditorActionListener true
            }
            false
        })

        edt_search_taxi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (edt_search_taxi.text.isNotEmpty()) {
                    edt_search_taxi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, R.drawable.ic_delete, 0)
                } else {
                    edt_search_taxi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0)
                }
            }

            override fun afterTextChanged(editable: Editable) {
                Handler().postDelayed({
                    mPresenter.searchData(edt_search_taxi.text.toString(), adapter)
                }, 200)
            }
        })

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
    }

    private fun setNavigationDrawer(){
        drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout?.addDrawerListener(toggle)
        toggle.syncState()
        drawer_layout?.addDrawerListener(object : DrawerLayout.SimpleDrawerListener(){
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val diffScaledOffset = slideOffset * (1 - END_SCALE)
                val offsetScale = 1 - diffScaledOffset
                app_bar_layout_taxi.scaleX = offsetScale
	            app_bar_layout_taxi.scaleY = offsetScale

                val  xOffset = drawerView.width * slideOffset
                val  xOffsetDiff = app_bar_layout_taxi.width * diffScaledOffset / 2
                val  xTranslation = xOffset - xOffsetDiff

	            app_bar_layout_taxi.translationX = xTranslation
            }
        })
        nav_view?.setNavigationItemSelectedListener(this)
	    nav_view?.menu?.getItem(1)?.isChecked = true

    }

    private fun setUser(){
        val user = intent.getParcelableExtra<User>("user")
        val a = findViewById<NavigationView>(R.id.nav_view).getHeaderView(0)
        if (user !=null ){
            a.findViewById<TextView>(R.id.user_name).text = user.username
            a.findViewById<TextView>(R.id.user_email).text = user.email
            Glide.with(this).load(user.image).into(a.findViewById(R.id.image_profile))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data!=null ) {
            mPresenter.takePhoto(data,contentResolver)
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

}