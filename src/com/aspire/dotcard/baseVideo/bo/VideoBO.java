package com.aspire.dotcard.baseVideo.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.WriteActionLogAndEmailTask;
import com.aspire.dotcard.baseVideo.dao.CategoryDAO;
import com.aspire.dotcard.baseVideo.dao.ReferenceDAO;
import com.aspire.dotcard.baseVideo.dao.VideoDAO;
import com.aspire.dotcard.baseVideo.vo.VideoCategoryVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

public class VideoBO
{
	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(VideoBO.class);

	private static VideoBO instance = new VideoBO();

	private VideoBO()
	{}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static VideoBO getInstance()
	{
		return instance;
	}

	/**
	 * ���ڷ�����Ƶ�����б�
	 * 
	 * @return
	 * @throws BOException
	 */
	public List queryVideoCategoryList() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.queryVideoCategoryList( ) is start...");
		}

		try
		{
			// ����VideoDAO���в�ѯ
			return VideoDAO.getInstance().queryVideoCategoryList();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("������Ƶ�����б�ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڷ�����Ƶ��Ʒ�б�
	 * 
	 * @return
	 * @throws BOException
	 */
	public List queryVideoProductList() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.queryVideoProductList( ) is start...");
		}

		try
		{
			// ����VideoDAO���в�ѯ
			return VideoDAO.getInstance().queryVideoProductList();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("������Ƶ��Ʒ�б�ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڷ�����Ƶ������Ϣ
	 * 
	 * @return
	 * @throws BOException
	 */
	public VideoCategoryVO queryVideoCategoryVO(String videoCategoryId)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.queryVideoCategoryVO(" + videoCategoryId
					+ ") is start...");
		}

		try
		{
			// ����NewMusicCategoryDAO���в�ѯ
			return VideoDAO.getInstance().queryVideoCategoryVO(videoCategoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("������Ƶ����Ϣʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣ
	 * 
	 * @return
	 * @throws BOException
	 */
	public int hasChild(String videoCategoryId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.hasChild(" + videoCategoryId
					+ ") is start...");
		}

		try
		{
			// ����NewMusicCategoryDAO���в�ѯ
			return VideoDAO.getInstance().hasChild(videoCategoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣʱ�������ݿ��쳣��");
		}
	}

	/**
	 * �鿴��ǰ�������Ƿ񻹴�������Ʒ
	 * 
	 * @param categoryId
	 * @return
	 * @throws BOException
	 */
	public int hasVideo(String videoCategoryId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.hasVideo(" + videoCategoryId
					+ ") is start...");
		}

		try
		{
			// ����NewMusicRefDAO���в�ѯ
			return ReferenceDAO.getInstance().hasVideo(videoCategoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("�鿴��ǰ�������Ƿ񻹴�������Ʒʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڻ�ȡ��Ƶ����ID
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
	public String getVideoCategoryId() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.getVideoCategoryId() is start...");
		}
		String cid = null;
		try
		{
			// ����NewMusicCategoryDAO�������������ֻ���
			cid = VideoDAO.getInstance().getVideoCategoryId();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��ȡ��Ƶ����IDʱ�������ݿ��쳣��");
		}
		return cid;
	}

	/**
	 * ����������Ƶ����
	 * 
	 * @param videoCategoryVO
	 * @throws BOException
	 */
	public void saveVideoCategory(VideoCategoryVO videoCategoryVO)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.saveVideoCategory() is start...");
		}

		try
		{
			// ����NewMusicCategoryDAO�������������ֻ���
			VideoDAO.getInstance().saveVideoCategory(videoCategoryVO);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("������Ƶ����ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڱ����Ƶ����
	 * 
	 * @param videoCategoryVO
	 * @throws BOException
	 */
	public void updateVideoCategory(VideoCategoryVO videoCategoryVO)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO..updateNewMusicCategory() is start...");
		}

		try
		{
			// ����NewMusicCategoryDAO���б�������ֻ���
			VideoDAO.getInstance().updateVideoCategory(videoCategoryVO);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("�����Ƶ����ʱ�������ݿ��쳣��");
		}
	}

	public List updateProductFeeDescs(List updateinfo) throws BOException
	{
		List list = new ArrayList();
		// List updateinfo = paraseDataFile(dataFile);
		if (updateinfo != null)
		{
			for (int i = 0; i < updateinfo.size(); i++)
			{
				String[] st = (String[]) updateinfo.get(i);
				int r = 0;
				if (st != null && st.length == 2)
				{
					try
					{
						r = VideoDAO.getInstance().updateProductFeeDesc(st[0],
								st[1]);
					}
					catch (DAOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				if (r == 0)
				{
					list.add(st[0]);
				}
			}
		}

		return list;
	}

	/**
	 * ����EXECL�ļ�����ȡҪ��ӵ���Ʒ��Ϣ
	 * 
	 * @param in
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public List paraseDataFile(FormFile dataFile) throws BOException

	{
		logger.info("VideoBO.paraseDataFile() is start!");
		List list = new ArrayList();
		Workbook book = null;
		try
		{
			book = Workbook.getWorkbook(dataFile.getInputStream());
			Sheet[] sheets = book.getSheets();
			int sheetNum = sheets.length;
			if (logger.isDebugEnabled())
			{
				logger.debug("paraseSoftVersion.sheetNum==" + sheetNum);
			}

			for (int i = 0; i < sheetNum; i++)
			{
				int rows = sheets[i].getRows();
				// int columns = sheets[i].getColumns();
				// Ϊ�����Ч�ʣ�֧ȡǰ���е�ֵ
				// int columns = 2;  del by wml 12.9.10
				for (int j = 0; j < rows; j++)
				{
					// for (int k = 0; k < columns; k++)
					// {
					String productId = sheets[i].getCell(0, j).getContents()
							.trim();
					String feeDesc = sheets[i].getCell(1, j).getContents()
							.trim();
					// switch (k + 1)
					// {
					// case 1:
					// //��һ��
					// break;
					// case 2:
					// //�ڶ���

					// break;
					// }
					if (productId != null && !productId.trim().equals(""))
					{ // &&
						// feeDesc != null &&! feeDesc.trim().equals("") ){
						// �ǿգ����ܽ���,ֵΪ�գ���ɾ�������
						String[] ps =
						{ productId, feeDesc };
						list.add(ps);
					}
				}
			}

		}
		catch (Exception e)
		{
			logger.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
			throw new BOException("���������ļ������쳣", e);
		}

		finally
		{
			book.close();
		}
		return list;
	}

	/**
	 * �������ơ��ṩ�̵��ֶβ�ѯӦ�û��
	 * 
	 * @param page
	 * @param contentID
	 *            ����ID
	 * @throws BOException
	 */
	public void queryVideoNodeExtList(PageResult page, String nodeId,
			String name) throws BOException
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("queryVideoNodeExtList(nodeId=" + nodeId + " name="
					+ name + ")");
		}
		if (page == null)
		{
			throw new BOException("invalid para , page is null");
		}
		try
		{
			VideoDAO.getInstance().queryVideoNodeExtList(page, nodeId, name);
		}
		catch (DAOException e)
		{
			logger.error("queryVideoNodeExtList...", e);
			throw new BOException("queryVideoNodeExtList", e);
		}
	}

	public List updateVideoNodeExts(List updateinfo) throws BOException
	{
		List list = new ArrayList();
		// List updateinfo = paraseDataFile(dataFile);
		if (updateinfo != null)
		{
			for (int i = 0; i < updateinfo.size(); i++)
			{
				String[] st = (String[]) updateinfo.get(i);
				int r = 0;
				if (st != null && st.length == 2)
				{
					try
					{
						r = VideoDAO.getInstance().updateVideoNodeExt(st[0],
								st[1]);
					}
					catch (DAOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				if (r == 0)
				{
					list.add(st[0]);
				}
			}
		}

		return list;
	}
	
    /**
     * ���ڷ�����Ƶ������չ�ֶ��б�
     * 
     * @param tid
     * @return
     * @throws BOException
     */
    public List queryVideoCategoryKeyBaseList(String tid) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoCategoryKeyBaseList( ) is start...");
        }

        try
        {
            // ����VideoDAO���в�ѯ
            return VideoDAO.getInstance()
                                      .queryVideoCategoryKeyBaseList(tid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("������Ƶ������չ�ֶ��б�ʱ�������ݿ��쳣��",e);
        }
    }
    
    
    /**
	 * �ύ����
	 * 
	 * @param categoryId ���ܱ��
	 * @throws BOException
	 */
	public void approvalCategory(HttpServletRequest request,String categoryId) throws BOException {
		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}
		// �����������
		TransactionDB tdb = null;
        try
        {
        	tdb = TransactionDB.getTransactionInstance();
            CategoryDAO.getInstance().approvalCategory(tdb,categoryId);
            CategoryDAO.getInstance().approvalCategory(tdb, categoryId, "2","1", logUser.getName());
            Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "��������ͻ����Ż���Ӫ���ݵ�������");
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;����������Ƶ���ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����;<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;����������Ƶ���ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 2) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
			map.put("mailContent", value);
			map.put("status", "0");
			map.put("categoryId", categoryId);
			map.put("operation", "60");
			map.put("operationtype", "��Ƶ���ܹ����ύ����");
			map.put("operationobj", "��Ƶ���ܹ���");
			map.put("operationobjtype", "����Id��" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
			DaemonTaskRunner.getInstance().addTask(task);
			tdb.commit();
        }
        catch (DAOException e)
        {
        	tdb.rollback();
            logger.error(e);
            throw new BOException("����ָ������ʱ�������ݿ��쳣��");
        }finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	
	/**
	 * ���ݻ���Id������Ƶ����ɾ��״̬
	 * 
	 * @param categoryId
	 * @throws BOException
	 */
	public void updateVideoCategoryDelStatus(String videoCategoryId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.updateVideoCategoryDelStatus(" + videoCategoryId
					+ ") is start...");
		}

		try
		{
			VideoDAO.getInstance().updateVideoCategoryDelStatus(videoCategoryId);
		}
		catch (DAOException e)
		{
			logger.error("���ݻ���Id������Ƶ����ɾ��״̬ʱ�����쳣:",e);
			throw new BOException("���ݻ���Id������Ƶ����ɾ��״̬ʱ�����쳣:",e);
		}
	}
}
