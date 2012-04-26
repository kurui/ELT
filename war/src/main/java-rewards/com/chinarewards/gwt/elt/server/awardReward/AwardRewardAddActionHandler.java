package com.chinarewards.gwt.elt.server.awardReward;

import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.model.reward.vo.RewardWinVo;
import com.chinarewards.elt.service.reward.RewardService;
import com.chinarewards.elt.service.sendmail.SendMailService;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddResponse;
import com.chinarewards.gwt.elt.model.ClientException;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * The handler to correspond the NominateAddActionHandler
 * 
 * @author nicho
 * @since 2011年12月21日 16:12:47
 */
public class AwardRewardAddActionHandler extends
		BaseActionHandler<AwardRewardAddRequest, AwardRewardAddResponse> {

	@InjectLogger
	Logger logger;

	RewardService rewardService;
	SendMailService sendMailService;
	@Inject
	public AwardRewardAddActionHandler(RewardService rewardService,SendMailService sendMailService) {
		this.rewardService = rewardService;
		this.sendMailService=sendMailService;
	}

	@Override
	public AwardRewardAddResponse execute(AwardRewardAddRequest request,
			ExecutionContext response) throws DispatchException {
		AwardRewardAddResponse awardresponse = new AwardRewardAddResponse();
		String lot ="";
		if("DETERMINEWINNERS".equals(request.getFal()))
		{
			List<String> deleteStaffNameList = rewardService.getIsDeleteStaff(request.getStaffIds());
			if (deleteStaffNameList.size() > 0) {
				String message = "";
				for (String name : deleteStaffNameList) {
					message += name + "已经离职;";
				}
				message += "请重新选择获奖人!";
				throw new ClientException(message);
			}

			 lot = rewardService.determineWinner(request.getNowUserId(), request.getRewardId(),request.getStaffIds());
			
		}
		else if("AWARDREWARDPAGE".equals(request.getFal()))
		{
			RewardWinVo lotPre = rewardService.awardRewardWinner(request.getNowUserId(), request.getRewardId());
			
			try {
				
				lot=lotPre.getReward().getId();
				String winStaffName="";
				List<Winner> preStaff=lotPre.getWinner();
	
				for (int i = 0; i < preStaff.size(); i++) {
					winStaffName+=preStaff.get(i).getStaff().getName()+";";
				}
				if(!StringUtil.isEmpty(winStaffName))
				{
					 if(request.isEmailBox())
					 {
						 //邮件提醒
						 if(request.getMessageStaffId()!=null && request.getMessageStaffId().size()>0)
						 {
							 for (String staffId:request.getMessageStaffId()) {
								 sendMailService.sendMail("通知电贺提醒",lotPre.getReward().getName()+"已经颁奖,获奖人:"+winStaffName, staffId);
							}
						 }
					 }
					 else if(request.isMessageBox())
					 {
						 //短信提醒(未实现)
					 }
				}
			} catch (Exception e) {
				logger.debug("邮件发送失败............");
			}
		}
		awardresponse.setLotId(lot);
		

		return awardresponse;
	}

	@Override
	public Class<AwardRewardAddRequest> getActionType() {
		return AwardRewardAddRequest.class;
	}

	@Override
	public void rollback(AwardRewardAddRequest request,
			AwardRewardAddResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
