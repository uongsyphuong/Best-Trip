package vinova.intern.best_trip.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.forgot_password.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.utils.NetworkUtils

class ForgetPassFragment: Fragment(),ForgetPassInterface.View{
    var mPresenter : ForgetPassInterface.Presenter = ForgetPassPresenter(this)
    var textReset : EditText? = null
    var btnReset : Button? = null
    val net = NetworkUtils()

    override fun success() {
        textReset?.setText("", TextView.BufferType.EDITABLE)
        Toast.makeText(activity,"Please check your email to reset password",Toast.LENGTH_SHORT).show()
        activity?.findViewById<ConstraintLayout>(R.id.signIn)?.visibility = View.VISIBLE
        activity?.findViewById<FrameLayout>(R.id.forget_frag)?.visibility = View.GONE
    }

    override fun setPresenter(presenter: ForgetPassInterface.Presenter) {
        this.mPresenter = presenter
    }

    override fun showLoading(isShow: Boolean) {
        val process = activity?.findViewById<ProgressBar>(R.id.progress_forget)
        process?.visibility = if (isShow) View.VISIBLE else View.GONE
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
            if (!net.isNetworkAvailable(activity) || !net.isOnline()){
                Toast.makeText(activity,"No internet connection",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            mPresenter.sendPasswordResetEmail(textReset?.text.toString())
        }
    }
}