package com.chinarewards.elt.service.org;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.org.search.DepartmentListVo;
import com.chinarewards.elt.model.org.search.DepartmentStatus;
import com.chinarewards.elt.model.user.UserContext;

/**
 * Service of corporation.
 * 
 * @author yanrui
 * @since 1.5
 */
public interface DepartmentService {

	/**
	 * 保存
	 * @param context
	 * @param gift
	 * @return
	 */
	public Department save(UserContext context, Department gift);

	/**
	 * 查找根据ID
	 * @param id
	 * @return
	 */
	public Department findDepartmentById(String id);
	/**
	 * 删除部门根据ID
	 * @param id
	 * @return
	 */
	public String deleteDepartment(String id);
	/**
	 * 部门列表
	 * @param context
	 * @param gift
	 * @return
	 */
	public PageStore<DepartmentListVo> giftList(UserContext context,DepartmentListVo giftListVo);

	/**
	 * 上下架
	 * @param id
	 * @return
	 */
	public String updateStatus(String id,DepartmentStatus status);
}
