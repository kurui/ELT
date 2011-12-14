package com.chinarewards.gwt.elt.client.sample;

import com.chinarewards.gwt.elt.client.sample2.Sample2Editor;
import com.chinarewards.gwt.elt.client.sample2.Sample2EditorDescriptor;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class SamplePluginModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SamplePluginDescriptor.class).in(Singleton.class);
		bind(SampleEditorDescriptor.class).in(Singleton.class);
		bind(SampleEditor.class);
		
		bind(Sample2EditorDescriptor.class).in(Singleton.class);
		bind(Sample2Editor.class);

		// // XXX testing only
		// bind(SessionManager.class).to(CookieSessionManager.class).in(
		// Singleton.class);
		// bind(LoginPresenter.class).to(LoginPresenterImpl.class);
		// bind(LoginDisplay.class).to(LoginWidget.class);
	}

}
