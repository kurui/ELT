package com.chinarewards.gwt.elt.client.department.Treetable;

import java.util.Iterator;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public class TreeTableListenerCollection extends Vector {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	   * Fires a "tree item selected" event to all listeners.
	   * 
	   * @param item the tree item being selected.
	   */
	  public void fireItemSelected(TreeItem item) {
	    for (Iterator it = iterator(); it.hasNext();) {
	      TreeTableListener listener = (TreeTableListener) it.next();
	      listener.onTreeItemSelected(item);
	    }
	  }

	  /**
	   * Fires a "tree item state changed" event to all listeners.
	   * 
	   * @param item the tree item whose state has changed.
	   */
	  public void fireItemStateChanged(TreeItem item) {
	    for (Iterator it = iterator(); it.hasNext();) {
	      TreeTableListener listener = (TreeTableListener) it.next();
	      listener.onTreeItemStateChanged(item);
	    }
	  }
	}
