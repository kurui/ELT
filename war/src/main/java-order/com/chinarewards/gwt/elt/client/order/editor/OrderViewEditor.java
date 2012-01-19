package com.chinarewards.gwt.elt.client.order.editor;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.presenter.OrderViewPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since 2012年1月16日 
 */
public class OrderViewEditor extends AbstractEditor {

	final OrderViewPresenter giftViewPresenter;
	Object model;

	@Inject
	protected OrderViewEditor(OrderViewEditorDescriptor editorDescriptor,
			OrderViewPresenter giftViewPresenter) {
		super(editorDescriptor);
		this.giftViewPresenter = giftViewPresenter;
	}

	@Override
	public Widget asWidget() {
		return giftViewPresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		giftViewPresenter.unbind();
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
		giftViewPresenter.bind();
		if (model != null) {
			giftViewPresenter.initInstanceId(instanceId, (OrderVo) model);

		}
	}
}
