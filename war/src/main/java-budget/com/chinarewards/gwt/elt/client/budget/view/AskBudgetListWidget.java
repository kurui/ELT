package com.chinarewards.gwt.elt.client.budget.view;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetListPresenter.AskBudgetListDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AskBudgetListWidget extends Composite implements AskBudgetListDisplay {
	@UiField
	Panel resultPanel;
	@UiField
	Panel resultpage;
	
	@UiField
	Button addBtn;
	@UiField
	Button searchBtn;
	
	@UiField
	ListBox year;
	@UiField
	ListBox depart;
	@UiField
	InlineLabel totalCount;
	@UiField
	InlineLabel remainCount;
	@UiField
	Panel breadCrumbs;
	@UiField
	ListBox pageNumber;
	@UiField
	ListBox managedepart;
	@UiField
	InlineLabel lbldep;
	private static BudgetWidgetUiBinder uiBinder = GWT
			.create(BudgetWidgetUiBinder.class);

	interface BudgetWidgetUiBinder extends UiBinder<Widget, AskBudgetListWidget> {
	}

	public AskBudgetListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		pageNumber.clear();
		pageNumber.addItem("10","10");
		pageNumber.addItem("20","20");
		pageNumber.addItem("50","50");
	}

	@Override
	public HasClickHandlers getAddBtnClickHandlers() {
		return addBtn;
	}
	@Override
	public HasClickHandlers getSearchBtnClickHandlers() {
		return searchBtn;
	}
	
	@Override
	public Panel getResultPanel() {
		return resultPanel;
	}


	
	@Override
	public Panel getResultpage() {
		return resultpage;
	}

	@Override
	public String getYear() {
		return year.getValue(year.getSelectedIndex());
	}
	@Override
	public String getDepart() {
		return depart.getValue(depart.getSelectedIndex());
	}
	@Override
	public void initManageDepart(Map<String, String> map) {

		managedepart.clear();
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			managedepart.addItem(entry.getValue(), entry.getKey());
		}
	}
	@Override
	public void initYear(Map<String, String> map) {

		
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			year.addItem(entry.getValue(), entry.getKey());
		}
	}
	
	@Override
	public void initDepart(Map<String, String> map) {
		depart.clear();
		depart.addItem("选择", "");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			depart.addItem(entry.getValue(), entry.getKey());
		}
	}


	

	@Override
	public void setTotalCount(String text) {
		totalCount.setText(text);
		
	}
	@Override
	public void setRemainCount(String text) {
		remainCount.setText(text);
		
	}
	
	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);		

	}

	@Override
	public ListBox getPageNumber() {
		return pageNumber;
	}

	@Override
	public void setAddBtnShow() {
		addBtn.setVisible(false);
		
	}
	@Override
	public ListBox getManageDep() {
		return managedepart;
	}

	@Override
	public void setDisplay() {
	   this.managedepart.setVisible(false);
	   this.lbldep.setVisible(false);	
	   this.addBtn.setVisible(false);
	}
}
