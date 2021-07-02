package com.qx.imlib.utils.encry;


import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 *
 * @author hechuan
 * @since JDK 1.8
 */
public class CryptUtil {

    public static final String PUBLIC_KEY = "_PUBLIC_KEY";

    public static final String PRIVATE_KEY = "_PRIVATE_KEY";

    final static private String UTF_8 = "UTF-8";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    /**
     * 随机盐生成源
     */
    private static final char[] SALT_RANDOM_SOURCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789`~!@#$%^&*()_+-={}[]:\";'<>?,./'".toCharArray();


    /**
     * 生成RSA密钥对
     *
     * @return 公钥-私钥对
     * @throws Exception
     * @author hechuan
     */
    public static Map<String, String> genRsaKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair kp = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = kp.getPrivate();
        PublicKey publicKey = kp.getPublic();
        Map<String, String> map = new HashMap<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            map.put(PRIVATE_KEY, Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            String aaaaa = Base64.getEncoder().encodeToString(publicKey.getEncoded());
//            Log.e("key", aaaaa);
            map.put(PUBLIC_KEY, Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        } else {
            map.put(PRIVATE_KEY,android.util.Base64.encodeToString(privateKey.getEncoded(), android.util.Base64.DEFAULT));
            String aaaaa = android.util.Base64.encodeToString(publicKey.getEncoded(), android.util.Base64.DEFAULT);
//            Log.e("key", "8.0 " + aaaaa);
            map.put(PUBLIC_KEY, android.util.Base64.encodeToString(publicKey.getEncoded(), android.util.Base64.DEFAULT));
        }

        return map;
    }

    /**
     * RSA公钥加密
     *
     * @param content 加密内容
     * @param pub     公钥
     * @return 加密byte数组
     * @throws Exception
     * @author hechuan
     */
    public static byte[] RSAEncrypt(byte[] content, String pub) throws Exception {
        byte[] keyBytes;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            keyBytes = Base64.getDecoder().decode(pub.getBytes(UTF_8));
        } else {
            keyBytes = android.util.Base64.decode(pub.getBytes(UTF_8), android.util.Base64.DEFAULT);
        }
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        int inputLen = content.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(content, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(content, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * RSA私钥解密
     *
     * @param content 解密内容
     * @param pri     私钥
     * @return 解密byte数组
     * @throws Exception
     * @author hechuan
     */
    public static byte[] RSADecrypt(byte[] content, String pri) throws Exception {
        try {

            byte[] keyBytes;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                keyBytes = Base64.getDecoder().decode(pri.getBytes(UTF_8));
            } else {
                keyBytes = android.util.Base64.decode(pri.getBytes(UTF_8), android.util.Base64.DEFAULT);
            }
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//			Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            int inputLen = content.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(content, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5加密字节数组
     *
     * @return 加密字符串
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws Exception
     * @author hechuan
     */
    public static String MD5Encode(byte[] bytes) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        byte b[] = md.digest();

        int i;

        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        // 32位加密
        return buf.toString();
        // 16位的加密
        // return buf.toString().substring(8, 24);

    }

    /**
     * MD5加密
     *
     * @param str 加密内容
     * @return 加密字符串
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws Exception
     * @author hechuan
     */
    public static String MD5Encode(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return MD5Encode(str.getBytes(UTF_8));
    }

    /**
     * 随机加盐加密
     *
     * @param src     加密字符串
     * @param times   加盐加密最大次数
     * @param saltLen 每次加盐最大长度
     * @return String[0]-加密后的字符串；String[1]-生成的随机盐编码
     * @throws Exception
     * @author hechuan
     */
    public static final String[] MD5EncryptWithRandomSalt(String src, int times, int saltLen) throws Exception {
        Random random = new Random();
        int count = random.nextInt(times) + 1; // 加盐1-6次
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            int len = random.nextInt(saltLen) + 1; // 当前盐长度
            int pos = random.nextInt(src.length() > 10 ? 10 : src.length()); // 加盐位置
            String curt = "";
            for (int j = 0; j < len; j++) {
                curt += SALT_RANDOM_SOURCE[random.nextInt(SALT_RANDOM_SOURCE.length)];
            }
            sb.append(len).append(pos).append(curt);
            String prefix = src.substring(0, pos);
            String suffix = src.substring(pos, src.length());
            src = prefix + curt + suffix;
            src = MD5Encode(src);
        }
        String[] result = new String[2];
        result[0] = src; // 加密串
        result[1] = sb.toString(); // 盐值
        return result;
    }

    /**
     * 随机加盐加密
     *
     * @param src      加密字符串
     * @param saltCode 加盐编码
     * @return
     * @throws Exception
     * @author hechuan
     */
    public static final String MD5EncryptWithSaltCode(String src, String saltCode) throws Exception {
        // 加密密码
        while (true) {
            int len = Integer.valueOf(saltCode.substring(0, 1));
            int pos = Integer.valueOf(saltCode.substring(1, 2));
            String curt = saltCode.substring(2, 2 + len);
            String prefix = src.substring(0, pos);
            String suffix = src.substring(pos, src.length());
            src = prefix + curt + suffix;
            src = CryptUtil.MD5Encode(src);
            saltCode = saltCode.substring(2 + len);

            // 结束
            if (saltCode.length() == 0) {
                break;
            }

        }
        return src;
    }

    /**
     * SHA加密 生成40位SHA码
     *
     * @return 加密字符串
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @author hechuan
     */
    public static String SHA1Encode(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] byteArray = str.getBytes(UTF_8);
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 生成SHA256
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     * @author hechuan
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 对文件MD5
     *
     * @param file
     * @return
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @author hechuan
     */
    public static String fileMD5Encode(File file) throws NoSuchAlgorithmException, IOException {
        BigInteger bigInt = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            bigInt = new BigInteger(1, md.digest());
            return bigInt.toString(16);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (NoSuchAlgorithmException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {

            }
        }

    }

    public static class CryptException extends Exception {
        public CryptException(Throwable cause) {
            super(cause);
        }

        public CryptException(String message) {
            super(message);
        }

        public CryptException(String message, Throwable cause) {
            super(message, cause);
        }

    }


    /**
     * AES加密
     *
     * @param content  加密内容
     * @param cryptKey 加密key
     * @return 加密byte数组
     * @throws Exception
     * @author hechuan
     */
    final static public byte[] AESEncrypt(byte[] content, byte[] cryptKey) throws CryptException {
        try {
            SecretKeySpec key = new SecretKeySpec(cryptKey, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            IvParameterSpec iv = new IvParameterSpec("0123456789012345".getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(content);// 加密
            return result;
        } catch (Exception e) {
            throw new CryptException(e);
        }
    }

    /**
     * AES解密
     *
     * @param content  解密内容
     * @param cryptKey 加密key
     * @return 解密byte数组
     * @throws Exception
     * @author hechuan
     */
    public static byte[] AESDecrypt(byte[] content, byte[] cryptKey) throws CryptException {
        try {
            SecretKeySpec key = new SecretKeySpec(cryptKey, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            IvParameterSpec iv = new IvParameterSpec("0123456789012345".getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, key, iv);// 初始化为解密模式的密码器
            byte[] result = cipher.doFinal(content);
            return result; // 明文
        } catch (Exception e) {
            throw new CryptException(e);
        }
    }

    /**
     * AES加密
     *
     * @param content  加密内容
     * @param cryptKey 加密key
     * @return 加密byte数组
     * @throws Exception
     * @throws UnsupportedEncodingException
     * @author hechuan
     */
    final static public byte[] AESEncrypt(byte[] content, String cryptKey) throws CryptException, UnsupportedEncodingException {
        return AESEncrypt(content, cryptKey.getBytes("UTF-8"));
    }

    /**
     * AES解密
     *
     * @param content  解密内容
     * @param cryptKey 加密key
     * @return 解密byte数组
     * @throws Exception
     * @throws UnsupportedEncodingException
     * @author hechuan
     */
    public static byte[] AESDecrypt(byte[] content, String cryptKey) throws CryptException, UnsupportedEncodingException {
        return AESDecrypt(content, cryptKey.getBytes("UTF-8"));
    }
}
