package com.chinarewards.gwt.elt.client.gift.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.gift.model.ImportGiftListClient;

/**
 * Models the response after user process request.
 * 
 * @author yanrui
 * @since 1.5.2
 */
public class ImportGiftListResponse implements Result {

	private List<ImportGiftListClient> result;
	private int total;
	private int selectTotal;



	public int getSelectTotal() {
		return selectTotal;
	}



	public void setSelectTotal(int selectTotal) {
		this.selectTotal = selectTotal;
	}



	public List<ImportGiftListClient> getResult() {
		return result;
	}



	public void setResult(List<ImportGiftListClient> result) {
		this.result = result;
	}



	public int getTotal() {
		return total;
	}



	public void setTotal(int total) {
		this.total = total;
	}



	public ImportGiftListResponse() {

	}
	public ImportGiftListResponse(List<ImportGiftListClient> result,int total,int selectTotal) {
		this.result=result;
		this.total=total;
		this.selectTotal=selectTotal;
	}
	

}
