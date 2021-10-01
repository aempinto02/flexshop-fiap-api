package br.com.buzz.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class Hashing {

	public static String md5(String content) {
		String md5 = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(content.getBytes());
			BigInteger number = new BigInteger(1, digest);
			return StringUtils.leftPad(number.toString(16), 32, "0");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return md5;
	}
}
