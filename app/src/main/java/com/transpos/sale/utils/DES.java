package com.transpos.sale.utils;


import com.transpos.sale.utils.base64.UrlBase64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DES {

//	private static byte[] IV = { 1, 3, 7, 3, 3, 8, 2, 7 };

	private static byte[] IV = {8,8,8,8,8,8,8,8};
	private static String KEY_STR = "$FFFFFF$";// 密钥
	private static String CHARSETNAME = "UTF-8";// 编码
	private static String ALGORITHM = "DES";// 加密类型
	

	/**
	 * 对str进行DES加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(IV);
			SecretKeySpec key = new SecretKeySpec(KEY_STR.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			byte[] data = cipher.doFinal(str.getBytes());

			return new String(UrlBase64.encode(data), CHARSETNAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对str进行DES解密
	 * 
	 * @param str
	 * @return
	 */
	public static String decrypt(String str) {
		try {
			byte[] bytes = UrlBase64.decode(str);// ByteUtils.hexDecode(str);//Base64Utils.decode(str);

			IvParameterSpec zeroIv = new IvParameterSpec(IV);
			SecretKeySpec key = new SecretKeySpec(KEY_STR.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte data[] = cipher.doFinal(bytes);

			return new String(data, CHARSETNAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String en_str = encrypt("administrator@hotmail.com");

		System.out.println(en_str);

		String de_str = decrypt(en_str);

		System.out.println(de_str);

	}
}
