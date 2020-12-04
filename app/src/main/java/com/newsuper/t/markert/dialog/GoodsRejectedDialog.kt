package com.newsuper.t.markert.dialog


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.FixDialogFragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.trans.network.utils.GsonHelper
import com.newsuper.t.R
import com.newsuper.t.markert.utils.ToolScanner

//import com.newsuper.t.markert.adapter.ItemDecorationPowerful
//import com.newsuper.t.markert.adapter.RejectedGoodsAdapter
//import com.newsuper.t.markert.adapter.RejectedReasonAdapter
//import com.newsuper.t.markert.base.BaseFixDialog
//import com.newsuper.t.markert.callback.IPayKeyboardListener
//import com.newsuper.t.markert.callback.IRejectedCallback
//import com.newsuper.t.markert.entity.*
//import com.newsuper.t.markert.entity.state.PayMode
//import com.newsuper.t.markert.function.rejected.OrderCheckManger
//import com.newsuper.t.markert.utils.*
//import com.newsuper.t.markert.view.CustomGradient
//import com.transpos.tools.logger.Logger

/**
 *DESCRIPTION: 退货
 **/
class GoodsRejectedDialog : FixDialogFragment(), ToolScanner.OnScanSuccessListener
//   , IPayKeyboardListener
{

    @JvmField
    @BindView(R.id.rl_top)
    var rl_top : View? = null

    @JvmField
    @BindView(R.id.tv_rejected_title)
    var tv_rejected_title : TextView? = null

    @JvmField
    @BindView(R.id.tv_rejected_num)
    var tv_rejected_num : TextView? = null

    @JvmField
    @BindView(R.id.tv_rejected_money)
    var tv_rejected_money : TextView? = null

    @JvmField
    @BindView(R.id.rv_goods)
    var rv_goods : RecyclerView? = null

    @JvmField
    @BindView(R.id.rv_rejected_reason)
    var rv_rejected_reason : RecyclerView? = null

    @JvmField
    @BindView(R.id.et_input)
    var et_input : EditText? = null

    @JvmField
    @BindView(R.id.root_view)
    var root_view : View? = null

//    private var mBuilder : Builder? = null
//    private var mCurrOrder : OrderObject? = null
//    private var couponMoney = 0.0
//    private val mGoodsAdapter : RejectedGoodsAdapter by lazy {
//        RejectedGoodsAdapter(R.layout.item_rejected_goods_list_layout)
//    }
//    private val mReasonAdapter : RejectedReasonAdapter by lazy {
//        RejectedReasonAdapter()
//    }
//    private val mReasonList : List<RejectedReasonBean> by lazy {
//        listOf(
//            RejectedReasonBean("0","不想购买"),
//            RejectedReasonBean("1","质量问题"),
//            RejectedReasonBean("2","过期商品"),
//            RejectedReasonBean("3","发现异物"),
//            RejectedReasonBean("4","其他")
//        )
//    }
//    private val toolScanner = object : ToolScanner(this){
////        override fun onScanCallbackChar(aChar: Char, clearFlag: Boolean) {
////            if(clearFlag){
////                et_input?.text?.clear()
////            } else{
////                et_input?.text?.append(aChar)
////            }
////        }
//    }
//    private var mKeyHandler : PayMoneyHanlder? = null
//
//    override fun onStart() {
//        super.onStart()
//        if (mBuilder == null) {
//            mBuilder = Builder()
//        }
//        val window = dialog.window
//        val params = window.attributes
//        params.gravity = mBuilder!!.gravity
//        window.attributes = params
//        window.setBackgroundDrawable(ColorDrawable())
//        window.setWindowAnimations(mBuilder!!.animations)
//        //设置边距
//        //设置边距
//        val dm = DisplayMetrics()
//        activity!!.windowManager.defaultDisplay.getMetrics(dm)
//        window.setLayout(mBuilder!!.withParams, mBuilder!!.heightParams)
//        isCancelable = false
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.dialog_goods_rejected_layout, container, false)
//        ButterKnife.bind(this, view)
//        initView()
//        return view
//    }
//
//    private fun initView() {
//        mKeyHandler = PayMoneyHanlder(this)
//        dialog.setOnKeyListener { _, _, event ->
//            toolScanner.analysisKeyEvent(event)
//            mKeyHandler?.handlle(event)
//            false
//        }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val radius5 = UiUtils.dp2px(5,context).toFloat()
//        val radius2 = UiUtils.dp2px(2,context).toFloat()
//        rl_top?.background = CustomGradient(
//            GradientDrawable.Orientation.LEFT_RIGHT,
//            intArrayOf(Color.parseColor("#1EC1B3"), Color.parseColor("#57D1C6")),
//            floatArrayOf(radius5,radius5,radius5,radius5,0f,0f,0f,0f,0f,0f))
//        tv_rejected_title?.background = CustomGradient(
//            GradientDrawable.Orientation.LEFT_RIGHT,
//            intArrayOf(Color.parseColor("#1EC1B3"), Color.parseColor("#57D1C6")),
//            floatArrayOf(radius2,radius2,radius2,radius2,0f,0f,0f,0f))
//        tv_rejected_num?.text = "退货数量：0"
//        tv_rejected_money?.text = "退金额：0.00"
//        rv_goods?.layoutManager = LinearLayoutManager(context)
//        rv_goods?.adapter = mGoodsAdapter
//        rv_goods?.addItemDecoration(
//            ItemDecorationPowerful(ItemDecorationPowerful.VERTICAL_DIV,
//                Color.parseColor("#BFBFBF"),
//                1)
//        )
//
//        rv_rejected_reason?.layoutManager = GridLayoutManager(context,2)
//        rv_rejected_reason?.adapter = mReasonAdapter
//        rv_rejected_reason?.addItemDecoration(ItemDecorationPowerful(ItemDecorationPowerful.GRID_DIV,Color.parseColor("#FFFFFF"),UiUtils.dp2px(8,context)))
//        mReasonAdapter.setOnItemClickListener { adapter, _, position ->
//            mReasonList.forEachIndexed { index, it -> if(index != position) it.check = false }
//            mReasonList[position].check = !mReasonList[position].check
//            adapter.notifyDataSetChanged()
//        }
//        mGoodsAdapter.setOnItemChildClickListener { adapter, view, position ->
//            when(view.id){
//                R.id.iv_check ->{
//                    mGoodsAdapter.data[position].isRejected = !mGoodsAdapter.data[position].isRejected
//                    adapter.notifyDataSetChanged()
//                    refreshTotal()
//                }
//                R.id.tv_count ->{
//                    val p = mGoodsAdapter.data[position]
//                    if(p.product?.weightFlag == 0){
//                        p.isEdit = true
//                        adapter.notifyDataSetChanged()
//                    }
//                }
//                R.id.btnDecrease ->{
//                    val p = mGoodsAdapter.data[position]
//                    var count = p.showCount.toInt()
//                    count --
//                    if(count < 1){
//                        count = 1
//                    }
//                    // p.product!!.rquantity = p.product!!.rquantity + count
//                    p.showCount = count.toDouble()
//                    adapter.notifyDataSetChanged()
//                    refreshTotal()
//                }
//                R.id.btnIncrease ->{
//                    val p = mGoodsAdapter.data[position]
//                    var count = p.showCount.toInt()
//                    count ++
//                    if(count > p.product!!.quantity - p.product!!.rquantity){
//                        count = (p.product!!.quantity - p.product!!.rquantity).toInt()
//                    }
//                    //p.product!!.rquantity = p.product!!.rquantity + count
//                    p.showCount = count.toDouble()
//                    adapter.notifyDataSetChanged()
//                    refreshTotal()
//                }
//            }
//
//        }
//        initData()
//    }
//
//    private fun initData() {
//        mReasonAdapter.setNewData(mReasonList)
//        refreshTotal()
//        val tradeNo = arguments?.getString(KeyConstrant.KEY_TRADE_NO,null)
//        if(!TextUtils.isEmpty(tradeNo)){
//            PayHolder.mPayMode = PayMode.REJECTED_ORDER
//            OrderCheckManger.checkOrdersByTradeNo(tradeNo!!,object : IRejectedCallback{
//                override fun onSuceess(order: OrderObject?, list: List<OrderItem>) {
//                    addData(order,list)
//                }
//
//            })
//        }
//    }
//
//    private fun refreshTotal(){
//        tv_rejected_num?.text = "退货数量：${mGoodsAdapter.getAllCount()}"
//        tv_rejected_money?.text = "退金额：${mGoodsAdapter.getPrice()}"
//    }
//
//    @OnClick(R.id.fl_close,R.id.btn_cancel,R.id.btn_all_check,R.id.btn_confirm,R.id.btn_check,
//        R.id.root_view,R.id.ll_left,R.id.rl_right)
//    fun onViewClick(view: View){
//        when(view.id){
//            R.id.fl_close,R.id.btn_cancel ->{
//                PayHolder.mPayMode = PayMode.PAY
//                dismiss()
//            }
//            R.id.btn_all_check ->{
//                mGoodsAdapter.data.forEach {
//                    it.isRejected = true
//                    mGoodsAdapter.notifyDataSetChanged()
//                    refreshTotal()
//                }
//            }
//            R.id.btn_confirm ->{
//                doConfirm()
//            }
//            R.id.btn_check ->{
//                if(TextUtils.isEmpty(et_input?.text.toString())){
//                    UiUtils.showToastShort("请输入订单号")
//                } else{
//                    OrderCheckManger.checkOrdersByTradeNo(et_input!!.text.toString(),object : IRejectedCallback{
//                        override fun onSuceess(order: OrderObject?, list: List<OrderItem>) {
//                            addData(order,list)
//                        }
//
//                    })
//                }
//            }
//            R.id.root_view ,R.id.ll_left,R.id.rl_right ->{
//                Logger.d("KeyboardUtils.hideSoftKeyboard")
//                KeyboardUtils.hideSoftKeyboard(context,root_view)
//            }
//        }
//    }
//
//    private fun doConfirm() {
//        if (mGoodsAdapter.getAllCount() == 0) {
//            UiUtils.showToastShort("请至少选择一个商品")
//        } else {
//            dismiss()
//            mGoodsAdapter.data.forEach {
//                it.product!!.rquantity += it.showCount
//            }
//            val json = mGoodsAdapter.getCheckProducts()
//            val rejectedDialog = BaseFixDialog.Builder().setCancelable(true).build(RejectedMoneyDialog::class.java,context)
//            var modify = false
//            if (mGoodsAdapter.getPrice().toDouble() < 0.0) {
//                UiUtils.showToastShort("退款金额异常")
//                return
//            }
//            if (mGoodsAdapter.getPrice().toDouble() != mCurrOrder!!.receivableAmount) {
//                //与原单金额不一致，更新订单金额
//                modify = true
//                modifyOrder()
//            }
//            val moling = if(mCurrOrder?.isRejectedMoling == 0) mCurrOrder?.malingAmount ?: 0.0 else 0.0
//            val accountsBean = AccountsBean()
//                .setTotalMoney(mGoodsAdapter.getPrice())
//                .setReduction(
//                    "${mGoodsAdapter.getOriginPrice().toDouble() - mGoodsAdapter.getPrice().toDouble() - mGoodsAdapter.couponMoney - moling}"
//                ).setChangeMoney(0.0)
//                .setTotalCount(mGoodsAdapter.getAllCount().toDouble())
//                .setOrderJsonStr(GsonHelper.toJson(mCurrOrder))
//                .setModify(modify)
//                .setMolingMoney(moling)
//                .setHoldCoupon(couponMoney)
//                .setReduceDiscountMoney(mGoodsAdapter.reduceDiscountMoney)
//                .setCounponMoney(mGoodsAdapter.couponMoney)
//
//            val reason = mReasonAdapter.data.find { it.check }
//            rejectedDialog.setDataJson(json)
//            rejectedDialog.setAccountsBean(accountsBean)
//            rejectedDialog.setReason(reason?.reason)
//            rejectedDialog.show()
//        }
//    }
//
//    private fun modifyOrder() {
//        mCurrOrder?.let {
//            it.totalQuantity = mGoodsAdapter.getAllCount().toDouble()
//            it.receivableAmount = mGoodsAdapter.getPrice().toDouble()
//            it.amount = mGoodsAdapter.getOriginPrice().toDouble()
//            it.discountAmount = it.amount - it.receivableAmount
//        }
//    }
//
//    private fun addData(order: OrderObject?, list: List<OrderItem>) {
//        et_input!!.text.clear()
//        if(order == null){
//            UiUtils.showToastShort("订单不存在")
//        } else{
//            mCurrOrder = order
//            if(order.isRejectedMoling == 0){
//                mGoodsAdapter.order = order
//            }
//            if(order?.isRejectedCoupon == 0){
//                mGoodsAdapter.setCouponMoney(order.tradeNo)
//                couponMoney = mGoodsAdapter.couponMoney
//            }
////            mGoodsAdapter.setReduceDiscount(order.tradeNo)
//            mGoodsAdapter.data.clear()
//            list.filter {
//                it.quantity - it.rquantity > 0
//            }.forEach {
//                val item = RejectedGoodsWrap()
//                item.product = it
//                item.showCount = it.quantity - it.rquantity
//                mGoodsAdapter.addData(item)
//            }
//            if(mGoodsAdapter.data.isEmpty()){
//                UiUtils.showToastShort("该订单无商品可退")
//            }
//            refreshTotal()
//        }
//    }
//
//    class Builder {
//        var withParams = ViewGroup.LayoutParams.MATCH_PARENT
//        var heightParams = ViewGroup.LayoutParams.MATCH_PARENT
//        var gravity = Gravity.CENTER
//        var animations = R.style.dialog_animation_fade
//
//
//        fun setWithParams(withParams: Int): Builder {
//            this.withParams = withParams
//            return this
//        }
//
//        fun setGravity(gravity: Int): Builder {
//            this.gravity = gravity
//            return this
//        }
//
//        fun setAnimations(animations: Int): Builder {
//            this.animations = animations
//            return this
//        }
//
//        fun setHeightParams(heightParams: Int): Builder {
//            this.heightParams = heightParams
//            return this
//        }
//
//        fun build(): GoodsRejectedDialog {
//            val dialog = GoodsRejectedDialog()
//            dialog.mBuilder = this
//            return dialog
//        }
//    }
//
    override fun onScanSuccess(barcode: String?) {
        if(!TextUtils.isEmpty(barcode)){
            barcode?.let {
//                OrderCheckManger.checkOrdersByTradeNo(it,object : IRejectedCallback{
//                    override fun onSuceess(order: OrderObject?, list: List<OrderItem>) {
//                        KeyboardUtils.hideSoftKeyboard(context, et_input)
//                        addData(order,list)
//                    }
//                })
            }
        }
    }

//    override fun onKeyClick(keyCodeTag: Int) {
//        if(keyCodeTag == KeyConstrant.KEY_ACTION_PAY_ENTER){
//            doConfirm()
//        }
//    }

}