package com.qx.message.rtc

import android.os.Parcel
import android.os.Parcelable

class RTCOffer() : Parcelable{
    var roomId: String? = null
    var from: String? = null
    var to: String? = null
    var sdp: String? = null

    constructor(parcel: Parcel) : this() {
        roomId = parcel.readString()
        from = parcel.readString()
        to = parcel.readString()
        sdp = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(roomId)
        parcel.writeString(from)
        parcel.writeString(to)
        parcel.writeString(sdp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RTCOffer> {
        override fun createFromParcel(parcel: Parcel): RTCOffer {
            return RTCOffer(parcel)
        }

        override fun newArray(size: Int): Array<RTCOffer?> {
            return arrayOfNulls(size)
        }
    }
}