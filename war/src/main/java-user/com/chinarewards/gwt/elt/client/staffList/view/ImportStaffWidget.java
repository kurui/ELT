package com.chinarewards.gwt.elt.client.staffList.view;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploader;

import com.chinarewards.gwt.elt.client.staff.ui.ImportStaffSingleUploader;
import com.chinarewards.gwt.elt.client.staffList.presenter.ImportStaffPresenter.ImportStaffDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ImportStaffWidget extends Composite implements ImportStaffDisplay {

	private static StaffImportWidgetUiBinder uiBinder = GWT
			.create(StaffImportWidgetUiBinder.class);

	interface StaffImportWidgetUiBinder extends
			UiBinder<Widget, ImportStaffWidget> {
	}

	public ImportStaffWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}


	@UiField
	VerticalPanel panelV;

	@UiField
	DecoratorPanel panelStep1;

	@UiField
	DecoratorPanel panelStep2;

	@UiField
	DecoratorPanel panelStep3;

	@UiField
	DecoratorPanel panelStep4;

	@UiField
	DecoratorPanel panelStep5;

	@UiField
	DecoratorPanel panelStep6;
	/**
	 * panel1
	 * 
	 */
	@UiField
	Anchor downloadTemplete;

	@UiField
	VerticalPanel panelUpload;

	ImportStaffSingleUploader staffFileUploader;

	@UiField
	Label resultMsg;

	@UiField
	Button goStep2Button;
    @UiField
    Button cancelImport;
	/**
	 * 
	 * panel2
	 * 
	 */
	@UiField
	HTML totalStaff;
	@UiField
	RadioButton hasTitleValue;
	@UiField
	RadioButton noHasTitleValue;
	@UiField
	DecoratorPanel titlePreviewPanel;
	@UiField
	Button backStep1Button;
	@UiField
	Button goStep3Button;

	/**
	 * 
	 * panel3
	 * 
	 */
	@UiField
	DecoratorPanel contentPreviewPanel;

	@UiField
	Button backStep2Button;
	@UiField
	Button goStep4Button;

	/**
	 * panel4
	 * 
	 */
	@UiField
	Button pretreatmentReportButton;
	@UiField
	Button backStep3Button;
	@UiField
	Button importStaffButton;
	@UiField
	HTML pretreatmentResult;
	
	/**
	 * 
	 * panel5
	 */
	
	@UiField
	Label importStaffBatchNo;
	/**
	 * 进度条panel
	 */
	@UiField
	HorizontalPanel panelProgressBar;
	
	/**
	 * panel6
	 * 
	 */
	@UiField
	Button closeButton;
	@UiField
	Button finalReportButton;
	@UiField
	Label importBatchNo;

	@UiField
	Label successImportMessage;

	HTML importDetails;

	HTML importFailure;

	DisclosurePanel importDetailsPanel;

	DisclosurePanel importFailurePanel;
	@UiField
	VerticalPanel contentPanel;


	@Override
	public void init() {

		panelStep2.setVisible(false);
		panelStep3.setVisible(false);
		panelStep4.setVisible(false);
		panelStep5.setVisible(false);
		panelStep6.setVisible(false);

		staffFileUploader = new ImportStaffSingleUploader(FileInputType.BROWSER_INPUT,
				null, this.getGoStep2Button(), null);
		staffFileUploader.setAutoSubmit(false);
		staffFileUploader.setServletPath(ImportStaffSingleUploader.SERVLET_PATH);

		panelUpload.add(staffFileUploader);

		importDetailsPanel = new DisclosurePanel("详细");
		importDetailsPanel.setAnimationEnabled(true);
		importDetailsPanel.setVisible(true);
		importDetailsPanel.setOpen(true);
		importDetails = new HTML();
		importDetailsPanel.add(importDetails);
		
		importFailurePanel = new DisclosurePanel("失败总结");
		importFailurePanel.setAnimationEnabled(true);
		importFailurePanel.setVisible(true);
		importFailurePanel.setOpen(true);
		importFailure = new HTML();
		importFailurePanel.add(importFailure);
		
	    contentPanel.setSpacing(8);
	    contentPanel.add(importDetailsPanel);
	    contentPanel.add(importFailurePanel);
	    
	    panelProgressBar.setWidth("80%");
	    panelProgressBar.setHeight("20px");
	    
		clear();
	}

	@Override
	public void clear() {
		resultMsg.setText("");
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public IUploader getStaffFileUploader() {
		return this.staffFileUploader;
	}

	@Override
	public VerticalPanel getPanelUpload() {
		return this.panelUpload;
	}

	@Override
	public Label getResultMessage() {
		return this.resultMsg;
	}

	@Override
	public void showPanelStep1() {
		hideAllPanelSteps();
		panelStep1.setVisible(true);

	}

	@Override
	public void showPanelStep2() {
		hideAllPanelSteps();
		panelStep2.setVisible(true);

	}

	@Override
	public void showPanelStep3() {
		hideAllPanelSteps();
		panelStep3.setVisible(true);

	}

	@Override
	public void showPanelStep4() {
		hideAllPanelSteps();
		panelStep4.setVisible(true);
	}

	@Override
	public void showPanelStep5() {
		hideAllPanelSteps();
		importBatchNo.setText("");
		importBatchNo.setVisible(true);
		
		panelStep5.setVisible(true);
	}

	@Override
	public void showPanelStep6() {
		hideAllPanelSteps();
		panelStep6.setVisible(true);
	}

	public void hideAllPanelSteps() {
		panelStep1.setVisible(false);
		panelStep2.setVisible(false);
		panelStep3.setVisible(false);
		panelStep4.setVisible(false);
		panelStep5.setVisible(false);
		panelStep6.setVisible(false);
	}

	/**
	 * @return the panelV
	 */
	@Override
	public VerticalPanel getPanelV() {
		return panelV;
	}

	/**
	 * @return the panelStep1
	 */
	@Override
	public DecoratorPanel getPanelStep1() {
		return panelStep1;
	}

	/**
	 * @return the panelStep2
	 */
	@Override
	public DecoratorPanel getPanelStep2() {
		return panelStep2;
	}

	/**
	 * @return the panelStep3
	 */
	@Override
	public DecoratorPanel getPanelStep3() {
		return panelStep3;
	}

	/**
	 * @return the panelStep4
	 */
	@Override
	public DecoratorPanel getPanelStep4() {
		return panelStep4;
	}

	/**
	 * @return the panelStep5
	 */
	@Override
	public DecoratorPanel getPanelStep5() {
		return panelStep5;
	}

	/**
	 * @return the panelStep6
	 */
	@Override
	public DecoratorPanel getPanelStep6() {
		return panelStep6;
	}

	/**
	 * @return the resultMsg
	 */
	@Override
	public Label getResultMsg() {
		return resultMsg;
	}

	/**
	 * @return the goStep2
	 */
	@Override
	public Button getGoStep2Button() {
		return goStep2Button;
	}

	/**
	 * @return the totalStaff
	 */
	@Override
	public HTML getTotalStaff() {
		return totalStaff;
	}

	/**
	 * @return the hasTitleValue
	 */
	@Override
	public RadioButton getHasTitleValue() {
		return hasTitleValue;
	}

	/**
	 * @return the noHasTitleValue
	 */
	@Override
	public RadioButton getNoHasTitleValue() {
		return noHasTitleValue;
	}

	/**
	 * @return the backStep1
	 */
	@Override
	public Button getBackStep1Button() {
		return backStep1Button;
	}

	/**
	 * @return the goStep3
	 */
	@Override
	public Button getGoStep3Button() {
		return goStep3Button;
	}

	/**
	 * @return the backStep2
	 */
	@Override
	public Button getBackStep2Button() {
		return backStep2Button;
	}

	/**
	 * @return the goStep4
	 */
	@Override
	public Button getGoStep4Button() {
		return goStep4Button;
	}

	/**
	 * @return the pretreatmentReportButton
	 */
	@Override
	public Button getPretreatmentReportButton() {
		return pretreatmentReportButton;
	}

	/**
	 * @return the backStep3
	 */
	@Override
	public Button getBackStep3Button() {
		return backStep3Button;
	}

	/**
	 * @return the importStaffButton
	 */
	@Override
	public Button getImportStaffButton() {
		return importStaffButton;
	}

	/**
	 * @return the importProgressButton
	 */
	@Override
	public Button getImportProgressButton() {
		return null;//importProgressBar;
	}

	/**
	 * @return the closeButton
	 */
	@Override
	public Button getCloseButton() {
		return closeButton;
	}

	/**
	 * @return the finalReportButton
	 */
	@Override
	public Button getFinalReportButton() {
		return finalReportButton;
	}

	/**
	 * @return the titlePreviewPanel
	 */
	@Override
	public DecoratorPanel getTitlePreviewPanel() {
		return titlePreviewPanel;
	}

	/**
	 * @return the titlePreviewPanel
	 */
	@Override
	public DecoratorPanel getContentPreviewPanel() {
		return contentPreviewPanel;
	}

	/**
	 * @return the pretreatmentResult
	 */
	@Override
	public HTML getPretreatmentResult() {
		return pretreatmentResult;
	}

	/**
	 * @return the importDetails
	 */
	@Override
	public HasHTML getImportDetails() {
		return importDetails;
	}

	/**
	 * @return the importFailure
	 */
	@Override
	public HasHTML getImportFailure() {
		return importFailure;
	}

	/**
	 * @return the importBatchNo
	 */
	@Override
	public HasText getImportBatchNo() {
		return importBatchNo;
	}

	/**
	 * @return the successImportMessage
	 */
	@Override
	public HasText getSuccessImportMessage() {
		return successImportMessage;
	}
	/**
	 * @return the panelProgessBar
	 */
	@Override
	public HorizontalPanel getPanelProgressBar() {
		return panelProgressBar;
	}

	@Override
	public HasText getImportStaffBatchNo() {
		
		return importStaffBatchNo;
	}

	@Override
	public HasClickHandlers getCancelImport() {
		return cancelImport;
	}

	@Override
	public HasClickHandlers getDownloadTemplete() {
		return downloadTemplete;
	}
	
}
