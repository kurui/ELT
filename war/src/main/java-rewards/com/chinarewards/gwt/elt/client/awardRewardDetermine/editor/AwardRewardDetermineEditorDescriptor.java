/**
 * 
 */
package com.chinarewards.gwt.elt.client.awardRewardDetermine.editor;

import com.chinarewards.gwt.elt.client.awardRewardDetermine.plugin.AwardRewardDetermineConstants;
import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author nicho
 * @since 2011年12月9日 15:36:27
 */
public class AwardRewardDetermineEditorDescriptor implements EditorDescriptor {

	final Provider<AwardRewardDetermineEditor> editProvider;

	@Inject
	AwardRewardDetermineEditorDescriptor(Provider<AwardRewardDetermineEditor> editProvider) {
		this.editProvider = editProvider;
	}

	@Override
	public String getEditorId() {
		return AwardRewardDetermineConstants.EDITOR_AWARDREWARDDETERMINE_SEARCH;
	}

	@Override
	public Editor createEditor(String instanceId, Object model) {
		AwardRewardDetermineEditor e = editProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("颁奖");
		e.setModel(instanceId,model);
		return e;
	}

}
