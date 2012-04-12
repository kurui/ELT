package com.chinarewards.gwt.elt.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
* <li>把数据导入到Excel公用类</li>
* </br> This is about <code>ExcelUtil</code>
* 
* @author hjy273
* @version 1.0
* @date Sep 6, 2008 9:52:52 PM
*/
public class ExcelUtil {

		private static Log log = LogFactory.getLog(ExcelUtil.class);
		
		private ExcelUtil(){}
		private static ExcelUtil bo = new ExcelUtil();
		public static ExcelUtil getInstance(){
			return bo;
		}
		
		public  boolean CreateExcel(List list ,HttpServletResponse response) {
		boolean flag = false;
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
		   sheet = workbook.createSheet("users", 0);
//		   WritableFont headerFont = new WritableFont(WritableFont.createFont("宋体"));
//			headerFont.setPointSize(12);
//			headerFont.setBoldStyle(WritableFont.BOLD);
//			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
//			headerFormat.setAlignment(Alignment.CENTRE);
//			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
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
		log.debug("写入标题成功");
		//WritableCellFormat columnFormat = setFormat();
		for (int i = 0; i < list.size(); i++){
			List lista =(List) list.get(i);
		for (int j = 0; j< lista.size(); j++) {
			 label = new Label(j, i+1,lista.get(j).toString());
		     sheet.addCell(label);
		}
		}
		log.info("写入内容成功");
		
		// 关闭文件
		workbook.write();
		workbook.close();
		log.info("数据成功写入Excel");
		flag = true;
		} catch (Exception e) {
		System.out.println(e.getMessage());
		
		} finally {
		try {
		workbook.close();
		} catch (Exception e) {
		}
		}
		return flag;
		}

