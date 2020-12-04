package com.newsuper.t.markert.service


import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.KeyEvent
import com.trans.network.utils.GsonHelper
import com.newsuper.t.markert.base.BaseApp
import com.newsuper.t.markert.entity.HotKeyMap
import com.newsuper.t.markert.utils.KeyConstrant
import com.transpos.tools.tputils.TPUtils
import java.lang.ref.WeakReference

class KeyboardHanlder(context: AppCompatActivity) {
    private var mCaps = false
    private val mStrBuilder = StringBuilder()
    private val mReference: WeakReference<AppCompatActivity> = WeakReference(context)
    private val mHandler = Handler()
    private val mRunnable = KeyboardTask()
   // private val mClickProfiler = ClickProfiler()

    fun handle(event: KeyEvent) {
        val keyCode = event.keyCode
        checkLetterStatus(event)
        if (event.action == KeyEvent.ACTION_UP) {
            if (keyCode != KeyEvent.KEYCODE_SHIFT_LEFT && keyCode != KeyEvent.KEYCODE_SHIFT_RIGHT) {
                val key = getInputCode(event)
                mStrBuilder.append(key)
                mRunnable.keyCode = keyCode
                mHandler.removeCallbacks(mRunnable)
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    mStrBuilder.clear()
                    mStrBuilder.append(key)
                    mHandler.post(mRunnable)
                } else {
                    mHandler.postDelayed(mRunnable, 100)
                }
            }
        }
    }

    private fun parseCode(keyCode: Int, key: String): Int {
        var id = -1
        TPUtils.get(BaseApp.getApplication(), KeyConstrant.KEY_HOT_KEY_SETTING, "").takeIf {
            !TextUtils.isEmpty(it)
        }?.let {
            val list = GsonHelper.fromJsonToList(it, Array<HotKeyMap>::class.java)
            val hot = list.find { l -> l.keyCode == keyCode }
            if (hot != null) {
                id = hot.id
            } else {
                //数字的keycode不一致，这里需要再次查询
                val find = list.find { l -> l.keyName == key }
                if (find != null) {
                    id = find.id
                }
            }
        }
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
    private fun keyboardHandle(context: AppCompatActivity, key: String, keyCode: Int) {
//        if (!mClickProfiler.verifyInterval() && keyCode != KeyEvent.KEYCODE_DEL) {
//            return
//        }
//        if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
//            ViewModelProviders.of(context)
//                .get(InputDialogVM::class.java).enterKey.postValue("enter")
//            return
//        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//            ViewModelProviders.of(context).get(InputDialogVM::class.java).upDownKey.postValue("up")
//            return
//        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
//            ViewModelProviders.of(context)
//                .get(InputDialogVM::class.java).upDownKey.postValue("down")
//            return
//        } else if (keyCode == KeyEvent.KEYCODE_DEL) {
//            mReference.get()?.run {
//                val model = ShortcutModel(KeyConstrant.KEY_ACTION_DELETE_CHAR)
//                ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(model)
//            }
//            return
//        } else if (keyCode == KeyEvent.KEYCODE_F1) {
//            mReference.get()?.run {
//                val model = ShortcutModel(KeyConstrant.KEY_ACTION_SHORTCUT)
//                ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(model)
//            }
//            return
//        } else if (keyCode == KeyEvent.KEYCODE_F2 || keyCode == KeyEvent.KEYCODE_F6 || keyCode == KeyEvent.KEYCODE_F8 || keyCode == KeyEvent.KEYCODE_F7) {
//            mReference.get()?.run {
//                val model = ShortcutModel(keyCode)
//                ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(model)
//            }
//            return
//        }
//        //    Logger.d("keyCode=$keyCode  key=$key  $mCaps")
//        when (parseCode(keyCode, key)) {
//            KeyConstrant.KEY_ACTION_PAY -> {
//                val model = ShortcutModel(KeyConstrant.KEY_ACTION_PAY)
//                ViewModelProviders.of(context).get(InputDialogVM::class.java).shortcut.postValue(
//                    model
//                )
//            }
//            //->数量
//            KeyConstrant.KEY_ACTION_ADD -> {
//                val model = ShortcutModel(KeyConstrant.KEY_ACTION_ADD)
//                ViewModelProviders.of(context).get(InputDialogVM::class.java).shortcut.postValue(
//                    model
//                )
//            }
//            KeyConstrant.KEY_ACTION_VERIFICATION -> {
//                val model = ShortcutModel(KeyConstrant.KEY_ACTION_VERIFICATION)
//                ViewModelProviders.of(context).get(InputDialogVM::class.java).shortcut.postValue(
//                    model
//                )
//            }
//            //退货
//            KeyConstrant.KEY_ACTION_REJECTED_GOODS -> {
//                if (OrderItemManger.getList().isEmpty()) {
//                    if (PayHolder.mPayMode != PayMode.REJECTED_ORDER) {
//                        PayHolder.mPayMode = PayMode.REJECTED_ORDER
//                        val dialog = GoodsRejectedDialog.Builder().build()
//                        dialog.show(context.supportFragmentManager, "t")
//
//                        val model = ShortcutModel(KeyConstrant.KEY_ACTION_NONE)
//                        ViewModelProviders.of(context)
//                            .get(InputDialogVM::class.java).shortcut.postValue(model)
//                    }
//                } else {
//                    if (PayHolder.mPayMode != PayMode.REJECTED_PRODUCT) {
//                        PayHolder.mPayMode = PayMode.REJECTED_PRODUCT
//                        UiUtils.showToastShort("已切换到退货模式")
//                        // ViewModelProviders.of(context).get(InputDialogVM::class.java).rejectedShortcut.postValue("rejected")
//                    } else {
//                        PayHolder.mPayMode = PayMode.PAY
//                        UiUtils.showToastShort("已切换到销售模式")
//                        ViewModelProviders.of(context)
//                            .get(InputDialogVM::class.java).rejectedShortcut.postValue("sale")
//                    }
//                }
//            }
//            //挂单
//            KeyConstrant.KEY_ACTION_HANG_ORDER -> {
//                if (OrderItemManger.getList().isNotEmpty()) {
//                    OrderManger.getOrderBean()?.let {
//                        it.orderStatus = OrderStatusFlag.HANG_STATE
//                        it.modifyDate = DateUtil.getNowDateStr()
//                        OrderObjectDbManger.update(it)
//                        UiUtils.showToastShort("挂单成功")
//                        mReference.get()?.run {
//                            ViewModelProviders.of(this)
//                                .get(HangVm::class.java).hangOption.postValue(OrderHangState.HANG)
//                        }
//
//                    }
//                }
//            }
//            //取单
//            KeyConstrant.KEY_ACTION_TAKE_ORDER -> {
//                mReference.get()?.run {
//                    ViewModelProviders.of(this).get(HangVm::class.java).hangOption.postValue(
//                        OrderHangState.TAKE_PRETREATMENT
//                    )
//                }
//            }
//            //单品折扣
//            KeyConstrant.KEY_ACTION_SINGLE_DISCOUNT -> {
//                mReference.get()?.run {
//                    ViewModelProviders.of(this)
//                        .get(InputDialogVM::class.java).singleDiscount.postValue("single")
//                }
//            }
//            //单品议价
//            KeyConstrant.KEY_ACTION_SINGLE_ARGUE -> {
//                mReference.get()?.run {
//                    ViewModelProviders.of(this)
//                        .get(InputDialogVM::class.java).singleArgue.postValue("single")
//                }
//            }
//            //会员
//            KeyConstrant.KEY_ACTION_MEMBER -> {
//                mReference.get()?.run {
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_MEMBER)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(
//                        model
//                    )
//                }
//            }
//            //删除一条
//            KeyConstrant.KEY_ACTION_DELETE_ITEM -> {
//                mReference.get()?.run {
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_DELETE_ITEM)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(
//                        model
//                    )
//                }
//            }
//            //打开钱箱
//            KeyConstrant.KEY_ACTION_OPEN_BOX -> {
//                mReference.get()?.run {
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_OPEN_BOX)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(
//                        model
//                    )
//                }
//            }
//            //锁屏
//            KeyConstrant.KEY_ACTION_LOCK_SCREEN -> {
//                mReference.get()?.run {
//                    startActivity(Intent(this, LockScreenActivity::class.java))
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_NONE)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(
//                        model
//                    )
//                }
//            }
//            //查交易
//            KeyConstrant.KEY_ACTION_CHECK_BUSINESS -> {
//                mReference.get()?.run {
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_CHECK_BUSINESS)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(
//                        model
//                    )
//                }
//            }
//            //查库存
//            KeyConstrant.KEY_ACTION_CHECK_STORE -> {
//                mReference.get()?.run {
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_CHECK_STORE)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(
//                        model
//                    )
//                }
//            }
//            else -> {
//                mReference.get()?.run {
//                    val model = ShortcutModel(KeyConstrant.KEY_ACTION_HIDE_SOFT)
//                    ViewModelProviders.of(this).get(InputDialogVM::class.java).shortcut.postValue(
//                        model
//                    )
//                }
//            }
//        }
    }


    private fun checkLetterStatus(event: KeyEvent) {
        val keyCode = event.keyCode
        if (keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
            mCaps = true
        } else {
            if (!mCaps)
                mCaps = event.isCapsLockOn
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

    inner class KeyboardTask : Runnable {
        var keyCode: Int = -1
        override fun run() {
            if (mStrBuilder.isNotEmpty()) {
                mReference.get()?.let {
                    if (mStrBuilder.length == 1 && !mCaps) {
                        keyboardHandle(it, mStrBuilder.toString(), keyCode)
                    } else {
                        if (mCaps && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER))
                            keyboardHandle(it, mStrBuilder.toString(), keyCode)
                        else if (keyCode == KeyEvent.KEYCODE_DEL) {
                            keyboardHandle(it, mStrBuilder.toString(), keyCode)
                        }
                    }
                }
                mStrBuilder.clear()
                mCaps = false
            }
        }
    }
}
