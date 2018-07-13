package vinova.intern.best_trip.model

import android.os.Parcel
import android.os.Parcelable

class User :Parcelable {

    var username: String =""
    var image: String = ""
    var email: String = ""

    constructor(parcel: Parcel) : this() {
        username = parcel.readString()
        image = parcel.readString()
        email = parcel.readString()
    }

    constructor()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(image)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}