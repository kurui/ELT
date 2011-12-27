package com.chinarewards.gwt.elt.client.rewardItem.view;

import java.util.Iterator;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemListPresenter.RewardsItemListDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsTypeClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
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
	TextBox name;

	

	@UiField
	Panel departmentName;

	@UiField
	CheckBox chooseSubDepartment;

	@UiField
	Button search;
	
	@UiField
	Button addBtn;
	
	// is inject
	//final DepartmentComboTree DeptCombTree;

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
	

}