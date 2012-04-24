package com.chinarewards.gwt.elt.client.budget.editor;

import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetListPresenter;
import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;


public class AskBudgetListEditor extends AbstractEditor {

	final AskBudgetListPresenter askBudgetPresenter;
	Object model;

	@Inject
	protected AskBudgetListEditor(AskBudgetListEditorDescriptor editorDescriptor,
			AskBudgetListPresenter askBudgetPresenter) {
		super(editorDescriptor);
		this.askBudgetPresenter = askBudgetPresenter;
	}

	@Override
	public Widget asWidget() {
		return askBudgetPresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		askBudgetPresenter.unbind();
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
		
		askBudgetPresenter.bind();
		
	}
}
