package com.transpos.market.callback

import com.transpos.market.entity.OrderItem

//import com.transpos.market.entity.ShiftByCategroyBean

interface ICategroyShiftCallback {
    fun onFetchCategroys(beans : MutableList<OrderItem>)
}