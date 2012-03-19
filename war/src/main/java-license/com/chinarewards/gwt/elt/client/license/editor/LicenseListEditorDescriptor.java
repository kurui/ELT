
package com.chinarewards.gwt.elt.client.license.editor;

import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.chinarewards.gwt.elt.client.license.plugin.LicenseListConstants;
import com.chinarewards.gwt.elt.model.rewards.RewardsPageClient;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author yanrui
 */
public class LicenseListEditorDescriptor implements EditorDescriptor {

	final Provider<LicenseListEditor> editProvider;

	@Inject
	LicenseListEditorDescriptor(Provider<LicenseListEditor> editProvider) {
		this.editProvider = editProvider;
	}

	@Override
	public String getEditorId() {
		return LicenseListConstants.EDITOR_LICENSELIST_SEARCH;
	}

	@Override
	public Editor createEditor(String instanceId, Object model) {
		LicenseListEditor e = editProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("授权证书列表");
		if (model instanceof RewardsPageClient) {
			if (model != null)
				e.setTitle(((RewardsPageClient) model).getTitleName());
		}
		e.setModel(model);
		return e;
	}

}
