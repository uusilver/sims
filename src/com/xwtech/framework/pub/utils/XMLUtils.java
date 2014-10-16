package com.xwtech.framework.pub.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个使用JDOM读取XML文件的工具类。
 * 这个类的内部封装了JDOM的具体实现，提供了常用的一些方法，避免直接使用JDOM的API。
 * 同时，也提供了方法可以返回JDOM的一些基本类型。目前的版本中还没有实现关于Namespace
 * 的操作。<br/>
 * <pre>
 * <b>使用了XPath，所以必须使用JDOM-beta9及以上的版本。</b><br/>
 * XML需要引入的包：jdom.jar，xerces.jar，xml-apis.jar，xalan.jar，
 * jaxen-core.jar，jaxen-jdom.jar，saxpath.jar<br/>
 * Log需要引入的包：commons-logging.jar
 * </pre>
 * <pre>
 * 基本示例：<br/>
 *      String filePath = "c:/xx/xxx.xml";
 *      XMLUtil util = XMLUtil.getInsance(filePath);
 *      Element element = util.getSingleElement("/root/elemA/elemB");
 *      String text = util.getSingleElementText("/root/elemA/elemB");
 * </pre>
 *
 * @author Backham Yu
 */
public class XMLUtils
{

	protected Logger log = LoggerFactory.getLogger(getClass());//日志

	private Document doc = null;
	//用于快速查询的cache
	@SuppressWarnings("unchecked")
	private Map<String, Element> lookupCache = new HashMap();

	/**
	 * 仅供测试使用
	 * @param doc
	 */
	XMLUtils(Document doc)
	{
		this.doc = doc;
		if (log.isDebugEnabled())
		{
			log.debug("使用了仅供测试的Constructor。");
		}
	}

	/**
	 * 私有的Constructor
	 * @param is 流文件
	 * @param validate 是否需要验证
	 * @throws XMLException
	 */
	private XMLUtils(InputStream is, boolean validate) throws Exception
	{
		SAXBuilder builder = null;
		if (validate)
		{
			if (log.isInfoEnabled())
			{
				log.info("需要使用文档验证，可以使用Schema或者DTD。");
			}
			//为了支持Schema，必须进行下面的处理
			builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser", true);
			builder.setFeature("http://apache.org/xml/features/validation/schema", true);
			builder.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation",
					"http://www.w3.org/2001/XMLSchema-instance");
		} else
		{
			builder = new SAXBuilder();
		}

