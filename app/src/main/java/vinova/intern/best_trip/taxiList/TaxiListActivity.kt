package vinova.intern.best_trip.taxiList

import android.graphics.Movie
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import vinova.intern.best_trip.R
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_taxi.*
import vinova.intern.best_trip.adapter.DataAdapter
import vinova.intern.best_trip.model.Taxi
<<<<<<< HEAD
=======
import vinova.intern.best_trip.utils.getData
import android.content.Intent
import vinova.intern.best_trip.taxiDetail.TaxiDetailActivity
>>>>>>> c184d14668f7abc9036ba86b8862920912a96439


@Suppress("UNREACHABLE_CODE")
class TaxiListActivity: AppCompatActivity(), TaxiListInterface.View{


    override fun getListTaxiSuccess(listTaxi: MutableList<Taxi?>) {
        adapter.setData(listTaxi)
    }

    override fun setMatches(boolean: Boolean) {
        if(boolean) tvNoMatches.visibility = View.VISIBLE
        else tvNoMatches.visibility = View.GONE

    }

    var mPresenter : TaxiListInterface.Presenter = TaxiListPresenter(this)
    lateinit var adapter: DataAdapter


    override fun setPresenter(presenter: TaxiListInterface.Presenter) {
        this.mPresenter = presenter
    }

    override fun showLoading(isShow: Boolean) {
        pro_bar_list_taxi.visibility = if (isShow) View.VISIBLE else View.GONE

    }

    override fun showError(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_taxi)
        val mLayoutManager = LinearLayoutManager(this)
        list_taxi.layoutManager = mLayoutManager
        adapter = DataAdapter(this)

        list_taxi.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mPresenter.getListTaxi()


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
                mPresenter.searchData(edt_search_taxi.text.toString(), adapter)

            }
        })


    }
}