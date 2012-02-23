package com.chinarewards.gwt.elt.client.broadcastReplyLattice.view;

import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
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
	@UiField
	Anchor replyBtn;
	MyReplyShortLatticeWidget myshort;
	ReplyLatticeWidget mywidget;
	String replyParentId;
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);
	private static ReplyLatticeWidgetWidgetUiBinder uiBinder = GWT
			.create(ReplyLatticeWidgetWidgetUiBinder.class);

	interface ReplyLatticeWidgetWidgetUiBinder extends
			UiBinder<Widget, ReplyLatticeWidget> {
	}

	public ReplyLatticeWidget(MyReplyShortLatticeWidget myshort,String replyParentId, String photo,final String staffName,
			String content, String createDate) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mywidget=this;
		if (!StringUtil.isEmpty(photo))
			this.photo.setUrl("imageshow?imageName="+photo);
		if (!StringUtil.isEmpty(staffName))
			this.staffName.setText(staffName);
		if (!StringUtil.isEmpty(content))
			this.content.setText(content);
		if (!StringUtil.isEmpty(createDate))
			this.createDate.setText(createDate);
		if(myshort!=null)
			this.myshort=myshort;
		if(replyParentId!=null)
			this.replyParentId=replyParentId;
		if(this.myshort!=null)
		replyBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				mywidget.myshort.replyContent.setFocus(true);
				mywidget.myshort.replyContent.setValue("@"+staffName+" ");
				mywidget.myshort.replyParentId=mywidget.replyParentId;
			}
		});
	}

}
