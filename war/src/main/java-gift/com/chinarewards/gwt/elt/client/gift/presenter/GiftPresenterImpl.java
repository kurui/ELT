package com.chinarewards.gwt.elt.client.gift.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.client.gift.plugin.GiftConstants;
import com.chinarewards.gwt.elt.client.gift.request.EditGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.EditGiftResponse;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdRequest;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.chinarewards.gwt.elt.util.XmlUtil_GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.xml.client.Document;
import com.google.inject.Inject;

public class GiftPresenterImpl extends BasePresenter<GiftPresenter.GiftDisplay>
		implements GiftPresenter {
	String instanceId;// 修改时传过来的ID

	private String thisAction;
	private String giftId;
	//
	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	private final SessionManager sessionManager;

	private final Win win;

	// GiftVo item;

	@Inject
	public GiftPresenterImpl(EventBus eventBus, GiftDisplay display,
			DispatchAsync dispatcher, ErrorHandler errorHandler,
			SessionManager sessionManager, Win win) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
	}

	@Override
	public void bind() {
		// 绑定事件
		init();

		System.out.println("-------thisAction:" + thisAction);

		if (GiftConstants.ACTION_GIFT_ADD.equals(thisAction)) {
			// initSave();
		} else if (GiftConstants.ACTION_GIFT_EDIT.equals(thisAction)) {
			initEdit();
		} else {
			win.alert("未定义的方法");
		}
	}

	private void init() {
		// 保存事件
		registerHandler(display.getSaveClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						if (!validateSubmit()) {
							return;
						}

						GiftVo giftVo = adapter();
						if ("addGift".equals(thisAction)) {
							giftVo.setId(null);
							doSave(giftVo);
						} else if ("editGift".equals(thisAction)) {
							giftVo.setId(giftId);
							doEdit(giftVo);
						} else {
							win.alert("未定义的方法");
						}
					}

					private void doSave(GiftVo gift) {
						dispatcher.execute(new EditGiftRequest(gift,
								sessionManager.getSession()),
								new AsyncCallback<EditGiftResponse>() {
									@Override
									public void onFailure(Throwable t) {
										errorHandler.alert(t.toString());
									}

									@Override
									public void onSuccess(
											EditGiftResponse response) {
										Window.alert("添加成功");
										// if(instanceId!=null||!instanceId.equals(""))
										Platform.getInstance()
												.getEditorRegistry()
												.openEditor(
														GiftConstants.EDITOR_GIFTLIST_SEARCH,
														GiftConstants.ACTION_GIFT_LIST,
														instanceId);
									}
								});
					}

					private void doEdit(GiftVo gift) {
						if (Window.confirm("确定修改?")) {
							dispatcher.execute(new EditGiftRequest(gift,
									sessionManager.getSession()),
									new AsyncCallback<EditGiftResponse>() {
										@Override
										public void onFailure(Throwable t) {
											Window.alert("修改失败");
											Platform.getInstance()
													.getEditorRegistry()
													.closeEditor(
															GiftConstants.EDITOR_GIFT_EDIT,
															instanceId);
										}

										@Override
										public void onSuccess(
												EditGiftResponse arg0) {
											Window.alert("修改成功");
											Platform.getInstance()
													.getEditorRegistry()
													.openEditor(
															GiftConstants.EDITOR_GIFTLIST_SEARCH,
															GiftConstants.ACTION_GIFT_LIST,
															instanceId);
										}
									});
						}
					}

				}));

		// 浏览即上传事件
		registerHandler(display.getPhotoUpload().addChangeHandler(
				new ChangeHandler() {
					@Override
					public void onChange(ChangeEvent arg0) {
						display.getGiftImage().setVisible(true);
						display.getPhotoForm().submit();
					}
				}));

		// 上传图片事件
		registerHandler(display.getUploadClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						display.getPhotoForm().submit();
					}
				}));

		// 文件上传后回调
		display.getPhotoForm().addSubmitCompleteHandler(
				new SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						// System.out.println("submitComplete event.getResults:"
						// + event.getResults());
						// win.alert(event.getResults());
						try {

							Document doc = XmlUtil_GWT.parseXml(event
									.getResults());
							String result = XmlUtil_GWT.getSingleNodeText(doc,
									"result");
							String info = XmlUtil_GWT.getSingleNodeText(doc,
									"info");
							if ("SUCCESS".equals(result)) {
								display.getPhoto().setValue(info);
								display.getGiftImage().setUrl(
										"/imageshow?imageName=" + info);
							} else {
								win.alert("上传图片异常<br>" + info);
							}
						} catch (Exception e) {
							e.printStackTrace();
							win.alert("上传图片异常，请重试" + e.getMessage());
							return;
						}

					}
				});

	}

	// 验证方法
	private boolean validateSubmit() {
		boolean flag = true;
		StringBuilder errorMsg = new StringBuilder();
		if (display.getName().getValue() == null
				|| "".equals(display.getName().getValue().trim())) {
			errorMsg.append("请填写礼品名称!<br>");
			flag = false;
		}

		if (display.getStock() == null
				|| StringUtil.valueOf(display.getStock().getValue()) == null
				|| StringUtil.valueOf(display.getStock().getValue()) < 0) {
			errorMsg.append("请正确填写礼品库存!<br>");
			flag = false;
		}

		// if (display.getName().getValue() == null
		// || "".equals(display.getName().getValue().trim())) {
		// errorMsg.append("请填写礼品名称!<br>");
		// flag = false;
		// }

		if (display.getPhotoUpload().getFilename().length() == 0) {
			errorMsg.append("请选择图片文件!<br>");
			flag = false;
		} else if (!display.getPhotoUpload().getFilename().endsWith(".jpg")
				&& !display.getPhotoUpload().getFilename().endsWith(".gif")) {
			errorMsg.append("请确认图片格式,仅支持JPG和GIF!<br>");
			flag = false;
		}

		if (!flag) {
			win.alert(errorMsg.toString());
		}

		return flag;
	}

	private void initEdit() {
		dispatcher.execute(new SearchGiftByIdRequest(giftId),
				new AsyncCallback<SearchGiftByIdResponse>() {
					@Override
					public void onFailure(Throwable arg0) {
						errorHandler.alert("查询出错!");
						Platform.getInstance()
								.getEditorRegistry()
								.closeEditor(GiftConstants.EDITOR_GIFT_EDIT,
										instanceId);
					}

					@Override
					public void onSuccess(SearchGiftByIdResponse response) {
						GiftVo giftVo = response.getGift();
						clear();
						display.initEditGift(giftVo);

					}
				});
	}

	/**
	 * 封装表单属性
	 * */
	private GiftVo adapter() {
		GiftVo giftVo = new GiftVo();
		//

		// // 基本信息
		giftVo.setName(display.getName().getValue().trim());
		giftVo.setExplains(display.getExplains().getValue().trim());
		giftVo.setType(display.getType());
		// giftVo.setSource(display.getSource().getValue().trim());
		giftVo.setSource("合作商家");
		// giftVo.setBusiness(display.getBusiness().getValue().trim());
		giftVo.setAddress(display.getAddress().getValue().trim());
		giftVo.setTell(display.getTell().getValue().trim());
		giftVo.setPhoto(display.getPhoto().getValue().trim());
		giftVo.setStock(StringUtil.valueOf(display.getStock().getValue()));
		giftVo.setIntegral(StringUtil.valueOf(display.getIntegral().getValue()));
		// giftVo.setPhoto(display.getPhone().getValue());
		// giftVo.setGiftStatus();
		// giftVo.setDeleted(false);
		// giftVo.setIndate(display.getIndate());

		return giftVo;
	}

	private void clear() {
		display.clear();
	}

	public void setId(String id) {
		this.giftId = id;
	}

	@Override
	public void initEditor(String giftId, String thisAction) {
		this.giftId = giftId;
		this.thisAction = thisAction;
	}

}
