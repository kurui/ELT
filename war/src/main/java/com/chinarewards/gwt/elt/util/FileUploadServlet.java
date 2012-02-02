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
import com.chinarewards.elt.util.DateUtil;

/**
 * @author yanrui
 * 
 * */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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

					String uploadPath = getUploadPath(request);
					if (uploadPath != null) {
						String itemName = item.getName();
						itemName = itemName.substring(itemName.indexOf("."),
								itemName.length());
						String fileName = DateUtil
								.getDateString("yyyyMMddHHmmss");
						fileName += new Random().nextInt(10000);
						fileName += itemName;
						BufferedOutputStream outputStream = new BufferedOutputStream(
								new FileOutputStream(new File(uploadPath
										+ File.separator + fileName)));// 获得文件输出流
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

	private static String getUploadPath(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		String uploadPath = null;
//		System.out.println("============realPath:" + realPath);
		int rootIndex = realPath.indexOf("jboss-5.1.0.GA");
		if (rootIndex < 0) {
			rootIndex = realPath.indexOf("war");
		}

		if (rootIndex < 0) {
			return null;
		} else {
			realPath = realPath.substring(0, rootIndex);

			uploadPath = realPath + "upload";
//			System.out.println("============uploadPath:" + uploadPath);
			File myFilePath = new File(uploadPath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
				System.out.println("创建图片上传文件夹：" + myFilePath);
			}
		}
		return uploadPath;
	}

	private void finishPrintResponseMsg(HttpServletResponse response,
			StringBuffer responseMessage) {
		try {
			responseMessage.append("</root>");
//			System.out.println(responseMessage);			
			response.getWriter().print(responseMessage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// private void printResponseMsg(HttpServletResponse response,
	// StringBuffer responseMessage) {
	// try {
	// response.getWriter().println(responseMessage);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public static void main(String[] args) {
		String realPath = "D:\\ServerRoot\\jboss-5.1.0.GA\\server";
		realPath = realPath.substring(0, realPath.indexOf("jboss-5.1.0.GA"));
		System.out.println(realPath);
	}
}
