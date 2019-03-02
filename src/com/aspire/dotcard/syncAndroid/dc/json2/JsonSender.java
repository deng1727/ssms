package com.aspire.dotcard.syncAndroid.dc.json2;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.web.CgyContentListAction;



public class JsonSender {
	
	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(JsonSender.class);
	
	private String sendurl;
	private JSONObject json;
	public JsonSender(String sendurl,JSONObject json){
		this.sendurl=sendurl;
		this.json=json;
	}
	

    /**
     * ��������
     * @param url
     * @param json
     * @return
     */
    public  String send(){
        String jsonString = "";
        HttpURLConnection connection = null;
        InputStream in = null;
        DataOutputStream out = null;
        try {
            //��������
            URL url = new URL(sendurl);
            LOG.info("sendurl:"+sendurl);
            connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.connect();
            LOG.info("sendurl:OK!!");
            //POST����
            out = new DataOutputStream(
                    connection.getOutputStream());
//            out.writeBytes(json.toString());
            LOG.info("������ܷ��͵�JSON:"+json.toString());
            out.write((json.toString()).getBytes("utf-8"));
            out.flush();

            /* ��ȡ�������˷�����Ϣ */
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            in = connection.getInputStream();
            int b = in.read();
            while (b > -1) {
                baos.write(b);
                b = in.read();
            }
            jsonString = baos.toString("utf-8");
            
            LOG.info("������ܽ��ܵ�JSON:"+jsonString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try
            {
                if(out!=null)
                {
                    out.close();
                }
                if(in!=null)
                {
                    in.close();
                }
                if(connection!=null)
                {
                    connection.disconnect();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return jsonString;
    }
    
}
