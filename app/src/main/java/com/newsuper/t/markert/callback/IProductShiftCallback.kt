package com.newsuper.t.markert.callback

import com.newsuper.t.markert.entity.OrderItem


interface IProductShiftCallback {
    fun onProductsCallback(beans : MutableList<OrderItem>)
}