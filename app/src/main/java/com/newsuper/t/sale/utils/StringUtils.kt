package com.newsuper.t.sale.utils

import android.graphics.Paint
import android.text.TextUtils
import android.widget.TextView
import java.lang.StringBuilder
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern


class StringUtils {

    companion object{
        private val NUMBERCHAR = "0123456789"
        private val df = DecimalFormat("0.00")

        fun formatPrice(price : Float) : String{
            return df.format(price)
        }

        fun formatPriceFloat(price : Float) : Float{

            return df.format(price).toFloat()
        }

        fun formatPriceDouble(price : Float) : Double{
            val bg =  BigDecimal(price.toDouble())
            return bg.setScale(2,BigDecimal.ROUND_HALF_UP).toDouble()
        }

        /**
         * 添加下划线
         */
        fun underLineText(tv: TextView, str:String){
            tv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            tv.text = str
        }

        /**
         * 移除线
         */
        fun removeLine(tv: TextView){
            tv.paint.flags = 0
        }

        fun getLast2str(str: String) : String?{
            var result : String? = null
            if(str.length < 3){
                result = str
                if(getTextByteLen(result) > 2){
                  result = "*"+result[result.length-1].toString()
                }
            } else{
                result = str.substring(str.length-2,str.length)
                if(getTextByteLen(result) > 2){
                    result = result[result.length-1].toString()
                }
                result = "*$result"
            }

            return result
        }

        private fun getTextByteLen(str : String) : Int{
            var len = 0
            if(!TextUtils.isEmpty(str)){
                for (element in str){
                    if(isChinese(element)){
                        len += 2
                    } else{
                        len++
                    }
                }
            }
            return len
        }

        /**
         * 是否是中文字符
         */
        private fun isChinese(c : Char) : Boolean{
            return c >= 0x4E00.toChar() && c <= 0x9FA5.toChar()
        }

        fun generateString(len : Int) : String{
            val sb = StringBuilder(len)
            val random = Random()
            for (i in 0 until len) {
                sb.append(NUMBERCHAR[random.nextInt(NUMBERCHAR.length)])
            }
            return sb.toString()
        }

        fun getFenValue(floatMoney: Float) : Int{
            return (floatMoney * 100).toInt()
        }

        fun isNumeric(str : String) : Boolean{
            val pattern: Pattern = Pattern.compile("[0-9]*")
            return pattern.matcher(str).matches()
        }
    }
}