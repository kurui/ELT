package com.chinarewards.gwt.elt.client.sample;

import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SampleEditorDescriptor implements EditorDescriptor {

	Provider<SampleEditor> editorProvider;

	@Inject
	SampleEditorDescriptor(Provider<SampleEditor> editorProvider) {
		// as new Instance of SampleEditor should be provided
		// so use provider instead
		this.editorProvider = editorProvider;
	}

	public Editor createEditor(String instanceId, Object model) {
		// as new Instance of SampleEditor should be provided
		// whether the instance is new or not is determined by the binder
		// if Singleton, then provider always return the same SampleEditor
		SampleEditor e = editorProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("企业注册");
		e.setModel(model);
		return e;
	}

	public String getEditorId() {
		return "sample.editor";
	}

}
