package com.chinarewards.gwt.elt.client.department.Treetable;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.Widget;
@SuppressWarnings({ "rawtypes", "deprecation","unchecked" })
public class TreeItem extends Widget implements HasHTML {


	private Vector children = new Vector();

	  private Element itemTable, contentElem, imgElem;

	  private boolean open;

	  private TreeItem parentItem;

	  private boolean selected;

	  private Object userObject;

	  private TreeTable table;

	  private int row;
	  
	  private Widget widget;

	  /**
	   * Creates an empty tree item.
	   */
	  public TreeItem() {
	    setElement(DOM.createDiv());
	    itemTable = DOM.createTable();
	    contentElem = DOM.createSpan();
	    imgElem = DOM.createImg();

	    // Uses the following Element hierarchy:
	    // <div (handle)>
	    // <table (itemElem)>
	    // <tr>
	    // <td><img (imgElem)/></td>
	    // <td><span (contents)/></td>
	    // </tr>
	    // </table>
	    // </div>

	    Element tbody = DOM.createTBody(), tr = DOM.createTR();
	    Element tdImg = DOM.createTD(), tdContent = DOM.createTD();
	    DOM.appendChild(itemTable, tbody);
	    DOM.appendChild(tbody, tr);
	    DOM.appendChild(tr, tdImg);
	    DOM.appendChild(tr, tdContent);
	    DOM.setStyleAttribute(tdImg, "verticalAlign", "middle");
	    DOM.setStyleAttribute(tdContent, "verticalAlign", "middle");

	    DOM.appendChild(getElement(), itemTable);
	    DOM.appendChild(tdImg, imgElem);
	    DOM.appendChild(tdContent, contentElem);

	    DOM.setAttribute(getElement(), "position", "relative");
	    DOM.setStyleAttribute(contentElem, "display", "inline");
	    DOM.setStyleAttribute(getElement(), "whiteSpace", "nowrap");
	    DOM.setAttribute(itemTable, "whiteSpace", "nowrap");
	    setStyleName(contentElem, "gwt-TreeItem", true);
	  }
	  
	  public TreeItem(Object userObj) {
	    this();
	    setUserObject(userObj);
	  }

	  /**
	   * Adds a child tree item containing the specified text.
	   * 
	   * @param itemText
	   *            the text to be added
	   * @return the item that was added
	   */
	  public TreeItem addItem(String itemText) {
	    TreeItem ret = new TreeItem(itemText);
	    addItem(ret);
	    return ret;
	  }
	  
	  public TreeItem addItem(Object userObj) {
	    TreeItem ret = new TreeItem(userObj);
	    addItem(ret);
	    return ret;
	  }
	  
	  /**
	   * Adds another item as a child to this one.
	   * 
	   * @param item
	   *            the item to be added
	   */

	public void addItem(TreeItem item) {
	    // If this element already belongs to a tree or tree item, it should be
	    // removed.
	    if ((item.getParentItem() != null) || (item.getTreeTable() != null)) {
	      item.remove();
	    }
	    item.setTreeTable(table);
	    item.setParentItem(this);
	    children.add(item);
	    int d = item.getDepth();
	    if (d != 0) {
	      DOM.setStyleAttribute(item.getElement(), "marginLeft", (d * 16) + "px");
	    }
	    if (table != null) {
	      table.insertItem(item, getRow() + getChildCount());
	      table.updateRowCache();
	      table.updateVisibility(item);
	    }

	    if (children.size() == 1) {
	      updateState();
	    }
	  }

	  public int getRow() {
	    return row;
	  }

	  void setRow(int r) {
	    row = r;
	  }

	  /**
	   * Returns the depth of this item. Depth of root child is 0.
	   * 
	   * @return
	   */
	  public int getDepth() {
	    if (parentItem == null) {
	      return 0;
	    }
	    return parentItem.getDepth() + 1;
	  }

	  /**
	   * Returns the count of all descendents; includes this item in the count.
	   * 
	   * @return
	   */
	  int getDescendentCount() {
	    int d = 1;
	    for (int i = getChildCount() - 1; i >= 0; i--) {
	      d += getChild(i).getDescendentCount();
	    }
	    return d;
	  }

