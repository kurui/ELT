package com.chinarewards.gwt.elt.client.staffList.presenter;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.IUploader.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.BaseDialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staff.model.ImportStaffAjaxRequestVo;
import com.chinarewards.gwt.elt.client.staff.model.ImportStaffAjaxResponseVo;
import com.chinarewards.gwt.elt.client.staff.request.ImportStaffAjaxRequest;
import com.chinarewards.gwt.elt.client.staff.request.ImportStaffAjaxResponse;
import com.chinarewards.gwt.elt.client.staff.ui.ImportStaffSingleUploader;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmHandler;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.widgetideas.client.ProgressBar;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;

public class ImportStaffPresenterImpl extends
		BaseDialogPresenter<ImportStaffPresenter.ImportStaffDisplay> implements
		ImportStaffPresenter {

	private final static int TITLE_INSTRUCTION_DISPLAY_ROW = 6;
	private final static int TITLE_INSTRUCTION_DISPLAY_COL = 5;
	private final static int CONTENT_INSTRUCTION_DISPLAY_COL = 5;
	private final static int CONTENT_INSTRUCTION_DISPLAY_ROW = 11;

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	boolean isHavingTitle = true;
	int rowNum = 0;
	String batchId;
	String uploadedUrl;
	List<List<String>> xlsContent = new ArrayList<List<String>>();
	List<List<Long>> errorCodes = new ArrayList<List<Long>>();

	List<ListBox> contentInstructionListBoxes = new ArrayList<ListBox>();
	List<ListBox> formaterListBoxes = new ArrayList<ListBox>();
	List<HTML> formaterWaningLabels = new ArrayList<HTML>();

	String[] correctHeaderSorting = { "jobNo", "name", "email",
			"phone", "dob"};

	@Inject
	public ImportStaffPresenterImpl(EventBus eventBus,
			ImportStaffDisplay display, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager, Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
	}
	
	private ChangeHandler staffColumnListBoxChangeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			refreshContentInstructions();
		}
	};

	private ChangeHandler formaterListBoxChangeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			refreshContentInstructions();
		}
	};

	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		@Override
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				if (uploader.getServerResponse() != null) {
					String body = uploader.getServerResponse();
					Document responseDoc = XMLParser.parse(body);
					String message = Utils.getXmlNodeValue(responseDoc,
							"message");
					Document messageDoc = XMLParser.parse(message);
					uploadedUrl = uploader.fileUrl();
					GWT.log("uploadedUrl = " + uploadedUrl);
					// instruction processing
					UploadedInfo info = uploader.getServerInfo();
					parseInstructionResponse(messageDoc, info);
				}
			} else {
				display.getResultMessage().setText("档案上传失败！");
			}
		}

		private void parseInstructionResponse(Document messageDoc,
				UploadedInfo info) {
			String errorMsg = Utils.getXmlNodeValue(messageDoc, "error-msg");
			if (StringUtil.isEmpty(errorMsg)) {
				try {
					String count = Utils.getXmlNodeValue(messageDoc,
							"raw-count");
					rowNum = Integer.parseInt(count);
				} catch (Exception e) {
				}
				batchId = Utils.getXmlNodeValue(messageDoc, "batch-id");
				GWT.log("batchId = " + batchId);
				xlsContent = parseCommonInstructionResponse(messageDoc, rowNum);
				GWT.log("xlsContent is "
						+ (xlsContent == null ? "null" : "not null")
						+ "; size = "
						+ (xlsContent == null ? 0 : xlsContent.size()));
				win.alert("档案成功上传！");
				display.getResultMessage().setText(
						"档案 " + info.name + " (" + info.size + "bytes) "
								+ "已经上传。");
				refreshPanelStep2();
				display.showPanelStep2();
			} else {
				display.getResultMessage().setText(
						"档案 " + info.name + " (" + info.size + "bytes) "
								+ "上传失败！");
				win.alert(errorMsg);
			}
		}

		private List<List<String>> parseCommonInstructionResponse(
				Document messageDoc, int rowNum) {
			int row = CONTENT_INSTRUCTION_DISPLAY_ROW > rowNum ? rowNum
					: CONTENT_INSTRUCTION_DISPLAY_ROW;
			int col = CONTENT_INSTRUCTION_DISPLAY_COL;
			GWT.log("row:" + row + "col:" + col + "");
			List<List<String>> result = new ArrayList<List<String>>();
			for (int i = 0; i < row; i++) {
				List<String> staffCells = new ArrayList<String>();
				for (int j = 0; j < col; j++) {
					String cell = Utils.getXmlNodeValue(messageDoc, "raw-" + i
							+ "-" + j);
					staffCells.add(cell == null ? "" : cell);
				}
				result.add(staffCells);
			}
			GWT.log("result.size:" + result.size());
			return result;
		}
	};



	public void bind() {

		display.getStaffFileUploader().addOnFinishUploadHandler(
				onFinishUploaderHandler);
		/** 取消 **/
		registerHandler(display.getCancelImport().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						closeDialog();

					}

				}));
		/****/
		registerHandler(display.getDownloadTemplete().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						doExport("template", "上传模板");

					}

				}));
		/** 回退第一步panel **/
		registerHandler(display.getBackStep1Button().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {

//						dispatch.execute(
//								new RemoveStaffRawBatchRequest(batchId),
//								new AsyncCallback<RemoveStaffRawBatchResponse>() {
//
//									@Override
//									public void onFailure(Throwable caught) {
//										// previous import batch could not be
//										// clear
//										// would be clear on a scheduled job
//									}
//
//									@Override
//									public void onSuccess(
//											RemoveStaffRawBatchResponse result) {
//										display.getContentPreviewPanel()
//												.clear();
//										display.getTitlePreviewPanel().clear();
//										display.getImportDetails().setHTML("");
//										display.getImportFailure().setHTML("");
//										display.getImportBatchNo().setText("");
//										display.getImportStaffBatchNo()
//												.setText("");
//										display.getPretreatmentResult()
//												.setHTML("");
//										display.getResultMessage().setText("");
//										display.getSuccessImportMessage()
//												.setText("");
//										batchId = null;
//										xlsContent = new ArrayList<List<String>>();
//										errorCodes = new ArrayList<List<Long>>();
//									}
//
//								});
						display.getContentPreviewPanel().clear();
						display.getTitlePreviewPanel().clear();
						display.getImportDetails().setHTML("");
						display.getImportFailure().setHTML("");
						display.getImportBatchNo().setText("");
						display.getImportStaffBatchNo().setText("");
						display.getPretreatmentResult().setHTML("");
						display.getResultMessage().setText("");
						display.getSuccessImportMessage().setText("");
						batchId = null;
						xlsContent = new ArrayList<List<String>>();
						errorCodes = new ArrayList<List<Long>>();
						display.showPanelStep1();
					}

				}));

		/** 显示第二步panel **/
		registerHandler(display.getGoStep2Button().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						if ("".equals(display.getStaffFileUploader()
								.getFileName())
								|| (!display.getStaffFileUploader()
										.getFileName().toLowerCase().trim()
										.endsWith(".xls") && !display
										.getStaffFileUploader().getFileName()
										.toLowerCase().trim().endsWith(".xlsx"))) {
							win.alert("请选中一个符合标准的EXCEL文件上传！");
							return;
						}
						display.getStaffFileUploader().setServletPath(
								ImportStaffSingleUploader.SERVLET_PATH+"?nowUserId="+sessionManager.getSession().getToken()+"&corporationId="+sessionManager.getSession().getCorporationId());
						String confirmMessage = "确定开始上传文档吗?";
						if (rowNum > 0) {
							confirmMessage = "上次上传的文档将被覆盖，确定开始上传文档吗?";
						}
						win.confirm("员工文档上传", confirmMessage,
								new ConfirmHandler() {
									@Override
									public void confirm() {
										display.getResultMessage().setText(
												"正在处理中，请稍后……");
										display.getStaffFileUploader().submit();
									}
								});
					}
				}));

		/**
		 * 点击有标题列单选框
		 */
		registerHandler(display.getHasTitleValue().addValueChangeHandler(
				new ValueChangeHandler<Boolean>() {
					@Override
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						if (event.getValue()) {
							isHavingTitle = true;
							refreshPanelStep2();
						}
					}
				}));

		/**
		 * 点击有标题列单选框
		 */
		registerHandler(display.getNoHasTitleValue().addValueChangeHandler(
				new ValueChangeHandler<Boolean>() {
					@Override
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						if (event.getValue()) {
							isHavingTitle = false;
							refreshPanelStep2();
						}
					}
				}));

		/** 回退第二步panel **/
		registerHandler(display.getBackStep2Button().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						refreshPanelStep2();
						display.showPanelStep2();
					}
				}));

		/** 显示第三步panel **/
		registerHandler(display.getGoStep3Button().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						if (uploadedFileTitleFormatInvalid()) {
							// there are some wrong instruction on the page
							win.alert("导入文件格式不正确，请修改后重新上传！");
							return;
						}

						refreshPanelStep3();
						display.showPanelStep3();

					}
				}));

		/** 回退第三步panel **/
		registerHandler(display.getBackStep3Button().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						refreshPanelStep3();
						display.showPanelStep3();
					}
				}));

		/** 显示第四步(预处理结果)panel **/
		registerHandler(display.getGoStep4Button().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						if (uploadedFileContentFormatInvalid()) {
							// there are some wrong instruction on the page
							win.alert("导入文件格式不正确，请修改后重新上传！");
							return;
						}
						doPretreatment();
						display.showPanelStep4();
					}

				}));

		/** 导入预览报告button **/
		registerHandler(display.getPretreatmentReportButton().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {

						doExport("preview", "导入预览报告");

					}
				}));

		/** 导入button **/
		registerHandler(display.getImportStaffButton().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {

						doFinalImport();
						display.showPanelStep5();
						refreshPanelStep5();

					}
				}));

		/** 下载失败纪录button **/
		registerHandler(display.getFinalReportButton().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {

						doExport("fatal", "下载失败纪录");

					}
				}));

		/** 导入成功button **/
		registerHandler(display.getCloseButton().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						closeDialog();

					}
				}));

	}

	@Override
	public void unbind() {
		super.unbind();

	}

	private boolean uploadedFileTitleFormatInvalid() {
		if (xlsContent == null || xlsContent.size() == 0) {
			GWT.log("xls content is empty!");
			return true;
		}
		return false;
	}

	private boolean uploadedFileContentFormatInvalid() {
		if (xlsContent.get(0).size() != TITLE_INSTRUCTION_DISPLAY_COL) {
			GWT.log("xls column size is not correct!");
			return true;
		}
		if (formaterWaningLabels == null || formaterWaningLabels.size() == 0) {
			GWT.log("formater warning label is unavialable!");
			return true;
		}
		for (int i = 0; i < formaterWaningLabels.size(); i++) {
			if (formaterWaningLabels.get(i).isVisible()) {
				GWT.log("formater contains error!");
				return true;
			}
		}
		return false;
	}

	private void refreshPanelStep2() {
		display.getHasTitleValue().setValue(isHavingTitle);
		display.getNoHasTitleValue().setValue(!isHavingTitle);
		int recordNum = rowNum;
		if (isHavingTitle) {
			recordNum = rowNum - 1;
		}
		display.getTotalStaff().setHTML("这个档案共有<b>" + recordNum + "</b>条记录:");

		display.getTitlePreviewPanel().clear();

		if (getInstructionStaffNum() > 0) {
			int displayRows = TITLE_INSTRUCTION_DISPLAY_ROW > getInstructionStaffNum() ? getInstructionStaffNum()
					: TITLE_INSTRUCTION_DISPLAY_ROW;
			int displayColumns = TITLE_INSTRUCTION_DISPLAY_COL > xlsContent
					.get(0).size() ? xlsContent.get(0).size()
					: TITLE_INSTRUCTION_DISPLAY_COL;

			// Create a table to layout the form options
			FlexTable layout = new FlexTable();
			layout.setCellSpacing(0);
			layout.setBorderWidth(1);

			for (int i = 0; i < displayRows; i++) {
				List<String> titleContentRow = xlsContent.get(i);
				for (int j = 0; j < titleContentRow.size(); j++) {
					if (j < displayColumns) {
						// Add cell content here
						if (titleContentRow.get(j) == null
								|| titleContentRow.get(j).trim().equals("")) {
							layout.setHTML(i, j, "&nbsp;");
						} else {
							layout.setText(i, j, titleContentRow.get(j));
						}
					}
				}
			}

			display.getTitlePreviewPanel().setWidget(layout);
		}
	}

	private void refreshPanelStep3() {
		display.getContentPreviewPanel().clear();

		if (getInstructionStaffNum() > 0) {
			// Create a table to layout the form options
			FlexTable layoutTitle = new FlexTable();
			layoutTitle.setHTML(0, 0, "字段");
			layoutTitle.setHTML(0, 1, "用途");
			layoutTitle.setHTML(0, 2, "格式");
			layoutTitle.setHTML(0, 3, "内容");
			layoutTitle.setCellSpacing(1);
			FlexTable layout = new FlexTable();

			layout.setCellSpacing(1);

			layout.setText(0, 0, "标题");
			layout.setText(0, 1, "用途");
			layout.setText(0, 2, "格式");
			layout.setText(0, 3, "内容");

			// title column
			List<String> titleRow = xlsContent.get(0);
			if (isHavingTitle) {
				for (int j = 0; j < titleRow.size(); j++) {
					if (titleRow.get(j) == null
							|| titleRow.get(j).trim().equals("")) {
						layout.setHTML(j + 1, 0, "&nbsp;");
					} else {
						layout.setText(j + 1, 0, titleRow.get(j));
					}
				}
			} else {
				for (int j = 0; j < titleRow.size(); j++) {
					layout.setText(j + 1, 0, "第" + (j + 1) + "列");
				}
			}

			// instruction column
			if (contentInstructionListBoxes == null
					|| contentInstructionListBoxes.size() != xlsContent.get(0)
							.size()) {
				contentInstructionListBoxes = new ArrayList<ListBox>();
				for (int j = 0; j < xlsContent.get(0).size(); j++) {
					contentInstructionListBoxes.add(getStaffColumnListBox());
					contentInstructionListBoxes.get(j).setItemSelected(j, true);
					layout.setWidget(j + 1, 1,
							contentInstructionListBoxes.get(j));
				}
			} else {
				for (int j = 0; j < xlsContent.get(0).size(); j++) {
					contentInstructionListBoxes.get(j).setItemSelected(j, true);
					layout.setWidget(j + 1, 1,
							contentInstructionListBoxes.get(j));
				}
			}

			// content instruction column
			List<ListBox> staffContentListBoxes = getStaffContentListBox();
			for (int j = 0; j < staffContentListBoxes.size(); j++) {
				layout.setWidget(j + 1, 3, staffContentListBoxes.get(j));
			}

			// formater column
			if (formaterListBoxes == null
					|| formaterListBoxes.size() != xlsContent.get(0).size()) {
				formaterListBoxes = new ArrayList<ListBox>();
				for (int j = 0; j < xlsContent.get(0).size(); j++) {
					formaterListBoxes.add(getFormaterListBox());
					layout.setWidget(j + 1, 2, formaterListBoxes.get(j));
				}
			} else {
				for (int j = 0; j < xlsContent.get(0).size(); j++) {
					layout.setWidget(j + 1, 2, formaterListBoxes.get(j));
				}
			}

			// formater warning column
			if (formaterWaningLabels == null
					|| formaterWaningLabels.size() != xlsContent.get(0).size()) {
				formaterWaningLabels = new ArrayList<HTML>();
				for (int j = 0; j < xlsContent.get(0).size(); j++) {
					formaterWaningLabels.add(getFormaterWarningLabel());
					layout.setWidget(j + 1, 4, formaterWaningLabels.get(j));
				}
			} else {
				for (int j = 0; j < xlsContent.get(0).size(); j++) {
					layout.setWidget(j + 1, 4, formaterWaningLabels.get(j));
				}
			}
			// display.getContentPreviewPanel().add(layoutTitle);
			display.getContentPreviewPanel().add(layout);

			refreshContentInstructions();
		}
	}

	private void refreshPanelStep4(ImportStaffAjaxResponseVo vo) {
		int totalStaff = 0;
//		int totalStaffFailed = 0;
	//	int totalStaffWarn = 0;
		List<String> warnIssues = new ArrayList<String>();
		Map<String, Long> warnIssueCounts = new HashMap<String, Long>();
		List<String> fatalIssues = new ArrayList<String>();
		Map<String, Long> fatalIssueCounts = new HashMap<String, Long>();
		GWT.log("vo.getImportStaffRawCode().size() = "
				+ vo.getImportStaffRawCode().size());
		GWT.log("vo.getImportStaffRawCode().get(0).size() = "
				+ vo.getImportStaffRawCode().get(0).size());
		for (List<Long> rawCode : vo.getImportStaffRawCode()) {
			totalStaff++;
			boolean isFatalStaffRaw = false;
			boolean isWarnStaffRaw = false;
			for (Long code : rawCode) {
				if ("严重问题".equals(vo.getAllImportStaffCodeTypes().get(code))) {
					if (!isFatalStaffRaw) {
//						totalStaffFailed++;
						isFatalStaffRaw = true;
					}
					if (!fatalIssues.contains(vo.getAllImportStaffCodeInfos()
							.get(code))) {
						fatalIssues.add(vo.getAllImportStaffCodeInfos().get(
								code));
					}
					Long count = fatalIssueCounts.get(vo
							.getAllImportStaffCodeInfos().get(code));
					GWT.log("fatal count = " + count + " for "
							+ vo.getAllImportStaffCodeInfos().get(code));
					if (count == null) {
						count = new Long(0);
					}
					fatalIssueCounts.put(
							vo.getAllImportStaffCodeInfos().get(code),
							count + 1);
				} else if ("警告".equals(vo.getAllImportStaffCodeTypes()
						.get(code))) {
					if (!isWarnStaffRaw) {
					//	totalStaffWarn++;
						isWarnStaffRaw = true;
					}
					if (!warnIssues.contains(vo.getAllImportStaffCodeInfos()
							.get(code))) {
						warnIssues.add(vo.getAllImportStaffCodeInfos()
								.get(code));
					}
					Long count = warnIssueCounts.get(vo
							.getAllImportStaffCodeInfos().get(code));
					if (count == null) {
						count = new Long(0);
					}
					warnIssueCounts.put(
							vo.getAllImportStaffCodeInfos().get(code),
							count + 1);
				}
			}
		}
		String result = "";
		result += "<br/>你已经准备好导入员工资料<br/><br/>";
		result += "<b>" + totalStaff + "</b>笔资料<br/><br/>";
//		result += "<span style='color:red'>" + totalStaffFailed
//				+ "</span>笔资料有下列严重问题, 将被忽略<br/><br/>";
//		for (String fatalIssue : fatalIssues) {
//			result += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-<span style='color:black'>"
//					+ fatalIssueCounts.get(fatalIssue)
//					+ "</span>笔资料"
//					+ fatalIssue + "<br/>";
//		}
//		result += "<br/><span style='color:black'>"
//				+ (totalStaff - totalStaffFailed) + "</span>>笔资料中:<br/>";
//		for (String warnIssue : warnIssues) {
//			result += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-<span style='color:black'>"
//					+ warnIssueCounts.get(warnIssue)
//					+ "</span>>笔资料"
//					+ warnIssue
//					+ "<br/>";
//		}

		result += "<br/><b>预计成功导入员工数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:blue'><b>"
				+ vo.getEstimateSuccessNum() + "</b></span><br/>";

		//result += "<br/>" + vo.getEstimateSuccessDeptNum() + "个部门将被创建<br/>";

//		result += "<br/>共有"
//				+ (vo.getEstNewAssignCardNum() + vo.getEstNewAutoCardNum() + vo
//						.getEstOldAssignCardNum()) + "个卡号将被指定，其中"
//				+ vo.getEstNewAutoCardNum() + "个新卡号将会自动分配，其中"
//				+ vo.getEstNewAssignCardNum() + "个新卡号将会手动分配。<br/>";
		// + vo.getEstNewAssignCardNum() + "个新卡号将会手动分配，其中"
		// + vo.getEstOldAssignCardNum() + "个旧卡号将会手动分配。<br/>";

		display.getPretreatmentResult().setHTML(result);

//		display.getImportStaffBatchNo()
//				.setText("导入编号：" + vo.getImportBatchNo());
	}

	ProgressBar importProgressBar;
	Timer progressTimer = null;
	double currentProgress = 0.0;

	private void refreshPanelStep5() {
		double maxProgress = isHavingTitle ? rowNum - 1 : rowNum;
		importProgressBar = new ProgressBar(0.0, maxProgress);
		importProgressBar.setWidth("600");
		currentProgress = 0.0;
		GWT.log("importProgressBar getProgress = "
				+ importProgressBar.getProgress());
		GWT.log("importProgressBar getMaxProgress = "
				+ importProgressBar.getMaxProgress());

		display.getPanelProgressBar().add(importProgressBar);
		progressTimer = new Timer() {
			public void run() {
				refreshCurrentProgress();
				GWT.log("after refreshCurrentProgress(), currentProgress = "
						+ currentProgress);
				if (currentProgress >= importProgressBar.getMaxProgress()) {
					importProgressBar.setProgress(importProgressBar
							.getMaxProgress());
					// wait panel step 6 display
					cancel();
					display.showPanelStep6();
				} else {
					importProgressBar.setProgress(currentProgress);
				}
				GWT.log("importProgressBar getProgress = "
						+ importProgressBar.getProgress());
				GWT.log("importProgressBar getMaxProgress = "
						+ importProgressBar.getMaxProgress());
			}
		};
		progressTimer.scheduleRepeating(500);
	}

	private void refreshCurrentProgress() {
		if (!GWT.isScript()) {
			currentProgress += 0.5;
		} else {
//			dispatch.execute(new ImportStaffProgressAjaxRequest(batchId),
//					new AsyncCallback<ImportStaffProgressAjaxResponse>() {
//						@Override
//						public void onFailure(Throwable e) {
//							currentProgress += 0.5;
//						}
//
//						@Override
//						public void onSuccess(
//								ImportStaffProgressAjaxResponse response) {
//							currentProgress = response.getProcessedNum();
//						}
//
//					});
		}
	}

	private Long toLong(Long p) {
		if (p != null)
			return p;
		return new Long(0);
	}

	private void refreshPanelStep6(ImportStaffAjaxResponseVo vo) {
		display.getImportBatchNo().setText("导入编号：" + vo.getImportBatchNo());

	//	long totalStaff = toLong(vo.getFinalFailedNum())+ toLong(vo.getFinalSuccessNum());
		int totalStaffFailed = 0;

//		List<String> fatalIssues = new ArrayList<String>();
//		Map<String, Long> fatalIssueCounts = new HashMap<String, Long>();
//		if (vo.getImportStaffRawCode() != null) {
//			for (List<Long> rawCode : vo.getImportStaffRawCode()) {
//				boolean isFatalStaffRaw = false;
//				for (Long code : rawCode) {
//					if ("严重问题"
//							.equals(vo.getAllImportStaffCodeTypes().get(code))) {
//						if (!isFatalStaffRaw) {
//							totalStaffFailed++;
//							isFatalStaffRaw = true;
//						}
//						if (!fatalIssues.contains(vo
//								.getAllImportStaffCodeInfos().get(code))) {
//							fatalIssues.add(vo.getAllImportStaffCodeInfos()
//									.get(code));
//						}
//						Long count = fatalIssueCounts.get(vo
//								.getAllImportStaffCodeInfos().get(code));
//						GWT.log("fatal count = " + count + " for "
//								+ vo.getAllImportStaffCodeInfos().get(code));
//						if (count == null) {
//							count = new Long(0);
//						}
//						fatalIssueCounts.put(vo.getAllImportStaffCodeInfos()
//								.get(code), count + 1);
//					}
//				}
//			}
//		}

		display.getSuccessImportMessage().setText(
				toLong(vo.getFinalSuccessNum()) + "笔员工资料已成功被导入");

		String fatalSummary = "";
//		for (String fatalIssue : fatalIssues) {
//			fatalSummary += "<span style='color:black'>"
//					+ fatalIssueCounts.get(fatalIssue) + "</span>笔资料"
//					+ fatalIssue + "<br/>";
//		}
		long unknownFatalNum = toLong(vo.getFinalFailedNum())
				- totalStaffFailed;
		if (unknownFatalNum > 0) {
			fatalSummary += "<span style='color:black'>" + unknownFatalNum
					+ "</span>笔资料由于系统或网络问题<br/>";
		}

		String detailSummary = "";
//		detailSummary += "<span style='color:black'>总共使用了"
//				+ (toLong(vo.getFinalNewAssignCardNum()) + toLong(vo
//						.getFinalNewAutoCardNum())) + "</span>个卡号：<br/>";
//		detailSummary += " - <span style='color:black'>其中"
//				+ toLong(vo.getFinalNewAutoCardNum()) + "</span>个为自动分配，<br/>";
//		detailSummary += " - <span style='color:black'>其中"
//				+ toLong(vo.getFinalNewAssignCardNum()) + "</span>个为手动分配，<br/>";
//		// detailSummary += " - <span style='color:black'>其中"
//		// + vo.getFinalOldAssignCardNum() + "</span>个为旧卡分配。<br/>";
//		detailSummary += "<br/><span style='color:black'>建立了"
//				+ toLong(vo.getFinalSuccessDeptNum()) + "</span>个新部门。<br/>";
		detailSummary += "<br/><span style='color:black'>共导入"
				+ toLong(vo.getFinalSuccessNum()) + "</span>个新员工。<br/><br/>";

		display.getImportDetails().setHTML(detailSummary);
		display.getImportFailure().setHTML(fatalSummary);

	}

	private void refreshContentInstructions() {
		for (int j = 0; j < xlsContent.get(0).size(); j++) {
			if (isValidHeaderSorting(j)) {
				if (isDateColumn(j)) {
					formaterListBoxes.get(j).setVisible(true);
					String format = formaterListBoxes.get(j).getValue(
							formaterListBoxes.get(j).getSelectedIndex());
					// GWT.log("format=" + format + "; j=" + j);
					if (isValidDateFormater(format, j)) {
						formaterWaningLabels.get(j).setVisible(false);
					} else {
						// invalid date format
						formaterWaningLabels.get(j).setVisible(true);
					}
				} else {
					formaterListBoxes.get(j).setVisible(false);
					formaterWaningLabels.get(j).setVisible(false);
				}
			} else {
				// invalid column sorting
				formaterWaningLabels.get(j).setVisible(true);
			}
		}
	}

	private boolean isValidHeaderSorting(int col) {
		String columnValue = contentInstructionListBoxes.get(col).getValue(
				contentInstructionListBoxes.get(col).getSelectedIndex());
		return correctHeaderSorting[col > correctHeaderSorting.length ? correctHeaderSorting.length
				: col].equals(columnValue);
	}

	private boolean isValidDateFormater(String format, int col) {
		for (int i = 0; i < getInstructionStaffNum(); i++) {
			if (i == 0 && isHavingTitle) {
				continue;
			}
			try { // parse the value in date column one by one
				GWT.log("isValidDateFormater - format=" + format + "; text="
						+ xlsContent.get(i).get(col));
				if (StringUtil.isEmpty(xlsContent.get(i).get(col))) {
					continue;
				}
				Date result = DateTimeFormat.getFormat(format).parseStrict(
						xlsContent.get(i).get(col));
				GWT.log("isValidDateFormater - result=" + result);
				if (format.contains("yyyy")) {
					// special case for example, use yyyy-MM-dd to match
					// 77-12-31
					// , could got result 0077-12-31 instead of 1977-12-31,
					// which
					// is correct
					Date tmp = DateTimeFormat.getFormat("yyyy-MM-dd")
							.parseStrict("0100-01-01");
					GWT.log("isValidDateFormater - got temp result=" + tmp);
					if (tmp.after(result)) {
						GWT.log("isValidDateFormater - result = false");
						return false;
					}
				}
			} catch (Exception e) {
				GWT.log("is invalid", e);
				return false;
			}
		}
		return true;
	}

	private boolean isDateColumn(int col) {
		String columnValue = contentInstructionListBoxes.get(col).getValue(
				contentInstructionListBoxes.get(col).getSelectedIndex());
		return "dob".equals(columnValue);
	}

	private ListBox getStaffColumnListBox() {
		ListBox listBox = new ListBox();
		listBox.addItem("员工编号", "jobNo");
		listBox.addItem("员工姓名", "name");
		listBox.addItem("邮箱", "email");
		listBox.addItem("电话", "phone");
		listBox.addItem("生日", "dob");

		listBox.addChangeHandler(staffColumnListBoxChangeHandler);
		listBox.setEnabled(false);
		return listBox;
	}

	private ListBox getFormaterListBox() {
		ListBox listBox = new ListBox();
		listBox.addItem("YYYY年MM月DD日", "yyyy年MM月dd日");
		listBox.addItem("YY年MM月DD日", "yy年MM月dd日");
		listBox.addItem("YYYY-MM-DD", "yyyy-MM-dd");
		listBox.addItem("YY-MM-DD", "yy-MM-dd");
		listBox.addItem("DD-MM-YYYY", "dd-MM-yyyy");
		listBox.addItem("DD-MM-YY", "dd-MM-yy");
		listBox.addItem("DD/MM/YYYY", "dd/MM/yyyy");
		listBox.addItem("DD/MM/YY", "dd/MM/yy");
		listBox.addItem("MM/DD/YYYY", "MM/dd/yyyy");
		listBox.addItem("MM/DD/YY", "MM/dd/yy");
		listBox.addItem("YYYY/MM/DD", "yyyy/MM/dd");
		listBox.addItem("YY/MM/DD", "yy/MM/dd");
		listBox.addChangeHandler(formaterListBoxChangeHandler);
		return listBox;
	}

	private HTML getFormaterWarningLabel() {
		HTML html = new HTML();
		html.setHTML("<span style='font-weight:bolder;font-color:red;font-size:12pt;' >&nbsp;！</span>");
		return html;
	}

	private int getInstructionStaffNum() {
		if (xlsContent == null)
			return 0;
		return CONTENT_INSTRUCTION_DISPLAY_ROW > xlsContent.size() ? xlsContent
				.size() : CONTENT_INSTRUCTION_DISPLAY_ROW;
	}

	private List<ListBox> getStaffContentListBox() {
		List<ListBox> list = new ArrayList<ListBox>();
		for (int i = 0; i < getInstructionStaffNum(); i++) {
			List<String> contentRow = xlsContent.get(i);
			for (int j = 0; j < contentRow.size(); j++) {
				if (i == 0) {
					list.add(new ListBox());
					if (isHavingTitle) {
						continue;
					}
				}
				ListBox box = list.get(j);
				box.addItem(" " + contentRow.get(j), String.valueOf(j));
				list.set(j, box);
			}
		}
		return list;
	}

	private void doPretreatment() {
		win.beginWait("导入数据分析中……");
//		if (!GWT.isScript()) {
//			Timer t = new Timer() {
//				int i = 0;
//				public void run() {
//					i ++;
//					if (i > 3) {
//						cancel();
//					}
//				}
//			};
//			t.scheduleRepeating(2000);
//			Window.alert("导入数据分析中");
//			List<ImportStaffRawVo> list = new ArrayList<ImportStaffRawVo>();
//			List<List<Long>> rawCodes = new ArrayList<List<Long>>();
//			Map<Long, String> codes = new HashMap<Long, String>();
//			Map<Long, String> types = new HashMap<Long, String>();
//			codes.put(new Long(0), "成功");
//			codes.put(new Long(1), "身份证不是15或18位");
//			codes.put(new Long(2), "入职日期在未来");
//			codes.put(new Long(3), "手机号为空");
//			codes.put(new Long(4), "企业卡号段不足分配");
//			types.put(new Long(0), "成功");
//			types.put(new Long(1), "警告");
//			types.put(new Long(2), "警告");
//			types.put(new Long(3), "严重错误");
//			types.put(new Long(4), "严重错误");
//			for (int i = 0; i < 500; i++) {
//				ImportStaffRawVo raw = new ImportStaffRawVo();
//				if (i >= 100) {
//					raw.setStaffNumber("1001" + i);
//				} else if (i >= 10) {
//					raw.setStaffNumber("10010" + i);
//				} else {
//					raw.setStaffNumber("100100" + i);
//				}
//				raw.setLastName("测");
//				raw.setFirstName("试" + i);
//				raw.setForeignLastName("elt");
//				raw.setForeignFirstName("test" + i);
//				raw.setEmailAddress("a" + i + "@b.c");
//				raw.setDateOfEmployment("2008-08-08");
//				raw.setDepartment("IT|Development");
//				raw.setDob("1988-08-08");
//				raw.setGender("男");
//				if (i >= 100) {
//					raw.setIdNo("321256198808081" + i);
//				} else if (i >= 10) {
//					raw.setIdNo("3212561988080810" + i);
//				} else {
//					raw.setIdNo("32125619880808100" + i);
//				}
//				raw.setLocation("tester location " + i);
//				raw.setMemberCardNumber("");
//				raw.setMinorityNationality("test minority nationality " + i);
//				if (i >= 100) {
//					raw.setMobileTelephoneNumber("13488885" + i);
//				} else if (i >= 10) {
//					raw.setMobileTelephoneNumber("134888850" + i);
//				} else {
//					raw.setMobileTelephoneNumber("1348888500" + i);
//				}
//				raw.setNativePlace("test native place " + i);
//				list.add(raw);
//				List<Long> rawCode = new ArrayList<Long>();
//				if (i % 5 == 0) {
//					rawCode.add(new Long(0));
//				} else if (i % 10 == 1) {
//					rawCode.add(new Long(1));
//				} else if (i % 10 == 2) {
//					rawCode.add(new Long(1));
//					rawCode.add(new Long(2));
//				} else if (i % 10 == 3) {
//					rawCode.add(new Long(1));
//					rawCode.add(new Long(2));
//					rawCode.add(new Long(3));
//				} else if (i % 10 == 4) {
//					rawCode.add(new Long(4));
//				}
//				rawCodes.add(rawCode);
//			}
//
//			ImportStaffAjaxResponseVo resp = new ImportStaffAjaxResponseVo();
//			resp.setStaffRawList(list);
//			resp.setAllImportStaffCodeInfos(codes);
//			resp.setAllImportStaffCodeTypes(types);
//			resp.setImportBatchNo(new Long(106));
//			resp.setId(batchId);
//			resp.setImportStaffRawCode(rawCodes);
//			resp.setEstimateSuccessDeptNum((long) 10);
//			resp.setEstimateSuccessNum((long) 500);
//			resp.setEstNewAssignCardNum((long) 100);
//			resp.setEstNewAutoCardNum((long) 100);
//			resp.setEstOldAssignCardNum((long) 100);
//			refreshPanelStep4(resp);
//			win.endWait();
//		} else {
			ImportStaffAjaxRequestVo request = new ImportStaffAjaxRequestVo();

			request.setAction("pretreatment");
			request.setId(batchId);
			request.setHavingTitle(isHavingTitle);

			String dobFormat = "";

			for (int j = 0; j < xlsContent.get(0).size(); j++) {
				if (formaterListBoxes.get(j).isVisible()) {
					// use correctHeaderSorting to obtain correct column
					// index because correctHeaderSorting must be ensure
					// in instruction steps
					if (correctHeaderSorting[j].equals("dob")) {
						dobFormat = formaterListBoxes.get(j).getValue(
								formaterListBoxes.get(j).getSelectedIndex());
					} 
				}
			}
			GWT.log("dobFormat = " + dobFormat);
			request.setDobFormat(dobFormat);


			dispatch.execute(
					new ImportStaffAjaxRequest(request, sessionManager
							.getSession()),
					new AsyncCallback<ImportStaffAjaxResponse>() {
						@Override
						public void onFailure(Throwable e) {
							errorHandler.alert(e.getMessage());
							win.endWait();
						}

						@Override
						public void onSuccess(ImportStaffAjaxResponse response) {
							ImportStaffAjaxResponseVo vo = response
									.getResponse();
							refreshPanelStep4(vo);
							win.endWait();
						}

					});
//		}
	}

	private void doFinalImport() {
//		if (!GWT.isScript()) {
//			List<ImportStaffRawVo> list = new ArrayList<ImportStaffRawVo>();
//			List<List<Long>> rawCodes = new ArrayList<List<Long>>();
//			Map<Long, String> codes = new HashMap<Long, String>();
//			Map<Long, String> types = new HashMap<Long, String>();
//			codes.put(new Long(0), "成功");
//			codes.put(new Long(1), "身份证不是15或18位");
//			codes.put(new Long(2), "入职日期在未来");
//			codes.put(new Long(3), "手机号为空");
//			codes.put(new Long(4), "企业卡号段不足分配");
//			types.put(new Long(0), "成功");
//			types.put(new Long(1), "警告");
//			types.put(new Long(2), "警告");
//			types.put(new Long(3), "严重错误");
//			types.put(new Long(4), "严重错误");
//			for (int i = 0; i < 500; i++) {
//				ImportStaffRawVo raw = new ImportStaffRawVo();
//				if (i >= 100) {
//					raw.setStaffNumber("1001" + i);
//				} else if (i >= 10) {
//					raw.setStaffNumber("10010" + i);
//				} else {
//					raw.setStaffNumber("100100" + i);
//				}
//				raw.setLastName("测");
//				raw.setFirstName("试" + i);
//				raw.setForeignLastName("elt");
//				raw.setForeignFirstName("test" + i);
//				raw.setEmailAddress("a" + i + "@b.c");
//				raw.setDateOfEmployment("2008-08-08");
//				raw.setDepartment("IT|Development");
//				raw.setDob("1988-08-08");
//				raw.setGender("男");
//				if (i >= 100) {
//					raw.setIdNo("321256198808081" + i);
//				} else if (i >= 10) {
//					raw.setIdNo("3212561988080810" + i);
//				} else {
//					raw.setIdNo("32125619880808100" + i);
//				}
//				raw.setLocation("tester location " + i);
//				raw.setMemberCardNumber("");
//				raw.setMinorityNationality("test minority nationality " + i);
//				if (i >= 100) {
//					raw.setMobileTelephoneNumber("13488885" + i);
//				} else if (i >= 10) {
//					raw.setMobileTelephoneNumber("134888850" + i);
//				} else {
//					raw.setMobileTelephoneNumber("1348888500" + i);
//				}
//				raw.setNativePlace("test native place " + i);
//				list.add(raw);
//				List<Long> rawCode = new ArrayList<Long>();
//				if (i % 5 == 0) {
//					rawCode.add(new Long(0));
//				} else if (i % 10 == 1) {
//					rawCode.add(new Long(1));
//				} else if (i % 10 == 2) {
//					rawCode.add(new Long(1));
//					rawCode.add(new Long(2));
//				} else if (i % 10 == 3) {
//					rawCode.add(new Long(1));
//					rawCode.add(new Long(2));
//					rawCode.add(new Long(3));
//				} else if (i % 10 == 4) {
//					rawCode.add(new Long(4));
//				}
//				rawCodes.add(rawCode);
//			}
//
//			ImportStaffAjaxResponseVo resp = new ImportStaffAjaxResponseVo();
//			resp.setAllImportStaffCodeInfos(codes);
//			resp.setAllImportStaffCodeTypes(types);
//			resp.setId(batchId);
//			resp.setImportStaffRawCode(rawCodes);
//			resp.setFinalSuccessDeptNum((long) 10);
//			resp.setFinalSuccessNum((long) 500);
//			resp.setFinalNewAssignCardNum((long) 100);
//			resp.setFinalNewAutoCardNum((long) 100);
//			resp.setFinalOldAssignCardNum((long) 100);
//			resp.setImportBatchNo((long) 8);
//			refreshPanelStep6(resp);
//			display.showPanelStep6();
//		} else {
			ImportStaffAjaxRequestVo request = new ImportStaffAjaxRequestVo();

			request.setAction("import");
			request.setId(batchId);

			dispatch.execute(
					new ImportStaffAjaxRequest(request, sessionManager
							.getSession()),
					new AsyncCallback<ImportStaffAjaxResponse>() {
						@Override
						public void onFailure(Throwable e) {
							if (progressTimer != null) {
								progressTimer.cancel();
								progressTimer = null;
								currentProgress = 0;
							}
							errorHandler.alert(e.getMessage());
							display.showPanelStep6();
						}

						@Override
						public void onSuccess(ImportStaffAjaxResponse response) {
							ImportStaffAjaxResponseVo vo = response
									.getResponse();
							GWT.log("do final import result = " + vo);
							refreshPanelStep6(vo);
							display.showPanelStep6();
						}

					});
//		}
	}

	private void doExport(String action, String title) {
		String url = GWT.getModuleBaseURL() + "servlet.export";
		String data = "batchId=" + batchId + "&action=" + action;
		String wholeUrl = url + "?" + data + "&radom=" + Math.random();
		Window.open(wholeUrl, title,
				"menubar=yes,location=no,resizable=yes,scrollbars=yes,status=yes");
	}
	


}
