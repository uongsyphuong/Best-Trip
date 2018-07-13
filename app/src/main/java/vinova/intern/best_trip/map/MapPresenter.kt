package vinova.intern.best_trip.map

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vinova.intern.best_trip.api.CallApi
import vinova.intern.best_trip.model.Distance
import vinova.intern.best_trip.model.GetLocation
import vinova.intern.best_trip.model.Taxi
import java.io.ByteArrayOutputStream
import java.io.IOException




class MapPresenter(view : MapInterface.View, var context: Context):MapInterface.Presenter {
	//Firebase
	var storage: FirebaseStorage? = null
	var storageReference: StorageReference? = null

	val userRef  = FirebaseAuth.getInstance().currentUser

	var mView: MapInterface.View? = view

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

	override fun signOut() {
		FirebaseAuth.getInstance().signOut()
		mView?.goToLogScreen()
	}

	override fun drawRoute(ori: String, desti: String) {
		CallApi.createService().getDirection(ori,desti,CallApi.Api_key).enqueue(
				object : Callback<GetLocation>{
					override fun onFailure(call: Call<GetLocation>?, t: Throwable?) {

					}

					override fun onResponse(call: Call<GetLocation>?, response: Response<GetLocation>?) {
						mView?.drawRoute(response?.body())
					}
				})
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



//			OnSuccessListener<UploadTask.TaskSnapshot> {
//				it.metadata.
//                val userReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("user")
//				val text = it.uploadSessionUri?.path
//				Log.e("abcd1", text)
//
//				userReference.child(userRef?.uid.toString()).child("image").setValue(it?.metadata?.path)


	}

	fun calcPrice(distance: Float) {
		val distanceKm  = distance/1000
		val mDatabaseReference = FirebaseDatabase.getInstance().reference.child("taxi")
		val listTaxi: MutableList<Taxi?> = mutableListOf()
		mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {
				val children = dataSnapshot.children

				children.forEach {
					val data = it.getValue(Taxi::class.java)
					data?.distance = distanceKm
					data?.price = price(distanceKm, data?.fourSeaters, data?.sevenSeaters)
					if (data != null) {
						listTaxi.add(data)
					}
				}

				//mView?.getListTaxiSuccess(listTaxi)
			}

			override fun onCancelled(p0: DatabaseError) {
				// Failed to read value
			}
		})
	}

	private fun price(distanceKm: Float, fourSeater:HashMap<String,Float>?, sevenSeater:HashMap<String,Float>?): HashMap<String, Float>? {
		if (distanceKm < 30 && distanceKm < 1) {
			if (fourSeater != null && sevenSeater != null) {
				val feeFour = fourSeater["first"]
				val feeSeven = sevenSeater["first"]
				if (feeFour != null && feeSeven != null) {
					return hashMapOf("fourSeater" to distanceKm * feeFour, "sevenSeater" to distanceKm * feeSeven)
				}
			}
		}
		if (distanceKm > 30) {
			if (fourSeater != null && sevenSeater != null) {
				val feeFiFour = fourSeater["first"]
				val feeFiSeven = sevenSeater["first"]
				val feeAfFour = fourSeater["after"]
				val feeAfSeven = sevenSeater["after"]
				if (feeFiFour != null && feeFiSeven != null && feeAfFour != null && feeAfSeven != null) {
					return hashMapOf("fourSeater" to (30 * feeFiFour + (distanceKm - 30) * feeAfFour),
							"sevenSeater" to (30 * feeFiSeven + (distanceKm - 30) * feeAfSeven))
				}
			}
		}
		val feeFou = fourSeater?.get("open_door")
		val feeSev = sevenSeater?.get("open_door")
		return if (feeFou != null && feeSev != null) {
			hashMapOf("fourSeater" to feeFou,
					"sevenSeater" to feeSev)
		} else null

	}
}