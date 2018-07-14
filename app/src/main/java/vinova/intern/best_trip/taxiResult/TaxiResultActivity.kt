package vinova.intern.best_trip.taxiResult

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_all_list_taxi.*
import vinova.intern.best_trip.R
import vinova.intern.best_trip.adapter.TaxiResultAdapter
import vinova.intern.best_trip.model.Taxi

class TaxiResultActivity: AppCompatActivity(){
    lateinit var adapter: TaxiResultAdapter
    lateinit var taxi: MutableList<Taxi?>
    var is4Seater:Boolean  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_list_taxi)

        val bundle = intent.extras
        taxi = bundle.getParcelableArrayList("taxi")
        is4Seater = bundle.getBoolean("is4")

        if (is4Seater){
            for(i in taxi.indices)
                    taxi[i]?.sevenSeaters = null
        }
        else {
            for (i in taxi.indices)
                taxi[i]?.fourSeaters = null
        }
        val mLayoutManager = LinearLayoutManager(this)
        all_list_taxi.layoutManager = mLayoutManager
        adapter = TaxiResultAdapter()
        all_list_taxi.adapter = adapter
        adapter.setData(taxi)
    }

}