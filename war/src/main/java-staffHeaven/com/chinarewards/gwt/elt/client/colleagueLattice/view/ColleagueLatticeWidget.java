package com.chinarewards.gwt.elt.client.colleagueLattice.view;

import com.chinarewards.gwt.elt.client.colleagueParticular.plugin.ColleagueParticularConstants;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class ColleagueLatticeWidget extends Composite {

	@UiField
	Image photo;
	@UiField
	Anchor staffName;
	@UiField
	InlineLabel deptName;
	private static ColleagueLatticeWidgetUiBinder uiBinder = GWT
			.create(ColleagueLatticeWidgetUiBinder.class);

	interface ColleagueLatticeWidgetUiBinder extends
			UiBinder<Widget, ColleagueLatticeWidget> {
	}

	public ColleagueLatticeWidget(final String staffId,String staffName,String deptName,String photo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.staffName.setText(staffName);
		this.deptName.setText(deptName);
		this.photo.setUrl("imageshow?imageName="+photo);
		
		this.staffName.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Platform.getInstance()
				.getEditorRegistry()
				.openEditor(
						ColleagueParticularConstants.EDITOR_COLLEAGUEPARTICULAR_SEARCH,
						"EDITOR_COLLEAGUEPARTICULAR_SEARCH_DO_ID", staffId);
				
			}
		});
	}

}
