package com.qx.message.rtc

import android.os.Parcel
import android.os.Parcelable

class RTCJoin() : Parcelable {
    var roomId: String? = null

    //通话类型 PRIVATE-单聊点对点；GROUP-群聊
    var roomType: String? = null

    constructor(parcel: Parcel) : this() {
        roomId = parcel.readString()
        roomType = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(roomId)
        parcel.writeString(roomType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RTCJoin> {
        override fun createFromParcel(parcel: Parcel): RTCJoin {
            return RTCJoin(parcel)
        }

        override fun newArray(size: Int): Array<RTCJoin?> {
            return arrayOfNulls(size)
        }
    }
}