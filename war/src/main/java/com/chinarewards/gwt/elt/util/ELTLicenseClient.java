package com.chinarewards.gwt.elt.util;

import java.io.File;
import java.util.prefs.Preferences;

import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;

/**
 * 客户端
 */
public class ELTLicenseClient {
	public ELTLicenseClient() {
		// ELTLicenseClient.class.getResourceAsStream(arg0)
	}

	/** The product id of your software */
	public static final String PRODUCT_ID = "cmvp20"; // CUSTOMIZE

	public static final String PRIVATEKEY_SUBJECT = "privatekey"; // CUSTOMIZE
																	// 别名/*此处要和生成lic的subject对应*/
	public static final String PUBSTORE_SUBJECT = "publiccert"; // CUSTOMIZE 别名

	/** The resource name of your private keystore file. */
	// com/chinarewards/gwt/elt/util/
	// \\com\\chinarewards\\gwt\\elt\\util\\
	// /publicCerts.store 需要放于WEB-INF/classes目录
	public static final String KEYSTORE_RESOURCE = "/publicCerts.store"; // CUSTOMIZE
																			// 公匙库文件名
	// ELTLicenseClient.class.getResourceAsStream(arg0)

	/** The password for the keystore. */
	public static final String KEYSTORE_STORE_PWD = "store123"; // CUSTOMIZE
																// 公匙库密码

	/** The password to encrypt the generated license key file. */
	public static final String CIPHER_KEY_PWD = "a8a8a8"; // CUSTOMIZE
															// license文件密码

	protected static final LicenseManager manager = new LicenseManager(
			new DefaultLicenseParam(PRIVATEKEY_SUBJECT,
					Preferences.userNodeForPackage(ELTLicenseClient.class),
					new DefaultKeyStoreParam(
							ELTLicenseClient.class, // CUSTOMIZE
							KEYSTORE_RESOURCE, PUBSTORE_SUBJECT,
							KEYSTORE_STORE_PWD, null),// 这里一定要是null
					new DefaultCipherParam(CIPHER_KEY_PWD)));

	/**
	 * NOTE: This main() method is never called by the actual key server. It is
	 * just useful for debugging the key generator.
	 */
	public static final void main(String args[]) {
		try {
			String licensePath = getCertPath();

			manager.install(new java.io.File(licensePath + "license.lic"));
			// manager.install(new java.io.File("D:\\cert\\license2.lic"));
			LicenseContent content = manager.verify();

			String subject = content.getSubject();
			System.out.println("subject========" + subject);
			System.out.println(content.getInfo() + "--"
					+ content.getConsumerType() + "--" + content.getIssued()
					+ "--" + content.getNotAfter());

			System.out.println(getCertPath());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getCertPath() {
		String realPath = "";
		realPath = ELTLicenseClient.class.getResource("").getPath();

		System.out.println(realPath);

		if (!StringUtil.isEmpty(realPath)) {
			int rootIndex = realPath.indexOf("jboss-5.1.0.GA");
			if (rootIndex < 0) {
				rootIndex = realPath.indexOf("war");
			}

			if (rootIndex < 0) {
				return null;
			} else {
				realPath = realPath.substring(0, rootIndex);
			}

			int firstIndex = realPath.indexOf("/");
			if (firstIndex == 0) {
				realPath = realPath.substring(1, realPath.length());
			}

			System.out.println(realPath);

			realPath = realPath + "cert";
		}
		return realPath;
	}

}
