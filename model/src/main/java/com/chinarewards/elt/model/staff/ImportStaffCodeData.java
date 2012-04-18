package com.chinarewards.elt.model.staff;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunhongliang, wensukun
 * 
 */
public class ImportStaffCodeData {

	public final static Object[] CODE1 = {
		new Long(1), "员工编号已经存在，不能新增", new Long(1000), ImportCodeType.FATAL
};
	public final static Object[] CODE2 = {
		new Long(2), "出生日期在未来", new Long(3900), ImportCodeType.WARN
};
	public final static Object[] CODE6 = {
		new Long(6), "员工姓名不能为空", new Long(4000), ImportCodeType.FATAL
};
	public final static Object[] CODE9 = {
		new Long(9), "电邮格式不正确", new Long(3900), ImportCodeType.FATAL
};
	public final static Object[] CODE10 = {
		new Long(10), "电邮不能为空", new Long(5000), ImportCodeType.FATAL
};
	public final static Object[] CODE11 = {
		new Long(11), "手机号码必须是1开头的数字且为11位", new Long(5000), ImportCodeType.WARN
};
	public final static Object[] CODE12 = {
		new Long(12), "手机号码不能为空", new Long(5000), ImportCodeType.WARN
};
//	public final static Object[] CODE13 = {
//		new Long(13), "部门列格式有误", new Long(1500), ImportCodeType.FATAL
//};
	public final static Object[] CODE17 = {
		new Long(17), "邮箱已经被注册", new Long(1000), ImportCodeType.FATAL
};
	public final static Object[] CODE18 = {
		new Long(18), "手机号已经被注册", new Long(1000), ImportCodeType.WARN
};
	public final static Object[] CODE19 = {
		new Long(19), "文档中存在相同手机号", new Long(1000), ImportCodeType.WARN
};
	public final static Object[] CODE20 = {
		new Long(20), "文档中存在相同邮箱", new Long(1000), ImportCodeType.FATAL
};
	public final static Object[] CODE21 = {
		new Long(21), "文档中存在相同员工编号", new Long(1000), ImportCodeType.FATAL
};
	
	
	public class ParserCode {
		private Long code;
		private String message;
		private Long priority;
		private ImportCodeType type;
		private String parserMethod;
		/**
		 * @return the code
		 */
		public Long getCode() {
			return code;
		}
		/**
		 * @param code the code to set
		 */
		public void setCode(Long code) {
			this.code = code;
		}
		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}
		/**
		 * @param message the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}
		/**
		 * @return the priority
		 */
		public Long getPriority() {
			return priority;
		}
		/**
		 * @param priority the priority to set
		 */
		public void setPriority(Long priority) {
			this.priority = priority;
		}
		/**
		 * @return the type
		 */
		public ImportCodeType getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(ImportCodeType type) {
			this.type = type;
		}
		/**
		 * @return the parserMethod
		 */
		public String getParserMethod() {
			return parserMethod;
		}
		/**
		 * @param parserMethod the parserMethod to set
		 */
		public void setParserMethod(String parserMethod) {
			this.parserMethod = parserMethod;
		}
	}

	/**
	 * 找出全部导入错误代码
	 * @return
	 * @throws Exception
	 */
	public static List<ParserCode> getAllImportStaffCode() throws Exception {
		List<ParserCode> result = new ArrayList<ParserCode>();
		Class<?> parser = Class.forName(ImportStaffCodeData.class.getName());
		Field[] fields = parser.getDeclaredFields();
		for(int j=0 ; j<fields.length ; j++){
            String name = fields[j].getName();
            Field field = parser.getField(name);
            Object[] value = (Object[])field.get(parser);
            ImportStaffCodeData data = new ImportStaffCodeData();
            ParserCode code = data.new ParserCode();
            code.setCode((Long)value[0]);
            code.setMessage((String)value[1]);
            code.setPriority((Long)value[2]);
            code.setType((ImportCodeType)value[3]);
			result.add(code);
		}
		return result;
	}

}
