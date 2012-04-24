package com.chinarewards.gwt.elt.client.budget.view;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.plugin.AskBudgetConstants;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetViewPresenter.AskBudgetViewDisplay;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.DateTool;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AskBudgetViewWidget extends Composite implements AskBudgetViewDisplay {

	// --------vo
	@UiField
	Label name;
	@UiField
	Label reason;
	@UiField
	Label askIntegral;
	@UiField
	Label department;
	@UiField
	Label year;
	@UiField
	Label date;
	@UiField
	TextBox approveUser;
	@UiField
	TextArea view;
	@UiField
	TextBox approveIntegeral;
	@UiField
	Button back;
    
	@UiField
	Button saveOK;
	@UiField
	Button saveNO;
	@UiField
	Panel breadCrumbs;
	
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface AskBudgetViewWidgetBinder extends UiBinder<Widget, AskBudgetViewWidget> {

	}

	private static AskBudgetViewWidgetBinder uiBinder = GWT
			.create(AskBudgetViewWidgetBinder.class);

	@Inject
	public AskBudgetViewWidget(DispatchAsync dispatch, ErrorHandler errorHandler,	SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));
		approveIntegeral.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (event.getValue() != null&& !event.getValue().equals("")) {
					try 
					{ 
					    Double.parseDouble(event.getValue()); 
					} 
					catch(Exception   e) 
					{ 
						approveIntegeral.setValue("");
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
		    this.name.setText(AskBudgetVo.getRecorduser());
		    this.reason.setText(AskBudgetVo.getReason());
			this.date.setText(DateTool.dateToString(AskBudgetVo.getRecorddate()));
			this.approveIntegeral.setText(AskBudgetVo.getApproveIntegeral()+"");
           
			this.askIntegral.setText(AskBudgetVo.getAskIntegral()+"");
			this.view.setText(AskBudgetVo.getView());
			this.year.setText(AskBudgetVo.getCorpBudget());
			this.department.setText(AskBudgetVo.getDepName());
			if(instanceId.equals(AskBudgetConstants.ACTION_ASKBUDGET_VIEW)){
				this.approveUser.setText(AskBudgetVo.getApproveuser());
				this.approveIntegeral.setEnabled(false);
				this.view.setEnabled(false);
				saveOK.setVisible(false);
				saveNO.setVisible(false);
				
			}
		
	}
	
	
	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
	}

	
	public void setUser(String text){
		this.approveUser.setText(text);
	}
	@Override
	public HasValue<String> getSPJF() {
		return this.approveIntegeral;
	}
	
	
	
	@Override
	public HasValue<String> getView() {
		return view;
	}

	
	
	@Override
	public HasClickHandlers getSaveOKClick() {
		return saveOK;
	}
	@Override
	public HasClickHandlers getSaveNOClick() {
		return saveNO;
	}
	@Override
	public HasClickHandlers getBackClick() {
		return back;
	}

		
	
}
