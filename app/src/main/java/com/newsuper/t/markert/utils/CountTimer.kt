package com.newsuper.t.markert.utils

import android.os.CountDownTimer
import android.widget.TextView
import com.newsuper.t.markert.ui.food.FoodActivity
import com.newsuper.t.R
import com.newsuper.t.markert.base.BaseActivity

import java.lang.ref.WeakReference

class CountTimer(millisInFuture : Long, countDownInterval : Long,
                 activity: BaseActivity, var tv : TextView, var isPaySuccess : Boolean = false): CountDownTimer(millisInFuture,countDownInterval) {
    private var mWeakReference : WeakReference<BaseActivity> ?= null
    var curSec : Int = 0



    init {
        mWeakReference = WeakReference(activity)
    }
    override fun onFinish() {
        var context = mWeakReference?.get()
        context?.startActivity(FoodActivity::class.java)
    }
    override fun onTick(millisUntilFinished: Long) {
        var context = mWeakReference?.get()
        val sec = (millisUntilFinished / 1000).toInt()
        curSec = sec
        if(isPaySuccess){
            if(sec == 0){
                tv.text = "0"
            } else{
                tv.text = "0$sec"
            }

        } else{
           // tv.text = context?.getString(R.string.txt_actionbar_content,sec.toString())
            tv.text = context?.getString(R.string.app_name,sec.toString())
        }

    }


}