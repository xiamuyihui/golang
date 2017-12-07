package com.kingthy.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtil
{
    /**
     * 
     * String2Doc(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param xmlInfo
     * @return
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException 潘勇 Document
     * @exception @since 1.0.0
     */
    public static Document String2Doc(String xmlInfo)
        throws SAXException, IOException, ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append("<?xml version=\"1.0\"?>");
        xmlStringBuilder.append(xmlInfo);
        ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
        org.w3c.dom.Document doc = builder.parse(input);
        return doc;
    }
    
    /**
     * <xml> <uid>000</uid> <u_name>123</u_name> </xml> 如果attributeName=u_name 则返回123
     * getXmlAttributeValue(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param xmlInfo
     * @param attributeName
     * @return
     * @throws XPathExpressionException String
     * @author 潘勇
     * @exception @since 1.0.0
     */
    public static String getXmlAttributeValue(String xmlInfo, String attributeName)
        throws XPathExpressionException
    {
        Document doc = null;
        try
        {
            doc = String2Doc(xmlInfo);
        }
        catch (SAXException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodeList =
            (NodeList)xpath.evaluate("//*[name() = '" + attributeName + "']", doc, XPathConstants.NODESET);
        if (null != nodeList && nodeList.getLength() > 0)
        {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
