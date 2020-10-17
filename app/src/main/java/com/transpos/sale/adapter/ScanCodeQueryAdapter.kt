package com.transpos.sale.adapter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.trans.network.utils.GsonHelper
import com.transpos.market.R
import com.transpos.sale.db.manger.LineSalesSettingDbManger
import com.transpos.sale.db.manger.MemberLevelDbManger
import com.transpos.sale.db.manger.OrderItemDbManger
import com.transpos.sale.entity.Member
import com.transpos.sale.entity.MemberLevel
import com.transpos.sale.entity.MultipleQueryProduct
import com.transpos.sale.thread.ThreadDispatcher
import com.transpos.sale.ui.scan.manger.OrderItemManger
import com.transpos.sale.ui.scan.manger.OrderManger
import com.transpos.sale.utils.StringUtils

open class ScanCodeQueryAdapter : BaseQuickAdapter<MultipleQueryProduct, BaseViewHolder> {

    private val options = RequestOptions()
        .placeholder(R.mipmap.ic_product_small_default)
        .error(R.mipmap.ic_product_small_default)
    private var context: Context

    var mMember: Member? = null
        set(value) {
            field = value
            var memberLevelList = MemberLevelDbManger.getInstance().loadAll()
            if(memberLevelList.isNotEmpty()){
                field?.memberLevel = getMemberLevel(memberLevelList)
            }
        }

    constructor(context: Context) : super(R.layout.item_scan_code_query_layout) {
        this.context = context

    }

    override fun convert(helper: BaseViewHolder, item: MultipleQueryProduct?) {
        item?.run {
            Glide.with(context).load(item.storageAddress).apply(options)
                .into(helper.getView<ImageView>(R.id.iv_product))
            if (!TextUtils.isEmpty(this.specName)) {
                helper.setText(R.id.tv_product_name, "${this.name}(${this.specName})")
            } else {
                helper.setText(R.id.tv_product_name, this.name)
            }
            val tv_product_origin_price = helper.getView<TextView>(R.id.tv_product_origin_price)
            helper.setText(R.id.tv_amount, this.getmAmount().toString())
            var total = 0f

            var vipPrice = calcVipPrice(this)
            var isvip = isVip(this, vipPrice)
            total += if(isvip){
                helper.setVisible(R.id.iv_vip_tag,true)
                helper.setVisible(R.id.tv_product_origin_price,true)
                StringUtils.underLineText(tv_product_origin_price,this.salePrice)
                helper.setText(R.id.tv_product_sale_price, "￥${vipPrice}")
                vipPrice.toFloat() * this.getmAmount()
            } else{
                helper.setVisible(R.id.iv_vip_tag,false)
                helper.setVisible(R.id.tv_product_origin_price,false)
                helper.setText(R.id.tv_product_sale_price, "￥${this.salePrice}")
                this.salePrice.toFloat() * this.getmAmount()
            }
            var formatPrice = StringUtils.formatPrice(total)
            item.setFinalPrice(formatPrice)
            helper.setText(R.id.tv_total_price, "小计￥$formatPrice")

            updateOrderItemDb(helper.adapterPosition,this,vipPrice)
        }

        helper.addOnClickListener(R.id.iv_delete, R.id.iv_sub, R.id.iv_add)
    }

    private fun updateOrderItemDb(
        position: Int,
        product: MultipleQueryProduct,
        vipPrice: String
    ) {
        val list = OrderItemManger.getList()
        var item = list[position]
        item.discountPrice = product.finalPrice.toDouble()
        item.receivableAmount = product.finalPrice.toDouble()
        item.discountAmount = StringUtils.formatPriceDouble((item.amount - item.discountPrice).toFloat())
        item.totalDiscountAmount = item.discountAmount
        item.totalReceivableAmount = item.receivableAmount
        item.discountRate = if(item.amount != 0.0 ) item.discountAmount / item.amount else 0.0
        item.totalDiscountRate = item.discountRate
        item.totalReceivableRemoveCouponAmount = item.receivableAmount
        item.totalReceivableRemoveCouponLeastCost = item.receivableAmount
        item.addPoint = OrderItemManger.calcPoint(mMember, product.finalPrice.toFloat()).toDouble()
        item.quantity = product.getmAmount().toDouble()
        item.ext1 = vipPrice
        OrderItemDbManger.update(item)

    }


    /**
     * 计算会员价
     */
    private fun calcVipPrice(item: MultipleQueryProduct) : String{
        var price = item.salePrice
        var discount = 1.0f
        mMember?.let {
            var discountWay = it.memberLevel?.discountWay
            //优惠方式
            // //0：零售价；1：会员价；2：会员价折扣；3：零售价折扣；4：批发价；5：会员价2；6：会员价3；7：会员价4；8：会员价5；

            when(discountWay){
                0 ->{
                    Log.e("debug","没有优惠")
                }
                //按会员价
                1 -> price = item.vipPrice
                5 -> price = item.vipPrice2
                6 -> price = item.vipPrice3
                7 -> price = item.vipPrice4
                8 -> price = item.vipPrice5

                //会员价折扣
                2 -> {
                    val loadAll = LineSalesSettingDbManger.getInstance().loadAll()
                    var tag = "0"
                    run breaking@{
                        loadAll.forEach {
                                set -> if(set.setKey.equals("allow_vip_discount_for_bandiscount_product")){
                            tag = set.setValue
                            return@breaking
                            }
                        }
                    }
                    if(tag.equals("0")){
                        Log.e("debug","商品禁止打折，不享受会员折扣")
                    } else{
                        discount = it.memberLevel.discount.toFloat() / 100
                        price = StringUtils.formatPrice(item.vipPrice.toFloat() * discount )
                    }

                }
                //零售价折扣
                3 -> {
                    val loadAll = LineSalesSettingDbManger.getInstance().loadAll()
                    var tag = "0"
                    run breaking@{
                        loadAll.forEach {
                                set -> if(set.setKey.equals("allow_vip_discount_for_bandiscount_product")){
                            tag = set.setValue
                            return@breaking
                            }
                        }
                    }
                    if(tag.equals("0")){
                        Log.e("debug","商品禁止打折，不享受会员折扣")
                    } else{
                        discount = it.memberLevel.discount.toFloat() / 100
                        price = StringUtils.formatPrice(item.salePrice.toFloat() * discount )
                    }
                }
                //批发价
                4 -> {
                    price = item.batchPrice
                }
                else ->{

                }
            }
        }
        if(price.toFloat() == 0f){
            price = item.salePrice
        }
        if(price.toFloat() < 0.01f){
            price = 0.01f.toString()
        }
        if(price.toFloat() < item.minPrice.toFloat()){
            price = item.minPrice
        }

        return price
    }

    /**
     * 是否是会员
     *
     */
    private fun isVip(item: MultipleQueryProduct,vipPrice : String) : Boolean{
        if(mMember == null){
            return false
        }
        if(item.salePrice.toFloat() == vipPrice.toFloat()){
            return false
        }
        return true
    }

    private fun getMemberLevel(list : MutableList<MemberLevel>) : MemberLevel?{
        var memberLevelNo = mMember?.memberLevelNo
        if(!memberLevelNo.isNullOrEmpty()){
            list.forEach {
                if (it.no.equals(memberLevelNo)){

                    return it
                }
            }
        }
        return null
    }
}
