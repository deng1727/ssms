package com.aspire.ponaadmin.webpps.common.helper;

import org.dom4j.Document;
import org.dom4j.Element;

import com.aspire.mm.common.client.httpsend.Resp;

/**
 * SSO���ص��û���Ϣ
 * @author xiongzy
 *
 */
public class MmSsoResp implements Resp
{
    // ������(0: �ɹ� ����:ʧ��)
    String resultCode; 
    
    // ����
    String ut;
    
    // �û�α��
    String puid;
    
    // �û���ʶ
    String buid;
    
    // һ����Ե����ƹ���ʱ������λΪ����,��120����
    String timeLimit;

    public Resp praseResp(Document document) {
        document.normalize();
        Element rootElement = document.getRootElement();
        Element resultCodeElement = rootElement.element("ResultCode");          
        if (resultCodeElement != null) {
            resultCode = resultCodeElement.getText();
        } else {
            return this;
        }

        // ����ɹ�,���������ֶ�
        if ("0".equals(resultCode)) {               
            Element utElement = rootElement.element("UT");
            if (utElement != null) {
                ut = utElement.getText();
            }

            Element puidElement = rootElement.element("PUID");
            if (puidElement != null) {
                puid = puidElement.getText();
            }
            
            Element buidElement = rootElement.element("BUID");
            if (buidElement != null) {
                buid = buidElement.getText();
            }
            
            Element timeLimitElement = rootElement.element("TimeLimit ");
            if (timeLimitElement != null) {
                timeLimit = timeLimitElement.getText();
            }
        }
        return this;
    }
    
    public String getResultCode()
    {
    
        return resultCode;
    }

    
    public void setResultCode(String resultCode)
    {
    
        this.resultCode = resultCode;
    }

    
    public String getUt()
    {
    
        return ut;
    }

    
    public void setUt(String ut)
    {
    
        this.ut = ut;
    }

    
    public String getPuid()
    {
    
        return puid;
    }

    
    public void setPuid(String puid)
    {
    
        this.puid = puid;
    }

    
    public String getBuid()
    {
    
        return buid;
    }

    
    public void setBuid(String buid)
    {
    
        this.buid = buid;
    }

    
    public String getTimeLimit()
    {
    
        return timeLimit;
    }

    
    public void setTimeLimit(String timeLimit)
    {
    
        this.timeLimit = timeLimit;
    }
}
