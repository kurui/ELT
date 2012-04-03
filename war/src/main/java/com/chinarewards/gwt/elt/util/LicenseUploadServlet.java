package com.chinarewards.gwt.elt.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chinarewards.elt.service.license.ELTLicenseUtil;
import com.chinarewards.elt.util.DateUtil;

/**
 * @author yanrui
 * 
 * */
public class LicenseUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// text/html IE浏览器下无法解析
		response.setContentType("text/plain;charset=utf-8");

		StringBuffer responseMessage = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		responseMessage.append("<root>");

		StringBuffer errorMsg = new StringBuffer(responseMessage)
				.append("<result>").append("FAILED").append("</result>");

		String info = "";
		ServletFileUpload upload = new ServletFileUpload();

		FileItemIterator iter = null;
		try {
			iter = upload.getItemIterator(request);

			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					info = "Form field " + name + " with value "
							+ Streams.asString(stream) + " detected.";
					responseMessage = new StringBuffer(responseMessage)
							.append(errorMsg).append("<info>").append(info)
							.append("</info>");
					finishPrintResponseMsg(response, responseMessage);
					return;
				} else {
					BufferedInputStream inputStream = new BufferedInputStream(
							stream);// 获得输入流

					String uploadPath = ELTLicenseUtil.getCertPath();
					if (uploadPath != null) {
						String fileName = getOutputFileName(item);
						fileName="license.lic";
						String outputFilePath = getOutputFilePath(uploadPath,
								fileName);

						BufferedOutputStream outputStream = new BufferedOutputStream(
								new FileOutputStream(new File(outputFilePath)));// 获得文件输出流
						Streams.copy(inputStream, outputStream, true); // 开始把文件写到你指定的上传文件夹

						stream.close();

						responseMessage.append("<result>").append("SUCCESS")
								.append("</result>");
						responseMessage.append("<info>");
						responseMessage.append(fileName);
						responseMessage.append(info).append("</info>");
					} else {
						responseMessage = errorMsg.append("<info>")
								.append("未定义图片上传路径").append("</info>");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = errorMsg.append("<info>")
					.append("文件上传异常:" + e.getMessage()).append("</info>");
		}
		finishPrintResponseMsg(response, responseMessage);
	}

	private static String getOutputFilePath(String uploadPath, String fileName) {

		String outputFilePath = uploadPath + File.separator + fileName;
		return outputFilePath;

	}

	private static String getOutputFileName(FileItemStream item) {
		String itemName = item.getName();
		itemName = itemName.substring(itemName.lastIndexOf("."),
				itemName.length());
		String fileName = DateUtil.getDateString("yyyyMMddHHmmss");
		fileName += new Random().nextInt(10000);
		fileName += itemName;

		return fileName;
	}

	private void finishPrintResponseMsg(HttpServletResponse response,
			StringBuffer responseMessage) {
		try {
			responseMessage.append("</root>");
			System.out.println(responseMessage);
			response.getWriter().print(responseMessage);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
