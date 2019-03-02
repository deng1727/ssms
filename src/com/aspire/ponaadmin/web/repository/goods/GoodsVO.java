package com.aspire.ponaadmin.web.repository.goods;

import java.util.Date;

import com.aspire.ponaadmin.VOObject;

/**
 * ��Ʒ��ϢVO��
 * 
 * @author bihui
 * 
 */
public class GoodsVO extends VOObject
{

    private static final long serialVersionUID = 1L;

    /**
	 * ��Ʒ����
	 */
	private String	goodsID;

	/**
	 * ��ҵ����
	 */
	private String	icpCode;

	/**
	 * ҵ�����
	 */
	private String	icpServId;

	/**
	 * ��Ʒ��Ӧ�����ݱ���
	 */
	private String	contentID;

	/**
	 * ��Ʒ���ڵĻ��ܱ���
	 */
	private String	categoryID;

	/**
	 * ��Ʒ����
	 */
	private String	goodsName;

	/**
	 * ��Ʒ״̬
	 */
	private int		state;

	/**
	 * ���ʱ��
	 */
	private Date	changeDate;

	/**
	 * �������ͣ�1�����ߣ�9������
	 */
	private int		actionType;

	/**
	 * ��Ʒ�ϴ�״̬
	 */
	private int		lastState;

	/**
	 * �ϼ�ʱ��
	 */
	private String loadDate = null;

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public int getActionType()
	{

		return actionType;
	}

	/**
	 * �趨��������
	 * 
	 * @param actionType
	 */
	public void setActionType( int actionType)
	{

		this.actionType = actionType;
	}

	/**
	 * ��ȡ���ʱ��
	 * 
	 * @return
	 */
	public Date getChangeDate()
	{

		return changeDate;
	}

	/**
	 * �趨���ʱ��
	 * 
	 * @param actionType
	 */
	public void setChangeDate( Date changeDate)
	{

		this.changeDate = changeDate;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public String getGoodsID()
	{

		return goodsID;
	}

	/**
	 * �趨��������
	 * 
	 * @param actionType
	 */
	public void setGoodsID( String goodsID)
	{

		this.goodsID = goodsID;
	}

	/**
	 * ��ȡ��Ʒ�ϴ�״̬
	 * 
	 * @return
	 */
	public int getLastState()
	{

		return lastState;
	}

	/**
	 * �趨��Ʒ�ϴ�״̬
	 * 
	 * @param actionType
	 */
	public void setLastState( int lastState)
	{

		this.lastState = lastState;
	}

	/**
	 * ��ȡ��Ʒ״̬
	 * 
	 * @return
	 */
	public int getState()
	{

		return state;
	}

	/**
	 * �趨��Ʒ״̬
	 * 
	 * @param actionType
	 */
	public void setState( int state)
	{

		this.state = state;
	}

	/**
	 * ��ȡ��Ʒ���ڵĻ��ܱ���
	 * 
	 * @return
	 */
	public String getCategoryID()
	{

		return categoryID;
	}

	/**
	 * ������Ʒ���ڵĻ��ܱ���
	 * 
	 * @param categorID
	 */
	public void setCategoryID( String categoryID)
	{

		this.categoryID = categoryID;
	}

	/**
	 * ��ȡ��Ʒ��Ӧ�����ݱ���
	 * 
	 * @return
	 */
	public String getContentID()
	{

		return contentID;
	}

	/**
	 * ������Ʒ��Ӧ�����ݱ���
	 * 
	 * @param id
	 */
	public void setContentID( String contentID)
	{

		this.contentID = contentID;
	}

	/**
	 * ��ȡ��Ʒ����
	 * 
	 * @return
	 */
	public String getGoodsName()
	{

		return goodsName;
	}

	/**
	 * ������Ʒ����
	 * 
	 * @param goodsName
	 */
	public void setGoodsName( String goodsName)
	{

		this.goodsName = goodsName;
	}

	/**
	 * ��ȡ��ҵ����
	 * 
	 * @return
	 */
	public String getIcpCode()
	{

		return icpCode;
	}

	/**
	 * ������ҵ����
	 * 
	 * @param icpCode
	 */
	public void setIcpCode( String icpCode)
	{

		this.icpCode = icpCode;
	}

	/**
	 * ��ȡҵ�����
	 * 
	 * @return
	 */
	public String getIcpServId()
	{

		return icpServId;
	}

	/**
	 * ����ҵ�����
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
