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
		String nodeText = "";
		if (doc != null) {
			NodeList nodeList = doc.getElementsByTagName(tagName);

			for (int i = 0; i < nodeList.getLength(); i++) {
				nodeText = nodeList.item(i).getNodeValue();
				System.out.println(nodeText);
			}
		} else {
			System.out.println("document is null...");
		}

		return nodeText;
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
