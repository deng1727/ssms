
package com.aspire.ponaadmin.web.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * ����ִ���������ݴ�ҵ�������ܵ�ִ�й���ִ�е�����ҵ�������ܵĸ�������
 * 
 * @author zhangwei
 * 
 */
public class CategoryRuleByCarveOutExcutor
{

    /**
     * �ô�ҵ�������ܵ���Ϣ
     */
    private CategoryRuleByCarveOutVO vo;

    /**
     * ִ�и÷�����task��
     */
    private CategoryRuleByCarveOutTask task;

    /**
     * ��־����
     */
    private static JLogger LOG = LoggerFactory.getLogger(CategoryRuleByCarveOutExcutor.class);

    /**
     * ���������Ӧ����Ϣ
     */
    public static Map BLACKAPPCACHE = Collections.synchronizedMap(new HashMap());
    /**
     * �����Զ����»���SQL��� ADD BY dongke 20110503
     */
    public static Map BASESQLCACHE = Collections.synchronizedMap(new HashMap());


    /**
     * ���췽��
     * 
     * @param vo
     */
    public CategoryRuleByCarveOutExcutor(CategoryRuleByCarveOutVO vo)
    {
        this.vo = vo;
    }

    /**
     * ���̣߳�ȫ������ִ������
     * 
     * @param vo
     * @param task
     */
    public CategoryRuleByCarveOutExcutor(CategoryRuleByCarveOutVO vo,
                                         CategoryRuleByCarveOutTask task)
    {
        this.vo = vo;
        this.task = task;
    }

    public int excucte() throws BOException
    {
        if (vo == null)
        {
            throw new BOException("��ҵ�������ܸ��¹���Ϊnull");
        }
        Date date = vo.getEffectiveTime();
        if (!isEffective(date))
        {
            LOG.info("��ҵ��������idΪ" + vo.getCid() + "�Ļ��ܻ�û����Ч������Ҫִ�С���Чʱ��:"
                     + PublicUtil.getDateString(date));
            return CategoryRuleByCarveOutTask.INEFFECTIVE;
        }
        LOG.info("��ʼ�Դ�ҵ��������idΪ��" + vo.getCid() + "�Ļ��ܽ��и���");
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�ô�ҵ�������ܵĹ���Ϊ��" + vo.getName());
        }

        // ���ݹ����е���Ʒ�Ƿ���Ҫ�Զ����ɺ���Ʒ�Ƿ���Ҫ���¼����������жϱ����Ƿ���Ҫ����û��ܡ�
        // �������ֵ���ǡ��񡱣����β���Ҫ����û��ܡ�
        Category cate = ( Category ) Repository.getInstance()
                                               .getNode(vo.getCid(),
                                                        RepositoryConstants.TYPE_CATEGORY);
        if (cate == null)
        {
            LOG.info("�Ҳ���Ҫ���µĴ�ҵ�������ܣ�cateId=" + vo.getCid());
            throw new BOException("�Ҳ���Ҫ���µĴ�ҵ��������");
        }
        if (cate.getDelFlag() == 1)//
        {
            LOG.info("�ô�ҵ���������Ѿ��¼ܣ�cateId=" + vo.getCid());
            throw new BOException("�ô�ҵ���������Ѿ��¼�,�������ƣ�" + cate.getName());
        }

        // �����ϴ�ִ��ʱ�俴�Ƿ���Ҫ����ִ�С�
        int sortId = 1000;// Ĭ�ϴ�1000��ʼ����

