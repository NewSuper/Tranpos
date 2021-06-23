package com.qx.imlib.qlog

interface IQLogTrace {
    fun upload()
    fun log(content:String)
}