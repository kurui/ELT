package com.chinarewards.elt.service.budget;


import com.chinarewards.elt.domain.budget.CorpBudget;
import com.chinarewards.elt.domain.budget.DepartmentBudget;
import com.chinarewards.elt.model.budget.search.DepartmentBudgetVo;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.order.search.OrderListVo;
import com.chinarewards.elt.model.order.search.OrderStatus;
import com.chinarewards.elt.model.user.UserContext;

/**
 * Service of corporation.
 * 
 * @author lw
 * @since 1.5
 */
public interface BudgetService {

	/**
	 * 保存企业预算
	 * @param context
	 * @param order
	 * @return
	 */
	public CorpBudget saveCorpBudget(UserContext context, CorpBudget budget);
	
	/**
	 * 保存部门预算
	 * @param context
	 * @param order
	 * @return
	 */
	public DepartmentBudget saveDepartmentBudget(UserContext context, DepartmentBudget budget);


	/**
	 * 查找根据CorpBudgetID
	 * @param id
	 * @return
	 */
	public CorpBudget findCorpBudgetById(String id);
	
	
	/**
	 * 查找根据部门预算ID
	 * @param id
	 * @return
	 */
	public DepartmentBudget findDepartmentBudgetById(String id);
	
	
	/**
	 * 删除订单根据ID
	 * @param id
	 * @return
	 */
	public String deleteDepartmentBudget(UserContext context,String id);
	/**
	 * 部门预算列表
	 * @param context
	 * @param CorpBudget
	 * @return
	 */
	public PageStore<DepartmentBudgetVo> deptBudgetList(UserContext context,DepartmentBudgetVo deptBudgetVo);

	
}
