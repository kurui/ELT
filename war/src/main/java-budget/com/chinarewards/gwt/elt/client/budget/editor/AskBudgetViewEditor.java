package com.chinarewards.gwt.elt.client.budget.editor;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetViewPresenter;
import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;


public class AskBudgetViewEditor extends AbstractEditor {

	final AskBudgetViewPresenter askBudgetViewPresenter;
	Object model;

	@Inject
	protected AskBudgetViewEditor(AskBudgetViewEditorDescriptor editorDescriptor,
			AskBudgetViewPresenter askBudgetViewPresenter) {
		super(editorDescriptor);
		this.askBudgetViewPresenter = askBudgetViewPresenter;
	}

	@Override
	public Widget asWidget() {
		return askBudgetViewPresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		askBudgetViewPresenter.unbind();
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
				askBudgetViewPresenter.initEditor(instanceId,(AskBudgetClientVo) model);
			else
				askBudgetViewPresenter.initEditor(instanceId,null);
		}
		
		askBudgetViewPresenter.bind();
		
	}
}
