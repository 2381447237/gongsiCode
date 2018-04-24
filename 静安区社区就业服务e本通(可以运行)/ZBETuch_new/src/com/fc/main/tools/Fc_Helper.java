package com.fc.main.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class Fc_Helper {

	static String public_key = "4006603260";

	public static String EncryptDES(String Text, String key) throws Exception {

		// String public_key = "62323260";
		byte[] pub_key = public_key.substring(0, 8).getBytes("utf-8");
		byte[] private_key = key.getBytes("utf-8");
		byte[] data = Text.getBytes("utf-8");

		/*
		 * Key puKey = new SecretKeySpec(pub_key, "DES"); Key prKey = new
		 * SecretKeySpec(private_key, "DES");
		 */
		/** 加密 */

		IvParameterSpec zeroIv = new IvParameterSpec(pub_key);
		SecretKeySpec _key = new SecretKeySpec(private_key, "DES");
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, _key, zeroIv);
		byte[] encryptedData = cipher.doFinal(data);

		String S_result = Base64.encodeToString(encryptedData, 0);

		return S_result;
	}

	/* 解密方法 */
	public static String DecryptDES1(String Text, String key) throws Exception {

		byte[] pub_key = public_key.getBytes("utf-8");
		byte[] private_key = key.getBytes("utf-8");
		byte[] data = Text.getBytes("utf-8");

		IvParameterSpec zeroIv = new IvParameterSpec(pub_key);
		SecretKeySpec _key = new SecretKeySpec(private_key, "DES");
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, _key, zeroIv);
		byte[] encryptedData = cipher.doFinal(data);

		String S_result = Base64.encodeToString(encryptedData, 0);

		return S_result;

	}

}