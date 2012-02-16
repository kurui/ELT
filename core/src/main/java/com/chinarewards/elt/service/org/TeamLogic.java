package com.chinarewards.elt.service.org;

import com.chinarewards.elt.domain.org.Members;
import com.chinarewards.elt.domain.org.Team;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.vo.TeamListVo;

/**
 * 
 * 
 * @author lw
 * @since 1.0
 */
public interface TeamLogic {

	/**
	 * 保存
	 * @param user
	 * @param team
	 * @return
	 */
	public Team save(SysUser user, Team team);
   
	public Members saveMembers(SysUser caller, Members members);
	/**
	 * 查找根据ID
	 * @param id
	 * @return
	 */
	public Team findTeamById(String id);
	/**
	 * 删除组根据ID
	 * @param id
	 * @return
	 * @throws TeamDeleteException 
	 */
	public String deleteTeam(String id) throws Exception;
	/**
	 * 组列表
	 * @param user
	 * @param team
	 * @return
	 */
	public PageStore<TeamListVo> teamList(SysUser user,TeamListVo teamListVo);

	
	
	
}
