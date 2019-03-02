package com.aspire.ponaadmin.web.blacklist.biz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.blacklist.WriteActionLogAndEmailTask;
import com.aspire.ponaadmin.web.blacklist.dao.BlackListDao;
import com.aspire.ponaadmin.web.blacklist.vo.BlackListVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;

public class BlackListBo {

	private static final JLogger log = LoggerFactory
			.getLogger(BlackListBo.class);

	private static BlackListBo bb = new BlackListBo();

	public static BlackListBo getInstance() {
		return bb;
	}

	/**
	 * ��ѯ�б�
	 * 
	 * @param page
	 * @param searchor
	 * @param taxis
	 * @throws BOException
	 */
	public void queryBlackList(PageResult page, Searchor searchor, Taxis taxis,String aprovalStatus)
			throws BOException {
		try {
			BlackListDao.getInstance().queryBlackList(page, searchor,aprovalStatus);

		} catch (Exception e) {
			throw new BOException("��ѯ�������������쳣", e);
		}
	}
	
	/**
	 * ��ѯ�б�
	 * 
	 * @param page
	 * @param searchor
	 * @param taxis
	 * @throws BOException
	 */
	public void queryBlackListOperation(PageResult page, Searchor searchor,String aprovalStatus)
			throws BOException {
		try {
			BlackListDao.getInstance().queryBlackListOperation(page, searchor,aprovalStatus);

		} catch (Exception e) {
			throw new BOException("��ѯ�������������쳣", e);
		}
	}

	/**
	 * ��ѯ���ں������е������б�
	 * 
	 * @param page
	 * @param searchor
	 * @param taxis
	 * @throws BOException
	 */
	public void queryContentNoInBlackList(PageResult page, Searchor searchor,
			Taxis taxis) throws BOException {
		try {
			BlackListDao.getInstance()
					.queryContentNoInBlackList(page, searchor);

		} catch (Exception e) {
			throw new BOException("��ѯ�������������쳣", e);
		}
	}
	
	

	/**
	 * ����������
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void addBlack(BlackListVO vo) throws Exception {

		BlackListVO tmp = BlackListDao.getInstance().getBlackByContentId(
				vo.getContentId());

		if (null != tmp && vo.getContentId().equals(tmp.getContentId())) {
			// �Ѵ���
			throw new BOException("�������Ѿ�����",
					RightManagerConstant.ROLE_NAME_EXIST);
		}
		tmp = BlackListDao.getInstance().getContentFromTGContentByContentId(
				vo.getContentId());
		if (null == tmp || null == tmp.getContentId()
				|| "".equals(tmp.getContentId())) {
			throw new BOException("����" + vo.getContentId() + "������");
		}
        
		// ���������ݱ��е�subtype���������6
        if(Constants.BLACK_LIST_CONTENT_SUBTYPE_CY.equals(tmp.getContentType()))
        {
            // ��ҵ����
            vo.setContentType(Constants.BLACK_LIST_CY);
        }
        else
        {
            // MMӦ��
            vo.setContentType(Constants.BLACK_LIST_MM);
        }
        
		BlackListDao.getInstance().add(vo);

	}

	/**
	 * ��������������
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void addBatchBlack(List list) throws Exception {
		BlackListVO vo = null;
		for (int i = 0; i < list.size(); i++) {
			vo = (BlackListVO) list.get(i);
			BlackListVO tmp = BlackListDao.getInstance().getBlackByContentId(
					vo.getContentId());

			if (null != tmp && vo.getContentId().equals(tmp.getContentId())) {
				// �Ѵ���
				throw new BOException("�������Ѿ�����",
						RightManagerConstant.ROLE_NAME_EXIST);
			}
			tmp = BlackListDao.getInstance()
					.getContentFromTGContentByContentId(vo.getContentId());
			if (null == tmp || null == tmp.getContentId()
					|| "".equals(tmp.getContentId())) {
				throw new BOException("����" + vo.getContentId() + "������");
			}
            
            // ���������ݱ��е�subtype���������6
            if(Constants.BLACK_LIST_CONTENT_SUBTYPE_CY.equals(tmp.getContentType()))
            {
                // ��ҵ����
                vo.setContentType(Constants.BLACK_LIST_CY);
            }
            else
            {
                // MMӦ��
                vo.setContentType(Constants.BLACK_LIST_MM);
            }
            
			BlackListDao.getInstance().add(vo);
		}
	}

	/**
	 * ����
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void updateBlack(BlackListVO vo) throws BOException {
		try {
			BlackListDao.getInstance().update(vo);

		} catch (Exception e) {
			throw new BOException("�޸ĺ������������쳣", e);
		}
	}

	/**
	 * ɾ��
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void deleteBlack(BlackListVO vo) throws BOException {
		try {
			BlackListDao.getInstance().delete(vo);

		} catch (Exception e) {
			throw new BOException("ɾ���������������쳣", e);
		}
	}
	/**
	 * ɾ�����������Ѻ���������״̬��Ϊ�༭״̬
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void deleteBlackItem(BlackListVO vo) throws BOException {
		try {
			BlackListDao.getInstance().deleteItem(vo);

		} catch (Exception e) {
			throw new BOException("ɾ���������������쳣", e);
		}
	}

	/**
	 * ����������ļ�
	 * 
	 * @param dataFile
	 * @return
	 * @throws Exception
	 */
	public int importBlack(FormFile dataFile,List err) throws Exception {
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(dataFile
				.getInputStream()));
		String tmp = null;
		BlackListVO black = null;
		BlackListVO tb = null;
		String[] str = new String[4];
		int line = 0;
		int successCount = 0;
		Pattern p = Pattern.compile("[0-9]{29}");
		Matcher m = null;
		
