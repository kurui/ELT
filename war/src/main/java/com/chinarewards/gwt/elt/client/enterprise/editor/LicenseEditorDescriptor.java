package com.chinarewards.gwt.elt.client.enterprise.editor;

import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.chinarewards.gwt.elt.client.enterprise.plugin.EnterpriseConstants;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class LicenseEditorDescriptor implements EditorDescriptor {

	Provider<LicenseEditor> editorProvider;

	@Inject
	LicenseEditorDescriptor(Provider<LicenseEditor> editorProvider) {
		// as new Instance of LicenseEditor should be provided
		// so use provider instead
		this.editorProvider = editorProvider;
	}

	public Editor createEditor(String instanceId, Object model) {
		LicenseEditor e = editorProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("授权信息");
		e.setModel(model);
		return e;
	}

	public String getEditorId() {
		return EnterpriseConstants.EDITOR_LICENSE_EDIT;
	}

}
