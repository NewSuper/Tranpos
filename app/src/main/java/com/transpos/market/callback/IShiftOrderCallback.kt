package com.transpos.market.callback

import com.transpos.market.entity.OrderObject

interface IShiftOrderCallback {
    fun onOrderListCallback(orders : List<OrderObject>)
}