package com.transpos.market.utils.usbPrint;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * 打印机工具类
 */
public class PrintUtil {

    public static final String ACTION_PRINT_TEST = "action_print_test";//打印测试
    public static final String ACTION_PRINT_EMPTY_TEST = "action_print_empty_test";//打印测试
    public static final String ACTION_PRINT_ORDER = "action_print_order";//打印测试2
    public static final String OpenCash = "action_open_cash";//打开钱箱

    private static int[][] Floyd16x16 = new int[][]{{0, 128, 32, 160, 8, 136, 40, 168, 2, 130, 34, 162, 10, 138, 42, 170}, {192, 64, 224, 96, 200, 72, 232, 104, 194, 66, 226, 98, 202, 74, 234, 106}, {48, 176, 16, 144, 56, 184, 24, 152, 50, 178, 18, 146, 58, 186, 26, 154}, {240, 112, 208, 80, 248, 120, 216, 88, 242, 114, 210, 82, 250, 122, 218, 90}, {12, 140, 44, 172, 4, 132, 36, 164, 14, 142, 46, 174, 6, 134, 38, 166}, {204, 76, 236, 108, 196, 68, 228, 100, 206, 78, 238, 110, 198, 70, 230, 102}, {60, 188, 28, 156, 52, 180, 20, 148, 62, 190, 30, 158, 54, 182, 22, 150}, {252, 124, 220, 92, 244, 116, 212, 84, 254, 126, 222, 94, 246, 118, 214, 86}, {3, 131, 35, 163, 11, 139, 43, 171, 1, 129, 33, 161, 9, 137, 41, 169}, {195, 67, 227, 99, 203, 75, 235, 107, 193, 65, 225, 97, 201, 73, 233, 105}, {51, 179, 19, 147, 59, 187, 27, 155, 49, 177, 17, 145, 57, 185, 25, 153}, {243, 115, 211, 83, 251, 123, 219, 91, 241, 113, 209, 81, 249, 121, 217, 89}, {15, 143, 47, 175, 7, 135, 39, 167, 13, 141, 45, 173, 5, 133, 37, 165}, {207, 79, 239, 111, 199, 71, 231, 103, 205, 77, 237, 109, 197, 69, 229, 101}, {63, 191, 31, 159, 55, 183, 23, 151, 61, 189, 29, 157, 53, 181, 21, 149}, {254, 127, 223, 95, 247, 119, 215, 87, 253, 125, 221, 93, 245, 117, 213, 85}};

