package com.chinarewards.gwt.elt.client.awardRewardDetermine.editor;

import com.chinarewards.gwt.elt.client.awardRewardDetermine.presenter.AwardRewardDeterminePresenter;
import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsClient;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author nicho
 * @since 2011年12月9日 15:36:15
 */
public class AwardRewardDetermineEditor extends AbstractEditor {

	final AwardRewardDeterminePresenter awardRewardDeterminePresenter;
	Object model;

	@Inject
	protected AwardRewardDetermineEditor(AwardRewardDetermineEditorDescriptor editorDescriptor,
			AwardRewardDeterminePresenter awardRewardDeterminePresenter) {
		super(editorDescriptor);
		this.awardRewardDeterminePresenter = awardRewardDeterminePresenter;
	}

	@Override
	public Widget asWidget() {
		return awardRewardDeterminePresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		awardRewardDeterminePresenter.unbind();
		return true;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void save() {

	}

	public void setModel(String instanceId, Object model) {
		if (model instanceof RewardsClient) {
			awardRewardDeterminePresenter.initReward(((RewardsClient) model).getId(),instanceId,((RewardsClient) model).getHeadcountLimit());
		}
		awardRewardDeterminePresenter.bind();
	}
}
