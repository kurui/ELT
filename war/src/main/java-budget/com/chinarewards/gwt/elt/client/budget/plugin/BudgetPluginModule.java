package com.chinarewards.gwt.elt.client.budget.plugin;

import com.chinarewards.gwt.elt.client.budget.editor.AskBudgetAddEditor;
import com.chinarewards.gwt.elt.client.budget.editor.AskBudgetAddEditorDescriptor;
import com.chinarewards.gwt.elt.client.budget.editor.AskBudgetListEditor;
import com.chinarewards.gwt.elt.client.budget.editor.AskBudgetListEditorDescriptor;
import com.chinarewards.gwt.elt.client.budget.editor.AskBudgetViewEditor;
import com.chinarewards.gwt.elt.client.budget.editor.AskBudgetViewEditorDescriptor;
import com.chinarewards.gwt.elt.client.budget.editor.CreateBudgetEditor;
import com.chinarewards.gwt.elt.client.budget.editor.CreateBudgetEditorDescriptor;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * 
 * @author lw
 * @since 2010-12-16
 */
public class BudgetPluginModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(BudgetPluginDescriptor.class).in(Singleton.class);
    	// create
		bind(CreateBudgetEditorDescriptor.class).in(Singleton.class);
		bind(CreateBudgetEditor.class);

		bind(AskBudgetListPluginDescriptor.class).in(Singleton.class);
    	bind(AskBudgetListEditorDescriptor.class).in(Singleton.class);
		bind(AskBudgetListEditor.class);
		
		bind(AskBudgetAddPluginDescriptor.class).in(Singleton.class);
    	bind(AskBudgetAddEditorDescriptor.class).in(Singleton.class);
		bind(AskBudgetAddEditor.class);
		
		bind(AskBudgetViewPluginDescriptor.class).in(Singleton.class);
    	bind(AskBudgetViewEditorDescriptor.class).in(Singleton.class);
		bind(AskBudgetViewEditor.class);
	}

}
