package com.newsuper.t.markert.ui.cash.mvp


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.TextUtils

import com.trans.network.callback.JsonObjectCallback
import com.trans.network.callback.StringCallback
import com.trans.network.model.Response

import com.newsuper.t.markert.base.mvp.BasePresenter
import com.newsuper.t.markert.db.manger.LineSalesSettingDbManger
import com.newsuper.t.markert.db.manger.MemberPointRuleDbManger

import com.newsuper.t.markert.db.manger.MultipleQueryProductDbManger.queryOneProductByAllcode
import com.newsuper.t.markert.db.manger.MultipleQueryProductDbManger.queryOneProductByCode
import com.newsuper.t.markert.db.manger.MultipleQueryProductDbManger.queryOneProductBysubCode
import com.newsuper.t.markert.db.manger.OrderPayDbManger
import com.newsuper.t.markert.dialog.MemberInputPasswardDialog
import com.newsuper.t.markert.dialog.vm.InputDialogVM
import com.newsuper.t.markert.entity.*
import com.newsuper.t.markert.entity.state.OrderPaymentStatusFlag
import com.newsuper.t.markert.entity.state.PayChannelFlag
import com.newsuper.t.markert.entity.state.PostWayFlag
import com.newsuper.t.markert.ui.cash.manger.OrderItemManger
import com.newsuper.t.markert.ui.cash.manger.OrderManger
import com.newsuper.t.markert.ui.cash.manger.OrderPayManger
import com.newsuper.t.markert.utils.DateUtil
import com.newsuper.t.markert.utils.StringKotlin
import com.newsuper.t.markert.utils.Tools
import com.newsuper.t.markert.utils.UiUtils
import com.transpos.sale.thread.ThreadDispatcher
import org.apache.commons.lang3.StringUtils
import org.json.JSONObject
import java.util.*


