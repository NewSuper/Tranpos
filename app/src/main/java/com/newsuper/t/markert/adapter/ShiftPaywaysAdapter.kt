package com.newsuper.t.markert.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.newsuper.t.R
import com.newsuper.t.markert.entity.OrderItem


class ShiftPaywaysAdapter : BaseQuickAdapter<OrderItem,BaseViewHolder>(R.layout.item_shift_pay_ways) {

    override fun convert(helper: BaseViewHolder, item: OrderItem?) {
        item?.let {
//            if(it.isPayWayTitle){
//                helper.setVisible(R.id.tv_count,false)
//                helper.setVisible(R.id.tv_money,false)
//                helper.setVisible(R.id.tv_pay_way,true)
//                helper.setText(R.id.tv_pay_way,it.payWay+":")
//
//            } else{
//                helper.setVisible(R.id.tv_count,true)
//                helper.setVisible(R.id.tv_money,true)
//                helper.setVisible(R.id.tv_pay_way,false)
//                helper.setText(R.id.tv_count,"${it.countWays}:  ${it.count.toString()}")
//                helper.setText(R.id.tv_money,StringKotlin.formatPrice(it.money))
//            }
        }
    }
}