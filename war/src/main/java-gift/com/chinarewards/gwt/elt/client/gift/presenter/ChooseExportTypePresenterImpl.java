package com.chinarewards.gwt.elt.client.gift.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.gift.presenter.ChooseExportTypePresenter.ChooseExportTypeDisplay;
import com.chinarewards.gwt.elt.client.mvp.BaseDialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ChooseExportTypePresenterImpl extends
		BaseDialogPresenter<ChooseExportTypeDisplay> implements
		ChooseExportTypePresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	final EltNewPager simplePager = new EltNewPager(TextLocation.CENTER);


	@Inject
	public ChooseExportTypePresenterImpl(EventBus eventBus,
			ChooseExportTypeDisplay display, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
	}

	public Widget asWidget() {
		return display.asWidget();
	}

	private void init() {

	}

	public void bind() {
		init();

		registerHandler(display.getChooseBtn().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						int selectedIndex = display.getExportFileType()
								.getSelectedIndex();
						String exportFileType = display.getExportFileType()
								.getValue(selectedIndex);

						eventBus.fireEvent(new ChooseExportTypeEvent(
								exportFileType));
						closeDialog();
					}
				}));
	}


}
