package com.chinarewards.gwt.elt.client.gift.ui;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.Uploader;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;

public class ImportGiftSingleUploader extends Uploader {

  public final static String SERVLET_PATH = "servlet.iss";

  private Button button;

  /**
   * Default constructor.
   * Uses the standard browser input, a basic status widget, and
   * creates a standard button to send the file
   * 
   */
  public ImportGiftSingleUploader() {
    this(FileInputType.BROWSER_INPUT);
  }

  /**
   * Use a basic status widget, and creates 
   * a standard button to send the file
   * 
   * @param type
   *        file input to use
   */
  public ImportGiftSingleUploader(FileInputType type) {
    this(type, null);
  }

  /**
   * Creates a standard button to send the file
   * 
   * @param type
   *        file input to use
   * @param status
   *        Customized status widget to use
   */
  public ImportGiftSingleUploader(FileInputType type, IUploadStatus status) {
    this(type, status, new Button());
  }

  /**
   * Constructor
   * 
   * @param type
   *        file input to use
   * @param status
   *        Customized status widget to use
   * @param button
   *        Customized button which submits the form
   */
  public ImportGiftSingleUploader(FileInputType type, IUploadStatus status, Button button) {
    this(type, status, button, null);
  }

  /**
   * This constructor allows to use an existing form panel.
   * 
   * @param type
   *        file input to use
   * @param status
   *        Customized status widget to use
   * @param button
   *        Customized button which submits the form
   * @param form
   *        Customized form panel
   */
  public ImportGiftSingleUploader(FileInputType type, IUploadStatus status, Button button, FormPanel form) {
    super(type, form);

   // final Uploader thisInstance = this;

    if (status == null) {
      status = new ImportGiftModalUploadStatus();
    }
    super.setStatusWidget(status);

    this.button = button;
    if (button.getText().length() == 0) {
      button.setText(I18N_CONSTANTS.uploaderSend());
    }

//    button.addStyleName("submit");
//    button.addClickHandler(new ClickHandler() {
//      public void onClick(ClickEvent event) {
//        thisInstance.submit();
//      }
//    });

    // The user could have attached the button anywhere in the page.
    if (button.getParent() == null) {
      super.add(button);
    }
  }

  /**
   * Uses the standard browser input, customized status, and creates a 
   * standard button to send the file
   * 
   * @param status
   *        Customized status widget to use
   */
  public ImportGiftSingleUploader(IUploadStatus status) {
    this(FileInputType.BROWSER_INPUT, status);
  }

  /**
   * 
   * @param status
   *        Customized status widget to use
   * @param button
   *        Customized button which submits the form
   */
  public ImportGiftSingleUploader(IUploadStatus status, Button button) {
    this(FileInputType.BROWSER_INPUT, status, button, null);
  }

  @Override
  public void setEnabled(boolean b) {
    super.setEnabled(b);
    button.setEnabled(b);
  }

  /* (non-Javadoc)
   * @see gwtupload.client.Uploader#setI18Constants(gwtupload.client.IUploader.UploaderConstants)
   */
  @Override
  public void setI18Constants(UploaderConstants strs) {
    super.setI18Constants(strs);
    button.setText(strs.uploaderSend());
  }

  /* (non-Javadoc)
   * @see gwtupload.client.Uploader#onChangeInput()
   */
  @Override
  protected void onChangeInput() {
    super.onChangeInput();
    button.addStyleName("changed");
    button.setFocus(true);
  }

  /* (non-Javadoc)
   * @see gwtupload.client.Uploader#onFinishUpload()
   */
  @Override
  protected void onFinishUpload() {
    super.onFinishUpload();
    if (getStatus() == Status.REPEATED) {
      getStatusWidget().setError(getI18NConstants().uploaderAlreadyDone());
    }
    getStatusWidget().setStatus(Status.UNINITIALIZED);
    reuse();
    assignNewNameToFileInput();
    button.setEnabled(true);
    button.removeStyleName("changed");
    if (autoSubmit) {
      getFileInput().setText(i18nStrs.uploaderBrowse());
    }
  }
  
  /* (non-Javadoc)
   * @see gwtupload.client.Uploader#onStartUpload()
   */
  @Override
  protected void onStartUpload() {
    super.onStartUpload();
    button.setEnabled(false);
    button.removeStyleName("changed");
  }
  
  /* (non-Javadoc)
   * @see gwtupload.client.Uploader#setAutoSubmit(boolean)
   */
  @Override
  public void setAutoSubmit(boolean b) {
    button.setVisible(!b);
    super.setAutoSubmit(b);
  }
}
