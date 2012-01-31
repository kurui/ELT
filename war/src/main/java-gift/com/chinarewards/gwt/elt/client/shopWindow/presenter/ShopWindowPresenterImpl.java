package com.chinarewards.gwt.elt.client.shopWindow.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.awardShopLattice.view.AwardShopLatticeWidget;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.shopWindow.presenter.ShopWindowPresenter.ShopWindowDisplay;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.user.client.ui.Grid;
import com.google.inject.Inject;

public class ShopWindowPresenterImpl extends BasePresenter<ShopWindowDisplay>
		implements ShopWindowPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	@Inject
	public ShopWindowPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			ShopWindowDisplay display, Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;

	}

	@Override
	public void bind() {
		init();

	}

	private void init() {

		Grid grid = new Grid(2, 3);

		// Add images to the grid
		int numRows = grid.getRowCount();
		int numColumns = grid.getColumnCount();
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				// grid.setWidget(row, col,new
				// AwardShopLatticeWidget(clint.getName(),clint.getIntegral()+"",clint.getIndate()+"",clint.getPhoto(),clint.getId()).asWidget());
				grid.setWidget(row, col, new AwardShopLatticeWidget("无数据",
						"无数据", "无数据", "无数据", "无数据").asWidget());

			}
		}

		// Return the panel
		grid.ensureDebugId("cwGrid");

		display.getResultPanel().clear();
		display.getResultPanel().add(grid);

	}

}