	  /**
	   * Adds a child tree item containing the specified widget.
	   * 
	   * @param widget
	   *            the widget to be added
	   * @return the item that was added
	   */
	  public TreeItem addItem(Widget widget) {
	    TreeItem ret = new TreeItem(widget);
	    addItem(ret);
	    return ret;
	  }

	  /**
	   * Gets the child at the specified index.
	   * 
	   * @param index
	   *            the index to be retrieved
	   * @return the item at that index
	   */

	  public TreeItem getChild(int index) {
	    if ((index < 0) || (index >= children.size())) {
	      return null;
	    }

	    return (TreeItem) children.get(index);
	  }

	  /**
	   * Gets the number of children contained in this item.
	   * 
	   * @return this item's child count.
	   */

	  public int getChildCount() {
	    return children.size();
	  }

	  /**
	   * Gets the index of the specified child item.
	   * 
	   * @param child
	   *            the child item to be found
	   * @return the child's index, or <code>-1</code> if none is found
	   */

	  public int getChildIndex(TreeItem child) {
	    return children.indexOf(child);
	  }

	  public String getHTML() {
	    return DOM.getInnerHTML(contentElem);
	  }

	  /**
	   * Gets this item's parent.
	   * 
	   * @return the parent item
	   */
	  public TreeItem getParentItem() {
	    return parentItem;
	  }

	  /**
	   * Gets whether this item's children are displayed.
	   * 
	   * @return <code>true</code> if the item is open
	   */
	  public boolean getState() {
	    return open;
	  }

	  public boolean isOpen() {
	    return getState();
	  }

	  public String getText() {
	    return DOM.getInnerText(contentElem);
	  }

	  /**
	   * Gets the tree that contains this item.
	   * 
	   * @return the containing tree
	   */
	  public TreeTable getTreeTable() {
	    return table;
	  }

	  /**
	   * Gets the user-defined object associated with this item.
	   * 
	   * @return the item's user-defined object
	   */
	  public Object getUserObject() {
	    return userObject;
	  }

	  /**
	   * Gets the <code>Widget</code> associated with this tree item.
	   * 
	   * @return the widget
	   */
	  public Widget getWidget() {
	    return widget;
	  }

	  /**
	   * Determines whether this item is currently selected.
	   * 
	   * @return <code>true</code> if it is selected
	   */
	  public boolean isSelected() {
	    return selected;
	  }

	  /**
	   * Removes this item from its tree.
	   */
	  public void remove() {
	    if (parentItem != null) {
	      // If this item has a parent, remove self from it.
	      parentItem.removeItem(this);
	    } else if (table != null) {
	      // If the item has no parent, but is in the Tree, it must be a
	      // top-level
	      // element.
	      table.removeItem(this);
	    }
	  }

	  /**
	   * Removes one of this item's children.
	   * 
	   * @param item
	   *            the item to be removed
	   */

	  public void removeItem(TreeItem item) {
	    if (!children.contains(item)) {
	      return;
	    }
	    // Update Item state.
	    item.setTreeTable(null);
	    item.setParentItem(null);

	    children.remove(item);
	    if (table != null) {
	      table.removeItemFromTable(item);
	    }

	    if (children.size() == 0) {
	      updateState();
	    }
	  }

	  /**
	   * Removes all of this item's children.
	   */
	  public void removeItems() {
	    while (getChildCount() > 0) {
	      removeItem(getChild(0));
	    }
	  }

	  public void setHTML(String html) {
	    DOM.setInnerHTML(contentElem, html);
//	    if (widget != null) {
//	      DOM.removeChild(contentElem, widget.getElement());
//	      widget = null;
//	    }
	  }

	  /**
	   * Selects or deselects this item.
	   * 
	   * @param selected
	   *            <code>true</code> to select the item, <code>false</code>
	   *            to deselect it
	   */
	  public void setSelected(boolean selected) {
	    if (this.selected == selected) {
	      return;
	    }
	    this.selected = selected;
	    setStyleName(contentElem, "gwt-TreeItem-selected", selected);
	  }

	  /**
	   * Sets whether this item's children are displayed.
	   * 
	   * @param open
	   *            whether the item is open
	   */
	  public void setState(boolean open) {
	    setState(open, true);
	  }

