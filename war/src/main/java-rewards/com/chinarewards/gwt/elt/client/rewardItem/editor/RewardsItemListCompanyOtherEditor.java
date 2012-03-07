package com.chinarewards.gwt.elt.client.rewardItem.editor;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemListCompanyPresenter;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsGridClient;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class RewardsItemListCompanyOtherEditor extends AbstractEditor {

	final RewardsItemListCompanyPresenter rewardsItemListPresenter;
	Object model;

	@Inject
	protected RewardsItemListCompanyOtherEditor(
			RewardsItemListCompanyOtherEditorDescriptor editorDescriptor,
			RewardsItemListCompanyPresenter rewardsItemListPresenter) {
		super(editorDescriptor);
		this.rewardsItemListPresenter = rewardsItemListPresenter;
	}

	@Override
	public Widget asWidget() {
		return rewardsItemListPresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		rewardsItemListPresenter.unbind();
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
		if(model!=null){
			if (model instanceof RewardsGridClient){
				RewardsGridClient rewardsGridClient = (RewardsGridClient) model;
				rewardsItemListPresenter.initEditor(rewardsGridClient);
			}		
		}
		
		rewardsItemListPresenter.bind();
	}
}