        if (IsNeedExecution())
        {
            // �Ȼ�ȡ�û�������Ʒ
            LOG.info("��ҵ�������ܸ��¿�ʼ��������");

            // ��ʼ��������Ӧ�õ�����
            if (BLACKAPPCACHE == null || BLACKAPPCACHE.size() <= 0)
            {
                this.getBlackContentList();
            }
//          ��ʼ��������Ӧ�õ�����
            if (BASESQLCACHE == null || BASESQLCACHE.size() <= 0)
            {
                this.getBaseSQL();
            }
            // ��Ҫ��Ʒ�������ɡ�
            if (vo.getRuleType() == CategoryConstants.RULETYPE_REFRESH)
            {
                // ��ž�Ʒ��Ͳ�Ʒ�����Ʒ��
                List productList = new ArrayList();

                // �л�ȡ��Ʒ���������ִ�С�
                productList.addAll(getContentIDList(vo));

                // ��ӡ��־
                LOG.info("ȥ������");

                // ȥ������
                PublicUtil.removeDuplicateWithOrder(productList);

                // ��ӡ��־
                LOG.info("APӦ�ú�����");

                // ȥ��APӦ�ú������е�Ӧ��
                this.removeConInBlackList(productList);

                // ��Ҫ���¼�������Ʒ
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("��ʼ�¼ܱ���ҵ�������ܵ�������Ʒ������");
                }

                // ��ӡ��־
                LOG.info("�¼���Ʒ");

                CategoryTools.clearCateGoods(vo.getCid(), false);

                // �ϼ���Ʒ �ϼܲ�Ʒ��ġ�
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("��ʼ�ϼܲ�Ʒ�����Ʒ��");
                }

                for (int i = 0; i < productList.size(); i++)
                {
                    String contId = ( String ) productList.get(i);

                    CategoryTools.addGood(cate,
                                          contId,
                                          sortId--,
                                          RepositoryConstants.VARIATION_NEW,
                                          true,
                                          null);
                }

