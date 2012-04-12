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

	public final static Object[] CODE1 = { new Long(1),
			"isStaffNumberDuplicated" };
	public final static Object[] CODE2 = { new Long(2), "isDobFuture" };
	public final static Object[] CODE3 = { new Long(3), "isMemberCardNumerUsed" };
	public final static Object[] CODE4 = { new Long(4),
			"isMemberCardNumberNotInChannel" };
	public final static Object[] CODE5 = { new Long(5),
			"isMemberCardNotDigit10" };
	public final static Object[] CODE6 = { new Long(6), "isLastNameEmpty" };
	public final static Object[] CODE7 = { new Long(7), "isFirstNameEmpty" };
	public final static Object[] CODE8 = { new Long(8), "isEmployedDateFuture" };
	public final static Object[] CODE9 = { new Long(9),
			"isEmailAddressFormatInvalid" };
	public final static Object[] CODE10 = { new Long(10), "isEmailAddressEmpty" };
	public final static Object[] CODE11 = { new Long(11),
			"isMobileTelephoneNumberInvalid" };
	public final static Object[] CODE12 = { new Long(12),
			"isMobileTelephoneNumberEmpty" };
	public final static Object[] CODE13 = { new Long(13), "isDepartmentInvalid" };
	public final static Object[] CODE14 = { new Long(14),
			"isIdNoDigitNot15or18" };
	public final static Object[] CODE015 = { new Long(15),
			"isMemberCardNumberNotEnough" };
	public final static Object[] CODE016 = { new Long(16),
			"isDesiredMemberCardNumberDuplicated" };
	public final static Object[] CODE17 = { new Long(17),
			"isEmailAddressDuplicated" };
	public final static Object[] CODE18 = { new Long(18),
			"isMobileTelephoneNumberDuplicated" };
	public final static Object[] CODE19 = { new Long(19),
			"isMobileTelephoneNumberDuplicatedInBatch" };
	public final static Object[] CODE20 = { new Long(20),
			"isEmailAddressDuplicatedInBatch" };
	public final static Object[] CODE21 = { new Long(21),
			"isStaffNumberDuplicatedInBatch" };

//	/**
//	 * 
//	 * 手机号是否在当前上传批次中存在，存在返回true
//	 * 
//	 * @param pStaffRaw
//	 * @param helper
//	 * @return
//	 */
//	public static boolean isStaffNumberDuplicatedInBatch(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (pStaffRaw.getStaffNumber() != null
//				&& helper.getDesiredStaffNumber() != null
//				&& helper.getDesiredStaffNumber().indexOf(
//						pStaffRaw.getStaffNumber()) != helper
//						.getDesiredStaffNumber().lastIndexOf(
//								pStaffRaw.getStaffNumber())) {
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * 
//	 * 手机号是否在当前上传批次中存在，存在返回true
//	 * 
//	 * @param pStaffRaw
//	 * @param helper
//	 * @return
//	 */
//	public static boolean isMobileTelephoneNumberDuplicatedInBatch(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (pStaffRaw.getMobileTelephoneNumber() != null
//				&& helper.getDesiredMobileNos() != null
//				&& helper.getDesiredMobileNos().indexOf(
//						pStaffRaw.getMobileTelephoneNumber()) != helper
//						.getDesiredMobileNos().lastIndexOf(
//								pStaffRaw.getMobileTelephoneNumber())) {
//			return true;
//		}
//		return false;
//	}

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

//	/**
//	 * 
//	 * 手机号已经存在，存在返回true
//	 * 
//	 * @param pStaffRaw
//	 * @param helper
//	 * @return
//	 */
//	public static boolean isMobileTelephoneNumberDuplicated(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (pStaffRaw.getMobileTelephoneNumber() != null
//				&& helper.getExistingEmailAddress() != null
//				&& helper.getExistingMobileNos().contains(
//						pStaffRaw.getMobileTelephoneNumber())) {
//			return true;
//		}
//		return false;
//	}

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

