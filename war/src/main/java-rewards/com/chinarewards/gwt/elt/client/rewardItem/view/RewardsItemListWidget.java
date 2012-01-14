package com.chinarewards.gwt.elt.client.rewardItem.view;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemListPresenter.RewardsItemListDisplay;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;

/**
 * 
 * @author yanxin
 * @since 0.2.0 2010-01-03
 */
public class RewardsItemListWidget extends Composite implements
		RewardsItemListDisplay {

	@UiField
	Panel result;
	@UiField
	Panel pager;
	@UiField
	TextBox name;
	
	@UiField
	DateBox createTime;
	@UiField
	DateBox createTimeEnd;

	@UiField
	Panel departmentName;

	@UiField
	CheckBox chooseSubDepartment;

	@UiField
	Button search;
	
	@UiField
	Button addBtn;
	@UiField
	ListBox status;
	@UiField
	InlineLabel dataCount;
	@Override
	public String getStatus() {
		return status.getValue(status.getSelectedIndex());
	}
	// is inject
	//final DepartmentComboTree DeptCombTree;
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(ViewConstants.date_format);
	interface RewardsItemListWidgetBinder extends
			UiBinder<Widget, RewardsItemListWidget> {

	}

	public static RewardsItemListWidgetBinder uiBinder = GWT
			.create(RewardsItemListWidgetBinder.class);
	
	@Inject
	public RewardsItemListWidget( DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager) {
		//this.DeptCombTree = new DepartmentComboTree(dispatch, errorHandler,	sessionManager);
		initWidget(uiBinder.createAndBindUi(this));
		createTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		createTimeEnd.setFormat(new DateBox.DefaultFormat(dateFormat));
		
	}
	public void initStatus(Map<String, String> map) {
		//status.addItem("未选择", "");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			status.addItem(entry.getValue(), entry.getKey());
		}
	}
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public Panel getDataContainer() {
		return result;
	}
	@Override
	public Panel getDataPager() {
		return pager;
	}

	@Override
	public HasValue<String> getSearchName() {
		return name;
	}

	@Override
	public HasClickHandlers getSearchClick() {
		return search;
	}

	
	@Override
	public HasValue<Boolean> getChooseSubDepartment() {
		return chooseSubDepartment;
	}

	@Override
	public void initialize() {

	}

	@Override
	public Panel getDepartmentPanel() {
		return departmentName;
	}

	@Override
	public HasValue<Date> getCreateTime() {
		return createTime;
	}
	
	@Override
	public HasValue<Date> getCreateTimeEnd() {
		return createTimeEnd;
	}

//	@Override
//	public void showDept(List<String> deptIds) {
//		if (deptIds != null) {
//			DeptCombTree.setRoot(deptIds);
//		}
//		departmentName.add(DeptCombTree);
//		
//	}
	
//	@Override
//	public String getBuildDept() {
//		return DeptCombTree.getSelectedItem() != null ? DeptCombTree
//				.getSelectedItem().getId() : null;
//	}

	@Override
	public HasClickHandlers getAddBut() {
		return addBtn;
	}
	@Override
	public void setDataCount(String text) {
		dataCount.setText(text);
	}
	

}
