package com.newsuper.t.markert.utils.usbPrint;



public class ByteUtils {

    /***************************************************************************
     * * @param src1 * 保存的源byte[] * @param src2 * 保存的源byte[] * @return 将src1和src2拼接的byte
     */
    public static byte[] addBytes(byte[] src1, byte[] src2) {
        byte[] dest = new byte[src1.length + src2.length];
        System.arraycopy(src1, 0, dest, 0, src1.length);
        System.arraycopy(src2, 0, dest, src1.length, src2.length);
        return dest;
    }

    /***************************************************************************
     * * @param maxlength * 目标byte的max长度 * @param src * 每一个byte[] * @return 目标byte[]数组
     */
    public static byte[] addBytes(int maxlength, byte[]... src) {
        int length = 0; // 获取每一byte数组的长//
        int index = 0; // 获取复制到目标数组的起始点，
        byte[] dest = new byte[maxlength]; // 目标数组
        for (int i = 0; i < src.length; i++) {
            length = src[i].length;
            System.arraycopy(src[i], 0, dest, index, length); // 将每一个byte[] // 复制到 目标数组
            index = index + length; // 起始位置向后挪动byte[]的length
        } // 目标长度的数组不知道长度，在长度不足的情况下，会向后补0， // 所以需要对得到的原始数组做一些处理
        int count = 0;
        for (int i = 0; i < dest.length; i++) {
            if (dest[i] == 0) {
                count++; // 统计原始数组补0的个数
            }
        }
        byte[] result = new byte[100 - count]; // 生成新数组保存我们需要的值（非补0）
        int pos = 0;
        for (int i = 0; i < result.length; i++) {
            if (dest[i] != 0) { // 判断是非为0，将不为0的值保存
                result[pos] = dest[i];
                pos++;
            }
        }
        return result;
    }

    // 测试类
    public static void main(String[] args) {
        byte[] a = {1, 2};
        byte[] b = {3, 4};
        byte[] c = {8, 9};
        byte[] ax = {4, 5};
        byte[] bx = {7, 5};
        byte[] cx = {4, 3};
        byte[] axx = {4, 1};
        byte[] bxx = {6, 9};
        byte[] cxx = {8, 10};
        byte[] r = ByteUtils.addBytes(100, a, b, c, ax, bx, cx, axx, bxx, cxx);
        System.out.println(java.util.Arrays.toString(r));
    }
}

