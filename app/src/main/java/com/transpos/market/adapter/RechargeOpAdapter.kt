package com.transpos.market.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.transpos.market.R
import com.transpos.market.entity.OrderItem
import com.transpos.market.view.CustomGradient

//RechargeOptionBean
class RechargeOpAdapter : BaseQuickAdapter<OrderItem,BaseViewHolder>(R.layout.item_recharge_op_layout) {
    override fun convert(helper: BaseViewHolder, item: OrderItem?) {
        item?.let {
          //  helper.setText(R.id.tv_money,"￥${it.money}")
         //   helper.setText(R.id.tv_gift,"赠${it.giftMoney}元  送${it.point}积分")
        }
    }
}
