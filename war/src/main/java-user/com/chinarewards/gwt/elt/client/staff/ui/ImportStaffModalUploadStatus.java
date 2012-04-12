package com.chinarewards.gwt.elt.client.staff.ui;


import gwtupload.client.BaseUploadStatus;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ImportStaffModalUploadStatus extends BaseUploadStatus {
  protected PopupPanel box = new PopupPanel(false, true);
  
  public ImportStaffModalUploadStatus() {
    super();
    super.getWidget().addStyleName("upld-status");
    box.add(super.getWidget());
    ((Element) box.getElement().getFirstChild()).setClassName("GWTUpld");
  }
  
  /**
   * Returns an empty html widget, 
   * so, PopupPanel will never attached to the document by the user
   * and it will be attached when show() is called.
   */
  @Override public Widget getWidget() {
    return new HTML();
  };
  
  /**
   * show/hide the modal dialog.
   */
  @Override
  public void setVisible(boolean b) {
    if (b) {
      box.center();
    } else {
      box.hide();
    }
  }
}