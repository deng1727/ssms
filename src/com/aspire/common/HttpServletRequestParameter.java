
package com.aspire.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;



public  class HttpServletRequestParameter extends HttpServletRequestWrapper
{

  
	 /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(HttpServletRequestParameter.class) ;
    
    public HttpServletRequestParameter(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

//	protected String getParameter (HttpServletRequest request,
//                                   String key)
//    {
//		
//		request.getParameter(arg0)
//        String value = request.getParameter(key) ;
//        if (value == null)
//        {
//            value = "" ;
//        }
//        return Validator.filter(value.trim()) ;
//    }
	
	public String getParameter(String arg0){
        String value = super.getParameter(arg0) ;
        if (value == null)
        {
            value = "" ;
        }
        
        // �������
        if(arg0.equals("backURL"))
        {
            return value;
        }
        
        if(LOG.isDebugEnabled()){
        	//System.out.println("ǰ��"+value.trim());
        	//System.out.println("��"+Validator.filter(value.trim()));
        }
        
        return Validator.filter(value.trim()) ;
        
		
	}

  
}
