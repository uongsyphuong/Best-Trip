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
        title = taxi.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val formatter = DecimalFormat("#,###")

        fee_open_door.text = formatter.format(taxi.fourSeaters?.get("open_door"))
        fee_first.text = formatter.format(taxi.fourSeaters?.get("first"))
        fee_over.text = formatter.format(taxi.fourSeaters?.get("after"))

        fee_open_door7.text = formatter.format(taxi.sevenSeaters?.get("open_door"))
        fee_first7.text = formatter.format(taxi.sevenSeaters?.get("first"))
        fee_over7.text = formatter.format(taxi.sevenSeaters?.get("after"))

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
        checkType()
    }

    @SuppressLint("MissingPermission")
    private fun startCall(){
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:0969981853")
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 1 )
            startCall()
    }

    fun checkType(){
        when{
            taxi.fourSeaters != null && taxi.sevenSeaters != null->{
                constraintLayout.visibility = View.GONE
            }
            taxi.fourSeaters == null && taxi.sevenSeaters != null->{

            }
            taxi.fourSeaters != null && taxi.sevenSeaters == null->{

            }
        }
    }


}