package com.aspire.dotcard.syncAndroid.dc.json2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author aiyan
 * 
 */
public class ListServlet extends HttpServlet {

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(ListServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	public void init() throws ServletException {
		LOG.info("ListServlet started!");
	}

	
//	hRet	����	Integer		����ֵ���μ�4����ֵ��ͳһ����
//	TotalRecord	����	Int		����
//	CurPage	����	Int		��ǰҳ��
//	RecordPage	����	Int		��ǰҳ��¼��
//	Ids	����	����		��Ids����
//	CategoryContent	��ѡ	����		������������
//
//	Ids����
//	�ֶ���	��Ҫ��	����	˵��
//	Id	����	String	��Ӧ����ҵ�����͵���Ʒ����
//	Content	����	����	��Content����

	public void process(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
        try {
			json.put("hRet", 0);
	        json.put("TotalRecord", 120);
	        json.put("RecordPage", 12);
	         
//    	        Id	����	String	��Ӧ����ҵ�����͵���Ʒ����
//    	        Content	����	����	��Content����
	        JSONArray arr = new JSONArray();
	        for(int i=0;i<4;i++){
	        	JSONObject ids = new JSONObject();
		        ids.put("Id", Math.random());
		        
	        	JSONObject objArr = new JSONObject();
	        	objArr.put("name", "name"+i);
	        	objArr.put("contentid", i);
		        JSONArray objArr2 = new JSONArray();
		        objArr2.put(objArr);
		        ids.put("Content", objArr2);
		        arr.put(ids);
	        }
	    
	        
	        json.put("Ids", arr);
	        json.put("CurPage", 1);
	        json.put("CategoryContent", "");  
	       
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.print(json.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pw.close();
		}
		
	}
	
	public static void main(String[] argv){
		JSONObject json = new JSONObject();
        try {
			json.put("hRet", 0);
	        json.put("TotalRecord", 120);
	        json.put("RecordPage", 12);
	         
//    	        Id	����	String	��Ӧ����ҵ�����͵���Ʒ����
//    	        Content	����	����	��Content����
	        JSONArray arr = new JSONArray();
	        for(int i=0;i<5;i++){
	        	JSONObject obj = new JSONObject();
		        obj.put("name", "name"+i);
		        obj.put("contentid", i);
		        arr.put(obj);
		        
		        JSONObject ids = new JSONObject();
		        ids.put("id", Math.random());
		        ids.put("Content", arr);
		        json.put("Ids", ids); 
	        }
	    
	        
	        
	        json.put("CurPage", 1);
	        json.put("CategoryContent", "");  
	       
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json.toString());
	}

}
