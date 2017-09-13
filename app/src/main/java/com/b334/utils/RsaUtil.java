package com.b334.utils;

import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;



/**
 * 用于RSA加密与解密
 */
public class RsaUtil {
	/**
	 * RSA加密
	 * 
	 * @param data
	 *            加密前的数据
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static String encryptData(String data, String publicKey) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(
					Base64.decodeBase64(publicKey.getBytes()));
			KeyFactory keyf = KeyFactory.getInstance("RSA", "BC");
			PublicKey pubKey = keyf.generatePublic(pubX509);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] dataToEncrypt = data.getBytes("utf-8");
			byte[] encryptedData = cipher.doFinal(dataToEncrypt);
			byte[] encode = android.util.Base64.encode(encryptedData,
					android.util.Base64.DEFAULT);
			String encryptString = new String(Base64.encodeBase64(encryptedData, false), "UTF-8");
			// String encryptString = Base64.encodeBase64String(encryptedData);
//			String encryptString = new String(encode);
			return encryptString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA解密
	 * 
	 * @param data
	 *            加密后的数据
	 * @param privateKey
	 *            密钥
	 * @return
	 */
	public static String decryptData(String data, String privateKey) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decodeBase64(privateKey.getBytes()));
			KeyFactory keyf = KeyFactory.getInstance("RSA", "BC");
			PrivateKey privKey = keyf.generatePrivate(priPKCS8);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] descryptData = Base64.decodeBase64(data);
			byte[] descryptedData = cipher.doFinal(descryptData);
			String srcData = new String(descryptedData, "utf-8");
			return srcData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回的是String数组 String[0]代表公钥的Base64编码 String[1]代表私钥Base64编码
	 * 
	 * @return
	 */
	public static String[] createKeyPairs() {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA",
					"BC");
			generator.initialize(512, new SecureRandom());
			KeyPair pair = generator.generateKeyPair();
			PublicKey pubKey = pair.getPublic();
			PrivateKey privKey = pair.getPrivate();
			byte[] pk = pubKey.getEncoded();
			byte[] privk = privKey.getEncoded();
			String strpk = new String(Base64.encodeBase64(pk));
			String strprivk = new String(Base64.encodeBase64(privk));
			System.out.println("..." + strprivk);
			String[] strings = { URLEncoder.encode(strpk, "utf-8"), strprivk,
					"utf-8" };
			return strings;
			// System.out.println("公钥Base64编码:" + strpk);
			// System.out.println("私钥Base64编码:" + strprivk);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
