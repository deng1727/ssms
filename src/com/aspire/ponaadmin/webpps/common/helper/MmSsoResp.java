package com.aspire.ponaadmin.webpps.common.helper;

import org.dom4j.Document;
import org.dom4j.Element;

import com.aspire.mm.common.client.httpsend.Resp;

/**
 * SSO返回的用户信息
 * @author xiongzy
 *
 */
public class MmSsoResp implements Resp
{
    // 错误码(0: 成功 其它:失败)
    String resultCode; 
    
    // 令牌
    String ut;
    
    // 用户伪码
    String puid;
    
    // 用户标识
    String buid;
    
    // 一个相对的令牌过期时长，单位为分钟,如120分钟
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

        // 如果成功,解析其它字段
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