		while ((tmp = bf.readLine()) != null) {
			line++;
			m = p.matcher(tmp);
			if(tmp.length()!=29||!m.matches()){
				log.error("��" + line + "�����ݸ�ʽ����ȷ");
				continue;
			}
			str[0] = tmp.substring(0, 8);
			str[1] = tmp.substring(8,16);
			str[2] = tmp.substring(16, 28);
			str[3] = tmp.substring(28);
			
			black = new BlackListVO();
			black.setContentId(str[2]);
			black.setStartDate(str[0]);
			black.setEndDate(str[1]);
			black.setType(Integer.parseInt(str[3]));
			
			try {
				tb = BlackListDao.getInstance().getContentFromTGContentByContentId(black.getContentId());
				if(null!=tb&&null!=tb.getContentId()){
                    
				    // ���������ݱ��е�subtype���������6
                    if(Constants.BLACK_LIST_CONTENT_SUBTYPE_CY.equals(tb.getContentType()))
                    {
                        // ��ҵ����
                        black.setContentType(Constants.BLACK_LIST_CY);
                    }
                    else
                    {
                        // MMӦ��
                        black.setContentType(Constants.BLACK_LIST_MM);
                    }
                    
				    BlackListDao.getInstance().importUpBlack(black);
				    successCount++;
				}else{
					log.error("��" + line + "��"+ black.getContentId()+" ���ݲ����ڵ���ʧ��!");
					err.add(black.getContentId());
				}
			} catch (Exception e) {
				log.error("��" + line + "��"+ black.getContentId()+"����ʧ��!");
				err.add(black.getContentId());
			}
			
			//list.add(black);
		}
		black = null;
		//if (list.size() > 0)
			
