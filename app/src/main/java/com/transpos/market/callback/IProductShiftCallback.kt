package com.transpos.market.callback

import com.transpos.market.entity.OrderItem

//import com.transpos.market.entity.ShiftByProductBean

interface IProductShiftCallback {
    fun onProductsCallback(beans : MutableList<OrderItem>)
}