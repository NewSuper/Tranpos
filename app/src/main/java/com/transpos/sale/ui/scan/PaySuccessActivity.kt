package com.transpos.sale.ui.scan

import android.content.Intent
import android.view.View
import butterknife.OnClick
import com.transpos.market.R
import com.transpos.sale.base.BaseActivity
import com.transpos.sale.db.manger.OrderItemDbManger
import com.transpos.sale.db.manger.OrderObjectDbManger
import com.transpos.sale.entity.state.OrderPaymentStatusFlag
import com.transpos.sale.entity.state.OrderStatusFlag
import com.transpos.sale.service.PrintService
import com.transpos.sale.thread.ThreadDispatcher
import com.transpos.sale.ui.food.FoodActivity
import com.transpos.sale.ui.scan.manger.OrderItemManger
import com.transpos.sale.ui.scan.manger.OrderManger
import com.transpos.sale.utils.CountTimer
import com.transpos.sale.utils.DateUtil
import kotlinx.android.synthetic.main.activity_pay_success_layout.*

class PaySuccessActivity : BaseActivity() {

    private lateinit var mTimerCount : CountTimer

    override fun getLayoutId(): Int {
       return R.layout.activity_pay_success_layout
    }

    override fun initView() {
        super.initView()
        tv_count_timer.text = "06"
        mTimerCount = CountTimer(6000,1000,this,tv_count_timer,true)
        mTimerCount.start()
    }

    override fun initData() {
        super.initData()
        //防止数据量过大 在线程中操作数据库
        ThreadDispatcher.getDispatcher().post{
            OrderManger.getOrderBean()?.let {
                it.orderStatus = OrderStatusFlag.PAY_STATE
                it.paymentStatus = OrderPaymentStatusFlag.PAY_COMPLETE_STATE
                OrderObjectDbManger.update(it)
                if(OrderItemManger.getList().isNotEmpty()){
                    OrderItemDbManger.update(OrderItemManger.getList())
                }
            }
        }
        //开启打印服务
        val work = Intent()
        OrderManger.getOrderBean()?.let{
            work.putExtra("tradeNo",it.tradeNo)
        }
        PrintService.enqueueWork(this,work)
    }

    @OnClick(R.id.btn_back_home)
    fun onViewClick(v : View){
        mTimerCount.cancel()
        startActivity(FoodActivity::class.java)
        finish()
    }

    override fun onDestroy() {
        OrderManger.getOrderBean()?.let {
            it.orderStatus = OrderStatusFlag.COMPLETE_STATE
            it.finishDate = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
            OrderItemManger.getList().forEach {
                o -> o.finishDate = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
            }
        }
        super.onDestroy()
    }
}