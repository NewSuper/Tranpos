package com.transpos.market.ui.cash.manger

import com.transpos.market.db.manger.MemberPointRuleDbManger
import com.transpos.market.db.manger.OrderItemDbManger
import com.transpos.market.entity.Member
import com.transpos.market.entity.MemberPointRule
import com.transpos.market.entity.MultipleQueryProduct
import com.transpos.market.entity.OrderItem
import com.transpos.market.utils.DateUtil
import com.transpos.market.utils.IdWorkerUtils
import com.transpos.market.utils.StringKotlin


object OrderItemManger {

    private val orderItemList : MutableList<OrderItem> = mutableListOf()
    private var serialNum = 0

    fun createItems(product : MultipleQueryProduct,
                    state: AddItemState,
                    position : Int,
                    member: Member?,
                    joinType : JoinTypeEnmu){
        when(state){
            AddItemState.REMOVE -> {
                if(orderItemList.isNotEmpty()){
                    updateAndInsert(AddItemState.REMOVE,position)
                    orderItemList.removeAt(position)
                }
            }
            AddItemState.MODIFY -> {
//                    已在adapter更新了相应的字段，这里不需要更新了
            }
            AddItemState.ADD -> {
                var orderBean = OrderManger.getOrderBean()
                val item = OrderItem()
                orderBean?.let {
                    item.id = IdWorkerUtils.nextId()
                    item.tenantId = it.tenantId
                    item.orderId = it.id
                    item.tradeNo = it.tradeNo
                    item.orderNo = ++serialNum
                    item.productId = product.id
                    item.productName = product.name
                    item.shortName = product.shortName
                    item.specId = product.specId
                    item.specName = product.specName
                    item.displayName = product.name
//                        item.quantity = product.getmAmount().toDouble()
                    item.rquantity = 0.toDouble()
                    item.ramount = 0.toDouble()
                    item.orgItemId = ""
                    item.salePrice = product.salePrice.toDouble()
                    item.price = product.salePrice.toDouble()
                    item.bargainReason = ""
//                        item.discountPrice = product.finalPrice.toDouble()
                    item.vipPrice = product.vipPrice.toDouble()
                    item.otherPrice = product.otherPrice.toDouble()
                    item.batchPrice = product.batchPrice.toDouble()
                    item.postPrice = product.postPrice.toDouble()
                    item.purPrice = product.purPrice.toDouble()
                    item.minPrice = product.minPrice.toDouble()
                    item.giftQuantity = 0.0
                    item.giftAmount = 0.0
                    item.giftReason = ""
                    item.flavorCount = 0
                    item.flavorNames = ""
                    item.flavorAmount = 0.0
                    item.flavorDiscountAmount = 0.0
                    item.flavorReceivableAmount = 0.0
                    item.amount = StringKotlin.formatPriceDouble(product.getmAmount() * product.salePrice.toFloat())
                    item.totalAmount = item.amount
//                        item.discountAmount = StringUtils.formatPriceDouble((item.amount - item.discountPrice).toFloat())
//                        item.receivableAmount = product.finalPrice.toDouble()
//                        item.totalDiscountAmount = item.discountAmount
//                        item.totalReceivableAmount = item.receivableAmount
//                        item.discountRate = item.discountAmount / item.amount
//                        item.totalDiscountRate = item.discountRate
                    item.malingAmount = 0.0
                    item.remark = ""
                    item.saleDate = it.saleDate
                    //item.finishDate
                    item.cartDiscount = 0.0
                    item.underline = 1
                    item.group = item.id
                    item.parentId = ""
                    item.flavor = 0
                    item.scheme = ""
                    item.rowType = 1
                    item.suitId = ""
                    item.suitQuantity = 0.0
                    item.suitAddPrice = 0.0
                    item.suitAmount = 0.0
                    item.chuda = ""
                    item.chudaFlag = ""
                    item.chudaQty = 0.0
                    item.chupin = ""
                    item.chupinFlag = ""
                    item.chupinQty = 0.0
                    item.productType = ProductTypeEnum.NORMALL.type
                    item.barCode = product.barCode
                    item.subNo = product.subNo
                    item.batchNo = ""
                    item.productUnitId = product.unitId
                    item.productUnitName = product.unitName
                    item.categoryId = product.categoryId
                    item.categoryNo = product.categoryNo
                    item.categoryName = product.categoryName
                    item.brandId = product.brandId
                    item.brandName = product.brandName
                    item.foreDiscount = product.foreDiscount
                    item.weightFlag = product.weightFlag
                    item.weightWay = product.weightWay
                    item.foreBargain = product.foreBargain
                    item.pointFlag = product.pointFlag
                    item.pointValue = product.pointValue
                    item.foreGift = product.foreGift
                    item.promotionFlag = product.promotionFlag
                    item.stockFlag = product.stockFlag
                    item.batchStockFlag = product.batchStockFlag
                    item.labelPrintFlag = product.labelPrintFlag
                    item.labelQty = 0.0
                    item.purchaseTax = product.purchaseTax
                    item.saleTax = product.saleTax
                    item.supplierId = product.supplierId
                    item.supplierName = product.supplierName
                    item.managerType = product.managerType
                    item.salesCode = ""
                    item.salesName = ""
                    item.itemSource = it.orderSource
                    item.posNo = it.posNo
                    item.addPoint = calcPoint(member,product.finalPrice.toFloat()).toDouble()
                    item.refundPoint = 0.0
                    item.promotionInfo = ""
                    item.createUser = it.createUser
                    item.createDate = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
                    item.modifyUser = it.modifyUser
                    item.modifyDate = item.createDate
                    item.lyRate = product.lyRate
                    item.chuDaLabel = ""
                    item.chuDaLabelFlag = ""
                    item.chuDaLabelQty = 0.0
                    item.shareCouponLeastCost = 0.0
                    item.couponAmount = 0.0
//                        item.totalReceivableRemoveCouponAmount = item.receivableAmount
//                        item.totalReceivableRemoveCouponLeastCost = item.receivableAmount
                    item.joinType = joinType.type
                    item.labelAmount = 0.0
                    item.isPlusPrice = 0

                    orderItemList.add(item)
                    updateAndInsert(AddItemState.ADD,position)
                }

            }
            else -> {

            }
        }

    }

