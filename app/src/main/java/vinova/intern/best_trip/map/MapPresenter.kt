package vinova.intern.best_trip.map

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vinova.intern.best_trip.api.CallApi
import vinova.intern.best_trip.model.GetLocation
import vinova.intern.best_trip.model.Taxi
import vinova.intern.best_trip.model.User
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

	override fun calcPrice(distance: Float) {

        val distanceKm  = (Math.round((distance/1000)*10.0)/10.0).toFloat()
		val mDatabaseReference = FirebaseDatabase.getInstance().reference.child("taxi")
		var listTaxi: MutableList<Taxi?> = mutableListOf()
		var listTaxiFour: MutableList<Taxi?> = mutableListOf()
		var listTaxiSeven: MutableList<Taxi?> = mutableListOf()

		mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {
				val children = dataSnapshot.children

				children.forEach {
					val data = it.getValue(Taxi::class.java)
					data?.distance = distanceKm
                    val priceFour = priceFour(distanceKm, data?.fourSeaters)?.let { it1 -> Math.round(it1) }
                    val priceSeven = priceSeven(distanceKm, data?.sevenSeaters)?.let { it1 -> Math.round(it1) }
					if(priceFour != null && priceSeven !=null) {
						data?.priceFour = priceFour
						data?.priceSeven = priceSeven
					}
					if (data != null) {
						listTaxi.add(data)
					}
				}
				listTaxi.sortBy{it?.priceFour}
				listTaxiFour = listTaxi.toMutableList()
				listTaxi.sortBy { it?.priceSeven }
				listTaxiSeven = listTaxi.toMutableList()

				mView?.getListTaxiAndPriceSuccess(listTaxiFour,listTaxiSeven)
			}

			override fun onCancelled(p0: DatabaseError) {
				// Failed to read value
			}
		})
	}

	private fun priceFour(distanceKm: Float, fourSeaters: HashMap<String, Float>?): Float? {

		if (distanceKm < 30 && distanceKm > 1) {

			val feeFour = fourSeaters?.get("first")
			if (feeFour != null) {
				return  distanceKm * feeFour
			}
		}
		if (distanceKm > 30) {
			val feeFiFour = fourSeaters?.get("first")
			val feeAfFour = fourSeaters?.get("after")
			if (feeFiFour != null && feeAfFour != null) {
				return 30 * feeFiFour + (distanceKm - 30) * feeAfFour
			}
		}
		return fourSeaters?.get("open_door")
	}

	private fun priceSeven(distanceKm: Float, sevenSeater: HashMap<String, Float>?): Float? {
		if (distanceKm < 30 && distanceKm > 1) {
			val feeFour = sevenSeater?.get("first")
			if (feeFour != null) {
				return distanceKm * feeFour
			}
		}
		if (distanceKm > 30) {
			val feeFiFour = sevenSeater?.get("first")
			val feeAfFour = sevenSeater?.get("after")
			if (feeFiFour != null && feeAfFour != null) {
				return (30 * feeFiFour + (distanceKm - 30) * feeAfFour)
			}
		}
		return sevenSeater?.get("open_door")
	}

	override fun getUser(uid: String?){
		if (uid!= null) {
			val mDatabaseReference = FirebaseDatabase.getInstance().reference.child("user").child(uid)
			mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
				override fun onDataChange(dataSnapshot: DataSnapshot) {
					val user = dataSnapshot.getValue(User::class.java)
					//mView?.showLoading(false)
					mView?.getUserSuccess(user)
				}

				override fun onCancelled(p0: DatabaseError) {
					// Failed to read value
				}
			})
		}
	}
}