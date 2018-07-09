package vinova.intern.best_trip.logo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import vinova.intern.best_trip.R
import vinova.intern.best_trip.log_in_out.LogScreenActivity
import vinova.intern.best_trip.map.MapActivity

class LogoActivity : AppCompatActivity(),LogoInterface.View {
    var mPresenter : LogoInterface.Presenter = LogoPresenter(this)

    override fun setPresenter(presenter: LogoInterface.Presenter) {
        this.mPresenter = presenter
    }

    override fun wasLogined() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_logo)
        Handler().postDelayed(Runnable {
            if (mPresenter.checkAutologin())
                startActivity(Intent(this,MapActivity::class.java))
            else
                startActivity(Intent(this, LogScreenActivity::class.java))
            finish()
        },3000)
    }
}