    public static ArrayList<byte[]> PrintBmp(int startx, Bitmap bitmap) {
        ArrayList<byte[]> datas = new ArrayList<>();
        byte[] imgbuf = new byte[(bitmap.getWidth() + startx + 7) / 8];
        byte[] xl = new byte[8];
        int width = bitmap.getWidth() + startx;
        if (width > 384)
            width = 384;
        int s = 0;
        // 打印光栅位图的指令
        xl[0] = 29;// 十六进制0x1D
        xl[1] = 118;// 十六进制0x76
        xl[2] = 48;// 30
        xl[3] = 0;// 位图模式 0,1,2,3
        // 表示水平方向位图字节数（xL+xH × 256）
        xl[4] = (byte) ((bitmap.getWidth() + startx + 7) / 8 % 256);
        xl[5] = (byte) ((bitmap.getWidth() + startx + 7) / 8 / 256);
        // 表示垂直方向位图点数（ yL+ yH × 256）
        xl[6] = (byte) (bitmap.getHeight() % 256);//
        xl[7] = (byte) (bitmap.getHeight() / 256);
        datas.add(xl);
        for (int i = 0; i < bitmap.getHeight(); i++) {// 循环位图的高度
            imgbuf = new byte[(bitmap.getWidth() + startx + 7) / 8];
            for (int k = 0; k < (bitmap.getWidth() + startx + 7) / 8; k++) {
                imgbuf[k] = (byte) 0;
                for (k = startx; k < (bitmap.getWidth() + startx); ) {// 循环位图的宽度
                    int temp = 0;
                    if ((k - startx) < bitmap.getWidth()) {
                        int pixel0 = bitmap.getPixel(k - startx, i);
                        if ((pixel0 & 255) > 128) {
                            temp += 0;
                        } else {
                            temp += 128;
                        }
                    }
                    if ((k - startx + 1) < bitmap.getWidth()) {
                        int pixel1 = bitmap.getPixel(k - startx + 1, i);
                        if ((pixel1 & 255) > 128) {
                            temp += 0;
                        } else {
                            temp += 64;
                        }
                    }
                    if ((k - startx + 2) < bitmap.getWidth()) {
                        int pixel2 = bitmap.getPixel(k - startx + 2, i);
                        if ((pixel2 & 255) > 128) {
                            temp += 0;
                        } else {
                            temp += 32;
                        }
                    }
                    if ((k - startx + 3) < bitmap.getWidth()) {
                        int pixel3 = bitmap.getPixel(k - startx + 3, i);
                        if ((pixel3 & 255) > 128) {
                            temp += 0;
                        } else {
                            temp += 16;
                        }
                    }
                    if ((k - startx + 4) < bitmap.getWidth()) {
                        int pixel4 = bitmap.getPixel(k - startx + 4, i);
                        if ((pixel4 & 255) > 128) {
                            temp += 0;
                        } else {
                            temp += 8;
                        }
                    }
                    if ((k - startx + 5) < bitmap.getWidth()) {
                        int pixel5 = bitmap.getPixel(k - startx + 5, i);
                        if ((pixel5 & 255) > 128) {
                            temp += 0;
                        } else {
                            temp += 4;
                        }
                    }
                    if ((k - startx + 6) < bitmap.getWidth()) {
                        int pixel6 = bitmap.getPixel(k - startx + 6, i);
                        if ((pixel6 & 255) > 128) {
                            temp += 0;
                        } else {
                            temp += 2;
                        }
                    }
                    if ((k - startx + 7) < bitmap.getWidth()) {
                        int pixel7 = bitmap.getPixel(k - startx + 7, i);
                        if ((pixel7 & 255) > 128) {
                            temp += 0;
                        } else {
                            temp += 1;
                        }
                    }
                    imgbuf[k / 8] = (byte) temp;
                    k += 8;
//                    System.out.println("色度："+ pixel);
//                    if (Color.red(pixel) == 0 || Color.green(pixel) == 0
//                            || Color.blue(pixel) == 0) {
//                        // 高位在左，所以使用128 右移
//                        imgbuf[k / 8] += 128 >> (k % 8);// (byte) (128 >> (y % 8));
//                    }
                }
            }
            datas.add(imgbuf);
        }
        if (bitmap != null) {
            bitmap.recycle();
        }

        return datas;
    }

    public static int blength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static byte[] addText(String content) {
        byte[] temp = null;
        try {
            temp = (content + "\n").getBytes("cp936");
        } catch (Exception e) {

        }
        return temp;
    }

    public static byte[] addText2(String content) {
        byte[] temp = null;
        try {
            temp = (content).getBytes("cp936");
        } catch (Exception e) {

        }
        return temp;
    }

