/**
 * 
 */
package com.chinarewards.gwt.elt.util;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * 
 * 
 * @author yanxin
 * 
 */
public class SimpleDateTimeProvider implements DateTimeProvider {

	public Date getTime() {
		return new Date();
	}
	public static String formatData(String partten, Date date) {
		if (StringUtil.isEmpty(partten)) {
			partten = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(partten);
		return sdf.format(date);
	}
}
