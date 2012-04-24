package com.chinarewards.gwt.elt.client.budget.presenter;
import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public interface AskBudgetViewPresenter extends Presenter<AskBudgetViewPresenter.AskBudgetViewDisplay> {

	public void initEditor(String instanceId,AskBudgetClientVo vo);
	public static interface AskBudgetViewDisplay extends Display {
		public void setUser(String text);
		HasValue<String> getSPJF();
	    HasValue<String> getView();
		public HasClickHandlers getSaveOKClick();
		public HasClickHandlers getSaveNOClick();
     	public void initEditAskBudget(AskBudgetClientVo askBudgetClientVo,String instanceId);
		public HasClickHandlers getBackClick();
		void setBreadCrumbs(Widget breadCrumbs);
		
	}

}
