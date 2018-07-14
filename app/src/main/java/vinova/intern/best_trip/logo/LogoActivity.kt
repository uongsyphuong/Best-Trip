package vinova.intern.best_trip.logo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_logo.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.log_in_out.LogScreenActivity
import vinova.intern.best_trip.map.MapActivity
import vinova.intern.best_trip.model.User

class LogoActivity : AppCompatActivity(),LogoInterface.View {
    var mPresenter : LogoInterface.Presenter = LogoPresenter(this)
    var user : User? = null

    override fun setPresenter(presenter: LogoInterface.Presenter) {
        this.mPresenter = presenter
    }

    override fun wasLogined() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_logo)
        setAnimation()
        var intent = Intent(this, LogScreenActivity::class.java)
        if (mPresenter.checkAutologin()){
            mPresenter.getUser(FirebaseAuth.getInstance().uid)
            intent = Intent(this,MapActivity::class.java)
            intent.putExtra("user",user)
            intent.putExtra("wasLoged",true)
        }

        Handler().postDelayed(Runnable {
            startActivity(intent)
            finish()
        },1500)
    }

    fun setAnimation(){
        val logo_anim = AnimationUtils.loadAnimation(this,R.anim.logo_anim)
        logo_anim.interpolator = LinearInterpolator()
        logo_anim.repeatCount = Animation.INFINITE
        logo_anim.duration = 1000
        logo_app.startAnimation(logo_anim)
    }

    override fun getUserSuccess(user: User?) {
        this.user = user
    }


}
