package com.chinarewards.gwt.elt.client.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Cream
 * @since 0.2.0 2011-01-07
 */
public class StringUtil {

	/**
	 * 
	 * @param str
	 * @return true - when input string is {@code null} or equals to "".
	 */
	public static boolean isEmpty(String str) {
		return null == str ? true : "".equals(str);
	}

	/**
	 * Returns {@code null} when input string is null.
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return null == str ? null : str.trim();
	}

	/**
	 * Truncating string.
	 * 
	 * @param str
	 * @param length
	 *            - length of output string.
	 * @return Returns "" if input <code>null</code>.
	 */
	public static String truncate(String str, int length) {
		if (isEmpty(str)) {
			return "";
		}
		String result = null;
		if (str.length() > length) {
			result = str.substring(0, length);
		} else {
			result = str;
		}
		return result;
	}

	/**
	 * The special Object must implement toString()
	 * 
	 * @param obj
	 * @return
	 */
	public static String valueOf(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	public static boolean isEmail(String email) {
		try {
	        Pattern p = Pattern.compile("(\\w+.)+@(\\w+.)+[a-z]{2,3}");  
	        Matcher m = p.matcher(email);  
	        boolean b = m.matches();  
	        return b;  

		} catch (Exception e) {
			return false;
		}
	}

}
