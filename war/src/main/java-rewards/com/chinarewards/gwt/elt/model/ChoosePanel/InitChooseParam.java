package com.chinarewards.gwt.elt.model.ChoosePanel;

import java.io.Serializable;

public class InitChooseParam implements Serializable {

	private static final long serialVersionUID = 1L;
	String topName;
	String chooseBtnName;
	public String getTopName() {
		return topName;
	}
	public void setTopName(String topName) {
		this.topName = topName;
	}
	public String getChooseBtnName() {
		return chooseBtnName;
	}
	public void setChooseBtnName(String chooseBtnName) {
		this.chooseBtnName = chooseBtnName;
	}
}
