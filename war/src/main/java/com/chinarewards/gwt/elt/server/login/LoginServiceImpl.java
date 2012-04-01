package com.chinarewards.gwt.elt.server.login;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserSessionVo;
import com.chinarewards.elt.model.user.UserStatus;
import com.chinarewards.elt.model.vo.LicenseBo;
import com.chinarewards.elt.service.license.LicenseService;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.elt.service.user.UserService;
import com.chinarewards.gwt.elt.client.remote.login.LoginService;
import com.chinarewards.gwt.elt.client.support.UserSession;
import com.chinarewards.gwt.elt.model.ClientException;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The server side implementation of the RPC service.
 */
@Singleton
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserService userService;
	LicenseService licenseService;
	IStaffService staffService;
	/**
	 * 校验码 KEY
	 */
	protected static final String CODE_SESSION_KEY = com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

	@Inject
	public LoginServiceImpl(UserService userService,IStaffService staffService,LicenseService licenseService) {
		this.userService = userService;
		this.staffService=staffService;
		this.licenseService=licenseService;
	}
	public LoginServiceImpl() {
		
	}
	@Override
	public UserSession authLogin(String username, String password,
			String verifyCode) throws ClientException {
		// check verification code (kaptcha)
		HttpSession session = getThreadLocalRequest().getSession();
		String code = (String) session.getAttribute(CODE_SESSION_KEY);
		if (!verifyCode.equalsIgnoreCase(code)) {
			throw new ClientException("验证码错误");
		}
		
	
		UserSession resp = new UserSession();
		UserSessionVo u = userService.authenticate(username,password);
		
		if (u != null) {
			if(u.getUserRoles().size()<=0)
			{
				throw new ClientException("用户无角色!");
			}
			if(u.getUserStatus()==UserStatus.Inactive)
			{
				throw new ClientException("您已离职,拒绝进入!");
			}
			resp.setCorporationId(u.getCorporationId());
			resp.setCorporationName(u.getCorporationName());
			resp.setPhoto(u.getPhoto());
			resp.setLoginName(u.getUsername());
			resp.setToken(u.getId());
			resp.setDepartmentId(u.getDepartmentId());
			resp.setUserRoles(UserRoleTool.adaptToRoleVo(u.getUserRoles()));
			resp.setStaffId(u.getStaffId());
			if(u.getLastLoginRole()!=null)
			{
				resp.setLastLoginRole(UserRoleVo.valueOf(u.getLastLoginRole().toString()));
			}
			
			
			UserContext context=new UserContext();
			context.setCorporationId(u.getCorporationId());

			LicenseBo licensebo=null;
			try {
//				 licensebo=licenseService.queryLicenseContent();
				licensebo=new LicenseBo();
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.MARCH, calendar.get(Calendar.YEAR)+1);
				licensebo.setNotafter(calendar.getTime());
				 licensebo.setStaffNumber(50);
			} catch (Exception e) {
				throw new ClientException("获取License异常,请联系管理员!");
			}
		
			if(licensebo==null)
			{
				throw new ClientException("获取License为空,请联系管理员!");
			}
			if(licensebo.getNotafter()==null)
			{
				throw new ClientException("获取License过期时间为空,请联系管理员!");
			}
			if("FAILED".equals(licensebo))
			{
				throw new ClientException(licensebo.getErrorInfo()+",请联系管理员!");
			}
			if(licensebo.getNotafter()!=null && licensebo.getNotafter().getTime()<=new Date().getTime())
			{
				throw new ClientException("软件License已过期!请重新申请!");
			}
			Integer number=staffService.findNotDeleteStaffNumber(context);
			if(number!=null && number.intValue()>licensebo.getStaffNumber())
			{
				throw new ClientException("当前在职用户数: "+number+" 人,已经超过软件License最大用户数 "+licensebo.getStaffNumber()+" !已非法!请重新申请!");
			}

		} else {
			throw new ClientException("用户名或密码错误!");
		}
		
		return resp;
	}
}
