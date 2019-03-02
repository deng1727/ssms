package com.aspire.dotcard.syncAndroid.ppms;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
/**
 * 
 * @author aiyan
 *
 */

//接收的XML
//<?xml version="1.0" encoding="UTF-8"?>
//<APPInfoNotifyRequest>
//	<Head>
//		<ActionCode>0</ActionCode>
//		<TransactionID>201012180000001</TransactionID>
//		<Version>0001</Version>
//		<ProcessTime>20101218160933</ProcessTime>
//	</Head>
//	<Body>
//		<Type>1</Type>
//		<ContentID>300000006524,300000006525</ContentID>
//	</Body>
//</APPInfoNotifyRequest>




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
			LOG.error("ppms的xml解析类有问题。",e);
		}
	}
	public static APPInfoVO getAPPInfoVO(InputStream stream) throws Exception{
		APPInfoVO vo = new APPInfoVO();
	    Document doc = db.parse(stream);
	    Element root = doc.getDocumentElement();
	    NodeList nodes = root.getChildNodes();
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
            		content = nodeBody.getTextContent();
            		if("Type".equals(name)){
            			vo.setType(content);
            		}else if("ContentID".equals(name)){
            			vo.setContentID(content);
            		}
            	}
            }
        }
	    //System.out.println(vo);
		return vo;
	}
	
//	public APPInfoVO parseXML() throws Exception{
//	//http://127.0.0.1:8080/ssms_svn/web/aa.xml
//	//URL url = null;//new URL("http://127.0.0.1:8080/ssms_svn/web/aa.xml");
//	
//	URL url = new URL("http://127.0.0.1:8080/ssms_svn/web/aa.xml");   
//    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//    InputStream stream = conn.getInputStream();
//    
//    APPInfoVO vo = getAPPInfoVO(stream);
//    return vo;
//    
//
//	}
//	
//	public static void main(String[] argv) throws Exception{
//		XMLParser p = new XMLParser();
//		p.getAPPInfoVO(null);
//	}
//	


//	private void b(InputStream stream)throws Exception{
//	    BufferedReader reader = new BufferedReader(new InputStreamReader(
//      stream, "UTF-8"));
//    StringBuffer document = new StringBuffer();
//    String line = null;
//    while ((line = reader.readLine()) != null) {
//     document.append(line);
//    }
//    
//    System.out.println(document.toString());
//	}
}
