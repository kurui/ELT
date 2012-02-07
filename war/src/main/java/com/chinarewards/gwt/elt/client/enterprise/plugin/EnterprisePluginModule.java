package com.chinarewards.gwt.elt.client.enterprise.plugin;

import com.chinarewards.gwt.elt.client.enterprise.editor.EnterpriseEditor;
import com.chinarewards.gwt.elt.client.enterprise.editor.EnterpriseEditorDescriptor;
import com.chinarewards.gwt.elt.client.enterprise.plugin.EnterprisePluginDescriptor;
import com.chinarewards.gwt.elt.client.sample2.Sample2Editor;
import com.chinarewards.gwt.elt.client.sample2.Sample2EditorDescriptor;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class EnterprisePluginModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EnterprisePluginDescriptor.class).in(Singleton.class);
		bind(EnterpriseEditorDescriptor.class).in(Singleton.class);
		bind(EnterpriseEditor.class);
		
		bind(Sample2EditorDescriptor.class).in(Singleton.class);
		bind(Sample2Editor.class);

		// // XXX testing only
		// bind(SessionManager.class).to(CookieSessionManager.class).in(
		// Singleton.class);
		// bind(LoginPresenter.class).to(LoginPresenterImpl.class);
		// bind(LoginDisplay.class).to(LoginWidget.class);
	}

}
