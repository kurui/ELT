/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author nicho
 * @since 2012年1月13日 09:52:24
 */
public class UpdateOrderStatusResponse implements Result {

	private String total;

	public UpdateOrderStatusResponse() {
	}

	public UpdateOrderStatusResponse(String total) {
		this.total = total;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
}
