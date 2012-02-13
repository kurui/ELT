package com.chinarewards.elt.service.budget.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.budget.CorpBudgetDao;
import com.chinarewards.elt.dao.budget.DepartmentBudgetDao;
import com.chinarewards.elt.dao.org.DepartmentDao;
import com.chinarewards.elt.domain.budget.CorpBudget;
import com.chinarewards.elt.domain.budget.DepartmentBudget;
import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.budget.search.DepartmentBudgetVo;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.service.budget.BudgetLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class BudgetLogicImpl implements BudgetLogic {
	private DepartmentBudgetDao departmentBudgetDao;
	private CorpBudgetDao corpBudgetDao;
	private DepartmentDao departmentDao;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	protected BudgetLogicImpl(DepartmentBudgetDao departmentBudgetDao,
			DepartmentDao departmentDao, CorpBudgetDao corpBudgetDao) {
		this.departmentBudgetDao = departmentBudgetDao;
		this.departmentDao = departmentDao;
		this.corpBudgetDao = corpBudgetDao;
	}

	@Override
	public CorpBudget saveCorpBudget(SysUser caller, CorpBudget corpBudget) {
		Date currTime = DateUtil.getTime();

		if (StringUtil.isEmptyString(corpBudget.getId())) {
			// Create
			corpBudget.setDeleted(0);// 正常状态，没有删除为0
			corpBudget.setRecorddate(currTime);
			corpBudget.setRecorduser(caller.getUserName());
			corpBudget.setRecorduser(caller.getUserName());
			corpBudget.setCorporationId(caller.getCorporation().getId());
			corpBudgetDao.save(corpBudget);
		} else {
			// Update
			CorpBudget tempCorpBudget = corpBudgetDao.findById(CorpBudget.class,
					corpBudget.getId());
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
	public DepartmentBudget saveDepartmentBudget(SysUser caller,
			DepartmentBudget departmentBudget) {
		Date currTime = DateUtil.getTime();
		if (StringUtil.isEmptyString(departmentBudget.getId())) {
			// Create
			departmentBudget.setDeleted(0);// 正常状态，没有删除为0
			departmentBudget.setRecorduser(caller.getUserName());
			departmentBudgetDao.save(departmentBudget);
		} else {
			// Update
			departmentBudget = departmentBudgetDao.findById(
					DepartmentBudget.class, departmentBudget.getId());
			departmentBudget.setRecorddate(currTime);
			departmentBudget.setRecorduser(caller.getUserName());
			departmentBudgetDao.update(departmentBudget);
		}

		return departmentBudget;
	}

	@Override
	public CorpBudget findCorpBudgetById(String id) {
		return corpBudgetDao.findById(CorpBudget.class, id);
	}
	@Override
	public List<CorpBudget> findCorpBudget(String corpid) {
		return corpBudgetDao.findCorpBudget(corpid);
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

	private DepartmentBudgetVo convertFromBudgetToVo(
			DepartmentBudget departmentBudget) {
		DepartmentBudgetVo departmentBudgetVo = new DepartmentBudgetVo();
		departmentBudgetVo.setBudgetIntegral(departmentBudget
				.getBudgetIntegral());
		departmentBudgetVo.setCorpBudgetId(departmentBudget.getCorpBudgetId());
		departmentBudgetVo.setDeleted(departmentBudget.getDeleted());
		departmentBudgetVo.setDepartmentId(departmentBudget.getDepartmentId());
		departmentBudgetVo.setId(departmentBudget.getId());
		departmentBudgetVo.setUseIntegeral(departmentBudget.getUseIntegeral());
		Department department = departmentDao.findById(Department.class,
				departmentBudget.getDepartmentId());
		departmentBudgetVo.setDepartmentName(department.getName());

		return departmentBudgetVo;
	}

}
