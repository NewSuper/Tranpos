package com.newsuper.t.markert.callback

import com.newsuper.t.markert.entity.OrderItem
import com.newsuper.t.markert.entity.OrderObject


interface IRejectedCallback {
    fun onSuceess(order : OrderObject?, list : List<OrderItem>)
}