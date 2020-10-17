package com.transpos.market.callback

import com.transpos.market.entity.OrderItem
import com.transpos.market.entity.OrderObject


interface IRejectedCallback {
    fun onSuceess(order : OrderObject?,list : List<OrderItem>)
}