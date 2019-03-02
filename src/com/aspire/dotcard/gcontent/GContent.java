package com.aspire.dotcard.gcontent;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.EntityNode;
import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>��Դ���е����ݽڵ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author ����
 */
public class GContent extends EntityNode
{

    /**
     * ��־��¼����
     */
    protected static JLogger logger = LoggerFactory.getLogger(GContent.class);

    /**
     * ��Դ���ͣ�ҵ�����ݣ���������
     */
    public static final String TYPE_CONTENT = "nt:gcontent";

    /**
     * ���췽��
     */
    public GContent()
    {
        this.type = TYPE_CONTENT;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GContent(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_CONTENT;
    }

    /**
     * ��ȡ���ݵ�����
     * @return Returns the name.
     */
    public String getName()
    {
        return getNoNullString((String) this.getProperty("name").getValue());
    }


    /**
     * �������ݵ������������
     * @param name The name to set.
     */
    public void setFulldeviceName(String fulldeviceName)
    {
        Property pro = new Property("fulldeviceName", fulldeviceName);
        this.setProperty(pro);
    }
    /**
     * ��ȡ���������������
     * @return Returns the name.
     */
    public String getFulldeviceName()
    {
        return getNoNullString((String) this.getProperty("fulldeviceName").getValue());
    }

    /**
     * �������ݵ������������ID
     * @param name The ID to set.
     */
    public void setFulldeviceID(String fulldeviceID)
    {
        Property pro = new Property("fulldeviceID", fulldeviceID);
        this.setProperty(pro);
    }
    /**
     * ��ȡ���������������ID
     * @return Returns the ID.
     */
    public String getFulldeviceID()
    {
        return getNoNullString((String) this.getProperty("fulldeviceID").getValue());
    }
    
    
    /**
     * �������ݵ�����
     * @param name The name to set.
     */
    public void setName(String name)
    {
        Property pro = new Property("name", name);
        this.setProperty(pro);
    }
    /**
     * ��ȡҵ�����ݷ���
     * @return Returns the cateName.
     */
    public String getCateName()
    {
        return getNoNullString((String) this.getProperty("cateName").getValue());
    }


    /**
     * ����ҵ�����ݷ���
     * @param cateName The cateName to set.
     */
    public void setCateName(String cateName)
    {
        Property pro = new Property("cateName", cateName);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����ṩ��
     * @return Returns the spName.
     */
    public String getSpName()
    {
        return getNoNullString((String) this.getProperty("spName").getValue());
    }


    /**
     * ���÷����ṩ��
     * @param spName The spName to set.
     */
    public void setSpName(String spName)
    {
        Property pro = new Property("spName", spName);
        this.setProperty(pro);
    }

    /**
     * ��ȡsp����ҵ���루���룩
     * @return Returns the icpCode.
     */
    public String getIcpCode()
    {
        return (String) this.getProperty("icpCode").getValue();
    }


    /**
     * ����sp����ҵ���루���룩
     * @param icpCode The icpCode to set.
     */
    public void setIcpCode(String icpCode)
    {
        Property pro = new Property("icpCode", icpCode);
        this.setProperty(pro);
    }


    /**
     * ��ȡ�������
     * @return Returns the icpServId.
     */
    public String getIcpServId()
    {
        return (String) this.getProperty("icpServId").getValue();
    }


    /**
     * ���÷������
     * @param icpServId The icpServId to set.
     */
    public void setIcpServId(String icpServId)
    {
        Property pro = new Property("icpServId", icpServId);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���ݱ�ʶ
     * @return Returns the contentTag.
     */
    public String getContentTag()
    {
        return (String) this.getProperty("contentTag").getValue();
    }


    /**
     * �������ݱ�ʶ
     * @param contentTag The contentTag to set.
     */
    public void setContentTag(String contentTag)
    {
        Property pro = new Property("contentTag", contentTag);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�������
     * @return Returns the introduction.
     */
    public String getIntroduction()
    {
        return (String) this.getProperty("introduction").getValue();
    }


    /**
     * �����������
     * @param The introduction to set.
     */
    public void setIntroduction(String introduction)
    {
        Property pro = new Property("introduction", introduction);
        this.setProperty(pro);
    }

    /**
     * ��ȡ���ݱ���
     * @return Returns the contentID.
     */
    public String getContentID()
    {
        return (String) this.getProperty("contentID").getValue();
    }


    /**
     * �������ݱ���
     * @param contentID The contentID to set.
     */
    public void setContentID(String contentID)
    {
        Property pro = new Property("contentID", contentID);
        this.setProperty(pro);
    }
    
    
    /**
     * ��ȡ���ݱ���
     * @return Returns the contentID.
     */
    public String getAppId()
    {
        return (String) this.getProperty("appId").getValue();
    }


    /**
     * �������ݱ���
     * @param contentID The contentID to set.
     */
    public void setAppId(String appId)
    {
        Property pro = new Property("appId", appId);
        this.setProperty(pro);
    }
    /**
     * ��ȡ��ҵ���루���룩
     * @return Returns the companyID.
     */
    public String getCompanyID()
    {
        return (String) this.getProperty("companyID").getValue();
    }

    /**
     * ������ҵ���루���룩
     * @param companyID The companyID to set.
     */
    public void setCompanyID(String companyID)
    {
        Property pro = new Property("companyID", companyID);
        this.setProperty(pro);
    }

    /**
     * ��ȡҵ����루���룩
     * @return Returns the productID.
     */
    public String getProductID()
    {
        return (String) this.getProperty("productID").getValue();
    }

    /**
     * ����ҵ����루���룩
     * @param productID The productID to set.
     */
    public void setProductID(String productID)
    {
        Property pro = new Property("productID", productID);
        this.setProperty(pro);
    }

    /**
     * �������ݹؼ���
     * @param imageName The imageName to set.
     */
    public void setKeywords(String keywords)
    {
        Property pro = new Property("keywords", keywords);
        this.setProperty(pro);
        
        setKeywordsList();
    }

    /**
     * ��ȡ���ݹؼ���
     * @return Returns the imageName.
     */
    public String getKeywords()
    {
        return (String) this.getProperty("keywords").getValue();
    }
    /**
     * ��ȡ���ݹؼ�����ʾ�ַ�,ȥ��{}
     * @return Returns the imageName.
     */
    public String getKeywordsDesc()
    {
        String s = (String) this.getProperty("keywords").getValue();
        if(s==null || s.equals(""))
        {
            return "";
        }
        
        char[] c =s.toCharArray();
        char[] d = new char[c.length];
        int j=0;
        for(int i=0;i<c.length;i++)
        {
            if(c[i]!='{' && c[i]!='}')
            {
                d[j++]=c[i];
            }
        }
        return new String(d,0,j);
    }
    public String getKeywordsFormat()
    {
        String s = getKeywordsDesc();
        s = s.replaceAll(";", "\r");
        return s;
        
    }
    /**
     * ��ȡ���ݱ�ǩ�б�,��Ҫ�ǲ��ʹ��
     * @return
     */
    public List getKeywordsList()
    {
        return (List) this.getProperty("keywordsList").getValue();
    }

    /**
     * ���ݱ�ǩ,���ñ�ǩ�б�
     *
     */
    public void setKeywordsList()
    {
        String s = getKeywordsDesc();
        String[] ss = s.split(";");
        List list = new ArrayList();
        for(int i=0;i<ss.length;i++)
            list.add(ss[i]);

        setKeywordsList(list);
    }
    
    /**
     * ����List,���ñ�ǩ�б�
     * @param list
     */
    public void setKeywordsList(List list)
    {
        Property pro = new Property("keywordsList", list);
        this.setProperty(pro);
    }

    /**
     * ��ȡ��������
     * @return Returns the createDate.
     */
    public String getCreateDate()
    {
        return (String) this.getProperty("createDate").getValue();
    }


    /**
     * ���õ�������
     * @param createDate The createDate to set.
     */
    public void setCreateDate(String createDate)
    {
        Property pro = new Property("createDate", createDate);
        this.setProperty(pro);
    }
    
    /**
     * ������������ʱ��
     * @param marketDate
     */
    public void setMarketDate(String marketDate)
    {
        Property pro = new Property("marketDate", marketDate);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ��������ʱ��
     * @return
     */
    public String getMarketDate()
    {       
        return (String) this.getProperty("marketDate").getValue();
    }
    
    /**
     * ����ʱ��
     * @param LupdDate
     */
    public void setLupdDate(String LupdDate)
    {
        Property pro = new Property("lupdDate", LupdDate);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ����ʱ��
     * @return
     */
    public String getLupdDate()
    {
        return (String) this.getProperty("lupdDate").getValue(); 
    }
    
    
    /**
     * ��������������ʱ��
     * @param LupdDate
     */
    public void setPlupdDate(String PlupdDate)
    {
        Property pro = new Property("plupdDate", PlupdDate);
        this.setProperty(pro);
    }
    /**
     * ��ȡ��������������ʱ��
     * @return
     */
    public String getPlupdDate()
    {
        return (String) this.getProperty("plupdDate").getValue(); 
    }
    
    /**
     * ����״̬
     * @param OtherNet
     */
    public void setOtherNet(String otherNet)
    {
        Property pro = new Property("otherNet", otherNet);
        this.setProperty(pro);
    }
    /**
     * ��ȡ����״̬
     * @return
     */
    public String getOtherNet()
    {
        return (String) this.getProperty("otherNet").getValue(); 
    }
    
    /**
     * ���������ۼƶ���������ֵ�ӱ�����
     */
    public void setOrderTimes(int orderTimes)
    {
        Property pro = new Property("orderTimes", new Integer(orderTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����ۼƶ�������
     * @return
     */
    public int getOrderTimes()
    {
        return (( Integer ) this.getProperty("orderTimes").getValue()).intValue();
    }
    
    /**
     * �����������ݶ���������ֵ�ӱ�����
     */
    public void setWeekOrderTimes(int weekOrderTimes)
    {
        Property pro = new Property("weekOrderTimes", new Integer(weekOrderTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�������ݶ�������
     * @return
     */
    public int getWeekOrderTimes()
    {
        return (( Integer ) this.getProperty("weekOrderTimes").getValue()).intValue();
    }
    
    /**
     * �����������ݶ���������ֵ�ӱ�����
     */
    public void setMonthOrderTimes(int monthOrderTimes)
    {
        Property pro = new Property("monthOrderTimes", new Integer(monthOrderTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�������ݶ�������
     * @return
     */
    public int getMonthOrderTimes()
    {
        return (( Integer ) this.getProperty("monthOrderTimes").getValue()).intValue();
    }
    
    /**
     * �����������ݶ���������ֵ�ӱ�����
     */
    public void setDayOrderTimes(int dayOrderTimes)
    {
        Property pro = new Property("dayOrderTimes", new Integer(dayOrderTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�������ݶ�������
     * @return
     */
    public int getDayOrderTimes()
    {
        return (( Integer ) this.getProperty("dayOrderTimes").getValue()).intValue();
    }
    
    /**
     * ���������ۼ�����������ֵ�ӱ�����
     */
    public void setSearchTimes(int searchTimes)
    {
        Property pro = new Property("searchTimes", new Integer(searchTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����ۼ���������
     * @return
     */
    public int getSearchTimes()
    {
        return (( Integer ) this.getProperty("searchTimes").getValue()).intValue();
    }
    
    /**
     * ����������������������ֵ�ӱ�����
     */
    public void setWeekSearchTimes(int weekSearchTimes)
    {
        Property pro = new Property("weekSearchTimes", new Integer(weekSearchTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ����������������
     * @return
     */
    public int getWeekSearchTimes()
    {
        return (( Integer ) this.getProperty("weekSearchTimes").getValue()).intValue();
    }
    
    /**
     * ����������������������ֵ�ӱ�����
     */
    public void setMonthSearchTimes(int monthSearchTimes)
    {
        Property pro = new Property("monthSearchTimes", new Integer(monthSearchTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ����������������
     * @return
     */
    public int getMonthSearchTimes()
    {
        return (( Integer ) this.getProperty("monthSearchTimes").getValue()).intValue();
    }
    
    /**
     * ����������������������ֵ�ӱ�����
     */
    public void setDaySearchTimes(int daySearchTimes)
    {
        Property pro = new Property("daySearchTimes", new Integer(daySearchTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ����������������
     * @return
     */
    public int getDaySearchTimes()
    {
        return (( Integer ) this.getProperty("daySearchTimes").getValue()).intValue();
    }
    
    /**
     * ���������ۼ����������ֵ�ӱ�����
     */
    public void setScanTimes(int ScanTimes)
    {
        Property pro = new Property("scanTimes", new Integer(ScanTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����ۼ��������
     * @return
     */
    public int getScanTimes()
    {
        return (( Integer ) this.getProperty("scanTimes").getValue()).intValue();
    }
    
    /**
     * ���������������������ֵ�ӱ�����
     */
    public void setWeekScanTimes(int weekScanTimes)
    {
        Property pro = new Property("weekScanTimes", new Integer(weekScanTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���������������
     * @return
     */
    public int getWeekScanTimes()
    {
        return (( Integer ) this.getProperty("weekScanTimes").getValue()).intValue();
    }
    
    /**
     * ���������������������ֵ�ӱ�����
     */
    public void setMonthScanTimes(int monthScanTimes)
    {
        Property pro = new Property("monthScanTimes", new Integer(monthScanTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���������������
     * @return
     */
    public int getMonthScanTimes()
    {
        return (( Integer ) this.getProperty("monthScanTimes").getValue()).intValue();
    }
    
    /**
     * ���������������������ֵ�ӱ�����
     */
    public void setDayScanTimes(int dayScanTimes)
    {
        Property pro = new Property("dayScanTimes", new Integer(dayScanTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���������������
     * @return
     */
    public int getDayScanTimes()
    {
        return (( Integer ) this.getProperty("dayScanTimes").getValue()).intValue();
    }
    
    /**
     * ���������ۼ����۴�����ֵ�ӱ�����
     */
    public void setCommentTimes(int commentTimes)
    {
        Property pro = new Property("commentTimes", new Integer(commentTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����ۼ����۴���
     * @return
     */
    public int getCommentTimes()
    {
        return (( Integer ) this.getProperty("commentTimes").getValue()).intValue();
    }
    
    /**
     * ���������������۴�����ֵ�ӱ�����
     */
    public void setWeekCommentTimes(int weekCommentTimes)
    {
        Property pro = new Property("weekCommentTimes", new Integer(weekCommentTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����������۴���
     * @return
     */
    public int getWeekCommentTimes()
    {
        return (( Integer ) this.getProperty("weekCommentTimes").getValue()).intValue();
    }
    
    /**
     * ���������������۴�����ֵ�ӱ�����
     */
    public void setMonthCommentTimes(int monthCommentTimes)
    {
        Property pro = new Property("monthCommentTimes", new Integer(monthCommentTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����������۴���
     * @return
     */
    public int getMonthCommentTimes()
    {
        return (( Integer ) this.getProperty("monthCommentTimes").getValue()).intValue();
    }
    
    /**
     * ���������������۴�����ֵ�ӱ�����
     */
    public void setDayCommentTimes(int dayCommentTimes)
    {
        Property pro = new Property("dayCommentTimes", new Integer(dayCommentTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����������۴���
     * @return
     */
    public int getDayCommentTimes()
    {
        return (( Integer ) this.getProperty("dayCommentTimes").getValue()).intValue();
    }

    /**
     * ���������ۼ����ִ�����ֵ�ӱ�����
     */
    public void setMarkTimes(int markTimes)
    {
        Property pro = new Property("markTimes", new Integer(markTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����ۼ����ִ���
     * @return
     */
    public int getMarkTimes()
    {
        return (( Integer ) this.getProperty("markTimes").getValue()).intValue();
    }
    
    /**
     * ���������������ִ�����ֵ�ӱ�����
     */
    public void setWeekMarkTimes(int weekMarkTimes)
    {
        Property pro = new Property("weekMarkTimes", new Integer(weekMarkTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����������ִ���
     * @return
     */
    public int getWeekMarkTimes()
    {
        return (( Integer ) this.getProperty("weekMarkTimes").getValue()).intValue();
    }
    
    /**
     * ���������������ִ�����ֵ�ӱ�����
     */
    public void setMonthMarkTimes(int monthMarkTimes)
    {
        Property pro = new Property("monthMarkTimes", new Integer(monthMarkTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����������ִ���
     * @return
     */
    public int getMonthMarkTimes()
    {
        return (( Integer ) this.getProperty("monthMarkTimes").getValue()).intValue();
    }
    
    /**
     * ���������������ִ�����ֵ�ӱ�����
     */
    public void setDayMarkTimes(int dayMarkTimes)
    {
        Property pro = new Property("dayMarkTimes", new Integer(dayMarkTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����������ִ���
     * @return
     */
    public int getDayMarkTimes()
    {
        return (( Integer ) this.getProperty("dayMarkTimes").getValue()).intValue();
    }
    
    /**
     * ���������ۼ��Ƽ�������ֵ�ӱ�����
     */
    public void setCommendTimes(int commendTimes)
    {
        Property pro = new Property("commendTimes", new Integer(commendTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����ۼ��Ƽ�����
     * @return
     */
    public int getCommendTimes()
    {
        return (( Integer ) this.getProperty("commendTimes").getValue()).intValue();
    }
    
    /**
     * �������������Ƽ�������ֵ�ӱ�����
     */
    public void setWeekCommendTimes(int weekCommendTimes)
    {
        Property pro = new Property("weekCommendTimes", new Integer(weekCommendTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���������Ƽ�����
     * @return
     */
    public int getWeekCommendTimes()
    {
        return (( Integer ) this.getProperty("weekCommendTimes").getValue()).intValue();
    }
    
    /**
     * �������������Ƽ�������ֵ�ӱ�����
     */
    public void setMonthCommendTimes(int monthCommendTimes)
    {
        Property pro = new Property("monthCommendTimes", new Integer(monthCommendTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���������Ƽ�����
     * @return
     */
    public int getMonthCommendTimes()
    {
        return (( Integer ) this.getProperty("monthCommendTimes").getValue()).intValue();
    }
    
    /**
     * �������������Ƽ�������ֵ�ӱ�����
     */
    public void setDayCommendTimes(int dayCommendTimes)
    {
        Property pro = new Property("dayCommendTimes", new Integer(dayCommendTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���������Ƽ�����
     * @return
     */
    public int getDayCommendTimes()
    {
        return (( Integer ) this.getProperty("dayCommendTimes").getValue()).intValue();
    }
    
    /**
     * ���������ۼ��ղش�����ֵ�ӱ�����
     */
    public void setCollectTimes(int collectTimes)
    {
        Property pro = new Property("collectTimes", new Integer(collectTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�����ۼ��ղش���
     * @return
     */
    public int getCollectTimes()
    {
        return (( Integer ) this.getProperty("collectTimes").getValue()).intValue();
    }
    
    /**
     * �������������ղش�����ֵ�ӱ�����
     */
    public void setWeekCollectTimes(int weekCollectTimes)
    {
        Property pro = new Property("weekCollectTimes", new Integer(weekCollectTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���������ղش���
     * @return
     */
    public int getWeekCollectTimes()
    {
        return (( Integer ) this.getProperty("weekCollectTimes").getValue()).intValue();
    }
    
    /**
     * �������������ղش�����ֵ�ӱ�����
     */
    public void setMonthCollectTimes(int monthCollectTimes)
    {
        Property pro = new Property("monthCollectTimes", new Integer(monthCollectTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���������ղش���
     * @return
     */
    public int getMonthCollectTimes()
    {
        return (( Integer ) this.getProperty("monthCollectTimes").getValue()).intValue();
    }
    
    /**
     * �������������ղش�����ֵ�ӱ�����
     */
    public void setDayCollectTimes(int dayCollectTimes)
    {
        Property pro = new Property("dayCollectTimes", new Integer(dayCollectTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���������ղش���
     * @return
     */
    public int getDayCollectTimes()
    {
        return (( Integer ) this.getProperty("dayCollectTimes").getValue()).intValue();
    }
    
    /**
     * �������ּ�Ȩƽ��ֵ
     */
    public void setAverageMark(int averageMark)
    {
        Property pro = new Property("averageMark", new Integer(averageMark));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ���ּ�Ȩƽ��ֵ
     * @return
     */
    public int getAverageMark()
    {
        return (( Integer ) this.getProperty("averageMark").getValue()).intValue();
    }

    /**
     * ��ȡ�������͵����������ַ�
     * @return String ���͵���������
     */
    public String getTypeDesc()
    {
        return GContentFactory.getTypeDesc(this);
    }
    
    /**
     * �õ���Ϊnull��String����
     * @param name
     * @return
     */
    public static String getNoNullString(String name)
    {
        return name == null ? "" : name;
    }
    
    /**
     * �������߷�Χ������/ȫ��
     * @param servAttre to set.
     */
    public void setServAttr(String servAttr)
    {
        Property pro = new Property("servAttr", servAttr);
        this.setProperty(pro);
        
        setKeywordsList();
    }

    /**
     * �������߷�Χ������/ȫ��
     * @return Returns the servAttr.
     */
    public String getServAttr()
    {
        return (String) this.getProperty("servAttr").getValue();
    }
    /**
     * ��ȡӦ������� 1��ʾwidgetӦ��
     * @return Returns the subType.
     */
    public String getSubType()
    {
        return (String) this.getProperty("subType").getValue();
    }


    /**
     * ����Ӧ������� 1��ʾwidgetӦ��
     * @param chargeTime
     */
    public void setSubType(String subType)
    {
        Property pro = new Property("subType", subType);
        this.setProperty(pro);
    }
    
	/**
	 * ģ�������豸ID zhangweixing add 
	 * @param matchDeviceid
	 */
	public void setMatch_Deviceid(String matchDeviceid) {
		Property pro = new Property("match_Deviceid", matchDeviceid);
		this.setProperty(pro);
	}

	/**
	 * ģ�������豸ID zhangweixing add 
	 * @return
	 */
	public String getMatch_Deviceid() {
		return getNoNullString((String) this.getProperty("match_Deviceid")
				.getValue());
	}
    
    /**
     * �����ն���������ֵ�ӱ�����
     */
    public void setMoDayOrderTimes(int moDayOrderTimes)
    {
        Property pro = new Property("moDayOrderTimes", new Integer(moDayOrderTimes));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�ն�������
     * @return
     */
    public int getMoDayOrderTimes()
    {
        return (( Integer ) this.getProperty("moDayOrderTimes").getValue()).intValue();
    }
    
    /**
	 * Ӧ�ý��ܣ�ͼ�ģ� 
	 * @param richAppdesc
	 */
	public void setRichAppdesc(String richAppdesc) {
		Property pro = new Property("richAppdesc", richAppdesc);
		this.setProperty(pro);
	}

	/**
	 * Ӧ�ý��ܣ�ͼ�ģ�
	 * @return
	 */
	public String getRichAppdesc() {
		return getNoNullString((String) this.getProperty("richAppdesc")
				.getValue());
	}
	
	 /**
     * ���ͼ  
     * @return Returns the advertPic.
     */
    public String getAdvertPic()
    {
        return (String) this.getProperty("advertPic").getValue();
    }


    /**
     * ���ͼ  
     * @param chargeTime
     */
    public void setAdvertPic(String advertPic)
    {
        Property pro = new Property("advertPic", advertPic);
        this.setProperty(pro);
    }
    
    /**
     * wp��ҵ��������URL
     * @return Returns the AuditionUrl.
     */
    public String getAuditionUrl()
    {
        return (String) this.getProperty("auditionUrl").getValue();
    }


    /**
     * wp��ҵ��������URL  
     * @param AuditionUrl
     */
    public void setAuditionUrl(String auditionUrl)
    {
        Property pro = new Property("auditionUrl", auditionUrl);
        this.setProperty(pro);
    }

	/**
	 * ������
	 * 
	 * @param mapname
	 */
	public void setMapname(String mapname) {
		Property pro = new Property("mapname", mapname);
		this.setProperty(pro);
	}

	/**
	 * ������
	 * 
	 * @return
	 */
	public String getMapname() {
		return (String) this.getProperty("mapname").getValue();
	}
	 /**
     * ��ȡ����״̬
     * @return Returns the spName.
     */
    public String getVerifyStatus()
    {
        return getNoNullString((String) this.getProperty("verifyStatus").getValue());
    }


    /**
     * ��������״̬
     * @param spName The spName to set.
     */
    public void setVerifyStatus(String verifyStatus)
    {
        Property pro = new Property("verifyStatus", verifyStatus);
        this.setProperty(pro);
    }

    /**
	 * ��������������ַ����ͣ�0��δѡ�����зַ���MM�ͻ��ˣ���1����ѡ�����зַ���������ֻ�пͻ��ˣ�
	 * 
	 * @param channeldisptype
	 */
	public void setChannelDispType(String channeldisptype) {
		Property pro = new Property("channeldisptype", channeldisptype);
		this.setProperty(pro);
	}

	/**
	 * ��������������ַ����ͣ�0��δѡ�����зַ���MM�ͻ��ˣ���1����ѡ�����зַ���������ֻ�пͻ��ˣ�
	 * 
	 * @return
	 */
	public String getChannelDispType() {
		return (String) this.getProperty("channeldisptype").getValue();
	}
    
}
