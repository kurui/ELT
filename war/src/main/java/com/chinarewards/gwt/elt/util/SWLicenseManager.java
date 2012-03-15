package com.chinarewards.gwt.elt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.security.auth.x500.X500Principal;

import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * Server
 */
public class SWLicenseManager {

	/**
	 * NOTE: This main() method is never called by the actual key server. It is
	 * just useful for debugging the key generator.
	 */
	public static final void main(String args[]) {
		KeyMain(args);
		/*
		 * try{ manager.install(new java.io.File("swutil.log"));
		 * System.out.println("subject=" + manager.verify().getSubject());
		 * System.exit(0); }catch(Exception ex){ ex.printStackTrace(); }
		 */

		LicenseParam parameter = new DefaultLicenseParam(SUBJECT,
				Preferences.userRoot(), new DefaultKeyStoreParam(
						SWLicenseManager.class, // CUSTOMIZE
						KEYSTORE_RESOURCE, SUBJECT, KEYSTORE_STORE_PWD,
						KEYSTORE_KEY_PWD), new DefaultCipherParam(
						CIPHER_KEY_PWD));
		LicenseContent content = new LicenseContent();
		// content.setHolder(holder);
		// content.setIssuer(issuer);
		content.setConsumerType("User");
		content.setConsumerAmount(1);
		// content.setInfo(props.toString());
		content.setSubject(SUBJECT);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(2012, 10, 10);//截止有效期
		content.setNotAfter(cal.getTime());

		SWLicenseManager manager = new SWLicenseManager();
		manager.createLicenseKey(parameter, content);//创建License
		
		manager.verifyLicenseKey(parameter);

		
	}

	public SWLicenseManager() {
	}

	LicenseContent verifyLicenseKey(LicenseParam parameter) {
		// Create a configured license manager.
		LicenseManager manager = new LicenseManager(parameter);
		try {
			// Verify the previously installed current license certificate.
			return manager.verify();
		} catch (Exception exc) {
			System.err.println("Could not verify license key");
			exc.printStackTrace();
			return null;
		}
	}

	void installLicenseKey(LicenseParam parameter, File keyFile) {
		// Create a configured license manager.
		LicenseManager manager = new LicenseManager(parameter);
		try {
			// Load the license key, verify and install it as the current
			// license key.
			manager.install(keyFile);
		} catch (Exception exc) {
			System.err.println("Could not install license certificate");
			exc.printStackTrace();
		}
	}

	void createLicenseKey(LicenseParam parameter, LicenseContent content) {
		// Create a configured license manager.
		LicenseManager manager = new LicenseManager(parameter);
		try {
			// Create the license key from the license content and save it to a
			// file.
			manager.store(content, new File("D:\\cert\\"+"license.lic"));
		} catch (Exception exc) {
			System.err.println("Could not save license key");
			exc.printStackTrace();
		}
	}

	//
	// Customizable global properties.
	//

	/** The product id of your software */
	public static final String PRODUCT_ID = "cmvp20"; // PRODUCT_ID

	/**
	 * The subject for the license manager and also the alias of the private key
	 * entry in the keystore.
	 */
	public static final String SUBJECT = "privatekey"; // CUSTOMIZE

	/** The resource name of your private keystore file. */
	public static final String KEYSTORE_RESOURCE = "privateKeys.store"; // 私匙库文件名

	/** The password for the keystore. */
	public static final String KEYSTORE_STORE_PWD = "store123"; // 私匙库密码

	/* The password for the private key entry in the keystore. */
	public static final String KEYSTORE_KEY_PWD = "key123"; // 私匙库主键密码

	/** The password to encrypt the generated license key file. */
	public static final String CIPHER_KEY_PWD = "a8a8a8"; // 即将生成的license密码

	/**
	 * The filename to be displayed for the generated binary key file when
	 * delivered. Please note that this is not used to write to a file of this
	 * name.
	 */
	public static final String DISPLAY_FILENAME = "license.lic"; // license.lic

	//
	// The rest of this key generator does not need to get customized.
	//

	/** The MIME type of the generated binary key file. */
	public static final String MIME_TYPE = "application/octet-stream";

	//
	// Possible key generator exit codes
	//

	/**
	 * Return <code>ERC_SUCCESS</code> on succesful creation of a textual key.
	 * Note that this example creates a binary key and thus this constant is not
	 * used here
	 */
	// public static final int ERC_SUCCESS = 00;

	/**
	 * Return <code>ERC_SUCCESS_BIN</code> on succesful creation of a binary
	 * key. (Which could contain text as well, if the content type is specified
	 * as <code>text/plain</code>)
	 */
	public static final int ERC_SUCCESS_BIN = 01;

	/**
	 * Return <code>ERC_ERROR</code> for general errors.
	 */
	public static final int ERC_ERROR = 10;