class CashPresenter : BasePresenter<CashContract.Model, CashContract.View>(),
    CashContract.Presenter {
    override fun createModule(): CashContract.Model {
        return CashModel()
    }

    fun queryMemberByPhoneByPay(phoneNum: String) {
        module.queryMemberByPhone(phoneNum, object : JsonObjectCallback<EntityResponse<Member>>() {

            override fun onSuccess(response: Response<EntityResponse<Member>>?) {
                super.onSuccess(response)
                response?.let {
                    if (it.body().code == BaseResponse.SUCCESS) {
                        var member = it.body().data
                        if (isViewAttached) {
                            view.queryMemberSuccessByPay(member)
                        }
                    } else if (it.body().code == -9) {
                        UiUtils.showToastLong(it.body().msg)
                    } else {
                        UiUtils.showToastLong(it.message())
                    }
                }
            }

            override fun onError(response: Response<EntityResponse<Member>>?) {
                super.onError(response)
            }
        })
    }

    fun queryMemberByBarcodeByPay(barcode: String) {
        module.queryMemberByBarcode(barcode, object : JsonObjectCallback<EntityResponse<Member>>() {

            override fun onSuccess(response: Response<EntityResponse<Member>>?) {
                super.onSuccess(response)
                response?.let {
                    if (it.body().code == BaseResponse.SUCCESS) {
                        var member = it.body().data
                        if (isViewAttached) {
                            view.queryMemberSuccessByPay(member)
                        }
                    } else if (it.body().code == -9) {
                        UiUtils.showToastLong(it.body().msg)
                    } else {
                        UiUtils.showToastLong(it.message())
                    }
                }
            }

            override fun onError(response: Response<EntityResponse<Member>>?) {
                super.onError(response)
            }
        })
    }

    override fun queryMemberByPhone(phoneNum: String) {
        module.queryMemberByPhone(phoneNum, object : JsonObjectCallback<EntityResponse<Member>>() {

            override fun onSuccess(response: Response<EntityResponse<Member>>?) {
                super.onSuccess(response)
                response?.let {
                    if (it.body().code == BaseResponse.SUCCESS) {
                        var member = it.body().data
                        if (isViewAttached) {
                            view.queryMemberSuccess(member)
                        }

                    } else if (it.body().code == -9) {
                        UiUtils.showToastLong(it.body().msg)
                    } else {
                        UiUtils.showToastLong(it.message())
                    }
                }
            }

            override fun onError(response: Response<EntityResponse<Member>>?) {
                super.onError(response)
            }
        })
    }

    override fun queryMemberByBarcode(barcode: String) {
        module.queryMemberByBarcode(barcode, object : JsonObjectCallback<EntityResponse<Member>>() {

            override fun onSuccess(response: Response<EntityResponse<Member>>?) {
                super.onSuccess(response)
                response?.let {
                    if (it.body().code == BaseResponse.SUCCESS) {
                        var member = it.body().data
                        if (isViewAttached) {
                            view.queryMemberSuccess(member)
                        }
                    } else if (it.body().code == -9) {
                        UiUtils.showToastLong(it.body().msg)
                    } else {
                        UiUtils.showToastLong(it.message())
                    }
                }
            }

            override fun onError(response: Response<EntityResponse<Member>>?) {
                super.onError(response)
            }
        })
    }

    override fun queryProductByBarcode(barCode: String?) {
        ThreadDispatcher.getDispatcher().post {
            var isEmpty = false
            //根据条码和自编码查询
            var multipleQueryProduct =
                barCode?.let { queryOneProductByCode(it) }
            if (multipleQueryProduct == null || multipleQueryProduct.isEmpty()) {
                multipleQueryProduct = barCode?.let { queryOneProductBysubCode(it) }
            }
            //条码和自编码未查询到 ，再去查询allcode
            if (multipleQueryProduct == null || multipleQueryProduct.isEmpty()) {
                multipleQueryProduct =
                    barCode?.let { queryOneProductByAllcode(it) }
                if (multipleQueryProduct == null || multipleQueryProduct.isEmpty()) {
                    isEmpty = true
                } else {
                    val iterator =
                        multipleQueryProduct.iterator()
                    while (iterator.hasNext()) {
                        val product = iterator.next()
                        val allCode = product.allCode
                        var isExits = false
                        if (!TextUtils.isEmpty(allCode)) {
                            val codeArr =
                                allCode.split(",").toTypedArray()
                            for (s in codeArr) {
                                if (s == barCode) {
                                    isExits = true
                                }
                            }
                        }
                        if (!isExits) {
                            iterator.remove()
                        }
                    }
                }
            }
            if (multipleQueryProduct == null || multipleQueryProduct.isEmpty()) {
                isEmpty = true
            }
            if (multipleQueryProduct != null) {
                post(isEmpty, multipleQueryProduct)
            };
        }
    }

    private fun post(isEmpty: Boolean, multipleQueryProduct: List<MultipleQueryProduct>) {
        ThreadDispatcher.getDispatcher().postOnMain {
            if (isEmpty) {
                UiUtils.showToastShort("无法识别商品码")
            } else {
                if (isViewAttached) {
                    view.queryProductSuccess(multipleQueryProduct)
                }
            }
        }
    }

    override fun createOrder() {
        OrderManger.initalize()
    }

    fun addOrderItem(
        product: MultipleQueryProduct?,
        state: OrderItemManger.AddItemState?,
        position: Int,
        member: Member?,
        joinType: OrderItemManger.JoinTypeEnmu?
    ) {
        if (product != null) {
            if (state != null) {
                if (joinType != null) {
                    OrderItemManger.createItems(product, state, position, member, joinType)
                }
            }
        }
    }

    fun setOrderValue(
        list: List<MultipleQueryProduct?>,
        discountAmount: Float,
        totalPrice: Float,
        mMember: Member?
    ) {
        val orderBean: OrderObject? = OrderManger.getOrderBean()
        if (orderBean != null) {
            orderBean.itemCount = list.size
            orderBean.payCount = 1
            orderBean.totalQuantity = getTotalAmount(list as List<MultipleQueryProduct>).toDouble()
            orderBean.amount = getOriginTotalPrice(list).toDouble()
            orderBean.discountAmount = discountAmount.toDouble()
            orderBean.receivableAmount = totalPrice.toDouble()
            orderBean.paidAmount = totalPrice.toDouble()
            orderBean.receivedAmount = totalPrice.toDouble()
            orderBean.malingAmount = 0.0
            orderBean.changeAmount = 0.0
            orderBean.invoicedAmount = 0.0
            orderBean.overAmount = 0.0
            orderBean.printTimes = 0
            orderBean.printStatus = 0
            orderBean.postWay = PostWayFlag.ZITI_STATE
            orderBean.discountRate =
                if (orderBean.amount == 0.0) 0.0 else discountAmount / orderBean.amount
            orderBean.isMember = if (mMember != null) 1 else 0
            orderBean.memberNo = if (mMember != null) mMember.no else ""
            orderBean.memberName = if (mMember != null) mMember.name else ""
            orderBean.memberMobileNo = if (mMember != null) mMember.mobile else ""
            orderBean.setPrePoint(mMember?.totalPoint ?: 0.0)
            orderBean.addPoint = calcPoint(mMember, totalPrice).toDouble()
            orderBean.aftPoint = orderBean.prePoint + orderBean.addPoint
            orderBean.memberId = if (mMember != null) mMember.id else ""
            orderBean.receivableRemoveCouponAmount = orderBean.receivableAmount
            orderBean.modifyDate = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
         //   orderBean.ext2 =
               // if (mMember != null) StringKotlin.formatPrice(mMember.totalAmount.toFloat()) else "" //设置会员余额
          //  OrderObjectDbManger.update(orderBean)
        }
    }

    /**
     * 计算积分
     * @param member
     * @param totalPrice
     * @return 未合并
     */
    public fun calcPoint(member: Member?, totalPrice: Float): Float {
        var p = 0
        if (member != null) {
            val memberLevelId = member.memberLevelId
            val pointRules: List<MemberPointRule> =
                MemberPointRuleDbManger.getInstance().loadAll()
            var memberPointRule: MemberPointRule? = null
            if (pointRules != null && pointRules.size > 0) {
                for (rule in pointRules) {
                    if (rule.getLevelId().equals(memberLevelId)) {
                        memberPointRule = rule
                        break
                    }
                }
            }
            if (memberPointRule != null) {
                if (memberPointRule.getPointType() === 1) {
                    p = (totalPrice * memberPointRule.getRate()) as Int
                }
            }
        }
        return p * 1.0f
    }


    fun getTotalAmount(datas: List<MultipleQueryProduct>): Int {
        var amount = 0
        for (data in datas) {
            amount += data.getmAmount()
        }
        return amount
    }

    /**
     * 获取总原价
     * @param datas
     * @return
     */
    fun getOriginTotalPrice(datas: List<MultipleQueryProduct>): Float {
        var p = 0f
        for (data in datas) {
            p += data.getmAmount() * data.salePrice.toFloat()
        }
        return StringKotlin.formatPriceFloat(p)
    }

    fun getVipPrice(
        member: Member?,
        datas: List<MultipleQueryProduct>
    ): FloatArray? {
        var price = 0f
        var sPrice = 0f
        var discount = 1f
        var reduction = 0f
        var settings: List<LineSalesSetting> =
            ArrayList<LineSalesSetting>()
        if (member == null) {
            for (data in datas) {
                sPrice += if (data.salePrice.toFloat() < data.minPrice.toFloat()) {
                    data.minPrice.toFloat() * data.getmAmount()
                } else {
                    data.salePrice.toFloat() * data.getmAmount()
                }
            }
        } else {
            val level: MemberLevel? = member.memberLevel
            if (level != null) {
                if (level.getDiscountWay() === 2) {
                    // discount = level.getDiscount() as Float / 100
                    discount = (level.getDiscount() as Double / 100).toFloat()
                } else if (level.getDiscountWay() === 3) {
                    //discount = level.getDiscount() as Float / 100  ClassCastException-- java.lang.Double cannot be cast to java.lang.Float
                    discount = (level.getDiscount() as Double / 100).toFloat()
                }
            }
            for (data in datas) {
                price = 0f
                if (level != null) {
                    when (level.getDiscountWay()) {
                        0 -> {
                        }
                        1 -> {
                            price = data.vipPrice.toFloat()
                        }
                        5 -> {
                            price = data.vipPrice2.toFloat()
                        }
                        6 -> {
                            price = data.vipPrice3.toFloat()
                        }
                        7 -> {
                            price = data.vipPrice4.toFloat()
                        }
                        8 -> {
                            price = data.vipPrice5.toFloat()
                        }
                        2 -> {
                            //会员价折扣
                            if (settings.isEmpty()) {
                                settings = LineSalesSettingDbManger.getInstance().loadAll()
                            }
                            var tag = "0"
                            for (setting in settings) {
                                if (setting.getSetKey()
                                        .equals("allow_vip_discount_for_bandiscount_product")
                                ) {
                                    tag = setting.getSetValue()
                                }
                            }
                            if (tag == "1") {
                                price = data.vipPrice.toFloat() * discount
                            }
                        }
                        3 ->  //售价打折
                        {
                            if (settings.isEmpty()) {
                                settings = LineSalesSettingDbManger.getInstance().loadAll()
                            }
                            var allow = "0"
                            for (setting in settings) {
                                if (setting.getSetKey()
                                        .equals("allow_vip_discount_for_bandiscount_product")
                                ) {
                                    allow = setting.getSetValue()
                                }
                            }
                            if (allow == "1") {
                                price = data.salePrice.toFloat() * discount
                            }
                        }
                        4 -> price = data.batchPrice.toFloat()
                    }
                }
                if (price == 0f) {
                    price = data.salePrice.toFloat()
                }
                //最低价 0.01
//                if (price < 0.01f) {
//                    price = 0.01f
//                }
                if (price < data.minPrice.toFloat()) {
                    price = data.minPrice.toFloat()
                }
                reduction += (data.salePrice.toFloat() - price) * data.getmAmount()
                sPrice += price * data.getmAmount()
            }
        }
        return floatArrayOf(
            StringKotlin.formatPriceFloat(sPrice),
            StringKotlin.formatPriceFloat(reduction)
        )
    }

    private var queryCount = 0
    private var mMember: Member? = null
    private var mMoney: Float = 0f

    override fun scanCode(money: Float, code: String) {
        val start = code.substring(0, 2)
        if (StringUtils.isNumeric(start)) {
            //插入支付订单表
            OrderPayManger.createPayInfo()
            OrderPayManger.payInfo.payTime = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
            var startCode = start.toInt()
            when (startCode) {
                in 10..15 -> {
                    //微信
                    OrderPayManger.payInfo.payNo = OrderPayManger.PayModeEnum.PAYWX.payNo
                    OrderPayManger.payInfo.name = OrderPayManger.PayModeEnum.PAYWX.payName
                    OrderPayManger.payInfo.payChannel = PayChannelFlag.LESHUA_PAY
                    OrderPayManger.payInfo.paidAmount = money.toDouble()
                    wxPay(money, code)
                }
                in 25..30 -> {
                    //alipay
                    OrderPayManger.payInfo.payNo = OrderPayManger.PayModeEnum.PAYAIL.payNo
                    OrderPayManger.payInfo.name = OrderPayManger.PayModeEnum.PAYAIL.payName
                    OrderPayManger.payInfo.payChannel = PayChannelFlag.LESHUA_PAY
                    OrderPayManger.payInfo.paidAmount = money.toDouble()
                    aliPay(money, code)
                }
                else -> {
                    OrderPayManger.payInfo.payNo = OrderPayManger.PayModeEnum.PAYMEMBER.payNo
                    OrderPayManger.payInfo.name = OrderPayManger.PayModeEnum.PAYMEMBER.payName
                    OrderPayManger.payInfo.paidAmount = money.toDouble()

                    performMemberPay(money, code)
                }
            }
            OrderPayDbManger.insert(OrderPayManger.payInfo)
        } else {
            view.dismissLoading()
            UiUtils.showToastLong("条码错误")
        }
    }
//    private fun insertMemberInfo(member: Member,money: Float){
//        val orderBean: OrderObject? = OrderManger.getOrderBean()
//        if (orderBean != null) {
//            orderBean.discountRate =
//                if (orderBean.amount == 0.0) 0.0 else discountAmount / orderBean.amount
//            orderBean.isMember = if (mMember != null) 1 else 0
//            orderBean.memberNo = if (mMember != null) member.no else ""
//            orderBean.memberName = if (mMember != null) member.name else ""
//            orderBean.memberMobileNo = if (mMember != null) member.mobile else ""
//            orderBean.setPrePoint(mMember?.totalPoint ?: 0.0)
//            orderBean.addPoint = calcPoint(mMember, money).toDouble()
//            orderBean.aftPoint = orderBean.prePoint + orderBean.addPoint
//            orderBean.memberId = if (mMember != null) member.id else ""
//            orderBean.receivableRemoveCouponAmount = orderBean.receivableAmount
//            orderBean.modifyDate = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
//            orderBean.ext2 =
//                if (mMember != null) StringKotlin.formatPrice(member.totalAmount.toFloat()) else "" //设置会员余额
//            OrderObjectDbManger.update(orderBean)
//        }
//    }

    private fun performMemberPay(money: Float, code: String) {
        module.queryMember(code, object : JsonObjectCallback<EntityResponse<Member>>() {
            override fun onSuccess(response: Response<EntityResponse<Member>>?) {
                super.onSuccess(response)
                response?.let {
                    var body = it.body()
                    if (body.code == BaseResponse.SUCCESS) {
                        val member = body.data
                        if (member.isNoPwd == 1) {
                            if (member.npAmount >= money) {
                                memberPay(money, member)
                            } else {
                                mMember = member
                                mMoney = money
                                mMember?.needShowPwdDialog = true
                                showPasswordDialog(member, money)
                            }
                        } else {
                            mMember = member
                            mMoney = money
                            mMember?.needShowPwdDialog = true
                            showPasswordDialog(member, money)
                        }
                        OrderPayManger.setMemberInfo(member)
                    } else {
                        UiUtils.showToastLong(body.msg)
                        view.dismissLoading()
                    }
                }
            }

            override fun onError(response: Response<EntityResponse<Member>>?) {
                super.onError(response)
                UiUtils.showToastLong(response?.message())
                view.dismissLoading()
            }
        })

    }

    /**
     * 弹出密码输入
     */
    private fun showPasswordDialog(member: Member, money: Float) {
        val dialog: MemberInputPasswardDialog = MemberInputPasswardDialog.Builder().build()
        var bundle = Bundle()
        bundle.putSerializable("member", member)
        dialog.arguments = bundle
        dialog.show((context as? FragmentActivity)?.supportFragmentManager, "p")
    }

    /**
     * 输入密码后调支付
     */
    override fun continueMemberPay(password: String) {
        mMember?.let {
            it.password = password
            memberPay(mMoney, it)
        }
    }

    //此方法是单独点击储值卡支付---2个弹窗，涉及传值麻烦---新加的
    //单独判断免密金额超限+支付金额大于
    override fun continueMemberPay2(member: Member, password: String, money: Float) {
        member?.let {
            it.password = password
            if (member.isNoPwd == 1) {
                if (member.npAmount >= money) {
                    memberPay(money, it)

                    insertPay(money)
                } else {
                    member?.needShowPwdDialog = true
                    memberPay(money, it)

                    insertPay(money)
                }
            } else {
                member?.needShowPwdDialog = true
                memberPay(money, it)

                insertPay(money)
            }
        }
    }

    private fun insertPay(money: Float) {
        //插入支付订单表
        OrderPayManger.createPayInfo()
        OrderPayManger.payInfo.payTime = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
        OrderPayManger.payInfo.payNo = OrderPayManger.PayModeEnum.PAYMEMBER.payNo
        OrderPayManger.payInfo.name = OrderPayManger.PayModeEnum.PAYMEMBER.payName
        OrderPayManger.payInfo.paidAmount = money.toDouble()
        OrderPayDbManger.insert(OrderPayManger.payInfo)
    }

    private fun memberPay(money: Float, member: Member) {
        module.memberPay(money, member, object : StringCallback() {
            override fun onSuccess(response: Response<String>?) {
                super.onSuccess(response)
                response?.let {
                    val jsonObj: JSONObject = JSONObject(it.body())
                    val code = jsonObj.optInt("code")
                    if (code == BaseResponse.SUCCESS) {
                        var optJSONObject = jsonObj.optJSONObject("data")
                        val status = optJSONObject.optInt("Status")
                        val tradeNo = optJSONObject.optString("tradeNo")
                        if (status == 1) {
                            // 成功
                            if (isViewAttached) {
                                view.onPaySuccess()
                            }
                           // insertMemberInfo(member,money);
                        } else {
                            queryCount = 0
                            queryMemberOrder(money, tradeNo, member)
                        }
                    } else {
                        UiUtils.showToastLong(jsonObj.optString("msg"))
                        if (isViewAttached) {
                            view.dismissLoading()
                        }
                    }
                }
            }

            override fun onError(response: Response<String>?) {
                super.onError(response)
                OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
                if (isViewAttached) {
                    view.dismissLoading()
                }
            }
        })
    }

    private fun queryMemberOrder(money: Float, tradeNo: String, member: Member) {
        module.queryMemberOrder(money, tradeNo, member, object : StringCallback() {
            override fun onSuccess(response: Response<String>) {
                super.onSuccess(response)
                val jsonObject = JSONObject(response.body())
                val code = jsonObject.optInt("code")
                if (code == BaseResponse.SUCCESS) {
                    if (isViewAttached) {
                        view.onPaySuccess()
                    }
                } else {
                    if(code == BaseResponse.MONEY_NOT_ENOUGH){
                        if (isViewAttached){
                            UiUtils.showToastLong(jsonObject.optString("msg"))
                            view.dismissLoading()
                        }
                        return
                    }
                    queryCount++
                    if (queryCount >= 30) {
                        if (isViewAttached) {
                            UiUtils.showToastLong("支付失败")
                            view.dismissLoading()
                        }
                        OrderManger.getOrderBean()?.paymentStatus =
                            OrderPaymentStatusFlag.PAY_ERROR_STATE
                    } else {
                        ThreadDispatcher.getDispatcher().postOnMainDelayed({
                            queryMemberOrder(money, tradeNo, member)
                        }, 3000)
                    }
                }
            }

            override fun onError(response: Response<String>?) {
                super.onError(response)
                if (isViewAttached) {
                    view.dismissLoading()
                }
                OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
            }
        })
    }

    private fun aliPay(floatMoney: Float, code: String) {
        module.scanPay(floatMoney, code, object : StringCallback() {
            override fun onSuccess(response: Response<String>?) {
                super.onSuccess(response)
                response?.let {
                    val leshua_order_id = Tools.parseGetAuthInfoXML(it.body(), "leshua_order_id")
                    if (!TextUtils.isEmpty(leshua_order_id)){
                        queryCount = 0
                        queryOrder(leshua_order_id)
                    }else{
                        view.dismissLoading()
                        UiUtils.showToastLong("支付失败")
                    }
                }
            }

            override fun onError(response: Response<String>?) {
                super.onError(response)
                view.dismissLoading()
                OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
            }
        })
    }

    private fun wxPay(floatMoney: Float, code: String) {
        module.scanPay(floatMoney, code, object : StringCallback() {
            override fun onSuccess(response: Response<String>?) {
                super.onSuccess(response)
                response?.let {
                    val leshua_order_id = Tools.parseGetAuthInfoXML(it.body(), "leshua_order_id")
                    if (!TextUtils.isEmpty(leshua_order_id)){
                        queryCount = 0
                        queryOrder(leshua_order_id)
                    }else{
                        view.dismissLoading()
                        UiUtils.showToastLong("支付失败")
                    }
                }
            }

            override fun onError(response: Response<String>?) {
                super.onError(response)
                OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
                view.dismissLoading()
            }
        })
    }

    private fun queryOrder(leshuaOrderId: String) {
        if (isViewAttached) {
            module.queryOrder(leshuaOrderId, object : StringCallback() {
                override fun onSuccess(response: Response<String>?) {
                    super.onSuccess(response)
                    var body = response?.body()
                    if (!TextUtils.isEmpty(body)) {
                        val status = Tools.parseGetAuthInfoXML(body, "status")
                        val errorMsg = Tools.parseGetAuthInfoXML(body, "error_msg")
                        val payWay = Tools.parseGetAuthInfoXML(body, "pay_way")
                        when (status) {
                            "0" -> {
                                val vm: InputDialogVM =
                                    ViewModelProviders.of(context as FragmentActivity).get(
                                        InputDialogVM::class.java
                                    )
                                vm.payLoadingData
                                    .postValue(arrayOf(payWay, errorMsg))
                                //支付中
                                queryCount++
                                if (queryCount >= 30) {
                                    updateOrderPayTable(false)
                                    if (isViewAttached)
                                        view.dismissLoading()
                                    OrderManger.getOrderBean()?.paymentStatus =
                                        OrderPaymentStatusFlag.PAY_ERROR_STATE
                                    UiUtils.showToastLong("支付失败")
                                } else {
                                    ThreadDispatcher.getDispatcher().postOnMainDelayed({
                                        queryOrder(leshuaOrderId)
                                    }, 3000L)
                                }
                            }
                            "2" -> {
                                updateOrderPayTable(true)
                                //  UiUtils.showToastLong("支付成功")
                                //支付成功
                                if (isViewAttached)
                                    view.onPaySuccess()
                            }
                            "6" -> {
                                OrderManger.getOrderBean()?.paymentStatus =
                                    OrderPaymentStatusFlag.PAY_ERROR_STATE
                                //订单关闭
                                updateOrderPayTable(false)
                                UiUtils.showToastLong("订单关闭")
                                if (isViewAttached)
                                    view.dismissLoading()
                            }
                            "8" -> {
                                OrderManger.getOrderBean()?.paymentStatus =
                                    OrderPaymentStatusFlag.PAY_ERROR_STATE
                                //支付失败]
                                updateOrderPayTable(false)
                                UiUtils.showToastLong("支付失败")
                                if (isViewAttached)
                                    view.dismissLoading()
                            }
                            else -> {
                                OrderManger.getOrderBean()?.paymentStatus =
                                    OrderPaymentStatusFlag.PAY_ERROR_STATE
                                updateOrderPayTable(false)
                                UiUtils.showToastLong("支付失败")
                                if (isViewAttached)
                                    view.dismissLoading()
                            }
                        }
                    }
                }

                override fun onError(response: Response<String>?) {
                    OrderManger.getOrderBean()?.paymentStatus =
                        OrderPaymentStatusFlag.PAY_ERROR_STATE
                    UiUtils.showToastLong(response?.message())
                    updateOrderPayTable(false)
                }
            })
        }

    }


    private fun updateOrderPayTable(isSuccess: Boolean) {
        var payInfo = OrderPayManger.payInfo
        payInfo.finishDate = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
        if (isSuccess) {
            payInfo.status = OrderPaymentStatusFlag.PAY_COMPLETE_STATE

        } else {
            payInfo.status = OrderPaymentStatusFlag.PAY_ERROR_STATE
        }
        OrderPayDbManger.update(payInfo)
    }
}
