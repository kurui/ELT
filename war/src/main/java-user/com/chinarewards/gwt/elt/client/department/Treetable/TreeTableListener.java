package com.chinarewards.gwt.elt.client.department.Treetable;

import java.util.EventListener;

public interface TreeTableListener extends EventListener {

	  /**
	   * Fired when a tree item is selected.
	   * 
	   * @param item the item being selected.
	   */
	  void onTreeItemSelected(TreeItem item);

	  /**
	   * Fired when a tree item is opened or closed.
	   * 
	   * @param item the item whose state is changing.
	   */
	  void onTreeItemStateChanged(TreeItem item);

	}
