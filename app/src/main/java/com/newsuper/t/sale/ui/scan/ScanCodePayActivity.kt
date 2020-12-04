package com.newsuper.t.sale.ui.scan

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import butterknife.OnClick
import com.newsuper.t.R
import com.newsuper.t.sale.base.mvp.BaseMvpActivity
import com.newsuper.t.sale.db.manger.OrderObjectDbManger
import com.newsuper.t.sale.entity.state.OrderStatusFlag
import com.newsuper.t.sale.ui.food.FoodActivity
import com.newsuper.t.sale.ui.scan.manger.OrderManger
import com.newsuper.t.sale.ui.scan.manger.OrderManger.getOrderBean
import com.newsuper.t.sale.ui.scan.mvp.PayContract
import com.newsuper.t.sale.ui.scan.mvp.PayPresenter
import com.newsuper.t.sale.ui.scan.vm.InputDialogVM
import com.newsuper.t.sale.utils.ToolScanner
import com.newsuper.t.sale.utils.UiUtils
import com.newsuper.t.sale.view.CustomGradient
import com.newsuper.t.sale.view.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_scan_code_pay_layout.*
import kotlinx.android.synthetic.main.viw_actionbar_layout.*

class ScanCodePayActivity : BaseMvpActivity<PayPresenter>(), ToolScanner.OnScanSuccessListener,
    PayContract.View {

    private var money : Float = 0f
    private val mLoadDialog : LoadingDialog = LoadingDialog(this,"正在支付...")

    override fun getLayoutId(): Int {
        return R.layout.activity_scan_code_pay_layout
    }


    override fun initView() {
        super.initView()
        ll_back?.background = CustomGradient(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                Color.parseColor("#FFB200"),
                Color.parseColor("#FF7B00")
            ),
            UiUtils.dp2px(4, this).toFloat()
        )
        tv_deal.visibility = View.VISIBLE
        tv_deal.text = "放弃购买"
        var vm = ViewModelProviders.of(this).get(InputDialogVM::class.java)
        vm.passwordData.observe(this, Observer<String> {
            it?.let {
                presenter.continueMemberPay(it)
            }
        })
        vm.cancelDialog.observe(this,Observer<String>{
            mLoadDialog.dismiss()
        })
    }


    @OnClick(R.id.tv_deal,R.id.ll_back)
    fun onViewClick(v : View){
        when(v.id){
            R.id.tv_deal -> {
                //更新取消订单状态
                //更新取消订单状态
                OrderManger.getOrderBean()!!.orderStatus = OrderStatusFlag.CANCEL_STATE
                OrderObjectDbManger.update(getOrderBean())
                OrderManger.clear()
                startActivity(FoodActivity::class.java)
            }
            R.id.ll_back -> finish()
        }
    }

    override fun getScanner(): ToolScanner {
        return ToolScanner(this)
    }

    override fun initData() {
        super.initData()
        money = intent.getStringExtra("money").toFloat()
    }

    override fun onScanSuccess(barcode: String) {
        if(mLoadDialog.isShowing){
            UiUtils.showToastLong("请稍后再试")
            return
        }
        mLoadDialog.setCancelable(false)
        mLoadDialog.show()
        presenter.scanCode(money,barcode)
    }

    override fun createPresenter(): PayPresenter {
        return PayPresenter()
    }

    override fun dismissLoading() {
        mLoadDialog.dismiss()
    }

    override fun onPaySuccess() {
        mLoadDialog.dismiss()
        startActivity(PaySuccessActivity::class.java)
    }
}