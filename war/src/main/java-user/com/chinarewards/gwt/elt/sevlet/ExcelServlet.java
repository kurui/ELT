package com.chinarewards.gwt.elt.sevlet;

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
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.chinarewards.elt.common.LogicContext;
import com.chinarewards.elt.common.UserContextProvider;
import com.chinarewards.elt.model.staff.StaffSearchCriteria;
import com.chinarewards.elt.model.staff.StaffStatus;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.sevlet.ServiceLocatorUtil;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author yanrui
 * 
 * */
public class ExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
   IStaffService staffService;
	public void init() throws ServletException {
	}

	// 处理post请求
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String staffStatus = request.getParameter("staffStatus");
		String role = request.getParameter("role");
		String departmentId = request.getParameter("departmentId");
		StaffSearchCriteria criteria=new StaffSearchCriteria();
		if(name !=null)
		criteria.setStaffNameorNo(name);
		if(staffStatus !=null)
		criteria.setStaffStatus(StaffStatus.valueOf(staffStatus));
		if(role !=null&&!role.equals(""))
		criteria.setStaffRole(UserRole.valueOf(role));
		if(departmentId !=null)
		criteria.setDepartmentId(departmentId);
		if(request.getParameter("email") !=null)
		criteria.setStaffEmail(request.getParameter("email"));
		UserContext context=new UserContext();
		if(request.getParameter("corpid") !=null)
		context.setCorporationId(request.getParameter("corpid"));
		
		String userRole = request.getParameter("userRole");
		if(userRole !=null){
		
		List<UserRole> cli = new ArrayList<UserRole>();
		cli.add(UserRole.valueOf(userRole));
		UserRole[] userRoles = cli.toArray(new UserRole[0]);
		context.setUserRoles(userRoles);
		}
	
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		Label label = null;
		
		// 创建Excel表
		try {
			
		//workbook = Workbook.createWorkbook(new File("e:/output.xls"));
			Date date = new Date();
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String filename = "Staff";//format.format(date.getTime())+"-"+Math.round((Math.random()*1000));
			OutputStream os=response.getOutputStream();//获取输出流
   			response.reset();
   			response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");//设定输出文件头
   			
   			response.setContentType("application/msexcel");//设定输出类型
		    workbook = Workbook.createWorkbook(os);
		
		// 创建Excel表中的sheet
		   sheet = workbook.createSheet("staffs", 0);
		   sheet.setColumnView(2, 30);
		   sheet.setColumnView(3, 20);
		   sheet.setColumnView(4, 20);
		   ArrayList labels = new ArrayList();
		   labels.add("员工编号");labels.add("姓名");  labels.add("邮箱"); labels.add("电话");labels.add("生日");	
		   labels.add("部门"); labels.add("职位"); labels.add("直属领导");
		 			   	   
////		// 添加标题
		for (int i = 0; i < labels.size(); i++) {
		String colName = labels.get(i).toString();
		
		label = new Label(i, 0, colName);
		// log.debug("标题:"+i+"---"+row +"---"+colName);
		sheet.addCell(label);
		}
		//System.out.println("写入标题成功");
	if(request.getParameter("content").equals("true")){//是否要内容
		List list = staffService.queryStaffListExport(criteria, context);
		if(list.size()>0)
		for (int i = 0; i < list.size(); i++){
			List lista =(List) list.get(i);
			if(lista.size()>0)
			for (int j = 0; j< lista.size(); j++) {
				 if(lista.get(j)==null)
					  label = new Label(j, i+1,"");
				 else
					 label = new Label(j, i+1,lista.get(j).toString());
				
			     sheet.addCell(label);
			}
		 }
		}
		//System.out.println("写入内容成功");
		
		// 关闭文件
		workbook.write();
		workbook.close();
		//System.out.println("数据成功写入Excel");
		
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
