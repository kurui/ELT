package com.chinarewards.gwt.elt.client.gift.presenter;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.IUploader.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.ImportGiftListAdapter;
import com.chinarewards.gwt.elt.client.gift.model.ImportGiftAjaxRequestVo;
import com.chinarewards.gwt.elt.client.gift.model.ImportGiftAjaxResponseVo;
import com.chinarewards.gwt.elt.client.gift.model.ImportGiftListClient;
import com.chinarewards.gwt.elt.client.gift.model.ImportGiftListCriteria;
import com.chinarewards.gwt.elt.client.gift.request.ImportGiftAjaxRequest;
import com.chinarewards.gwt.elt.client.gift.request.ImportGiftAjaxResponse;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftImportListRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftImportListResponse;
import com.chinarewards.gwt.elt.client.gift.request.UpdateImportGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.UpdateImportGiftResponse;
import com.chinarewards.gwt.elt.client.gift.ui.ImportGiftSingleUploader;
import com.chinarewards.gwt.elt.client.mvp.BaseDialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmHandler;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.TextInputCell;
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

public class GiftImportPresenterImpl extends
		BaseDialogPresenter<GiftImportPresenter.GiftImportDisplay> implements
		GiftImportPresenter {

//	private final static int TITLE_INSTRUCTION_DISPLAY_ROW = 6;
	private final static int TITLE_INSTRUCTION_DISPLAY_COL = 6;
	private final static int CONTENT_INSTRUCTION_DISPLAY_COL = 6;
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

	String[] correctHeaderSorting = { "name", "sourceText", "integral",
			"price", "stock","statusText"};
	
	EltNewPager pager;
	ListCellTable<ImportGiftListClient> cellTable;
	ImportGiftListAdapter listViewAdapter;
	int pageSize=ViewConstants.per_page_number_in_dialog;
	@Inject
	public GiftImportPresenterImpl(EventBus eventBus,
			GiftImportDisplay display, DispatchAsync dispatch,
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
						"档案 "  + " (" + info.size + "bytes) "
								+ "已经上传。");
				refreshPanelStep2();
				display.showPanelStep2();
			} else {
				display.getResultMessage().setText(
						"档案 "  + " (" + info.size + "bytes) "
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
		
		registerHandler(display.getPageNumber().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				pageSize=Integer.parseInt(display.getPageNumber().getValue(display.getPageNumber().getSelectedIndex()));
				buildTable();
				doSearch();
			}
		}));
		
		display.getGiftFileUploader().addOnFinishUploadHandler(
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
//								new RemoveGiftRawBatchRequest(batchId),
//								new AsyncCallback<RemoveGiftRawBatchResponse>() {
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
//											RemoveGiftRawBatchResponse result) {
//										display.getContentPreviewPanel()
//												.clear();
//										display.getTitlePreviewPanel().clear();
//										display.getImportDetails().setHTML("");
//										display.getImportFailure().setHTML("");
//										display.getImportBatchNo().setText("");
//										display.getImportGiftBatchNo()
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
//						display.getContentPreviewPanel().clear();
//						display.getTitlePreviewPanel().clear();
//						display.getImportDetails().setHTML("");
//						display.getImportFailure().setHTML("");
//						display.getImportBatchNo().setText("");
//						display.getImportGiftBatchNo().setText("");
//						display.getPretreatmentResult().setHTML("");
//						display.getResultMessage().setText("");
//						display.getSuccessImportMessage().setText("");
//						batchId = null;
//						xlsContent = new ArrayList<List<String>>();
//						errorCodes = new ArrayList<List<Long>>();
						display.showPanelStep1();
					}

				}));

		/** 显示第二步panel **/
		registerHandler(display.getGoStep2Button().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						if("".equals(display.getGiftFileUploader()
								.getFileName()) && batchId!=null)
						{
							refreshPanelStep2();
							display.showPanelStep2();
						}
						else
						{
							if ("".equals(display.getGiftFileUploader()
									.getFileName())
									|| (!display.getGiftFileUploader()
											.getFileName().toLowerCase().trim()
											.endsWith(".xls") && !display
											.getGiftFileUploader().getFileName()
											.toLowerCase().trim().endsWith(".xlsx"))) {
								win.alert("请选中一个符合标准的EXCEL文件上传！");
								return;
							}
							display.getGiftFileUploader().setServletPath(
									ImportGiftSingleUploader.SERVLET_PATH+"?nowUserId="+sessionManager.getSession().getToken()+"&corporationId="+sessionManager.getSession().getCorporationId());
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
											display.getGiftFileUploader().submit();
										}
									});
						}	
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
						if(display.getSelectDataCount()<=0)
						{
							win.alert("没有选择任何上传数据,请重新选择！");
							return;
						}
						updateShowData();			
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
						doExportToo("preview", "导入预览报告");
					}
				}));

		/** 导入button **/
		registerHandler(display.getImportGiftButton().addClickHandler(
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
						doExportToo("fatal", "下载失败纪录");
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
		initTable();
	}

	private void refreshPanelStep3() {
		display.getContentPreviewPanel().clear();

		if (getInstructionGiftNum() > 0) {
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
					contentInstructionListBoxes.add(getGiftColumnListBox());
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
			List<ListBox> staffContentListBoxes = getGiftContentListBox();
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

	private void refreshPanelStep4(ImportGiftAjaxResponseVo vo) {
		int totalGift = 0;
		int totalGiftFailed = 0;
		int totalGiftWarn = 0;
		List<String> warnIssues = new ArrayList<String>();
		Map<String, Long> warnIssueCounts = new HashMap<String, Long>();
		List<String> fatalIssues = new ArrayList<String>();
		Map<String, Long> fatalIssueCounts = new HashMap<String, Long>();
		GWT.log("vo.getImportGiftRawCode().size() = "
				+ vo.getImportGiftRawCode().size());
		GWT.log("vo.getImportGiftRawCode().get(0).size() = "
				+ vo.getImportGiftRawCode().get(0).size());
		for (List<Long> rawCode : vo.getImportGiftRawCode()) {
			totalGift++;
			boolean isFatalGiftRaw = false;
			boolean isWarnGiftRaw = false;
			for (Long code : rawCode) {
				if ("严重问题".equals(vo.getAllImportGiftCodeTypes().get(code))) {
					if (!isFatalGiftRaw) {
						totalGiftFailed++;
						isFatalGiftRaw = true;
					}
					if (!fatalIssues.contains(vo.getAllImportGiftCodeInfos()
							.get(code))) {
						fatalIssues.add(vo.getAllImportGiftCodeInfos().get(
								code));
					}
					Long count = fatalIssueCounts.get(vo
							.getAllImportGiftCodeInfos().get(code));
					GWT.log("fatal count = " + count + " for "
							+ vo.getAllImportGiftCodeInfos().get(code));
					if (count == null) {
						count = new Long(0);
					}
					fatalIssueCounts.put(
							vo.getAllImportGiftCodeInfos().get(code),
							count + 1);
				} else if ("警告".equals(vo.getAllImportGiftCodeTypes()
						.get(code))) {
					if (!isWarnGiftRaw) {
						totalGiftWarn++;
						isWarnGiftRaw = true;
					}
					if (!warnIssues.contains(vo.getAllImportGiftCodeInfos()
							.get(code))) {
						warnIssues.add(vo.getAllImportGiftCodeInfos()
								.get(code));
					}
					Long count = warnIssueCounts.get(vo
							.getAllImportGiftCodeInfos().get(code));
					if (count == null) {
						count = new Long(0);
					}
					warnIssueCounts.put(
							vo.getAllImportGiftCodeInfos().get(code),
							count + 1);
				}
			}
		}
		String result = "";
		result += "<br/>你已经准备好导入信息<br/><br/>";
		result += "<b>" + totalGift + "</b>笔信息<br/><br/>";
		
		if(totalGiftFailed>0)
		{
			result += "<span style='color:red'>" + totalGiftFailed
					+ "</span>笔信息有下列严重问题, 将被忽略<br/><br/>";
			for (String fatalIssue : fatalIssues) {
				result += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-<span style='color:black'>"
						+ fatalIssueCounts.get(fatalIssue)
						+ "</span>笔信息"
						+ fatalIssue + "<br/>";
			}
		}
		if(totalGiftWarn>0)
		{
			result += "<br/><span style='color:black'>"
					+ (totalGiftWarn) + "</span>笔信息中有下列警告问题:<br/>";
			for (String warnIssue : warnIssues) {
				result += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-<span style='color:black'>"
						+ warnIssueCounts.get(warnIssue)
						+ "</span>笔信息"
						+ warnIssue
						+ "<br/>";
			}
		}

		result += "<br/><b>预计成功导入员工数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:blue'><b>"
				+ vo.getEstimateSuccessNum() + "</b></span><br/>";
		
		if(!StringUtil.isEmpty(vo.getLicenseMessage()))
		{
			result +="<br/><span style='color:red'><b>"+vo.getLicenseMessage()+"</b></span><br/>";
			display.importGiftButtonEnable(false);
		}

		//result += "<br/>" + vo.getEstimateSuccessDeptNum() + "个部门将被创建<br/>";


		display.getPretreatmentResult().setHTML(result);

//		display.getImportGiftBatchNo()
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
		currentProgress += 0.5;
	}

	private Long toLong(Long p) {
		if (p != null)
			return p;
		return new Long(0);
	}

	private void refreshPanelStep6(ImportGiftAjaxResponseVo vo) {
		display.getImportBatchNo().setText("导入编号：" + vo.getImportBatchNo());

	//	long totalGift = toLong(vo.getFinalFailedNum())+ toLong(vo.getFinalSuccessNum());
		int totalGiftFailed = 0;

		display.getSuccessImportMessage().setText(
				toLong(vo.getFinalSuccessNum()) + "笔信息已成功被导入");

		String fatalSummary = "<span style='color:black'> </span>信息"+ "编号或邮箱重复" + "<br/>";

		long unknownFatalNum = toLong(vo.getFinalFailedNum())
				- totalGiftFailed;
		if (unknownFatalNum > 0) {
			fatalSummary += "<span style='color:black'>" + unknownFatalNum
					+ "</span>笔信息由于系统或网络问题<br/>";
		}

		String detailSummary = "";

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
		for (int i = 0; i < getInstructionGiftNum(); i++) {
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
		boolean result= "dateColumnName".equals(columnValue);
		
		return result;
	}

	private ListBox getGiftColumnListBox() {
		ListBox listBox = new ListBox();
		listBox.addItem("名称", "name");
		listBox.addItem("来源", "sourceText");
		listBox.addItem("兑换缤纷", "integral");
		listBox.addItem("采购价", "price");
		listBox.addItem("库存", "stock");
		listBox.addItem("状态", "statusText");

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

	private int getInstructionGiftNum() {
		if (xlsContent == null)
			return 0;
		return CONTENT_INSTRUCTION_DISPLAY_ROW > xlsContent.size() ? xlsContent
				.size() : CONTENT_INSTRUCTION_DISPLAY_ROW;
	}

	private List<ListBox> getGiftContentListBox() {
		List<ListBox> list = new ArrayList<ListBox>();
		for (int i = 0; i < getInstructionGiftNum(); i++) {
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
			ImportGiftAjaxRequestVo request = new ImportGiftAjaxRequestVo();

			request.setAction("pretreatment");
			request.setId(batchId);
			request.setHavingTitle(isHavingTitle);

			String dobFormat = "";

			for (int j = 0; j < xlsContent.get(0).size(); j++) {
				if (formaterListBoxes.get(j).isVisible()) {
					if (correctHeaderSorting[j].equals("dob")) {
						dobFormat = formaterListBoxes.get(j).getValue(
								formaterListBoxes.get(j).getSelectedIndex());
					} 
				}
			}
			GWT.log("dobFormat = " + dobFormat);
			request.setDobFormat(dobFormat);


			dispatch.execute(
					new ImportGiftAjaxRequest(request, sessionManager
							.getSession()),
					new AsyncCallback<ImportGiftAjaxResponse>() {
						@Override
						public void onFailure(Throwable e) {
							errorHandler.alert(e.getMessage());
							win.endWait();
						}

						@Override
						public void onSuccess(ImportGiftAjaxResponse response) {
							ImportGiftAjaxResponseVo vo = response
									.getResponse();
							refreshPanelStep4(vo);
							win.endWait();
						}

					});

	}

	private void doFinalImport() {
			ImportGiftAjaxRequestVo request = new ImportGiftAjaxRequestVo();

			request.setAction("import");
			request.setId(batchId);

			dispatch.execute(
					new ImportGiftAjaxRequest(request, sessionManager
							.getSession()),
					new AsyncCallback<ImportGiftAjaxResponse>() {
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
						public void onSuccess(ImportGiftAjaxResponse response) {
							ImportGiftAjaxResponseVo vo = response
									.getResponse();
							GWT.log("do final import result = " + vo);
							refreshPanelStep6(vo);
							display.showPanelStep6();
						}

					});
	}

	private void doExport(String action, String title) {
		String url = GWT.getModuleBaseURL() + "servlet.export";
		String data = "&content=false";
		String wholeUrl = url + "?" + data + "&radom=" + Math.random();
		Window.open(wholeUrl, title,
				"menubar=yes,location=no,resizable=yes,scrollbars=yes,status=yes");
	}
	private void doExportToo(String action, String title) {
		String url = GWT.getModuleBaseURL() + "servlet.isrs";
		String data = "batchId=" + batchId + "&action=" + action;
		String wholeUrl = url + "?" + data + "&radom=" + Math.random();
		Window.open(wholeUrl, title,
				"menubar=yes,location=no,resizable=yes,scrollbars=yes,status=yes");
	}

	private void initTable() {	
		buildTable();
		doSearch();
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<ImportGiftListClient>();

		initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(pageSize);
	//	cellTable.getColumn(0).setCellStyleNames("divTextLeft");
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
		
	}

	private void doSearch() {
		
		ImportGiftListCriteria criteria = new ImportGiftListCriteria();
		criteria.setBatchId(batchId);
		criteria.setTitlefal(isHavingTitle);
		listViewAdapter = new ImportGiftListAdapter(dispatch, criteria,
				errorHandler, sessionManager,display);
		listViewAdapter.addDataDisplay(cellTable);

	}

	private void initTableColumns() {
		Sorting<ImportGiftListClient> ref = new Sorting<ImportGiftListClient>() {
			@Override
			public void sortingCurrentPage(Comparator<ImportGiftListClient> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);

			}
		};
		cellTable.addColumn("选择", new CheckboxCell(),
				new GetValue<ImportGiftListClient, Boolean>() {
					@Override
					public Boolean getValue(ImportGiftListClient staff) {
						if(staff.getImportfal()!=null && staff.getImportfal()==1)
							return false;
						else
							return true;
					}
				},new FieldUpdater<ImportGiftListClient, Boolean>() {
					@Override
					public void update(int index, ImportGiftListClient o,
							Boolean value) {
						//Window.alert(o.getId()+"=="+value);
						int fal=0;
						if(value==false)
						{
							fal=1;
							display.setSelectDataCount((display.getSelectDataCount()-1)+"");
						}
						else
						{
							fal=0;
							display.setSelectDataCount((display.getSelectDataCount()+1)+"");
						}
						
						dispatch.execute(
								new UpdateImportGiftRequest(sessionManager
										.getSession(),o.getId(),fal),
								new AsyncCallback<UpdateImportGiftResponse>() {
									@Override
									public void onFailure(Throwable e) {
										win.alert("失败");
									}

									@Override
									public void onSuccess(UpdateImportGiftResponse response) {
//										if(response.getFal()!=1)
//											win.alert("失败");
											
									}

								});
					}
				});
		
		cellTable.addColumn("名称", new TextCell(),
				new GetValue<ImportGiftListClient, String>() {
					@Override
					public String getValue(ImportGiftListClient client) {
						return client.getName();
					}
				}, ref, "name");
		cellTable.addColumn("来源", new TextCell(),
				new GetValue<ImportGiftListClient, String>() {
					@Override
					public String getValue(ImportGiftListClient client) {
						return client.getSourceText();
					}
				}, ref, "source");
		
		cellTable.addColumn("库存", new TextCell(),
				new GetValue<ImportGiftListClient, String>() {
					@Override
					public String getValue(ImportGiftListClient client) {
						return client.getStock();
					}
				}, ref, "stock");
		cellTable.addColumn("采购价格", new TextCell(),
				new GetValue<ImportGiftListClient, String>() {
					@Override
					public String getValue(ImportGiftListClient client) {
						return client.getPrice();
					}
				}, ref, "price");
		
		/*cellTable.addColumn("兑换缤纷", new TextCell(),
				new GetValue<ImportGiftListClient, String>() {
					@Override
					public String getValue(ImportGiftListClient client) {
						return client.getIntegral();
					}
				}, ref, "integral");
*/
		cellTable.addColumn("兑换缤纷", new TextInputCell(),
				new GetValue<ImportGiftListClient, String>() {
					@Override
					public String getValue(ImportGiftListClient client) {
						return client.getIntegral();
					}
				}, ref, "integral");
		/*cellTable.addColumn("状态", new TextCell(),
				new GetValue<ImportGiftListClient, String>() {
					@Override
					public String getValue(ImportGiftListClient client) {
						return client.getStatusText();
					}
				}, ref, "status");
	*/
		List<String> statusList=new ArrayList<String>();
		statusList.add("不限");
		statusList.add("上架");
		statusList.add("未上架");
		
		cellTable.addColumn("状态", new SelectionCell(statusList),
				new GetValue<ImportGiftListClient, String>() {
					@Override
					public String getValue(ImportGiftListClient client) {
						return client.getStatusText();
					}
				}, ref, "status");
	}
	
	private void updateShowData() {
		
		System.out.println(cellTable);
		
		
		ImportGiftListCriteria criteria = new ImportGiftListCriteria();
		criteria.setBatchId(batchId);
		criteria.setImportfal(true);
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(0);
		pagination.setLimit(10);
		criteria.setPagination(pagination);
		
		dispatch.execute(new SearchGiftImportListRequest(criteria, sessionManager
				.getSession()), new AsyncCallback<SearchGiftImportListResponse>() {
			@Override
			public void onFailure(Throwable e) {
				errorHandler.alert(e.getMessage());
			}

			@Override
			public void onSuccess(SearchGiftImportListResponse response) {

				List<List<String>> result = new ArrayList<List<String>>();
				for (int i = 0; i < response.getResult().size(); i++) {
					ImportGiftListClient client=response.getResult().get(i);
					
					List<String> giftCells = new ArrayList<String>();
									
					giftCells.add(client.getName());
					giftCells.add(client.getSourceText());
					giftCells.add(client.getIntegral());
					giftCells.add(client.getPrice());
					giftCells.add(client.getStock());
					giftCells.add(client.getStatusText());
				
					result.add(giftCells);
				}
				xlsContent=result;
				refreshPanelStep3();
				display.showPanelStep3();
			}

		});
		
	}
}
