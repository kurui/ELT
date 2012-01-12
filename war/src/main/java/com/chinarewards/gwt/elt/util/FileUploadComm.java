package com.chinarewards.gwt.elt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

public class FileUploadComm {
	/**
	 * 上传图片方法
	 * 
	 * @param dir
	 * @param formFile
	 * @return
	 * @throws Exception
	 */

	public static String upload(String dir, File file, String uploadFileName)
			throws Exception {
		Date date = new Date();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyyMMddhhmmss");
		// 取欲上传的文件的名字和长度
		// 将上传时间加入文件名
		int i = uploadFileName.indexOf(".");
		String name = String.valueOf(format.format(date.getTime()));
		String type = uploadFileName.substring(i + 1);
		uploadFileName = name + "." + type;
		File uploadFile = new File(dir); // 创建把上传数据写到目标文件的对象
		if (!uploadFile.exists() || uploadFile == null) { // 判断指定路径是否存在，不存在则创建路径
			uploadFile.mkdirs();
		}
		String path = uploadFile.getPath() + "/" + uploadFileName;
		FileOutputStream fos = new FileOutputStream(path);
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];// 设置缓充
		int len = 0;
		while ((len = fis.read(buf)) != -1) {
			fos.write(buf, 0, len);
		}
		if (fis != null)
			fis.close();
		if (fos != null)
			fos.close();

		return uploadFileName;
	}
}
