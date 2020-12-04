package com.newsuper.t.markert.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

class HotKeyBean(val id : Int,var description : String,var keyName : String) : MultiItemEntity {
    var dataType = Type.KEY
    var title = ""
    var supportModify = true
    var moduleType = ""

    override fun getItemType(): Int {
        return dataType
    }

    companion object Type{
        const val TITLE = 0
        const val KEY = 1
    }
}

class HotKeyMap(var id : Int,var keyCode : Int,var keyName : String,var description : String,var moduleType : String = "")