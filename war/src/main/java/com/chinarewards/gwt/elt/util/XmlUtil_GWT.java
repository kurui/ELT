package com.chinarewards.gwt.elt.util;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class XmlUtil_GWT {

	public static void main(String[] args) {
		Document doc = parseXml("<root><node>333</node></root>");
		System.out.println(getSingleNodeText(doc, "node"));
	}

	public static String getSingleNodeText(Document doc, String tagName) {
		String itemValue = "";
		if (doc != null) {

			NodeList nodeList = doc.getElementsByTagName(tagName);

			for (int i = 0; i < nodeList.getLength(); i++) {
				// System.out.println("node item:" + nodeList.item(i));
				// System.out.println(nodeList.item(i).getFirstChild()
				// .getNodeValue());
				// System.out.println(nodeList.item(i).toString());
				itemValue = nodeList.item(i).getFirstChild().getNodeValue();
				return itemValue;
			}
		} else {
			System.out.println("document is null...");
		}

		return itemValue;
	}

	public static Document parseXml(String contents) {
		Document doc = null;
		try {
			doc = XMLParser.parse(contents);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}
}
