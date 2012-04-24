/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;

/**
 * @author lw
 * @since 2012年1月20日 19:00:32
 */
public class SearchAskBudgetResponse implements Result {

	private List<AskBudgetClientVo> result;
	private int total;


	/**
	 * @return the result
	 */
	public List<AskBudgetClientVo> getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(List<AskBudgetClientVo> result) {

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
