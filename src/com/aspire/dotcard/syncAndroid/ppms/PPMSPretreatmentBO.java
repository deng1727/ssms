package com.aspire.dotcard.syncAndroid.ppms;

import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.unifiedPackage.vo.GoodsCenterMessagesVo;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.util.DateUtil;

public class PPMSPretreatmentBO {
	JLogger LOG = LoggerFactory.getLogger(PPMSPretreatmentBO.class);
	private static PPMSPretreatmentBO bo = new PPMSPretreatmentBO();

	private PPMSPretreatmentBO() {
	}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static PPMSPretreatmentBO getInstance() {

		return bo;
	}

	public void handlePretreatment() throws BOException {
		LOG
				.info("syncAndroid:handlePretreatment(每5分钟开始检查T_A_PPMS_RECEIVE_MESSAGE状态为-1的记录)开始...");
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
			PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
			List<GoodsCenterMessagesVo> messagesList = dao.getMessageByState();
			if (!messagesList.isEmpty()) {
				for (GoodsCenterMessagesVo vo : messagesList) {
					try {
						List<PretreatmentVO> pvos = dao
								.getPretreatmentByStatusAppid(vo.getAppId());   //status -1, appid
						if (pvos.isEmpty()) {
							LOG
									.info("appid在T_A_PPMS_PRETREATMENT_MESSAGE表里不存在插入一条状态为-1的记录");

							// 在T_A_PPMS_PRETREATMENT_MESSAGE里插入一条记录，状态为-1
							// 更新消息表状态为已处理
							// 问题1.处理时间是消息表处理时间
							PretreatmentVO preVo  = dao.getPretreatmentByAppid(vo.getAppId());
							compareVO(vo,preVo,dao);
							tdb.commit();
						} else {
							dao.updatePretreatment(null, 0, vo.getId());
							tdb.commit();
//							List<PretreatmentVO> prvos = dao
//									.getPretreatmentByStatusAppid(vo.getAppId());
//							if (prvos.isEmpty()) {
//								dao.updatePretreatment(null, 0, vo.getId());
//							} else {
//								// appid在T_A_PPMS_PRETREATMENT_MESSAGE表里存在判断和消息表的处理时间
//								// 问题1T_A_PPMS_PRETREATMENT_MESSAGE表里有多条记录怎么办，根据处理时间降序取第一条
//
//								Date messageDate = DateUtil.stringToDate(vo
//										.getChangeTime(), "yyyyMMddHHmmss");
//								Date changeDate = DateUtil.stringToDate(pvos
//										.get(0).getChangetime(),
//										"yyyyMMddHHmmss");
//								if (messageDate.getTime() <= changeDate
//										.getTime()) {
//									// 消息表的状态更新为已处理
//									dao.updatePretreatment(null, 0, vo.getId());
//								}
//								if (messageDate.getTime() > changeDate
//										.getTime()) {
//									// 判断是0已处理或者1处理中
//									if (0 == pvos.get(0).getStatus()
//											|| 1 == pvos.get(0).getStatus()) {
//										// 在T_A_PPMS_PRETREATMENT_MESSAGE里插入一条记录，状态为-1
//										PretreatmentVO pvo = new PretreatmentVO();
//
//										pvo.setAppid(vo.getAppId());
//										pvo.setChangetime(vo.getChangeTime());
//										pvo.setStatus(-1);
//										dao.updatePretreatment(pvo, 0, vo
//												.getId());
//									}
//									if (-1 == pvos.get(0).getStatus()) {
//										// 消息表的状态更新为已处理
//										dao.updatePretreatment(null, 0, vo
//												.getId());
//									}
//								}
//							}
						}
						// 根据appid查询c表
						// select * from (select c.*,row_number() over(partition
						// by
						// c.appid,c.contentid order by c.createdate desc)
						// rn from T_UNIFIED_PKG_PRIORITY_OUTPUT_INCR c) where
						// rn =
						// 1 and appid=?
						// if(c表记录不为空){
						// for(c表记录){
						// 记录插到t_a_ppms_receive_change表，创建时间来自于c表
						// //问题插入到t_a_ppms_receive_change表的逻辑
						// }
						// }
					} catch (Exception e) {
						LOG.error("Throwable:PPMSPretreatmentBO:", e);
						continue;
					}

				}
			}
			
		} catch (Exception e) {
			LOG.error("Throwable:PPMSPretreatmentBO:", e);
			tdb.rollback();
		} finally {
			if (null!=tdb) {
				tdb.close();
			}
			
		}

	}

	public void handleChange() throws BOException {
		LOG
				.info("syncAndroid:handleChange(每5分钟开始检查T_A_PPMS_PRETREATMENT_MESSAGE状态为-1的记录)开始...");
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
			PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
			List<PretreatmentVO> pList = dao.getPretreatmentByStatus();
			if (!pList.isEmpty()) {
				for (PretreatmentVO vo : pList) {
					try {

						// 根据appid查询c表
						List<PriorityOutputIncrVO> cList = dao.getCByAppid(vo
								.getAppid());
						dao.updateChange(0, vo.getId(), cList);
						tdb.commit();
					} catch (Exception e) {
						LOG.error("Throwable:PPMSPretreatmentBO:", e);
						continue;
					}

				}
			}
			//tdb.commit();
		} catch (Exception e) {
			LOG.error("Throwable:PPMSPretreatmentBO:", e);
			tdb.rollback();
		} finally {
			if (null != tdb) {
				tdb.close();
			}
		}
	}
	
	public void compareVO(GoodsCenterMessagesVo vo, PretreatmentVO pvos ,PPMSDAO dao ) throws DAOException{
		

		// appid在T_A_PPMS_PRETREATMENT_MESSAGE表里存在判断和消息表的处理时间
		// 问题1T_A_PPMS_PRETREATMENT_MESSAGE表里有多条记录怎么办，根据处理时间降序取第一条
		
		if (null == pvos) {
			PretreatmentVO pvo = new PretreatmentVO();

			pvo.setAppid(vo.getAppId());
			pvo.setChangetime(vo.getChangeTime());
			pvo.setStatus(-1);
			dao.updatePretreatment(pvo, 0, vo
					.getId());
			return ;
		}
		
		
		Date messageDate = DateUtil.stringToDate(vo
				.getChangeTime(), "yyyyMMddHHmmss");
		Date changeDate = DateUtil.stringToDate(pvos
				.getChangetime(),
				"yyyyMMddHHmmss");
		if (messageDate.getTime() <= changeDate
				.getTime()) {
			// 消息表的状态更新为已处理
			dao.updatePretreatment(null, 0, vo.getId());
		}
		if (messageDate.getTime() > changeDate
				.getTime()) {
			// 判断是0已处理或者1处理中
			if (0 == pvos.getStatus()
					|| 1 == pvos.getStatus()) {
				// 在T_A_PPMS_PRETREATMENT_MESSAGE里插入一条记录，状态为-1
				PretreatmentVO pvo = new PretreatmentVO();

				pvo.setAppid(vo.getAppId());
				pvo.setChangetime(vo.getChangeTime());
				pvo.setStatus(-1);
				dao.updatePretreatment(pvo, 0, vo
						.getId());
			}
			if (-1 == pvos.getStatus()) {
				// 消息表的状态更新为已处理
				dao.updatePretreatment(null, 0, vo
						.getId());
			}
		}
	
	}
}
