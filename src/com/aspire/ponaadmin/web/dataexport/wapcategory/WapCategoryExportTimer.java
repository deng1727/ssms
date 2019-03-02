package com.aspire.ponaadmin.web.dataexport.wapcategory;

import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.catesyn.CateSynTask;
import com.aspire.ponaadmin.web.catesyn.Config;
import com.aspire.ponaadmin.web.dataexport.appexp.AppsExpTask;
import com.aspire.ponaadmin.web.music139.KeywordSynchroBo;
import com.aspire.ponaadmin.web.music139.Music139Task;
import com.aspire.ponaadmin.web.music139.album.AlbumSynchroBo;
import com.aspire.ponaadmin.web.music139.billboard.BillboardSynchroBo;
import com.aspire.ponaadmin.web.music139.pic.PicSynchroBoImpl;
import com.aspire.ponaadmin.web.music139.tag.MusicTagSynchroBo;

public class WapCategoryExportTimer {
	private static final JLogger log = LoggerFactory
			.getLogger(WapCategoryExportTimer.class);

	public static void start() {
		Timer timer = new Timer(true);
		log.debug("wap信息导出调度器开始启动,现在加入WAP货架导出任务....");
		timer.schedule(new WapCategoryExportTask(), WapCategoryExportTask
				.getConfig().getStartTime(), WapCategoryExportTask.getConfig()
				.getInterval());
		log.debug("加入WAP货架导出任务完成,加入应用信息导出任务....");
		timer.schedule(new AppsExpTask(), AppsExpTask.getConfig()
				.getStartTime(), AppsExpTask.getConfig().getInterval());
		log.debug("wap信息导出调度器任务加载完毕!");
		
//		Music139Task abtask = new Music139Task(new AlbumSynchroBo());
//		timer.schedule(abtask, abtask.getConfig().getStartTime(), abtask
//				.getConfig().getInterval());
//		log.debug(abtask.getBo().getOperationName() + "调度器任务加载完毕!");
		
//		Music139Task billtask = new Music139Task(new BillboardSynchroBo());
//		timer.schedule(billtask, billtask.getConfig().getStartTime(), billtask
//				.getConfig().getInterval());
//		log.debug(billtask.getBo().getOperationName() + "调度器任务加载完毕!");
		
//		Music139Task ktask = new Music139Task(new KeywordSynchroBo());
//		timer.schedule(ktask, ktask.getConfig().getStartTime(), ktask
//				.getConfig().getInterval());
//		log.debug(ktask.getBo().getOperationName() + "调度器任务加载完毕!");
		
//		Music139Task tagTask = new Music139Task(new MusicTagSynchroBo());
//		timer.schedule(tagTask, tagTask.getConfig().getStartTime(), tagTask
//				.getConfig().getInterval());
//		log.debug(tagTask.getBo().getOperationName() + "调度器任务加载完毕!");
		
//		Music139Task picTask = new Music139Task(new PicSynchroBoImpl());
//		timer.schedule(picTask, picTask.getConfig().getStartTime(), picTask
//				.getConfig().getInterval());
//		log.debug(picTask.getBo().getOperationName() + "调度器任务加载完毕!");

		
//      removed by aiyan 2012-03-28  		
//		// 运营分类同步任务
//		CateSynTask cateTask = new CateSynTask();
//		timer.schedule(cateTask, Config.getStartTime(), Config.getInterval());
//		log.debug(tagTask.getBo().getOperationName() + "调度器任务加载完毕!");
	}

}