//		public ArrayList getDataFromExcel(String path){
//			ArrayList<JwhUser> list = new ArrayList<JwhUser>();
//			try {
//				InputStream is = new FileInputStream(path);
//				Workbook rwb = Workbook.getWorkbook(is);
//				Sheet sheet = rwb.getSheet(0);
//				int rows = sheet.getRows();
//				for(int i=1;i<rows;i++){
//					String name = sheet.getCell(3, i).getContents();
//					if("".equals(name))
//						continue;
//					    JwhUser vo = new JwhUser();
//					    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
//						int sid = Integer.parseInt(sheet.getCell(0, i).getContents());
//						int bid = Integer.parseInt(sheet.getCell(1, i).getContents());
//						int roomid = Integer.parseInt(sheet.getCell(2, i).getContents());
//						int sex = Integer.parseInt(sheet.getCell(4, i).getContents());
//						String idnumber = sheet.getCell(5, i).getContents();
//						String birthday = sheet.getCell(6, i).getContents();
//						Date bdate = formatter.parse(birthday);
//							vo.setRoomId(roomid);
//							vo.setSid(sid);
//							vo.setBid(bid);
//							vo.setName(name);
//							vo.setIdnumber(idnumber);
//							vo.setSex((short)sex);
//							vo.setBirthday(birthday);	
//						String households = sheet.getCell(7, i).getContents();
//						 vo.setHouseholds(households);
//						String special = sheet.getCell(8, i).getContents();
//						 vo.setSpecial(special);
//						String bianhao = sheet.getCell(9, i).getContents();
//						 vo.setBianhao(bianhao);
//						String chengyongname = sheet.getCell(10, i).getContents();
//						 vo.setChengyongname(chengyongname);
//						String issueauthority = sheet.getCell(11, i).getContents();
//						  vo.setIssueauthority(issueauthority);
//						String issuedate = sheet.getCell(12, i).getContents();
//						if(issuedate!=null&& !issuedate.equals("")){
//						  Date issuedates = formatter.parse(issuedate);
//						  vo.setIssuedate(issuedate);
//						}
//						String hometown = sheet.getCell(13, i).getContents();
//						  vo.setHometown(hometown);
//						String height = sheet.getCell(14, i).getContents();
//						  vo.setHeight(height);
//						String bloodtype = sheet.getCell(15, i).getContents();
//						  vo.setBloodtype(bloodtype);
//						String health = sheet.getCell(16, i).getContents();
//						  vo.setHealth(health);
//						String householdstype = sheet.getCell(17, i).getContents();
//						  vo.setHouseholdstype(householdstype);
//						String national = sheet.getCell(18, i).getContents();
//						  vo.setNational(national);
//						String byconsistent = sheet.getCell(19, i).getContents();
//						  vo.setByconsistent(byconsistent);
//						String address = sheet.getCell(20, i).getContents();
//						  vo.setAddress(address);
//						String phone = sheet.getCell(21, i).getContents();
//						  vo.setPhone(phone);
//						String otheraddress = sheet.getCell(22, i).getContents();
//						  vo.setOtheraddress(otheraddress);
//						String education = sheet.getCell(23, i).getContents();
//						  vo.setEducation(education);
//						String maritalstatus = sheet.getCell(24, i).getContents();
//						  vo.setMaritalstatus(maritalstatus);
//						String veteranstatus = sheet.getCell(25, i).getContents();
//						  vo.setVeteranstatus(veteranstatus);
//						String religion = sheet.getCell(26, i).getContents();
//						  vo.setReligion(religion);
//						String politics = sheet.getCell(27, i).getContents();
//						  vo.setPolitics(politics);
//						String professional = sheet.getCell(28, i).getContents();
//						  vo.setProfessional(professional);
//						String fuwuplace = sheet.getCell(29, i).getContents();
//						  vo.setFuwuplace(fuwuplace);
//						String remarks = sheet.getCell(30, i).getContents();
//						  vo.setRemarks(remarks);
//						String qianrudate = sheet.getCell(31, i).getContents();
//						if(qianrudate!=null&& !qianrudate.equals("")){
//						  Date qianrudates = formatter.parse(qianrudate);
//						  vo.setQianchudate(qianrudate);
//						}
//						String yuanaddress = sheet.getCell(32, i).getContents();
//						  vo.setYuanaddress(yuanaddress);
//						String qianrucategory = sheet.getCell(33, i).getContents();
//						  vo.setQianrucategory(qianrucategory);
//						String qianrureason = sheet.getCell(34, i).getContents();
//						  vo.setQianrureason(qianrureason);
//						String qianchudate = sheet.getCell(35, i).getContents();
//						if(qianchudate!=null&& !qianchudate.equals("")){
//						  Date qianchudates = formatter.parse(qianchudate);
//						  vo.setQianchudate(qianchudate);
//						}
//						String qianchuaddress = sheet.getCell(36, i).getContents();
//						  vo.setQianchuaddress(qianchuaddress);
//						String qianchucategory = sheet.getCell(37, i).getContents();
//						  vo.setQianchucategory(qianchucategory);
//						String qianchureason = sheet.getCell(38, i).getContents();
//						  vo.setQianchureason(qianchureason);
//						String deathdate = sheet.getCell(39, i).getContents();
//						if(deathdate!=null&& !deathdate.equals("")){
//						  Date deathdates = formatter.parse(deathdate);
//						  vo.setDeathdate(deathdate);
//						}
//						String deathsituation = sheet.getCell(40, i).getContents();
//						  vo.setDeathsituation(deathsituation);
//						String deathstatus = sheet.getCell(41, i).getContents();
//						if(deathstatus==null||deathstatus.equals(""))
//							deathstatus = "0";
//						  vo.setDeathstatus(deathstatus);
//						String zhuxiaodate = sheet.getCell(42, i).getContents();
//						if(zhuxiaodate!=null&& !zhuxiaodate.equals("")){
//						  Date zhuxiaodates = formatter.parse(zhuxiaodate);
//						  vo.setZhuxiaodate(zhuxiaodate);
//						}
//						String zhuxiaoreason = sheet.getCell(43, i).getContents();
//						  vo.setZhuxiaoreason(zhuxiaoreason);
//						String zhuxiaostatus = sheet.getCell(44, i).getContents();
//						if(zhuxiaostatus==null||zhuxiaostatus.equals(""))
//						 zhuxiaostatus = "0";
//						  vo.setZhuxiaostatus(zhuxiaostatus);
//						String pubtime = sheet.getCell(45, i).getContents();
//						String pubmember = sheet.getCell(46, i).getContents();
//						  vo.setPubmember(pubmember);
//					   list.add(vo);
//					}
//				is.close();
//				rwb.close();
//								
//			} catch (Exception e) {
//				e.printStackTrace();
//			} 
//			
//			return list;
//		}
		private WritableCellFormat setFormat(){
			WritableFont columnFont = new WritableFont(WritableFont.createFont("宋体"));
			WritableCellFormat columnFormat = null;
			try {
				columnFont.setPointSize(11);
				columnFormat = new WritableCellFormat(columnFont);			
				columnFormat.setAlignment(Alignment.CENTRE);
				columnFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			} catch (WriteException e) {
				e.printStackTrace();
			}
			return columnFormat;
			
		}
}


