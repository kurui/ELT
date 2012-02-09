package com.chinarewards.gwt.elt.client.budget.presenter;

import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.user.client.ui.Widget;


public interface CorpBudgetPresenter extends Presenter<CorpBudgetPresenter.CorpBudgetDisplay> {

	public static interface CorpBudgetDisplay extends Display {


		public void clear();

		public void initEditCorpBudget(CorpBudgetVo corpBudgetVo);


		void setBreadCrumbs(Widget breadCrumbs);

	}

	public void initEditor(String coppBudgetId, String thisAction);
}
