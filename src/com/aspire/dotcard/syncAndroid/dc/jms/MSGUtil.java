package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MSGUtil {
	
	public static void addMSG(MSGType type,Map feedback,String msg){
        List list = (List)feedback.get(type);
        if(list==null){
        	list = new ArrayList<String>();
        	feedback.put(type,list);
        }
        list.add(msg);
	}

}
