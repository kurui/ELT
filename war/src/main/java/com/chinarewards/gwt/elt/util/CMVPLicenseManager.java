package com.chinarewards.gwt.elt.util;

import java.util.prefs.Preferences;

import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.LicenseManager;

/**
*客户端
 */
public class CMVPLicenseManager {
	public CMVPLicenseManager() {
	}

	/** The product id of your software */
	public static final String PRODUCT_ID = "cmvp20"; // CUSTOMIZE

	/**
	 * The subject for the license manager and also the alias of the private key
	 * entry in the keystore.
	 */
	public static final String SUBJECT = "privatekey"; // CUSTOMIZE 别名

	/** The resource name of your private keystore file. */
	public static final String KEYSTORE_RESOURCE = "publicCerts.store"; // CUSTOMIZE 公匙库文件名 

	/** The password for the keystore. */
	public static final String KEYSTORE_STORE_PWD = "store123"; // CUSTOMIZE 公匙库密码

	/** The password to encrypt the generated license key file. */
	public static final String CIPHER_KEY_PWD = "a8a8a8"; // CUSTOMIZE  license文件密码

	protected static final LicenseManager manager = new LicenseManager(
			new DefaultLicenseParam(SUBJECT,
					Preferences.userNodeForPackage(CMVPLicenseManager.class),
					new DefaultKeyStoreParam(
							CMVPLicenseManager.class, // CUSTOMIZE
							KEYSTORE_RESOURCE, SUBJECT, KEYSTORE_STORE_PWD,
							null),// 这里一定要是null
					new DefaultCipherParam(CIPHER_KEY_PWD)));

	/**
	 * NOTE: This main() method is never called by the actual key server. It is
	 * just useful for debugging the key generator.
	 */
	public static final void main(String args[]) {
		try {

			manager.install(new java.io.File("D:\\cert\\license.lic"));
			String subject = manager.verify().getSubject();
			System.out.println("subject========" + subject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
