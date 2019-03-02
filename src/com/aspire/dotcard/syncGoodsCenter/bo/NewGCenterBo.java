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
	 * 日志引用
	 */
	JLogger LOG = LoggerFactory.getLogger(NewGCenterBo.class);
	private static NewGCenterBo bo = new NewGCenterBo();

	private static JLogger DCMP = LoggerFactory.getLogger("FTP-DCMP-Log");

	private NewGCenterBo() {
	}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static NewGCenterBo getInstance() {

		return bo;
	}

	public void handleAPPInfo() throws BOException {

		LOG.info("syncAndroid:handleAPPInfo(每5分钟开始检查t_a_ppms_receive_change状态为-1的记录,而且entityid是33开头的)开始...");

		long start = System.currentTimeMillis();
		int receiveChangeLimit = 1000;// 默认不开放
		int threadLimit = 2;// 默认为2;
		try {
			ModuleConfig module = ConfigFactory.getSystemConfig()
					.getModuleConfig("syncAndroid");
			receiveChangeLimit = Integer.parseInt(module
					.getItemValue("ReceiveChangeLimit"));
			if (receiveChangeLimit > 5000) {// 太大可能不好。故程序中限定一下。
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
			if (threadLimit > 10) {// 太大可能不好。故程序中限定一下。
				threadLimit = 10;
			}
			LOG.info("ThreadLimit is:" + threadLimit);
		} catch (Exception e) {
			LOG.error("threadLimit is bad...");
		}
		List<ReceiveChangeVO> receiveChangeList = null;
		try {
			// 非聚合应用
			receiveChangeList = APPInfoDAO.getInstance().getReceiveChangeList(
					Constant.MESSAGE_HANDLE_STATUS_INIT, receiveChangeLimit,
					Constant.MESSAGE_CONTENT_TYPE_33);
		} catch (DAOException e) {
			// throw new BOException("获取receiveChangeList出错", e);
			LOG.error("获取receiveChangeList出错," + e.getMessage());
		}

		// update by fanqh 20131210
		if (receiveChangeList == null
				|| (receiveChangeList != null && receiveChangeList.size() < 1)) {
			LOG.error("获取receiveChangeList数据为空!");
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("开始receiveChangeList数据处理,需要处理的数据大小："
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
			// 构造异步任务
			ReflectedTask task = new ReflectedTask(hgcb,
					"handleGoodsCenterContent", null, null);
			// 将任务加到运行器中
			dataSynTaskRunner.addTask(task);
		}
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("waitToFinished前,让线程睡眠500毫秒！");
			}
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// e1.printStackTrace();
			LOG.error("线程睡眠500毫秒，报错！");
		}
		dataSynTaskRunner.waitToFinished();
		dataSynTaskRunner.stop();

	}

	/**
	 * 已appid为维度处理消息
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
				// 处理按照sql规则查出来的第一条
				// if (i == 0) {
				// //如果opt为1, 则走
				// if ("1".equals(vos.get(i).getOpt())) {
				// try {
				// // HandleContentWithAppid.grounding(vos.get(i));
				// // 1,应用的上线
				// // 2,应用的上架 找到所有appid对应的商品,将这个新的contentid上架上去
				// // 3,发送消息到数据中心 都集合到HandleGoodsCenterBo中
				// // HandleContent.grounding(vos.get(i));
				// // 如果排第一的是33应用,则走聚合33应用的上下架流程
				// if (vos.get(i).getEntityid().startsWith("33")) {
				// HandleGoodsCenterBo cm = new HandleGoodsCenterBo(
				// vos.get(i));
				// // 构造异步任务
				// cm.handleGoodsCenterContent();
				// } else {
				// // 否则走聚合30应用的上下架流程
				// HandleContent cm = new HandleContent(vos.get(i));
				// cm.handleContent();
				// }
				//
				// } catch (Exception e) {
				// LOG.debug(vos.get(0).getAppid() + "上架失败.....", e);
				// }
				// } else {
				//
				// try {
				// // 如果排第一的是33应用,则走聚合33应用的上下架流程
				// if (vos.get(i).getEntityid().startsWith("33")) {
				// HandleGoodsCenterBo cm = new HandleGoodsCenterBo(
				// vos.get(i));
				// cm.handleGoodsCenterContent();
				// } else {
				// // 否则走聚合30应用的上下架流程
				// HandleContent cm = new HandleContent(vos.get(i));
				// cm.handleContent();
				// }
				// } catch (Exception e) {
				// LOG.debug(vos.get(0).getAppid() + "下架失败.....", e);
				// }
				//
				// }
				// } else
				// {

				// 将任务加到运行器中

				ReflectedTask task = null;

				try {
					if (vos.get(i).getEntityid().startsWith("33")) {
						HandleGoodsCenterBo cm = new HandleGoodsCenterBo(
								vos.get(i));
						// cm.handleGoodsCenterContent();

						// 构造异步任务
						task = new ReflectedTask(cm,
								"handleGoodsCenterContent", null, null);
					} else {
						// 构造异步任务

						// 否则走聚合30应用的上下架流程
						HandleContent cm = new HandleContent(vos.get(i));
						task = new ReflectedTask(cm, "handleContent", null,
								null);
						// cm.handleContent();
					}

					dataSynTaskRunner.addTask(task);
				} catch (Exception e) {
					LOG.debug("批量查询其他的处理失败!", e);
					new PPMSDAO().updateReceiveChangeNoTran(vos.get(i).getId(),
							"-2", "-2");
					// }
				}

			}
			long end = System.currentTimeMillis();
			DCMP.debug("appid为:" + appid + "===耗时:" + (end - start));
		}
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("waitToFinished前,让线程睡眠500毫秒！");
			}
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// e1.printStackTrace();
			LOG.error("线程睡眠500毫秒，报错！");
		}
		dataSynTaskRunner.waitToFinished();
		dataSynTaskRunner.stop();

	}

}
