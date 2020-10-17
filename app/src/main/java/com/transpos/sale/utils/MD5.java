package com.transpos.sale.utils;

import java.security.MessageDigest;

public class MD5 {

	/**
	 * 生成 MD5
	 *
	 * @param data
	 *            待处理数据
	 * @return MD5结果
	 */
	public static String hex_md5(String data) {
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(data.getBytes("UTF-8"));
			for (byte item : array) {
				sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return sb.toString().toUpperCase();
	}
}
