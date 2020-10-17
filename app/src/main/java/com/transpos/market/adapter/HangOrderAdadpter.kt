package com.transpos.market.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.transpos.market.R
import com.transpos.market.base.BaseApp
import com.transpos.market.entity.OrderItem
import com.transpos.market.entity.Worker
import com.transpos.market.utils.KeyConstrant
import com.transpos.tools.tputils.TPUtils
//HangOrderBean

class HangOrderAdadpter : BaseQuickAdapter<OrderItem,BaseViewHolder>(R.layout.item_hang_order_layout) {

    private val worker : Worker by lazy {
        TPUtils.getObject(
            BaseApp.getApplication(),
            KeyConstrant.KEY_WORKER,
            Worker::class.java
        )
    }

    override fun convert(helper: BaseViewHolder, item: OrderItem?) {
        item?.let {
//            helper.setText(R.id.tv_num,"${helper.adapterPosition+1}")
//            helper.setText(R.id.tv_order_no,it.order.tradeNo)
//            helper.setText(R.id.tv_time,it.order.modifyDate)
//            helper.setText(R.id.tv_money,"${it.order.receivableAmount}")
//            helper.setText(R.id.tv_count,"${it.order.totalQuantity}")
//            helper.setText(R.id.tv_casher,"${worker.name}")
//            if(it.isCheck){
//                helper.setBackgroundColor(R.id.root_view, Color.parseColor("#c7efec"))
//            } else{
//                helper.setBackgroundColor(R.id.root_view, Color.WHITE)
//            }
        }
    }
}
