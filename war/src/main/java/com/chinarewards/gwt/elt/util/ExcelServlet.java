package com.chinarewards.gwt.elt.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @author yanrui
 * 
 * */
public class ExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
	}

	// 处理post请求
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		List list = (List)request.getAttribute("list");
		String name = request.getParameter("name");
		String staffStatus = request.getParameter("staffStatus");
		String role = request.getParameter("role");
		String departmentId = request.getParameter("departmentId");
		
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		Label label = null;
		
		// 创建Excel表
		try {
			
		//workbook = Workbook.createWorkbook(new File("e:/output.xls"));
			Date date = new Date();
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			String filename = format.format(date.getTime())+"-"+Math.round((Math.random()*1000));
			OutputStream os=response.getOutputStream();//获取输出流
   			response.reset();
   			response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");//设定输出文件头
   			response.setContentType("application/msexcel");//设定输出类型
		    workbook = Workbook.createWorkbook(os);
		
		// 创建Excel表中的sheet
		   sheet = workbook.createSheet("staffs", 0);
		   ArrayList labels = new ArrayList();
					
		   labels.add("街道办ID");labels.add("居委会ID");labels.add("房间ID");labels.add("姓名");
		   labels.add("性别(男1,女0)");labels.add("身份证");labels.add("出生日期");labels.add("户籍所在地");
		   labels.add("特殊属性");labels.add("编号");labels.add("曾用名");labels.add("签发机关");
		   labels.add("签发日期");labels.add("出生地");labels.add("身高");labels.add("血型");
		   labels.add("健康状况");labels.add("户籍类型");labels.add("民族");
		   labels.add("藉贯");labels.add("住址");labels.add("联系电话");labels.add("其他住址");
		   labels.add("文化程度");labels.add("婚姻状况");labels.add("兵役状况");labels.add("宗教信仰");
		   labels.add("政治面貌");labels.add("职业");labels.add("服务住所");labels.add("备注");
		   labels.add("迁入日期");labels.add("原住址");labels.add("迁入类别");labels.add("迁入原因");
		   labels.add("迁出日期");labels.add("迁出住址");labels.add("迁出类别");labels.add("迁出原因");
		   labels.add("死亡日期");labels.add("死亡原因");labels.add("死亡状态(1死亡)");
		   labels.add("注销日期");labels.add("注销原因");labels.add("注销状态(1注销)");labels.add("登记时间");labels.add("登记人");
		   
////		// 添加标题
		for (int i = 0; i < labels.size(); i++) {
		String colName = labels.get(i).toString();
		
		label = new Label(i, 0, colName);
		// log.debug("标题:"+i+"---"+row +"---"+colName);
		sheet.addCell(label);
		}
		System.out.println("写入标题成功");
		//WritableCellFormat columnFormat = setFormat();
		for (int i = 0; i < list.size(); i++){
			List lista =(List) list.get(i);
		for (int j = 0; j< lista.size(); j++) {
			 label = new Label(j, i+1,lista.get(j).toString());
		     sheet.addCell(label);
		}
		}
		System.out.println("写入内容成功");
		
		// 关闭文件
		workbook.write();
		workbook.close();
		System.out.println("数据成功写入Excel");
		flag = true;
		} catch (Exception e) {
		System.out.println(e.getMessage());
		
		} finally {
		try {
		workbook.close();
		
		} catch (Exception e) {
		}
		}
		
	}

	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void destroy() {
	}

}
