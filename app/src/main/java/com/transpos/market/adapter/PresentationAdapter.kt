package com.transpos.market.adapter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.transpos.market.R
import com.transpos.market.db.manger.LineSalesSettingDbManger
import com.transpos.market.db.manger.MemberLevelDbManger
import com.transpos.market.entity.Member
import com.transpos.market.entity.MemberLevel
import com.transpos.market.entity.MultipleQueryProduct
import com.transpos.market.utils.StringKotlin
import java.util.ArrayList


open class PresentationAdapter : BaseQuickAdapter<MultipleQueryProduct, BaseViewHolder> {
    private var context: Context
    var mMember: Member? = null
        set(value) {
            field = value
            var memberLevelList = MemberLevelDbManger.getInstance().loadAll()
            if (memberLevelList.isNotEmpty()) {
                field?.memberLevel = getMemberLevel(memberLevelList)
            }
        }

    constructor(context: Context) : super(R.layout.item_presentation) {
        this.context = context
    }


    override fun convert(helper: BaseViewHolder, item: MultipleQueryProduct?) {
        item?.run {
            if (!TextUtils.isEmpty(this.specName)) {
                helper.setText(R.id.tv_goodsName, "${this.name}(${this.specName})")
            } else {
                helper.setText(R.id.tv_goodsName, this.name)
            }
            helper.setText(R.id.tv_num, this.getmAmount().toString())
            var total = 0f
            var vipPrice = calcVipPrice(this)
            var isvip = isVip(this, vipPrice)
            total += if (isvip) {
                vipPrice.toFloat() * this.getmAmount()
            } else {
                this.salePrice.toFloat() * this.getmAmount()
            }
          //  var formatPrice = StringKotlin.formatPrice(total)
          //  item.finalPrice = formatPrice
           // helper.setText(R.id.tv_totalPrice, "$formatPrice")
        }
        helper.addOnClickListener(R.id.ll_root)
    }

    /**
     * 计算会员价
     */
    private fun calcVipPrice(item: MultipleQueryProduct): String {
        var price = item.salePrice
        var discount = 1.0f
        mMember?.let {
            var discountWay = it.memberLevel?.discountWay
            //优惠方式
            // //0：零售价；1：会员价；2：会员价折扣；3：零售价折扣；4：批发价；5：会员价2；6：会员价3；7：会员价4；8：会员价5；

            when (discountWay) {
                0 -> {
                    Log.e("debug", "没有优惠")
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
                        loadAll.forEach { set ->
                            if (set.setKey.equals("allow_vip_discount_for_bandiscount_product")) {
                                tag = set.setValue
                                return@breaking
                            }
                        }
                    }
                    if (tag.equals("0")) {
                        Log.e("debug", "商品禁止打折，不享受会员折扣")
                    } else {
                        discount = it.memberLevel.discount.toFloat() / 100
                       // price = StringKotlin.formatPrice(item.vipPrice.toFloat() * discount)
                    }

                }
                //零售价折扣
                3 -> {
                    val loadAll = LineSalesSettingDbManger.getInstance().loadAll()
                    var tag = "0"
                    run breaking@{
                        loadAll.forEach { set ->
                            if (set.setKey.equals("allow_vip_discount_for_bandiscount_product")) {
                                tag = set.setValue
                                return@breaking
                            }
                        }
                    }
                    if (tag.equals("0")) {
                        Log.e("debug", "商品禁止打折，不享受会员折扣")
                    } else {
                        discount = it.memberLevel.discount.toFloat() / 100
                     //   price = StringKotlin.formatPrice(item.salePrice.toFloat() * discount)
                    }
                }
                //批发价
                4 -> {
                    price = item.batchPrice
                }
                else -> {

                }
            }
        }
        if (price.toFloat() == 0f) {
            price = item.salePrice
        }
//        if (price.toFloat() < 0.1f) {
//            price = 0.01f.toString()
//        }
        if (price.toFloat() < item.minPrice.toFloat()) {
            price = item.minPrice
        }

        return price
    }

    /**
     * 是否是会员
     *
     */
    private fun isVip(item: MultipleQueryProduct, vipPrice: String): Boolean {
        if (mMember == null) {
            return false
        }
        if (item.salePrice.toFloat() == vipPrice.toFloat()) {
            return false
        }
        return true
    }

    private fun getMemberLevel(list: MutableList<MemberLevel>): MemberLevel? {
        var memberLevelNo = mMember?.memberLevelNo
        if (!memberLevelNo.isNullOrEmpty()) {
            list.forEach {
                if (it.no.equals(memberLevelNo)) {

                    return it
                }
            }
        }
        return null
    }
}