package com.chinarewards.gwt.elt.client.license.view;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.license.model.LicenseVo;
import com.chinarewards.gwt.elt.client.license.presenter.LicenseViewPresenter.LicenseViewDisplay;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class LicenseViewWidget extends Composite implements LicenseViewDisplay {

	// --------vo
	@UiField
	Label name;
	@UiField
	Label summary;
	@UiField
	Label dispatchcycle;
	@UiField
	Label explains;
	@UiField
	Label notes;
	@UiField
	Label typeText;
	@UiField
	Label source;
	@UiField
	Label brand;
	@UiField
	Label integral;
	@UiField
	Label stock;

	@UiField
	Label business;
	@UiField
	Label address;
	@UiField
	Label tell;
	@UiField
	Label servicetell;

	@UiField
	Label indate;


	@UiField
	Button back;

	@UiField
	Button update;

	@UiField
	Panel breadCrumbs;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface LicenseWidgetBinder extends UiBinder<Widget, LicenseViewWidget> {

	}

	private static LicenseWidgetBinder uiBinder = GWT
			.create(LicenseWidgetBinder.class);

	@Inject
	public LicenseViewWidget(DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getBackClick() {
		return back;
	}

	@Override
	public HasClickHandlers getUpdateClick() {
		return update;
	}

	@Override
	public void showLicense(LicenseVo licenseVo) {
		name.setText(licenseVo.getName());
		summary.setText(licenseVo.getSummary());
		dispatchcycle.setText(licenseVo.getDispatchcycle());
		explains.setText(licenseVo.getExplains());
		notes.setText(licenseVo.getNotes());
		typeText.setText(licenseVo.getTypeText());
		brand.setText(licenseVo.getBrand());


		integral.setText(licenseVo.getIntegral() + "");
		stock.setText(licenseVo.getStock() + "");
		
		source.setText(licenseVo.getSourceText());

		business.setText(licenseVo.getBusiness());
		address.setText(licenseVo.getAddress());
		tell.setText(licenseVo.getTell());
		servicetell.setText(licenseVo.getServicetell());
		
		indate.setText(DateTool.dateToString(licenseVo.getIndate()));

		// @UiField
		// Label status;// boolean
		// @UiField
		// Label deleted;// boolean
		// @UiField
		// DateBox recorddate;
		// @UiField
		// Label recorduser;
		// @UiField
		// DateBox updatetime;
		// ---end vo
	}

	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
	}
}
