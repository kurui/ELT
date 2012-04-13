package com.chinarewards.elt.dao.org;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;

/**
 * Dao of {@link Department}
 * 
 * @author yanxin
 * @since 1.0
 */
public class DepartmentDao extends BaseDao<Department> {

	public static final String ROOT_NAME = "ROOT_DEPT";

	/**
	 * Get the root Department.
	 * 
	 * @return
	 */
	public Department getRootDepartmentOfCorp(String corporationId) {
		String name = ROOT_NAME + corporationId;
		try {
			Department dept = (Department) getEm()
					.createQuery(
							"FROM Department d WHERE d.name = :name AND d.corporation.id = :corpId AND d.deleted =:deleted")
					.setParameter("name", name)
					.setParameter("corpId", corporationId).setParameter("deleted", false).getSingleResult();
			return dept;
		} catch (NoResultException e) {
			return null;
		}
	}

	public Department addRootDepartment(Corporation corp) {
		String name = ROOT_NAME + corp.getId();
		Date now = DateUtil.getTime();
		Department dept = new Department();
		dept.setCorporation(corp);
	    dept.setName(name);
		dept.setLft(1);
		dept.setRgt(2);
		dept.setCreatedAt(now);
		dept.setDeleted(false);
		getEm().persist(dept);

		return dept;
	}

	/**
	 * Maintain index of Department after adding a new Department node.Every
	 * corporation maintain a series of independent index.
	 * 
	 * @param index
	 */
	public void maintainIndexAfterAddNode(int index, String corpId) {
		logger.debug(
				"Invoking method maintainIndexAfterAddNode, param[index={}, corpId={}]",
				new Object[] { index, corpId });
		getEm().createQuery(
				"UPDATE Department d SET d.lft = (d.lft+2) WHERE d.lft >= :index AND d.corporation.id =:corpId AND d.deleted =:deleted")
				.setParameter("index", index).setParameter("corpId", corpId).setParameter("deleted",false)
				.executeUpdate();
		getEm().createQuery(
				"UPDATE Department d SET d.rgt = (d.rgt+2) WHERE d.rgt >= :index AND d.corporation.id =:corpId AND d.deleted =:deleted")
				.setParameter("index", index).setParameter("corpId", corpId).setParameter("deleted",false)
				.executeUpdate();
	}

	/**
	 * Maintain index of Department after a leaf Department node.Every
	 * corporation maintain a series of independent index.
	 * 
	 * @param index
	 */
	public void maintainIndexAfterDeleteNode(int index, String corpId) {
		getEm().createQuery(
				"UPDATE Department d SET d.lft = (d.lft-2) WHERE d.lft >= :index AND d.corporation.id =:corpId AND d.deleted =:deleted")
				.setParameter("index", index).setParameter("corpId", corpId).setParameter("deleted",false)
				.executeUpdate();
		getEm().createQuery(
				"UPDATE Department d SET d.rgt = (d.rgt-2) WHERE d.rgt >= :index AND d.corporation.id =:corpId AND d.deleted =:deleted")
				.setParameter("index", index).setParameter("corpId", corpId).setParameter("deleted",false)
				.executeUpdate();
	}
	
	public  void  checkNoChildNode(){
		List<String> deptIdList= getEm().createQuery("select parent.id from Department  where deleted =:deleted").setParameter("deleted",false).getResultList();
		getEm()
				.createQuery("UPDATE  Department  set rgt=(lft+1) WHERE id not in(:deptIdList) ").setParameter("deptIdList", deptIdList).executeUpdate();
	}
	

	/**
	 * Find list of department by parent id.
	 * 
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Department> findDepartmentsByParentId(String deptId) {
		return getEm()
				.createQuery("FROM Department d WHERE d.parent.id =:deptId AND d.deleted =:deleted")
				.setParameter("deptId", deptId).setParameter("deleted", false).getResultList();
	}

	/**
	 * Find list of department by corporation id. It should not contain root
	 * department of the corporation.
	 * 
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Department> findDepartmentsByCoporationId(String corporationId) {
		return getEm()
				.createQuery(
						"FROM Department d WHERE d.corporation.id =:corpId  AND d.deleted =:deleted ")
				.setParameter("corpId", corporationId).setParameter("deleted", false).getResultList();
	}

	/**
	 * Find list of department by parent id and corporation id.
	 * 
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Department> findDepartmentsByParentIdAndCoporationId(
			String deptId, String corporationId) {
		return getEm()
				.createQuery(
						"FROM Department d WHERE d.parent.id =:deptId AND d.corporation.id =:corpId AND d.deleted =:deleted")
				.setParameter("deptId", deptId)
				.setParameter("corpId", corporationId).setParameter("deleted", false).getResultList();
	}



	/**
	 * Find list of department by index(lft and rgt).
	 * 
	 * @param lft
	 * @param rgt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Department> findDepartmentsByLefRgt(int lft, int rgt) {
		return getEm()
				.createQuery(
						"FROM Department d WHERE d.lft > :lft AND d.rgt < :rgt AND d.deleted =:deleted")
				.setParameter("lft", lft).setParameter("rgt", rgt).setParameter("deleted", false)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Set<String> findSiblingIds(String rootId, boolean includeRoot) {

		logger.debug(" Process in findSiblingIds, rootId:{}, includeRoot:{}",
				new Object[] { rootId, includeRoot });

		Set<String> result = new TreeSet<String>();
		List<String> currectList = new ArrayList<String>();

		currectList.add(rootId);

		StringBuffer eql = new StringBuffer();
		eql.append(" SELECT d.id FROM Department d WHERE d.parent.id IN (:parentIds)  AND d.deleted =:deleted");
		logger.debug(" EQL:{}", eql.toString());

		while (true) {
			Query query = getEm().createQuery(eql.toString());
			currectList = query.setParameter("parentIds", currectList).setParameter("deleted", false)
					.getResultList();
			logger.trace("currectList:{}", currectList);
			if (currectList == null || currectList.size() == 0) {
				break;
			} else {
				result.addAll(currectList);
			}
		}

		if (includeRoot) {
			result.add(rootId);
		}

		return result;
	}

	/**
	 * @param departmentIds
	 * @return
	 */
	public String mergeDepartment(String departmentIds,String departmentName,String leaderId) {
		
		

		
		return "";
	}

	@SuppressWarnings("unchecked")
	public List<Department> getDepartmentsOfCorporationAndKey(
			String corporationId, String name) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" SELECT d FROM Department d WHERE d.parent is not null AND d.deleted =:deleted");
		if (!StringUtil.isEmptyString(corporationId)) {
			hql.append(" AND d.corporation.id =:corpId  ");
			param.put("corpId", corporationId);
		}
		if (!StringUtil.isEmptyString(name)) {
			hql.append(" AND d.name LIKE :name  ");
			param.put("name", "%" + name + "%");
		}
		param.put("deleted",false);
		Query query = getEm().createQuery(hql.toString());
		for (String key : param.keySet()) {
			query.setParameter(key, param.get(key));
		}

		return query.getResultList();
	}

}
