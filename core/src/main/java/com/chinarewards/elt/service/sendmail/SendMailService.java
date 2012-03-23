package com.chinarewards.elt.service.sendmail;

import java.util.List;


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
	
	/**
	 * 群发邮件
	 * @param 
	 * @return
	 */
	public String sendMailAll(String title,String info,List<String[]> organList);
	
}
