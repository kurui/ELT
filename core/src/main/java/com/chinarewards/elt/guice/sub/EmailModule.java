package com.chinarewards.elt.guice.sub;

import com.google.inject.AbstractModule;

/**
 * It is used to send sms or send email.
 * 
 * @author yanxin
 * @since 0.0.1 2010-09-21
 */
public class EmailModule extends AbstractModule {

	@Override
	protected void configure() {
		//bind(Session.class).in(Singleton.class);

	//	bind(EmailSender.class).to(JavaMailEmailSender.class).in(Singleton.class);

		
	}

}