		return successCount;
	}
	/**
	 * 
	 * ���ݺ������ύ����
	 * 
	 * @param request
	 * @param dealContent Ӧ������id
	 * 
	 * @throws BOException
	 */
	public void submitAproval(HttpServletRequest request, String[] dealContent)
			throws BOException {
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
		try {
			tdb = TransactionDB.getInstance();
			for (int i = 0; i < dealContent.length; i++) {
				BlackListDao.getInstance().submitApproval(tdb, dealContent[i]);
				BlackListDao.getInstance().approvalBlackList(tdb, dealContent[i], "2", logUser.getName());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mailTitle", "��������ͻ����Ż���Ӫ���ݵ�������");
				String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�����������ݺ�����;<br>"
						+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��"
						+ "�޸�;<br>&nbsp;&nbsp;&nbsp;&nbsp;���ݱ䶯���������" + dealContent[i];
				map.put("mailContent", value);
				map.put("status", "0");
				map.put("contentId", dealContent[i]);
				map.put("operation", "20");
				map.put("operationtype", "���ݺ������ύ����");
				map.put("operationobj", "���ݺ�����");
				map.put("operationobjtype", "����Id��" + dealContent[i]);
				map.put("operator", logUser.getName());
				WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
				DaemonTaskRunner.getInstance().addTask(task);
			}
			tdb.commit();
		} catch (BOException e) {
			// ִ�лع�
			tdb.rollback();
			throw new BOException("���º����������쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	/**
	 * ���ݺ���������ͨ��
	 * 
	 * @param request
	 * @param dealContent Ӧ������id
	 * @throws BOException
	 */
	public void approval(HttpServletRequest request, String[] dealContent)
			throws BOException {
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
		try {
			tdb = TransactionDB.getInstance();
			for (int i = 0; i < dealContent.length; i++) {
				Map<String,Object> itemMap = BlackListDao.getInstance().queryBlackListItem(dealContent[i]);
				if(itemMap != null){
					if(!"2".equals(itemMap.get("delpro_status"))){
						BlackListVO vo = new BlackListVO();
						vo.setContentId(dealContent[i]);
						BlackListDao.getInstance().delete(vo);
					}else{
						BlackListDao.getInstance().approval(tdb,dealContent[i], "1","2");
					}
					BlackListDao.getInstance().approvalBlackList(tdb, dealContent[i], "1", logUser.getName());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("mailTitle", "�����ѷ������ͻ����Ż���Ӫ�����ѷ���");
					String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�����������ݺ�����;<br>"
							+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;���ݱ䶯���������Id" + dealContent[i];
					map.put("mailContent", value);
					map.put("status", "1");
					map.put("contentId", dealContent[i]);
					map.put("operation", "20");
					map.put("operationtype", "���ݺ�������������");
					map.put("operationobj", "���ݺ�����");
					map.put("operationobjtype", "����Id��" + dealContent[i]);
					map.put("operator", logUser.getName());
					WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
			tdb.commit();
		} catch (BOException e) {
			// ִ�лع�
			tdb.rollback();
			throw new BOException("���»�����Ʒ�����쳣:", e);
		} catch (Exception e) {
			// ִ�лع�
			tdb.rollback();
			throw new BOException("���»�����Ʒ�����쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	/**
	 * ���ݺ�����������ͨ��
	 * 
	 * @param request
	 * @param dealContent Ӧ������id
	 * @throws BOException
	 */
	public void refuse(HttpServletRequest request, String[] dealContent)
			throws BOException {
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
		try {
			tdb = TransactionDB.getInstance();
			for (int i = 0; i < dealContent.length; i++) {
				Map<String,Object> itemMap = BlackListDao.getInstance().queryBlackListItem(dealContent[i]);
				if(itemMap != null){
					if(!"2".equals(itemMap.get("delpro_status"))){
						BlackListDao.getInstance().approval(tdb,dealContent[i], itemMap.get("delpro_status").toString(),"2");
					}else{
						BlackListDao.getInstance().approval(tdb,dealContent[i], "3","2");
					}
					BlackListDao.getInstance().approvalBlackList(tdb, dealContent[i], "1", logUser.getName());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("mailTitle", "��˲��أ��ͻ����Ż���Ӫ������������");
					String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�����������ݺ�����;<br>"
							+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;���ݱ䶯���������Id" + dealContent[i];
					map.put("mailContent", value);
					map.put("status", "2");
					map.put("contentId", dealContent[i]);
					map.put("operation", "20");
					map.put("operationtype", "���ݺ�����������ͨ��");
					map.put("operationobj", "���ݺ�����");
					map.put("operationobjtype", "����Id��" + dealContent[i]);
					map.put("operator", logUser.getName());
					WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
			tdb.commit();
		} catch (BOException e) {
			// ִ�лع�
			tdb.rollback();
			throw new BOException("���»�����Ʒ�����쳣:", e);
		} catch (Exception e) {
			// ִ�лع�
			tdb.rollback();
			throw new BOException("���»�����Ʒ�����쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
}
