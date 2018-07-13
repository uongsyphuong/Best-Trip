package vinova.intern.best_trip.logo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_logo.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.log_in_out.LogScreenActivity
import vinova.intern.best_trip.map.MapActivity
import vinova.intern.best_trip.model.User

class LogoActivity : AppCompatActivity(),LogoInterface.View {
    override fun getUserSuccess(user: User?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
        setAnimation()
        Handler().postDelayed(Runnable {
            if (mPresenter.checkAutologin())
                startActivity(Intent(this,MapActivity::class.java))
            else
                startActivity(Intent(this, LogScreenActivity::class.java))
            finish()
        },1600)
    }

    fun setAnimation(){
        val logo_anim = AnimationUtils.loadAnimation(this,R.anim.logo_anim)
        logo_anim.interpolator = LinearInterpolator()
        logo_anim.repeatCount = Animation.INFINITE
        logo_anim.duration = 1500
        logo_app.startAnimation(logo_anim)
    }

}
