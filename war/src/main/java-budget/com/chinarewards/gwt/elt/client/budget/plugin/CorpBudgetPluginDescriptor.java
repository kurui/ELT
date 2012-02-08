/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.budget.editor.CorpBudgetEditorDescriptor;
import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.chinarewards.gwt.elt.client.plugin.MenuConstants;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since
 */
public class CorpBudgetPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final CorpBudgetPlugin CorpBudgetPlugin;
	final CorpBudgetEditorDescriptor giftEditorDescriptor;

	@Inject
	public CorpBudgetPluginDescriptor(
			final CorpBudgetEditorDescriptor giftEditorDescriptor) {
		this.giftEditorDescriptor = giftEditorDescriptor;
		CorpBudgetPlugin = new CorpBudgetPlugin(this);

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
						return MenuConstants.MENU_ORDER_GIFT_ADD;
					}

					@Override
					public String getMenuId() {
						return CorpBudgetConstants.MENU_GIFT_ADD;
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
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										CorpBudgetConstants.EDITOR_GIFT_EDIT,
										CorpBudgetConstants.ACTION_GIFT_ADD,
										null);
					}

					@Override
					public Image getIcon() {
						return null;
					}

				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return CorpBudgetPluginDescriptor.this;
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
				return CorpBudgetPluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return CorpBudgetConstants.PLUGIN_GIFT;
	}

	@Override
	public Plugin getInstance() {
		return CorpBudgetPlugin;
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
