package com.transpos.market.service

import android.annotation.SuppressLint
import android.app.admin.DevicePolicyManager
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.KeyEvent
import com.trans.network.utils.GsonHelper
import com.transpos.market.base.BaseApp
//import com.transpos.market.base.BaseDialog
//import com.transpos.market.db.manger.OrderObjectDbManger
//import com.transpos.market.dialog.GoodsRejectedDialog
//import com.transpos.market.dialog.HangOrderDialog
import com.transpos.market.dialog.vm.InputDialogVM
//import com.transpos.market.entity.HotKeyMap
//import com.transpos.market.entity.ShortcutModel
//import com.transpos.market.entity.state.OrderHangState
import com.transpos.market.entity.state.OrderStatusFlag
//import com.transpos.market.entity.state.PayMode
//import com.transpos.market.function.hang.vm.HangVm
//import com.transpos.market.function.rejected.PayHolder
//import com.transpos.market.receiver.ScreenOffAdminReceiver
import com.transpos.market.ui.cash.manger.OrderItemManger
import com.transpos.market.ui.cash.manger.OrderManger
import com.transpos.market.utils.DateUtil
import com.transpos.market.utils.KeyConstrant
import com.transpos.market.utils.UiUtils
import com.transpos.sale.thread.ThreadDispatcher
//import com.transpos.tools.logger.Logger
import com.transpos.tools.tputils.TPUtils
import java.lang.ref.WeakReference
import java.util.logging.Logger


class KeyboardHanlder(context : AppCompatActivity) {
    private var mCaps = false
    private val mStrBuilder = StringBuilder()
    private val mReference : WeakReference<AppCompatActivity> = WeakReference(context)

    fun handlle(event : KeyEvent){
        val keyCode = event.keyCode
        checkLetterStatus(event)
        if (event.action == KeyEvent.ACTION_UP) {
            if(mStrBuilder.isEmpty()){
               ThreadDispatcher.getDispatcher().postOnMainDelayed(Runnable{
                   mReference.get()?.let {
                       keyboardHandle(it,mStrBuilder.toString(),keyCode)
                   }
                   mStrBuilder.clear()
                },500)
            }
            val key = getInputCode(event)
            mStrBuilder.append(key)
        }
    }

    private fun parseCode(keyCode: Int,key : String) : Int{
        var id = -1
//        TPUtils.get(BaseApp.getApplication(), KeyConstrant.KEY_HOT_KEY_SETTING,"").takeIf {
//            !TextUtils.isEmpty(it)
//        }?.let {
//            val list = GsonHelper.fromJsonToList(it,Array<HotKeyMap>::class.java)
//            val hot = list.find { l -> l.keyCode == keyCode }
//            if(hot != null){
//                id = hot.id
//            } else {
//                //数字的keycode不一致，这里需要再次查询
//               val find =  list.find { l -> l.keyName == key }
//                if(find != null){
//                    id = find.id
//                }
//            }
//        }
        return id
    }