//	/**
//	 * 
//	 * 判断导入的员工身份证不是18位或者15位，返回true
//	 * 
//	 * @param pStaffRaw
//	 * @param helper
//	 * @return
//	 */
//	public static boolean isIdNoDigitNot15or18(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//
//		if (pStaffRaw.getIdNo() != null) {
//			if (pStaffRaw.getIdNo().length() == 18
//					|| pStaffRaw.getIdNo().length() == 15) {
//				return false;
//			} else {
//				return true;
//			}
//		}
//
//		return false;
//	}

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
//
//	/**
//	 * 就职日期在未来，返回true
//	 */
//	public static boolean isEmployedDateFuture(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (pStaffRaw.getDateOfEmployment() != null
//				&& !pStaffRaw.getDateOfEmployment().equals("")) {
//			if (helper.getDoeFormat() != null && helper.getNow() != null) {
//				SimpleDateFormat format = new SimpleDateFormat(
//						helper.getDoeFormat());
//				Date src;
//				try {
//					src = format.parse(pStaffRaw.getDateOfEmployment());
//					if (src.compareTo(helper.getNow()) > 0) {
//						return true;
//					}
//				} catch (ParseException e) {
//					// e.printStackTrace();
//				}
//
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 
//	 * 手机号码为空，返回true
//	 * 
//	 * @param pStaffRaw
//	 * @return
//	 */
//	public static boolean isMobileTelephoneNumberEmpty(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (pStaffRaw.getMobileTelephoneNumber() == null
//				|| pStaffRaw.getMobileTelephoneNumber().equals("")) {
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * 
//	 * 手机号码不是13、15、18开头的数字并且为11位，非法手机号返回true
//	 * 
//	 * @param pStaffRaw
//	 * @return
//	 */
//	public static boolean isMobileTelephoneNumberInvalid(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		String reg = "^[1][358][0-9]{9}$";
//		if (!isMobileTelephoneNumberEmpty(pStaffRaw, null)) {
//			if (!pStaffRaw.getMobileTelephoneNumber().matches(reg)) {
//				return true;
//			}
//		}
//		return false;
////	}
//
//	/**
//	 * 判断中文姓为空，返回true
//	 * 
//	 * @param pStaffRaw
//	 * @return
//	 */
//	public static boolean isFirstNameEmpty(ImportStaffRawParameter pStaffRaw,
//			final ImportStaffEjbHelper helper) {
//		if (pStaffRaw.getFirstName() == null
//				|| pStaffRaw.getFirstName().trim().equals("")) {
//			return true;
//		}
//		return false;
//	}

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

	/**
	 * 
	 * 部门格式有误(部门不能为空、父子不能同名，否则)返回true
	 * 
	 * @param pStaffRaw
	 * @return
	 */
	public static boolean isDepartmentInvalid(
			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
		if (pStaffRaw.getDepartment() == null
				|| pStaffRaw.getDepartment().trim().equals("")) {
			return true;
		} else {
			if (parseStringByDelimiter(pStaffRaw.getDepartment(),
					helper.IMPORT_STAFF_DEPARTMENT_DELIMITER,
					helper.IMPORT_STAFF_DEPARTMENT_ESCAPE_CHAR) == null) {
				return true;
			}
		}
		return false;
	}

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

//	/**
//	 * 验证卡号不属于本企业卡号段，返回true
//	 * 
//	 * @param pStaffRaw
//	 * @param PreCode
//	 * @return
//	 */
//	public static boolean isMemberCardNumberNotInChannel(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (// ignore current checking if card number is not digit 10
//		!ImportStaffParser.isMemberCardNotDigit10(pStaffRaw, null)
//		// ignore not input member card, namely, automatic dispatch
//				&& pStaffRaw.getMemberCardNumber() != null) {
//			try {
//				Long inputCardNumber = Long.parseLong(pStaffRaw
//						.getMemberCardNumber());
//				if (inputCardNumber > helper.getLastCardNumberInChannel()
//						|| inputCardNumber < helper
//								.getFirstCardNumberInChannel()) {
//					return true;
//				}
//			} catch (Exception e) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 
//	 * 员工卡号不是10位的卡号，返回true
//	 * 
//	 * @param pStaffRaw
//	 * @return
//	 */
//	public static boolean isMemberCardNotDigit10(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (pStaffRaw.getMemberCardNumber() != null
//				&& !pStaffRaw.getMemberCardNumber().equals("")) {
//			if (pStaffRaw.getMemberCardNumber().length() != 10) {
//				return true;
//			}
//
//		}
//		return false;
//	}
//
//	/**
//	 * 
//	 * 如果指定卡号已经被企业现有员工使用，返回true
//	 * 
//	 * @param pStaffRaw
//	 * @param usedCard
//	 * @return
//	 */
//	public static boolean isMemberCardNumerUsed(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (// ignore current checking if card number is not digit 10
//		!isMemberCardNotDigit10(pStaffRaw, null)
//		// ignore not input member card, namely, automatic dispatch
//				&& pStaffRaw.getMemberCardNumber() != null) {
//			if (helper.getExistingMemberCardNumbers() != null
//					&& helper.getExistingMemberCardNumbers().contains(
//							pStaffRaw.getMemberCardNumber())) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 
//	 * 自动分配卡号，但卡号段已经没有可分配卡号，返回true
//	 * 
//	 * @param pStaffRaw
//	 * @param usedCard
//	 * @param strSrc
//	 * @param temp
//	 * @return
//	 */
//	public static boolean isMemberCardNumberNotEnough(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//
//		return helper.getAssignedCardNumber() == null;
//	}
//
//	/**
//	 * 
//	 * 手工指定的会员卡号在上传文件里重复，返回true
//	 * 
//	 * @param pStaffRaw
//	 * @param helper
//	 * @return
//	 */
//	public static boolean isDesiredMemberCardNumberDuplicated(
//			ImportStaffRawParameter pStaffRaw, final ImportStaffEjbHelper helper) {
//		if (// ignore once digit 10 checking failed
//		!isMemberCardNotDigit10(pStaffRaw, helper)
//		// ignore once card already used checking failed
//				&& !isMemberCardNumerUsed(pStaffRaw, helper)
//				// ignore not input member card, namely, automatic dispatch
//				&& pStaffRaw.getMemberCardNumber() != null) {
//			if (helper.getDesiredMemberCardNumbers().indexOf(
//					pStaffRaw.getMemberCardNumber()) != helper
//					.getDesiredMemberCardNumbers().lastIndexOf(
//							pStaffRaw.getMemberCardNumber())) {
//				return true;
//			}
//
//		}
//		return false;
//	}

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
