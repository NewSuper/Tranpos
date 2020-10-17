package com.transpos.market.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.transpos.market.R

import com.transpos.market.entity.PayMode

class PayTypeSelectAdapter : BaseQuickAdapter<PayMode, BaseViewHolder>(R.layout.item_paytype) {

    override fun convert(helper: BaseViewHolder, item: PayMode?) {
        item?.run {
            val tv_specs_name = helper.getView<TextView>(R.id.tv_specs_name)
            tv_specs_name.text = this.name
        }
    }
}