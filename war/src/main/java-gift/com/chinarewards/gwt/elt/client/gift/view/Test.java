package com.chinarewards.gwt.elt.client.gift.view;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test implements EntryPoint {

	public void onModuleLoad() {
		// 选择文件上传的浏览按钮
		final FileUpload fileUpload = new FileUpload();
		fileUpload.setName("uploadFormElement");
		// 创建表单Panel，提交HTML表格，添加fileUpload
		final FormPanel formPanel = new FormPanel();
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setAction(GWT.getModuleBaseURL() + "fileupload");
		formPanel.setWidget(fileUpload);
		RootPanel.get("nameFieldContainer").add(formPanel);
		// 文件上传按钮
		final Button btnUpload = new Button("Upload");
		RootPanel.get("sendButtonContainer").add(btnUpload);
		// 单击按钮提交servlet
		btnUpload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});
		formPanel.addSubmitHandler(new SubmitHandler() {

			@Override
			public void onSubmit(SubmitEvent event) {
				if (fileUpload.getFilename().length() == 0) {
					// Window.alert("必须选择一个文件");
					event.cancel();
				} else if (!fileUpload.getFilename().endsWith(".jpg")
						&& !fileUpload.getFilename().endsWith(".gif")) {
					System.err.println("error in format");
					event.cancel();
				}

			}

		});
		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// TODO Auto-generated method stub
				System.out.println(" ==== onSubmitComplete ====");
				System.out.println("dddd=" + event.getResults());

			}

		});

	}
}