	/**
	 * Return <code>ERC_MEMORY</code> if memory allocation fails.
	 */
	public static final int ERC_MEMORY = 11;

	/**
	 * Return <code>ERC_FILE_IO</code> on IOException
	 */
	public static final int ERC_FILE_IO = 12;

	/**
	 * Return <code>ERC_BAD_ARGS</code> if the command line parameters are bad.
	 */
	public static final int ERC_BAD_ARGS = 13;

	/**
	 * Return <code>ERC_BAD_INPUT</code> if a particular input value is missing
	 * or has a bad value. Don't forget to supply a meaningful error message
	 * naming the exact cause of the error.
	 */
	public static final int ERC_BAD_INPUT = 14;

	/**
	 * Return <code>ERC_EXPIRED</code> if this generator is expired. This can be
	 * used to limit the lifetime of this generator.
	 */
	// public static final int ERC_EXPIRED = 15;

	/**
	 * Return <code>ERC_INTERNAL</code> if an unhandled exception occurs.
	 * 
	 * @see java.lang.Exception
	 */
	// public static final int ERC_INTERNAL = 16;

	/** Encoding keys in properties. */
	public static final String ENCODING_KEY = "ENCODING";
	public static final String PRODUCT_ID_KEY = "PRODUCT_ID";
	public static final String PURCHASE_ID_KEY = "PURCHASE_ID";
	public static final String RUNNING_NO_KEY = "RUNNING_NO";
	public static final String PURCHASE_DATE_KEY = "PURCHASE_DATE";
	public static final String LANGUAGE_ID_KEY = "LANGUAGE_ID";
	public static final String QUANTITY_KEY = "QUANTITY";
	public static final String REG_NAME_KEY = "REG_NAME";
	public static final String ADDITIONAL1_KEY = "ADDITIONAL1";
	public static final String ADDITIONAL2_KEY = "ADDITIONAL2";
	public static final String RESELLER_KEY = "RESELLER";
	public static final String LASTNAME_KEY = "LASTNAME";
	public static final String FIRSTNAME_KEY = "FIRSTNAME";
	public static final String COMPANY_KEY = "COMPANY";
	public static final String EMAIL_KEY = "EMAIL";
	public static final String PHONE_KEY = "PHONE";
	public static final String FAX_KEY = "FAX";
	public static final String STREET_KEY = "STREET";
	public static final String ZIP_KEY = "ZIP";
	public static final String CITY_KEY = "CITY";
	public static final String STATE_KEY = "STATE";
	public static final String COUNTRY_KEY = "COUNTRY";

	/** Default encoding for properties. */
	public static final String ENCODING_PROPERTIES = "ISO-8859-1";

	/** Default share-it encoding if key not present in properties. */
	public static final String ENCODING_DEFAULT = ENCODING_PROPERTIES;

	protected static final LicenseManager manager = new LicenseManager(
			new DefaultLicenseParam(SUBJECT, Preferences.userRoot(),
					new DefaultKeyStoreParam(
							SWLicenseManager.class, // CUSTOMIZE
							KEYSTORE_RESOURCE, SUBJECT, KEYSTORE_STORE_PWD,
							KEYSTORE_KEY_PWD), new DefaultCipherParam(
							CIPHER_KEY_PWD)));

	/**
	 * Validates the properties and generates a license certificate file.
	 */
	private static void generateLicense(Properties props, File certFile)
			throws Exception {
		// Check for supported product id.
		final String productId = props.getProperty(PRODUCT_ID_KEY);
		if (!PRODUCT_ID.equals(productId)) {
			throw new BadInputException("Bad product ID: " + productId);
		}

		final StringBuffer dn = new StringBuffer();
		addAttribute(
				dn,
				"CN",
				props.getProperty(FIRSTNAME_KEY) + ' '
						+ props.getProperty(LASTNAME_KEY));
		if (dn.length() == 0) {
			addAttribute(dn, "CN", props, REG_NAME_KEY);
		}
		addAttribute(dn, "O", props, COMPANY_KEY);
		addAttribute(dn, "STREET", props, STREET_KEY);
		addAttribute(dn, "L",
				props.getProperty(ZIP_KEY) + ' ' + props.getProperty(CITY_KEY));
		addAttribute(dn, "ST", props, STATE_KEY);
		addAttribute(dn, "C", props, COUNTRY_KEY);
		final X500Principal holder = new X500Principal(dn.toString());
		final X500Principal issuer = new X500Principal(
				"OU=share-it!,O=element 5 AG,STREET=Vogelsanger Strasse 78,L=50823 K\u00F6ln,ST=Nordrhein-Westfalen,C=DE");

		final LicenseContent content = new LicenseContent();
		content.setHolder(holder);
		content.setIssuer(issuer);
		content.setConsumerType("User");
		content.setConsumerAmount(1);
		content.setInfo(props.toString());
		content.setSubject(SUBJECT);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(2011, 10, 10);
		content.setNotAfter(cal.getTime());
		manager.store(content, certFile);
	}

