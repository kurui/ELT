package com.chinarewards.gwt.elt.client.staffList.presenter;

import java.util.Comparator;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.ui.DialogCloseListener;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.department.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.department.request.SearchDepartmentByCorpIdRequest;
import com.chinarewards.gwt.elt.client.department.request.SearchDepartmentByCorpIdResponse;
import com.chinarewards.gwt.elt.client.mail.model.MailVo;
import com.chinarewards.gwt.elt.client.mail.presenter.MailSendAllDialog;
import com.chinarewards.gwt.elt.client.mail.presenter.MailSendDialog;
import com.chinarewards.gwt.elt.client.mail.request.MailRequest;
import com.chinarewards.gwt.elt.client.mail.request.MailResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffAdd.plugin.StaffAddConstants;
import com.chinarewards.gwt.elt.client.staffList.dataprovider.StaffListViewAdapter;
import com.chinarewards.gwt.elt.client.staffList.dialog.ImportStaffDialog;
import com.chinarewards.gwt.elt.client.staffList.dialog.StaffListPrintDialog;
import com.chinarewards.gwt.elt.client.staffList.model.StaffListClient;
import com.chinarewards.gwt.elt.client.staffList.model.StaffListCriteria;
import com.chinarewards.gwt.elt.client.staffList.model.StaffListCriteria.StaffStatus;
import com.chinarewards.gwt.elt.client.staffList.request.DeleteStaffRequest;
import com.chinarewards.gwt.elt.client.staffList.request.DeleteStaffResponse;
import com.chinarewards.gwt.elt.client.staffList.request.StaffGenerateUserRequest;
import com.chinarewards.gwt.elt.client.staffList.request.StaffGenerateUserResponse;
import com.chinarewards.gwt.elt.client.staffList.request.UpdateUserPwdRequest;
import com.chinarewards.gwt.elt.client.staffList.request.UpdateUserPwdResponse;
import com.chinarewards.gwt.elt.client.staffView.plugin.StaffViewConstants;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.client.ui.UniversalCell;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmHandler;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class StaffListPresenterImpl extends
		BasePresenter<StaffListPresenter.StaffListDisplay> implements
		StaffListPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;
	EltNewPager pager;
	ListCellTable<StaffListClient> cellTable;
	StaffListViewAdapter listViewAdapter;
	private final Provider<StaffListPrintDialog> staffListPrintDialogProvider;
	private final Provider<ImportStaffDialog> importStaffDialogProvider;
	private final BreadCrumbsPresenter breadCrumbs;
	final Provider<MailSendDialog> mailSendDialog;
	final Provider<MailSendAllDialog> mailSendAllDialog;
	int pageSize=ViewConstants.per_page_number_in_dialog;
	@Inject
	public StaffListPresenterImpl(EventBus eventBus,Provider<MailSendDialog> mailSendDialog,Provider<MailSendAllDialog> mailSendAllDialog,
			StaffListDisplay display, DispatchAsync dispatch,SessionManager sessionManager,Win win,BreadCrumbsPresenter breadCrumbs,
			ErrorHandler errorHandler,Provider<StaffListPrintDialog> staffListPrintDialogProvider,Provider<ImportStaffDialog> importStaffDialogProvider) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler=errorHandler;
		this.win=win;
		this.breadCrumbs=breadCrumbs;
		this.staffListPrintDialogProvider=staffListPrintDialogProvider;
		this.mailSendDialog = mailSendDialog;
		this.mailSendAllDialog = mailSendAllDialog;
		this.importStaffDialogProvider=importStaffDialogProvider;
	}

	@Override
	public void bind() {
		breadCrumbs.loadListPage();
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		init();
		
		registerHandler(display.getPageNumber().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				pageSize=Integer.parseInt(display.getPageNumber().getValue(display.getPageNumber().getSelectedIndex()));
				buildTable();
				doSearch();
			}
		}));
		registerHandler(display.getSearchBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						buildTable();
						doSearch();
					}
				}));

		registerHandler(display.getAddStaffBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Platform.getInstance()
						.getEditorRegistry()
						.openEditor(
								StaffAddConstants.EDITOR_STAFFADD_SEARCH,
								"EDITOR_STAFFADD_SEARCH_DO_ID", null);
					}
				}));
		registerHandler(display.getSynchronousStaffBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						win.alert("同步");
	
					}
				}));
		registerHandler(display.getQueryKey().addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				display.getQueryKey().setValue("");
				display.getQueryKey().setStyleName("textcss");
				
				
			}
		}));
		registerHandler(display.getPrintBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						
						StaffListPrintDialog dialog = staffListPrintDialogProvider.get();
						
						StaffListCriteria criteria = new StaffListCriteria();
						if(!"请输入员工编号、姓名或邮箱地址".equals(display.getStaffNameorNo().getValue()))
						criteria.setStaffNameorNo(display.getStaffNameorNo().getValue());

						if(!"ALL".equals(display.getSttaffStatus()))
							criteria.setStaffStatus(StaffStatus.valueOf(display.getSttaffStatus()));
						if(!"ALL".equals(display.getSttaffRole()))
							criteria.setStaffRole(UserRoleVo.valueOf(display.getSttaffRole()));
						if(!"ALL".equals(display.getDepartment().getValue(display.getDepartment().getSelectedIndex())))
							criteria.setDepartmentId(display.getDepartment().getValue(display.getDepartment().getSelectedIndex()));
						
						dialog.initPrintQuery(criteria);
						dialog.init();
						Platform.getInstance().getSiteManager().openDialog(dialog,null);

	
					}
				}));
		registerHandler(display.getImportStaffBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						
						ImportStaffDialog dialog = importStaffDialogProvider.get();
						Platform.getInstance().getSiteManager().openDialog(dialog,new DialogCloseListener() {
							
							@Override
							public void onClose(String dialogId, String instanceId) {
								buildTable();
								doSearch();
								
							}
						});

	
					}
				}));
		registerHandler(display.getCreateSysUserBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						win.confirm("提示", "确定批量生成帐号?",new ConfirmHandler() {
							
							@Override
							public void confirm() {
								dispatch.execute(new StaffGenerateUserRequest("ALL",sessionManager.getSession()),
										new AsyncCallback<StaffGenerateUserResponse>() {

											@Override
											public void onFailure(Throwable t) {
												win.alert(t.getMessage());
											}

											@Override
											public void onSuccess(StaffGenerateUserResponse resp) {
												win.alert(resp.getMessage());
												buildTable();
												doSearch();
											}
										});
								
							}
						});
					}
				}));
		  registerHandler(display.getSendMailAllBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						
						MailSendAllDialog dialog = mailSendAllDialog.get();
						Platform.getInstance().getSiteManager().openDialog(dialog,null);

	
					}
				}));
		  registerHandler(display.getExportBtnClickHandlers().addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							win.confirm("提示", "确定导出员工资料?",new ConfirmHandler() {
								
								@Override
								public void confirm() {
									String keyStr="";
									if(!"请输入员工编号、姓名或邮箱地址".equals(display.getStaffNameorNo().getValue()))
										keyStr=display.getStaffNameorNo().getValue();
									String data = "name="+keyStr;
									data = data+"&corpid="+sessionManager.getSession().getCorporationId();
									data = data+"&content=true";
									UserRoleVo[] userRoleVos =sessionManager.getSession().getUserRoles();
									for (UserRoleVo role : userRoleVos) {
									    String s = role.toString();
										if(s.equals("CORP_ADMIN")){
											data=data+"&userRole=CORP_ADMIN";
										}else if(s.equals("DEPT_MGR"))
											data=data+"&userRole=DEPT_MGR";
											
									}
									
									
									if(!"ALL".equals(display.getSttaffStatus())){
										 data=data+"&staffStatus="+StaffStatus.valueOf(display.getSttaffStatus());
									    
									}else{
										data=data+"&staffStatus=";
									}
									if(!"ALL".equals(display.getSttaffRole())){
										
									    data = data+"&role="+ UserRoleVo.valueOf(display.getSttaffRole());
									}else{
										data=data+"&role=";
									}
									if(!"ALL".equals(display.getDepartment().getValue(display.getDepartment().getSelectedIndex()))){
										
									   data = data+"&departmentId="+ display.getDepartment().getValue(display.getDepartment().getSelectedIndex());
									
									}else{
										data=data+"&departmentId=";
									}
									   doExport( data,  "导出员工数据");
								}
							});
						}
					}));
	}
	private void doExport(String data, String title) {
		String url = GWT.getModuleBaseURL() + "servlet.export";
		//String data = "batchId=" + batchId + "&action=" + action;
		String wholeUrl = url + "?" + data + "&radom=" + Math.random();
		Window.open(wholeUrl, title,"menubar=yes,location=no,resizable=yes,scrollbars=yes,status=yes");
	}
	private void init() {	
		display.initStaffStatus();
		initDepartmentList(null);
		
		
	}

	private void initDepartmentList(final String selectedValue){
		String corporationId=sessionManager.getSession().getCorporationId();
		dispatch.execute(new SearchDepartmentByCorpIdRequest(corporationId),
				new AsyncCallback<SearchDepartmentByCorpIdResponse>() {

					@Override
					public void onFailure(Throwable t) {
						win.alert("查询部门列表异常："+t.getMessage());
					}
		
					@Override
					public void onSuccess(SearchDepartmentByCorpIdResponse resp) {
						
						display.getDepartment().clear();
						display.getDepartment().addItem("不限", "ALL");
						
						int selectIndex = 0;
						int i = 0;
						for (DepartmentVo entry : resp.getDepartmentList()) {
							String keyValue = entry.getId();
							// System.out.println(entry.getId() + ": " + entry.getName());
						
								
							if(entry.getName().indexOf("ROOT")==-1){
								display.getDepartment().addItem(entry.getName(), entry.getId());
							}
							
							
							if (selectedValue != null && StringUtil.trim(selectedValue) != ""
									&& StringUtil.trim(keyValue) != "") {
								if (selectedValue.equals(keyValue)) {
									
									selectIndex = i;
								}
							}
							i++;
						}
						display.getDepartment().setSelectedIndex(selectIndex);
						
						buildTable();
						doSearch();
					}					
		});
	}
	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<StaffListClient>();

		initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(pageSize);
	//	cellTable.getColumn(0).setCellStyleNames("divTextLeft");
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
		
	}

	private void doSearch() {
		StaffListCriteria criteria = new StaffListCriteria();
		if(!"请输入员工编号、姓名或邮箱地址".equals(display.getStaffNameorNo().getValue()))
		criteria.setStaffNameorNo(display.getStaffNameorNo().getValue());
		if(!"ALL".equals(display.getSttaffStatus()))
			criteria.setStaffStatus(StaffStatus.valueOf(display.getSttaffStatus()));
		if(!"ALL".equals(display.getSttaffRole()))
			criteria.setStaffRole(UserRoleVo.valueOf(display.getSttaffRole()));
		if(!"ALL".equals(display.getDepartment().getValue(display.getDepartment().getSelectedIndex())))
			criteria.setDepartmentId(display.getDepartment().getValue(display.getDepartment().getSelectedIndex()));
		listViewAdapter = new StaffListViewAdapter(dispatch, criteria,
				errorHandler, sessionManager,display);
		listViewAdapter.addDataDisplay(cellTable);

	}

	private void initTableColumns() {
		Sorting<StaffListClient> ref = new Sorting<StaffListClient>() {
			@Override
			public void sortingCurrentPage(Comparator<StaffListClient> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);

			}
		};


		cellTable.addColumn("员工编号", new TextCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient staff) {
						return staff.getStaffNo();
					}
				}, ref, "jobNo");
		cellTable.addColumn("姓名", new TextCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient staff) {
						return staff.getStaffName();
					}
				}, ref, "name");
		cellTable.addColumn("所属部门", new TextCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient staff) {
						if(staff.getDepartmentName().indexOf("ROOT")==-1)
						return staff.getDepartmentName();
						else
						return "";
					}
				});
		cellTable.addColumn("职位", new TextCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient staff) {
						return staff.getJobPosition();
					}
				}, ref, "jobPosition");
		cellTable.addColumn("邮箱", new TextCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient staff) {
						return staff.getEmail();
					}
				}, ref, "email");
		cellTable.addColumn("员工状态", new TextCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient staff) {
						if(staff.getStaffStatus()!=null)
						return staff.getStaffStatus().getDisplayName();
						else
						return "未知";
					}
				});
		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient rewards) {
						return "查看";
					}
				}, new FieldUpdater<StaffListClient, String>() {

					@Override
					public void update(int index, final StaffListClient o,
							String value) {
						Platform.getInstance()
						.getEditorRegistry()
						.openEditor(
								StaffViewConstants.EDITOR_STAFFVIEW_SEARCH,
								"EDITOR_STAFFVIEW_SEARCH_DO_ID", o.getStaffId());
					}

				});
		if(sessionManager.getSession().getLastLoginRole()==UserRoleVo.CORP_ADMIN)
		{
		
		cellTable.addColumn("操作", new UniversalCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient rewards) {
						if(StringUtil.isEmpty(rewards.getUserId()) && rewards.getStaffStatus()==StaffStatus.JOB)
						return "<a style=\"color:bule;\" href=\"javascript:void(0);\">生成账户</a>";
						else
						return "<span  style='color: rgb(221, 221, 221);'>生成账户</span>";
					}
				}, new FieldUpdater<StaffListClient, String>() {

					@Override
					public void update(int index, final StaffListClient o,
							String value) {
						if(StringUtil.isEmpty(o.getUserId()) && o.getStaffStatus()==StaffStatus.JOB)
						{
						win.confirm("提示", "确定生成 <font color='blue'>"+o.getStaffName()+"</font> 的账户",new ConfirmHandler() {
							
							@Override
							public void confirm() {
								dispatch.execute(new StaffGenerateUserRequest(o.getStaffId(),sessionManager.getSession()),
										new AsyncCallback<StaffGenerateUserResponse>() {

											@Override
											public void onFailure(Throwable t) {
												win.alert(t.getMessage());
											}

											@Override
											public void onSuccess(StaffGenerateUserResponse resp) {
												win.alert(resp.getMessage());
												//sendMail(o.getEmail(),o.getStaffId());
											}
										});
								
							}
						});

					}}

				});
		cellTable.addColumn("操作", new UniversalCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient rewards) {
						if(!StringUtil.isEmpty(rewards.getUserId()) && rewards.getStaffStatus()!=StaffStatus.HIDE)
						return "<a style=\"color:bule;\" href=\"javascript:void(0);\">重置密码</a>";
						else
						return "<span  style='color: rgb(221, 221, 221);'>重置密码</span>";
					}
				}, new FieldUpdater<StaffListClient, String>() {

					@Override
					public void update(int index, final StaffListClient o,
							String value) {
						if(!StringUtil.isEmpty(o.getUserId()) && o.getStaffStatus()!=StaffStatus.HIDE)
						win.confirm("提示", "确定重置 <font color='blue'>"+o.getStaffName()+"</font> 的密码", new ConfirmHandler() {
							
							@Override
							public void confirm() {
								dispatch.execute(new UpdateUserPwdRequest(o.getStaffId(),"123",sessionManager.getSession()),
										new AsyncCallback<UpdateUserPwdResponse>() {

											@Override
											public void onFailure(Throwable t) {
												win.alert(t.getMessage());
											}

											@Override
											public void onSuccess(UpdateUserPwdResponse resp) {
												if("success".equals(resp.getMessage()))
												{
													win.alert("密码重置成功!初始密码:123");
													sendMail(o.getEmail(),o.getStaffId());
												}
												
											}
										});
								}
						});
						
						
					}

				});
		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient rewards) {
						return "发送Email";
					}
				}, new FieldUpdater<StaffListClient, String>() {

					@Override
					public void update(int index, final StaffListClient o,String value) {
						final MailSendDialog dialog = mailSendDialog.get();
						dialog.initStaff(o.getStaffId(), o.getStaffName());
						Platform.getInstance().getSiteManager().openDialog(dialog, new DialogCloseListener() {
							public void onClose(String dialogId,
									String instanceId) {
								
							}
						});
					}

				});
		cellTable.addColumn("操作", new UniversalCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient rewards) {
						if(rewards.getStaffStatus()!=StaffStatus.HIDE)
						return "<a style=\"color:bule;\" href=\"javascript:void(0);\">隐藏</a>";
						else
						return "<span  style='color: rgb(221, 221, 221);'>隐藏</span>";
					}
				}, new FieldUpdater<StaffListClient, String>() {

					@Override
					public void update(int index, final StaffListClient o,String value) {
						if(o.getStaffStatus()!=StaffStatus.HIDE)
							win.confirm("提示", "确定隐藏员工:"+o.getStaffName()+"!(隐藏后自动离职)",new ConfirmHandler() {
								
								@Override
								public void confirm() {
									dispatch.execute(new DeleteStaffRequest(o.getStaffId(),sessionManager.getSession(),"HIDE"),
											new AsyncCallback<DeleteStaffResponse>() {

												@Override
												public void onFailure(Throwable t) {
													win.alert(t.getMessage());
												}

												@Override
												public void onSuccess(DeleteStaffResponse resp) {
													if("success".equals(resp.getMessage()))
													{
														win.alert("隐藏成功!");
														buildTable();
														doSearch();
													}
													
												}
											});
									
								}
							});
					}

				});
		cellTable.addColumn("操作", new UniversalCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient rewards) {
						if(rewards.getStaffStatus()==StaffStatus.HIDE)
						return "<a style=\"color:bule;\" href=\"javascript:void(0);\">恢复</a>";
						else
						return "<span  style='color: rgb(221, 221, 221);'>恢复</span>";
					}
				}, new FieldUpdater<StaffListClient, String>() {

					@Override
					public void update(int index, final StaffListClient o,String value) {
						if(o.getStaffStatus()==StaffStatus.HIDE)
							win.confirm("提示", "确定恢复员工:"+o.getStaffName(),new ConfirmHandler() {
								
								@Override
								public void confirm() {
									dispatch.execute(new DeleteStaffRequest(o.getStaffId(),sessionManager.getSession(),"REST"),
											new AsyncCallback<DeleteStaffResponse>() {

												@Override
												public void onFailure(Throwable t) {
													win.alert(t.getMessage());
												}

												@Override
												public void onSuccess(DeleteStaffResponse resp) {
													if("success".equals(resp.getMessage()))
													{
														win.alert("恢复成功!");
														buildTable();
														doSearch();
													}
													
												}
											});
									
								}
							});
					}

				});
		cellTable.addColumn("操作", new UniversalCell(),
				new GetValue<StaffListClient, String>() {
					@Override
					public String getValue(StaffListClient rewards) {
						if(rewards.getStaffStatus()==StaffStatus.HIDE)
						return "<a style=\"color:bule;\" href=\"javascript:void(0);\">删除</a>";
						else
						return "<span  style='color: rgb(221, 221, 221);'>删除</span>";
					}
				}, new FieldUpdater<StaffListClient, String>() {

					@Override
					public void update(int index, final StaffListClient o,String value) {
						if(o.getStaffStatus()==StaffStatus.HIDE)
							win.confirm("提示", "您确认要删除<font color='blue'>'"+o.getStaffName()+"'</font>这条员工记录吗？ 删除后无法恢复,将会删除以下信息:" +
									"<br>1.获奖历史" +
									"<br>2.广播" +
									"<br>3.小组成员信息" +
									"<br>4.管理的部门信息" +
									"<br>5.提名人信息" +
									"<br>6.被提名人信息" +
									"<br>7.账户",new ConfirmHandler() {
								
								@Override
								public void confirm() {
									dispatch.execute(new DeleteStaffRequest(o.getStaffId(),sessionManager.getSession(),"DELETE"),
											new AsyncCallback<DeleteStaffResponse>() {

												@Override
												public void onFailure(Throwable t) {
													win.alert("有关联奖项数据,无法删除!");
												}

												@Override
												public void onSuccess(DeleteStaffResponse resp) {
													if("success".equals(resp.getMessage()))
													{
														win.alert("删除成功!");
														buildTable();
														doSearch();
													}
													
												}
											});
									
								}
							});
					}

				});
		}
		else
		{
			display.displayBtn();
		}
	}
	public void sendMail(String emailAddress,String staffId)
	   {
		   MailVo mailvo = new MailVo();
		   mailvo.setStaffId(staffId);
		   mailvo.setTitle("ELT账户注册信息");
		   mailvo.setContent("你的ELT账号是"+emailAddress.substring(0,emailAddress.indexOf("@"))+"初始密码是123");
		   MailRequest request = new MailRequest(mailvo,sessionManager.getSession());
		   dispatch.execute(request,new AsyncCallback<MailResponse>() {

						@Override
						public void onFailure(Throwable t) {
							win.alert(t.getMessage());
						}
	    				@Override
						public void onSuccess(MailResponse resp) {
							win.alert(resp.getToken());
							
						}
					});
	   }

}
