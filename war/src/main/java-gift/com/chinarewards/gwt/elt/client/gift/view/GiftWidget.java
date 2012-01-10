package com.chinarewards.gwt.elt.client.gift.view;

import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftPresenter.GiftDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.OrganizationSpecialTextArea;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class GiftWidget extends Composite implements GiftDisplay {

	// 保存或修改
	@UiField
	Button save;

	FrequencyClient frequency;
	String rewardsUnit;

	SpecialTextArea<OrganicationClient> staffArea;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface GiftWidgetBinder extends UiBinder<Widget, GiftWidget> {

	}

	private static GiftWidgetBinder uiBinder = GWT
			.create(GiftWidgetBinder.class);

	@Inject
	public GiftWidget(DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		staffArea = new OrganizationSpecialTextArea();

	}

	@Override
	public HasClickHandlers getSearchBtnClickHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<String> getName() {
		return null;
	}

	@Override
	public HasValue<String> getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Panel getResultPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Panel getResultpage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<Boolean> getNowJudge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasClickHandlers getSaveClick() {
		return save;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}
}
