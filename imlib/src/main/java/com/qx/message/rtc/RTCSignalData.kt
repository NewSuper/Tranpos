package com.qx.message.rtc

import android.os.Parcel
import android.os.Parcelable
import com.qx.message.Data

class RTCSignalData() : Data(), Parcelable {

    var cmd:Short? = null
    var data:String? = null

    constructor(parcel: Parcel) : this() {
        cmd = parcel.readValue(Short::class.java.classLoader) as? Short
        data = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(cmd)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RTCSignalData> {
        override fun createFromParcel(parcel: Parcel): RTCSignalData {
            return RTCSignalData(parcel)
        }

        override fun newArray(size: Int): Array<RTCSignalData?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "RTCSignalData(cmd=$cmd, data=$data)"
    }

}