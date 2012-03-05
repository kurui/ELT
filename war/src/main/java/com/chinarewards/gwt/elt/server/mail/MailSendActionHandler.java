package com.chinarewards.gwt.elt.server.mail;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.chinarewards.elt.util.JavaMailSend;
import com.chinarewards.gwt.elt.client.mail.model.MailVo;
import com.chinarewards.gwt.elt.client.mail.request.MailRequest;
import com.chinarewards.gwt.elt.client.mail.request.MailResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.google.inject.Inject;

public class MailSendActionHandler extends	BaseActionHandler<MailRequest, MailResponse> {

	
	@Inject
	public MailSendActionHandler() {
		
	}

	@Override
	public Class<MailRequest> getActionType() {
		return MailRequest.class;
	}

	@Override
	public MailResponse execute(MailRequest request,
			ExecutionContext context) throws DispatchException {
		    MailVo vo = request.getMailvo();
		    JavaMailSend jms=new JavaMailSend();
			String fileNames[] = {};
			String to[] = {vo.getEmailAddress()};
			String smtp="smtp.qq.com";
			String userService="649709649@qq.com";//用时修改邮箱地址和密码
			String pwdService ="******";
			String m=jms.sendmail(smtp,userService,pwdService,"注册账号通知",vo.getEmailAddress(),to,vo.getContent(),fileNames,"text/html;charset=UTF-8");
			String message ="邮件发送失败,请重新发送!";
			if(m.indexOf("发送成功")!=-1)
				message = "邮件发送成功";
		    MailResponse resp = new MailResponse();
		    resp.setToken(message);
		return resp;
	}

	@Override
	public void rollback(MailRequest action, MailResponse result,
			ExecutionContext context) throws DispatchException {

	}

}
