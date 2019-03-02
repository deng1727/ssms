package com.aspire.dotcard.syncAndroid.cssp.free;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.cssp.Bean;
import com.aspire.dotcard.syncAndroid.cssp.DataDealer;
import com.aspire.dotcard.syncAndroid.cssp.IBuilder;

/**
 * 
 * add by fanqh
 */
public class FreeDLReportBuilder implements IBuilder
{
    private static JLogger LOG = LoggerFactory.getLogger(FreeDLReportDealer.class);
    
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    @Override
    public String getPrefix()
    {
        return "report";
    }

    @Override
    public Bean getBean(String line, String fileName)
    {
        FreeDLReportBean bean = null;
        
        if (StringUtils.isEmpty(line))
        {
            return bean;
        }
        
        try
        {
            String[] arr = line.split((char)31 + "",-1);
            if (arr.length == 6)
            {
                bean = new FreeDLReportBean();
                Date date = new Date();
                String day = df.format(date);
                bean.setId(arr[0]);//ID标识（序号生成）
                bean.setSequenceId(arr[0]);
                bean.setPushId(arr[1]);
                bean.setStatus(arr[2]);
                bean.setOutputTime(arr[3]);
                bean.setParams(arr[4]);
                bean.setUserAccessType(arr[5]);
                bean.setLupdatetime(day);
                bean.setFlag("-1");
                
                bean.setFilename(fileName);
            }
            else
            {
                if (LOG.isDebugEnabled())
                {
                    LOG.debug(fileName + "中：[" + line + "]格式不对!");
                }
            }
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        if(bean!=null && !checkLen(bean))
        {
            LOG.info("字段长度超长:");
            return null;
        }
        return bean;
    }

    @Override
    public DataDealer getDataDealer()
    {
        return new FreeDLReportDealer();
    }
    
    private boolean checkLen(FreeDLReportBean bean){

        if(StringUtils.isNotEmpty(bean.getId())){
            if(bean.getId().getBytes().length>60){
                return false;
            }
        }
    
        if(StringUtils.isNotEmpty(bean.getSequenceId())){
            if(bean.getSequenceId().getBytes().length>20){
                return false;
            }
        }
       
        if(StringUtils.isNotEmpty(bean.getPushId())){
            if(bean.getPushId().getBytes().length>20){
                return false;
            }
        }
      
        if(StringUtils.isNotEmpty(bean.getStatus())){
            if(bean.getStatus().getBytes().length>10){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getOutputTime())){
            if(bean.getOutputTime().getBytes().length>14){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getParams())){
            if(bean.getParams().getBytes().length>14){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getUserAccessType())){
            if(bean.getUserAccessType().getBytes().length>20){
                return false;
            }
        }
        
        return true;
    }
}
