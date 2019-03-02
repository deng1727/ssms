package com.aspire.dotcard.syncAndroid.dc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MessageVO
{
    private int id;
    
    private String type ;
    
    private String message;
    
    private int status;
    
    private String transactionId;
    
    private Date lupdate;
    
    @Override
    public String toString(){
    	
    	return "id:"+id+",type:"+type+",message:"+message+",status:"+status+",transactionId:"+transactionId;
    }
    
//    public Map toMap(){
//    	return new HashMap(){{
//    		put("type", type);
//    		put("message", message);
//    	}};
//    }
    
    public int getId()
    {
    
        return id;
    }

    
    
    
    public void setId(int id)
    {
    
        this.id = id;
    }

    
    public String getType()
    {
    
        return type;
    }

    
    public void setType(String type)
    {
    
        this.type = type;
    }

    
    public String getMessage()
    {
    
        return message;
    }

    
    public void setMessage(String message)
    {
    
        this.message = message;
    }

    
    public int getStatus()
    {
    
        return status;
    }

    
    public void setStatus(int status)
    {
    
        this.status = status;
    }

    
    public Date getLupdate()
    {
    
        return lupdate;
    }

    
    public void setLupdate(Date lupdate)
    {
    
        this.lupdate = lupdate;
    }


    
    public String getTransactionId()
    {
    
        return transactionId;
    }


    
    public void setTransactionId(String transactionId)
    {
    
        this.transactionId = transactionId;
    }
    
    
}
