package com.newsuper.t.markert.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.newsuper.t.R
import com.newsuper.t.markert.entity.OrderItem

import com.newsuper.t.markert.utils.StringKotlin

class ShiftCategroyAdapter : BaseQuickAdapter<OrderItem,BaseViewHolder>(R.layout.item_shift_by_categroy_layout) {
    override fun convert(helper: BaseViewHolder, item: OrderItem?) {
        item?.let {
//            helper.setText(R.id.tv_name,it.name)
//            helper.setText(R.id.tv_amount,StringKotlin.formatPrice(it.amount))
//            helper.setText(R.id.tv_money,StringKotlin.formatPrice(it.money))
        }
    }

    fun getTotal() : Array<String>{
        var amount = 0.0
        var money = 0.0
        data.forEach {
            amount += it.amount
         //   money += it.money
        }
        return arrayOf(StringKotlin.formatPrice(amount), StringKotlin.formatPrice(money))
    }
}