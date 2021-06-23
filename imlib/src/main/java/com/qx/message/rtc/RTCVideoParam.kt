package com.qx.message.rtc

import android.os.Parcel
import android.os.Parcelable

class RTCVideoParam() : Parcelable {

    var roomId: String? = null
    var userId: String? = null
    var param: String? = null

    constructor(parcel: Parcel) : this() {
        roomId = parcel.readString()
        userId = parcel.readString()
        param = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(roomId)
        parcel.writeString(userId)
        parcel.writeString(param)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RTCVideoParam> {
        override fun createFromParcel(parcel: Parcel): RTCVideoParam {
            return RTCVideoParam(parcel)
        }

        override fun newArray(size: Int): Array<RTCVideoParam?> {
            return arrayOfNulls(size)
        }
    }

    class Param {
        var camera:Int = 0
    }
}