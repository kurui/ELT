/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.order.model.OrderVo;

/**
 * @author nicho
 * @since 2012年1月9日 19:00:32
 */
public class SearchOrderResponse implements Result {

	private List<OrderVo> result;
	private int total;


	/**
	 * @return the result
	 */
	public List<OrderVo> getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(List<OrderVo> result) {
		this.result = result;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
}
