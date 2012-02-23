package com.chinarewards.gwt.elt.client.broadcastReplyLattice.view;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.broadcastReply.model.ReplyListClient;
import com.chinarewards.gwt.elt.client.broadcastReply.model.ReplyListCriteria;
import com.chinarewards.gwt.elt.client.broadcastReply.request.SearchBroadcastReplyRequest;
import com.chinarewards.gwt.elt.client.broadcastReply.request.SearchBroadcastReplyResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.util.DateTool;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class BroadcastReplyLatticeWidget extends Composite {

	@UiField
	InlineLabel deptName;
	@UiField
	InlineLabel staffName;
	@UiField
	InlineLabel content;
	@UiField
	InlineLabel createDate;
	@UiField
	InlineLabel createDept;
	@UiField
	Anchor replyNumber;
	@UiField
	Anchor myreply;
	@UiField
	Panel replyPanel;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	final Win win;
	final DispatchAsync dispatch;
	final SessionManager sessionManager;
	final String broadcastId;
	final BroadcastReplyLatticeWidget widget;
	private static BroadcastReplyLatticeWidgetUiBinder uiBinder = GWT
			.create(BroadcastReplyLatticeWidgetUiBinder.class);

	interface BroadcastReplyLatticeWidgetUiBinder extends
			UiBinder<Widget, BroadcastReplyLatticeWidget> {
	}

	public BroadcastReplyLatticeWidget(final Win win, final DispatchAsync dispatch,
			final SessionManager sessionManager, final String broadcastId, String deptName,
			String staffName, String content, String createDate,
			String createDept, final int replyNumber) {
		this.win=win;
		this.dispatch=dispatch;
		this.sessionManager=sessionManager;
		this.broadcastId=broadcastId;
		this.widget=this;
		initWidget(uiBinder.createAndBindUi(this));
		if (!StringUtil.isEmpty(deptName))
			this.deptName.setText(deptName);
		if (!StringUtil.isEmpty(staffName))
			this.staffName.setText(staffName);
		if (!StringUtil.isEmpty(content))
			this.content.setText(content);
		if (!StringUtil.isEmpty(createDate))
			this.createDate.setText(createDate);
		if (!StringUtil.isEmpty(createDept))
			this.createDept.setText(createDept);

		this.replyNumber.setText("回复(" + replyNumber + ")");

		myreply.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				replyPanel.clear();
				replyPanel.add(new MyReplyLatticeWidget(win, dispatch,
						sessionManager, null, broadcastId,replyNumber,widget));

			}
		});
		if (replyNumber != 0) {
			this.replyNumber.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					refWidget();
				}
			});
		}
	}

	void refWidget() {
		ReplyListCriteria cr = new ReplyListCriteria();
		cr.setBroadcastId(broadcastId);
		dispatch.execute(
				new SearchBroadcastReplyRequest(cr, sessionManager.getSession()),
				new AsyncCallback<SearchBroadcastReplyResponse>() {
					@Override
					public void onFailure(Throwable e) {
						Window.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchBroadcastReplyResponse response) {

						List<ReplyListClient> giftList = response.getResult();
						int index = 0;
						int tol = 10;
						if (response.getResult().size() < tol)
							tol = response.getResult().size();
						Grid grid = new Grid(tol, 1);

						// Add images to the grid
						int numRows = grid.getRowCount();
						int numColumns = grid.getColumnCount();
						for (int row = 0; row < numRows; row++) {
							for (int col = 0; col < numColumns; col++) {
								if (index < giftList.size()) {
									ReplyListClient clint = giftList.get(index);
									grid.setWidget(
											row,
											col,
											new ReplyLatticeWidget(
													clint.getReplyUserPhoto(),
													clint.getReplyUserName(),
													clint.getReplyContent(),
													DateTool.dateToStringChina2(clint
															.getReplyTime()))
													.asWidget());
									index++;
								} else {
									break;
									// grid.setWidget(row, col,new
									// BroadcastReplyLatticeWidget().asWidget());
								}
							}
						}

						// Return the panel
						grid.ensureDebugId("cwGrid");

						replyPanel.clear();
						replyPanel.add(grid);
					}

				});
	}

}
