package com.aspire.ponaadmin.web.repository.category;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class XMLParser {
	
	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(XMLParser.class);
	
	private static DocumentBuilderFactory factory = null;
	private static DocumentBuilder db = null;
	static {
		try {
			factory = DocumentBuilderFactory.newInstance();
			db = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			LOG.error("CategoryOperation的xml解析类有问题。",e);
		}
	}
	public static ProductShelvesVO getAPPInfoVO(InputStream stream) throws Exception{
		ProductShelvesVO vo = new ProductShelvesVO();
	    Document doc = db.parse(stream);
	    Element root = doc.getDocumentElement();
	    NodeList nodes = root.getChildNodes();
	    List<ApplicationVO> list = new ArrayList<ApplicationVO>();
	    for (int i = 0; i < nodes.getLength(); i++)
        {
            Node result = nodes.item(i);
          
            if (result.getNodeType() == Node.ELEMENT_NODE 
            		&& result.getNodeName().equals("Head")){
            	NodeList childsHead = result.getChildNodes();
            	String name = "",content="";
            	for(int j = 0;j<childsHead.getLength();j++){
            		Node nodeHead = childsHead.item(j);
            		name = nodeHead.getNodeName();
            		content = nodeHead.getTextContent();
            		if("ActionCode".equals(name)){
            			vo.setActionCode(content);
            		}else if("TransactionID".equals(name)){
            			vo.setTransactionID(content);
            		}else if("Version".equals(name)){
            			vo.setVersion(content);
            		}else if("ProcessTime".equals(name)){
            			vo.setProcessTime(content);
            		}
            		
            	}
            	
            }else if(result.getNodeType() == Node.ELEMENT_NODE &&result.getNodeName().equals("Body")){
            	NodeList childsBody = result.getChildNodes();
            	String name = "",content="";
            	for(int j = 0;j<childsBody.getLength();j++){
            		Node nodeBody = childsBody.item(j);
            		name = nodeBody.getNodeName();
            		if("ShelvesID".equals(name)){
            			content = nodeBody.getTextContent();
            			vo.setShelvesID(content);
            		}else if("AppNum".equals(name)){
            			content = nodeBody.getTextContent();
            			vo.setAppNum(content);
            		}else if("Action".equals(name)){
            			content = nodeBody.getTextContent();
            			vo.setAction(content);
            		}else if("items".equals(name)){
            			NodeList nodeList = nodeBody.getChildNodes();
            			for(int k = 0; k < nodeList.getLength();k++){
            				Node node = nodeList.item(k);
            					if("item".equals(node.getNodeName())){
            						NodeList nodeContent = node.getChildNodes();
            						ApplicationVO applicationVo = new ApplicationVO();
            						for(int n = 0;n < nodeContent.getLength();n++){
            							Node nodeItem = nodeContent.item(n);
            							if("appid".equals(nodeItem.getNodeName())){
            								applicationVo.setAppid(nodeItem.getTextContent());
            							}else if("appName".equals(nodeItem.getNodeName())){
            								applicationVo.setAppName(nodeItem.getTextContent());
            							}else if("position".equals(nodeItem.getNodeName())){
            								applicationVo.setPosition(nodeItem.getTextContent());
            							}
            						}
            						list.add(applicationVo);
            					}
            			}
            			
            		}
            	}
            }
        }
	    vo.setList(list);
		return vo;
	}

}
