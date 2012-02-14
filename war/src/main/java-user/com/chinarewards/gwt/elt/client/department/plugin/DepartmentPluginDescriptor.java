/**
 * 
 */
package com.chinarewards.gwt.elt.client.department.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.chinarewards.gwt.elt.client.department.editor.DepartmentEditorDescriptor;
import com.chinarewards.gwt.elt.client.department.model.DepartmentClient;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since
 */
public class DepartmentPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final DepartmentPlugin departmentPlugin;
	final DepartmentEditorDescriptor giftEditorDescriptor;

	@Inject
	public DepartmentPluginDescriptor(final DepartmentEditorDescriptor giftEditorDescriptor) {
		this.giftEditorDescriptor = giftEditorDescriptor;
		departmentPlugin = new DepartmentPlugin(this);

		/**
		 * 新建礼品
		 */
		ext.add(new Extension() {
			@Override
			public String getExtensionPointId() {
				return PluginConstants.MENU;
			}

			@Override
			public Object getInstance() {
				return new MenuItem() {
					@Override
					public int getOrder() {
//						return MenuConstants.MENU_ORDER_DEPARTMENT_ADD;
						return 0;
					}

					@Override
					public String getMenuId() {
						return DepartmentConstants.MENU_DEPARTMENT_ADD;
					}

					@Override
					public String getParentMenuId() {
						return null;
					}

					@Override
					public String getTitle() {
						return "新建礼品";
					}

					@Override
					public void execute() {
						DepartmentClient giftClient = new DepartmentClient();
						giftClient.setThisAction(DepartmentConstants.ACTION_DEPARTMENT_ADD);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(DepartmentConstants.EDITOR_DEPARTMENT_EDIT,
										DepartmentConstants.ACTION_DEPARTMENT_ADD,
										giftClient);
					}

					@Override
					public Image getIcon() {
						return null;
					}

				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return DepartmentPluginDescriptor.this;
			}
		});

		
		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return giftEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return DepartmentPluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return DepartmentConstants.PLUGIN_DEPARTMENT;
	}

	@Override
	public Plugin getInstance() {
		return departmentPlugin;
	}

	@Override
	public Set<ExtensionPoint> getExtensionPoints() {
		return null;
	}

	@Override
	public Set<Extension> getExtensions() {
		return ext;
	}

}
