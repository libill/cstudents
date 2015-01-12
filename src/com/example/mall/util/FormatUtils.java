package com.example.mall.util;

import java.util.Random;

public class FormatUtils {

	public static String getUUID() {
		int targetLen = 32;
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < targetLen; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
