package com.mdcl.jdom.server;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

public class MakeMessageToXMLTree {

public String inputMessagebyTemplete(String TempleteFile) {
		SAXBuilder builder = new SAXBuilder();
		String ret = "";

		try {
			Document myDoc = builder.build(TempleteFile);
			// System.out.println("adsfsadf");
			Element pageElmt = (Element) XPath.selectSingleNode(myDoc
					.getRootElement(), "page");
			((Element) XPath.selectSingleNode(pageElmt, "docType"))
			.setText("REQ");
			((Element) XPath.selectSingleNode(pageElmt, "applyNo"))
					.setText("AAA");
			((Element) XPath.selectSingleNode(pageElmt, "applyDate"))
					.setText("BBB");
			((Element) XPath.selectSingleNode(pageElmt, "applyDepartment"))
					.setText("CCC");
			((Element) XPath.selectSingleNode(pageElmt, "budgetaryType"))
					.setText("DDD");
			((Element) XPath.selectSingleNode(pageElmt, "Title")).setText("EEE");
			// ......finnish by yourself......

			// .....do List for more message
			Element listElmt = (Element) XPath.selectSingleNode(pageElmt,
					"List");
			// use clone for more items
			Element itemsElmt = (Element) XPath.selectSingleNode(listElmt,
					"items");

			((Element) XPath.selectSingleNode(itemsElmt, "rowNo"))
					.setText("1");

			Element itemsElmt2 = (Element) itemsElmt.clone();
			listElmt.addContent(itemsElmt2);
			((Element) XPath.selectSingleNode(itemsElmt2, "rowNo"))
					.setText("2");

			// doXMLDoc(myDoc,"c:/mydoc2.xml");
			ret = doXMLStr(myDoc);
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static void doXMLDoc(Document myDoc, String sDestFN) {
//		Format fmt = Format.getPrettyFormat();
//		fmt.setEncoding("GBK");
//		XMLOutputter outputter = new XMLOutputter(fmt);
		FileOutputStream fout;
		try {
			XMLOutputter outputter = new XMLOutputter(" ", true, "GBK");
			fout = new FileOutputStream(sDestFN);
			outputter.output(myDoc, fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String doXMLStr(Document myDoc) {
//		Format fmt = Format.getPrettyFormat();
//		fmt.setEncoding("GBK");
//		XMLOutputter outputter = new XMLOutputter(fmt);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			XMLOutputter outputter = new XMLOutputter(" ", true, "GBK");
			outputter.output(myDoc, bout);

			return bout.toString("GBK");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// return bout.toString("GBK" );
	}

	public static void main(String[] args) {
		MakeMessageToXMLTree makeMessageToXMLTree = new MakeMessageToXMLTree();
		makeMessageToXMLTree.inputMessagebyTemplete("c:/ebuyapply.xml");
		System.out.println("run ok...");
	}
}
