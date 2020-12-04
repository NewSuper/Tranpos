package com.newsuper.t.sale.ui.scan.mvp

import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import com.trans.network.callback.JsonObjectCallback
import com.trans.network.callback.StringCallback
import com.trans.network.model.Response
import com.newsuper.t.sale.base.mvp.BasePresenter
import com.newsuper.t.sale.db.manger.OrderPayDbManger
import com.newsuper.t.sale.entity.BaseResponse
import com.newsuper.t.sale.entity.EntityResponse
import com.newsuper.t.sale.entity.Member
import com.newsuper.t.sale.entity.state.OrderPaymentStatusFlag
import com.newsuper.t.sale.entity.state.PayChannelFlag
import com.newsuper.t.sale.ui.scan.manger.OrderManger
import com.newsuper.t.sale.ui.scan.manger.OrderPayManger
import com.newsuper.t.sale.ui.scan.mvp.PayContract
import com.newsuper.t.sale.utils.DateUtil
import com.newsuper.t.sale.utils.StringUtils
import com.newsuper.t.sale.utils.Tools
import com.newsuper.t.sale.utils.UiUtils
import com.newsuper.t.sale.view.dialog.MemberInputPasswardDialog
import com.transpos.sale.thread.ThreadDispatcher
import org.json.JSONObject

class PayPresenter : BasePresenter<PayModel, PayContract.View>(), PayContract.Presenter {

    private var queryCount = 0
    private var mMember : Member? = null
    private var mMoney : Float = 0f

    override fun createModule(): PayModel {
        return PayModel()
    }

