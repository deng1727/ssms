
package com.aspire.dotcard.reportdata.cystatistic.vo;

import java.util.Date;


/**
 * <p>
 * ÿ�մ�ҵ������Ʒ��Ӫ�������ݶ�������
 * </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class CyListVO
{

    /**
     * ͳ������,��ʽ��YYYYMMDD
     */
    private String stattime;

    /**
     * ����ID
     */
    private String contentid;

    /**
     * ��������
     */
    private String contentname;

    /**
     * �����û���
     */
    private int downloadusernum;

    /**
     * �����û���
     */
    private int testusernum;

    /**
     * �����Ǽ�
     */
    private int teststar;

    /**
     * ��̽�Ƽ��÷�
     */
    private int starscorecount;

    /**
     * �����ۺ��Ƽ�ָ��
     */
    private int globalscorecount;

    
    /**
     * �����û���
     */
    private int daydownloadusernum;

    /**
     * �����û���
     */
    private int daytestusernum;

    /**
     * �����Ǽ�
     */
    private int dayteststar;

    /**
     * ��̽�Ƽ��÷�
     */
    private int daystarscorecount;

    /**
     * �����ۺ��Ƽ�ָ��
     */
    private int dayglobalscorecount;
    
    /**
     * ��¼���һ�θ��µ�ʱ��
     */
    private Date updatetime;

    public String getContentid()
    {

        return contentid;
    }

    public void setContentid(String contentid)
    {

        this.contentid = contentid;
    }

    public String getContentname()
    {

        return contentname;
    }

    public void setContentname(String contentname)
    {

        this.contentname = contentname;
    }

    public int getDownloadusernum()
    {

        return downloadusernum;
    }

    public void setDownloadusernum(int downloadusernum)
    {

        this.downloadusernum = downloadusernum;
    }

    public int getGlobalscorecount()
    {

        return globalscorecount;
    }

    public void setGlobalscorecount(int globalscorecount)
    {

        this.globalscorecount = globalscorecount;
    }

    public int getStarscorecount()
    {

        return starscorecount;
    }

    public void setStarscorecount(int starscorecount)
    {

        this.starscorecount = starscorecount;
    }

    public String getStattime()
    {

        return stattime;
    }

    public void setStattime(String stattime)
    {

        this.stattime = stattime;
    }

    public int getTeststar()
    {

        return teststar;
    }

    public void setTeststar(int teststar)
    {

        this.teststar = teststar;
    }

    public int getTestusernum()
    {

        return testusernum;
    }

    public void setTestusernum(int testusernum)
    {

        this.testusernum = testusernum;
    }

    public Date getUpdatetime()
    {

        return updatetime;
    }

    public void setUpdatetime(Date updatetime)
    {

        this.updatetime = updatetime;
    }

	/**
	 * @return Returns the daydownloadusernum.
	 */
	public int getDaydownloadusernum()
	{
		return daydownloadusernum;
	}

	/**
	 * @param daydownloadusernum The daydownloadusernum to set.
	 */
	public void setDaydownloadusernum(int daydownloadusernum)
	{
		this.daydownloadusernum = daydownloadusernum;
	}

	/**
	 * @return Returns the dayglobalscorecount.
	 */
	public int getDayglobalscorecount()
	{
		return dayglobalscorecount;
	}

	/**
	 * @param dayglobalscorecount The dayglobalscorecount to set.
	 */
	public void setDayglobalscorecount(int dayglobalscorecount)
	{
		this.dayglobalscorecount = dayglobalscorecount;
	}

	/**
	 * @return Returns the daystarscorecount.
	 */
	public int getDaystarscorecount()
	{
		return daystarscorecount;
	}

	/**
	 * @param daystarscorecount The daystarscorecount to set.
	 */
	public void setDaystarscorecount(int daystarscorecount)
	{
		this.daystarscorecount = daystarscorecount;
	}

	/**
	 * @return Returns the dayteststar.
	 */
	public int getDayteststar()
	{
		return dayteststar;
	}

	/**
	 * @param dayteststar The dayteststar to set.
	 */
	public void setDayteststar(int dayteststar)
	{
		this.dayteststar = dayteststar;
	}

	/**
	 * @return Returns the daytestusernum.
	 */
	public int getDaytestusernum()
	{
		return daytestusernum;
	}

	/**
	 * @param daytestusernum The daytestusernum to set.
	 */
	public void setDaytestusernum(int daytestusernum)
	{
		this.daytestusernum = daytestusernum;
	}

}
