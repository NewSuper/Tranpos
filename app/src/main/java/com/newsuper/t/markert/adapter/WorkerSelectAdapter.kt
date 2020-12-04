package com.newsuper.t.markert.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.newsuper.t.R
import com.newsuper.t.markert.base.BaseApp
import com.newsuper.t.markert.entity.OrderItem
import com.newsuper.t.markert.entity.Worker
import com.newsuper.t.markert.utils.UiUtils
import com.newsuper.t.markert.view.CustomGradient

class WorkerSelectAdapter :
    BaseQuickAdapter<Worker, BaseViewHolder>(R.layout.item_product_specs_select_layout) {
    private val checkDrawable : CustomGradient = CustomGradient(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(Color.parseColor("#00BAFF"), Color.parseColor("#009BFF")),
        UiUtils.dp2px(5, BaseApp.getApplication()).toFloat())
    private val unCheckDrawable : CustomGradient = CustomGradient(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(Color.parseColor("#f2f3f7"), Color.parseColor("#f2f3f7")),
        UiUtils.dp2px(5, BaseApp.getApplication()).toFloat())
    override fun convert(helper: BaseViewHolder, item: Worker?) {

        item?.run {
            // helper.setVisible(R.id.iv_select,isCheck)
            val tv_specs_name = helper.getView<TextView>(R.id.tv_specs_name)
            val tv_specs_price = helper.getView<TextView>(R.id.tv_specs_price)
            var root_view = helper.getView<View>(R.id.root_view)
            tv_specs_name.setText(this.name)
            tv_specs_price.setText(this.no)
//            if (isCheck) {
//                root_view.background = checkDrawable
//            }else{
//                root_view.background = unCheckDrawable
//            }
    }
}}