package com.chinarewards.gwt.elt.client.sample;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

public class SamplePluginDescriptor implements PluginDescriptor {

	final static Set<Extension> extensions = new HashSet<Extension>();

	final static String PLUGIN_ID = "sample";

	final SamplePlugin plugin;

	final SampleEditorDescriptor descriptor;



	@Inject
	public SamplePluginDescriptor(final SampleEditorDescriptor editorDesc) {

		this.descriptor = editorDesc;
	

		plugin = new SamplePlugin(this);

		extensions.add(new Extension() {

			public String getExtensionPointId() {
				return "core.menu";
			}

			public Object getInstance() {
				return new MenuItem() {

					public void execute() {
					//	Window.alert("i am sample1");
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor("sample.editor",
										"SampleInstanceID", null);
					}

					public Image getIcon() {
						return null;
					}

					public String getMenuId() {
						return "sample";
					}

					public int getOrder() {
						return 0;
					}

					public String getParentMenuId() {
						return null;
					}

					public String getTitle() {
						return "企业注册";
					}

				};
			}

			public PluginDescriptor getPluginDescriptor() {
				return SamplePluginDescriptor.this;
			}

		});

		extensions.add(new Extension() {

			public String getExtensionPointId() {
				return "core.editor";
			}

			public Object getInstance() {
				return descriptor;
			}

			public PluginDescriptor getPluginDescriptor() {
				return SamplePluginDescriptor.this;
			}

		});
//
//		extensions.add(new Extension() {
//
//			public String getExtensionPointId() {
//				return "core.menu";
//			}
//
//			public Object getInstance() {
//				return new MenuItem() {
//
//					public void execute() {
//						Window.alert("i am sample2");
//						Platform.getInstance()
//								.getEditorRegistry()
//								.openEditor("sample2.editor",
//										"Sample2InstanceID", null);
//					}
//
//					public Image getIcon() {
//						return null;
//					}
//
//					public String getMenuId() {
//						return "sample2";
//					}
//
//					public int getOrder() {
//						return 0;
//					}
//
//					public String getParentMenuId() {
//						return "root.sample";
//					}
//
//					public String getTitle() {
//						return "Sample Function22222";
//					}
//
//				};
//			}
//
//			public PluginDescriptor getPluginDescriptor() {
//				return SamplePluginDescriptor.this;
//			}
//
//		});
//
//		extensions.add(new Extension() {
//
//			public String getExtensionPointId() {
//				return "core.editor";
//			}
//
//			public Object getInstance() {
//				return descriptor2;
//			}
//
//			public PluginDescriptor getPluginDescriptor() {
//				return SamplePluginDescriptor.this;
//			}
//
//		});
	}

	public Set<ExtensionPoint> getExtensionPoints() {
		return null;
	}

	public Set<Extension> getExtensions() {
		return extensions;
	}

	public Plugin getInstance() {
		return plugin;
	}

	public String getPluginId() {
		return PLUGIN_ID;
	}

}
