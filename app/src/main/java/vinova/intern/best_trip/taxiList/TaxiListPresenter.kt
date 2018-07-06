package vinova.intern.best_trip.taxiList

import com.google.firebase.database.*
import vinova.intern.best_trip.model.Taxi

class TaxiListPresenter{
    fun TaxiListPresenter(){

    }

    fun getData(){
        val mDatabaseReference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        var taxi = mDatabaseReference?.child("taxi")


        taxi?.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val taxi = dataSnapshot.getValue(Taxi::class.java)

            }

            override fun onCancelled(p0: DatabaseError) {
                // Failed to read value
            }


        })
    }
}