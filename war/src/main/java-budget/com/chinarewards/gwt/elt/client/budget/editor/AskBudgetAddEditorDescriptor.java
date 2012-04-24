/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.editor;

import com.chinarewards.gwt.elt.client.budget.plugin.AskBudgetConstants;
import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AskBudgetAddEditorDescriptor implements EditorDescriptor {

	final Provider<AskBudgetAddEditor> editProvider;

	@Inject
	AskBudgetAddEditorDescriptor(Provider<AskBudgetAddEditor> editProvider) {
		this.editProvider = editProvider;
	}

	@Override
	public String getEditorId() {
		return AskBudgetConstants.EDITOR_ADD_BUDGET;
	}

	@Override
	public Editor createEditor(String instanceId, Object model) {
		AskBudgetAddEditor e = editProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("增加申请");
		e.setModel(instanceId,model);
		return e;
	}
}