    private fun updateAndInsert(state : AddItemState,position: Int){
        when(state){
            AddItemState.REMOVE -> {
                OrderItemDbManger.delete(orderItemList[position])
            }
            AddItemState.MODIFY -> {
                OrderItemDbManger.update(orderItemList)
            }
            AddItemState.ADD -> {
                OrderItemDbManger.insert(orderItemList)
            }
        }
    }

    fun calcPoint(
        member: Member?,
        totalPrice: Float
    ): Float {
        var p = 0
        if (member != null) {
            val memberLevelId = member.memberLevelId
            val pointRules =
                MemberPointRuleDbManger.getInstance().loadAll()
            var memberPointRule: MemberPointRule? = null
            if (pointRules != null && pointRules.size > 0) {
                for (rule in pointRules) {
                    if (rule.levelId == memberLevelId) {
                        memberPointRule = rule
                        break
                    }
                }
            }
            if (memberPointRule != null) {
                if (memberPointRule.pointType == 1) {
                    p = (totalPrice * memberPointRule.rate).toInt()
                }
            }
        }
        return p * 1.0f
    }

    fun clear(){
        orderItemList.clear()
    }

    fun getList() : MutableList<OrderItem>{
        return orderItemList
    }


    enum class AddItemState{
        ADD,REMOVE,MODIFY,NONE
    }

    /**
     * 商品类型 0-普通商品；1-可拆零商品；2-捆绑商品；3-自动转货；
     */
    enum class ProductTypeEnum(val type : Int){
        NORMALL(0),
        SPLIT(1),
        BIND(2),
        AUTO(3),
        NONE(-1)
    }

    /// 订单项加入方式
    //        NONE = -1,
    //        触摸点击 = 0,
    //        扫描条码 = 1,
    //        扫描金额码 = 2,
    //        扫描数量码 = 3,
    //        系统自动添加 = 4, //促销赠送
    enum class JoinTypeEnmu(val type: Int){

        NONE(-1),
        TOUCH(0),
        SCANCODE(1),
        SCANMONEY(2),
        SCANAMOUNT(3),
        SYSTEMADD(4)
    }
}


