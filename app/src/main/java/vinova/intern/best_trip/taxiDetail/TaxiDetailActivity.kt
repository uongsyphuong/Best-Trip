package vinova.intern.best_trip.taxiDetail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_taxi.*
import kotlinx.android.synthetic.main.app_bar_detail_taxi.*
import kotlinx.android.synthetic.main.free_breakdown.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.model.Taxi
import java.text.DecimalFormat

class TaxiDetailActivity: AppCompatActivity(){
    lateinit var taxi:Taxi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_taxi)

        setSupportActionBar(toolbar_detail)
        val bundle = intent.extras
        taxi = bundle.getParcelable("taxi")
        val is4:Boolean = bundle.getBoolean("is4")
        val is7:Boolean = bundle.getBoolean("is7")
        title = taxi.name
        if (is4) taxi.sevenSeaters =null
        if(is7) taxi.fourSeaters = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val formatter = DecimalFormat("#,###")

        when{
            taxi.fourSeaters != null && taxi.sevenSeaters != null->{
                ct_trip.visibility = View.GONE
                cardView.visibility = View.GONE
                fee_open_door.text = formatter.format(taxi.fourSeaters?.get("open_door"))
                fee_first.text = formatter.format(taxi.fourSeaters?.get("first"))
                fee_over.text = formatter.format(taxi.fourSeaters?.get("after"))

                fee_open_door7.text = formatter.format(taxi.sevenSeaters?.get("open_door"))
                fee_first7.text = formatter.format(taxi.sevenSeaters?.get("first"))
                fee_over7.text = formatter.format(taxi.sevenSeaters?.get("after"))
            }
            taxi.fourSeaters != null && taxi.sevenSeaters == null->{

                ct_trip.visibility = View.VISIBLE
                price.text = formatter.format( taxi.priceFour)
                ct_sevenSeater.visibility = View.GONE
                fee_open_door.text = formatter.format(taxi.fourSeaters?.get("open_door"))
                fee_first.text = formatter.format(taxi.fourSeaters?.get("first"))
                fee_over.text = formatter.format(taxi.fourSeaters?.get("after"))
            }
            taxi.fourSeaters == null && taxi.sevenSeaters != null->{
                ct_trip.visibility = View.VISIBLE
                price.text = formatter.format( taxi.priceSeven)
                ct_fourSeater.visibility = View.GONE
                fee_open_door7.text = formatter.format(taxi.sevenSeaters?.get("open_door"))
                fee_first7.text = formatter.format(taxi.sevenSeaters?.get("first"))
                fee_over7.text = formatter.format(taxi.sevenSeaters?.get("after"))
            }
        }
        destination.text = intent.getStringExtra("desti")

        Glide.with(this)
                .load(taxi.logo)
                .into(image_taxi)
        about_taxi.text = taxi.about

        fab.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
            }
            else{
                startCall()
            }

        }
        toolbar_detail.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startCall(){
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${taxi.phone}")
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 1 )
            startCall()
    }


}