package com.chinarewards.elt.service.gift.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.gift.GiftDao;
import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.broadcast.BroadcastingVo;
import com.chinarewards.elt.model.broadcast.OrganType;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.gift.search.GiftListVo;
import com.chinarewards.elt.model.gift.search.GiftStatus;
import com.chinarewards.elt.model.information.BroadcastingCategory;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.broadcast.BroadcastService;
import com.chinarewards.elt.service.gift.GiftLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class GiftLogicImpl implements GiftLogic{
	private GiftDao giftDao;
	
	private BroadcastService broadcastService;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Inject
	protected GiftLogicImpl(GiftDao giftDao,BroadcastService broadcastService){
		this.giftDao = giftDao;
		this.broadcastService=broadcastService;
	}
	
	@Override
	public Gift save(SysUser caller, Gift gift) {
		Date currTime = DateUtil.getTime();
		
		if (StringUtil.isEmptyString(gift.getId())) {
			// Create			
			gift.setDeleted(false);
			gift.setRecorduser(caller.getUserName());
			gift.setRecorddate(currTime);
			gift.setStatus(GiftStatus.SHELVES);//新增的是上架的商品
			giftDao.save(gift);
			//礼品添加发送广播
			UserContext context=new UserContext();
			context.setCorporationId(caller.getCorporation().getId());
			context.setUserId(caller.getId());
			
			List<String[]> organList=new ArrayList<String[]>();
			
			BroadcastingVo vo=new BroadcastingVo();
			vo.setBroadcastingTimeStart(DateUtil.getTime());
			vo.setBroadcastingTimeEnd(DateUtil.getTime());
			vo.setAllowreplies(true);
			vo.setContent("新礼品:"+gift.getName()+" ,上架啦~快快拿出你的积分来兑换吧!");
			
			//接收对象为当前人机构(接收人加入获奖人)
			
			String[] nameAndId = new String[3];
			nameAndId[0] = caller.getCorporation().getId();
			nameAndId[1] = caller.getCorporation().getName();
			nameAndId[2] = OrganType.ORG.toString();
			organList.add(nameAndId);
			
			vo.setOrganList(organList);
			broadcastService.createOrUpdateBroadcast(vo, context, BroadcastingCategory.SYSBROADCAST);
			
		} else {
			// Update
			Gift tempGift = giftDao.findById(Gift.class, gift.getId());
			tempGift.setName(gift.getName());
			tempGift.setSummary(gift.getSummary());
			tempGift.setDispatchcycle(gift.getDispatchcycle());
			tempGift.setExplains(gift.getExplains());
			tempGift.setNotes(gift.getNotes());
			tempGift.setType(gift.getType());
			tempGift.setStatus(gift.getStatus());
			tempGift.setBrand(gift.getBrand());
			tempGift.setModel(gift.getModel());
			tempGift.setSource(gift.getSource());
			tempGift.setBusiness(gift.getBusiness());
			tempGift.setAddress(gift.getAddress());
			tempGift.setTell(gift.getTell());
			tempGift.setServicetell(gift.getServicetell());
			tempGift.setIntegral(gift.getIntegral());
			tempGift.setPrice(gift.getPrice());
			tempGift.setStock(gift.getStock());
			tempGift.setPhoto(gift.getPhoto());
			tempGift.setIndate(gift.getIndate());
		    tempGift.setUpdatetime(currTime);
		  
			giftDao.update(tempGift);
		}

		return gift;
	}
	

	@Override
	public Gift findGiftById(String id) {
		
		return  giftDao.findById(Gift.class, id);
	}

	@Override
	public String deleteGift(String id) {
		Gift gift = giftDao.findById(Gift.class, id);
		gift.setDeleted(true);
		gift= giftDao.update(gift);
		return gift.getId();
	}

	@Override
	public PageStore<GiftListVo> giftList(SysUser caller, GiftListVo giftVo) {
		
		PageStore<Gift> pageStore = new PageStore<Gift>();
		pageStore.setResultCount(giftDao.countGift(giftVo));
		List<Gift> giftList = giftDao.giftList(giftVo);
		List<GiftListVo> giftVoList = new ArrayList<GiftListVo>();
		for (Gift gift : giftList) {
			giftVoList.add(convertFromGiftToVo(gift));
		}
		PageStore<GiftListVo> storeVo = new PageStore<GiftListVo>();
		storeVo.setResultCount(pageStore.getResultCount());
		storeVo.setResultList(giftVoList);

		return storeVo;
	}
	
	@Override
	public List<GiftListVo> exportGiftList(SysUser caller, GiftListVo giftVo) {
		
//		PageStore<Gift> pageStore = new PageStore<Gift>();
//		pageStore.setResultCount(giftDao.countGift(giftVo));
		List<Gift> giftList = giftDao.giftList(giftVo);
		List<GiftListVo> giftVoList = new ArrayList<GiftListVo>();
		for (Gift gift : giftList) {
			giftVoList.add(convertFromGiftToVo(gift));
		}
//		PageStore<GiftListVo> storeVo = new PageStore<GiftListVo>();
//		storeVo.setResultCount(pageStore.getResultCount());
//		storeVo.setResultList(giftVoList);

//		return storeVo;
		return giftVoList;
	}
	
	
	private GiftListVo convertFromGiftToVo(Gift gift) {
		GiftListVo giftVo = new GiftListVo();
		giftVo.setAddress(gift.getAddress());
		giftVo.setBusiness(gift.getBusiness());
		giftVo.setDeleted(gift.isDeleted());
		giftVo.setExplains(gift.getExplains());
		giftVo.setId(gift.getId());
		giftVo.setIndate(gift.getIndate());
		giftVo.setName(gift.getName());
		giftVo.setPhoto(gift.getPhoto());
		giftVo.setSource(gift.getSource());
		giftVo.setStatus(gift.getStatus());
		giftVo.setStock(gift.getStock());
		giftVo.setTell(gift.getTell());
		giftVo.setType(gift.getType());
		giftVo.setModel(gift.getModel());

        giftVo.setRecorddate(gift.getRecorddate());
        giftVo.setRecorduser(gift.getRecorduser());
        giftVo.setUpdatetime(gift.getUpdatetime());
        giftVo.setIntegral(gift.getIntegral());
        giftVo.setPrice(gift.getPrice());
		return giftVo;
	}
	@Override
	public String updateStatus(String id,GiftStatus status) {
		Gift gift = giftDao.findById(Gift.class, id);
		gift.setStatus(status);
		gift= giftDao.update(gift);
		return gift.getId();
	}

	@Override
	public List<Gift> findNotDeleteGift() {
		return giftDao.findNotDeleteGift();
	}
	
	


}
