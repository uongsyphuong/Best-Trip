package vinova.intern.best_trip.taxiList

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import vinova.intern.best_trip.adapter.DataAdapter
import vinova.intern.best_trip.model.Taxi
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class TaxiListPresenter(view: TaxiListInterface.View):TaxiListInterface.Presenter{
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    private var isFirst:Boolean = true
    val userRef  = FirebaseAuth.getInstance().currentUser

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
        mView?.goToLogScreen()
    }

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
        if (isFirst) mView?.showLoading(true)
        val mDatabaseReference = FirebaseDatabase.getInstance().reference.child("taxi")
        val listTaxi: MutableList<Taxi?> = mutableListOf()
        mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children

                children.forEach {
                    val data = it.getValue(Taxi::class.java)
                    if (data != null) {
                        listTaxi.add(data)
                        if (isFirst) arrayTaxiName.add(data.name.toString())
                    }
                }
                isFirst = false
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
        storage = FirebaseStorage.getInstance()
        storageReference = storage?.getReference()
    }

    override fun selectImg(data: Intent, contentResolver: ContentResolver) {
        val uri: Uri = data.data
        try {
            val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            upload(bitmap)
            mView?.setImg(bitmap)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun takePhoto(data: Intent, contentResolver: ContentResolver) {
        val bitmap = data.extras.get("data") as Bitmap
        upload(bitmap)
        mView?.setImg(bitmap)
    }

    private fun upload(bitmap: Bitmap){
        val storageReference = storage?.reference
        val profileRef = storageReference?.child("images/profile_${userRef?.uid}.png")

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream)
        Log.e("abcd", profileRef.toString())
        profileRef?.putBytes(byteArrayOutputStream.toByteArray())?.addOnSuccessListener {
            // success
            profileRef.downloadUrl.addOnCompleteListener { taskSnapshot ->
                val url = taskSnapshot.result.toString()
                val userReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("user")
                userReference.child(userRef?.uid.toString()).child("image").setValue(url)
            }
        }
    }


}