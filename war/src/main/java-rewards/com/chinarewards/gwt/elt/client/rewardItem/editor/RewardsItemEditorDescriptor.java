/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewardItem.editor;

import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemConstants;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author lw
 * @since 2011年12月9日 15:36:27
 */
public class RewardsItemEditorDescriptor implements EditorDescriptor {

	final Provider<RewardsItemEditor> editProvider;

	@Inject
	RewardsItemEditorDescriptor(Provider<RewardsItemEditor> editProvider) {
		this.editProvider = editProvider;
	}

	@Override
	public String getEditorId() {
		return RewardsItemConstants.EDITOR_REWARDSITEM_SEARCH;
	}

	@Override
	public Editor createEditor(String instanceId, Object model) {
		RewardsItemEditor e = editProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("新建奖项");
		e.setModel(model);
		return e;
	}

}
