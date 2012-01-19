package com.chinarewards.gwt.elt.client.order.editor;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.presenter.OrderViewPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class OrderViewEditor extends AbstractEditor {

	final OrderViewPresenter orderViewPresenter;
	Object model;

	@Inject
	protected OrderViewEditor(OrderViewEditorDescriptor editorDescriptor,
			OrderViewPresenter orderViewPresenter) {
		super(editorDescriptor);
		this.orderViewPresenter = orderViewPresenter;
	}

	@Override
	public Widget asWidget() {
		return orderViewPresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		orderViewPresenter.unbind();
		return true;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void save() {

	}

	public void setModel(String instanceId, Object model) {
		orderViewPresenter.bind();
		if (model != null) {
			orderViewPresenter.initInstanceId(instanceId, (OrderVo) model);

		}
	}
}