    override fun scanCode(money: Float,code: String) {
        val start = code.substring(0,2)
        if(StringUtils.isNumeric(start)){
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
                    wxPay(money,code)
                }
                in 25..30 -> {
                    //alipay
                    OrderPayManger.payInfo.payNo = OrderPayManger.PayModeEnum.PAYAIL.payNo
                    OrderPayManger.payInfo.name = OrderPayManger.PayModeEnum.PAYAIL.payName
                    OrderPayManger.payInfo.payChannel = PayChannelFlag.LESHUA_PAY
                    aliPay(money,code)
                }
                else -> {
                    OrderPayManger.payInfo.payNo = OrderPayManger.PayModeEnum.PAYMEMBER.payNo
                    OrderPayManger.payInfo.name = OrderPayManger.PayModeEnum.PAYMEMBER.payName
                    performMemberPay(money,code)
                }
            }
            OrderPayDbManger.insert(OrderPayManger.payInfo)
        } else{
            view.dismissLoading()
            UiUtils.showToastLong("条码错误")
        }
    }

    private fun performMemberPay(money: Float, code: String) {
        module.queryMember(code,object : JsonObjectCallback<EntityResponse<Member>>(){
            override fun onSuccess(response: Response<EntityResponse<Member>>?) {
                super.onSuccess(response)
                response?.let {
                    var body = it.body()
                    if(body.code == BaseResponse.SUCCESS){
                        val member = body.data
                        if(member.isNoPwd == 1 ){
                            if(member.npAmount >= money){
                                memberPay(money,member)
                            } else{
                                mMember = member
                                mMoney = money
                                mMember?.needShowPwdDialog = true
                                showPasswordDialog()
                            }
                        } else{
                            mMember = member
                            mMoney = money
                            mMember?.needShowPwdDialog = true
                            showPasswordDialog()
                        }
                        OrderPayManger.setMemberInfo(member)
                    } else{
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
    private fun showPasswordDialog() {
        val dialog : MemberInputPasswardDialog = MemberInputPasswardDialog.Builder().build()
        dialog.show((context as? FragmentActivity)?.supportFragmentManager,"p")
    }

    /**
     * 输入密码后调支付
     */
    override fun continueMemberPay(password : String){
        mMember?.let {
            it.password = password
            memberPay(mMoney,it)
        }
    }

    private fun memberPay(money: Float,member: Member){
        module.memberPay(money,member,object : StringCallback(){
            override fun onSuccess(response: Response<String>?) {
                super.onSuccess(response)
                response?.let {
                    val jsonObj :JSONObject = JSONObject(it.body())
                    val code = jsonObj.optInt("code")
                    if(code == BaseResponse.SUCCESS){
                        var optJSONObject = jsonObj.optJSONObject("data")
                        val status = optJSONObject.optInt("Status")
                        val tradeNo = optJSONObject.optString("tradeNo")
                        if(status == 1){
                            // 成功
                            if(isViewAttached){
                                view.onPaySuccess()
                            }
                        } else{
                            queryCount = 0
                            queryMemberOrder(money,tradeNo,member)
                        }
                    } else{
                        UiUtils.showToastLong(jsonObj.optString("msg"))
                        if(isViewAttached){
                            view.dismissLoading()
                        }
                    }
                }
            }

            override fun onError(response: Response<String>?) {
                super.onError(response)
                OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
                if(isViewAttached){
                    view.dismissLoading()
                }
            }
        })
    }

    private fun queryMemberOrder(money : Float ,tradeNo: String,member : Member) {
        module.queryMemberOrder(money,tradeNo,member,object : StringCallback(){
            override fun onSuccess(response: Response<String>) {
                super.onSuccess(response)
                val jsonObject = JSONObject(response.body())
                val code = jsonObject.optInt("code")
                if(code == BaseResponse.SUCCESS){
                    if(isViewAttached){
                        view.onPaySuccess()
                    }
                } else{
                    if(code == BaseResponse.MONEY_NOT_ENOUGH){
                        if(isViewAttached){
                            UiUtils.showToastLong(jsonObject.optString("msg"))
                            view.dismissLoading()
                        }
                        return
                    }
                    queryCount ++
                    if(queryCount >= 30){
                        if(isViewAttached){
                            UiUtils.showToastLong("支付失败")
                            view.dismissLoading()
                        }
                        OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
                    } else{
                        ThreadDispatcher.getDispatcher().postOnMainDelayed({
                            queryMemberOrder(money,tradeNo,member)
                        },3000)
                    }
                }
            }

            override fun onError(response: Response<String>?) {
                super.onError(response)
                if(isViewAttached){
                    view.dismissLoading()
                }
                OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
            }
        })
    }

    private fun aliPay(floatMoney: Float,code: String) {
        module.scanPay(floatMoney,code,object : StringCallback(){
            override fun onSuccess(response: Response<String>?) {
                super.onSuccess(response)
                response?.let {
                    val leshua_order_id = Tools.parseGetAuthInfoXML(it.body(),"leshua_order_id")
                    if(TextUtils.isEmpty(leshua_order_id)){
                        view.dismissLoading()
                        UiUtils.showToastLong("支付失败")
                    } else{
                        queryCount = 0
                        queryOrder(leshua_order_id)
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

    private fun wxPay(floatMoney: Float,code: String) {
        module.scanPay(floatMoney,code,object : StringCallback(){
            override fun onSuccess(response: Response<String>?) {
                super.onSuccess(response)
                response?.let {
                    val leshua_order_id = Tools.parseGetAuthInfoXML(it.body(),"leshua_order_id")
                    if(TextUtils.isEmpty(leshua_order_id)){
                        view.dismissLoading()
                        UiUtils.showToastLong("支付失败")
                    } else{
                        queryCount = 0
                        queryOrder(leshua_order_id)
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
        if(isViewAttached) {
            module.queryOrder(leshuaOrderId,object : StringCallback(){
                override fun onSuccess(response: Response<String>?) {
                    super.onSuccess(response)
                    var body = response?.body()
                    if(!TextUtils.isEmpty(body)){
                        val status = Tools.parseGetAuthInfoXML(body,"status")
                        when(status){
                            "0" ->{
                                //支付中
                                queryCount ++
                                if(queryCount >= 30){
                                    updateOrderPayTable(false)
                                    if(isViewAttached)
                                        view.dismissLoading()
                                    OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
                                    UiUtils.showToastLong("支付失败")
                                } else{
                                    ThreadDispatcher.getDispatcher().postOnMainDelayed({
                                        queryOrder(leshuaOrderId)
                                    },3000L)
                                }
                            }
                            "2"->{
                                updateOrderPayTable(true)
                                UiUtils.showToastLong("支付成功")
                                //支付成功
                                if(isViewAttached)
                                view.onPaySuccess()
                            }
                            "6"->{
                                OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
                                //订单关闭
                                updateOrderPayTable(false)
                                UiUtils.showToastLong("订单关闭")
                                if(isViewAttached)
                                view.dismissLoading()
                            }
                            "8"->{
                                OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
                                //支付失败]
                                updateOrderPayTable(false)
                                UiUtils.showToastLong("支付失败")
                                if(isViewAttached)
                                view.dismissLoading()
                            }
                            else ->{
                                OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
                                updateOrderPayTable(false)
                                UiUtils.showToastLong("支付失败")
                                if(isViewAttached)
                                view.dismissLoading()
                            }
                        }
                    }
                }

                override fun onError(response: Response<String>?) {
                    OrderManger.getOrderBean()?.paymentStatus = OrderPaymentStatusFlag.PAY_ERROR_STATE
                    UiUtils.showToastLong(response?.message())
                    updateOrderPayTable(false)
                }
            })
        }

    }

    fun startFace(money: Float){
        module.startFace(money,object : StringCallback(){
            override fun onSuccess(response: Response<String>?) {
                super.onSuccess(response)
                //创建刷脸支付订单
                OrderPayManger.createPayInfo()
                OrderPayManger.payInfo.payTime = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
                OrderPayManger.payInfo.payNo = OrderPayManger.PayModeEnum.PAYWX.payNo
                OrderPayManger.payInfo.name = OrderPayManger.PayModeEnum.PAYWX.payName
                OrderPayManger.payInfo.payChannel = PayChannelFlag.LESHUA_PAY
                OrderPayDbManger.insert(OrderPayManger.payInfo!!)
                response?.let {
                    val leshua_order_id = Tools.parseGetAuthInfoXML(it.body(),"leshua_order_id")
                    queryCount = 0
                    if(leshua_order_id != null){
                        queryOrder(leshua_order_id)
                    } else{
                        view.dismissLoading()
                    }
                }
            }

            override fun onError(response: Response<String>?) {
                super.onError(response)
                view.dismissLoading()
                UiUtils.showToastLong(response?.message())
            }
        })
    }

    private fun updateOrderPayTable(isSuccess : Boolean){
        var payInfo = OrderPayManger.payInfo
        payInfo.finishDate = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
        if(isSuccess){
            payInfo.status = OrderPaymentStatusFlag.PAY_COMPLETE_STATE

        } else{
            payInfo.status = OrderPaymentStatusFlag.PAY_ERROR_STATE
        }
        OrderPayDbManger.update(payInfo)
    }
}