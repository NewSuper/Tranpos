package com.newsuper.t.sale.view.dialog

import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.FixDialogFragment
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.newsuper.t.R
import com.newsuper.t.sale.db.manger.OrderPayDbManger
import com.newsuper.t.sale.ui.scan.manger.OrderPayManger
import com.newsuper.t.sale.ui.scan.vm.InputDialogVM
import com.newsuper.t.sale.utils.UiUtils
import com.newsuper.t.sale.view.HandInputKeyboardView
import kotlinx.android.synthetic.main.dialog_member_input_password_layout.*

class MemberInputPasswardDialog : FixDialogFragment(),
    HandInputKeyboardView.OnKeyboardInputListener {

    private var mBuilder : Builder? = null

    override fun onStart() {
        super.onStart()
        if (mBuilder == null) {
            mBuilder = Builder()
        }
        val window = dialog.window
        val params = window.attributes
        params.gravity = mBuilder!!.gravity
        window.attributes = params
        window.setBackgroundDrawable(ColorDrawable())
        window.setWindowAnimations(mBuilder!!.animations)
        //设置边距
        //设置边距
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        window.setLayout(mBuilder!!.withParams, mBuilder!!.heightParams)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_member_input_password_layout, container, false)
        ButterKnife.bind(this,view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mKeyboard.setmKeyboardInputListener(this)
    }

    class Builder{
        var withParams = ViewGroup.LayoutParams.MATCH_PARENT
        var heightParams = ViewGroup.LayoutParams.WRAP_CONTENT
       var gravity = Gravity.CENTER
        var animations = R.style.dialog_animation_fade


        fun setWithParams(withParams: Int): Builder {
            this.withParams = withParams
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setAnimations(animations: Int): Builder {
            this.animations = animations
            return this
        }

        fun setHeightParams(heightParams: Int): Builder {
            this.heightParams = heightParams
            return this
        }

        fun build() : MemberInputPasswardDialog {
            val dialog = MemberInputPasswardDialog()
            dialog.mBuilder = this
            return dialog
        }
    }

    @OnClick(R.id.iv_clear,R.id.iv_cancel)
    fun onViewClick(v : View){
        when (v.id){
            R.id.iv_clear -> {
                tv_input.setText("")
                mKeyboard.setContent("",0)
            }
            R.id.iv_cancel -> {
                val vm = ViewModelProviders.of(activity!!).get(InputDialogVM::class.java)
                vm.cancelDialog.value = ""
                dismiss()
                UiUtils.showToastLong("取消支付")
            }

        }
    }

    override fun onChanged(content: String) {
        tv_input.setText(content)
    }

    override fun onConfirm() {
        if(TextUtils.isEmpty(tv_input.text.toString())){
            UiUtils.showToastLong("密码不能为空")
        } else{
            OrderPayManger.payInfo.useConfirmed = 1
            OrderPayDbManger.update(OrderPayManger.payInfo)

            val vm = ViewModelProviders.of(activity!!).get(InputDialogVM::class.java)
            vm.passwordData.value = tv_input.text.toString()
            dismiss()
        }
    }
}