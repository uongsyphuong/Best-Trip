package vinova.intern.best_trip

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import vinova.intern.best_trip.log_in_out.LogScreenActivity

class LogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_logo)
        val inent : Intent = Intent(this, LogScreenActivity::class.java)
        Handler().postDelayed(Runnable {
            startActivity(inent)
        },3000)
    }
}
