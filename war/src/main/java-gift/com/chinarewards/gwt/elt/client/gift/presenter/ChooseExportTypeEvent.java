package com.chinarewards.gwt.elt.client.gift.presenter;

import com.google.gwt.event.shared.GwtEvent;

public class ChooseExportTypeEvent extends GwtEvent<ChooseExportTypeHandler> {

	private static Type<ChooseExportTypeHandler> TYPE = new Type<ChooseExportTypeHandler>();

	String exportFileType = "";

	public ChooseExportTypeEvent() {

	}

	public ChooseExportTypeEvent(String exportFileType) {
		this.exportFileType = exportFileType;
	}

	public static Type<ChooseExportTypeHandler> getType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChooseExportTypeHandler handler) {
		handler.chooseExportType(exportFileType);
	}

	@Override
	public Type<ChooseExportTypeHandler> getAssociatedType() {
		return getType();
	}
}
