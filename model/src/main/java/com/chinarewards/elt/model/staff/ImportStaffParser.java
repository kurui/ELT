package com.chinarewards.elt.model.staff;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author sunhongliang, wensukun
 * 
 */
public class ImportStaffParser {

	public final static Object[] CODE1 = { new Long(1),"isStaffNumberDuplicated" };//编号重复
	public final static Object[] CODE2 = { new Long(2), "isDobFuture" };//生日错位
	public final static Object[] CODE6 = { new Long(6), "isNameEmpty" };//名字为空
	public final static Object[] CODE9 = { new Long(9),"isEmailAddressFormatInvalid" };//邮件地址错误
	public final static Object[] CODE10 = { new Long(10), "isEmailAddressEmpty" };//邮件为空
	public final static Object[] CODE11 = { new Long(11),	"isMobileTelephoneNumberInvalid" };//手机号错误
	public final static Object[] CODE12 = { new Long(12),	"isMobileTelephoneNumberEmpty" };//手机号为空
	public final static Object[] CODE13 = { new Long(13), "isDepartmentInvalid" };//部门错误
	public final static Object[] CODE17 = { new Long(17),	"isEmailAddressDuplicated" };//邮件地址重复
	public final static Object[] CODE18 = { new Long(18),	"isMobileTelephoneNumberDuplicated" };//手机号码重复
	public final static Object[] CODE19 = { new Long(19),	"isMobileTelephoneNumberDuplicatedInBatch" };//本批次中有相同手机号
	public final static Object[] CODE20 = { new Long(20),	"isEmailAddressDuplicatedInBatch" };//本批次中有相同邮件地址
	public final static Object[] CODE21 = { new Long(21),	"isStaffNumberDuplicatedInBatch" };//本批次中有相同员工编号

