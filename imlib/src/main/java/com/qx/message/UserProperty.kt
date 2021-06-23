package com.qx.message

import android.os.Parcel
import android.os.Parcelable

/**
 *
 */
class UserProperty() : Parcelable {

    var language: String? = null

    constructor(parcel: Parcel) : this() {
        language = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(language)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserProperty> {
        override fun createFromParcel(parcel: Parcel): UserProperty {
            return UserProperty(parcel)
        }

        override fun newArray(size: Int): Array<UserProperty?> {
            return arrayOfNulls(size)
        }
    }
}