	private static final void addAttribute(final StringBuffer dn,
			final String oid, final Properties props, final String key) {
		addAttribute(dn, oid, props.getProperty(key));
	}

	private static void addAttribute(final StringBuffer dn, final String oid,
			String value) {
		if (value == null) {
			return;
		}

		final String trimmedValue = value.trim();
		if ("".equals(trimmedValue)) {
			return;
		}

		// See http://www.ietf.org/rfc/rfc2253.txt
		boolean quote = false;
		if (!value.equals(trimmedValue)) {
			quote = true;
		} else if (value.matches(".*[+,;<>\"].*")) {
			;
		}
		quote = true;

		if (dn.length() != 0) {
			dn.append(',');
		}
		dn.append(oid);
		dn.append('=');
		if (quote) {
			dn.append('"');
		}
		// Replace every single backslash with two backslashes
		// whereas both parameters are expressed as regular expressions.
		value = value.replaceAll("\\\\", "\\\\\\\\");
		// Replace every single quote with an escaped quote
		// whereas both parameters are expressed as regular expressions.
		value = value.replaceAll("\"", "\\\\\"");
		dn.append(value);
		if (quote) {
			dn.append('"');
		}
	}

	private static Properties readInput(String pathname) throws IOException {
		Properties props = new EncodedProperties();
		InputStream in = new FileInputStream("D:\\cert\\" + pathname);
		try {
			props.load(in);
		} catch (IllegalArgumentException iae) {
			throw new BadInputException(iae);
		} finally {
			in.close();
		}

		return props;
	}

	/**
	 * This is the main entry point for JAVA key generators. It processes the
	 * command line arguments, loads and parses the input file, calls the key
	 * generator and writes output files.
	 * 
	 * JAVA Exceptions are handled and transformed into key generator error
	 * codes. Exception messages will be written to <code>args[1]</code> and
	 * display on the error console.
	 */
	public static int KeyMain(String args[]) {
		args = new String[3];
		args[0] = "license.properties";
		args[1] = "license.status";
		args[2] = "license.lic";

		if (args.length != 3) {
			System.err.println("Usage: <input> <output1> <output2>");

			return ERC_BAD_ARGS;
		}

		int errorCode = ERC_ERROR;
		PrintWriter out = new PrintWriter(System.err);
		try {
			try {
				// Read input and get encoding
				final Properties props = readInput(args[0]);
				final String encoding = props.getProperty(ENCODING_KEY,
						ENCODING_DEFAULT);

				// Setup real output with encoding read from input file.
				out = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(args[1]), encoding));

				// Validate input and generate key file.
				generateLicense(props, new File(args[2]));

				// Write status.
				out.write(MIME_TYPE + ":" + DISPLAY_FILENAME);
			} catch (BadInputException bie) {
				errorCode = ERC_BAD_INPUT;
				throw bie;
			} catch (IOException ioe) {
				errorCode = ERC_FILE_IO;
				throw ioe;
			} catch (OutOfMemoryError oome) {
				errorCode = ERC_MEMORY;
				throw oome;
			}
		} catch (Throwable t) {
			out.println("Error #" + errorCode);
			t.printStackTrace(out);

			return errorCode;
		} finally {
			out.close();
		}

		return ERC_SUCCESS_BIN;
	}

	public static class EncodedProperties extends Properties {
		public EncodedProperties() {
			this(new Properties());
		}

		/**
		 * @throws NullPointerException
		 *             if <tt>defaults</tt> is <tt>null</tt>.
		 */
		public EncodedProperties(Properties defaults) {
			super(defaults);

			// Make sure we have a proper default for the encoding.
			defaults.setProperty(ENCODING_KEY, ENCODING_DEFAULT);
		}

		public void load(InputStream inStream) throws IOException {
			super.load(inStream);

			String encoding = super.getProperty(ENCODING_KEY);
			if (ENCODING_PROPERTIES.equals(encoding)) {
				return;
			}

			// Convert properties
			try {
				Map.Entry[] entries = new Map.Entry[entrySet().size()];
				entrySet().toArray(entries);
				for (int i = entries.length; --i >= 0;) {
					Map.Entry entry = entries[i];
					String value = (String) entry.getValue();
					value = new String(value.getBytes(ENCODING_PROPERTIES),
							encoding);
					setProperty((String) entry.getKey(), value);
				}
			} catch (UnsupportedEncodingException ignored) {
			}
		}
	}

	public static class BadInputException extends IOException {
		public BadInputException(String message) {
			super(message);
		}

		public BadInputException(Throwable cause) {
			initCause(cause);
		}
	}

}
