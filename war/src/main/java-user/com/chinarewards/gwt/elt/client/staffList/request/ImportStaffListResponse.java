package com.chinarewards.gwt.elt.client.staffList.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.staffList.model.ImportStaffListClient;

/**
 * Models the response after user process request.
 * 
 * @author nicho
 * @since 2012年2月14日 10:35:32
 */
public class ImportStaffListResponse implements Result {

	private List<ImportStaffListClient> result;
	private int total;
	private int selectTotal;



	public int getSelectTotal() {
		return selectTotal;
	}



	public void setSelectTotal(int selectTotal) {
		this.selectTotal = selectTotal;
	}



	public List<ImportStaffListClient> getResult() {
		return result;
	}



	public void setResult(List<ImportStaffListClient> result) {
		this.result = result;
	}



	public int getTotal() {
		return total;
	}



	public void setTotal(int total) {
		this.total = total;
	}



	public ImportStaffListResponse() {

	}
	public ImportStaffListResponse(List<ImportStaffListClient> result,int total,int selectTotal) {
		this.result=result;
		this.total=total;
		this.selectTotal=selectTotal;
	}
	

}
