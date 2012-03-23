package com.chinarewards.gwt.elt.server.mail;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.sendmail.SendMailService;
import com.chinarewards.gwt.elt.client.mail.model.MailVo;
import com.chinarewards.gwt.elt.client.mail.request.MailRequest;
import com.chinarewards.gwt.elt.client.mail.request.MailResponse;
import com.chinarewards.gwt.elt.client.mail.request.MailSendAllRequest;
import com.chinarewards.gwt.elt.client.mail.request.MailSendAllResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

public class MailSendAllActionHandler extends	BaseActionHandler<MailSendAllRequest, MailSendAllResponse> {
	@InjectLogger
	Logger logger;
	SendMailService sendMailService;
	
	@Inject
	public MailSendAllActionHandler(SendMailService sendMailService) {
        this.sendMailService = sendMailService;
	}

	@Override
	public Class<MailSendAllRequest> getActionType() {
		return MailSendAllRequest.class;
	}

	@Override
	public MailSendAllResponse execute(MailSendAllRequest request,
			ExecutionContext context) throws DispatchException {
		    MailVo vo = request.getMailvo();
		    String message = sendMailService.sendMailAll(vo.getTitle(),vo.getContent(), request.getOrganList());
		    MailSendAllResponse resp = new MailSendAllResponse();
		    resp.setToken(message);
		return resp;
	}

	@Override
	public void rollback(MailSendAllRequest action, MailSendAllResponse result,
			ExecutionContext context) throws DispatchException {

	}

}
