package h_mal.appttude.com.driver.Objects.WholeObject

import android.os.Parcel
import android.os.Parcelable
import h_mal.appttude.com.driver.Objects.WholeDriverObject


class MappedObject : Parcelable {
    var userId: String? = null
    var wholeDriverObject: WholeDriverObject? = null

    constructor(userId: String?, wholeDriverObject: WholeDriverObject?) {
        this.userId = userId
        this.wholeDriverObject = wholeDriverObject
    }

    constructor()
    protected constructor(`in`: Parcel) {
        userId = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(userId)
    }

    companion object {
        val CREATOR: Parcelable.Creator<MappedObject?> = object : Parcelable.Creator<MappedObject?> {
            override fun createFromParcel(`in`: Parcel): MappedObject? {
                return MappedObject(`in`)
            }

            override fun newArray(size: Int): Array<MappedObject?> {
                return arrayOfNulls(size)
            }
        }
    }
}