                // ��ӡ��־
                LOG.info("�ϼ����");
            }
            // ˢ�º�����������Ʒʱ���������
            else if (vo.getRuleType() == CategoryConstants.RULETYPE_REORDER)
            {
                // ��ȡ��ǰ��������Ʒ�����ź���
                LOG.info("������������ʼ��������");

                // ��Ʒ����Ĺ��������ֻ������һ���������ɡ�
                if ("".equals(vo.getWSql()) && "".equals(vo.getOSql()))
                {
                    LOG.error("���������û�ж������������������ܲ���������");
                    throw new BOException("���������û�����������쳣");
                }

                // �õ���������������б�ֵ
                List list = getSortedGoods(cate, vo);

                // ������Ӧ���õ�
                this.changeToLastBlackApp(list);

                List goodList = new ArrayList();

                // ���������
                for (int i = 0; i < list.size(); i++)
                {
                    ReferenceNode node = new ReferenceNode();
                    node.setId(( String ) list.get(i));
                    node.setSortID(sortId--);
                    goodList.add(node);
                }
                // ���µ����ݿ��С�
                if (goodList.size() > 0)// ֻ�к�����Ʒ����Ҫ��������ֵ��
                {
                    updateSortId(goodList);
                }
            }

            // ���»��ܵ����һ����Ʒ�Զ�����ִ��ʱ�䡣
            CategoryRuleDAO.getInstance()
                           .updateRuleLastExecTimeByCarveOut(vo.getCid(),
                                                             new Date());
            return CategoryRuleByCarveOutTask.UPDATESUCESS;
        }
        else
        {
            LOG.info("���β���Ҫִ�С���ҵ������������id=" + vo.getCid());
            return CategoryRuleByCarveOutTask.EXECUTED;
        }
    }

    /**
     * ��ȡAPˢ�������Ӧ��
     * 
     * @return
     * @throws BOException
     */
    private HashMap getBlackContentList() throws BOException
    {
        HashMap blackContentMap = new HashMap();
        String sqlCode = "category.rule.CategoryRuleByCarveOutExcutor.getBlackContentList().SELECT";
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            if (LOG.isDebugEnabled())
            {
                LOG.debug("�����ݿ��ȡӦ�ú������б�");
            }
            while (rs.next())
            {
                BLACKAPPCACHE.put(rs.getString("cid"), "1");
            }
        }
        catch (DAOException e)
        {
            e.printStackTrace();
            LOG.error("�����ݿ��ȡӦ�ú������б�,DAO��������" + e);
            throw new BOException("DAO��������", e);
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
            LOG.error("�����ݿ��ȡӦ�ú������б�,DAO��������" + e1);
            throw new BOException("SQL��������", e1);
        }
        finally
        {
            DB.close(rs);
        }
        return blackContentMap;
    }
    /**
     * ��ȡAPˢ�������Ӧ��
     * 
     * @return
     * @throws BOException
     */
    private HashMap getBaseSQL() throws BOException
    {
        HashMap baseSQLMap = new HashMap();
        String sqlCode = "category.rule.CategoryRuleByCarveOutExcutor.getBaseSQL().SELECT";
        ResultSet rs = null;
        DB db = DB.getInstance();
        try
        {
            rs = db.queryBySQLCode(sqlCode, null);

            if (LOG.isDebugEnabled())
            {
                LOG.debug("�����ݿ��ȡ��ҵ��������SQL���");
            }
            while (rs.next())
            {
            	BASESQLCACHE.put(rs.getString("id"), rs.getString("BASESQL"));
            	baseSQLMap.put(rs.getString("id"), rs.getString("BASESQL"));
            }
        }
        catch (DAOException e)
        {
            e.printStackTrace();
            LOG.error("�����ݿ��ȡ��ҵ�����Զ����»�������б�,DAO��������", e);
            throw new BOException("DAO��������", e);
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
            LOG.error("�����ݿ��ȡ��ҵ�����Զ����»�������б�,DAO��������", e1);
            throw new BOException("SQL��������", e1);
        }
        finally
        {
        	db.close(rs);
        }
        return baseSQLMap;
    }
    /**
     * ȥ�����ں�������Ӧ��
     * 
     * @param conId ��������
     * @return true ���Ǻ������������ϼܣ�false �Ǻ��������������ϼ�
     * @throws BOException
     */
    private synchronized void removeConInBlackList(List contentlist)
                    throws BOException
    {
        Map hm = null;
        if (BLACKAPPCACHE != null)
        {
            hm = BLACKAPPCACHE;
        }
        else
        {
            this.getBlackContentList();
            hm = BLACKAPPCACHE;
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("������Ӧ�ò��ϼ�");
        }
        for (int i = 0; i < contentlist.size(); i++)
        {
            String conId = ( String ) contentlist.get(i);
            String values = ( String ) hm.get(conId);
            // ��ֹ������©����
            if (values != null && values.equals("1"))
            {
                LOG.error("conId=" + conId + ",��APӦ�ú������У������ϼ�");
                contentlist.remove(i);
                i--;
            }

        }
    }

    /**
     * 
     * @param contentlist
     * @throws BOException
     */
    private void changeToLastBlackApp(List contentlist) throws BOException
    {
        List blackapp = new ArrayList();

        if (contentlist != null && contentlist.size() > 0)
        {
            // ��ȡ������Ӧ��
            Map hm = null;
            if (BLACKAPPCACHE != null)
            {
                hm = BLACKAPPCACHE;
            }
            else
            {
                this.getBlackContentList();
                hm = BLACKAPPCACHE;
            }

            if (LOG.isDebugEnabled())
            {
                LOG.debug("������Ӧ���õ�");
            }

            for (int i = 0; i < contentlist.size(); i++)
            {
                String conId = ( String ) contentlist.get(i);
                ReferenceNode ref = ( ReferenceNode ) Repository.getInstance()
                                                                .getNode(conId,
                                                                         RepositoryConstants.TYPE_REFERENCE);
                String values = ( String ) hm.get(ref.getRefNodeID());
                if (values != null && values.equals("1"))
                {
                    // ��ֹ������©����
                    LOG.error("conId=" + conId + ",��APӦ�ú������У��õ���ʾ-move to last");
                    blackapp.add(contentlist.get(i));
                    contentlist.remove(i);
                    i--;
                }
            }
            contentlist.addAll(blackapp);
        }
    }

    /**
     * �жϵ�ǰʱ��,�˴������Ƿ���Ҫִ�С�
     * 
     * @return
     */
    private boolean IsNeedExecution()// (Date lastExcuteTime, int period)
    {

        Calendar now = Calendar.getInstance();
        if (CategoryConstants.INTERVALTYPE_DAY == vo.getIntervalType())
        {
            if (vo.getLastExcuteTime() == null)
            {
                return true;
            }
            Calendar lastDate = Calendar.getInstance();
            // ��Ҫ��Date����ת��ΪCalendar
            lastDate.setTime(vo.getLastExcuteTime());
            return compareDateDif(now,
                                  lastDate,
                                  Calendar.DAY_OF_MONTH,
                                 // vo.getIntervalCount());
                                  vo.getExcuteInterval());
        }
        else if (vo.getIntervalType() == CategoryConstants.INTERVALTYPE_WEEK)

        {
            // Calendar����ֵ�У�������1��������7,��Ҫת�����й��ı�׼��
            // ��һ��1��������7
            int weekDay = now.get(Calendar.DAY_OF_WEEK);
            weekDay = weekDay - 1;
            if (weekDay == 0)
            {
                weekDay = 7;
            }
            if (weekDay != vo.getExcuteTime())
            
            {
                return false;
            }
            if (vo.getLastExcuteTime() == null)
            {
                return true;
            }
            Calendar lastDate = Calendar.getInstance();
            // ��Ҫ��Date����ת��ΪCalendar
            lastDate.setTime(vo.getLastExcuteTime());
            return compareDateDif(now,
                                  lastDate,
                                  Calendar.WEEK_OF_MONTH,
                                //  vo.getIntervalCount());
                                  vo.getExcuteInterval());
        }
        else if (vo.getIntervalType() == CategoryConstants.INTERVALTYPE_MONTH)
        {
            int monthDay = now.get(Calendar.DAY_OF_MONTH);
            if (monthDay != vo.getExcuteTime())
            
            {
                return false;
            }
            if (vo.getLastExcuteTime() == null)
            {
                return true;
            }
            Calendar lastDate = Calendar.getInstance();
            // ��Ҫ��Date����ת��ΪCalendar
            lastDate.setTime(vo.getLastExcuteTime());
            return compareDateDif(now,
                                  lastDate,
                                  Calendar.MONTH,
                                 // vo.getIntervalCount());
                                  vo.getExcuteInterval());
        }
        else
        {
            LOG.error("Ŀǰ����֧�ִ����ͣ�IntervalType=" + vo.getIntervalType());
            return false;
        }

    }

    /**
     * �Ƚ�nowDate��lastDate���ʱ���Ƿ���ڵ���count����field Ϊ��λ��ʱ�䡣
     * 
     * @param nowDate ��ǰʱ��
     * @param lastDate �ϴθ���ʱ��
     * @param field ���ĵ�λ���գ��ܣ���
     * @param count ��λʱ��ĸ���
     */
    private boolean compareDateDif(Calendar nowDate, Calendar lastDate,
                                   int field, int count)
    {

        nowDate.add(field, -count);
        // �Ƚ��ַ����жϵ�ǰʱ���Ƿ���ͬ��
        String data1String = PublicUtil.getDateString(nowDate.getTime(),
                                                      "yyyyMMdd");
        String data2String = PublicUtil.getDateString(lastDate.getTime(),
                                                      "yyyyMMdd");

        int result = data1String.compareTo(data2String);
        if (result >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * �жϵ�ǰ�������Ƿ��Ѿ���Ч��
     * 
     * @param date ��Ч����
     * @return ��ǰ���ڴ��ڵ���date�ȱ�ʾ��Ч������Ϊ��λ���бȽϣ���
     */
    private boolean isEffective(Date date)
    {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        return now.getTime().after(date);
    }

    /**
     * �������������Ϣ
     * 
     * @param goodList
     * @throws BOException
     */
    private void updateSortId(List goodList) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ʼ���µ�ǰ���ܵ�����ֵ������");
        }
        String sqlCode = "category.rule.CategoryRuleExcutor.UPDATE.updateSortId";
        Object mutiParas[][] = new Object[goodList.size()][2];
        for (int i = 0; i < goodList.size(); i++)
        {
            ReferenceNode node = ( ReferenceNode ) goodList.get(i);
            mutiParas[i][0] = new Integer(node.getSortID());
            mutiParas[i][1] = node.getId();
        }
        try
        {
            DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
        }
        catch (DAOException e)
        {
            throw new BOException("���»�����Ʒ�����쳣");
        }
    }

    /**
     * ���ܱ�������б���ֵ
     * 
     * @param cate
     * @param condition
     * @return
     * @throws BOException
     */
    private List getSortedGoods(Category cate,
                                CategoryRuleByCarveOutVO condition)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ʼ��ȡ��ǰ�����µ�������Ʒ������");
        }
        String sql;
        try
        {
            // select r.id from t_r_gcontent g ,t_r_reference r
            // where r.categoryid=? and r.refnodeid=g.id
        	 String baseSQLId = condition.getBaseSQLId();
             sql = (String)BASESQLCACHE.get(baseSQLId);
             if(sql == null || sql.equals("")){
            	 throw  new BOException("��ȡ������ҵ�����Զ����»������"); 
             }
            //sql = ;
            	
            	//DB.getInstance()
                  //  .getSQLByCode("category.rule.CategoryRuleExcutor.SELECT.getSortedGoodsByCarveOut");
        }
        catch (Exception e1)
        {
            throw new BOException("��ѯsqlcode�쳣��category.rule.CategoryRuleExcutor.SELECT.getSortedGoodsByCarveOut");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(sql);
        if (condition.getOSql() != null && !condition.getOSql().equals(""))
        {
            sb.append(" order by ");
            sb.append(condition.getOSql());
        }

        LOG.info("getSortedGoods:" + sb.toString());
        LOG.info("paras:" + cate.getCategoryID());

        String paras[] = { cate.getCategoryID() };
        List list = new ArrayList();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().query(sb.toString(), paras);
            while (rs.next())
            {
                list.add(rs.getString("id"));
            }
        }
        catch (Exception e)
        {
            throw new BOException("�����ݿ��ȡ���ݳ���.getSortedGoods=" + sb.toString()
                                  + ",paras=" + cate.getCategoryID(), e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    private List getContentIDList(CategoryRuleByCarveOutVO condition)
                    throws BOException
    {

        String sql;
        String paras[] = null;
        String condType;
        try
        {
            condType = "��Ʒ������ҵ���ȡ����sql��";
            /**
             * select b.id from t_r_base b, t_r_gcontent g, v_service
             * v,t_content_count c where b.id = g.id and b.type like
             * 'nt:gcontent:app%' and v.icpcode = g.icpcode and v.icpservid =
             * g.icpservid and g.contentid=c.contentid(+) and g.provider !='B'
             * and g.subtype = '6'
             */
            String baseSQLId = condition.getBaseSQLId();
            sql = (String)BASESQLCACHE.get(baseSQLId);
            if(sql == null || sql.equals("")){
           	 throw  new BOException("��ȡ������ҵ�����Զ����»������"); 
            }
            	//DB.getInstance()
                   // .getSQLByCode("category.rule.CategoryRuleByCarveOutExcutor.SELECT.getOwnerContentIDList");

        }
        catch (Exception e1)
        {
            throw new BOException("��ȡ���ݳ���", e1);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(sql);
        if (condition.getWSql() != null)
        {
            sb.append(" and ");
            sb.append(condition.getWSql());
        }
        if (condition.getOSql() != null
            && !"".equals(condition.getOSql().trim()))
        {
            sb.append(" order by ");
            sb.append(condition.getOSql());
        }
        List list = new ArrayList();
        int count = condition.getCount();
        if (count == -1)// -1��ʾϵͳ�����Ƹ���,ʹ���������е����ֵ��
        {
            count = CategoryRuleByCarveOutConfig.CONDITIONMAXVALUE;// default;
        }

        // �����װsql��ֻ��ȡ�ƶ��������ݡ�
        sql = "select * from (" + sb.toString() + ")where rownum <= " + count;

        LOG.info(condType + sql);// ��ӡsql��־
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().query(sql, paras);
            while (rs.next())
            {
                list.add(rs.getString("id"));
            }
        }
        catch (Exception e)
        {
            LOG.error("��ȡ���ݳ���:" + condType + sql, e);
            throw new BOException("��ȡ���ݳ���");
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    public static void clearCache()
    {
        BLACKAPPCACHE.clear();
        BASESQLCACHE.clear();
    }

    public void task() throws Throwable
    {
        try
        {
            int result = excucte();
            if (result == CategoryRuleByCarveOutTask.EXECUTED)
            {
                task.addExecutedCount();
            }
            if (result == CategoryRuleByCarveOutTask.INEFFECTIVE)
            {
                task.addIneffectiveCount();
            }
            if (result == CategoryRuleByCarveOutTask.UPDATESUCESS)
            {
                task.addSuccCount();  
            }
        }
        catch (Exception e)
        {
            LOG.error("��ҵ�������ܸ���ʧ�ܡ�" + vo, e);
            task.addError(vo);
        }
    }
}
