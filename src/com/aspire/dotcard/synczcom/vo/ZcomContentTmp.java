/**
 * SSMS
 * com.aspire.dotcard.synczcom.vo ZcomContentTmp.java
 * Apr 10, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.synczcom.vo;

import java.util.Date;

/**
 * @author tungke
 *
 */
public class ZcomContentTmp {

	
	 /**
     * 内容id
     */
    private String contentId;
    /**
     * 应用名称
     */
    private String name;

   

    /**
     * 最后更新日期
     */
    private Date LupdDate;

    /**
     * 操作结果
     */
    private String optype;

	/**
	 * @return Returns the contentId.
	 */
	public String getContentId() {
		return contentId;
	}


	/**
	 * @param contentId The contentId to set.
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}


	/**
	 * @return Returns the lupdDate.
	 */
	public Date getLupdDate() {
		return LupdDate;
	}


	/**
	 * @param lupdDate The lupdDate to set.
	 */
	public void setLupdDate(Date lupdDate) {
		LupdDate = lupdDate;
	}


	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	  /**
	 * @return Returns the optype.
	 */
	public String getOptype() {
		return optype;
	}


	


	/**
	 * @param optype The optype to set.
	 */
	public void setOptype(String optype) {
		this.optype = optype;
	}


	public int hashCode()
	    {
	    	
	    	return contentId.hashCode();
	    }
	   public String toString()
	    {
	        StringBuffer sb = new StringBuffer();
	        sb.append("内容编码:");
	        sb.append(this.contentId);
	        sb.append(",");
	        sb.append("名称:");
	        sb.append(this.name);
	        sb.append(",");
	        sb.append("操作结果:");
	        sb.append(this.optype);
	        return sb.toString();
	    }
    
    
}
