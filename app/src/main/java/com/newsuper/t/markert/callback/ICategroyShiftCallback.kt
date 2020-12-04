package com.newsuper.t.markert.callback

import com.newsuper.t.markert.entity.OrderItem


//import com.transpos.market.entity.ShiftByCategroyBean

interface ICategroyShiftCallback {
    fun onFetchCategroys(beans : MutableList<OrderItem>)
}