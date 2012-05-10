package com.chinarewards.elt.service.budget.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.budget.AskBudgetDao;
import com.chinarewards.elt.dao.budget.CorpBudgetDao;
import com.chinarewards.elt.dao.budget.DepartmentBudgetDao;
import com.chinarewards.elt.dao.org.DepartmentDao;
import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.domain.budget.AskBudget;
import com.chinarewards.elt.domain.budget.CorpBudget;
import com.chinarewards.elt.domain.budget.DepartmentBudget;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.budget.search.AskBudgetVo;
import com.chinarewards.elt.model.budget.search.DepartmentBudgetVo;
import com.chinarewards.elt.model.budget.search.IntegralManagementVo;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.model.vo.StaffSearchVo;
import com.chinarewards.elt.service.budget.BudgetLogic;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class BudgetLogicImpl implements BudgetLogic {
	private DepartmentBudgetDao departmentBudgetDao;
	private CorpBudgetDao corpBudgetDao;
	private DepartmentDao departmentDao;
	private AskBudgetDao askBudgetDao;
	private DepartmentLogic departmentLogic;
	private UserLogic userLogic;
	private StaffDao   staffDao;
	private RewardDao rewardDao;
	private StaffLogic   staffLogic;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
 
	@Inject
	protected BudgetLogicImpl(DepartmentBudgetDao departmentBudgetDao,UserLogic userLogic,RewardDao rewardDao,
			DepartmentDao departmentDao, CorpBudgetDao corpBudgetDao,StaffDao   staffDao,AskBudgetDao askBudgetDao,
			DepartmentLogic departmentLogic,StaffLogic   staffLogic) {
		this.departmentBudgetDao = departmentBudgetDao;
		this.departmentDao = departmentDao;
		this.corpBudgetDao = corpBudgetDao;
		this.departmentLogic = departmentLogic;
		this.staffDao  = staffDao;
		this.userLogic = userLogic;
		this.askBudgetDao = askBudgetDao;
		this.rewardDao = rewardDao;
		this.staffLogic = staffLogic;
	}

	@Override
	public CorpBudget saveCorpBudget(SysUser caller, CorpBudget corpBudget) {
		Date currTime = DateUtil.getTime();

		if (StringUtil.isEmptyString(corpBudget.getId())) {
			// Create
			corpBudget.setDeleted(0);// 正常状态，没有删除为0
			corpBudget.setRecorddate(currTime);
			corpBudget.setRecorduser(caller.getUserName());
			corpBudget.setCorporationId(caller.getCorporation().getId());
			corpBudgetDao.save(corpBudget);
		} else {
			// Update
			CorpBudget tempCorpBudget = corpBudgetDao.findById(
					CorpBudget.class, corpBudget.getId());
			tempCorpBudget.setId(corpBudget.getId());
			tempCorpBudget.setBudgetTitle(corpBudget.getBudgetTitle());
			tempCorpBudget.setMoneyType(corpBudget.getMoneyType());
			tempCorpBudget.setBudgetAmount(corpBudget.getBudgetAmount());
			tempCorpBudget.setBudgetIntegral(corpBudget.getBudgetIntegral());
			tempCorpBudget.setBeginDate(corpBudget.getBeginDate());
			tempCorpBudget.setEndDate(corpBudget.getEndDate());

			tempCorpBudget.setRecorddate(currTime);
			tempCorpBudget.setRecorduser(caller.getUserName());
			corpBudgetDao.update(tempCorpBudget);
		}

		return corpBudget;
	}
    
	@Override
	public DepartmentBudget saveDepartmentBudget(UserContext context,DepartmentBudget departmentBudget) {
		Date currTime = DateUtil.getTime();
		SysUser caller = userLogic.findUserById(context.getUserId());
		CorpBudget corpBudget= corpBudgetDao.findById(CorpBudget.class,departmentBudget.getCorpBudgetId());
		//Department sonDepartment= departmentDao.findById(Department.class,departmentBudget.getDepartmentId());//得到操作的部门ID
		// Department ParentDepartment= departmentDao.findById(Department.class,sonDepartment.getParent().getId());//得到操作的部门的父部门
		List<UserRole> roleList = Arrays.asList(context.getUserRoles());//得到角色是HR还是leader
		if (StringUtil.isEmptyString(departmentBudget.getId())) {
			// Create
			departmentBudget.setDeleted(0);// 正常状态，没有删除为0
			departmentBudget.setRecorduser(caller.getUserName());
			departmentBudget.setUseIntegeral(0);//已用的积分为0
			departmentBudget = departmentBudgetDao.save(departmentBudget);
			if (roleList.contains(UserRole.CORP_ADMIN)){//是HR就更新企业的财年预算
			 //更新企业财年的已用积分
			  
			  corpBudget.setUseIntegeral(corpBudget.getUseIntegeral()+departmentBudget.getBudgetIntegral());
			  corpBudgetDao.update(corpBudget);
		     }else if (roleList.contains(UserRole.DEPT_MGR)){
		    	  //得到的是所增加的子部门的父部门ID再得到父部门的预算,
		    	  Department depart = departmentDao.findById(Department.class, departmentBudget.getDepartmentId());
		    	  DepartmentBudget ParentdepartmentBudget= departmentBudgetDao.findDepartmentBudgetByDepartmentId(depart.getParent().getId(),departmentBudget.getCorpBudgetId());
		    	  ParentdepartmentBudget.setUseIntegeral(ParentdepartmentBudget.getUseIntegeral()+departmentBudget.getBudgetIntegral());
		    	//更新父部门财年的已用积分
		    	  departmentBudgetDao.update(ParentdepartmentBudget);
			 }
		} else {
			// Update
			double newBudgetIntegral = departmentBudget.getBudgetIntegral();
			DepartmentBudget depBudget = departmentBudgetDao.findById(DepartmentBudget.class, departmentBudget.getId());
			double oldBudgetIntegral = depBudget.getBudgetIntegral();
			double sc =oldBudgetIntegral-newBudgetIntegral;//得到部门以前的积分与要修改的新设置的积分差
			departmentBudget.setRecorddate(currTime);
			departmentBudget.setRecorduser(caller.getUserName());
			departmentBudget = departmentBudgetDao.findById(DepartmentBudget.class, departmentBudget.getId());
			departmentBudget.setBudgetIntegral(newBudgetIntegral);
			departmentBudgetDao.update(departmentBudget);
			if (roleList.contains(UserRole.CORP_ADMIN)){//是HR就更新企业的财年预算
			   corpBudget.setUseIntegeral(corpBudget.getUseIntegeral()-sc);//修改财年的已使用的积分
			   corpBudgetDao.update(corpBudget);
			}else if (roleList.contains(UserRole.DEPT_MGR)){
				 //得到的是所增加的子部门的父部门ID再得到父部门的预算,
				  Department depart = departmentDao.findById(Department.class, departmentBudget.getDepartmentId());
		    	  DepartmentBudget ParentdepartmentBudget= departmentBudgetDao.findDepartmentBudgetByDepartmentId(depart.getParent().getId(),departmentBudget.getCorpBudgetId());
		    	  ParentdepartmentBudget.setUseIntegeral(ParentdepartmentBudget.getUseIntegeral()-sc);
		    	//更新父部门财年的已用积分
		    	  departmentBudgetDao.update(ParentdepartmentBudget);
			 }
		}

		return departmentBudget;
	}
	@Override
	public AskBudget saveAskBudget(UserContext context, AskBudget askBudget) {
		Date currTime = DateUtil.getTime();
		SysUser caller = userLogic.findUserById(context.getUserId());
		List<UserRole> roleList = Arrays.asList(context.getUserRoles());//得到角色是HR还是leader
		if (StringUtil.isEmptyString(askBudget.getId())) {
			// Create
			
			askBudget.setDeleted(0);// 正常状态，没有删除为0
			askBudget.setRecorddate(currTime);
			askBudget.setRecorduser(caller.getStaff());
		    askBudget.setStatus(0);
		    askBudgetDao.save(askBudget);
		    
		} else {
			// Update
			AskBudget tempaskBudget = askBudgetDao.findById(AskBudget.class, askBudget.getId());
			tempaskBudget.setId(askBudget.getId());
			tempaskBudget.setApprovedate(currTime);
			tempaskBudget.setApproveIntegeral(askBudget.getApproveIntegeral());
			tempaskBudget.setView(askBudget.getView());
			tempaskBudget.setApproveuser(caller.getStaff());
			tempaskBudget.setStatus(askBudget.getStatus());	
			askBudgetDao.update(tempaskBudget);
			if(askBudget.getStatus()==1){//如果是审批通过
				CorpBudget corpBudget= corpBudgetDao.findById(CorpBudget.class,tempaskBudget.getCorpBudgetId());
				DepartmentBudget departmentBudget = departmentBudgetDao.findDepartmentBudgetByDepartmentId(tempaskBudget.getDepartmentId(),tempaskBudget.getCorpBudgetId());//得到申请部门的预算
				
				if (roleList.contains(UserRole.CORP_ADMIN)){//是HR就更新企业的财年预算
					    corpBudget.setUseIntegeral(corpBudget.getUseIntegeral()+askBudget.getApproveIntegeral());//修改财年的已使用的积分,增加审批额
					    corpBudgetDao.update(corpBudget);//更新企业预算
					    departmentBudget.setBudgetIntegral(departmentBudget.getBudgetIntegral()+askBudget.getApproveIntegeral());//修改申请部门的预算,增加审批额
						departmentBudgetDao.update(departmentBudget);//更新申请部预算
					}else if (roleList.contains(UserRole.DEPT_MGR)){
						//得到的是所增加的子部门的父部门ID再得到父部门的预算,
						  Department depart = departmentDao.findById(Department.class, tempaskBudget.getDepartmentId());
				    	  DepartmentBudget ParentdepartmentBudget= departmentBudgetDao.findDepartmentBudgetByDepartmentId(depart.getParent().getId(),tempaskBudget.getCorpBudgetId());
				    	  ParentdepartmentBudget.setUseIntegeral(ParentdepartmentBudget.getUseIntegeral()+askBudget.getApproveIntegeral());//增加审批人所在部门的预算
				    	//更新父部门财年的已用积分
				    	  departmentBudgetDao.update(ParentdepartmentBudget);
				    	  
				    	  //得到子部门的预算
						  DepartmentBudget sonDepartmentBudget= departmentBudgetDao.findDepartmentBudgetByDepartmentId(tempaskBudget.getDepartmentId(),tempaskBudget.getCorpBudgetId());
						  sonDepartmentBudget.setBudgetIntegral(sonDepartmentBudget.getBudgetIntegral()+askBudget.getApproveIntegeral());//增加审批人所在部门的预算
				    	//更新子部门财年的总积分
				    	  departmentBudgetDao.update(sonDepartmentBudget);
					 }
			}
		}

		return askBudget;
	}
	@Override
	public AskBudget approveBudget(UserContext context, AskBudget askBudget) {
		Date currTime = DateUtil.getTime();
		SysUser caller = userLogic.findUserById(context.getUserId());
	
			// Update
			AskBudget tempaskBudget = askBudgetDao.findById(AskBudget.class, askBudget.getId());
			tempaskBudget.setId(askBudget.getId());
			tempaskBudget.setApprovedate(currTime);
			tempaskBudget.setApproveIntegeral(askBudget.getApproveIntegeral());
			tempaskBudget.setApproveuser(caller.getStaff());
			tempaskBudget.setView(askBudget.getView());
			askBudget.setStatus(1);	
			askBudgetDao.update(tempaskBudget);
		

		return askBudget;
	}
	@Override
	public CorpBudget findCorpBudgetById(String id) {
		return corpBudgetDao.findById(CorpBudget.class, id);
	}
	@Override
	public AskBudget findAskBudgetById(String id) {
		return askBudgetDao.findById(AskBudget.class, id);
	}
	@Override
	public List<CorpBudget> findCorpBudget(String corpid) {
		return corpBudgetDao.findCorpBudget(corpid);
	}
	@Override
	public List<DepartmentBudget> findDepartBudget(String depId) {
		return departmentBudgetDao.findDepartBudget(depId);
	}
	@Override
	public CorpBudget findCorpBudgetByCorpId(String corpid) {
		return corpBudgetDao.findByCorpId(corpid);
	}

	@Override
	public DepartmentBudget findDepartmentBudgetById(String id) {

		return departmentBudgetDao.findById(DepartmentBudget.class, id);
	}

	@Override
	public String deleteDepartmentBudget(SysUser caller, String id) {
		Date currTime = DateUtil.getTime();
		DepartmentBudget departmentBudget = departmentBudgetDao.findById(
				DepartmentBudget.class, id);
		departmentBudget.setDeleted(1);
		departmentBudget.setRecorddate(currTime);
		departmentBudget.setRecorduser(caller.getUserName());
		departmentBudget = departmentBudgetDao.update(departmentBudget);
		return departmentBudget.getId();
	}

	@Override
	public PageStore<DepartmentBudgetVo> deptBudgetList(SysUser caller,
			DepartmentBudgetVo departmentBudgetVo) {

		PageStore<DepartmentBudget> pageStore = new PageStore<DepartmentBudget>();

		pageStore.setResultCount(departmentBudgetDao
				.countDepartmentBudget(departmentBudgetVo));
		List<DepartmentBudget> BudgetList = departmentBudgetDao
				.departmentBudgetList(departmentBudgetVo);
		List<DepartmentBudgetVo> BudgetVoList = new ArrayList<DepartmentBudgetVo>();
		for (DepartmentBudget order : BudgetList) {
			BudgetVoList.add(convertFromBudgetToVo(order));
		}
		PageStore<DepartmentBudgetVo> storeVo = new PageStore<DepartmentBudgetVo>();
		storeVo.setResultCount(pageStore.getResultCount());
		storeVo.setResultList(BudgetVoList);

		return storeVo;
	}
	@Override
	public PageStore<AskBudgetVo> askBudgetList(SysUser caller,	AskBudgetVo askBudgetVo) {

		PageStore<DepartmentBudget> pageStore = new PageStore<DepartmentBudget>();

		pageStore.setResultCount(askBudgetDao.countAskBudget(askBudgetVo));
		List<AskBudget> BudgetList = askBudgetDao.AskBudgetList(askBudgetVo);
		List<AskBudgetVo> budgetVoList = new ArrayList<AskBudgetVo>();
		for (AskBudget order : BudgetList) {
			budgetVoList.add(convertAskBudgetToVo(order));
		}
		PageStore<AskBudgetVo> storeVo = new PageStore<AskBudgetVo>();
		storeVo.setResultCount(pageStore.getResultCount());
		storeVo.setResultList(budgetVoList);

		return storeVo;
	}

	private DepartmentBudgetVo convertFromBudgetToVo(
			DepartmentBudget departmentBudget) {
		DepartmentBudgetVo departmentBudgetVo = new DepartmentBudgetVo();
		departmentBudgetVo.setBudgetIntegral(departmentBudget.getBudgetIntegral());
		departmentBudgetVo.setCorpBudgetId(departmentBudget.getCorpBudgetId());
		departmentBudgetVo.setDeleted(departmentBudget.getDeleted());
		departmentBudgetVo.setDepartmentId(departmentBudget.getDepartmentId());
		departmentBudgetVo.setId(departmentBudget.getId());
		departmentBudgetVo.setUseIntegeral(departmentBudget.getUseIntegeral());
		Department department = departmentDao.findById(Department.class,departmentBudget.getDepartmentId());
		StaffSearchVo searchVo = new StaffSearchVo ();
		int people =0;
//		List<Department>  list=departmentLogic.getWholeChildren(departmentBudget.getDepartmentId(),true);//得到子部门及本身
		List<Department>  list=departmentLogic.getAllChildren(departmentBudget.getDepartmentId(),true);
		list.add(department);
		
		if(list.size()>0){
			Department depar = new Department();
			for(int i=0;i<list.size();i++){
				depar = list.get(i);
				searchVo.setDeptId(depar.getId());
				 people = staffDao.queryStaffPageActionCount(searchVo)+people;//查找部门人数
				
			}
		}
		departmentBudgetVo.setPeople(people);
		departmentBudgetVo.setDepartmentName(department.getName());

		return departmentBudgetVo;
	}
	private AskBudgetVo convertAskBudgetToVo(AskBudget askBudget) {
		AskBudgetVo askBudgetVo = new AskBudgetVo();
		askBudgetVo.setApprovedate(askBudget.getApprovedate());
		askBudgetVo.setApproveIntegeral(askBudget.getApproveIntegeral());
		if(askBudget.getApproveuser()!=null)
		    askBudgetVo.setApproveuser(askBudget.getApproveuser().getName());
		else
			askBudgetVo.setApproveuser("");
		askBudgetVo.setAskIntegral(askBudget.getAskIntegral());
		askBudgetVo.setId(askBudget.getId());
		askBudgetVo.setReason(askBudget.getReason());
		askBudgetVo.setRecorddate(askBudget.getRecorddate());
		askBudgetVo.setRecordname(askBudget.getRecorduser().getName());
		askBudgetVo.setRecorduser(askBudget.getRecorduser().getId());
		askBudgetVo.setStatus(askBudget.getStatus());
		askBudgetVo.setView(askBudget.getView());
		
		Department department = departmentDao.findById(Department.class,askBudget.getDepartmentId());
		askBudgetVo.setDepName(department.getName());
		CorpBudget corpBudget =findCorpBudgetById(askBudget.getCorpBudgetId());
		 askBudgetVo.setCorpBudget(corpBudget.getBudgetTitle());
		return askBudgetVo;
	}
	@Override

	public DepartmentBudget findByDepAndCorpBudgetId(DepartmentBudget departmentBudget) {
		return departmentBudgetDao.findByDepAndCorpBudgetId(departmentBudget);//是否已经有了数据
	}
	public List<IntegralManagementVo> getIntegralManagementList(String corpId,String corpBudgetId) {
		List<IntegralManagementVo> volist = new ArrayList<IntegralManagementVo>();
		List<Department> department = departmentLogic
				.getWholeDepartmentsOfCorporation(corpId);
		for (Department dep : department) {
			IntegralManagementVo vo = new IntegralManagementVo();
			vo.setDepartmentId(dep.getId());
			vo.setDepartmentName(dep.getName());
			if(dep.getParent()!=null)
			vo.setParentId(dep.getParent().getId());
			vo.setLeaf(departmentLogic.isLeaf(dep));
			DepartmentBudget budget = this
					.findDepartmentBudgetByDepartmentId(dep.getId(),corpBudgetId);
			if (budget != null) {
				vo.setCorpBudgetId(budget.getCorpBudgetId());
				vo.setBudgetIntegral(budget.getBudgetIntegral());
				vo.setUseIntegeral(budget.getUseIntegeral());
			}
			volist.add(vo);
		}

		return volist;
	}

	@Override
	public DepartmentBudget findDepartmentBudgetByDepartmentId(	String departmentId,String corpBudgetId) {
		return departmentBudgetDao
				.findDepartmentBudgetByDepartmentId(departmentId,corpBudgetId);
	}
	@Override
	public String updateBudget(String rewardId,double integral) {
		
		String bakcStr = "fail";
		Reward reward = rewardDao.findById(Reward.class, rewardId);
		SysUser user = reward.getCreatedBy();  
		List<UserRole> roleList = staffLogic.findUserRoles(user.getStaff().getId());//得到角色是HR还是leader
		
	 if (roleList.contains(UserRole.CORP_ADMIN)){//是HR就更新企业的财年已用预算
		CorpBudget corpBudget= corpBudgetDao.findByCorpId(reward.getCorporation().getId(),reward.getExpectAwardDate());
	    corpBudget.setUseIntegeral(corpBudget.getUseIntegeral()+integral);//修改财年的已使用的积分
	    corpBudgetDao.update(corpBudget);
	    bakcStr = "success";
	  }else if (roleList.contains(UserRole.DEPT_MGR)){
		DepartmentBudget depBudget = departmentBudgetDao.findById(DepartmentBudget.class, reward.getBuilderDept().getId());
    	//更新奖项创建部门财年的已用积分
		 depBudget.setUseIntegeral(depBudget.getUseIntegeral()+integral);
    	 departmentBudgetDao.update(depBudget);
    	 bakcStr = "success";
	  }
		

		return bakcStr;
	}
}
