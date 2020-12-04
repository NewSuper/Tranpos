package com.newsuper.t.markert.utils;

import java.math.BigDecimal;

public class DecimalUtils {

    private static final String TAG = "DecimalUtils";

    /// <summary>
    /// 分转元
    /// </summary>
    /// <param name="amount"></param>
    /// <returns></returns>
    public static double fen2Yuan(int amount)
    {
        return MathUtil.divide(amount, 100.0, 2);
    }

    /// <summary>
    /// 元转分
    /// </summary>
    /// <param name="amount"></param>
    /// <returns></returns>
    public static String yuan2Fen(double amount)
    {
        return String.valueOf(Double.valueOf(MathUtil.multiply(amount, 100, 0)).intValue());
    }

    /**
     * 优雅的显示数字
     * @param num
     * @return
     */
    public static String getPrettyNumber(double num){
        if(num == 0.0){
            //android 中对0的处理不正确
            return "0";
        }
        return BigDecimal.valueOf(num).stripTrailingZeros().toPlainString();
    }

    /// <summary>
    /// 格式化为指定精度
    /// </summary>
    /// <param name="str"></param>
    /// <param name="degree"></param>
    /// <returns></returns>
    public static double formatDegree(String str, int degree)
    {
        double result = 0;
        switch (degree)
        {
            case 0:
            {
                result = Double.valueOf(str).intValue();
            }
            break;
            case 1:
            {
                result = MathUtil.divide(Double.valueOf(str), 10, degree);
            }
            break;
            case 3:
            {
                result = MathUtil.divide(Double.valueOf(str), 1000, degree);
            }
            break;
            case 2:
            default:
            {
                result = MathUtil.divide(Double.valueOf(str), 100, degree);
            }
            break;
        }
        return result;
    }
}

