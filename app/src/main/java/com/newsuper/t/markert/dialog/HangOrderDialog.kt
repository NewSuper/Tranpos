package com.newsuper.t.markert.dialog


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.newsuper.t.R
import com.newsuper.t.markert.adapter.HangGoodsAdadpter
import com.newsuper.t.markert.adapter.HangOrderAdadpter
import com.newsuper.t.markert.base.BaseDialog
//import com.newsuper.t.markert.callback.IPayKeyboardListener
import com.newsuper.t.markert.db.manger.OrderItemDbManger
import com.newsuper.t.markert.db.manger.OrderObjectDbManger
//import com.newsuper.t.markert.entity.HangOrderBean
import com.newsuper.t.markert.entity.OrderItem
import com.newsuper.t.markert.entity.OrderObject
import com.newsuper.t.markert.entity.state.OrderHangState
import com.newsuper.t.markert.entity.state.OrderStatusFlag
//import com.newsuper.t.markert.function.hang.helper.HangPresenter
//import com.newsuper.t.markert.function.hang.vm.HangVm
//import com.newsuper.t.markert.ui.cash.manger.OrderItemManger
//import com.newsuper.t.markert.ui.cash.manger.OrderManger
//import com.newsuper.t.markert.utils.CommonKeyHandler
import com.newsuper.t.markert.utils.DateUtil
import com.newsuper.t.markert.utils.UiUtils
import com.newsuper.t.markert.view.CustomGradient
import com.transpos.sale.thread.ThreadDispatcher
//import com.transpos.tools.logger.Logger

/**
 *CREATED BY AM
 *ON 2020/8/10
 *DESCRIPTION: 挂单
 **/
