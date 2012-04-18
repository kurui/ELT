package com.chinarewards.elt.util;

public class StringUtil {
	public static boolean isEmptyString(String str) {
		return str == null || "".equals(str) || "null".equals(str);
	}
	
    public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }  
    public static boolean isValidEmail(Object value) {
        if(value == null) return false;
        
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";
        
        boolean valid = false;
        
        if(value.getClass().toString().equals(String.class.toString())) {
                valid = ((String)value).matches(emailPattern);
        } else {
                valid = ((Object)value).toString().matches(emailPattern);
        }

        return valid;
}
}
