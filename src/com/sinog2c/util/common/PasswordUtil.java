package com.sinog2c.util.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

	/**
	 * 对密码按国科政信V2的约定进行加密
	 * @param plainPassword 未加密的密码
	 * @return 返回两次标准 md5 摘要后的密码文本
	 */
	public static String password(String plainPassword) {
		// 两次 md5,
		String password_md5_md5 = md5(md5(plainPassword));
		return password_md5_md5;
	}
	
	/**
	 * 检查密码是否匹配
	 * @param plainPassword 纯文本的密码
	 * @param encryptedPassword 加密后的密码
	 * @return
	 */
	public static boolean passwordCheck(String plainPassword, String encryptedPassword) {
		// 是否登录成功
		boolean checkStatus = false;
		// 如果用户密码就没有设置密码
		if(null == encryptedPassword || encryptedPassword.length()< 1){
			if(null == plainPassword || plainPassword.length()< 1){
				// 用户也没有输入密码,则认为校验成功
				checkStatus = true;
			}
		} else {
			// 两次 md5,
			String password_md5_md5 = password(plainPassword);
			// 不区分大小写, 对比是否相等
			if(encryptedPassword.equalsIgnoreCase(password_md5_md5)){
				// 密码校验成功
				checkStatus = true;
			}
		}
		
		return checkStatus;
	}
	
	/**
	 * 对数字执行 md5 摘要加密
	 * @param number
	 * @return
	 */
	public static String md5(long number) {
		return md5("" + number);
	}
	/**
	 * 对文本执行 md5 摘要加密, 此算法与 mysql,JavaScript生成的md5摘要进行过一致性对比.
	 * @param plainText
	 * @return
	 */
	public static String md5(String plainText) {
		if (null == plainText) {
			plainText = "";
		}
		String MD5Str = "";
		try {
			// JDK 6 支持以下6种消息摘要算法，不区分大小写
			// md5,sha(sha-1),md2,sha-256,sha-384,sha-512
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuilder builder = new StringBuilder(32);
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					builder.append("0");
				builder.append(Integer.toHexString(i));
			}
			MD5Str = builder.toString();
			// LogUtil.println("result: " + buf.toString());// 32位的加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return MD5Str;
	}

	/**
	 * 对文本执行 SHA-1 摘要加密
	 * @param plainText
	 * @return
	 */
	public static String sha(String plainText) {
		if (null == plainText) {
			plainText = "";
		}
		String MD5Str = "";
		try {
			// JDK 6 支持以下6种消息摘要算法，不区分大小写
			// md5,sha(sha-1),md2,sha-256,sha-384,sha-512
			MessageDigest md = MessageDigest.getInstance("sha");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuilder builder = new StringBuilder();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					builder.append("0");
				builder.append(Integer.toHexString(i));
			}
			MD5Str = builder.toString();
			// LogUtil.println("result: " + buf.toString());// 32位的加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return MD5Str;
	}
	/**
	 * 对文本执行 SHA-256 摘要加密
	 * @param strSrc
	 * @return
	 */
	public static String sha256(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		String encName = "SHA-256";
		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	/**
	 * 二进制字节,转换为16进制字符串,字母小写
	 * @param bts
	 * @return
	 */
	public static String bytes2Hex(byte[] bts) {
		StringBuilder builder = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				builder.append("0");
			}
			builder.append(tmp);
		}
		return builder.toString();
	}
}
