package com.chinarewards.elt.service.sendmail;


/**
 * Service of corporation.
 * 
 * @author lw
 * @since 1.5
 */
public interface SendMailService {

	
	
	/**
	 * 发邮件
	 * @param 
	 * @return
	 */
	public String sendMail(String title,String info,String staffId);
	
}
