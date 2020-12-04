package com.newsuper.t.markert.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.newsuper.t.R
import com.newsuper.t.markert.base.BaseApp
import com.newsuper.t.markert.entity.OrderItem
import com.newsuper.t.markert.entity.ProductSpecsBean
import com.newsuper.t.markert.utils.UiUtils
import com.newsuper.t.markert.view.CustomGradient


class SpecsSelectAdapter :BaseQuickAdapter<ProductSpecsBean,BaseViewHolder>(R.layout.item_product_specs_select_layout) {

    private val checkDrawable : CustomGradient = CustomGradient(GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(Color.parseColor("#00BAFF"),Color.parseColor("#009BFF")),
        UiUtils.dp2px(5, BaseApp.getApplication()).toFloat())
    private val unCheckDrawable : CustomGradient = CustomGradient(GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(Color.parseColor("#f2f3f7"),Color.parseColor("#f2f3f7")),
        UiUtils.dp2px(5,BaseApp.getApplication()).toFloat())


    override fun convert(helper: BaseViewHolder, item: ProductSpecsBean?) {

        item?.run {
            helper.setVisible(R.id.iv_select,isCheck)
            val tv_specs_name = helper.getView<TextView>(R.id.tv_specs_name)
            val tv_specs_price = helper.getView<TextView>(R.id.tv_specs_price)
            var root_view = helper.getView<View>(R.id.root_view)
            tv_specs_name.setText(this.specsName)
            tv_specs_price.setText("￥${this.specsPrice}")
            if(isCheck){
                tv_specs_name.setTextColor(ContextCompat.getColor(mContext,R.color.white))
                tv_specs_price.setTextColor(ContextCompat.getColor(mContext,R.color.white))
                root_view.background = checkDrawable
            } else{
                tv_specs_name.setTextColor(ContextCompat.getColor(mContext,R.color.color_2F2F2F))
                tv_specs_price.setTextColor(ContextCompat.getColor(mContext,R.color.color_2F2F2F))
                root_view.background = unCheckDrawable
            }
        }
    }
}