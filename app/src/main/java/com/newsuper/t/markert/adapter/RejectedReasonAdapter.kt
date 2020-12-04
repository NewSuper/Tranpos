package com.newsuper.t.markert.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.newsuper.t.R
import com.newsuper.t.markert.entity.OrderItem


//class RejectedReasonAdapter : BaseQuickAdapter<RejectedReasonBean,BaseViewHolder>(R.layout.item_dialog_rejected_reason_layout) {
//    override fun convert(helper: BaseViewHolder, item: RejectedReasonBean) {
//        helper.setText(R.id.tv_name,item.reason)
//        if(item.check){
//            helper.setBackgroundRes(R.id.tv_name,R.drawable.shape_rejected_reason_check)
//        } else{
//            helper.setBackgroundRes(R.id.tv_name,R.drawable.shape_rejected_reason_uncheck)
//        }
//    }
//}
class RejectedReasonAdapter : BaseQuickAdapter<OrderItem,BaseViewHolder>(R.layout.item_dialog_rejected_reason_layout) {
    override fun convert(helper: BaseViewHolder, item: OrderItem) {
//        helper.setText(R.id.tv_name,item.reason)
//        if(item.check){
//            helper.setBackgroundRes(R.id.tv_name,R.drawable.shape_rejected_reason_check)
//        } else{
//            helper.setBackgroundRes(R.id.tv_name,R.drawable.shape_rejected_reason_uncheck)
//        }
    }
}