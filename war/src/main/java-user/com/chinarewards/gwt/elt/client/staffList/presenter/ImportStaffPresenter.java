package com.chinarewards.gwt.elt.client.staffList.presenter;


import gwtupload.client.IUploader;

import com.chinarewards.gwt.elt.client.mvp.DialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public interface ImportStaffPresenter extends DialogPresenter<ImportStaffPresenter.ImportStaffDisplay> {
	

	public static interface ImportStaffDisplay extends Display {

		public IUploader getStaffFileUploader();

		/**
		 * @return the whole panel to upload file
		 */
		public VerticalPanel getPanelUpload();

		/**
		 * @return the result message
		 */
		public Label getResultMessage();

		void init();

		void clear();

		void showPanelStep1();

		void showPanelStep2();

		void showPanelStep3();

		void showPanelStep4();

		void showPanelStep5();

		void showPanelStep6();

		VerticalPanel getPanelV();

		DecoratorPanel getPanelStep1();

		DecoratorPanel getPanelStep2();

		DecoratorPanel getPanelStep3();

		DecoratorPanel getPanelStep4();

		DecoratorPanel getPanelStep5();

		DecoratorPanel getPanelStep6();

		HasText getResultMsg();

		HasClickHandlers getGoStep2Button();
		
		HasClickHandlers getCancelImport();

		HasHTML getTotalStaff();

		HasValue<Boolean> getHasTitleValue();

		HasValue<Boolean> getNoHasTitleValue();

		HasClickHandlers getBackStep1Button();

		HasClickHandlers getGoStep3Button();

		HasClickHandlers getBackStep2Button();

		HasClickHandlers getGoStep4Button();

		HasClickHandlers getPretreatmentReportButton();

		HasClickHandlers getBackStep3Button();

		HasClickHandlers getImportStaffButton();

		HasClickHandlers getImportProgressButton();

		HasClickHandlers getCloseButton();

		HasClickHandlers getFinalReportButton();

		DecoratorPanel getTitlePreviewPanel();

		DecoratorPanel getContentPreviewPanel();

		HasHTML getPretreatmentResult();

		HasHTML getImportDetails();

		HasHTML getImportFailure();

		HasText getImportBatchNo();

		HasText getSuccessImportMessage();

		HorizontalPanel getPanelProgressBar();
		
		HasText getImportStaffBatchNo();
		
		HasClickHandlers getDownloadTemplete();
		
		void importStaffButtonEnable(boolean fal);
		
		void setDataCount(String text);
		void setSelectDataCount(String text);
		int getSelectDataCount();
		
		Panel getResultPanel();
		ListBox getPageNumber();
		Panel getResultpage();
	}
}
