package com.aspire.ponaadmin.web.repository.goods;

import java.util.Date;

import com.aspire.ponaadmin.VOObject;

/**
 * 商品信息VO类
 * 
 * @author bihui
 * 
 */
public class GoodsVO extends VOObject
{

    private static final long serialVersionUID = 1L;

    /**
	 * 商品编码
	 */
	private String	goodsID;

	/**
	 * 企业代码
	 */
	private String	icpCode;

	/**
	 * 业务代码
	 */
	private String	icpServId;

	/**
	 * 商品对应的内容编码
	 */
	private String	contentID;

	/**
	 * 商品所在的货架编码
	 */
	private String	categoryID;

	/**
	 * 商品名称
	 */
	private String	goodsName;

	/**
	 * 商品状态
	 */
	private int		state;

	/**
	 * 变更时间
	 */
	private Date	changeDate;

	/**
	 * 操作类型，1：上线，9：下线
	 */
	private int		actionType;

	/**
	 * 商品上次状态
	 */
	private int		lastState;

	/**
	 * 上架时间
	 */
	private String loadDate = null;

	/**
	 * 获取操作类型
	 * 
	 * @return
	 */
	public int getActionType()
	{

		return actionType;
	}

	/**
	 * 设定操作类型
	 * 
	 * @param actionType
	 */
	public void setActionType( int actionType)
	{

		this.actionType = actionType;
	}

	/**
	 * 获取变更时间
	 * 
	 * @return
	 */
	public Date getChangeDate()
	{

		return changeDate;
	}

	/**
	 * 设定变更时间
	 * 
	 * @param actionType
	 */
	public void setChangeDate( Date changeDate)
	{

		this.changeDate = changeDate;
	}

	/**
	 * 获取操作类型
	 * 
	 * @return
	 */
	public String getGoodsID()
	{

		return goodsID;
	}

	/**
	 * 设定操作类型
	 * 
	 * @param actionType
	 */
	public void setGoodsID( String goodsID)
	{

		this.goodsID = goodsID;
	}

	/**
	 * 获取商品上次状态
	 * 
	 * @return
	 */
	public int getLastState()
	{

		return lastState;
	}

	/**
	 * 设定商品上次状态
	 * 
	 * @param actionType
	 */
	public void setLastState( int lastState)
	{

		this.lastState = lastState;
	}

	/**
	 * 获取商品状态
	 * 
	 * @return
	 */
	public int getState()
	{

		return state;
	}

	/**
	 * 设定商品状态
	 * 
	 * @param actionType
	 */
	public void setState( int state)
	{

		this.state = state;
	}

	/**
	 * 获取商品所在的货架编码
	 * 
	 * @return
	 */
	public String getCategoryID()
	{

		return categoryID;
	}

	/**
	 * 设置商品所在的货架编码
	 * 
	 * @param categorID
	 */
	public void setCategoryID( String categoryID)
	{

		this.categoryID = categoryID;
	}

	/**
	 * 获取商品对应的内容编码
	 * 
	 * @return
	 */
	public String getContentID()
	{

		return contentID;
	}

	/**
	 * 设置商品对应的内容编码
	 * 
	 * @param id
	 */
	public void setContentID( String contentID)
	{

		this.contentID = contentID;
	}

	/**
	 * 获取商品名称
	 * 
	 * @return
	 */
	public String getGoodsName()
	{

		return goodsName;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param goodsName
	 */
	public void setGoodsName( String goodsName)
	{

		this.goodsName = goodsName;
	}

	/**
	 * 获取企业代码
	 * 
	 * @return
	 */
	public String getIcpCode()
	{

		return icpCode;
	}

	/**
	 * 设置企业代码
	 * 
	 * @param icpCode
	 */
	public void setIcpCode( String icpCode)
	{

		this.icpCode = icpCode;
	}

	/**
	 * 获取业务代码
	 * 
	 * @return
	 */
	public String getIcpServId()
	{

		return icpServId;
	}

	/**
	 * 设置业务代码
	 * 
	 * @param icpServId
	 */
	public void setIcpServId( String icpServId)
	{

		this.icpServId = icpServId;
	}

	public String getLoadDate()
	{
		return loadDate;
	}

	public void setLoadDate( String loadDate)
	{
		this.loadDate = loadDate;
	}

}
