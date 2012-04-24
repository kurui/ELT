package com.chinarewards.elt.model.gift.dataexchange;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ImportGiftXmlUtils {
	
	public final static String INSTRUCTION_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
			+ "<instruction><title>true</title><dob-format>yy-MM-dd</dob-format><doe-format>yy-MM-dd</doe-format></instruction>";
	
	public final static String PRETREATMENT_RESULT_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
		+ "<pretreatment><Gift-total>100</Gift-total><Gift-success>50</Gift-success>"
		+ "<member-card-auto-new>40</member-card-auto-new><member-card-assign-new>5</member-card-assign-new></pretreatment>";
	
	public final static String FINAL_IMPORT_RESULT_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
		+ "<final-import><Gift-total>100</Gift-total><Gift-success>50</Gift-success>"
		+ "<member-card-auto-new>40</member-card-auto-new><member-card-assign-new>5</member-card-assign-new></final-import>";
	
	public static String doc2String(Document document) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF8");
			DOMSource source = new DOMSource(document);
			OutputStream os = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(os);
			transformer.transform(source, result);
			return os.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Document string2Doc(String content) {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(content.getBytes("UTF8"));
			document = builder.parse(is);
			document.normalize();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * return the text content of a tag in a xml document.
	 */
	public static String getXmlNodeValue(Document doc, String tag) {
		if (doc == null) {
			return null;
		}

		NodeList list = doc.getElementsByTagName(tag);
		if (list.getLength() == 0) {
			return null;
		}

		Node node = list.item(0);
		if (node.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}

		String ret = "";
		NodeList textNodes = node.getChildNodes();
		for (int i = 0; i < textNodes.getLength(); i++) {
			Node n = textNodes.item(i);
			if (n.getNodeType() == Node.TEXT_NODE
					&& n.getNodeValue().replaceAll("[ \\n\\t\\r]", "").length() > 0) {
				ret += n.getNodeValue();
			} else if (n.getNodeType() == Node.CDATA_SECTION_NODE) {
				ret += n.getNodeValue();
			}
		}
		return ret.length() == 0 ? null : ret.replaceAll("^\\s+", "")
				.replaceAll("\\s+$", "");
	}

	public static void setXmlNodeValue(Document doc, String tag, String value) {
		Node root = doc.getDocumentElement();
		if (root.hasChildNodes()) {
			NodeList cnodes = root.getChildNodes();
			for (int i = 0; i < cnodes.getLength(); i++) {
				if (cnodes.item(i).getNodeType() == Node.ELEMENT_NODE
						&& cnodes.item(i).getNodeName() == tag) {
					cnodes.item(i).getFirstChild().setNodeValue(value);
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {
		String test2 = INSTRUCTION_TEMPLATE;
		Document document = string2Doc(test2);

		System.out.println("" + getXmlNodeValue(document, "title"));

		setXmlNodeValue(document, "title", "false");

		System.out.println("xml content:");
		System.out.println(doc2String(document));
	}
}
