package com.chinarewards.elt.service.org.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.org.MembersDao;
import com.chinarewards.elt.dao.org.TeamDao;
import com.chinarewards.elt.domain.org.Members;
import com.chinarewards.elt.domain.org.Team;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.vo.TeamListVo;
import com.chinarewards.elt.service.org.TeamLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * 
 * 
 * @author lw
 * @since 1.0
 */
public class TeamLogicImpl implements TeamLogic {

	Logger logger = LoggerFactory.getLogger(getClass());

	TeamDao teamDao;
	MembersDao membersDao;
	

	@Inject
	public TeamLogicImpl(TeamDao teamDao,MembersDao membersDao) {
			this.teamDao = teamDao;
			this. membersDao = membersDao;
	}

	@Override
	public Team findTeamById(String id) {
		return teamDao.findById(Team.class, id);
	}
	@Override
	public String deleteTeam(String deptId)	throws Exception {
		Team team = teamDao.findById(Team.class, deptId);
		teamDao.delete(team);
		return team.getId();
	}

	
	
	@Override
	public Team save(SysUser caller, Team team) {
		Date currTime = DateUtil.getTime();
		String teamCode = DateUtil.formatData("yyyyMMddhhmmss", currTime);
		if (StringUtil.isEmptyString(team.getId())) {
			// Create
			team.setDeleted(0);//正常状态，没有删除为0
			team.setCreatedBy(caller);
			team.setCreatedAt(currTime);
			team.setCode(teamCode);//用当前时间作为编号
			teamDao.save(team);
			
			//增加成员还没有实现
		} else {
			// Update
			team = teamDao.findById(Team.class, team.getId());
			team.setCreatedBy(caller);
			team.setCreatedAt(currTime);
			teamDao.update(team);
		}

		return team;
	}
	
	@Override
	public PageStore<TeamListVo> teamList(SysUser caller,TeamListVo teamVo) {
        PageStore<Team> pageStore = new PageStore<Team>();
		
		pageStore.setResultCount(teamDao.countTeam(teamVo));
		List<Team> TeamList = teamDao.TeamList(teamVo);
		List<TeamListVo> TeamVoList = new ArrayList<TeamListVo>();
		for (Team team : TeamList) {
			TeamVoList.add(convertFromTeamToVo(team));
		}
		PageStore<TeamListVo> storeVo = new PageStore<TeamListVo>();
		storeVo.setResultCount(pageStore.getResultCount());
		storeVo.setResultList(TeamVoList);

		return storeVo;
	}

	private TeamListVo convertFromTeamToVo(Team team) {
		TeamListVo teamVo = new TeamListVo();
		teamVo.setId(team.getId());
		teamVo.setName(team.getName());
		teamVo.setCode(team.getCode());
		   	
		return teamVo;
	}

	@Override
	public Members saveMembers(SysUser caller, Members members) {
		Date currTime = DateUtil.getTime();
		
		if (StringUtil.isEmptyString(members.getId())) {
			// Create
			members.setDeleted(0);//正常状态，没有删除为0
			members.setRecorddate(currTime);
			members.setRecorduser(caller.getUserName());
			membersDao.save(members);
		
			
		} else {
			// Update
			members = membersDao.findById(Members.class, members.getId());
			members.setRecorddate(currTime);
			members.setRecorduser(caller.getUserName());
			membersDao.update(members);
		}

		return members;
	}
}
