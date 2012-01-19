package com.chinarewards.gwt.elt.client.order.editor;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.presenter.OrderPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class OrderEditor extends AbstractEditor {

	final OrderPresenter orderPresenter;
	Object model;

	@Inject
	protected OrderEditor(OrderEditorDescriptor editorDescriptor,
			OrderPresenter orderPresenter) {
		super(editorDescriptor);
		this.orderPresenter = orderPresenter;
	}

	@Override
	public Widget asWidget() {
		return orderPresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		orderPresenter.unbind();
		return true;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void save() {

	}

	public void setModel(Object model) {
		this.model = model;
		OrderVo orderClient = (OrderVo) model;
		orderPresenter
				.initEditor(orderClient.getId(), orderClient.getThisAction());
		orderPresenter.bind();
	}
}
