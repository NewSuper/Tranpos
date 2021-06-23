package com.qx.message.rtc

import android.os.Parcel
import android.os.Parcelable

class RTCJoined() : Parcelable {
    
    var roomId:String? = null
    var peers:ArrayList<String>? = null

    constructor(parcel: Parcel) : this() {
        roomId = parcel.readString()
        peers = parcel.createStringArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(roomId)
        parcel.writeStringList(peers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RTCJoined> {
        override fun createFromParcel(parcel: Parcel): RTCJoined {
            return RTCJoined(parcel)
        }

        override fun newArray(size: Int): Array<RTCJoined?> {
            return arrayOfNulls(size)
        }
    }


}