package com.chinarewards.elt.service.budget.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chinarewards.elt.domain.budget.AskBudget;
import com.chinarewards.elt.domain.budget.CorpBudget;
import com.chinarewards.elt.domain.budget.DepartmentBudget;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.budget.search.AskBudgetVo;
import com.chinarewards.elt.model.budget.search.DepartmentBudgetVo;
import com.chinarewards.elt.model.budget.search.IntegralManagementVo;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.service.budget.BudgetLogic;
import com.chinarewards.elt.service.budget.BudgetService;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.tx.service.TransactionService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
@Transactional
public class BudgetServiceImpl implements BudgetService {
	private final BudgetLogic budgetLogic;
	private final DepartmentLogic departmentLogic;
	private final UserLogic userLogic;
    private final TransactionService tx;
    private final DepartmentService departmentService;
	@Inject
	public BudgetServiceImpl(BudgetLogic budgetLogic,UserLogic userLogic,DepartmentLogic departmentLogic
			,TransactionService tx,DepartmentService departmentService) {
		this.userLogic = userLogic;
		this.budgetLogic = budgetLogic;
		this.departmentLogic = departmentLogic;
		this.tx = tx;
		this.departmentService = departmentService;
	}
	@Override
	public CorpBudget saveCorpBudget(UserContext context, CorpBudget corpBudget) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		return   budgetLogic.saveCorpBudget(caller, corpBudget);
		
	}
	
	@Override
	public DepartmentBudget saveDepartmentBudget(UserContext context, DepartmentBudget departmentBudget) {
		
		return   budgetLogic.saveDepartmentBudget(context, departmentBudget);
		
	}
	/**
	 * 部门申请预算
	 * @param context
	 * @param order
	 * @return
	 */
	@Override
	public AskBudget saveAskBudget(UserContext context, AskBudget budget){
		return   budgetLogic.saveAskBudget(context, budget);
	}
	@Override
	public AskBudget approveBudget(UserContext context, AskBudget budget){
		return   budgetLogic.approveBudget(context, budget);
	}
	@Override
	public PageStore<DepartmentBudgetVo> deptBudgetList(UserContext context, DepartmentBudgetVo deptBudgetVo) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		List<UserRole> roles =Arrays.asList(context.getUserRoles());
		//如果是管理员，在查看全部时只可以查看一级部门记录，
		if (roles.contains(UserRole.CORP_ADMIN)&& (deptBudgetVo.getDepartmentId()==null||deptBudgetVo.getDepartmentId().equals(""))){
			List<Department> depts = departmentLogic.getImmediacyDepartmentsOfCorporation(caller.getCorporation().getId());
			List<String> list = new ArrayList<String>();
			for (Department dept : depts) {
				list.add(dept.getId());
			}
			deptBudgetVo.setDeptIds(new ArrayList<String>(list));
		}else if (roles.contains(UserRole.DEPT_MGR)&& !roles.contains(UserRole.CORP_ADMIN)&& (deptBudgetVo.getDepartmentId()==null||deptBudgetVo.getDepartmentId().equals(""))){
			List<String> deptIds = new ArrayList<String>();//如果是部门管理员，在查看全部时只可以查看下级部门记录，
			List<Department> deptId = new ArrayList<Department>();
			//得到所管理的部门
			if(deptBudgetVo.getManageDepId()==null||deptBudgetVo.getManageDepId().equals("")){
				List<Department> listManagedDep= departmentService.findDepartmentsManagedByStaffId(caller.getStaff().getId());
				for(Department dep:listManagedDep){
					deptId = departmentLogic.getAllChildren(dep.getId(),false);
					if(deptId!=null&&deptId.size()>0){
						for(int i=0;i<deptId.size();i++){
							deptIds.add(deptId.get(i).getId());
						}
					}
			  }
			}else{
				deptId = departmentLogic.getAllChildren(deptBudgetVo.getManageDepId(),false);
				if(deptId!=null&&deptId.size()>0){
					for(int i=0;i<deptId.size();i++){
						deptIds.add(deptId.get(i).getId());
					}
				}
			}
			if(deptIds!=null&&deptIds.size()>0)
			   deptBudgetVo.setDeptIds(new ArrayList<String>(deptIds));
			else{
				deptIds.add("NO");
				deptBudgetVo.setDeptIds(new ArrayList<String>(deptIds));
			 }
		}
		return budgetLogic.deptBudgetList(caller, deptBudgetVo);
	}
	@Override
	public CorpBudget findCorpBudgetById(String id) {
		// TODO Auto-generated method stub
		return budgetLogic.findCorpBudgetById(id);
	}
	@Override
	public DepartmentBudget findDepartmentBudgetById(String id) {
		// TODO Auto-generated method stub
		return budgetLogic.findDepartmentBudgetById(id);
	}
	@Override
	public String deleteDepartmentBudget(UserContext context, String id) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		return budgetLogic.deleteDepartmentBudget(caller, id);
	}
	@Override
	public List<CorpBudget> findCorpBudget(String corpid) {
		// TODO Auto-generated method stub
		return budgetLogic.findCorpBudget(corpid);
	}
	public List<DepartmentBudget> findDepartBudget(String depId){
		return budgetLogic.findDepartBudget(depId);
	}
	@Override
	public CorpBudget findCorpBudgetByCorpId(String corpid) {
		return budgetLogic.findCorpBudgetByCorpId(corpid);
	}
	@Override
	public List<IntegralManagementVo> getIntegralManagementList(String corpId,String corpBudgetId) {
		return budgetLogic.getIntegralManagementList(corpId, corpBudgetId);
	}

	public DepartmentBudget findByDepAndCorpBudgetId(DepartmentBudget departmentBudget){
		return budgetLogic.findByDepAndCorpBudgetId(departmentBudget);
	} 
	
	@Override
	public DepartmentBudget findDepartmentBudgetByDepartmentId(String departmentId,String corpBudgetId){
		return budgetLogic.findDepartmentBudgetByDepartmentId(departmentId, corpBudgetId);
	}
	@Override
	public AskBudget findAskBudgetById(String id) {
		// TODO Auto-generated method stub
		return budgetLogic.findAskBudgetById(id);
	}
	@Override
	public PageStore<AskBudgetVo> askBudgetList(UserContext context,AskBudgetVo askBudgetVo) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		List<UserRole> roles =Arrays.asList(context.getUserRoles());
		//如果是HR管理员，在查看全部时只可以查看一级部门记录，
		if (roles.contains(UserRole.CORP_ADMIN)&& (askBudgetVo.getDepartmentId()==null||askBudgetVo.getDepartmentId().equals(""))){
			List<Department> depts = departmentLogic.getImmediacyDepartmentsOfCorporation(caller.getCorporation().getId());
			List<String> list = new ArrayList<String>();
			for (Department dept : depts) {
				list.add(dept.getId());
			}
			askBudgetVo.setDeptIds(new ArrayList<String>(list));
		}else if (roles.contains(UserRole.DEPT_MGR)&& !roles.contains(UserRole.CORP_ADMIN)&& (askBudgetVo.getDepartmentId()==null||askBudgetVo.getDepartmentId().equals(""))){
			//得到所管理的部门
			List<String> deptIds = null;//leader可看一级
			if(askBudgetVo.getManageDepId()==null||askBudgetVo.getManageDepId().equals(""))//没有指定的主部门
//			    deptIds = departmentLogic.getWholeChildrenIds(caller.getStaff().getDepartment().getId(), true);
		    deptIds = departmentLogic.getAllChildrenIds(caller.getStaff().getDepartment().getId(),true);
			
			else
//				deptIds = departmentLogic.getWholeChildrenIds(askBudgetVo.getManageDepId(), true);
				deptIds = departmentLogic.getAllChildrenIds(askBudgetVo.getManageDepId(),true);
				
			if(deptIds!=null&&deptIds.size()>0)//二级部门
				   askBudgetVo.setDeptIds(new ArrayList<String>(deptIds));
				else{
					deptIds.add("NO");//第三级部门
					askBudgetVo.setDeptIds(new ArrayList<String>(deptIds));
				 }
		}
		return budgetLogic.askBudgetList(caller, askBudgetVo);
	}
	@Override
	 public String updateBudget(String rewardId,double integral){
		return budgetLogic.updateBudget(rewardId,  integral);
	}
}
