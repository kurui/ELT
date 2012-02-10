package com.chinarewards.gwt.elt.client.integralManagement.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chinarewards.gwt.elt.client.integralManagement.model.ContactDatabase;
import com.chinarewards.gwt.elt.client.integralManagement.model.ContactDatabase.ContactInfo;
import com.chinarewards.gwt.elt.client.integralManagement.model.ContactTreeViewModel;
import com.chinarewards.gwt.elt.client.integralManagement.presenter.IntegralManagementPresenter.IntegralManagementDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

public class IntegralManagementWidget extends Composite implements
		IntegralManagementDisplay {

	  @UiField(provided = true)
	  CellTree cellTree;


	  @UiField
	  Label selectedLabel;
	private static IntegralManagementWidgetUiBinder uiBinder = GWT
			.create(IntegralManagementWidgetUiBinder.class);

	interface IntegralManagementWidgetUiBinder extends
			UiBinder<Widget, IntegralManagementWidget> {
	}

	public IntegralManagementWidget() {
	
		
	    final MultiSelectionModel<ContactInfo> selectionModel =
	    	      new MultiSelectionModel<ContactInfo>(ContactDatabase.ContactInfo.KEY_PROVIDER);
	    	    selectionModel.addSelectionChangeHandler(
	    	        new SelectionChangeEvent.Handler() {
	    	          public void onSelectionChange(SelectionChangeEvent event) {
	    	            StringBuilder sb = new StringBuilder();
	    	            boolean first = true;
	    	            List<ContactInfo> selected = new ArrayList<ContactInfo>(
	    	                selectionModel.getSelectedSet());
	    	            Collections.sort(selected);
	    	            for (ContactInfo value : selected) {
	    	              if (first) {
	    	                first = false;
	    	              } else {
	    	                sb.append(", ");
	    	              }
	    	              sb.append(value.getFullName());
	    	            }
	    	            selectedLabel.setText(sb.toString());
	    	          }
	    	        });

	    	    CellTree.Resources res = GWT.create(CellTree.BasicResources.class);
	    	    cellTree = new CellTree(
	    	        new ContactTreeViewModel(selectionModel), null, res);
	    	    cellTree.setAnimationEnabled(true);
	    		initWidget(uiBinder.createAndBindUi(this));
	}

	
	



}
