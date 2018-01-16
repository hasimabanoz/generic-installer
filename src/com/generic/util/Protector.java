package com.generic.util;

import java.io.IOException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.catalina.util.HexUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Protector {

	private static final String ALGORITHM = "DESede";
	private static final String keyValue = "0b0bad325752f1194626e6d076e56ead529291c20b973413";

	/**
	 * Encrypt clear text password
	 *
	 * @param value
	 *            Clear text password
	 * @return Encrypted password
	 * @throws Exception
	 */
	public static String encrypt(String value) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encValue = c.doFinal(value.getBytes());
		return new BASE64Encoder().encode(encValue);
	}

	/**
	 * Decrypt encrypted password
	 *
	 * @param value
	 *            Encrypted password
	 * @return Cleartext password
	 * @throws Exception
	 */
	public static String decrypt(String value) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.DECRYPT_MODE, key);

		byte[] decordedValue = new BASE64Decoder().decodeBuffer(value);
		byte[] decValue = c.doFinal(decordedValue);
		return new String(decValue);
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(HexUtils.convert(keyValue), ALGORITHM);
		return key;
	}

	public static void main(String[] args) throws Exception {
		try {
			String encriptedPwd = Protector.encrypt(args[0]);
			System.out.println("Encrypted : " + encriptedPwd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}