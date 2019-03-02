package com.aspire.dotcard.syncGoodsCenter.bo;

import java.util.List;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.ppms.APPInfoDAO;
import com.aspire.dotcard.syncAndroid.ppms.HandleContent;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.dotcard.syncAndroid.ppms.ReceiveChangeVO;

public class NewGCenterBo {
	/**
	 * ��־����
	 */
	JLogger LOG = LoggerFactory.getLogger(NewGCenterBo.class);
	private static NewGCenterBo bo = new NewGCenterBo();

	private static JLogger DCMP = LoggerFactory.getLogger("FTP-DCMP-Log");

	private NewGCenterBo() {
	}

	/**
	 * ����ģʽ
	 * 
	 * @return
	 */
	public static NewGCenterBo getInstance() {

		return bo;
	}

	public void handleAPPInfo() throws BOException {

		LOG.info("syncAndroid:handleAPPInfo(ÿ5���ӿ�ʼ���t_a_ppms_receive_change״̬Ϊ-1�ļ�¼,����entityid��33��ͷ��)��ʼ...");

		long start = System.currentTimeMillis();
		int receiveChangeLimit = 1000;// Ĭ�ϲ�����
		int threadLimit = 2;// Ĭ��Ϊ2;
		try {
			ModuleConfig module = ConfigFactory.getSystemConfig()
					.getModuleConfig("syncAndroid");
			receiveChangeLimit = Integer.parseInt(module
					.getItemValue("ReceiveChangeLimit"));
			if (receiveChangeLimit > 5000) {// ̫����ܲ��á��ʳ������޶�һ�¡�
				receiveChangeLimit = 5000;
			}
			LOG.info("ReceiveChangeLimit is:" + receiveChangeLimit);
		} catch (Exception e) {
			LOG.error("ReceiveChangeLimit is bad...");
		}
		try {
			ModuleConfig module = ConfigFactory.getSystemConfig()
					.getModuleConfig("syncAndroid");
			threadLimit = Integer.parseInt(module.getItemValue("ThreadLimit"));
			if (threadLimit > 10) {// ̫����ܲ��á��ʳ������޶�һ�¡�
				threadLimit = 10;
			}
			LOG.info("ThreadLimit is:" + threadLimit);
		} catch (Exception e) {
			LOG.error("threadLimit is bad...");
		}
		List<ReceiveChangeVO> receiveChangeList = null;
		try {
			// �Ǿۺ�Ӧ��
			receiveChangeList = APPInfoDAO.getInstance().getReceiveChangeList(
					Constant.MESSAGE_HANDLE_STATUS_INIT, receiveChangeLimit,
					Constant.MESSAGE_CONTENT_TYPE_33);
		} catch (DAOException e) {
			// throw new BOException("��ȡreceiveChangeList����", e);
			LOG.error("��ȡreceiveChangeList����," + e.getMessage());
		}

		// update by fanqh 20131210
		if (receiveChangeList == null
				|| (receiveChangeList != null && receiveChangeList.size() < 1)) {
			LOG.error("��ȡreceiveChangeList����Ϊ��!");
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("��ʼreceiveChangeList���ݴ���,��Ҫ��������ݴ�С��"
					+ receiveChangeList.size());
		}

		// ExecutorService exec = new ThreadPoolExecutor(threadLimit,
		// threadLimit, 0L,
		// TimeUnit.MILLISECONDS, new
		// LinkedBlockingQueue<Runnable>(receiveChangeLimit),
		// new ThreadPoolExecutor.CallerRunsPolicy());
		TaskRunner dataSynTaskRunner = new TaskRunner(threadLimit, 0);

		for (int i = 0; i < receiveChangeList.size(); i++) {
			final ReceiveChangeVO vo = receiveChangeList.get(i);
			HandleGoodsCenterBo hgcb = new HandleGoodsCenterBo(vo);// modify by
																	// aiyan
																	// 2012-07-24
			// �����첽����
			ReflectedTask task = new ReflectedTask(hgcb,
					"handleGoodsCenterContent", null, null);
			// ������ӵ���������
			dataSynTaskRunner.addTask(task);
		}
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("waitToFinishedǰ,���߳�˯��500���룡");
			}
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// e1.printStackTrace();
			LOG.error("�߳�˯��500���룬����");
		}
		dataSynTaskRunner.waitToFinished();
		dataSynTaskRunner.stop();

	}

	/**
	 * ��appidΪά�ȴ�����Ϣ
	 * 
	 * @throws DAOException
	 */
	public void upOrDownCategory() throws DAOException {
		List<String> appids = APPInfoDAO.getInstance().getAppidsNotNull();
		List<ReceiveChangeVO> vos;

		TaskRunner dataSynTaskRunner = new TaskRunner(10, 0);
		for (String appid : appids) {
			long start = System.currentTimeMillis();
			vos = APPInfoDAO.getInstance().getProHandleVO(appid);
			for (int i = 0; i < vos.size(); i++) {
				// ������sql���������ĵ�һ��
				// if (i == 0) {
				// //���optΪ1, ����
				// if ("1".equals(vos.get(i).getOpt())) {
				// try {
				// // HandleContentWithAppid.grounding(vos.get(i));
				// // 1,Ӧ�õ�����
				// // 2,Ӧ�õ��ϼ� �ҵ�����appid��Ӧ����Ʒ,������µ�contentid�ϼ���ȥ
				// // 3,������Ϣ���������� �����ϵ�HandleGoodsCenterBo��
				// // HandleContent.grounding(vos.get(i));
				// // ����ŵ�һ����33Ӧ��,���߾ۺ�33Ӧ�õ����¼�����
				// if (vos.get(i).getEntityid().startsWith("33")) {
				// HandleGoodsCenterBo cm = new HandleGoodsCenterBo(
				// vos.get(i));
				// // �����첽����
				// cm.handleGoodsCenterContent();
				// } else {
				// // �����߾ۺ�30Ӧ�õ����¼�����
				// HandleContent cm = new HandleContent(vos.get(i));
				// cm.handleContent();
				// }
				//
				// } catch (Exception e) {
				// LOG.debug(vos.get(0).getAppid() + "�ϼ�ʧ��.....", e);
				// }
				// } else {
				//
				// try {
				// // ����ŵ�һ����33Ӧ��,���߾ۺ�33Ӧ�õ����¼�����
				// if (vos.get(i).getEntityid().startsWith("33")) {
				// HandleGoodsCenterBo cm = new HandleGoodsCenterBo(
				// vos.get(i));
				// cm.handleGoodsCenterContent();
				// } else {
				// // �����߾ۺ�30Ӧ�õ����¼�����
				// HandleContent cm = new HandleContent(vos.get(i));
				// cm.handleContent();
				// }
				// } catch (Exception e) {
				// LOG.debug(vos.get(0).getAppid() + "�¼�ʧ��.....", e);
				// }
				//
				// }
				// } else
				// {

				// ������ӵ���������

				ReflectedTask task = null;

				try {
					if (vos.get(i).getEntityid().startsWith("33")) {
						HandleGoodsCenterBo cm = new HandleGoodsCenterBo(
								vos.get(i));
						// cm.handleGoodsCenterContent();

						// �����첽����
						task = new ReflectedTask(cm,
								"handleGoodsCenterContent", null, null);
					} else {
						// �����첽����

						// �����߾ۺ�30Ӧ�õ����¼�����
						HandleContent cm = new HandleContent(vos.get(i));
						task = new ReflectedTask(cm, "handleContent", null,
								null);
						// cm.handleContent();
					}

					dataSynTaskRunner.addTask(task);
				} catch (Exception e) {
					LOG.debug("������ѯ�����Ĵ���ʧ��!", e);
					new PPMSDAO().updateReceiveChangeNoTran(vos.get(i).getId(),
							"-2", "-2");
					// }
				}

			}
			long end = System.currentTimeMillis();
			DCMP.debug("appidΪ:" + appid + "===��ʱ:" + (end - start));
		}
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("waitToFinishedǰ,���߳�˯��500���룡");
			}
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// e1.printStackTrace();
			LOG.error("�߳�˯��500���룬����");
		}
		dataSynTaskRunner.waitToFinished();
		dataSynTaskRunner.stop();

	}

}
