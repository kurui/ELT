package com.chinarewards.gwt.elt.client.budget.view;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetAddPresenter.AskBudgetAddDisplay;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.team.plugin.TeamConstants;
import com.chinarewards.gwt.elt.client.view.OrganizationSpecialTextArea;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AskBudgetAddWidget extends Composite implements AskBudgetAddDisplay {

	// --------vo
	@UiField
	TextBox name;
	@UiField
	TextArea reason;
	@UiField
	TextBox askIntegral;
	@UiField
	ListBox department;
	@UiField
	ListBox year;
	@UiField
	Button back;

	@UiField
	Button save;
	
	@UiField
	Panel breadCrumbs;
	
	
	interface AskBudgetAddWidgetBinder extends UiBinder<Widget, AskBudgetAddWidget> {

	}

	private static AskBudgetAddWidgetBinder uiBinder = GWT
			.create(AskBudgetAddWidgetBinder.class);

	@Inject
	public AskBudgetAddWidget(DispatchAsync dispatch, ErrorHandler errorHandler,	SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));
		askIntegral.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (event.getValue() != null&& !event.getValue().equals("")) {
					try 
					{ 
					    Double.parseDouble(event.getValue()); 
					} 
					catch(Exception   e) 
					{ 
						askIntegral.setValue("");
					}
				} 
			}
		});
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void initEditAskBudget(AskBudgetClientVo AskBudgetVo,String instanceId) {//修改时显示得到的数据
		    name.setText(AskBudgetVo.getRecorduser());
			reason.setText(AskBudgetVo.getReason());
			
			if(instanceId.equals(TeamConstants.ACTION_TEAM_VIEW)){
				name.setEnabled(false);
				reason.setEnabled(false);
				save.setVisible(false);
				
				
			}
		
	}
	
	
	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
	}

	@Override
	public HasValue<String> getName() {
		return name;
	}
	public void setName(String text){
		this.name.setText(text);
	}
	@Override
	public HasValue<String> getJF() {
		return this.askIntegral;
	}
	
	
	@Override
	public String getYear() {
		return year.getValue(year.getSelectedIndex());
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

		department.addItem("选择", "");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			department.addItem(entry.getValue(), entry.getKey());
		}
	}
	@Override
	public HasValue<String> getReason() {
		return reason;
	}

	@Override
	public String getDepart() {
		return department.getValue(department.getSelectedIndex());
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
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	
	
}
