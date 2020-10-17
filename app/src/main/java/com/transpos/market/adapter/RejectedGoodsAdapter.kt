package com.transpos.market.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.trans.network.utils.GsonHelper
import com.transpos.market.R
import com.transpos.market.callback.INotifyRejectedListener
import com.transpos.market.entity.OrderItem
import com.transpos.market.utils.StringKotlin
//
//class RejectedGoodsAdapter(private val listener : INotifyRejectedListener) : BaseQuickAdapter<RejectedGoodsWrap,BaseViewHolder>(R.layout.item_rejected_goods_list_layout) {
//    override fun convert(helper: BaseViewHolder, item: RejectedGoodsWrap?) {
//        item?.let {
//            helper.setText(R.id.tv_num,(helper.adapterPosition+1).toString())
//            helper.setText(R.id.tv_code,it.product?.showBarcode ?: "")
//            helper.setText(R.id.tv_brand,it.product?.productName ?: "")
//            if(it.product?.weightFlag == 0){
//                helper.setText(R.id.tv_count,it.product?.quantity?.toInt()?.toString() ?: "0")
//            } else {
//                helper.setText(R.id.tv_count,StringKotlin.formatPrice(it.product?.quantity ?: 0.0))
//            }
//            helper.setText(R.id.tv_price,StringKotlin.formatPrice(it.product?.ext1?.toDouble() ?: 0.0))
//            it.product!!.receivableAmount = it.product!!.ext1.toDouble() * it.product!!.quantity
//            helper.setText(R.id.tv_rprice,StringKotlin.formatPrice(it.product!!.receivableAmount))
//            if(it.isRejected){
//                helper.getView<ImageView>(R.id.iv_check).setImageResource(R.mipmap.ic_reason_check)
//            } else{
//                helper.getView<ImageView>(R.id.iv_check).setImageResource(R.mipmap.ic_reason_uncheck)
//            }
//            if(it.isEdit){
//                helper.setVisible(R.id.amount_view,true)
//                helper.setVisible(R.id.tv_count,false)
//            } else{
//                helper.setVisible(R.id.amount_view,false)
//                helper.setVisible(R.id.tv_count,true)
//            }
//            helper.addOnClickListener(R.id.iv_check)
//            helper.getView<View>(R.id.tv_count).setOnClickListener {
//                v ->
//                if(it.product?.weightFlag == 0){
//                    it.isEdit = true
//                    helper.getView<AmountView>(R.id.amount_view).amount = it.product!!.quantity.toInt()
//                    notifyDataSetChanged()
//                }
//
//            }
//            helper.getView<AmountView>(R.id.amount_view).setOnAmountChangeListener { _, amount ->
//                item.product?.quantity = amount.toDouble()
//                notifyDataSetChanged()
//                listener.notifyDataChanged()
//            }
//        }
//    }

class RejectedGoodsAdapter(private val listener : INotifyRejectedListener) : BaseQuickAdapter<OrderItem,BaseViewHolder>(R.layout.item_rejected_goods_list_layout) {
    override fun convert(helper: BaseViewHolder, item: OrderItem?) {
        item?.let {
//            helper.setText(R.id.tv_num,(helper.adapterPosition+1).toString())
//            helper.setText(R.id.tv_code,it.product?.showBarcode ?: "")
//            helper.setText(R.id.tv_brand,it.product?.productName ?: "")
//            if(it.product?.weightFlag == 0){
//                helper.setText(R.id.tv_count,it.product?.quantity?.toInt()?.toString() ?: "0")
//            } else {
//                helper.setText(R.id.tv_count,StringKotlin.formatPrice(it.product?.quantity ?: 0.0))
//            }
//            helper.setText(R.id.tv_price,StringKotlin.formatPrice(it.product?.ext1?.toDouble() ?: 0.0))
//            it.product!!.receivableAmount = it.product!!.ext1.toDouble() * it.product!!.quantity
//            helper.setText(R.id.tv_rprice,StringKotlin.formatPrice(it.product!!.receivableAmount))
//            if(it.isRejected){
//                helper.getView<ImageView>(R.id.iv_check).setImageResource(R.mipmap.ic_reason_check)
//            } else{
//                helper.getView<ImageView>(R.id.iv_check).setImageResource(R.mipmap.ic_reason_uncheck)
//            }
//            if(it.isEdit){
//                helper.setVisible(R.id.amount_view,true)
//                helper.setVisible(R.id.tv_count,false)
//            } else{
//                helper.setVisible(R.id.amount_view,false)
//                helper.setVisible(R.id.tv_count,true)
//            }
//            helper.addOnClickListener(R.id.iv_check)
//            helper.getView<View>(R.id.tv_count).setOnClickListener {
//                    v ->
//                if(it.product?.weightFlag == 0){
//                    it.isEdit = true
//                    helper.getView<AmountView>(R.id.amount_view).amount = it.product!!.quantity.toInt()
//                    notifyDataSetChanged()
//                }
//
//            }
//            helper.getView<AmountView>(R.id.amount_view).setOnAmountChangeListener { _, amount ->
//                item.product?.quantity = amount.toDouble()
//                notifyDataSetChanged()
//                listener.notifyDataChanged()
//            }
        }
    }


    fun getAllCount() : Int{
        var sum = 0
        data.forEach {
//            if(it.isRejected){
//                sum += if(it.product?.weightFlag == 0){
//                    it.product?.quantity?.toInt() ?: 0
//                } else{
//                    1
//                }
//            }
        }
        return sum
    }

    fun getPrice() : String {
        var price = 0.0
        data.forEach {
//            if(it.isRejected){
//                val singlePrice = it.product!!.ext1.toDouble() * it.product!!.quantity
//                price += singlePrice
//            }
        }
        return StringKotlin.formatPrice(price)
    }

    /**
     * 获取原价
     */
    fun getOriginPrice() : String {
        var price = 0.0
        data.forEach {
//            if(it.isRejected){
//                val singlePrice = it.product!!.salePrice * it.product!!.quantity
//                price += singlePrice
//            }
        }
        return StringKotlin.formatPrice(price)
    }

    fun getCheckProducts() : String{
        val list = mutableListOf<OrderItem>()
        data.forEach {
//            if(it.isRejected){
//                list.add(it.product!!)
//            }
        }
        return GsonHelper.toJson(list)
    }
}