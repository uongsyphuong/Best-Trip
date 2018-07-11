package vinova.intern.best_trip.utils

import com.google.firebase.database.*
import vinova.intern.best_trip.model.Taxi
fun getData():MutableList<Taxi?> {
    val mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("taxi")
    var myList: MutableList<Taxi?> = mutableListOf<Taxi?>()


    mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val children = dataSnapshot.children


            children.forEach {
                val data = it.getValue(Taxi::class.java)
                if(data!=null)
                    myList.add(data)

            }


        }

        override fun onCancelled(p0: DatabaseError) {
            // Failed to read value
        }


    })
    return myList
}