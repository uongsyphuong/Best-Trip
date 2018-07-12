package vinova.intern.best_trip.map

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vinova.intern.best_trip.api.CallApi
import vinova.intern.best_trip.model.GetLocation
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
		bitmap.compress(Bitmap.CompressFormat.JPEG,1,byteArrayOutputStream)
		profileRef?.putBytes(byteArrayOutputStream.toByteArray())
	}







}