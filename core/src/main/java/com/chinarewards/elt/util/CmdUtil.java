package com.chinarewards.elt.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CmdUtil {

	public CmdUtil() {
	}

	public static String exec(String cmd) {
		StringBuffer sb1;
		StringBuffer sb2;
		sb1 = new StringBuffer();
		sb2 = new StringBuffer();
		String temp1 = "";
		String temp2 = "";
		Runtime rt = Runtime.getRuntime();
		try {
			System.out.println(cmd);
			Process p = rt.exec(cmd);
			BufferedReader bufferedReader1 = new BufferedReader(
					new InputStreamReader(p.getInputStream(),"GBK"));
			BufferedReader bufferedReader2 = new BufferedReader(
					new InputStreamReader(p.getErrorStream()));

			while ((temp1 = bufferedReader1.readLine()) != null) {
				sb1.append(temp1);
				System.out.println(temp1);
			}

			while ((temp2 = bufferedReader2.readLine()) != null) {
				sb2.append(temp2);
				System.out.println("error:"+temp2);
			}
			
			BufferedWriter bufferedReader3 = new BufferedWriter(
					new OutputStreamWriter(p.getOutputStream()));
			
			p.waitFor();
			
			return sb1.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb1.toString();
	}

	public static void main(String args[]) {
		try {
			String cmd="cmd   /c   dir   c:";
//			cmd=" keytool -import -alias publiccert -file F:/project/license-server/cert\\2012032916335679.cer -keystore F:/project/license-server/cert\\201203281035422591.store -storepass  publicstore123 -validity 3500";
//			cmd+=" y";
			cmd="ipconfig /all";
			String result=exec(cmd);
			
//			System.out.println("result:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
