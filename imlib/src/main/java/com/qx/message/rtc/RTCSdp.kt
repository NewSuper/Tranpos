package com.qx.message.rtc

import android.os.Parcel
import android.os.Parcelable

class RTCSdp() : Parcelable {
    var sdpMid: String? = null
    var sdpMLineIndex: String? = null
    var sdp: String? = null

    constructor(parcel: Parcel) : this() {
        sdpMid = parcel.readString()
        sdpMLineIndex = parcel.readString()
        sdp = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sdpMid)
        parcel.writeString(sdpMLineIndex)
        parcel.writeString(sdp)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "RTCSdp(sdpMid=$sdpMid, sdpMLineIndex=$sdpMLineIndex, sdp=$sdp)"
    }

    companion object CREATOR : Parcelable.Creator<RTCSdp> {
        override fun createFromParcel(parcel: Parcel): RTCSdp {
            return RTCSdp(parcel)
        }

        override fun newArray(size: Int): Array<RTCSdp?> {
            return arrayOfNulls(size)
        }
    }


}