package com.chinarewards.gwt.elt.client.gift.view;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.gift.presenter.ChooseExportTypePresenter.ChooseExportTypeDisplay;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ChooseExportTypeWidget extends Composite implements
		ChooseExportTypeDisplay {

	@UiField
	ListBox exportFileType;

	@UiField
	Button chooseBtn;

	interface ChooseExportTypeWidgetBinder extends
			UiBinder<Widget, ChooseExportTypeWidget> {
	}

	private static ChooseExportTypeWidgetBinder uiBinder = GWT
			.create(ChooseExportTypeWidgetBinder.class);

	@Inject
	public ChooseExportTypeWidget(DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));
		initWidget();
	}

	private void initWidget() {
		exportFileType.clear();
		exportFileType.addItem("XLS", "XLS");
		exportFileType.addItem("CSV", "CSV");

	}

	public Widget asWidget() {
		return this;
	}


	@Override
	public ListBox getExportFileType() {
		return exportFileType;
	}

	@Override
	public Button getChooseBtn() {
		return chooseBtn;
	}

}
