package com.chinarewards.gwt.elt.client.budget.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.budget.editor.AskBudgetListEditorDescriptor;
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
 * 
 * @author harry
 * @since 2010-12-16
 */
public class AskBudgetListPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final AskBudgetListPlugin budgetPlugin;
	final AskBudgetListEditorDescriptor askBudgetListEditorDescriptor;


	@Inject
	public AskBudgetListPluginDescriptor(	final AskBudgetListEditorDescriptor askBudgetListEditorDescriptor	) {
		this.askBudgetListEditorDescriptor = askBudgetListEditorDescriptor;
		budgetPlugin = new AskBudgetListPlugin(this);

		// create budget menu
		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.MENU;
			}

			@Override
			public Object getInstance() {
				return new MenuItem() {

					@Override
					public String getTitle() {
						return "预算申请";
					}

					@Override
					public String getParentMenuId() {
						return "root";
					}

					@Override
					public int getOrder() {
						return MenuConstants.MENU_ORDER_LIST_ASKBUDGET;
					}

					@Override
					public String getMenuId() {
						return AskBudgetConstants.MENU_LIST_BUDGET;
					}

					@Override
					public Image getIcon() {
						return null;
					}

					@Override
					public void execute() {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										AskBudgetConstants.EDITOR_LIST_BUDGET,
										"EDITOR_LIST_BUDGET_ID", null);
					}
				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return AskBudgetListPluginDescriptor.this;
			}

		});

		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return askBudgetListEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return AskBudgetListPluginDescriptor.this;
			}

		});

		

		
	}

	@Override
	public String getPluginId() {
		return AskBudgetConstants.PLUGIN_BUDGET;
	}

	@Override
	public Plugin getInstance() {
		return budgetPlugin;
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
