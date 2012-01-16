package com.chinarewards.gwt.elt.util;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.chinarewards.elt.util.DateUtil;

/**
 * @author yanrui
 * 
 * */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		StringBuffer responseMessage = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		responseMessage.append("<root>");
		String info = "";
		ServletFileUpload upload = new ServletFileUpload();
		try {
			FileItemIterator iter = upload.getItemIterator(request);

			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					responseMessage.append("<result>").append("FAILED")
							.append("</result>");
					responseMessage.append("<info>");
					info = "Form field " + name + " with value "
							+ Streams.asString(stream) + " detected.";
					responseMessage.append(info).append("</info>");
				} else {
					BufferedInputStream inputStream = new BufferedInputStream(
							stream);// 获得输入流
					String uploadPath = request.getRealPath("/upload")
							+ File.separator;

					String itemName = item.getName();
					itemName = itemName.substring(itemName.indexOf("."),
							itemName.length());
					String fileName = DateUtil.getDateString("yyyyMMddHHmmss")
							+ itemName;

					BufferedOutputStream outputStream = new BufferedOutputStream(
							new FileOutputStream(
									new File(uploadPath + fileName)));// 获得文件输出流
					Streams.copy(inputStream, outputStream, true); // 开始把文件写到你指定的上传文件夹
					stream.close();

					responseMessage.append("<result>").append("SUCCESS")
							.append("</result>");
					responseMessage.append("<info>");
					responseMessage.append(fileName);
					responseMessage.append(info).append("</info>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage.append("<result>").append("FAILED")
					.append("</result>");
			responseMessage.append("<info>");
			responseMessage.append("上传文件异常:" + e.getMessage());
			responseMessage.append(info).append("</info>");
		}
		
		responseMessage.append("</root>");
		
		System.out.println(responseMessage);
		response.getWriter().println(responseMessage);
	}
}
