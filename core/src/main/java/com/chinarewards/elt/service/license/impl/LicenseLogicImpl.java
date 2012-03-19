package com.chinarewards.elt.service.license.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.license.LicenseDao;
import com.chinarewards.elt.domain.license.License;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.license.search.LicenseListVo;
import com.chinarewards.elt.model.license.search.LicenseStatus;
import com.chinarewards.elt.service.license.LicenseLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class LicenseLogicImpl implements LicenseLogic{
	private LicenseDao licenseDao;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Inject
	protected LicenseLogicImpl(LicenseDao licenseDao){
		this.licenseDao = licenseDao;
	}
	
	@Override
	public License save(SysUser caller, License license) {
		Date currTime = DateUtil.getTime();
		
		if (StringUtil.isEmptyString(license.getId())) {
			// Create			
			license.setDeleted(false);
			license.setRecorduser(caller.getUserName());
			license.setRecorddate(currTime);
			license.setStatus(LicenseStatus.SHELVES);//新增的是上架的商品
			licenseDao.save(license);
		} else {
			// Update
			License tempLicense = licenseDao.findById(License.class, license.getId());
			tempLicense.setName(license.getName());
			tempLicense.setSummary(license.getSummary());
			tempLicense.setDispatchcycle(license.getDispatchcycle());
			tempLicense.setExplains(license.getExplains());
			tempLicense.setNotes(license.getNotes());
			tempLicense.setType(license.getType());
			tempLicense.setBrand(license.getBrand());
			tempLicense.setSource(license.getSource());
			tempLicense.setBusiness(license.getBusiness());
			tempLicense.setAddress(license.getAddress());
			tempLicense.setTell(license.getTell());
			tempLicense.setServicetell(license.getServicetell());
			tempLicense.setIntegral(license.getIntegral());
			tempLicense.setStock(license.getStock());
			tempLicense.setPhoto(license.getPhoto());
			tempLicense.setIndate(license.getIndate());
		    tempLicense.setUpdatetime(currTime);
			licenseDao.update(tempLicense);
		}

		return license;
	}
	

	@Override
	public License findLicenseById(String id) {
		
		return  licenseDao.findById(License.class, id);
	}

	@Override
	public String deleteLicense(String id) {
		License license = licenseDao.findById(License.class, id);
		license.setDeleted(true);
		license= licenseDao.update(license);
		return license.getId();
	}

	@Override
	public PageStore<LicenseListVo> licenseList(SysUser caller, LicenseListVo licenseVo) {
		
		PageStore<License> pageStore = new PageStore<License>();
		pageStore.setResultCount(licenseDao.countLicense(licenseVo));
		List<License> licenseList = licenseDao.licenseList(licenseVo);
		List<LicenseListVo> licenseVoList = new ArrayList<LicenseListVo>();
		for (License license : licenseList) {
			licenseVoList.add(convertFromLicenseToVo(license));
		}
		PageStore<LicenseListVo> storeVo = new PageStore<LicenseListVo>();
		storeVo.setResultCount(pageStore.getResultCount());
		storeVo.setResultList(licenseVoList);

		return storeVo;
	}
	private LicenseListVo convertFromLicenseToVo(License license) {
		LicenseListVo licenseVo = new LicenseListVo();
		licenseVo.setAddress(license.getAddress());
		licenseVo.setBusiness(license.getBusiness());
		licenseVo.setDeleted(license.isDeleted());
		licenseVo.setExplains(license.getExplains());
		licenseVo.setId(license.getId());
		licenseVo.setIndate(license.getIndate());
		licenseVo.setName(license.getName());
		licenseVo.setPhoto(license.getPhoto());
		licenseVo.setSource(license.getSource());
		licenseVo.setStatus(license.getStatus());
		licenseVo.setStock(license.getStock());
		licenseVo.setTell(license.getTell());
		licenseVo.setType(license.getType());
        licenseVo.setRecorddate(license.getRecorddate());
        licenseVo.setRecorduser(license.getRecorduser());
        licenseVo.setUpdatetime(license.getUpdatetime());
        licenseVo.setIntegral(license.getIntegral());
		return licenseVo;
	}
	@Override
	public String updateStatus(String id,LicenseStatus status) {
		License license = licenseDao.findById(License.class, id);
		license.setStatus(status);
		license= licenseDao.update(license);
		return license.getId();
	}

}
