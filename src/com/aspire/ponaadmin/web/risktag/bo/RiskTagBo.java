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
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(RiskTagBo.class);

    private static RiskTagBo instance = new RiskTagBo();
    
    private RiskTagBo(){
    	
    }
    
    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static RiskTagBo getInstance()
    {
        return instance;
    }
	 /**
     * 用于查询榜单元数据列表
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
            // 调用TagManagerDAO进行查询
        	RiskTagDAO.getInstance().queryTagList(page,riskid,stats,content,contentid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询榜单元数据列表时发生数据库异常！");
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
    			 throw new BOException("黑名单表操作时出错！");
    		}
		}
    	return k;
    }
    
    public void sendmessage(String[] contentids,String isblack) throws DAOException{
    	TransactionDB tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
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
    	
    	//查询出riskid为特定值的contentid
    	ArrayList<String > ls = RiskTagDAO.getInstance().doSelectBlack(riskid);
    	TransactionDB tdb = null;
    	if (ls.isEmpty()) {
			throw new Exception("无屏蔽的应用");
		}else {
			tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
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
		WritableSheet ws = wwb.createSheet(sheetName, 0); // 创建sheet
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
				String des = "展示";
				if ("1".equals(vo.getIsblack())) {
					des="屏蔽";
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
	 * 设置榜单元数据excel的第一列标题
	 * 
	 * @param ws
	 */
	private void setTitle(WritableSheet ws) throws RowsExceededException, WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "内容ID");
		ws.addCell(label);
		label = new Label(col++, row, "内容名称");
		ws.addCell(label);
		label = new Label(col++, row, "提供商");
		ws.addCell(label);
		label = new Label(col++, row, "内容类型");
		ws.addCell(label);
		label = new Label(col++, row, "上线时间");
		ws.addCell(label);
		label = new Label(col++, row, "状态");
		ws.addCell(label);

	}
}
