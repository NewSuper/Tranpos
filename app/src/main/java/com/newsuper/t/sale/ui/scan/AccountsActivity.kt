package com.newsuper.t.sale.ui.scan

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.newsuper.t.R

import com.newsuper.t.sale.base.mvp.BaseMvpActivity
import com.newsuper.t.sale.entity.AccountsBean
import com.newsuper.t.sale.entity.state.OrderStatusFlag
import com.newsuper.t.sale.ui.food.FoodActivity
import com.newsuper.t.sale.ui.scan.manger.OrderManger
import com.newsuper.t.sale.ui.scan.mvp.PayContract
import com.newsuper.t.sale.ui.scan.mvp.PayPresenter
import com.newsuper.t.sale.utils.CountTimer
import com.newsuper.t.sale.ui.scan.PaySuccessActivity
import com.newsuper.t.sale.ui.scan.ScanCodePayActivity
import com.newsuper.t.sale.utils.DateUtil
import com.newsuper.t.sale.utils.KeyConstrant
import com.newsuper.t.sale.utils.UiUtils
import com.newsuper.t.sale.view.CustomGradient
import com.newsuper.t.sale.view.dialog.LoadingDialog

class AccountsActivity : BaseMvpActivity<PayPresenter>(), PayContract.View {

    @JvmField
    @BindView(R.id.tv_total_price)
    var tv_total_price : TextView? = null

    @JvmField
    @BindView(R.id.tv_count)
    var tv_count : TextView? = null

    @JvmField
    @BindView(R.id.tv_reduction)
    var tv_reduction : TextView? = null

    @JvmField
    @BindView(R.id.ll_face_pay)
    var ll_face_pay : LinearLayout? = null

    @JvmField
    @BindView(R.id.ll_scan_pay)
    var ll_scan_pay : LinearLayout? = null

    @JvmField
    @BindView(R.id.ll_back)
    var ll_back : LinearLayout? = null

    @JvmField
    @BindView(R.id.tv_deal)
    var tv_deal : TextView? = null

    private var mTimerCount : CountTimer? = null
    private var accountsBean : AccountsBean? = null
    private var mLoadingDialog : LoadingDialog? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_scan_accounts
    }

    override fun initView() {
        super.initView()
        tv_deal?.visibility = View.VISIBLE
        ll_face_pay?.background = CustomGradient(GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.parseColor("#0082FF"),Color.parseColor("#00BBFF")),
            UiUtils.dp2px(10,this).toFloat())
        ll_scan_pay?.background = CustomGradient(GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.parseColor("#FF9C00"),Color.parseColor("#FFC000")),
            UiUtils.dp2px(10,this).toFloat())
        ll_back?.background = CustomGradient(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                Color.parseColor("#FFB200"),
                Color.parseColor("#FF7B00")
            ),
            UiUtils.dp2px(4, this).toFloat()
        )
        mTimerCount = object : CountTimer(300*1000L,1000L,this,tv_deal!!){
            override fun onFinish() {
                OrderManger.getOrderBean()?.orderStatus = OrderStatusFlag.CANCEL_STATE
                super.onFinish()
            }
        }
        mTimerCount?.start()
    }


    @OnClick(R.id.ll_back,R.id.ll_face_pay,R.id.ll_scan_pay,R.id.tv_deal)
    fun onViewClick(view : View){
        when(view.id){
            R.id.tv_deal -> {
                OrderManger.getOrderBean()?.orderStatus = OrderStatusFlag.CANCEL_STATE
                startActivity(FoodActivity::class.java)
            }
            R.id.ll_back -> finish()
            R.id.ll_scan_pay -> {
                val intent = Intent(this, ScanCodePayActivity::class.java)
                intent.putExtra("money",accountsBean?.totalMoney)
                startActivity(intent)
            }
            R.id.ll_face_pay -> {
                accountsBean?.totalMoney?.toFloat()?.let {
                    if(mLoadingDialog == null){
                        mLoadingDialog = LoadingDialog(this,"支付中...")
                        mLoadingDialog!!.setCancelable(false)
                    }
                    mLoadingDialog?.show()
                    presenter.startFace(it)
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        mTimerCount?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimerCount?.cancel()
        mTimerCount = null
    }

    override fun onResume() {
        super.onResume()
        mTimerCount?.start()
    }

    override fun initData() {
        super.initData()

        accountsBean = intent.getSerializableExtra(KeyConstrant.KEY_ACCOUNT_BEAN) as? AccountsBean
        accountsBean?.let {
            val countStr = """
                件数：  <font color="#0093FF">${it.totalCount} </font>
            """.trimIndent()
            val reductionStr = """
                优惠：  ￥<font color="#0093FF">${it.reduction}</font>
            """.trimIndent()
            tv_count?.setText(Html.fromHtml(countStr))
            tv_reduction?.setText(Html.fromHtml(reductionStr))
            tv_total_price?.setText("￥${it.totalMoney}")
        }
    }

    override fun createPresenter(): PayPresenter {
        return PayPresenter()
    }

    override fun onPaySuccess() {
        mLoadingDialog?.dismiss()
        startActivity(PaySuccessActivity::class.java)
    }

    override fun dismissLoading() {
        super.dismissLoading()
        mLoadingDialog?.dismiss()
    }
}