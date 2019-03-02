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
public class FreeDLOrderBuilder implements IBuilder
{
    private static JLogger LOG = LoggerFactory.getLogger(FreeDLOrderBuilder.class);
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    @Override
    public String getPrefix()
    {
        return "report";
    }

    @Override
    public Bean getBean(String line, String fileName)
    {
        FreeDLOrderBean bean = null;
        
        if (StringUtils.isEmpty(line))
        {
            return bean;
        }
        
        try
        {
            String[] arr = line.split((char)31 + "",-1);
            if (arr.length == 39)
            {
                bean = new FreeDLOrderBean();
                Date date = new Date();
                String day = df.format(date);
                
                bean.setId(arr[0]);//ID标识（序号生成）
                bean.setOrderNumber(arr[0]);
                bean.setDestUserID(arr[1]);
                bean.setFeeUserID(arr[2]);
                bean.setContentID(arr[3]);
                bean.setPackageCode(arr[4]);
                bean.setChannelID(arr[5]);
                bean.setSubsPlace(arr[6]);
                bean.setUA(arr[7]);
                bean.setSpCode(arr[8]);
                bean.setServiceCode(arr[9]);
                bean.setProductCode(arr[10]);
                bean.setOrderStartTime(arr[11]);
                bean.setDownloadTime(arr[12]);
                bean.setAccessModeID(arr[13]);
                bean.setPayWay(arr[14]);
                bean.setDiscountRate(arr[15]);
                bean.setPrice(arr[16]);
                bean.setFee(arr[17]);
                bean.setActionType(arr[18]);
                bean.setDownChannelID(arr[19]);
                bean.setAppName(arr[20]);
                bean.setUserType(arr[21]);
                bean.setOndemandType(arr[22]);
                bean.setApn(arr[23]);
                bean.setApnAddr(arr[24]);
                bean.setUserAccessType(arr[25]);
                bean.setExpireTime(arr[26]);
                bean.setOrderFlag(arr[27]);
                bean.setAcessPlat(arr[28]);
                bean.setInterChannel(arr[29]);
                bean.setTouchVersion(arr[30]);
                
                bean.setBrowser(arr[31]);
                
                bean.setSPM(arr[32]);
                bean.setMMSource(arr[33]);
                bean.setMMInstallId(arr[34]);
                bean.setMMVisitorId(arr[35]);
                bean.setMMDeviceInfo(arr[36]);
                bean.setReferrer(arr[37]);
                bean.setFromarea(arr[38]);
                bean.setLupdate(day);
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
        return new FreeDLOrderDealer();
    }

    private boolean checkLen(FreeDLOrderBean bean){


        if(StringUtils.isNotEmpty(bean.getId())){
            if(bean.getId().getBytes().length>60){
                return false;
            }
        }
        if(StringUtils.isNotEmpty(bean.getOrderNumber())){
            if(bean.getOrderNumber().getBytes().length>20){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getDestUserID())){
            if(bean.getDestUserID().getBytes().length>32){
                return false;
            }
        }
       
        if(StringUtils.isNotEmpty(bean.getFeeUserID())){
            if(bean.getFeeUserID().getBytes().length>32){
                return false;
            }
        }
       
        if(StringUtils.isNotEmpty(bean.getContentID())){
            if(bean.getContentID().getBytes().length>20){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getPackageCode())){
            if(bean.getPackageCode().getBytes().length>12){
                return false;
            }
        }
       
        if(StringUtils.isNotEmpty(bean.getChannelID())){
            if(bean.getChannelID().getBytes().length>64){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getSubsPlace())){
            if(bean.getSubsPlace().getBytes().length>20){
                return false;
            }
        }
    
        if(StringUtils.isNotEmpty(bean.getUA())){
            if(bean.getUA().getBytes().length>200){
                return false;
            }
        }
  
        if(StringUtils.isNotEmpty(bean.getSpCode())){
            if(bean.getSpCode().getBytes().length>10){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getServiceCode())){
            if(bean.getServiceCode().getBytes().length>20){
                return false;
            }
        }
       
        if(StringUtils.isNotEmpty(bean.getProductCode())){
            if(bean.getProductCode().getBytes().length>40){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getOrderStartTime())){
            if(bean.getOrderStartTime().getBytes().length>14){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getDownloadTime())){
            if(bean.getDownloadTime().getBytes().length>14){
                return false;
            }
        }
      
        if(StringUtils.isNotEmpty(bean.getAccessModeID())){
            if(bean.getAccessModeID().getBytes().length>2){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getPayWay())){
            if(bean.getPayWay().getBytes().length>11){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getDiscountRate())){
            if(bean.getDiscountRate().getBytes().length>11){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getPrice())){
            if(bean.getPrice().getBytes().length>11){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getFee())){
            if(bean.getFee().getBytes().length>11){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getActionType())){
            if(bean.getActionType().getBytes().length>11){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getDownChannelID())){
            if(bean.getDownChannelID().getBytes().length>20){
                return false;
            }
        }
       
        if(StringUtils.isNotEmpty(bean.getAppName())){
            if(bean.getAppName().getBytes().length>100){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getUserType())){
            if(bean.getUserType().getBytes().length>11){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getOndemandType())){
            if(bean.getOndemandType().getBytes().length>11){
                return false;
            }
        }

        if(StringUtils.isNotEmpty(bean.getApn())){
            if(bean.getApn().getBytes().length>32){
                return false;
            }
        }
      
        if(StringUtils.isNotEmpty(bean.getApnAddr())){
            if(bean.getApnAddr().getBytes().length>32){
                return false;
            }
        }
       
        if(StringUtils.isNotEmpty(bean.getUserAccessType())){
            if(bean.getUserAccessType().getBytes().length>24){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getExpireTime())){
            if(bean.getExpireTime().getBytes().length>14){
                return false;
            }
        }
  
        if(StringUtils.isNotEmpty(bean.getOrderFlag())){
            if(bean.getOrderFlag().getBytes().length>11){
                return false;
            }
        }
      
        if(StringUtils.isNotEmpty(bean.getAcessPlat())){
            if(bean.getAcessPlat().getBytes().length>20){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getInterChannel())){
            if(bean.getInterChannel().getBytes().length>50){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getTouchVersion())){
            if(bean.getTouchVersion().getBytes().length>11){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getBrowser())){
            if(bean.getBrowser().getBytes().length>200){
                return false;
            }
        }
       
        if(StringUtils.isNotEmpty(bean.getSPM())){
            if(bean.getSPM().getBytes().length>64){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getMMSource())){
            if(bean.getMMSource().getBytes().length>64){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getMMInstallId())){
            if(bean.getMMInstallId().getBytes().length>64){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getMMVisitorId())){
            if(bean.getMMVisitorId().getBytes().length>200){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getMMDeviceInfo())){
            if(bean.getMMDeviceInfo().getBytes().length>80){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getReferrer())){
            if(bean.getReferrer().getBytes().length>1024){
                return false;
            }
        }
        
        if(StringUtils.isNotEmpty(bean.getFromarea())){
            if(bean.getFromarea().getBytes().length>200){
                return false;
            }
        }

        
        return true;
    }
}
