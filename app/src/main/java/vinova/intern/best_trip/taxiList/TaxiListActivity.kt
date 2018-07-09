package vinova.intern.best_trip.taxiList

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import android.view.MenuInflater
import android.widget.SearchView
import vinova.intern.best_trip.R
import vinova.intern.best_trip.R.id.list_taxi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_list_taxi.*
import vinova.intern.best_trip.adapter.DataAdapter
import vinova.intern.best_trip.model.Taxi
import vinova.intern.best_trip.utils.getData


@Suppress("UNREACHABLE_CODE")
class TaxiListActivity: AppCompatActivity(){
    lateinit var adapter: DataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_taxi)
        val mLayoutManager = LinearLayoutManager(this)
        list_taxi.layoutManager = mLayoutManager
        adapter = DataAdapter(this)
        list_taxi.adapter = adapter


        val mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("taxi")


        mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                var myList: MutableList<Taxi?> = mutableListOf<Taxi?>()
                children.forEach {

                    var data = it.getValue(Taxi::class.java)

                    Log.d("UKKKK", it?.child("about")?.value.toString())
                    if(data!=null)
                        myList.add(data)
                }
                adapter.setData(myList)


            }

            override fun onCancelled(p0: DatabaseError) {
                // Failed to read value
            }


        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_taxi_list, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
    }
}