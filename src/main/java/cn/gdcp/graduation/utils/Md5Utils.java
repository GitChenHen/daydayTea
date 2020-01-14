package cn.gdcp.graduation.utils;

import org.springframework.util.DigestUtils;


public class Md5Utils {
	public static String getPasswrod(String passwrod, String salf) throws Exception {
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(passwrod.getBytes());
		md5DigestAsHex = DigestUtils.md5DigestAsHex((md5DigestAsHex + salf).getBytes());
		for (int i = 0; i < 20; i++) {
			md5DigestAsHex = DigestUtils.md5DigestAsHex((md5DigestAsHex + (char) i).getBytes());
		}
		return md5DigestAsHex;
	}
}
