package com.chinarewards.elt.service.license;

import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public LicenseContent queryLicenseContent() {
		LicenseContent content=null;
		try {
System.out.println(LicenseLogicImpl.class.getResource(""));
			String licensePath = ELTLicenseUtil.getCertPath();

			final String PRIVATEKEY_SUBJECT = "privatekey"; //
			final String PUBSTORE_SUBJECT = "publiccert"; //
			final String KEYSTORE_RESOURCE = "/publicCerts.store"; //
			///F:/project/elt/core/target/classes/com/chinarewards/elt/service/license/ 
			// publicCerts.store 放于src/license下，打包时如何
			final String KEYSTORE_STORE_PWD = "store123"; // CUSTOMIZE
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
			content = manager.verify();

			String subject = content.getSubject();
			System.out.println("subject========" + subject);
			System.out.println(content.getInfo() + "--"
					+ content.getConsumerType() + "--" + content.getIssued()
					+ "--" + content.getNotAfter());
		} catch (Exception e) {
			e.printStackTrace();
			content=null;
		}
		
		return content;
	}
}
