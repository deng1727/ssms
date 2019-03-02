package com.aspire.ponaadmin.web.risktag.bo;

import java.util.ArrayList;
import java.util.List;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.blacklist.bo.AndroidBlackListBO;
import com.aspire.ponaadmin.web.category.blacklist.dao.AndroidBlackListDAO;
import com.aspire.ponaadmin.web.category.blacklist.vo.AndroidBlackListVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.risktag.dao.RiskDAO;
import com.aspire.ponaadmin.web.risktag.dao.RiskTagDAO;
import com.aspire.ponaadmin.web.risktag.vo.RiskTagVO;

public class RiskTagBo {
	/**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(RiskTagBo.class);

    private static RiskTagBo instance = new RiskTagBo();
    
    private RiskTagBo(){
    	
    }
    
    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static RiskTagBo getInstance()
    {
        return instance;
    }
	 /**
     * ���ڲ�ѯ��Ԫ�����б�
     * 
     * @param page
     * @param tagName
     * @throws BOException
     */
    public void queryList(PageResult page,String riskid,String stats,String content,String contentid)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("RiskTagBo.queryList( ) is start...");
        }

        try
        {
            // ����TagManagerDAO���в�ѯ
        	RiskTagDAO.getInstance().queryTagList(page,riskid,stats,content,contentid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ��Ԫ�����б�ʱ�������ݿ��쳣��");
        }
    }
    
    public int doEdit(String[] contentids ,String isblack) throws BOException{
    	int k = 0 ;
    	for (String contentid : contentids) {
    		try {
    			
    			RiskTagDAO.getInstance().doDeleteBlack(contentid);
    	    	k=RiskTagDAO.getInstance().doInsert(contentid, isblack);
    		} catch (DAOException e) {
    			logger.error(e);
    			 throw new BOException("�����������ʱ����");
    		}
		}
    	return k;
    }
    
    public void sendmessage(String[] contentids,String isblack) throws DAOException{
    	TransactionDB tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
        PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
        if ("1".equals(isblack)) {
        	for (String contentid : contentids) {
        		dao.addMessages(MSGType.ContentModifyReq, "", contentid + ":9");
    		}
		}else {
			for (String contentid : contentids) {
        		dao.addMessages(MSGType.ContentModifyReq, "", contentid + ":1");
    		}
		}
    	
    	tdb.commit();
    }
    public void sendmessages(String riskid,String isblack) throws Exception{
    	
    	//��ѯ��riskidΪ�ض�ֵ��contentid
    	ArrayList<String > ls = RiskTagDAO.getInstance().doSelectBlack(riskid);
    	TransactionDB tdb = null;
    	if (ls.isEmpty()) {
			throw new Exception("�����ε�Ӧ��");
		}else {
			tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
            PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
            if ("0".equals(isblack)) {
            	for (int i = 0; i < ls.size(); i++) {
					dao.addMessages(MSGType.ContentModifyReq, "", ls.get(i) + ":1");
					RiskTagDAO.getInstance().doDeleteBlack(ls.get(i));
					RiskTagDAO.getInstance().doInsert(ls.get(i), isblack);
				}
			}else {
				for (int i = 0; i < ls.size(); i++) {
					dao.addMessages(MSGType.ContentModifyReq, "", ls.get(i) + ":9");
					RiskTagDAO.getInstance().doDeleteBlack(ls.get(i));
					RiskTagDAO.getInstance().doInsert(ls.get(i), isblack);
				}
				
			}
            RiskDAO.getInstance().doUpdateBlack(isblack, riskid);
			tdb.commit();
			
		}
    	
    	
    }
    
    public void exportTagData(WritableWorkbook wwb,String type ,String riskid,String contentid) throws DAOException{
    	List<RiskTagVO> lists = null ;
    	if (null == riskid || "".equals(riskid)) {
    		lists = RiskTagDAO.getInstance().output(type);
		}else {
			lists = RiskTagDAO.getInstance().doOutput(riskid, contentid);
		}
    	generateQueryDataExcel(lists ,wwb);
    }
    
    private void generateQueryDataExcel(List<RiskTagVO> lists, WritableWorkbook wwb)
	{
		String sheetName = "Sheet";
		WritableSheet ws = wwb.createSheet(sheetName, 0); // ����sheet
		Label label = null;
		int row = 0;
		int col = 0;
		int sheet = 0;
		try
		{
			for (int i = 0; i < lists.size(); i++)
			{
				if(i%65533 == 0)
				{
					ws = wwb.createSheet(sheetName + sheet, sheet++);
					setTitle(ws);
					row = 0;
				}
				
				RiskTagVO vo = (RiskTagVO) lists.get(i);
				col = 0;
				row++;

				label = new Label(col++, row, vo.getContentID());
				ws.addCell(label);
				label = new Label(col++, row, vo.getName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getCompany());
				ws.addCell(label);
				label = new Label(col++, row, vo.getType());
				ws.addCell(label);
				label = new Label(col++, row, vo.getTime());
				ws.addCell(label);
				String des = "չʾ";
				if ("1".equals(vo.getIsblack())) {
					des="����";
				}
				label = new Label(col++, row, des);
				ws.addCell(label);
			}
		}
		catch (RowsExceededException e)
		{
			e.printStackTrace();
		}
		catch (WriteException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
    
    /**
	 * ���ð�Ԫ����excel�ĵ�һ�б���
	 * 
	 * @param ws
	 */
	private void setTitle(WritableSheet ws) throws RowsExceededException, WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "����ID");
		ws.addCell(label);
		label = new Label(col++, row, "��������");
		ws.addCell(label);
		label = new Label(col++, row, "�ṩ��");
		ws.addCell(label);
		label = new Label(col++, row, "��������");
		ws.addCell(label);
		label = new Label(col++, row, "����ʱ��");
		ws.addCell(label);
		label = new Label(col++, row, "״̬");
		ws.addCell(label);

	}
}
