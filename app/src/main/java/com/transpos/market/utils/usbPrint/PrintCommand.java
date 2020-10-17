package com.transpos.market.utils.usbPrint;


/**
 * 串口打印机指令
 */           //h
public class PrintCommand {
    public static final byte[] left = new byte[]{0x1b, 0x61, 0x00};// 靠左
    public static final byte[] center = new byte[]{0x1b, 0x61, 0x01};// 居中
    public static final byte[] right = new byte[]{0x1b, 0x61, 0x02};// 靠右
    public static final byte[] bold = new byte[]{0x1b, 0x45, 0x01};// 选择加粗模式
    public static final byte[] bold_cancel = new byte[]{0x1b, 0x45, 0x00};// 取消加粗模式
    public static final byte[] text_normal_size = new byte[]{0x1d, 0x21, 0x00};// 字体不放大
    public static final byte[] text_big_height = new byte[]{0x1b, 0x21, 0x10};// 高加倍
    public static final byte[] text_big_size = new byte[]{0x1d, 0x21, 0x11};// 宽高加倍
    public static final byte[] reset = new byte[]{0x1b, 0x40};//复位打印机
    public static final byte[] print = new byte[]{0x0a};//打印并换行
    public static final byte[] under_line = new byte[]{0x1b, 0x2d, 2};//下划线
    public static final byte[] under_line_cancel = new byte[]{0x1b, 0x2d, 0};//下划线
    public static final byte[] init = new byte[]{0x1B, 0x23, 0x23, 0x53, 0x4C, 0x41, 0x4E, 0x0f};// 中文
    public static final byte[] end = new byte[]{0x1d, 0x56, 0x20, 0x20};
    public static final byte[] end2 = new byte[]{0x1d, 0x56, 0x30};

}
