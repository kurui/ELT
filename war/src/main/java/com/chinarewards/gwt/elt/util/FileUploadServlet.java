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
		
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload();
		try {
			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					System.out
							.println("==================upload=================Form field "
									+ name
									+ " with value "
									+ Streams.asString(stream) + " detected.");
				} else {
					System.out
							.println("==================upload=================File field "
									+ name
									+ " with file name "
									+ item.getName()
									+ " detected.="
									+ item.getFieldName());
					// Process the input stream

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
