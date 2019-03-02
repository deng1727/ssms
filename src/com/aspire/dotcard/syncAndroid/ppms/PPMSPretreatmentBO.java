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
	 * ����ģʽ
	 * 
	 * @return
	 */
	public static PPMSPretreatmentBO getInstance() {

		return bo;
	}

	public void handlePretreatment() throws BOException {
		LOG
				.info("syncAndroid:handlePretreatment(ÿ5���ӿ�ʼ���T_A_PPMS_RECEIVE_MESSAGE״̬Ϊ-1�ļ�¼)��ʼ...");
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
									.info("appid��T_A_PPMS_PRETREATMENT_MESSAGE���ﲻ���ڲ���һ��״̬Ϊ-1�ļ�¼");

							// ��T_A_PPMS_PRETREATMENT_MESSAGE�����һ����¼��״̬Ϊ-1
							// ������Ϣ��״̬Ϊ�Ѵ���
							// ����1.����ʱ������Ϣ����ʱ��
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
//								// appid��T_A_PPMS_PRETREATMENT_MESSAGE��������жϺ���Ϣ��Ĵ���ʱ��
//								// ����1T_A_PPMS_PRETREATMENT_MESSAGE�����ж�����¼��ô�죬���ݴ���ʱ�併��ȡ��һ��
//
//								Date messageDate = DateUtil.stringToDate(vo
//										.getChangeTime(), "yyyyMMddHHmmss");
//								Date changeDate = DateUtil.stringToDate(pvos
//										.get(0).getChangetime(),
//										"yyyyMMddHHmmss");
//								if (messageDate.getTime() <= changeDate
//										.getTime()) {
//									// ��Ϣ���״̬����Ϊ�Ѵ���
//									dao.updatePretreatment(null, 0, vo.getId());
//								}
//								if (messageDate.getTime() > changeDate
//										.getTime()) {
//									// �ж���0�Ѵ������1������
//									if (0 == pvos.get(0).getStatus()
//											|| 1 == pvos.get(0).getStatus()) {
//										// ��T_A_PPMS_PRETREATMENT_MESSAGE�����һ����¼��״̬Ϊ-1
//										PretreatmentVO pvo = new PretreatmentVO();
//
//										pvo.setAppid(vo.getAppId());
//										pvo.setChangetime(vo.getChangeTime());
//										pvo.setStatus(-1);
//										dao.updatePretreatment(pvo, 0, vo
//												.getId());
//									}
//									if (-1 == pvos.get(0).getStatus()) {
//										// ��Ϣ���״̬����Ϊ�Ѵ���
//										dao.updatePretreatment(null, 0, vo
//												.getId());
//									}
//								}
//							}
						}
						// ����appid��ѯc��
						// select * from (select c.*,row_number() over(partition
						// by
						// c.appid,c.contentid order by c.createdate desc)
						// rn from T_UNIFIED_PKG_PRIORITY_OUTPUT_INCR c) where
						// rn =
						// 1 and appid=?
						// if(c���¼��Ϊ��){
						// for(c���¼){
						// ��¼�嵽t_a_ppms_receive_change������ʱ��������c��
						// //������뵽t_a_ppms_receive_change����߼�
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
				.info("syncAndroid:handleChange(ÿ5���ӿ�ʼ���T_A_PPMS_PRETREATMENT_MESSAGE״̬Ϊ-1�ļ�¼)��ʼ...");
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
			PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
			List<PretreatmentVO> pList = dao.getPretreatmentByStatus();
			if (!pList.isEmpty()) {
				for (PretreatmentVO vo : pList) {
					try {

						// ����appid��ѯc��
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
		

		// appid��T_A_PPMS_PRETREATMENT_MESSAGE��������жϺ���Ϣ��Ĵ���ʱ��
		// ����1T_A_PPMS_PRETREATMENT_MESSAGE�����ж�����¼��ô�죬���ݴ���ʱ�併��ȡ��һ��
		
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
			// ��Ϣ���״̬����Ϊ�Ѵ���
			dao.updatePretreatment(null, 0, vo.getId());
		}
		if (messageDate.getTime() > changeDate
				.getTime()) {
			// �ж���0�Ѵ������1������
			if (0 == pvos.getStatus()
					|| 1 == pvos.getStatus()) {
				// ��T_A_PPMS_PRETREATMENT_MESSAGE�����һ����¼��״̬Ϊ-1
				PretreatmentVO pvo = new PretreatmentVO();

				pvo.setAppid(vo.getAppId());
				pvo.setChangetime(vo.getChangeTime());
				pvo.setStatus(-1);
				dao.updatePretreatment(pvo, 0, vo
						.getId());
			}
			if (-1 == pvos.getStatus()) {
				// ��Ϣ���״̬����Ϊ�Ѵ���
				dao.updatePretreatment(null, 0, vo
						.getId());
			}
		}
	
	}
}
