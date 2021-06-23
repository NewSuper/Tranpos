package com.qx.message.rtc

import android.os.Parcel
import android.os.Parcelable

class RTCCandidate() : Parcelable {
    var roomId: String? = null
    var from: String? = null
    var to: String? = null
    var candidate: RTCSdp? = null

    constructor(parcel: Parcel) : this() {
        roomId = parcel.readString()
        from = parcel.readString()
        to = parcel.readString()
        candidate = parcel.readParcelable(RTCSdp::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(roomId)
        parcel.writeString(from)
        parcel.writeString(to)
        parcel.writeParcelable(candidate, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "RTCCandidate(roomId=$roomId, from=$from, to=$to, candidate=$candidate)"
    }

    companion object CREATOR : Parcelable.Creator<RTCCandidate> {
        override fun createFromParcel(parcel: Parcel): RTCCandidate {
            return RTCCandidate(parcel)
        }

        override fun newArray(size: Int): Array<RTCCandidate?> {
            return arrayOfNulls(size)
        }
    }


}