package com.chinarewards.gwt.elt.client.department.view;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.department.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentPresenter.DepartmentDisplay;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class DepartmentWidget extends Composite implements DepartmentDisplay {

	@UiField
	Label departmentNameLabel;
	@UiField
	TextBox departmentName;

	@UiField
	Hidden departmentId;
	@UiField
	TextBox leader;
	@UiField
	Hidden parentId;
	@UiField
	Label parentName;
	@UiField
	TextBox childdepartment;
	@UiField
	TextBox peopleNumber;
	@UiField
	TextBox yearintegral;
	@UiField
	TextBox issueintegral;

	@UiField
	Button save;

	@UiField
	Button back;

	@UiField
	Panel breadCrumbs;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface DepartmentWidgetBinder extends UiBinder<Widget, DepartmentWidget> {

	}

	private static DepartmentWidgetBinder uiBinder = GWT
			.create(DepartmentWidgetBinder.class);

	@Inject
	public DepartmentWidget(DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void initEditDepartment(DepartmentVo departmentVo) {
		System.out.println("----widget  initEditDepartment:"
				+ departmentVo.getId());
		departmentId.setValue(departmentVo.getId());
		departmentName.setText(departmentVo.getName());
		// departmentName.setVisible(false);
		departmentNameLabel.setText(departmentVo.getName());

		leader.setValue(departmentVo.getLeader());
		parentId.setValue(departmentVo.getParentId());
		parentName.setText(getDepartmentParentName(departmentVo.getParentName()));
		// private String childdeparmentIds;
		// private String childdeparmentNames;
		peopleNumber.setValue(departmentVo.getPeopleNumber());
		yearintegral.setValue(departmentVo.getYearintegral());
		issueintegral.setValue(departmentVo.getIssueintegral());

	}

	@Override
	public void initAddDepartment(DepartmentVo departmentVo) {
		departmentId.setValue(departmentVo.getId());
	}

	@Override
	public void initSaveSameLevelDepartment(DepartmentVo departmentVo) {
		parentId.setValue(departmentVo.getParentId());
		parentName.setText(getDepartmentParentName(departmentVo.getParentName()));

	}
	
	
	private static String getDepartmentParentName(String parentName){
		if(parentName!=null){
			if(parentName.indexOf("ROOT_DEPT")>-1){
				return "";
			}
		}
		return parentName;
	}

	@Override
	public void initSaveChildDepartment(DepartmentVo departmentVo) {
		parentId.setValue(departmentVo.getId());
		parentName.setText(departmentVo.getName());

	}

	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
	}

	@Override
	public HasValue<String> getLeader() {
		return leader;
	}

	@Override
	public void clear() {

	}

	@Override
	public HasClickHandlers getSaveClick() {
		return save;
	}

	@Override
	public HasClickHandlers getBackClick() {
		return back;
	}

	@Override
	public HasValue<String> getChilddepartment() {
		return childdepartment;
	}

	@Override
	public HasValue<String> getPeopleNumber() {
		return peopleNumber;
	}

	@Override
	public HasValue<String> getYearintegral() {
		return yearintegral;
	}

	@Override
	public HasValue<String> getIssueintegral() {
		return issueintegral;
	}

	@Override
	public HasValue<String> getDepartmentName() {
		return departmentName;
	}

	@Override
	public Hidden getParentId() {
		return parentId;
	}

	@Override
	public Hidden getDepartmentId() {
		return departmentId;
	}

}
