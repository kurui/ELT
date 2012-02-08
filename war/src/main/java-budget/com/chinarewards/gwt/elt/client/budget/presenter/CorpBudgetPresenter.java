package com.chinarewards.gwt.elt.client.budget.presenter;

import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public interface CorpBudgetPresenter extends Presenter<CorpBudgetPresenter.CorpBudgetDisplay> {

	public static interface CorpBudgetDisplay extends Display {

		public HasValue<String> getName();
	
		public HasValue<Boolean> getStatus();

		public HasValue<Boolean> getDeleted();

		
		public HasClickHandlers getUploadClick();

		public HasClickHandlers getSaveClick();

		public void clear();

		public void initEditCorpBudget(CorpBudgetVo giftVo);

		public HasClickHandlers getBackClick();

		void setBreadCrumbs(Widget breadCrumbs);

	}

	public void initEditor(String giftId, String thisAction);
}
