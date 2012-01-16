package com.chinarewards.gwt.elt.client.gift.presenter;

import org.dom4j.Document;

import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.client.gift.plugin.GiftConstants;
import com.chinarewards.gwt.elt.client.gift.request.AddGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.AddGiftResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.inject.Inject;

public class GiftPresenterImpl extends BasePresenter<GiftPresenter.GiftDisplay>
		implements GiftPresenter {
	String instanceId;// 修改时传过来的ID

	/**
	 * 是否为修改页，默认为false
	 */
	private boolean isEditPage = false;
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

						GiftVo gift = new GiftVo();
						//
						// // 基本信息
						gift.setName(display.getName().getValue().trim());
						gift.setExplains(display.getExplains().getValue()
								.trim());
						gift.setType(display.getType());
						// gift.setSource(display.getSource().getValue().trim());
						gift.setSource("合作商家");
						// gift.setBusiness(display.getBusiness().getValue().trim());
						gift.setAddress(display.getAddress().getValue().trim());
						gift.setTell(display.getTell().getValue().trim());
						gift.setPhoto(display.getPhoto().getValue().trim());
						gift.setStock(StringUtil.valueOf(display.getStock()
								.getValue()));
						// gift.setPhoto(display.getPhone().getValue());
						// gift.setGiftStatus();
						// gift.setDeleted(false);
						// gift.setIndate(display.getIndate());

						if (!isEditPage) {
							gift.setId(null);
							doSave(gift);
						} else {
							gift.setId(giftId);
							// doEdit(gift);// 修改功能
						}
					}

					private void doSave(GiftVo gift) {
						dispatcher.execute(new AddGiftRequest(gift,
								sessionManager.getSession()),
								new AsyncCallback<AddGiftResponse>() {
									@Override
									public void onFailure(Throwable t) {
										errorHandler.alert(t.toString());
									}

									@Override
									public void onSuccess(
											AddGiftResponse response) {
										Window.alert("添加成功");
										// if(instanceId!=null||!instanceId.equals(""))
										Platform.getInstance()
												.getEditorRegistry()
												.openEditor(
														GiftConstants.EDITOR_GIFTLIST_SEARCH,
														"EDITOR_REWARDSITEM_List_DO_ID",
														instanceId);
									}
								});
					}

					private void doEdit(GiftVo gift) {
						if (Window.confirm("确定修改?")) {
							dispatcher.execute(new AddGiftRequest(gift,
									sessionManager.getSession()),
									new AsyncCallback<AddGiftResponse>() {
										@Override
										public void onFailure(Throwable t) {
											Window.alert("修改失败");
											Platform.getInstance()
													.getEditorRegistry()
													.closeEditor(
															GiftConstants.EDITOR_GIFT_ADD,
															instanceId);
										}

										@Override
										public void onSuccess(
												AddGiftResponse arg0) {
											Window.alert("修改成功");
											Platform.getInstance()
													.getEditorRegistry()
													.openEditor(
															GiftConstants.EDITOR_GIFTLIST_SEARCH,
															"EDITOR_REWARDSITEM_List_DO_ID",
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
						System.out.println("submitComplete event.getResults:"
								+ event.getResults());
						win.alert(event.getResults());

						try {
//							Document doc = com.chinarewards.elt.util.XmlUtil_dom4j
//									.readResult(new StringBuffer().append(event
//											.getResults()));
//							com.chinarewards.elt.util.XmlUtil_dom4j.test();
//							com.chinarewards.gwt.elt.util.XmlUtil_dom4j.test();
							com.chinarewards.gwt.elt.client.util.XmlUtil_dom4j.test();
							
							// String result = XmlUtil_dom4j.getTextByNode(doc,
							// "/result");
							// String info = XmlUtil_dom4j.getTextByNode(doc,
							// "/info");
							// win.alert(result + "<br>" + info);
						} catch (Exception e) {
							e.printStackTrace();
							win.alert("上传图片异常，请重试" + e.getMessage());
							return;
						}

						// display.getPhoto().setValue(event.getResults());
						// display.getGiftImage().setUrl(
						// "/imageshow?imageName=" + event.getResults());
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

		System.out.println("validateSubmit()======" + flag);
		return flag;
	}

	private void clear() {
		display.clear();
	}

	public void setId(String id) {
		this.giftId = id;
		isEditPage = true;
	}

}
