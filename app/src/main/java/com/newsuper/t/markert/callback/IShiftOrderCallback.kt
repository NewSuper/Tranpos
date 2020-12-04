package com.newsuper.t.markert.callback

import com.newsuper.t.markert.entity.OrderObject


interface IShiftOrderCallback {
    fun onOrderListCallback(orders : List<OrderObject>)
}