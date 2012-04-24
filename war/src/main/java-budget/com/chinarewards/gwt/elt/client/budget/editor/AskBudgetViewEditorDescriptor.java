/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.editor;

import com.chinarewards.gwt.elt.client.budget.plugin.AskBudgetConstants;
import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AskBudgetViewEditorDescriptor implements EditorDescriptor {

	final Provider<AskBudgetViewEditor> editProvider;

	@Inject
	AskBudgetViewEditorDescriptor(Provider<AskBudgetViewEditor> editProvider) {
		this.editProvider = editProvider;
	}

	@Override
	public String getEditorId() {
		return AskBudgetConstants.EDITOR_VIEW_BUDGET;
	}

	@Override
	public Editor createEditor(String instanceId, Object model) {
		AskBudgetViewEditor e = editProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("预算申请");
		e.setModel(instanceId,model);
		return e;
	}
}
