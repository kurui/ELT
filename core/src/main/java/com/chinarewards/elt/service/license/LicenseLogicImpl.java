package com.chinarewards.elt.service.license;

import java.util.prefs.Preferences;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.model.vo.LicenseBo;
import com.chinarewards.elt.util.StringUtil;
import com.chinarewards.elt.util.XmlUtil_dom4j;
import com.google.inject.Inject;

import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;

public class LicenseLogicImpl implements LicenseLogic {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	protected LicenseLogicImpl() {

	}

	@Override
	public LicenseBo queryLicenseContent() {
		LicenseBo licenseBo=new  LicenseBo();
		LicenseContent content = null;
		try {
			String licensePath = ELTLicenseUtil.getCertPath();

			final String PRIVATEKEY_SUBJECT = "privatekey"; //
			final String PUBSTORE_SUBJECT = "publiccert"; //

			// /F:/project/elt/core/target/classes/com/chinarewards/elt/service/license/
			final String KEYSTORE_RESOURCE = "publicCerts"; //

			// F:/project/elt/core/target/classes/
			// final String KEYSTORE_RESOURCE = "/publicCerts.store"; //

			//
			final String KEYSTORE_STORE_PWD = "publicstore123"; // CUSTOMIZE
			final String CIPHER_KEY_PWD = "a8a8a8"; //

			LicenseManager manager = new LicenseManager(
					new DefaultLicenseParam(PRIVATEKEY_SUBJECT,
							Preferences
									.userNodeForPackage(ELTLicenseUtil.class),
							new DefaultKeyStoreParam(
									ELTLicenseUtil.class, // CUSTOMIZE
									KEYSTORE_RESOURCE, PUBSTORE_SUBJECT,
									KEYSTORE_STORE_PWD, null),// 这里一定要是null
							new DefaultCipherParam(CIPHER_KEY_PWD)));

			manager.install(new java.io.File(licensePath + "license.lic"));
//			manager.install(new java.io.File(licensePath + "license201203301001193147.lic"));
			
			content = manager.verify();

			String subject = content.getSubject();
			System.out.println("subject========" + subject);
			System.out.println(content.getInfo() + "--"
					+ content.getConsumerType() + "--" + content.getIssued()
					+ "--" + content.getNotAfter()+"=="+content.getIssuer().getName());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			content = null;
		}

		licenseBo=adapter(content);
		
		return licenseBo;
	}
	
	public LicenseBo adapter(LicenseContent content){
		LicenseBo licenseBo=new LicenseBo();
		
		if (content!=null) {
			licenseBo.setNotafter(content.getNotAfter());
			licenseBo.setIssued(content.getIssued());
			
			String contentInfo=content.getInfo();
			if (!StringUtil.isEmptyString(contentInfo)) {
				Document doc=XmlUtil_dom4j.readResult(new StringBuffer(contentInfo));
				if(doc!=null){
					String licenseId=XmlUtil_dom4j.getTextByNode(doc,"//root/licenseId");
					String corporationId=XmlUtil_dom4j.getTextByNode(doc,"//root/corporationId");
					String corporationName=XmlUtil_dom4j.getTextByNode(doc,"//root/corporationName");		
					String licenseType=XmlUtil_dom4j.getTextByNode(doc,"//root/license-type");
					String macaddress=XmlUtil_dom4j.getTextByNode(doc,"//root/macaddress");
					String description=XmlUtil_dom4j.getTextByNode(doc,"//root/description");
					
					
					licenseBo.setLicenseId(licenseId);
					licenseBo.setCorporationId(corporationId);
					licenseBo.setCorporationName(corporationName);
					licenseBo.setLicenseType(licenseType);
					licenseBo.setMacaddress(macaddress);
					licenseBo.setDescription(description);
				}
			}
		}
		
		return licenseBo;
	}

	public static void main(String[] args) {
		LicenseLogicImpl imp = new LicenseLogicImpl();
		imp.queryLicenseContent();
	}
}
