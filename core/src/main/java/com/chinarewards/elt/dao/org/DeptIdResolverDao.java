/**
 * 
 */
package com.chinarewards.elt.dao.org;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.org.Department;

/**
 * For resolving hierarchical department query.
 * <p>
 * <b>This class should not </b> depend on other DAO.
 * 
 * @author Cream
 * @since 0.2.0
 */
public class DeptIdResolverDao extends BaseDao<Department> {

	/**
	 * Resolve all sub-department IDs rooted from the given rootId, which is a
	 * department ID.
	 * 
	 * @param rootId
	 * @param includeRoot
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Set<String> findSiblingIds(String rootId, boolean includeRoot) {

		logger.debug(" Process in findSiblingIds, rootId:{}, includeRoot:{}",
				new Object[] { rootId, includeRoot });

		Set<String> result = new TreeSet<String>();
		List<String> currectList = new ArrayList<String>();

		currectList.add(rootId);

		StringBuffer eql = new StringBuffer();
		eql.append(" SELECT dept.id FROM Department dept WHERE dept.parent.id IN (:parentIds) ");
		logger.debug(" EQL:{}", eql.toString());

		while (true) {
			Query query = getEm().createQuery(eql.toString());
			currectList = query.setParameter("parentIds", currectList)
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

	public List<String> findAllParentIds(String deptId, boolean includeItself) {
		logger.debug(
				" Process in findAllParentIds, deptId:{}, includeItSelf:{}",
				new Object[] { deptId, includeItself });

		List<String> result = new ArrayList<String>();
		Department dept = findById(Department.class, deptId);

		if (includeItself) {
			result.add(dept.getId());
		}

		while (null != dept.getParent()) {
			result.add(dept.getParent().getId());
			dept = dept.getParent();
		}
		return result;
	}
}
