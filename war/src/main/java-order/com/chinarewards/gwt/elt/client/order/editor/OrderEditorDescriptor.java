/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.editor;

import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.chinarewards.gwt.elt.client.order.plugin.OrderConstants;
import com.chinarewards.gwt.elt.model.rewards.RewardsPageClient;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author yanrui
 * @since 2012年1月9日 17:25:09
 */
public class OrderEditorDescriptor implements EditorDescriptor {

	final Provider<OrderEditor> editProvider;

	@Inject
	OrderEditorDescriptor(Provider<OrderEditor> editProvider) {
		this.editProvider = editProvider;
	}

	@Override
	public String getEditorId() {
		return OrderConstants.EDITOR_ORDER_EDIT;
	}

	@Override
	public Editor createEditor(String instanceId, Object model) {
		OrderEditor e = editProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("新建订单");
		if (model instanceof RewardsPageClient) {
			if (model != null)
				e.setTitle(((RewardsPageClient) model).getTitleName());
		}
		e.setModel(model);
		return e;
	}

}
