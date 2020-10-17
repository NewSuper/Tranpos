package com.transpos.market.service

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import com.transpos.market.db.manger.OrderItemDbManger
import com.transpos.market.db.manger.OrderPayDbManger
import com.transpos.market.entity.OrderPay
import com.transpos.market.ui.cash.manger.OrderPayManger
import com.transpos.market.utils.ActivityCollector
import com.transpos.market.utils.UiUtils
import com.transpos.market.utils.summiPrint.SummiPrinterQueneService
import com.transpos.market.utils.usbPrint.EscCommand
import com.transpos.market.utils.usbPrint.UsbPhoneService
import com.transpos.market.utils.usbPrint.UsbPrintUtils
import com.transpos.sale.db.manger.OrderObjectDbManger
import org.apache.commons.lang3.ArrayUtils
import java.io.Serializable
import java.util.*

class SummiPrintTask(val context: Context, val tradeNo: String?) : Runnable {

    override fun run() {
        tradeNo?.run {
            Log.e("debug", "开始打印了SummiPrintTask")
            var order = OrderObjectDbManger.checkOrder(this)
            var orderItemList = OrderItemDbManger.findOrderItemsByTradeno(tradeNo)
            var payInfos = OrderPayDbManger.findOderPayInfos(tradeNo)
            val byteArray =
                ArrayList<ByteArray>()
            val esc = EscCommand()
            esc.addArrayToCommand(UsbPrintUtils.setChineses())
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER) //设置打印居中

            esc.addArrayToCommand(UsbPrintUtils.setSize(17))
            esc.addText("${order.storeName}" + "\n", "GB2312")
            esc.addText("结账单" + "\n", "GB2312")
            esc.addPrintAndLineFeed()
            esc.addArrayToCommand(UsbPrintUtils.setSize(0))
            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT)
            esc.addText("单号：" + "${order.tradeNo}" + "\n", "GB2312")
            esc.addText("收银员：${order.workerNo}\n", "GB2312")
            esc.addText("销售时间：${order.saleDate}" + "\n", "GB2312")
            esc.addText("--------------------------------\n", "GB2312")
            esc.addText("品名          数量   单价   小计\n", "GB2312")
            val num = order.totalQuantity.toInt()
            val total_price = order.amount
            val byteOrder = ArrayList<ByteArray>()

            for (item in orderItemList) {
                val name = item.productName
                val amount = item.quantity.toInt().toString()
                val price = if (TextUtils.isEmpty(item.ext1)) "" else item.ext1
                val buy_price = item.discountPrice.toString()
                Log.e(
                    "debug",
                    "name=$name   amount=$amount   price=$price   buy=$buy_price   tradeNo=${item.tradeNo}"
                )
                byteOrder.addAll(
                    UsbPrintUtils.PrintOrderList58mm(
                        name,
                        amount,
                        price,
                        buy_price
                    )
                )
            }
            for (i in byteOrder.indices) {
                esc.addArrayToCommand(byteOrder[i])
            }
            esc.addText("--------------------------------\n", "GB2312")
            esc.addText("    共" + num + "件商品   实付金额：${order.receivableAmount} \n", "GB2312")
           // esc.addText("    共" + num + "件商品   实付金额：${} \n", "GB2312")
            esc.addText("                原价金额：$total_price\n", "GB2312")
            esc.addText("                优惠金额：${order.discountAmount}" + "\n", "GB2312")

            esc.addText("              付款详情：" + "\n", "GB2312")
            payInfos.forEach {
                val values = OrderPayManger.PayModeEnum.values()
                var e = OrderPayManger.PayModeEnum.PAYAIL
                for (v in values) {
                    if (v.payNo == it.payNo) {
                        e = v
                    }
                }
                esc.addText("                  ${e.payName}：${it.paidAmount}" + "\n", "GB2312")
            }
            if (order.changeAmount <= 0) {

            } else {
                esc.addText("                    找零： ${order.changeAmount}" + "\n", "GB2312")
            }

            esc.addText("--------------------------------\n", "GB2312")
            if (order.isMember == 1) {
              esc.addText("会员号：${UiUtils.hidePhoneNum(order.memberMobileNo)}\n", "GB2312")
               // esc.addText("会员号：${UiUtils.hidePhoneNum(order.memberMobileNo)}\n","GB2312")
                esc.addText(
                    "本次积分:${order.addPoint.toInt()}     剩余积分：${order.aftPoint.toInt()}\n",
                    "GB2312"
                )
                esc.addText("会员余额：${order.ext2}\n", "GB2312")
                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER)
                esc.addText("电话:${order.memberMobileNo}\n", "GB2312")
            } else {
                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER)
            }
            esc.addText("谢谢惠顾！欢迎您的下次光临" + "\n", "GB2312")
            esc.addPrintAndLineFeed()
            esc.addPrintAndFeedLines(1.toByte())
            esc.addCutAndFeedPaper(1.toByte())
            val datas = esc.command //发送数据

            val Bytes = datas.toTypedArray()
            val bytes = ArrayUtils.toPrimitive(Bytes)
            val sss =
                Base64.encodeToString(bytes, Base64.DEFAULT)
            val data2 = Base64.decode(sss, 0)
            val vector: Vector<Byte> = Vector<Byte>()
            val var10 = data2.size
            for (var7 in 0 until var10) {
                val b1 = data2[var7]
                vector.add(java.lang.Byte.valueOf(b1))
            }
            val Command: Vector<Byte> = vector
            if (Command != null && Command.size > 0) {
                val sendData = ByteArray(Command.size)
                for (e in Command.indices) {
                    sendData[e] = (Command[e] as Byte).toByte()
                }
                byteArray.add(sendData)
            }
            if (byteArray.size > 0) {
                LoopPrint(byteArray)
            }
        }

    }


    /**
     * 循环打印
     */
    private fun LoopPrint(data: ArrayList<ByteArray>) {
        val it = Intent()
        it.setClass(context, SummiPrinterQueneService::class.java)
        context.startService(it)
        AddParam(data)
    }

    @kotlin.jvm.Synchronized
    private fun AddParam(bytes: ArrayList<ByteArray>?) {
        if (SummiPrinterQueneService.printerbytes == null) {
            SummiPrinterQueneService.printerbytes
        }
        if (bytes != null) {
            SummiPrinterQueneService.printerbytes.addAll(bytes)
        }
    }
}