    public static ArrayList<byte[]> printOther(String other) {

        int blength = blength(other);
        ArrayList<byte[]> printBytes = new ArrayList<byte[]>();
        try {

            if (blength > 32) {
                printBytes.addAll(PrintNormalString(other, 16));
            } else {
                String other1 = "";
                for (int h = 0; h < 32 - blength; h++) {
                    other1 += " ";
                }
                other1 += other;
                printBytes.add(other1.getBytes("cp936"));
                printBytes.add(PrintCommand.print);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> printCenterString(String shop, int length) {
        ArrayList<byte[]> printBytes = new ArrayList<byte[]>();
        try {
            if (blength(shop) > length * 2) {
                printBytes.addAll(PrintNormalString(shop, length));
            } else {
                int i = (length * 2 - blength(shop)) / 2;
                String shop1 = "";
                for (int i1 = 0; i1 < i; i1++) {
                    shop1 += " ";
                }
                shop1 += shop;
                printBytes.add(shop1.getBytes("cp936"));
                printBytes.add(PrintCommand.print);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> PrintNormalString(String str, int length) {
        ArrayList<byte[]> printBytes = new ArrayList<byte[]>();
        try {
            int mL = blength(str);
            if (mL > 32) {
                int i = str.length() / length;

                for (int j = 0; j < i; j++) {
                    printBytes.add(str.substring(j * length, (j + 1) * length).getBytes("cp936"));
                    printBytes.add(PrintCommand.print);
                }
                if (str.length() % length != 0) {
                    printBytes.add(str.substring(i * length, i * length + str.length() % length).getBytes("cp936"));
                    printBytes.add(PrintCommand.print);
                }
            } else {
                printBytes.add(str.getBytes("cp936"));
                printBytes.add(PrintCommand.print);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> PrintOrderItemString(String name, String value, String num, String price) {
        ArrayList<byte[]> printBytes = new ArrayList<byte[]>();
        try {
            int ml = blength(name);
            if (ml > 12) {
                int i = name.length() / 16;

                for (int j = 0; j < i; j++) {
                    printBytes.add(name.substring(j * 16, (j + 1) * 16).getBytes("cp936"));
                    printBytes.add(PrintCommand.print);
                }
                if (name.length() % 16 != 0) {
                    if (name.length() % 16 > 6) {
                        printBytes.add(name.substring(i * 16, i * 16 + name.length() % 16).getBytes("cp936"));
                        printBytes.add(PrintCommand.print);
                        printBytes.addAll(PrintNormalString(getOrderItemString("", value, num, price), 6 * 2 + 20));
                    } else {
                        printBytes.addAll(PrintNormalString(getOrderItemString(name.substring(i * 16, i * 16 + name.length() % 16), value, num, price),
                                32));
                    }
                } else {
                    printBytes.addAll(PrintNormalString(getOrderItemString("", value, num, price),
                            6 * 2 + 20));
                }
            } else {
                printBytes.addAll(PrintNormalString(getOrderItemString(name, value, num, price),
                        32));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static String getOrderItemString(String name, String value, String num, String price) {
        String orderItemString = "";
        int w = 12 - blength(name);
        String name1 = name;
        for (int w1 = 0; w1 < w; w1++) {
            name1 += " ";
        }
        String value1 = "";
        if (blength(value) < 8) {
            int j1 = (8 - blength(value)) / 2;
            for (int i = 0; i < j1; i++) {
                value1 = value1 + " ";
            }
            value1 = value1 + value;
            for (int i = 0; i < 8 - j1 - blength(value); i++) {
                value1 = value1 + " ";
            }
        } else {
            value1 = value;
        }
        String num1 = "";
        if (blength(num) < 4) {
            int k1 = (4 - blength(num)) / 2;
            for (int i = 0; i < k1; i++) {
                num1 = num1 + " ";

            }
            num1 = num1 + num;
            for (int i = 0; i < 4 - k1 - blength(num); i++) {
                num1 = num1 + " ";
            }
        } else {
            num1 = num;
        }
        String price1 = "";
        if (blength(num) > 4) {
            if (price.length() < 12 - blength(num)) {
                int k2 = 12 - blength(num) - price.length();
                for (int i = 0; i < k2; i++) {
                    price1 = price1 + " ";
                }
                price1 = price1 + price;
            } else {
                price1 = price;
            }
        } else {
            if (price.length() < 8) {
                int k2 = 8 - price.length();
                for (int i = 0; i < k2; i++) {
                    price1 = price1 + " ";
                }
                price1 = price1 + price;
            } else {
                price1 = price;
            }
        }
        orderItemString = name1 + value1 + num1 + price1;
        return orderItemString;
    }
}
