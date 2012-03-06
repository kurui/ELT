package com.chinarewards.elt.util;

public class StringUtil {
	public static boolean isEmptyString(String str) {
		return str == null || "".equals(str) || "null".equals(str);
	}
}
