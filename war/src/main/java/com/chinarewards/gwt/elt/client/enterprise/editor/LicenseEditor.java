package com.chinarewards.gwt.elt.client.enterprise.editor;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.enterprise.presenter.LicensePresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class LicenseEditor extends AbstractEditor {

	Object model;
	LicensePresenter enterprisePresenter;

	@Inject
	protected LicenseEditor(
			LicenseEditorDescriptor editorDescriptor,
			LicensePresenter enterprisePresenter) {
		super(editorDescriptor);
		this.enterprisePresenter = enterprisePresenter;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void save() {
	}

	public Widget asWidget() {
		return enterprisePresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		return true;
	}

	public void setModel(Object model) {
		this.model = model;
		EnterpriseVo enterpriseVo = (EnterpriseVo) model;
		enterprisePresenter.initEditor(enterpriseVo.getId());
		enterprisePresenter.bind();
	}

}
