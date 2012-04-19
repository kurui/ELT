package com.chinarewards.gwt.elt.client.support.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.ui.event.PlatformInitEvent;
import com.chinarewards.gwt.elt.client.login.LastLoginRoleRequest;
import com.chinarewards.gwt.elt.client.login.LastLoginRoleResponse;
import com.chinarewards.gwt.elt.client.login.LoginRequest;
import com.chinarewards.gwt.elt.client.login.LoginResponse;
import com.chinarewards.gwt.elt.client.login.TokenValidRequest;
import com.chinarewards.gwt.elt.client.login.TokenValidResponse;
import com.chinarewards.gwt.elt.client.login.event.LoginEvent;
import com.chinarewards.gwt.elt.client.login.event.LoginHandler;
import com.chinarewards.gwt.elt.client.login.presenter.AlertErrorWidget;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.remote.login.LoginServiceAsync;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.support.UserSession;
import com.chinarewards.gwt.elt.client.ui.DialogBox;
import com.chinarewards.gwt.elt.client.win.confirm.WinEvent;
import com.chinarewards.gwt.elt.client.win.confirm.WinHandler;
import com.chinarewards.gwt.elt.client.win.loginconfirm.LoginConfirmDialog;
import com.chinarewards.gwt.elt.model.ClientException;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Provide LoginEvent
 * 
 * @author kmtong
 * 
 */
public class CookieSessionManager implements SessionManager {

	final UserSession session;
	final EventBus eventBus;
	final DispatchAsync dispatchAsync;
	private final LoginServiceAsync loginService;
	final Provider<LoginConfirmDialog> logindialogProvider;
	private static final int COOKIE_TIMEOUT = 1000 * 60 * 60;
	List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();

	@Inject
	public CookieSessionManager(UserSession session, final EventBus eventBus,
			DispatchAsync dispatchAsync,LoginServiceAsync loginService,Provider<LoginConfirmDialog> logindialogProvider) {
		this.session = session;
		this.eventBus = eventBus;
		this.dispatchAsync = dispatchAsync;
		this.loginService=loginService;
		this.logindialogProvider=logindialogProvider;
	}

