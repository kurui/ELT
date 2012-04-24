package com.chinarewards.gwt.elt.client.budget.editor;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetAddPresenter;
import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;


public class AskBudgetAddEditor extends AbstractEditor {

	final AskBudgetAddPresenter askBudgetAddPresenter;
	Object model;

	@Inject
	protected AskBudgetAddEditor(AskBudgetAddEditorDescriptor editorDescriptor,
			AskBudgetAddPresenter askBudgetAddPresenter) {
		super(editorDescriptor);
		this.askBudgetAddPresenter = askBudgetAddPresenter;
	}

	@Override
	public Widget asWidget() {
		return askBudgetAddPresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		askBudgetAddPresenter.unbind();
		return true;
	}
	
	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void save() {

	}

	public void setModel(String instanceId,Object model) {
		this.model = model;
		if (model != null) {
			if (model instanceof AskBudgetClientVo)
				askBudgetAddPresenter.initEditor(instanceId,(AskBudgetClientVo) model);
			else
				askBudgetAddPresenter.initEditor(instanceId,null);
		}
		
		askBudgetAddPresenter.bind();
		
	}
}
