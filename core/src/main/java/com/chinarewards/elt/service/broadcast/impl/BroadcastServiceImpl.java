package com.chinarewards.elt.service.broadcast.impl;

import java.util.List;

import com.chinarewards.elt.dao.broadcast.BroadcastingReceivingDao;
import com.chinarewards.elt.dao.broadcast.ReceivingObjectDao;
import com.chinarewards.elt.domain.information.Broadcasting;
import com.chinarewards.elt.domain.information.BroadcastingReceiving;
import com.chinarewards.elt.domain.information.DepartmentObject;
import com.chinarewards.elt.domain.information.OrgObject;
import com.chinarewards.elt.domain.information.ReceivingObject;
import com.chinarewards.elt.domain.information.StaffObject;
import com.chinarewards.elt.domain.information.TeamObject;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListCriteria;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListVo;
import com.chinarewards.elt.model.broadcast.BroadcastingVo;
import com.chinarewards.elt.model.broadcast.OrganType;
import com.chinarewards.elt.model.information.BroadcastingCategory;
import com.chinarewards.elt.model.information.BroadcastingStatus;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.broadcast.BroadcastLogic;
import com.chinarewards.elt.service.broadcast.BroadcastService;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.org.TeamLogic;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class BroadcastServiceImpl implements BroadcastService {

	private final BroadcastLogic broadcastLogic;
	private final CorporationLogic corporationLogic;
	private final UserLogic userLogic;
	private final StaffLogic staffLogic;
	private final DepartmentLogic departmentLogic;
	private final BroadcastingReceivingDao broadcastingReceivingDao;
	private final ReceivingObjectDao receivingObjectDao;
	private final TeamLogic teamLogic;

	@Inject
	public BroadcastServiceImpl(BroadcastLogic broadcastLogic,
			CorporationLogic corporationLogic, UserLogic userLogic,
			StaffLogic staffLogic, DepartmentLogic departmentLogic,
			BroadcastingReceivingDao broadcastingReceivingDao,ReceivingObjectDao receivingObjectDao,TeamLogic teamLogic) {
		this.broadcastLogic = broadcastLogic;
		this.corporationLogic = corporationLogic;
		this.userLogic = userLogic;
		this.staffLogic = staffLogic;
		this.departmentLogic = departmentLogic;
		this.broadcastingReceivingDao = broadcastingReceivingDao;
		this.receivingObjectDao=receivingObjectDao;
		this.teamLogic=teamLogic;
	}

	@Override
	public BroadcastQueryListVo queryBroadcastList(
			BroadcastQueryListCriteria criteria) {
		return broadcastLogic.queryBroadcastList(criteria);
	}

	@Override
	public String createOrUpdateBroadcast(BroadcastingVo broadcast,
			UserContext context) {
		Broadcasting broadcastBo = null;
		SysUser nowUser = userLogic.findUserById(context.getUserId());
		if (StringUtil.isEmptyString(broadcast.getBroadcastingId())) {
			broadcastBo = new Broadcasting();
			broadcastBo.setContent(broadcast.getContent());
			broadcastBo.setBroadcastingTimeStart(broadcast
					.getBroadcastingTimeStart());
			broadcastBo.setBroadcastingTimeEnd(broadcast
					.getBroadcastingTimeEnd());
			broadcastBo.setAllowreplies(broadcast.isAllowreplies());
			broadcastBo.setNumber("编号");
			broadcastBo.setStatus(BroadcastingStatus.HASBROADCAST);
			broadcastBo.setCategory(BroadcastingCategory.COMPANYBROADCAST);
			broadcastBo.setCorporation(corporationLogic
					.findCorporationById(context.getCorporationId()));
			broadcastBo.setCreatedAt(DateUtil.getTime());
			broadcastBo.setCreatedBy(nowUser);
			broadcastBo.setDeleted(false);
			broadcastBo.setLastModifiedAt(DateUtil.getTime());
			broadcastBo.setLastModifiedBy(nowUser);
			broadcastBo.setReplyNumber(0);

		} else {
			broadcastBo = broadcastLogic.findbroadcastingById(broadcast
					.getBroadcastingId());
			broadcastBo.setContent(broadcast.getContent());
			broadcastBo.setBroadcastingTimeStart(broadcast
					.getBroadcastingTimeStart());
			broadcastBo.setBroadcastingTimeEnd(broadcast
					.getBroadcastingTimeEnd());
			broadcastBo.setAllowreplies(broadcast.isAllowreplies());
			broadcastBo.setLastModifiedAt(DateUtil.getTime());
			broadcastBo.setLastModifiedBy(nowUser);
			
		}
		Broadcasting broadcastNew = broadcastLogic.createOrUpdateBroadcast(broadcastBo);
		// 处理接收对象保存
		List<String[]> organList = broadcast.getOrganList();
		if (organList.size() > 0) {
			for (int i = 0; i < organList.size(); i++) {
				String[] organ = organList.get(i);
				ReceivingObject receivingObj=null;
				if (OrganType.valueOf(organ[2].toString()) == OrganType.STAFF) {
					StaffObject staff = new StaffObject();
					staff.setId(organ[0]);
					staff.setName(organ[1]);
					staff.setStaff(staffLogic.findStaffById(organ[0]));
					receivingObj=receivingObjectDao.save(staff);
				} else if (OrganType.valueOf(organ[2].toString()) == OrganType.DEPT) {
					DepartmentObject dept = new DepartmentObject();
					dept.setId(organ[0]);
					dept.setName(organ[1]);
					dept.setDept(departmentLogic.findDepartmentById(organ[0]));
					receivingObj=receivingObjectDao.save(dept);
				} else if (OrganType.valueOf(organ[2].toString()) == OrganType.GROUP) {
					TeamObject team=new TeamObject();
					team.setId(organ[0]);
					team.setName(organ[1]);
					team.setTeam(teamLogic.findTeamBoById(organ[0]));
					receivingObj=receivingObjectDao.save(team);
				} else if (OrganType.valueOf(organ[2].toString()) == OrganType.ORG) {
					OrgObject org=new OrgObject();
					org.setId(organ[0]);
					org.setName(organ[1]);
					org.setCorporation(corporationLogic.findCorporationById(organ[0]));
					receivingObj=receivingObjectDao.save(org);
				}
				
				BroadcastingReceiving broadcastReceiving=new BroadcastingReceiving();
				broadcastReceiving.setBroadcast(broadcastNew);
				broadcastReceiving.setReceiving(receivingObj);
				broadcastReceiving.setCreatedAt(DateUtil.getTime());
				broadcastReceiving.setCreatedBy(nowUser);
				broadcastReceiving.setLastModifiedAt(DateUtil.getTime());
				broadcastReceiving.setLastModifiedBy(nowUser);
				broadcastingReceivingDao.save(broadcastReceiving);
			}

		}


		return null;
	}

}