		try
		{
			doc = builder.build(is);
		} catch (Exception e)
		{
			log.error("解析XML文件发生异常！", e);
			throw e;
		}
	}

	/**
	 * 得到一个工具类的实例。默认不需要验证。
	 * @param filePath 需要读取的文件路径
	 * @return XMLUtil
	 * @throws XMLException
	 */
	public static XMLUtils getInsance(String filePath) throws Exception
	{
		return getInsance(filePath, false);
	}

	/**
	 * 得到一个工具类的实例。
	 * <br/><b>如果指明需要Schema验证，XML文件中指明Schema的路径分隔符号
	 * 必须使用左斜线</b>
	 * @param filePath 需要读取的文件路径
	 * @param validate 是否需要验证
	 * @return XMLUtil
	 * @throws XMLException
	 */
	public static XMLUtils getInsance(String filePath, boolean validate) throws Exception
	{
		try
		{
			return getInsance(new FileInputStream(filePath), validate);
		} catch (FileNotFoundException e)
		{
			throw e;
		}
	}

	/**
	 * 得到一个工具类的实例。默认不需要验证。
	 * @param is 流文件
	 * @return XMLUtil
	 * @throws XMLException
	 */
	public static XMLUtils getInsance(InputStream is) throws Exception
	{
		return getInsance(is, false);
	}

	/**
	 * 得到一个工具类的实例。
	 * <br/><b>如果指明需要验证，只能使用DTD</b>
	 * @param is 流文件
	 * @param validate
	 * @return XMLUtil
	 * @throws Exception
	 */
	public static XMLUtils getInsance(InputStream is, boolean validate) throws Exception
	{
		return new XMLUtils(is, validate);
	}

	/**
	 * 把XML文档的内容输出到一个给定的流对象中。默认编码GB2312
	 * @param stream 给定的输出流对象
	 * @throws Exception
	 */
	public void writeToStream(OutputStream stream) throws Exception
	{
		writeToStream(stream, "gb2312");
	}

	/**
	 * 把XML文档的内容输出到一个给定的流对象中。
	 * @param stream 给定的输出流对象
	 * @param encoding 指定字符编码。
	 * @throws Exception
	 */
	public void writeToStream(OutputStream stream, String encoding) throws Exception
	{
		//表现形式的常量

		final String indent = "  ";
		final boolean newLines = true;

		try
		{
			XMLOutputter out = new XMLOutputter(indent, newLines, encoding);
			out.output(doc, stream);
		} catch (Exception e)
		{
			log.error("输出文件到指定的流对象发生异常！", e);
			throw e;
		}
	}

	/**
	 * 返回所有满足XPath条件的节点元素集合。
	 * @param xpath
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getAllElements(String xpath) throws Exception
	{
		List<Element> elements = null;
		try
		{
			elements = XPath.selectNodes(doc, xpath);
		} catch (JDOMException e)
		{
			log.error("获取节点元素集合发生异常！XPath: " + xpath, e);
			throw e;
		}
		return elements;
	}

	/**
	 * 返回满足XPath条件的第一个节点元素。
	 * @param xpath
	 * @return Element
	 * @throws Exception
	 */
	public Element getSingleElement(String xpath) throws Exception
	{
		//所有查询单个元素的方法都调用了这个方法，所以只在这里使用cache
		if (lookupCache.containsKey(xpath))
		{
			return (Element) lookupCache.get(xpath);
		} else
		{
			Element element = null;
			try
			{
				element = (Element) XPath.selectSingleNode(doc, xpath);
			} catch (JDOMException e)
			{

				log.error("获取节点元素发生异常！XPath: " + xpath, e);

				throw e;
			}
			lookupCache.put(xpath, element);
			return element;
		}
	}

	/**
	 * 返回满足XPath条件的第一个节点元素的内容，字符串格式
	 * @param xpath
	 * @return String。如果指定的元素不存在，返回null。
	 * @throws Exception
	 */
	public String getSingleElementText(String xpath) throws Exception
	{
		Element element = getSingleElement(xpath);
		if (element == null)
		{
			return null;
		} else
		{
			return element.getTextTrim();
		}
	}

	/**
	 * 返回满足XPath条件的第一个节点元素的指定属性。
	 * @param xpath
	 * @param attrName
	 * @return Attribute
	 * @throws Exception
	 */
	public Attribute getElementAttribute(String xpath, String attrName) throws Exception
	{
		if (getSingleElement(xpath) == null)
		{
			return null;
		} else
		{
			return getSingleElement(xpath).getAttribute(attrName);
		}
	}

	/**
	 * 返回满足XPath条件的第一个节点元素的指定属性的内容值。
	 * @param xpath
	 * @param attrName
	 * @return String 属性的内容值，如果指定的属性不存在，返回null
	 * @throws Exception
	 */
	public String getElementAttributeValue(String xpath, String attrName) throws Exception
	{
		Attribute attr = getElementAttribute(xpath, attrName);
		if (attr == null)
		{
			return null;
		} else
		{
			return attr.getValue().trim();
		}
	}

	/**
	 * 在指定的元素下面增加一个元素。
	 * @param xpath 指定的元素
	 * @param elemName 增加元素的名称
	 * @param elemText 增加元素的内容
	 * @throws Exception
	 */
	public void addElement(String xpath, String elemName, String elemText) throws Exception
	{
		Element parent = getSingleElement(xpath);
		parent.addContent(new Element(elemName).addContent(elemText));
	}

	/**
	 * 使指定位置的元素从他的上级脱离。并且返回这个元素。如果没有上级，不作任何删除
	 * 的操作。
	 * @param xpath
	 * @return 被修改的元素
	 * @throws Exception
	 */
	public Content removeElement(String xpath) throws Exception
	{
		lookupCache.remove(xpath);
		Element element = getSingleElement(xpath);
		if (element.isRootElement())
		{
			return element;
		} else
		{
			return element.detach();
		}
	}

	/**
	 * 改变指定元素的文本内容。
	 * @param xpath 指定元素
	 * @param elemText 需要设置的文本
	 * @throws Exception 如果指定的元素不存在
	 */
	public void setElementText(String xpath, String elemText) throws Exception
	{
		Element element = getSingleElement(xpath);
		if (element == null)
		{
			throw new IllegalArgumentException("指定的元素XPath:" + xpath + "不存在！");
		} else
		{
			element.setText(elemText);
		}
	}

	/**
	 * 在指定路径的元素上增加一个属性。如果同名属性已经存在，重新设置这个属性的值。
	 * @param xpath
	 * @param attrName
	 * @param attrValue
	 * @throws Exception
	 */
	public void setAttribute(String xpath, String attrName, String attrValue) throws Exception
	{
		Element element = getSingleElement(xpath);
		try
		{
			element.setAttribute(attrName, attrValue);
		} catch (Exception e)
		{
			log.error("设置节点元素的属性发生异常！", e);
			throw e;
		}
	}

	/**
	 * 删除指定元素的指定属性。
	 * @param xpath
	 * @param attrName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean removeAttribute(String xpath, String attrName) throws Exception
	{
		Element element = getSingleElement(xpath);
		if (element == null)
		{
			return false;
		} else
		{
			return element.removeAttribute(attrName);
		}
	}
}
