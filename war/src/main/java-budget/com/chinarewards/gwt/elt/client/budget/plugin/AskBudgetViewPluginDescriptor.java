package com.chinarewards.gwt.elt.client.budget.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.budget.editor.AskBudgetViewEditorDescriptor;
import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.inject.Inject;

/**
 * 
 * @author harry
 * @since 2010-12-16
 */
public class AskBudgetViewPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final AskBudgetViewPlugin budgetPlugin;
	final AskBudgetViewEditorDescriptor askBudgetViewEditorDescriptor;


	@Inject
	public AskBudgetViewPluginDescriptor(	final AskBudgetViewEditorDescriptor askBudgetViewEditorDescriptor	) {
		this.askBudgetViewEditorDescriptor = askBudgetViewEditorDescriptor;
		budgetPlugin = new AskBudgetViewPlugin(this);

		// create budget menu
////		ext.View(new Extension() {
////
////			@Override
////			public String getExtensionPointId() {
////				return PluginConstants.MENU;
////			}
////
////			@Override
////			public Object getInstance() {
////				return new MenuItem() {
////
////					@Override
////					public String getTitle() {
////						return "预算申请";
////					}
////
////					@Override
////					public String getParentMenuId() {
////						return "root";
////					}
////
////					@Override
////					public int getOrder() {
////						return MenuConstants.MENU_ORDER_LIST_ASKBUDGET;
////					}
////
////					@Override
////					public String getMenuId() {
////						return AskBudgetConstants.MENU_LIST_BUDGET;
////					}
////
////					@Override
////					public Image getIcon() {
////						return null;
////					}
////
////					@Override
////					public void execute() {
////						Platform.getInstance()
////								.getEditorRegistry()
////								.openEditor(
////										AskBudgetConstants.EDITOR_LIST_BUDGET,
////										"EDITOR_LIST_BUDGET_ID", null);
////					}
////				};
////			}
//
//			@Override
//			public PluginDescriptor getPluginDescriptor() {
//				return AskBudgetViewPluginDescriptor.this;
//			}
//
//		});

		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return askBudgetViewEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return AskBudgetViewPluginDescriptor.this;
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
