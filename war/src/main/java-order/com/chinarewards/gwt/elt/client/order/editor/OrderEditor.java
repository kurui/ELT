package com.chinarewards.gwt.elt.client.order.editor;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.presenter.OrderPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since 2012年1月9日 17:25:16
 */
public class OrderEditor extends AbstractEditor {

	final OrderPresenter giftPresenter;
	Object model;

	@Inject
	protected OrderEditor(OrderEditorDescriptor editorDescriptor,
			OrderPresenter giftPresenter) {
		super(editorDescriptor);
		this.giftPresenter = giftPresenter;
	}

	@Override
	public Widget asWidget() {
		return giftPresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		giftPresenter.unbind();
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
		OrderVo giftClient = (OrderVo) model;
		giftPresenter
				.initEditor(giftClient.getId(), giftClient.getThisAction());
		giftPresenter.bind();
	}
}
