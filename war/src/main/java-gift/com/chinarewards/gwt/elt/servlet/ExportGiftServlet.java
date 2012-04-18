package com.chinarewards.gwt.elt.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinarewards.elt.model.gift.search.GiftListVo;
import com.chinarewards.elt.model.gift.search.GiftStatus;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.service.gift.GiftService;
import com.chinarewards.gwt.elt.adapter.gift.GiftAdapter;
import com.chinarewards.gwt.elt.client.gift.model.GiftClient;
import com.chinarewards.gwt.elt.util.FileExcelUtil;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since 2012-4-17
 * */
public class ExportGiftServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	GiftService giftService;

	public void init() throws ServletException {
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		GiftListVo giftListVo = buildGiftListVo(request);

		UserContext uc = buildUserContext(request);
		
		String exportFileType=request.getParameter("exportFileType");

		List<GiftListVo> giftVoList = giftService
				.exportGiftList(uc, giftListVo);
		
		List<GiftClient> clientList=GiftAdapter.adapter(giftVoList);
		
		doExportFile(clientList,exportFileType, response);

	}

	private void doExportFile(List<GiftClient> clientList,
			String exportFileType,HttpServletResponse response) {
		ArrayList<Object> title = new ArrayList<Object>();
		title.add("名称");
		title.add("来源");
		title.add("兑换缤纷");
		title.add("采购价");
		title.add("库存");
		title.add("状态");

		String sheetName = "GIFT";
		ArrayList<ArrayList<Object>> lists = new ArrayList<ArrayList<Object>>();
		
		for (int i = 0; i < clientList.size(); i++) {
			GiftClient client=clientList.get(i);
			
			ArrayList<Object> list=new ArrayList<Object>();
			
			list.add(client.getName());
			list.add(client.getSourceText());
			list.add(client.getIntegral()+"");
			list.add("采购价");
			list.add(client.getInventory());
			list.add(client.getStatus().getDisplayName());
			
			lists.add(list);
		}

		if ("XLS".equals(exportFileType)) {
			FileExcelUtil.createXLSFile(title, lists, sheetName, response);
		}else if("CSV".equals(exportFileType)){
			FileExcelUtil.createCSVFile(sheetName+".csv", title,lists,response);
		}
		

	}

	private UserContext buildUserContext(HttpServletRequest request) {
		UserContext context = new UserContext();
		if (!StringUtil.isEmpty(request.getParameter("corporationId"))) {
			context.setCorporationId(request.getParameter("corporationId"));
		}

		if (!StringUtil.isEmpty(request.getParameter("userRole"))) {
			String userRole = request.getParameter("userRole");
			List<UserRole> cli = new ArrayList<UserRole>();
			cli.add(UserRole.valueOf(userRole));
			UserRole[] userRoles = cli.toArray(new UserRole[0]);
			context.setUserRoles(userRoles);
		}
		if (!StringUtil.isEmpty(request.getParameter("userId"))) {
			context.setUserId(request.getParameter("userId"));
		}

		return context;
	}

	private GiftListVo buildGiftListVo(HttpServletRequest request) {
		GiftListVo listVo = new GiftListVo();
		if (!StringUtil.isEmpty(request.getParameter("name"))) {
			listVo.setName(request.getParameter("name"));
		}

		if (!StringUtil.isEmpty(request.getParameter("status"))) {
			listVo.setStatus(GiftStatus.valueOf(request.getParameter("status")));
		}

		return listVo;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void destroy() {
	}

}