    /**
     * t -> 退货
     * g -> 挂单
     * n -> 取单
     * o -> 单品折扣
     * w ->单品议价
     * m -> 会员
     * + ->结算
     * * ->数量
     * u ->删除
     * x -> 开钱箱
     * p -> 锁屏
     */
     private fun keyboardHandle(context : AppCompatActivity,key : String,keyCode : Int){
//        if(keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER){
//            ViewModelProviders.of(context).get(InputDialogVM::class.java).enterKey.postValue("enter")
//        } else if(keyCode == KeyEvent.KEYCODE_DPAD_UP){
//            ViewModelProviders.of(context).get(InputDialogVM::class.java).upDownKey.postValue("up")
//        } else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
//            ViewModelProviders.of(context).get(InputDialogVM::class.java).upDownKey.postValue("down")
//        }
//
//        Logger.d("keyCode=$keyCode")
//        when(parseCode(keyCode,key)){
//            KeyConstrant.KEY_ACTION_PAY -> {
//                val model = ShortcutModel(KeyConstrant.KEY_ACTION_PAY)
//                ViewModelProviders.of(context).get(InputDialogVM::class.java).shortcut.postValue(model)
//            }
//            //->数量
//            KeyEvent.KEYCODE_NUMPAD_MULTIPLY -> {
//                val model = ShortcutModel(KeyConstrant.KEY_ACTION_COUNT)
//                ViewModelProviders.of(context).get(InputDialogVM::class.java).shortcut.postValue(model)
//            }
//            //退货
//            KeyConstrant.KEY_ACTION_REJECTED_GOODS ->{
//                if(OrderItemManger.getList().isEmpty()){
//                    if(PayHolder.mPayMode != PayMode.REJECTED_ORDER){
//                        PayHolder.mPayMode = PayMode.REJECTED_ORDER
//                        val dialog = GoodsRejectedDialog.Builder().build()
//                        dialog.show(context.supportFragmentManager,"t")
//                    }
//                } else {
//                    if(PayHolder.mPayMode != PayMode.REJECTED_PRODUCT){
//                        PayHolder.mPayMode = PayMode.REJECTED_PRODUCT
//                        UiUtils.showToastShort("已切换到退货模式")
//                        ViewModelProviders.of(context).get(InputDialogVM::class.java).rejectedShortcut.postValue("rejected")
//                    } else{
//                        PayHolder.mPayMode = PayMode.PAY
//                        UiUtils.showToastShort("已切换到销售模式")
//                        ViewModelProviders.of(context).get(InputDialogVM::class.java).rejectedShortcut.postValue("sale")
//                    }
//                }
//            }
//            //挂单
//            KeyConstrant.KEY_ACTION_HANG_ORDER -> {
//                if(OrderItemManger.getList().isNotEmpty()){
//                    OrderManger.getOrderBean()?.let {
//                        it.orderStatus = OrderStatusFlag.HANG_STATE
//                        it.modifyDate = DateUtil.getNowDateStr()
//                        OrderObjectDbManger.update(it)
//                        UiUtils.showToastShort("挂单成功")
//                        mReference.get()?.run {
//                            ViewModelProviders.of(this).get(HangVm::class.java).hangOption.postValue(OrderHangState.HANG)
//                        }
//
//                    }
//                }
//            }
//            //取单
//            KeyConstrant.KEY_ACTION_TAKE_ORDER -> {
//                mReference.get()?.run{
//                    ViewModelProviders.of(this).get(HangVm::class.java).hangOption.postValue("")
//                    BaseDialog.Builder().build(HangOrderDialog::class.java)
//                        .show(supportFragmentManager, null)
//                }
//            }
//            //单品折扣
//            KeyConstrant.KEY_ACTION_SINGLE_DISCOUNT -> {
//                mReference.get()?.run {
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).singleDiscount.postValue("single")
//                }
//            }
//            //单品议价
//            KeyConstrant.KEY_ACTION_SINGLE_ARGUE -> {
//                mReference.get()?.run {
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).singleArgue.postValue("single")
//                }
//            }
//            //会员
//            KeyConstrant.KEY_ACTION_MEMBER -> {
//                mReference.get()?.run {
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_MEMBER)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(model)
//                }
//            }
//            //删除一条
//            KeyConstrant.KEY_ACTION_DELETE_ITEM -> {
//                mReference.get()?.run {
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_DELETE_ITEM)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(model)
//                }
//            }
//            //打开钱箱
//            KeyConstrant.KEY_ACTION_OPEN_BOX -> {
//                mReference.get()?.run {
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_OPEN_BOX)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(model)
//                }
//            }
//            //锁屏
//            KeyConstrant.KEY_ACTION_LOCK_SCREEN -> {
//                mReference.get()?.run {
//                    val mDevicePolicyManager  = this.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
//                    val adminReceiver = ComponentName(
//                        context,
//                        ScreenOffAdminReceiver::class.java
//                    )
//                    val admin: Boolean = mDevicePolicyManager.isAdminActive(adminReceiver)
//                    if(admin){
//                        mDevicePolicyManager.lockNow()
//                    } else{
//                        val intent = Intent()
//                        intent.action = DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
//                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminReceiver)
//                        startActivityForResult(intent,0)
//                    }
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_NONE)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(model)
//                }
//            }
//        }
    }

    /**
     * 唤醒屏幕
     */
    @SuppressLint("InvalidWakeLockTag")
    private fun wakeScreen(){
        val mPowerManager  = mReference.get()?.getSystemService(Context.POWER_SERVICE) as? PowerManager
        if(mPowerManager != null){
            val mWakeLock  = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag")
            mWakeLock.acquire()
            mWakeLock.release()
        }
    }

    private fun checkLetterStatus(event: KeyEvent) {
        val keyCode = event.keyCode
        if (keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
            mCaps = event.action == KeyEvent.ACTION_DOWN
        }
    }

    private fun getInputCode(event: KeyEvent): Char {
        val keyCode = event.keyCode
        val aChar: Char
        aChar = if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            ((if (mCaps) 'A' else 'a').toInt() + keyCode - KeyEvent.KEYCODE_A).toChar()
        } else if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) { // 数字
            ('0'.toInt() + keyCode - KeyEvent.KEYCODE_0).toChar()
        } else { // 其他符号
            when (keyCode) {
                KeyEvent.KEYCODE_MINUS -> if (mCaps) '_' else '-'
                KeyEvent.KEYCODE_BACKSLASH -> if (mCaps) '|' else '\\'
                KeyEvent.KEYCODE_LEFT_BRACKET -> if (mCaps) '{' else '['
                KeyEvent.KEYCODE_RIGHT_BRACKET -> if (mCaps) '}' else ']'
                KeyEvent.KEYCODE_COMMA -> if (mCaps) '<' else ','
                KeyEvent.KEYCODE_PERIOD -> if (mCaps) '>' else '.'
                KeyEvent.KEYCODE_SLASH -> if (mCaps) '?' else '/'
                KeyEvent.KEYCODE_SEMICOLON -> if (mCaps) ':' else ';'
                KeyEvent.KEYCODE_APOSTROPHE -> if (mCaps) '"' else '\''
                else -> 0.toChar()
            }
        }
        return aChar
    }
}
