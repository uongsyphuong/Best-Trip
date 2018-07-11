package vinova.intern.best_trip.taxiList

import com.google.firebase.database.*
import vinova.intern.best_trip.adapter.DataAdapter
import vinova.intern.best_trip.model.Taxi
import java.util.ArrayList

class TaxiListPresenter(view: TaxiListInterface.View):TaxiListInterface.Presenter{
    var arrayTaxiName: ArrayList<String> = ArrayList()



    override fun searchData(search: String, adapter:DataAdapter) {
        mView?.showLoading(true)
        val mDatabaseReference = FirebaseDatabase.getInstance().reference.child("taxi")
        val listTaxi: MutableList<Taxi?> = mutableListOf()
        mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children

                children.forEach {
                    val data = it.getValue(Taxi::class.java)
                    if (data != null) {
                        listTaxi.add(data)
                    }
                }
                updateData(search,adapter,listTaxi)
            }

            override fun onCancelled(p0: DatabaseError) {
                // Failed to read value
            }
        })

    }

    fun updateData(search:String,adapter: DataAdapter, listTaxi: MutableList<Taxi?>) {
        val listSort:MutableList<Taxi?> = mutableListOf<Taxi?>()
        for (i in arrayTaxiName.indices) {
            if (arrayTaxiName[i].toLowerCase().indexOf(search.toLowerCase()) >= 0) {
                listSort.add(listTaxi.find { it?.name == arrayTaxiName[i] })
            }
        }
        adapter.setData(listSort)
        if (listSort.isEmpty()) {
            mView?.showLoading(false)
            mView?.setMatches(true)
        } else {
            mView?.showLoading(false)
            mView?.setMatches(false)
        }
    }

    var mView: TaxiListInterface.View? = view

    override fun getListTaxi() {
        mView?.showLoading(true)
        val mDatabaseReference = FirebaseDatabase.getInstance().reference.child("taxi")
        val listTaxi: MutableList<Taxi?> = mutableListOf()
        mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children

                children.forEach {
                    val data = it.getValue(Taxi::class.java)
                    if (data != null) {
                        listTaxi.add(data)
                        arrayTaxiName.add(data.name.toString())
                    }
                }
                mView?.showLoading(false)
                mView?.getListTaxiSuccess(listTaxi)
            }

            override fun onCancelled(p0: DatabaseError) {
                // Failed to read value
            }
        })
    }

    init {
        mView?.setPresenter(this)
    }



}