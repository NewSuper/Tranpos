package com.qx.imlib.qlog

import android.content.Context

class QLogConfig(var context: Context,var appId:String,var sdkVersion:String)  {

    var uploadUrl:String = ""
    var userId:String = ""

}