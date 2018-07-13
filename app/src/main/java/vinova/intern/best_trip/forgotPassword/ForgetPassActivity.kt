package vinova.intern.best_trip.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import vinova.intern.best_trip.R

class ForgetPassActivity: Fragment(),ForgetPassInterface.View{
    var mPresenter : ForgetPassInterface.Presenter = ForgetPassPresenter(this)
    var textReset : EditText? = null
    var btnReset : Button? = null

    override fun success() {
        activity?.findViewById<ViewPager>(R.id.viewPager)?.getChildAt(0)?.findViewById<ConstraintLayout>(R.id.signIn)?.visibility = View.VISIBLE
        activity?.findViewById<ViewPager>(R.id.viewPager)?.getChildAt(0)?.findViewById<FrameLayout>(R.id.forget_frag)?.visibility = View.GONE
    }

    override fun setPresenter(presenter: ForgetPassInterface.Presenter) {
        this.mPresenter = presenter
    }

    override fun showLoading(isShow: Boolean) {

    }

    override fun showError(message: String) {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.forgot_password,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textReset = view.findViewById(R.id.forgot_password)
        btnReset = view.findViewById(R.id.send_reset)

        btnReset?.setOnClickListener{
            mPresenter.sendPasswordResetEmail(textReset?.text.toString())
        }
    }
}