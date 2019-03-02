/**
 * com.aspire.dotcard.baseVideo.vo VideoNodeVO.java
 * Jul 9, 2012
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.dotcard.baseVideo.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.dotcard.baseVideo.dao.VideoDAO;
import com.aspire.ponaadmin.common.page.PageVOInterface;

/**
 * @author tungke
 *
 */
public class VideoNodeExtVO implements PageVOInterface
{
	
	private String nodeId;
	private String  nodeName;
	private String nodeDesc;
	/**
	 * @return Returns the nodeDesc.
	 */
	public String getNodeDesc()
	{
		return nodeDesc;
	}
	/**
	 * @param nodeDesc The nodeDesc to set.
	 */
	public void setNodeDesc(String nodeDesc)
	{
		this.nodeDesc = nodeDesc;
	}
	/**
	 * @return Returns the nodeId.
	 */
	public String getNodeId()
	{
		return nodeId;
	}
	/**
	 * @param nodeId The nodeId to set.
	 */
	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}
	/**
	 * @return Returns the nodeName.
	 */
	public String getNodeName()
	{
		return nodeName;
	}
	/**
	 * @param nodeName The nodeName to set.
	 */
	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}
	
    public void CopyValFromResultSet(Object vo, ResultSet rs)
    throws SQLException
{

    	VideoDAO.getInstance().getVideoNodeExtVOFromRS(( VideoNodeExtVO ) vo,
                                          rs);
}

/**
* 
* @return VOObject
* @todo Implement this com.aspire.ponaadmin.PageVOInterface method
*/
public Object createObject()
{

return new VideoNodeExtVO();
}
	

}
