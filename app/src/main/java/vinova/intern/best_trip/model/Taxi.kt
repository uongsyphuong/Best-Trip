@file:Suppress("UNCHECKED_CAST")

package vinova.intern.best_trip.model

import android.os.Parcel
import android.os.Parcelable


class Taxi :Parcelable {
    var name: String? = null
    var phone:String = ""
    var logo:String = ""
    var about:String = ""
    var fourSeaters: HashMap<String,Float>? =null
    var sevenSeaters: HashMap<String,Float>? = null
    var distance:Float? = null
    var price: HashMap<String, Float>? = hashMapOf()

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        phone = parcel.readString()
        logo = parcel.readString()
        about = parcel.readString()
        fourSeaters = parcel.readSerializable() as HashMap<String, Float>
        sevenSeaters = parcel.readSerializable() as HashMap<String, Float>
        distance = parcel.readValue(Int::class.java.classLoader) as? Float
        price = parcel.readSerializable() as HashMap<String, Float>
    }


    constructor()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(logo)
        parcel.writeString(about)
        parcel.writeSerializable(fourSeaters)
        parcel.writeSerializable(sevenSeaters)
        parcel.writeValue(distance)
        parcel.writeSerializable(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Taxi> {
        override fun createFromParcel(parcel: Parcel): Taxi {
            return Taxi(parcel)
        }

        override fun newArray(size: Int): Array<Taxi?> {
            return arrayOfNulls(size)
        }
    }


}
