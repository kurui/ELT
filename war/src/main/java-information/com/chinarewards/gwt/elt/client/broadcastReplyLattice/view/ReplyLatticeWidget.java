package com.chinarewards.gwt.elt.client.broadcastReplyLattice.view;

import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class ReplyLatticeWidget extends Composite {


	@UiField
	InlineLabel staffName;
	@UiField
	InlineLabel content;
	@UiField
	InlineLabel createDate;
	@UiField
	Image photo;
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);
	private static ReplyLatticeWidgetWidgetUiBinder uiBinder = GWT
			.create(ReplyLatticeWidgetWidgetUiBinder.class);

	interface ReplyLatticeWidgetWidgetUiBinder extends
			UiBinder<Widget, ReplyLatticeWidget> {
	}

	public ReplyLatticeWidget(String photo,String staffName,
			String content, String createDate) {
		initWidget(uiBinder.createAndBindUi(this));
		if (!StringUtil.isEmpty(photo))
			this.photo.setUrl("imageshow?imageName="+photo);
		if (!StringUtil.isEmpty(staffName))
			this.staffName.setText(staffName);
		if (!StringUtil.isEmpty(content))
			this.content.setText(content);
		if (!StringUtil.isEmpty(createDate))
			this.createDate.setText(createDate);

	}

}