	public void authenticate(String username, String password, String verifyCode) {
		if (null == username || username.trim().equals("")) {
			//Window.alert("账号不能为空!");
			final AlertErrorWidget ae = new AlertErrorWidget();
			final DialogBox dialogBoxae = new DialogBox();
			ae.getOkBtn().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					dialogBoxae.hide();
				}
			});
			ae.setMessage("账号不能为空!");
			dialogBoxae.setWidget(ae);
			dialogBoxae.setGlassEnabled(true);
			dialogBoxae.setAnimationEnabled(true);
			dialogBoxae.setWidth("350px");
			dialogBoxae.setText("提示");
			dialogBoxae.center();
			dialogBoxae.show();
			return;
		}
		if (null == password || password.trim().equals("")) {
			//Window.alert("密码不能为空!");
			final AlertErrorWidget ae = new AlertErrorWidget();
			final DialogBox dialogBoxae = new DialogBox();
			ae.getOkBtn().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					dialogBoxae.hide();
				}
			});
			ae.setMessage("密码不能为空!");
			dialogBoxae.setWidget(ae);
			dialogBoxae.setGlassEnabled(true);
			dialogBoxae.setAnimationEnabled(true);
			dialogBoxae.setWidth("350px");
			dialogBoxae.setText("提示");
			dialogBoxae.center();
			dialogBoxae.show();
			return;
		}
		if (null == verifyCode || verifyCode.trim().equals("")) {
			//Window.alert("密码不能为空!");
			final AlertErrorWidget ae = new AlertErrorWidget();
			final DialogBox dialogBoxae = new DialogBox();
			ae.getOkBtn().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					dialogBoxae.hide();
				}
			});
			ae.setMessage("验证码不能为空!");
			dialogBoxae.setWidget(ae);
			dialogBoxae.setGlassEnabled(true);
			dialogBoxae.setAnimationEnabled(true);
			dialogBoxae.setWidth("350px");
			dialogBoxae.setText("提示");
			dialogBoxae.center();
			dialogBoxae.show();
			return;
		}
		loginService.authLogin(username, password, verifyCode,new AsyncCallback<UserSession>() {
			
			@Override
			public void onSuccess(final UserSession resp) {
				// TODO Auto-generated method stub
				tokenObtained(resp);

				UserRoleVo role = resp.getLastLoginRole();
				//判断最后一次登录的权限是否被删除
				List <UserRoleVo> roleslt = new ArrayList<UserRoleVo>();
				UserRoleVo [] roles=resp.getUserRoles();
				if(roles.length>0)
				{
					for (UserRoleVo r:roles) {
						roleslt.add(r);
					}
					if(roleslt.contains(UserRoleVo.CORP_ADMIN) && roleslt.contains(UserRoleVo.DEPT_MGR))
					{
						final DialogBox dialogBox = new DialogBox();
						final LoginConfirmDialog dialog = logindialogProvider.get();
						dialog.setTitle("权限选择");
						final HandlerRegistration registration = eventBus.addHandler(
								WinEvent.getType(), new WinHandler() {
									@Override
									public void confirm() {
										
										dialogBox.hide();

										if (UserRoleVo.valueOf(dialog.getLoginType()) == UserRoleVo.CORP_ADMIN)
										{		
											 session.setLastLoginRole(UserRoleVo.CORP_ADMIN);
											 
											 UserRoleVo [] tempRole=session.getUserRoles();
											 UserRoleVo [] tempRoleNew=new UserRoleVo[tempRole.length-1];
												if(tempRole.length>0)
												{
													int index=0;
													for (int i = 0; i < tempRole.length; i++) {
														if(tempRole[i]!= UserRoleVo.DEPT_MGR)
														{
															tempRoleNew[index]=tempRole[i];
															index++;
														}
													}
												}
											 session.setUserRoles(tempRoleNew);
											 
											 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK));
											 

										}
										else if (UserRoleVo.valueOf(dialog.getLoginType()) == UserRoleVo.DEPT_MGR)
										{
											 session.setLastLoginRole(UserRoleVo.DEPT_MGR);
											 
											 UserRoleVo [] tempRole=session.getUserRoles();
											 UserRoleVo [] tempRoleNew=new UserRoleVo[tempRole.length-1];
												if(tempRole.length>0)
												{
													int index=0;
													for (int i = 0; i < tempRole.length; i++) {
														if(tempRole[i]!= UserRoleVo.CORP_ADMIN)
														{
															tempRoleNew[index]=tempRole[i];
															index++;
														}
													}
												}
											 session.setUserRoles(tempRoleNew);
											 
											 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_DEPT));
										}
										dispatchAsync.execute(new LastLoginRoleRequest(resp.getToken(),session.getLastLoginRole()),
												new AsyncCallback<LastLoginRoleResponse>() {
					
													@Override
													public void onFailure(Throwable e) {
														tokenObtained(null);
														eventBus.fireEvent(new PlatformInitEvent(false));
													}
					
													@Override
													public void onSuccess(LastLoginRoleResponse resp) {
														//成功
														if("success".equals(resp.getFal()))
															GWT.log("success update last login role ");
														
													}
												});
										
									}
								});
						
						
						ScrollPanel panel = new ScrollPanel();
						panel.add(dialog.asWidget());
						dialogBox.setWidget(panel);
						dialogBox.setGlassEnabled(true);
						dialogBox.setAnimationEnabled(true);
						dialogBox.setText(dialog.getTitle());
						dialogBox.setDialog(dialog);				
						dialogBox.center();
						dialogBox.setPopupPosition(Window.getClientWidth()/4, Window.getClientHeight()/4);
						dialogBox.show();
						dialogBox.addCloseHandler(new CloseHandler<PopupPanel>() {
							
							@Override
							public void onClose(CloseEvent<PopupPanel> event) {
								registration.removeHandler();
								dialogBox.hide();
							}
						});
					}
					else
					{
						if(role!=null && roleslt.contains(role))
						{
								if (role == UserRoleVo.CORP_ADMIN)
									 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK));
								else if (role == UserRoleVo.DEPT_MGR)
									 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_DEPT));
								else if (role == UserRoleVo.STAFF)
									 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_STAFF));
								else if (role == UserRoleVo.GIFT)
									eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_GIFT));	
						}
						else
						{
									if(roleslt.contains(UserRoleVo.CORP_ADMIN))
									{		
										 role=UserRoleVo.CORP_ADMIN;
										 session.setLastLoginRole(role);
										 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK));
									}
									else if(roleslt.contains(UserRoleVo.DEPT_MGR))
									{
										 role=UserRoleVo.DEPT_MGR;
										 session.setLastLoginRole(role);
										 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_DEPT));
									}
									else if(roleslt.contains(UserRoleVo.GIFT))
									{
										 role=UserRoleVo.GIFT;
										 session.setLastLoginRole(role);
										 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_GIFT));							 
									}
									else if(roleslt.contains(UserRoleVo.STAFF))
									{
										 role=UserRoleVo.STAFF;
										 session.setLastLoginRole(role);
										 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_STAFF));
									}
									else 
										Window.alert("没有角色");
								
						}
						if(role!=null)
						{
							dispatchAsync.execute(new LastLoginRoleRequest(resp.getToken(),role),
									new AsyncCallback<LastLoginRoleResponse>() {
		
										@Override
										public void onFailure(Throwable e) {
											tokenObtained(null);
											eventBus.fireEvent(new PlatformInitEvent(false));
										}
		
										@Override
										public void onSuccess(LastLoginRoleResponse resp) {
											//成功
											if("success".equals(resp.getFal()))
												GWT.log("success update last login role ");
											
										}
									});
							
						}
					}
					
					
					
					
					
					

					
					
				
				}
			}
			
			@Override
			public void onFailure(Throwable e) {
				tokenObtained(null);
				
				final AlertErrorWidget ae = new AlertErrorWidget();
				final DialogBox dialogBoxae = new DialogBox();
				ae.getOkBtn().addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						dialogBoxae.hide();
					}
				});
				ae.setMessage(e.getMessage());
				dialogBoxae.setWidget(ae);
				dialogBoxae.setGlassEnabled(true);
				dialogBoxae.setAnimationEnabled(true);
				dialogBoxae.setWidth("350px");
				dialogBoxae.setText("提示");
				dialogBoxae.center();
				dialogBoxae.show();
				eventBus.fireEvent(new LoginEvent(
						LoginEvent.LoginStatus.LOGIN_FAILED, e));
				
			}
		});
		
		LoginRequest req = new LoginRequest(username, password, verifyCode);
		dispatchAsync.execute(req, new AsyncCallback<LoginResponse>() {

			@Override
			public void onFailure(Throwable e) {
				
			}

			@Override
			public void onSuccess(LoginResponse resp) {
				
			
				}
		});
	}

	public void logout() {
		this.session.setToken(null);
		eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGOUT));
	}
	public void relogout() {
		this.session.setToken(null);
		eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.RELOGOUT));
	}
	public UserSession getSession() {
		String token = Cookies.getCookie("token");
		if(!StringUtil.isEmpty(token))
			return session;
		else{
		
			relogout();
		    throw new ClientException("登录失效,请重新登录!");
		}
	}

	public void registerLoginEventHandler(LoginHandler handler) {
		registerHandler(eventBus.addHandler(LoginEvent.getType(), handler));
	}

	protected void registerHandler(HandlerRegistration handlerRegistration) {
		handlerRegistrations.add(handlerRegistration);
	}

	public void bind() {
		GWT.log("SessionManager Bind");
		String token = Cookies.getCookie("token");
		if (token != null) {
			// userService.reauthenticate(token, new AsyncCallback<String>() {
			// public void onFailure(Throwable e) {
			// Window.alert("Login Error: " + e.getMessage());
			// tokenObtained(null);
			// // no login token in cookie
			// eventBus.fireEvent(new LoginEvent(
			// LoginEvent.LoginStatus.LOGIN_EXPIRED));
			// }
			//
			// public void onSuccess(String token) {
			// tokenObtained(token);
			// eventBus.fireEvent(new LoginEvent(
			// LoginEvent.LoginStatus.LOGIN_OK));
			// }
			// });
			eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGOUT));
		} else {
			eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGOUT));
		}
	}
	protected void tokenObtained(UserSession rep) {
		if (rep != null && rep.getToken() != null) {
			session.setToken(rep.getToken());
			session.setLoginName(rep.getLoginName());
			session.setCorporationId(rep.getCorporationId());
			session.setUserRoles(rep.getUserRoles());
			session.setDepartmentId(rep.getDepartmentId());
			session.setStaffId(rep.getStaffId());
			session.setLastLoginRole(rep.getLastLoginRole());
			session.setCorporationName(rep.getCorporationName());
			session.setPhoto(rep.getPhoto());
			Date expires = new Date((new Date()).getTime() + COOKIE_TIMEOUT);
			Cookies.setCookie("token", rep.getToken(), expires);

		} else {
			session.setToken(null);
			Cookies.removeCookie("token");
		}
	}
	protected void tokenObtaineds(LoginResponse rep) {
		if (rep != null && rep.getToken() != null) {
			session.setToken(rep.getToken());
			session.setLoginName(rep.getLoginName());
			session.setCorporationId(rep.getCorporationId());
			session.setUserRoles(rep.getUserRoles());
			session.setDepartmentId(rep.getDepartmentId());
			session.setStaffId(rep.getStaffId());
			session.setLastLoginRole(rep.getLastLoginRole());
			session.setCorporationName(rep.getCorporationName());
			session.setPhoto(rep.getPhoto());
			Date expires = new Date((new Date()).getTime() + COOKIE_TIMEOUT);
			Cookies.setCookie("token", rep.getToken(), expires);

		} else {
			session.setToken(null);
			Cookies.removeCookie("token");
		}
	}

	protected void tokenObtainedToo(TokenValidResponse rep) {
		if (rep != null && rep.getToken() != null) {
			session.setToken(rep.getToken());
			session.setLoginName(rep.getLoginName());
			session.setCorporationId(rep.getCorporationId());
			session.setUserRoles(rep.getUserRoles());
			session.setDepartmentId(rep.getDepartmentId());
			session.setStaffId(rep.getStaffId());
			session.setLastLoginRole(rep.getLastLoginRole());
			session.setCorporationName(rep.getCorporationName());
			session.setPhoto(rep.getPhoto());
			Date expires = new Date((new Date()).getTime() + COOKIE_TIMEOUT);
			Cookies.setCookie("token", rep.getToken(), expires);

		} else {
			session.setToken(null);
			Cookies.removeCookie("token");
		}
	}

	public void resetLogin() {
		tokenObtained(null);
	}

	@Override
	public void initialize() {
		// Check Cookie Validity
		GWT.log("Initializing SessionManager...");
		String token = Cookies.getCookie("token");

		System.out.println("token==========" + token);
		if (null != token && !token.trim().equals("")) {
			// check the token value.
			dispatchAsync.execute(new TokenValidRequest(token),
					new AsyncCallback<TokenValidResponse>() {

						@Override
						public void onFailure(Throwable e) {
							tokenObtained(null);
							eventBus.fireEvent(new PlatformInitEvent(false));
						}

						@Override
						public void onSuccess(final TokenValidResponse resp) {
							tokenObtainedToo(resp);

							UserRoleVo role = resp.getLastLoginRole();
							//判断最后一次登录的权限是否被删除
							List <UserRoleVo> roleslt = new ArrayList<UserRoleVo>();
							UserRoleVo [] roles=resp.getUserRoles();
							if(roles.length>0)
							{
								for (UserRoleVo r:roles) {
									roleslt.add(r);
								}
								
								
								
								
								if(role!=null && roleslt.contains(role))
								{
									if (role == UserRoleVo.CORP_ADMIN)
									{
										 UserRoleVo [] tempRole=session.getUserRoles();
										 UserRoleVo [] tempRoleNew=new UserRoleVo[tempRole.length-1];
											if(tempRole.length>0)
											{
												int index=0;
												for (int i = 0; i < tempRole.length; i++) {
													if(tempRole[i]!= UserRoleVo.DEPT_MGR)
													{
														tempRoleNew[index]=tempRole[i];
														index++;
													}
												}
											}
										 session.setUserRoles(tempRoleNew);
										 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK));
									}
									else if (role == UserRoleVo.DEPT_MGR)
									{
										 UserRoleVo [] tempRole=session.getUserRoles();
										 UserRoleVo [] tempRoleNew=new UserRoleVo[tempRole.length-1];
											if(tempRole.length>0)
											{
												int index=0;
												for (int i = 0; i < tempRole.length; i++) {
													if(tempRole[i]!= UserRoleVo.CORP_ADMIN)
													{
														tempRoleNew[index]=tempRole[i];
														index++;
													}
												}
											}
										 session.setUserRoles(tempRoleNew);
										 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_DEPT));
										 
									}
									else if (role == UserRoleVo.STAFF)
										 eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_STAFF));
									else if (role == UserRoleVo.GIFT)
										eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGIN_OK_GIFT));	
								}
								else
								{
									
									eventBus.fireEvent(new PlatformInitEvent(false));
									
								}
								
								if(role!=null)
								{
									dispatchAsync.execute(new LastLoginRoleRequest(resp.getToken(),role),
											new AsyncCallback<LastLoginRoleResponse>() {
				
												@Override
												public void onFailure(Throwable e) {
													tokenObtained(null);
													eventBus.fireEvent(new PlatformInitEvent(false));
												}
				
												@Override
												public void onSuccess(LastLoginRoleResponse resp) {
													//成功
													if("success".equals(resp.getFal()))
														GWT.log("success update last login role ");
													
												}
											});
									
								}
							
							}
						}
					});

		} else {
			eventBus.fireEvent(new PlatformInitEvent(false));
		}

	}

}
