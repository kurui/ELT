package com.chinarewards.gwt.elt.client.budget.presenter;


import java.util.Map;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.client.team.model.TeamVo;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface AskBudgetAddPresenter extends Presenter<AskBudgetAddPresenter.AskBudgetAddDisplay> {

	public void initEditor(String instanceId,AskBudgetClientVo vo);
	public static interface AskBudgetAddDisplay extends Display {

		public HasValue<String> getName();
		public void setName(String text);
		public HasValue<String> getReason();
		HasValue<String> getJF();
		public HasClickHandlers getSaveClick();
        String getYear();
		
		public void initYear(Map<String, String> map);
		public void clear();
		public void initDepart(Map<String, String> map);
		String getDepart();
		public void initEditAskBudget(AskBudgetClientVo askBudgetClientVo,String instanceId);
		public HasClickHandlers getBackClick();

		void setBreadCrumbs(Widget breadCrumbs);
		
	}

}
