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
	 * 查询列表
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
			throw new BOException("查询黑名单下内容异常", e);
		}
	}
	
	/**
	 * 查询列表
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
			throw new BOException("查询黑名单下内容异常", e);
		}
	}

	/**
	 * 查询不在黑名单中的内容列表
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
			throw new BOException("查询黑名单下内容异常", e);
		}
	}
	
	

	/**
	 * 新增黑名单
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void addBlack(BlackListVO vo) throws Exception {

		BlackListVO tmp = BlackListDao.getInstance().getBlackByContentId(
				vo.getContentId());

		if (null != tmp && vo.getContentId().equals(tmp.getContentId())) {
			// 已存在
			throw new BOException("黑名单已经存在",
					RightManagerConstant.ROLE_NAME_EXIST);
		}
		tmp = BlackListDao.getInstance().getContentFromTGContentByContentId(
				vo.getContentId());
		if (null == tmp || null == tmp.getContentId()
				|| "".equals(tmp.getContentId())) {
			throw new BOException("内容" + vo.getContentId() + "不存在");
		}
        
		// 根据在内容表中的subtype类形如果是6
        if(Constants.BLACK_LIST_CONTENT_SUBTYPE_CY.equals(tmp.getContentType()))
        {
            // 创业大赛
            vo.setContentType(Constants.BLACK_LIST_CY);
        }
        else
        {
            // MM应用
            vo.setContentType(Constants.BLACK_LIST_MM);
        }
        
		BlackListDao.getInstance().add(vo);

	}

	/**
	 * 批量新增黑名单
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
				// 已存在
				throw new BOException("黑名单已经存在",
						RightManagerConstant.ROLE_NAME_EXIST);
			}
			tmp = BlackListDao.getInstance()
					.getContentFromTGContentByContentId(vo.getContentId());
			if (null == tmp || null == tmp.getContentId()
					|| "".equals(tmp.getContentId())) {
				throw new BOException("内容" + vo.getContentId() + "不存在");
			}
            
            // 根据在内容表中的subtype类形如果是6
            if(Constants.BLACK_LIST_CONTENT_SUBTYPE_CY.equals(tmp.getContentType()))
            {
                // 创业大赛
                vo.setContentType(Constants.BLACK_LIST_CY);
            }
            else
            {
                // MM应用
                vo.setContentType(Constants.BLACK_LIST_MM);
            }
            
			BlackListDao.getInstance().add(vo);
		}
	}

	/**
	 * 更新
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void updateBlack(BlackListVO vo) throws BOException {
		try {
			BlackListDao.getInstance().update(vo);

		} catch (Exception e) {
			throw new BOException("修改黑名单下内容异常", e);
		}
	}

	/**
	 * 删除
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void deleteBlack(BlackListVO vo) throws BOException {
		try {
			BlackListDao.getInstance().delete(vo);

		} catch (Exception e) {
			throw new BOException("删除黑名单下内容异常", e);
		}
	}
	/**
	 * 删除黑名单，把黑名单审批状态置为编辑状态
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void deleteBlackItem(BlackListVO vo) throws BOException {
		try {
			BlackListDao.getInstance().deleteItem(vo);

		} catch (Exception e) {
			throw new BOException("删除黑名单下内容异常", e);
		}
	}

	/**
	 * 导入黑名单文件
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
				log.error("第" + line + "行数据格式不正确");
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
                    
				    // 根据在内容表中的subtype类形如果是6
                    if(Constants.BLACK_LIST_CONTENT_SUBTYPE_CY.equals(tb.getContentType()))
                    {
                        // 创业大赛
                        black.setContentType(Constants.BLACK_LIST_CY);
                    }
                    else
                    {
                        // MM应用
                        black.setContentType(Constants.BLACK_LIST_MM);
                    }
                    
				    BlackListDao.getInstance().importUpBlack(black);
				    successCount++;
				}else{
					log.error("第" + line + "行"+ black.getContentId()+" 内容不存在导入失败!");
					err.add(black.getContentId());
				}
			} catch (Exception e) {
				log.error("第" + line + "行"+ black.getContentId()+"导入失败!");
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
	 * 内容黑名单提交审批
	 * 
	 * @param request
	 * @param dealContent 应用内容id
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

		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			for (int i = 0; i < dealContent.length; i++) {
				BlackListDao.getInstance().submitApproval(tdb, dealContent[i]);
				BlackListDao.getInstance().approvalBlackList(tdb, dealContent[i], "2", logUser.getName());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mailTitle", "待审事项：客户端门户运营内容调整审批");
				String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：内容黑名单;<br>"
						+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式："
						+ "修改;<br>&nbsp;&nbsp;&nbsp;&nbsp;数据变动：变更内容" + dealContent[i];
				map.put("mailContent", value);
				map.put("status", "0");
				map.put("contentId", dealContent[i]);
				map.put("operation", "20");
				map.put("operationtype", "内容黑名单提交审批");
				map.put("operationobj", "内容黑名单");
				map.put("operationobjtype", "内容Id：" + dealContent[i]);
				map.put("operator", logUser.getName());
				WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
				DaemonTaskRunner.getInstance().addTask(task);
			}
			tdb.commit();
		} catch (BOException e) {
			// 执行回滚
			tdb.rollback();
			throw new BOException("更新黑名单发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	/**
	 * 内容黑名单审批通过
	 * 
	 * @param request
	 * @param dealContent 应用内容id
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

		// 进行事务操作
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
					map.put("mailTitle", "数据已发布：客户端门户运营内容已发布");
					String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：内容黑名单;<br>"
							+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;数据变动：变更内容Id" + dealContent[i];
					map.put("mailContent", value);
					map.put("status", "1");
					map.put("contentId", dealContent[i]);
					map.put("operation", "20");
					map.put("operationtype", "内容黑名单审批发布");
					map.put("operationobj", "内容黑名单");
					map.put("operationobjtype", "内容Id：" + dealContent[i]);
					map.put("operator", logUser.getName());
					WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
			tdb.commit();
		} catch (BOException e) {
			// 执行回滚
			tdb.rollback();
			throw new BOException("更新货架商品表发生异常:", e);
		} catch (Exception e) {
			// 执行回滚
			tdb.rollback();
			throw new BOException("更新货架商品表发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	/**
	 * 内容黑名单审批不通过
	 * 
	 * @param request
	 * @param dealContent 应用内容id
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

		// 进行事务操作
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
					map.put("mailTitle", "审核驳回：客户端门户运营内容审批驳回");
					String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：内容黑名单;<br>"
							+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;数据变动：变更内容Id" + dealContent[i];
					map.put("mailContent", value);
					map.put("status", "2");
					map.put("contentId", dealContent[i]);
					map.put("operation", "20");
					map.put("operationtype", "内容黑名单审批不通过");
					map.put("operationobj", "内容黑名单");
					map.put("operationobjtype", "内容Id：" + dealContent[i]);
					map.put("operator", logUser.getName());
					WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
			tdb.commit();
		} catch (BOException e) {
			// 执行回滚
			tdb.rollback();
			throw new BOException("更新货架商品表发生异常:", e);
		} catch (Exception e) {
			// 执行回滚
			tdb.rollback();
			throw new BOException("更新货架商品表发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
}
