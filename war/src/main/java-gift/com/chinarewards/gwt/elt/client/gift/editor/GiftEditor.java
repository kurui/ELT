package com.chinarewards.gwt.elt.client.gift.editor;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since 2012年1月9日 17:25:16
 */
public class GiftEditor extends AbstractEditor {

	final GiftPresenter giftPresenter;
	Object model;

	@Inject
	protected GiftEditor(GiftListEditorDescriptor editorDescriptor,
			GiftPresenter giftPresenter) {
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
		giftPresenter.bind();
	}
}
