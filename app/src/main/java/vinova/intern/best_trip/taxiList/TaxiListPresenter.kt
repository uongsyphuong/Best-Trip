package vinova.intern.best_trip.taxiList

import android.view.View
import com.google.firebase.database.*
import vinova.intern.best_trip.adapter.DataAdapter
import vinova.intern.best_trip.model.Taxi

class TaxiListPresenter(view: TaxiListInterface.View):TaxiListInterface.Presenter{

    var mView: TaxiListInterface.View? = view

    override fun getListTaxi(adapter: DataAdapter) {
        val mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("taxi")
        mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                val myList: MutableList<Taxi?> = mutableListOf<Taxi?>()
                children.forEach {
                    val data = it.getValue(Taxi::class.java)
                    if(data!=null)
                        myList.add(data)
                }
                adapter.setData(myList)
            }
            override fun onCancelled(p0: DatabaseError) {
                // Failed to read value
            }
        })    }

    init {
        mView?.setPresenter(this)
    }



}