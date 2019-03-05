package com.hiair.cmm.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmmEncryptUtil {
	private static final Logger logger = LoggerFactory.getLogger(CmmEncryptUtil.class);

	private static String algorithm = "AES";
	private static SecretKey secretKey = null;

	static {
		final int keySize = 16;
		final byte[] keyBytes = new byte[keySize];

		final String keyStr = "HIAIR_KEY"; //** Key String **
		final byte[] temp = CmmEncryptUtil.getBase64Encode(keyStr.getBytes()).getBytes();
		final int len = temp.length;

		for (int i = 0; i < keySize; i++) {
			keyBytes[i] = temp[i % len];
		}
		CmmEncryptUtil.secretKey = new SecretKeySpec(keyBytes, CmmEncryptUtil.algorithm);
	}

	/**
	 * 문자열을 복호화 하는 메소드이다.(AES256 복호화)
	 * @param sValue - 복호화 할 문자열
	 * @return - 복호화 된 문자열(예외 발생시 빈 문자열 반환)
	 */
	public static String decrypt(final String sValue) {
		try {
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, CmmEncryptUtil.secretKey);
			return new String(cipher.doFinal(CmmEncryptUtil.getBase64Decode(sValue)), "UTF-8");
		} catch (final Exception e) {
			logger.error("decrypt method Exception", e);
			return "";
		}
	}

	/**
	 * 문자열을 암호화 하는 메소드이다.(AES256 암호화)
	 * @param sValue - 암호화 할 문자열
	 * @return - 함호화 된 문자열(예외 발생시 빈 문자열 반환)
	 */
	public static String encrypt(final String sValue) {
		try {
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, CmmEncryptUtil.secretKey);
			return CmmEncryptUtil.getBase64Encode(cipher.doFinal(sValue.getBytes("UTF-8")));
		} catch (final Exception e) {
			logger.error("encrypt method Exception", e);
			return "";
		}
	}

	/**
	 * SHA-512로 암호화하는 메소드이다.
	 * @param data 암호화할 문자열
	 * @return 암호화된 문자열(예외 발생시 빈 문자열 반환)
	 */
	public static String hashSha512(String data) {
		String result = "";

		if (CmmStringUtil.isEmpty(data))
			return result;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(data.getBytes());
			byte digest[] = md.digest();
			StringBuilder buffer = new StringBuilder();
			for (int i = 0; i < digest.length; i++) {
				buffer.append(String.format("%02x", 0xFF & digest[i]));
			}
			result = buffer.toString();
		} catch (Exception e) {
			logger.error("hashSha512 method Exception", e);
			result = "";
		}
		return result;
	}

	private static byte[] getBase64Decode(final String base64) {
		int pad = 0;
		for (int i = base64.length() - 1; base64.charAt(i) == '='; i--) {
			pad++;
		}
		final int length = ((base64.length() * 6) / 8) - pad;
		final byte[] raw = new byte[length];
		int rawIndex = 0;
		for (int i = 0; i < base64.length(); i += 4) {
			final int block = (CmmEncryptUtil.getValue(base64.charAt(i)) << 18) + (CmmEncryptUtil.getValue(base64.charAt(i + 1)) << 12) + (CmmEncryptUtil.getValue(base64.charAt(i + 2)) << 6) + (CmmEncryptUtil.getValue(base64.charAt(i + 3)));
			for (int j = 0; (j < 3) && ((rawIndex + j) < raw.length); j++) {
				raw[rawIndex + j] = (byte) ((block >> (8 * (2 - j))) & 0xff);
			}
			rawIndex += 3;
		}
		return raw;
	}

	private static String getBase64Encode(final byte[] raw) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < raw.length; i += 3) {
			sb.append(CmmEncryptUtil.encodeBlock(raw, i));
		}
		return sb.toString();
	}

	private static char[] encodeBlock(final byte[] raw, final int offset) {
		int block = 0;
		final int slack = raw.length - offset - 1;
		final int end = (slack >= 2) ? 2 : slack;
		for (int i = 0; i <= end; i++) {
			final byte b = raw[offset + i];
			final int neuter = (b < 0) ? b + 256 : b;
			block += neuter << (8 * (2 - i));
		}
		final char[] base64 = new char[4];
		for (int i = 0; i < 4; i++) {
			final int sixbit = (block >>> (6 * (3 - i))) & 0x3f;
			base64[i] = CmmEncryptUtil.getChar(sixbit);
		}
		if (slack < 1) {
			base64[2] = '=';
		}
		if (slack < 2) {
			base64[3] = '=';
		}
		return base64;
	}

	private static char getChar(final int sixBit) {
		if ((0 <= sixBit) && (sixBit <= 25)) {
			return (char) ('A' + sixBit);
		}
		if ((26 <= sixBit) && (sixBit <= 51)) {
			return (char) ('a' + (sixBit - 26));
		}
		if ((52 <= sixBit) && (sixBit <= 61)) {
			return (char) ('0' + (sixBit - 52));
		}
		if (sixBit == 62) {
			return '+';
		}
		if (sixBit == 63) {
			return '/';
		}
		return '?';
	}

	private static int getValue(final char c) {
		if (('A' <= c) && (c <= 'Z')) {
			return c - 'A';
		}
		if (('a' <= c) && (c <= 'z')) {
			return (c - 'a') + 26;
		}
		if (('0' <= c) && (c <= '9')) {
			return (c - '0') + 52;
		}
		if (c == '+') {
			return 62;
		}
		if (c == '/') {
			return 63;
		}
		if (c == '=') {
			return 0;
		}
		return - 1;
	}
}
