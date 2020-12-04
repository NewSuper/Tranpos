package com.newsuper.t.sale.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.newsuper.t.R
import com.newsuper.t.markert.utils.StringKotlin
import com.newsuper.t.sale.entity.OrderItem


class HangGoodsAdadpter : BaseQuickAdapter<OrderItem,BaseViewHolder>(R.layout.item_hang_goods_layout) {
    override fun convert(helper: BaseViewHolder, item: OrderItem?) {
        item?.let {
            helper.setText(R.id.tv_product_name,it.productName)
            helper.setText(R.id.tv_count,getItemCounts(it))
            helper.setText(R.id.tv_single_price,it.ext1)
            helper.setText(R.id.tv_total_money, StringKotlin.formatPrice(it.discountPrice))
        }
    }

    private fun getItemCounts(item: OrderItem) : String{
        return if(item.weightFlag == 0){
            item.quantity.toInt().toString()
        } else {
            item.quantity.toString()
        }
    }
}