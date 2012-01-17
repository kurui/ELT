package com.chinarewards.gwt.elt.client.gift.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.gift.plugin.GiftConstants;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdResponse;
import com.chinarewards.gwt.elt.client.gift.model.GiftClient;
import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class GiftViewPresenterImpl extends
		BasePresenter<GiftViewPresenter.GiftViewDisplay> implements
		GiftViewPresenter {
	String instanceId;// 修改时传过来的ID

	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	String giftId;
	GiftClient param = new GiftClient();

	@Inject
	public GiftViewPresenterImpl(EventBus eventBus, GiftViewDisplay display,
			DispatchAsync dispatcher, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.errorHandler = errorHandler;

	}

	@Override
	public void bind() {
		registerHandler(display.getBackClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										GiftConstants.EDITOR_GIFTLIST_SEARCH,
										"EDITOR_GIFT_List_DO_ID", instanceId);

					}

				}));

		registerHandler(display.getUpdateClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(GiftConstants.EDITOR_GIFT_EDIT,
										"EDITOR_GIFT_EDIT" + giftId, param);
					}
				}));
	}

	// 查看时初始化数据
	@Override
	public void initInstanceId(String instanceId, GiftClient item) {
		this.instanceId = instanceId;
		param = item;// 把查看得到的VO保存下来给修改时做为参数用
		initDataToEditGift(item, instanceId);
	}

	private void initDataToEditGift(final GiftClient item,
			final String instanceId) {
		giftId = item.getId();

		if (instanceId.equals(GiftConstants.EDITOR_GIFT_VIEW)) {
			dispatcher.execute(new SearchGiftByIdRequest(giftId),
					new AsyncCallback<SearchGiftByIdResponse>() {
						@Override
						public void onFailure(Throwable arg0) {
							errorHandler.alert("查询礼品出错!");
							Platform.getInstance()
									.getEditorRegistry()
									.closeEditor(
											GiftConstants.EDITOR_GIFT_VIEW,
											instanceId);
						}

						@Override
						public void onSuccess(SearchGiftByIdResponse response) {
							GiftVo item = response.getGift();
							display.showGift(item);
						}

					});
		}
	}

}
