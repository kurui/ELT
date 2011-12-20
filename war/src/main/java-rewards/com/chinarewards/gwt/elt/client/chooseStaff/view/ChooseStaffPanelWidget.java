package com.chinarewards.gwt.elt.client.chooseStaff.view;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.chooseStaff.presenter.ChooseStaffPanelPresenter.ChooseStaffPanelDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.view.OrganizationSpecialTextArea;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 选择员工
 * 
 * @author nicho
 * 
 */
public class ChooseStaffPanelWidget extends Composite implements ChooseStaffPanelDisplay {


	@UiField
	Button chooseBtn;

	@UiField
	Panel staffTextAreaPanel;
	@UiField HTMLPanel select1;

	SpecialTextArea<OrganicationClient> staffTextArea;

	/**
	 * 符合条件的员工集合 StaffReacher and DepartmentReacher
	 */
	// Map<String, OrganicationClient> staffMap = new HashMap<String,
	// OrganicationClient>();

	public ChooseStaffPanelWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	interface ChooseStaffPanelBinder extends
			UiBinder<Widget, ChooseStaffPanelWidget> {
	}

	private static ChooseStaffPanelBinder uiBinder = GWT
			.create(ChooseStaffPanelBinder.class);

	void init() {
		
		staffTextArea = new OrganizationSpecialTextArea();
		staffTextAreaPanel.add(staffTextArea);
		
	}
    
 
	/*
	 * @Override public Map<String, OrganicationClient> getStaffMap() { return
	 * staffMap; }
	 */
	@Override
	public SpecialTextArea<OrganicationClient> getSpecialTextArea() {
		return staffTextArea;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public List<String> getRealOrginzationIds() {
		List<String> realOrginzationIds = new ArrayList<String>();
		List<OrganicationClient> existKeys = staffTextArea.getItemList();
		for (OrganicationClient key : existKeys) {
			// if (staffMap.containsKey(key.getId())) {
			// OrganicationClient org = staffMap.get(key.getId());
			realOrginzationIds.add(key.getId());
			// }
		}
		return realOrginzationIds;
	}


	
	@Override
	public HasClickHandlers getChooseStaffBtnClick() {
		return chooseBtn;
	}


}