class HangOrderDialog : BaseDialog()
    //, IPayKeyboardListener
{

    @JvmField
    @BindView(R.id.rl_top)
    var rl_top : View? = null

    @JvmField
    @BindView(R.id.rv_order)
    var rv_order : RecyclerView? = null

    @JvmField
    @BindView(R.id.tv_total_money)
    var tv_total_money : TextView? = null

    @JvmField
    @BindView(R.id.tv_total_count)
    var tv_total_count : TextView? = null

    @JvmField
    @BindView(R.id.tv_order_num)
    var tv_order_num : TextView? = null

    @JvmField
    @BindView(R.id.tv_order_time)
    var tv_order_time : TextView? = null

    @JvmField
    @BindView(R.id.tv_post_way)
    var tv_post_way : TextView? = null

    @JvmField
    @BindView(R.id.tv_memo)
    var tv_memo : TextView? = null

    @JvmField
    @BindView(R.id.tv_originalPrice)
    var tv_originalPrice : TextView? = null

    @JvmField
    @BindView(R.id.tv_reduce)
    var tv_reduce : TextView? = null

    @JvmField
    @BindView(R.id.tv_post)
    var tv_post_fee : TextView? = null

    @JvmField
    @BindView(R.id.tv_the_order_price)
    var tv_the_order_price : TextView? = null

    @JvmField
    @BindView(R.id.tv_name)
    var tv_name : TextView? = null

    @JvmField
    @BindView(R.id.tv_phone)
    var tv_phone : TextView? = null

    @JvmField
    @BindView(R.id.rv_goods)
    var rv_goods : RecyclerView? = null

    private val mOrderAdapter : HangOrderAdadpter by lazy {
        HangOrderAdadpter()
    }
    private val mGoodsAdadpter : HangGoodsAdadpter by lazy {
        HangGoodsAdadpter()
    }

    private var mHangCallback : (()->Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_hang_order_layout, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    fun setCallback(callback :()->Unit){
        this.mHangCallback = callback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radius5 = UiUtils.dp2px(5,context).toFloat()
//        val mCommonHandler = CommonKeyHandler(this)
//        dialog.setOnKeyListener { _, _, event ->
//            mCommonHandler.handle(event)
//            false
//        }
//        rl_top?.background = CustomGradient(
//            GradientDrawable.Orientation.LEFT_RIGHT,
//            intArrayOf(Color.parseColor("#1EC1B3"), Color.parseColor("#57D1C6")),
//            floatArrayOf(radius5,radius5,radius5,radius5,0f,0f,0f,0f,0f,0f))
//        rv_order?.layoutManager = LinearLayoutManager(context)
//        rv_order?.adapter = mOrderAdapter
//        rv_goods?.layoutManager = LinearLayoutManager(context)
//        rv_goods?.adapter = mGoodsAdadpter
//        initData()
//        mOrderAdapter.setOnItemClickListener { adapter, _, position ->
//            mOrderAdapter.data.forEachIndexed { index, hangOrderBean -> hangOrderBean.isCheck =
//                index == position }
//            adapter.notifyDataSetChanged()
//            setOrderDetail(mOrderAdapter.data[position].order,OrderItemDbManger.findOrderItemsByTradeno(mOrderAdapter.data[position].order.tradeNo))
//        }
//        ViewModelProviders.of(activity!!).get(HangVm::class.java).deleteOption.observe(this, Observer<String> {
//            deleteAll()
//        })
    }

    private fun setOrderDetail(bean: OrderObject,goods : MutableList<OrderItem>) {
//        tv_order_num?.text = bean.tradeNo
//        tv_order_time?.text = bean.modifyDate
//        tv_post_way?.text = HangPresenter.getPostWay(bean.postWay)
//        tv_memo?.text = bean.remark
//        tv_originalPrice?.text = "${bean.amount}"
//        tv_reduce?.text = "${bean.discountAmount}"
//        tv_post_fee?.text = "${0.00}"
//        tv_the_order_price?.text = "${bean.receivableAmount}"
//        tv_name?.text = bean.memberName
//        tv_phone?.text = HangPresenter.getEncryptionPhone(bean.memberMobileNo)
//        mGoodsAdadpter.data.addAll(goods)
//        mGoodsAdadpter.notifyDataSetChanged()
    }

    private fun initData() {
        ThreadDispatcher.getDispatcher().post {
//            var orders = OrderObjectDbManger.checkHangOrdrs().map { HangOrderBean(false,it) }
//            orders = orders.sortedWith(Comparator{o1, o2->
//                o2.order.modifyDate.compareTo(o1.order.modifyDate)
//            })
//            if(orders.isNotEmpty()){
//                orders.first().isCheck = true
//                val items =
//                    OrderItemDbManger.findOrderItemsByTradeno(orders.first().order.tradeNo)
//                ThreadDispatcher.getDispatcher().postOnMain {
//                    mOrderAdapter.data.addAll(orders)
//                    mOrderAdapter.notifyDataSetChanged()
//                    tv_total_money?.text = HangPresenter.getTotal(orders)[0]
//                    tv_total_count?.text = HangPresenter.getTotal(orders)[1]
//                    setOrderDetail(orders[0].order,items)
//                }
//            }
        }

    }

    @OnClick(R.id.fl_close,R.id.btn_delete,R.id.btn_take)
    fun onViewClick(view: View){
        when(view.id){
            R.id.fl_close -> dismiss()
            R.id.btn_delete -> {
                if(mOrderAdapter.data.isNotEmpty()){
                    if(activity != null){
                      //  HangOrderTipsDialog(activity!!).show()
                    }
                }
            }
            R.id.btn_take -> {
                takeOrder()
            }
            else -> Unit
        }
    }

    /**
     * 取单
     */
    private fun takeOrder() {
//        mOrderAdapter.data
//            .find { it.isCheck }
//            .takeIf { it != null }
//            ?.let {
//                val tradeNo = it.order.tradeNo
//                if(OrderItemManger.getList().isNotEmpty()){
//                    //取单之前如果有未结账的订单  继续挂单
//                    OrderManger.getOrderBean()?.let { o->
//                        o.orderStatus = OrderStatusFlag.HANG_STATE
//                        o.modifyDate = DateUtil.getNowDateStr()
//                        OrderObjectDbManger.update(o)
////                       if(activity != null){
////                           ViewModelProviders.of(activity!!).get(HangVm::class.java).hangOption.postValue(OrderHangState.HANG)
////                       }
//                        mHangCallback?.invoke()
//                    }
//                }
//                it.order.orderStatus = OrderStatusFlag.WAIT_STATE
//                OrderManger.setOrderBean(it.order)
//                OrderItemManger.getList().clear()
//                OrderItemManger.getList().addAll(mGoodsAdadpter.data)
//                OrderObjectDbManger.update(OrderManger.getOrderBean())
//                dismiss()
//
//                if(activity != null){
//                    ViewModelProviders.of(activity!!).get(HangVm::class.java).hangOption.postValue(OrderHangState.TAKE)
//                }
//            }
    }

    private fun deleteAll() {
//        var pos = -1
//        var item : HangOrderBean? = null
//        mOrderAdapter.data.forEachIndexed { index, hangOrderBean ->
//            if(hangOrderBean.isCheck){
//                pos = index
//                item = hangOrderBean
//            }
//        }
//        if(item != null){
//            mOrderAdapter.data.removeAt(pos)
//            if(mOrderAdapter.data.isNotEmpty()){
//                mOrderAdapter.data.first().isCheck = true
//                val tradeNo = mOrderAdapter.data.first().order.tradeNo
//                setOrderDetail(mOrderAdapter.data.first().order,OrderItemDbManger.findOrderItemsByTradeno(tradeNo))
//            } else{
//                clearDetail()
//            }
//            if(activity != null){
//                ViewModelProviders.of(activity!!).get(HangVm::class.java).hangOption.postValue(OrderHangState.DELETE)
//            }
//            mOrderAdapter.notifyDataSetChanged()
//            OrderObjectDbManger.deleteOrder(item!!.order.tradeNo)
//            OrderItemDbManger.deleteItems(item!!.order.tradeNo)
//        }
//        tv_total_money?.text = HangPresenter.getTotal(mOrderAdapter.data)[0]
//        tv_total_count?.text = HangPresenter.getTotal(mOrderAdapter.data)[1]
    }

    private fun clearDetail() {
        tv_order_num?.text = ""
        tv_order_time?.text = ""
        tv_post_way?.text = ""
        tv_originalPrice?.text = "0"
        tv_reduce?.text = "0"
        tv_post_fee?.text = "${0.00}"
        tv_the_order_price?.text = "0"
        tv_name?.text = ""
        tv_phone?.text = ""
        mGoodsAdadpter.data.clear()
        mGoodsAdadpter.notifyDataSetChanged()
    }

//    override fun onKeyClick(keyCode: Int) {
//        when(keyCode){
//            KeyEvent.KEYCODE_DPAD_UP -> {
//                var k = -1
//                mOrderAdapter.data.forEachIndexed { index, hangOrderBean ->
//                    if(hangOrderBean.isCheck) k = index
//                }
//                if(k != -1){
//                    k --
//                    if(k >= 0 && k <= mOrderAdapter.data.size - 1){
//                        mOrderAdapter.data.forEachIndexed { index, hangOrderBean ->
//                            hangOrderBean.isCheck = k == index
//                        }
//                        mOrderAdapter.notifyDataSetChanged()
//                    }
//                }
//            }
//            KeyEvent.KEYCODE_DPAD_DOWN ->{
//                var k = -1
//                mOrderAdapter.data.forEachIndexed { index, hangOrderBean ->
//                    if(hangOrderBean.isCheck) k = index
//                }
//                if(k != -1){
//                    k ++
//                    if(k >= 0 && k <= mOrderAdapter.data.size - 1){
//                        mOrderAdapter.data.forEachIndexed { index, hangOrderBean ->
//                            hangOrderBean.isCheck = k == index
//                        }
//                        mOrderAdapter.notifyDataSetChanged()
//                    }
//                }
//            }
//        }
//    }

}