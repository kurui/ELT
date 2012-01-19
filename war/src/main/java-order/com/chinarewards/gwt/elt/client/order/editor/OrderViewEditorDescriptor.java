/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.editor;

import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.plugin.OrderConstants;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author yanrui
 * @since 2012年1月16日 
 */
public class OrderViewEditorDescriptor implements EditorDescriptor {

	final Provider<OrderViewEditor> editProvider;

	@Inject
	OrderViewEditorDescriptor(Provider<OrderViewEditor> editProvider) {
		this.editProvider = editProvider;
	}

	@Override
	public String getEditorId() {
		return OrderConstants.EDITOR_ORDER_VIEW;
	}

	@Override
	public Editor createEditor(String instanceId, Object model) {
		OrderViewEditor e = editProvider.get();
		e.setInstanceId(instanceId);
		
		String name = ((OrderVo) model).getName();
		String subName = name.length() > 6 ? name.substring(0, 6) : name;
		String title =subName+ "-详细" ;
		e.setTitle(title);
	
		e.setModel(instanceId,model);
		return e;
	}

}
