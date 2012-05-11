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
					.setParameter("corpId", corporationId)
					.setParameter("deleted", false).getSingleResult();
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
				.setParameter("index", index).setParameter("corpId", corpId)
				.setParameter("deleted", false).executeUpdate();
		getEm().createQuery(
				"UPDATE Department d SET d.rgt = (d.rgt+2) WHERE d.rgt >= :index AND d.corporation.id =:corpId AND d.deleted =:deleted")
				.setParameter("index", index).setParameter("corpId", corpId)
				.setParameter("deleted", false).executeUpdate();
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
				.setParameter("index", index).setParameter("corpId", corpId)
				.setParameter("deleted", false).executeUpdate();
		getEm().createQuery(
				"UPDATE Department d SET d.rgt = (d.rgt-2) WHERE d.rgt >= :index AND d.corporation.id =:corpId AND d.deleted =:deleted")
				.setParameter("index", index).setParameter("corpId", corpId)
				.setParameter("deleted", false).executeUpdate();
	}

	public void checkNoChildNode() {
		List<String> deptIdList = getEm()
				.createQuery(
						"select parent.id from Department  where deleted =:deleted")
				.setParameter("deleted", false).getResultList();
		getEm().createQuery(
				"UPDATE  Department  set rgt=(lft+1) WHERE id not in(:deptIdList) ")
				.setParameter("deptIdList", deptIdList).executeUpdate();
	}

	public void refactorDepartmentTree(String corporationId) {

		Department root = getRootDepartmentOfCorp(corporationId);
		root.setLft(1);
		root.setRgt(2);
		update(root);
		
		List<Department> childList = findDepartmentsByParentId(root.getId());

		refactorDepartment(root, childList);

	}

	public int refactorDepartment(Department root, List<Department> childList) {
		int thislft = root.getRgt() + 1;
		
		
		boolean isSuperRoot=false;
		int index=root.getName().indexOf("ROOT_DEPT");
		if(index>-1){
			isSuperRoot=true;
		}
		
		if(childList!=null&&childList.size()>0){
			int childListsize=childList.size();
			for (int i = 0; i < childList.size(); i++) {
				System.out.println("-------------for i:" + i);
				Department child = childList.get(i);

				child.setLft(thislft);
				child.setRgt(thislft + 1);
				child = update(child);

				thislft = thislft + 2;

				List<Department> newchildList = findDepartmentsByParentId(child.getId());

				if (newchildList != null && newchildList.size() > 0) {
					thislft=refactorDepartment(child, newchildList);
					child.setRgt(thislft);
					update(child);
					
					thislft=thislft+1;
					
					
				} 
				
				if(isSuperRoot&&i==childListsize-1){
					root.setRgt(thislft);
					update(root);
				}
			}
		}
		
		return thislft;
	}

	private List<Department> refactorChildren(List<Department> deptList) {
		List<Department> sameLevenChildList = new ArrayList<Department>();
		int thislft = 0;
		int lastRgt = 0;

		for (int k = 0; k < deptList.size(); k++) {
			Department root = deptList.get(k);

			thislft = root.getLft() + 1;
			lastRgt = root.getRgt();
			List<Department> childList = getAllChildren(root.getId(), false);
			sameLevenChildList.addAll(childList);

			if (childList != null && childList.size() > 0) {
				for (int i = 0; i < childList.size(); i++) {
					Department child = childList.get(i);
					if (child != null) {
						logger.debug("================refresh lft rgt===============");
						child.setLft(thislft);
						child.setRgt(thislft + 1);

						update(child);

						List tempList = findDepartmentsByParentId(child.getId());
						if (tempList != null) {
							refactorChildren(tempList);
						} else {
							thislft = thislft + 2;

							lastRgt = child.getRgt() + 1;
						}
					}
				}
			}
			root.setRgt(lastRgt);
			update(root);
		}

		return sameLevenChildList;
	}

	public List<Department> getAllChildrenByCorpId(String corpId) {
		Department department = getRootDepartmentOfCorp(corpId);
		String deptId = department.getId();
		return getAllChildren(deptId, false);
	}

	public List<String> getAllChildrenIdsByCorpId(String corpId) {
		List<String> list = new ArrayList<String>();
		List<Department> depts = getAllChildrenByCorpId(corpId);
		for (Department dept : depts) {
			list.add(dept.getId());
		}
		return list;
	}
	
	public List<Department> getAllChildren(String deptId, boolean containItSelf) {
		Department dept = findById(Department.class, deptId);
		
		List<Department> depts=new ArrayList<Department>();

		if(dept!=null){
			depts =findDepartmentsByLefRgt(dept.getLft(),dept.getRgt());
		}
		
		if(containItSelf){
			depts.add(dept);
		}
		
		
		return depts;
	}

	public List<Department> getAllChildren_backup(String deptId, boolean containItSelf) {
		Department dept = findById(Department.class, deptId);

		List<Department> depts = findDepartmentsByParentId(deptId);

		if (depts != null && depts.size() > 0) {
			for (int i = 0; i < depts.size(); i++) {
				Department child = depts.get(i);
				if (child != null) {
					List<Department> tempChilds = findDepartmentsByParentId(child
							.getId());
					if (tempChilds != null && tempChilds.size() > 0) {
						depts.addAll(tempChilds);

						for (int j = 0; j < tempChilds.size(); j++) {
							Department child2 = tempChilds.get(j);
							if (child2 != null) {
								List<Department> tempChilds2 = findDepartmentsByParentId(child2
										.getId());
								if (tempChilds2 != null
										&& tempChilds2.size() > 0) {
									depts.addAll(tempChilds2);

									for (int k = 0; k < tempChilds2.size(); k++) {
										Department child3 = tempChilds.get(k);
										if (child3 != null) {
											List<Department> tempChilds3 = findDepartmentsByParentId(child3
													.getId());
											if (tempChilds3 != null
													&& tempChilds3.size() > 0) {
												depts.addAll(tempChilds3);

											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (containItSelf) {
			depts.add(dept);
		}

		return depts;
	}

	public List<String> getAllChildrenIds(String deptId, boolean containItSelf) {
		List<String> list = new ArrayList<String>();
		List<Department> depts = getAllChildren(deptId, containItSelf);
		for (Department dept : depts) {
			list.add(dept.getId());
		}
		return list;
	}

	public List<String> getAllChildrenNames(String deptId, boolean containItSelf) {
		List<String> list = new ArrayList<String>();
		List<Department> depts = getAllChildren(deptId, containItSelf);
		for (Department dept : depts) {
			list.add(dept.getName());
		}
		return list;
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
				.createQuery(
						"FROM Department d WHERE d.parent.id =:deptId AND d.deleted =:deleted")
				.setParameter("deptId", deptId).setParameter("deleted", false)
				.getResultList();
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
				.setParameter("corpId", corporationId)
				.setParameter("deleted", false).getResultList();
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
				.setParameter("corpId", corporationId)
				.setParameter("deleted", false).getResultList();
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
				.setParameter("lft", lft).setParameter("rgt", rgt)
				.setParameter("deleted", false).getResultList();
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
			currectList = query.setParameter("parentIds", currectList)
					.setParameter("deleted", false).getResultList();
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
	public String mergeDepartment(String departmentIds, String departmentName,
			String leaderId) {

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
		param.put("deleted", false);
		Query query = getEm().createQuery(hql.toString());
		for (String key : param.keySet()) {
			query.setParameter(key, param.get(key));
		}

		return query.getResultList();
	}

	public Department findDepartmentsByName(String name) {
		try {
			return (Department) getEm()
					.createQuery(
							"FROM Department d WHERE d.name =:name AND d.deleted =:deleted")
					.setParameter("name", name).setParameter("deleted", false)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