	/**
	 * 
	 * 员工编号是否在当前上传批次中存在，存在返回true
	 * 
	 * @param pStaffRaw
	 * @param helper
	 * @return
	 */
	public static boolean isStaffNumberDuplicatedInBatch(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getJobNo() != null
				&& helper.getDesiredStaffNumber() != null
				&& helper.getDesiredStaffNumber().indexOf(
						pStaffRaw.getJobNo()) != helper
						.getDesiredStaffNumber().lastIndexOf(
								pStaffRaw.getJobNo())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 手机号是否在当前上传批次中存在，存在返回true
	 * 
	 * @param pStaffRaw
	 * @param helper
	 * @return
	 */
	public static boolean isMobileTelephoneNumberDuplicatedInBatch(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getPhone() != null
				&& helper.getDesiredMobileNos() != null
				&& helper.getDesiredMobileNos().indexOf(
						pStaffRaw.getPhone()) != helper
						.getDesiredMobileNos().lastIndexOf(
								pStaffRaw.getPhone())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * email是否在当前上传批次中存在，存在返回true
	 * 
	 * @param pStaffRaw
	 * @param helper
	 * @return
	 */
	public static boolean isEmailAddressDuplicatedInBatch(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getEmail() != null
				&& helper.getDesiredEmailAddress() != null
				&& helper.getDesiredEmailAddress().indexOf(
						pStaffRaw.getEmail()) != helper
						.getDesiredEmailAddress().lastIndexOf(
								pStaffRaw.getEmail())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * email是否已经存在，存在返回true
	 * 
	 * @param pStaffRaw
	 * @param helper
	 * @return
	 */
	public static boolean isEmailAddressDuplicated(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getEmail() != null
				&& helper.getExistingEmailAddress() != null
				&& helper.getExistingEmailAddress().contains(
						pStaffRaw.getEmail())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 手机号已经存在，存在返回true
	 * 
	 * @param pStaffRaw
	 * @param helper
	 * @return
	 */
	public static boolean isMobileTelephoneNumberDuplicated(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getPhone() != null
				&& helper.getExistingEmailAddress() != null
				&& helper.getExistingMobileNos().contains(
						pStaffRaw.getPhone())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 是否全部通过，全部通过返回true
	 * 
	 * @param pStaffRaw
	 * @param helper
	 * @return
	 */
	public static boolean isAllPassed(ImportStaffRawParameter pStaffRaw,
			final ImportStaffEjbHelper helper) {
		return helper.isAllPassed();
	}


	/**
	 * 
	 * 判断电邮为空，返回true
	 * 
	 * @param pStaffRaw
	 * @param helper
	 * @return
	 */
	public static boolean isEmailAddressEmpty(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getEmail() == null
				|| pStaffRaw.getEmail().trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 电邮格式错误，返回true
	 * 
	 * @param pStaffRaw
	 * @param helper
	 * @return
	 */
	public static boolean isEmailAddressFormatInvalid(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (isEmailAddressEmpty(pStaffRaw, helper)) {
			return false;
		} else {
			return helper.isEmailFormatInvalid();
		}
	}

	/**
	 * 出生日期在未来，返回true
	 */
	public static boolean isDobFuture(ImportStaffRawParameter pStaffRaw,
			final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getDob() != null && !pStaffRaw.getDob().equals("")) {
			if (helper.getDobFormat() != null && helper.getNow() != null) {
				SimpleDateFormat format = new SimpleDateFormat(
						helper.getDobFormat());
				try {
					Date src = format.parse(pStaffRaw.getDob());
					if (src.compareTo(helper.getNow()) > 0) {
						return true;
					}
				} catch (ParseException e) {
					// e.printStackTrace();
				}
			}

		}

		return false;
	}


	/**
	 * 
	 * 手机号码为空，返回true
	 * 
	 * @param pStaffRaw
	 * @return
	 */
	public static boolean isMobileTelephoneNumberEmpty(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getPhone() == null
				|| pStaffRaw.getPhone().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 手机号码不是13、15、18开头的数字并且为11位，非法手机号返回true
	 * 
	 * @param pStaffRaw
	 * @return
	 */
	public static boolean isMobileTelephoneNumberInvalid(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		String reg = "^[1][358][0-9]{9}$";
		if (!isMobileTelephoneNumberEmpty(pStaffRaw, null)) {
			if (!pStaffRaw.getPhone().matches(reg)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * 
	 * 判断中文名为空，返回true
	 * 
	 * @param pStaffRaw
	 * @return
	 */
	public static boolean isLastNameEmpty(ImportStaffRawParameter pStaffRaw,
			final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getName() == null
				|| pStaffRaw.getName().trim().equals("")) {
			return true;
		}
		return false;
	}

//	/**
//	 * 
//	 * 部门格式有误(部门不能为空、父子不能同名，否则)返回true
//	 * 
//	 * @param pStaffRaw
//	 * @return
//	 */
//	public static boolean isDepartmentInvalid(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (pStaffRaw.getDepartment() == null
//				|| pStaffRaw.getDepartment().trim().equals("")) {
//			return true;
//		} else {
//			if (parseStringByDelimiter(pStaffRaw.getDepartment(),
//					helper.IMPORT_STAFF_DEPARTMENT_DELIMITER,
//					helper.IMPORT_STAFF_DEPARTMENT_ESCAPE_CHAR) == null) {
//				return true;
//			}
//		}
//		return false;
//	}

	/**
	 * 
	 * 员工编号已经存在，不能被新增，返回true
	 * 
	 * @param pStaffRaw
	 * @return
	 */
	public static boolean isStaffNumberDuplicated(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getJobNo() != null
				&& !pStaffRaw.getJobNo().trim().equals("")) {
			if (helper.getExistingStaffNumbers().contains(
					pStaffRaw.getJobNo())) {
				return true;
			}
		}

		return false;
	}

	public class ParserMethod {
		private Long code;
		private String parserMethod;

		/**
		 * @return the code
		 */
		public Long getCode() {
			return code;
		}

		/**
		 * @param code
		 *            the code to set
		 */
		public void setCode(Long code) {
			this.code = code;
		}

		/**
		 * @return the parserMethod
		 */
		public String getParserMethod() {
			return parserMethod;
		}

		/**
		 * @param parserMethod
		 *            the parserMethod to set
		 */
		public void setParserMethod(String parserMethod) {
			this.parserMethod = parserMethod;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ParserMethod [code=" + code + ", parserMethod="
					+ parserMethod + "]";
		}
	}

	/**
	 * 找出全部导入检查方法
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public static List<ParserMethod> getAllImportStaffMethod()
			throws ClassNotFoundException, SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		List<ParserMethod> result = new ArrayList<ParserMethod>();
		Class<?> cls = Class.forName(ImportStaffParser.class.getName());
		Field[] fields = cls.getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			String name = fields[j].getName();
			Field field = cls.getField(name);
			Object[] value = (Object[]) field.get(cls);
			ImportStaffParser parser = new ImportStaffParser();
			ParserMethod method = parser.new ParserMethod();
			method.setCode((Long) value[0]);
			method.setParserMethod((String) value[1]);
			result.add(method);
		}
		return result;
	}

	public static String getParserMethod(Long code)
			throws ClassNotFoundException, SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		if (code == null)
			return null;
		Class<?> cls = Class.forName(ImportStaffParser.class.getName());
		Field[] fields = cls.getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			String name = fields[j].getName();
			Field field = cls.getField(name);
			Object[] value = (Object[]) field.get(cls);
			if (code.compareTo((Long) value[0]) == 0) {
				return (String) value[1];
			}
		}
		return null;
	}

	public static Long getParserCode(String method)
			throws ClassNotFoundException, SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		if (method == null)
			return null;
		Class<?> cls = Class.forName(ImportStaffParser.class.getName());
		Field[] fields = cls.getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			String name = fields[j].getName();
			Field field = cls.getField(name);
			Object[] value = (Object[]) field.get(cls);
			if (method.equals((String) value[1])) {
				return (Long) value[0];
			}
		}
		return null;
	}

	/**
	 * 执行此类的静态方法
	 * 
	 * @param className
	 * @param methodName
	 * @param args
	 * @return 执行方法返回的结果
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Object getParserMethodResult(String methodName, Object[] args)
			throws ClassNotFoundException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (methodName == null || "".equals(methodName.trim())) {
			// only
			return null;
		}
		Class<?> ownerClass = Class.forName(ImportStaffParser.class.getName());
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(null, args);
	}

	public static void main(String[] stra) throws Exception {
		String source = "部门1\\|\\\\|部门2|部门3|\\|部门4";
		String test = "技术部||开发部";
		source = test;
		StringTokenizer st = new StringTokenizer(source, "|", true);
		while (st.hasMoreTokens()) {
			System.out.println(st.nextToken());
		}
		System.out.println();
		List<String> result = convertRaw2Department(source);
//			parseStringByDelimiter(test,
//					"|",
//					'\\', false);
		for (String token : result) {
			System.out.println(token);
		}
//		System.out.println();
//		for (Object token : result.toArray()) {
//			System.out.println(token);
//		}
		System.out.println();
		System.out.println(convertDepartment2Raw(result));
	}

	public static int countAppointedCharAtEnd(String src, char ch) {
		int result = 0;
		for (int i = src.lastIndexOf(ch); i >= 0; i--) {
			if (src.charAt(i) == ch) {
				result++;
			} else {
				break;
			}

		}
		return result;
	}

	public static List<String> parseStringByDelimiter(String source,
			String delimiter, char escapeChar) {
		List<String> result = new ArrayList<String>();
		String myToken = null;
		boolean isTokenEnd = true;
		StringTokenizer st = new StringTokenizer(source, delimiter, true);
		while (st.hasMoreTokens()) {
			if (isTokenEnd) {
				if (myToken != null && !myToken.equals(delimiter)) {
					result.add(myToken);
				}
				myToken = st.nextToken();
			} else {
				myToken += st.nextToken();
			}
			if (myToken.endsWith(String.valueOf(escapeChar))) {
				if (countAppointedCharAtEnd(myToken, escapeChar) % 2 == 0) {
					isTokenEnd = true;
				} else {
					isTokenEnd = false;
				}
			} else if (myToken.equals(delimiter)) {
				isTokenEnd = true;
			} else if (myToken.endsWith(delimiter)) {
				isTokenEnd = false;
			} else {
				isTokenEnd = true;
			}
			System.out.println("myToken = " + myToken + "; isTokenEnd = " + isTokenEnd);
		}
		result.add(myToken);
		return result;
	}

	public static String populateStringByDelimiter(List<String> source,
			String delimiter, char escapeChar) {
		StringBuffer result = new StringBuffer();
		long index = 0;
		for (String src : source) {
			if (index > 0) {
				result.append(delimiter);
			}
			StringTokenizer st = new StringTokenizer(src, delimiter, true);
			while (st.hasMoreTokens()) {
				String myToken = st.nextToken();
//				System.out.println("myToken=" + myToken);
				if (myToken.equals(delimiter)) {
					result.append(escapeChar).append(delimiter);
				} else {
					if (myToken.indexOf("\\\\")>-1) {
						result.append(myToken.replaceAll("\\\\", "\\\\\\\\"));
					} else {
						result.append(myToken);
					}
				}
			}
			index++;
		}
		return result.toString();
	}

	public static List<String> convertRaw2Department(String source) {
		return parseStringByDelimiter(source,
				ImportStaffEjbHelper.IMPORT_STAFF_DEPARTMENT_DELIMITER,
				ImportStaffEjbHelper.IMPORT_STAFF_DEPARTMENT_ESCAPE_CHAR);
	}

	public static String convertDepartment2Raw(List<String> departments) {
		return populateStringByDelimiter(departments,
				ImportStaffEjbHelper.IMPORT_STAFF_DEPARTMENT_DELIMITER,
				ImportStaffEjbHelper.IMPORT_STAFF_DEPARTMENT_ESCAPE_CHAR);
	}
}
