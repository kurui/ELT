/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.request;

import com.chinarewards.gwt.elt.client.order.request.SearchOrderByIdResponse;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author yanrui
 */
public class SearchOrderByIdRequest implements
		Action<SearchOrderByIdResponse> {

	private String id;

	public SearchOrderByIdRequest() {
	}

	public SearchOrderByIdRequest(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
