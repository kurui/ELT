package com.chinarewards.gwt.elt.client.gift.view;

import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftViewPresenter.GiftViewDisplay;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class GiftViewWidget extends Composite implements GiftViewDisplay {

	// --------vo
	@UiField
	Label name;
	@UiField
	Label explains;
	@UiField
	ListBox type;
	// @UiField
	// Label source;
	@UiField
	Label business;
	@UiField
	Label address;
	@UiField
	Label tell;
	@UiField
	Label integral;
	@UiField
	Label stock;
	// @UiField
	Label phone;
	// @UiField
	// Label status;// boolean
	// @UiField
	// Label deleted;// boolean
	// @UiField
	// DateBox indate;
	// @UiField
	// DateBox recorddate;
	// @UiField
	// Label recorduser;
	// @UiField
	// DateBox updatetime;
	// ---end vo

	@UiField
	Image giftImage;

	@UiField
	Button back;

	@UiField
	Button update;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface GiftWidgetBinder extends UiBinder<Widget, GiftViewWidget> {

	}

	private static GiftWidgetBinder uiBinder = GWT
			.create(GiftWidgetBinder.class);

	@Inject
	public GiftViewWidget(DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));

		initAddWidget();

	}

	private void initAddWidget() {
		type.addItem("实物", "1");
		type.addItem("虚拟", "2");
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
	public void showGift(GiftVo giftVo) {
		System.out.println("showGift ----------" + giftVo.getName()+"--"+giftVo.getPhoto());
		name.setText(giftVo.getName());
		explains.setText(giftVo.getExplains());
//		type.setItemText(0, giftVo.getType());
		// type.setText();
		business.setText(giftVo.getBusiness());
		address.setText(giftVo.getAddress());
		tell.setText(giftVo.getTell());
		// photo.setText(giftVo.getPhoto());
		giftImage.setUrl("/imageshow?imageName=" + giftVo.getPhoto());
		giftImage.setVisible(true);
		
		integral.setText(giftVo.getIntegral() + "");
		stock.setText(giftVo.getStock() + "");
//		phone.setText(giftVo.getPhone());
		
		// @UiField
		// Label status;// boolean
		// @UiField
		// Label deleted;// boolean
		// @UiField
		// DateBox indate;
		// @UiField
		// DateBox recorddate;
		// @UiField
		// Label recorduser;
		// @UiField
		// DateBox updatetime;
		// ---end vo
	}
}
