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
		System.out.println("=========fileupload servlet=============");

		response.setContentType("text/html;charset=utf-8");
		ServletFileUpload upload = new ServletFileUpload();
		try {
			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);

			System.out.println("file item size:" + upload.getSizeMax());

			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					System.out.println("Form field " + name + " with value "
							+ Streams.asString(stream) + " detected.");
				} else {
					System.out.println("File field " + name
							+ " with file name " + item.getName()
							+ " detected.=" + item.getFieldName());

					BufferedInputStream inputStream = new BufferedInputStream(
							stream);// 获得输入流
					String imagePath = request.getRealPath("/upload")
							+ File.separator;

					System.out.println("imagePath:" + imagePath);
					BufferedOutputStream outputStream = new BufferedOutputStream(
							new FileOutputStream(new File(imagePath
									+ item.getName())));// 获得文件输出流
					Streams.copy(inputStream, outputStream, true); // 开始把文件写到你指定的上传文件夹
				
				}
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