	  /**
	   * Sets whether this item's children are displayed.
	   * 
	   * @param open
	   *            whether the item is open
	   * @param fireEvents
	   *            <code>true</code> to allow open/close events to be fired
	   */
	  public void setState(boolean open, boolean fireEvents) {
	    if (open && children.size() == 0) {
	      return;
	    }

	    this.open = open;
	    if (open) {
	      table.showChildren(this);
	    } else {
	      table.hideChildren(this);
	    }
	    updateState();

	    if (fireEvents) {
	      table.fireStateChanged(this);
	    }
	  }

	  public void setText(String text) {
	    DOM.setInnerText(contentElem, text);
//	    if (widget != null) {
//	      DOM.removeChild(contentElem, widget.getElement());
//	      widget = null;
//	    }
	  }

	  /**
	   * Sets the user-defined object associated with this item.
	   * 
	   * @param userObj
	   *            the item's user-defined object
	   */
	  public void setUserObject(Object userObj) {
	    userObject = userObj;
	  }

	  /**
	   * Sets the current widget. Any existing child widget will be removed.
	   * 
	   * @param widget
	   *            Widget to set
	   */
	  public void setWidget(Widget w) {
	    if (widget != null) {
	      DOM.removeChild(contentElem, widget.getElement());
//	      widget.setParent(null);
	    }
	    
	    if (w != null) {
	      widget = w;
	      DOM.setInnerText(contentElem, null);
	      DOM.appendChild(contentElem, w.getElement());
//	      widget.setParent(this);
	    }
	  }

	  /**
	   * Returns the widget, if any, that should be focused on if this TreeItem is
	   * selected.
	   * 
	   * @return widget to be focused.
	   */
	  protected HasFocus getFocusableWidget() {
	    Widget widget = getWidget();
	    if (widget instanceof HasFocus) {
	      return (HasFocus) widget;
	    } else {
	      return null;
	    }
	  }


	void addTreeItems(List accum) {
	    for (int i = 0; i < children.size(); i++) {
	      TreeItem item = (TreeItem) children.get(i);
	      accum.add(item);
	      item.addTreeItems(accum);
	    }
	  }

	  Vector getChildren() {
	    return children;
	  }

	  Element getContentElem() {
	    return contentElem;
	  }

	  int getContentHeight() {
	    return DOM.getIntAttribute(itemTable, "offsetHeight");
	  }

	  Element getImageElement() {
	    return imgElem;
	  }

	  int getTreeTop() {
	    TreeItem item = this;
	    int ret = 0;

	    while (item != null) {
	      ret += DOM.getIntAttribute(item.getElement(), "offsetTop");
	      item = item.getParentItem();
	    }

	    return ret;
	  }

	  String imgSrc(String img) {
	    if (table == null) {
	      return img;
	    }
	    return table.getImageBase() + img;
	  }

	  void setParentItem(TreeItem parent) {
	    this.parentItem = parent;
	  }

	  void setTreeTable(TreeTable table) {
	    if (this.table == table) {
	      return;
	    }

	    if (this.table != null) {
	      if (this.table.getSelectedItem() == this) {
	        this.table.setSelectedItem(null);
	      }
	    }
	    this.table = table;
	    for (int i = 0, n = children.size(); i < n; ++i) {
	      ((TreeItem) children.get(i)).setTreeTable(table);
	    }
	    updateState();
	  }

	  void updateState() {
	    if (children.size() == 0) {
	      // UIObject.setVisible(childSpanElem, false);
	      DOM.setAttribute(imgElem, "src", imgSrc("images/tree_white.png"));
	      return;
	    }

	    // We must use 'display' rather than 'visibility' here,
	    // or the children will always take up space.
	    if (open) {
	      // UIObject.setVisible(childSpanElem, true);
	      DOM.setAttribute(imgElem, "src", imgSrc("images/tree_open.gif"));
	    } else {
	      // UIObject.setVisible(childSpanElem, false);
	      DOM.setAttribute(imgElem, "src", imgSrc("images/tree_closed.gif"));
	    }
	    
//	    if (getParentItem() != null) {
//	      table.updateVisibility(getParentItem());
//	    }
	  }

	  void updateStateRecursive() {
	    updateState();
	    for (int i = 0, n = children.size(); i < n; ++i) {
	      ((TreeItem) children.get(i)).updateStateRecursive();
	    }
	  }
	}
