/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.editor;

import com.chinarewards.gwt.elt.client.budget.plugin.AskBudgetConstants;
import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.chinarewards.gwt.elt.model.rewards.RewardsPageClient;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AskBudgetListEditorDescriptor implements EditorDescriptor {

	final Provider<AskBudgetListEditor> editProvider;

	@Inject
	AskBudgetListEditorDescriptor(Provider<AskBudgetListEditor> editProvider) {
		this.editProvider = editProvider;
	}

	@Override
	public String getEditorId() {
		return AskBudgetConstants.EDITOR_LIST_BUDGET;
	}

	@Override
	public Editor createEditor(String instanceId, Object model) {
		AskBudgetListEditor e = editProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("预算申请列表");
		if (model instanceof RewardsPageClient) {
			if (model != null)
				e.setTitle(((RewardsPageClient) model).getTitleName());
		}
		
		e.setModel(model);
		return e;
	}

}
