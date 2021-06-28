package com.qx.im.model.json

import com.qx.im.core.bean.json.*


class JSonMessage(var messageId: String,
                  var to: String,
                  var from: String,
                  var sendType: String,
                  var messageType: String,
                  var timestamp: Long) {


    var notice: JSonNotice? = null
    var text: JSonText? = null
    var image: JSonImage? = null
    var imageText: JSonImageText? = null
    var audio: JSonAudio? = null
    var video: JSonVideo? = null
    var file: JSonFile? = null
    var geo: JSonGeo? = null
    var act: JSonAct? = null
    var vct: JSonVct? = null
    var custom: JSonCustom? = null
    var reply: JSonReply? = null
    var record: JSonRecord? = null
}