package com.chinarewards.gwt.elt.server.order;

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

import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.gift.search.GiftListVo;
import com.chinarewards.elt.model.order.search.OrderListVo;
import com.chinarewards.elt.model.order.search.OrderStatus;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.elt.service.staff.IStaffService;


import com.google.inject.Inject;

/**
 * @author lw
 * 
 * */
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	IStaffService staffService;
	@Inject
    OrderService orderService;
	public void init() throws ServletException {
	}

	// 处理post请求
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		String staffid = request.getParameter("staffid");
		String source = request.getParameter("source");
		OrderListVo orderVo = new OrderListVo();
		GiftListVo giftVo = new GiftListVo();
		if(name !=null)
			orderVo.setName(name);
		if(status !=null)
			orderVo.setStatus(OrderStatus.valueOf(status));
		
		if(source !=null)
			giftVo.setSource(source);
			
		orderVo.setGiftvo(giftVo);
		orderVo.setDeleted(0);
		UserContext context=new UserContext();
		List<UserRole> roles = staffService.findUserRoles(staffid);
		UserRole[] userRoles = roles.toArray(new UserRole[0]);
		context.setUserRoles(userRoles);
		context.setUserId(request.getParameter("userid"));
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		Label label = null;
		
		// 创建Excel表
		try {
			
		//workbook = Workbook.createWorkbook(new File("e:/output.xls"));
			Date date = new Date();
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String filename = "Orders";//format.format(date.getTime())+"-"+Math.round((Math.random()*1000));
			OutputStream os=response.getOutputStream();//获取输出流
   			response.reset();
   			response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");//设定输出文件头
   			
   			response.setContentType("application/msexcel");//设定输出类型
		    workbook = Workbook.createWorkbook(os);
		
		// 创建Excel表中的sheet
		   sheet = workbook.createSheet("staffs", 0);
		   sheet.setColumnView(0, 30);
		   sheet.setColumnView(3, 20);
		   sheet.setColumnView(4, 20);
		   ArrayList labels = new ArrayList();
		   labels.add("订单编号");labels.add("礼品名称");  labels.add("数量"); labels.add("积分");labels.add("兑换日期");labels.add("采购价格");	
		 
		 			   	   
////		// 添加标题
		for (int i = 0; i < labels.size(); i++) {
		String colName = labels.get(i).toString();
		
		label = new Label(i, 0, colName);
		// log.debug("标题:"+i+"---"+row +"---"+colName);
		sheet.addCell(label);
		}
		//System.out.println("写入标题成功");
	
		PageStore<OrderListVo> orderList = orderService.OrderList(context,orderVo );
		
		List<OrderListVo> orderlist = orderList.getResultList();
		List list = new ArrayList();
		if(orderlist.size()>0){
			for(int a=0;a<orderlist.size();a++){//加入要导出的字段
				List lista = new ArrayList();
				OrderListVo ordervo = orderlist.get(a);
				lista.add(ordervo.getOrderCode());
				lista.add(ordervo.getGiftvo().getName());
				lista.add(ordervo.getAmount());
				lista.add(ordervo.getIntegral());
				lista.add(ordervo.getExchangeDate());
				lista.add(ordervo.getGiftvo().getPrice());
			
				list.add(lista);
			}
		}
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
