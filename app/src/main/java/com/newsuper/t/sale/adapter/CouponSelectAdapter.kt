package com.newsuper.t.sale.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.newsuper.t.R
import com.newsuper.t.sale.entity.OrderItem

class CouponSelectAdapter :BaseQuickAdapter<OrderItem,BaseViewHolder>(R.layout.item_coupon) {

    override fun convert(helper: BaseViewHolder, item: OrderItem?) {
//        item?.run {
//            helper.setText(R.id.tv_couponValue, "￥${discountValue}")
//            helper.setText(R.id.tv_leastCost, "消费满${leastCost}可用")
//            endEffectiveTime.takeIf {
//                it.length >= 10
//            }?.let {
//                val time = it.substring(0,10)
//                helper.setText(R.id.tv_endEffectiveTime, "有效期：${time}")
//            }
//            if(enableUse) helper.setBackgroundRes(R.id.root_view,R.mipmap.coupon_verify)
//            else helper.setBackgroundRes(R.id.root_view,R.mipmap.ic_coupon_bg_unenable)
//
//        }
    }
}