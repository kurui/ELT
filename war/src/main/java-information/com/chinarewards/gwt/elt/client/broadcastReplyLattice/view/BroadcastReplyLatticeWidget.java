package com.chinarewards.gwt.elt.client.broadcastReplyLattice.view;

import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
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
	InlineLabel replyNumber;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);
	private static BroadcastReplyLatticeWidgetUiBinder uiBinder = GWT
			.create(BroadcastReplyLatticeWidgetUiBinder.class);

	interface BroadcastReplyLatticeWidgetUiBinder extends
			UiBinder<Widget, BroadcastReplyLatticeWidget> {
	}

	public BroadcastReplyLatticeWidget(String deptName, String staffName,
			String content, String createDate, String createDept,
			String replyNumber) {
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
		if (!StringUtil.isEmpty(replyNumber))
			this.replyNumber.setText(replyNumber);
	}

}
