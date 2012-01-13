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

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String responseMessage = "";

		ServletFileUpload upload = new ServletFileUpload();
		try {
			FileItemIterator iter = upload.getItemIterator(request);

			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					responseMessage = "Form field " + name + " with value "
							+ Streams.asString(stream) + " detected.";
				} else {
					responseMessage = "File field " + name + " with file name "
							+ item.getName() + " detected.="
							+ item.getFieldName();

					BufferedInputStream inputStream = new BufferedInputStream(
							stream);// 获得输入流
					String uploadPath = request.getRealPath("/upload")
							+ File.separator;

					// System.out.println("uploadPath:" + uploadPath);
					String itemName = item.getName();
					itemName = itemName.substring(itemName.indexOf("."),
							itemName.length());
					String fileName = DateTool.getDateString("yyyyMMddHHmmss")
							+ itemName;

					BufferedOutputStream outputStream = new BufferedOutputStream(
							new FileOutputStream(
									new File(uploadPath + fileName)));// 获得文件输出流
					Streams.copy(inputStream, outputStream, true); // 开始把文件写到你指定的上传文件夹
					stream.close();

					responseMessage = "================print():" + fileName;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = "上传文件异常:" + e.getMessage();
		}
		System.out.println("responseMessage:" + responseMessage);
		response.getWriter().print(responseMessage);
	}

}
