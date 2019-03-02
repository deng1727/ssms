package com.aspire.dotcard.basevideosync.exportfile;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportXmlFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.LiveExportXmlFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile;
import com.aspire.dotcard.basevideosync.vo.LiveVideoContentVO;
import com.aspire.dotcard.basevideosync.vo.LiveVideoVO;

public class SynBaseVideoXmlTask {

private JLogger logger = LoggerFactory.getLogger(SynBaseVideoXmlTask.class);
	
	/**
	 * 当前行信息
	 */
	private String lineText;
	
	/**
	 * 当前是第几行
	 */
	private int lineNumeber;
	
	/**
	 * 当前行的分隔符
	 */
	private String dataSpacers;
	
	/**
	 * 调用执行任务类
	 */
	private BaseExportXmlFile baseXml;

	
	public SynBaseVideoXmlTask(String data,
			int lineNumeber, String dataSpacers, BaseExportXmlFile base)
			throws InstantiationException, IllegalAccessException
	{
		this.lineText = data;
		this.lineNumeber = lineNumeber;
		this.dataSpacers = dataSpacers;
		this.baseXml = base;
	}
	
	/**
	 * 用于被多线程调用的具体执行方法
	 * 
	 * @param lineText
	 * @param tempFileName
	 */
	public void sysDataByXml()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始处理第" + lineNumeber + "条数据。数据内容：" + lineText);
		}
		
		// 临时数据
		String[] tempData = lineText.split(dataSpacers, -1);
		String exportDBText;

		//视频节目详情内容导入
		if (baseXml instanceof ProgramExportXMLFile)
		{
			//获取和处理节目详情xml文件内容
			exportDBText = exportXmlDataToDBByProgram(tempData);
		}else if (baseXml instanceof LiveExportXmlFile){
		//直播xml解析
			exportDBText = exportXmlDataToDBByLive(tempData);
		}
		//热点内容xml文件处理
		else if(baseXml instanceof HotcontentExportXmlFile)
		{
			exportDBText = exportXmlDataToDBByHotcontent(tempData);
		}else{
			exportDBText = "找不到对应的xml文件实现类";
		}
		
		// 存入数据库，如果处理失败加入邮件信息
		if (!BaseVideoConfig.EXPORT_DATA_SUCCESS.equals(exportDBText))
		{
			// 加入错误信息至邮件中...................
			baseXml.writeErrorToMail(lineNumeber, lineText,
					exportDBText);
			baseXml.setFailureProcessAdd();
			return;
		}
		else
		{
			baseXml.setSuccessAddAdd();
		}
	}
	
	/**
	 * 实现类实现把数据存入库中
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	private String exportXmlDataToDBByProgram(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始获取节目详情xml文件并处理数据至库中，programId="+data[0]);
		}

		String programId = data[0];
		String cmsID = data[1];
		String fileName = programId.substring(0, 3)+ File.separator + programId.substring(3, 6)+ File.separator + programId.substring(6, 9)+".xml";
		String filePath = null;
		try {
			
			filePath = baseXml.getXMLDataFile(fileName);
			
		} catch (BOException e) {
			logger.error("获取基地节目详情数据文件失败，当前数据文件名为：" + fileName + "！" + e);
			//更新视频节目概览表为已失败状态
			BaseVideoDAO.getInstance().updateSProgramExestatus(programId,cmsID,"2");
			return "获取基地节目详情数据文件失败，当前数据文件名为：" + fileName;
		}
		
		//获取xml文件目录
		Element root = BaseFileTools.loadXML(filePath);
		if(root == null){
			return "基地节目详情数据文件内容为空，文件名称为："+filePath;
		}
		Object object = baseXml.getObject(root);
		// 校验数据正确性，如果校验失败加入邮件信息
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(baseXml.checkData(object,data)))
		{
			// 加入错误信息至邮件中...................
			baseXml.writeErrorToMail(lineNumeber, lineText,
					"校验失败");
			baseXml.setFailureCheckAdd();
			//更新视频节目概览表为已失败状态
			BaseVideoDAO.getInstance().updateSProgramExestatus(programId,cmsID,"2");
			return "校验失败";
		}
		
		try
		{
			Map<String,Object> map = (Map<String,Object>)baseXml.getObjectParas(object);
			
			if(baseXml.hasKey(programId))
			{
				DB.getInstance().executeBySQLCode(baseXml.getUpdateSqlCode(), (Object[])map.get("data"));
				DB.getInstance().executeMutiBySQLCodeFaild((String[])map.get("sqlCodes"),(Object[][])map.get("paras"));
				baseXml.delKeyMap(programId);
				baseXml.setModifyNumAdd();
			}
			else
			{
				DB.getInstance().executeBySQLCode(baseXml.getInsertSqlCode(), (Object[])map.get("data"));
				DB.getInstance().executeMutiBySQLCodeFaild((String[])map.get("sqlCodes"),(Object[][])map.get("paras"));
				baseXml.setAddNumAdd();
			}
			
		}
		catch (DAOException e)
		{
			logger.error("执行基地节目详情数据入库操作失败，当前数据主健为：" + programId + "！" + e);
			//更新视频节目概览表为已失败状态
			BaseVideoDAO.getInstance().updateSProgramExestatus(programId,cmsID,"2");
			return "执行基地节目详情数据入库操作时发生数据库异常";
		}
		
		//更新视频节目概览表为已处理状态
		BaseVideoDAO.getInstance().updateSProgramExestatus(programId,cmsID,"1");
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/**
	 * 实现类实现把数据存入库中
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	private String exportXmlDataToDBByLive(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始获取直播节目单xml文件并处理数据至库中.");
		}

		String programId = data[0];
		String cmsid = data[1];
		String fileName = cmsid.substring(0, 4)+ File.separator + cmsid.substring(4, 7)+ File.separator + cmsid.substring(7, 10)+File.separator +cmsid+ File.separator +"playbill.xml";
		String filePath = null;
		try {
			
			filePath = baseXml.getXMLDataFile(fileName);
			
		} catch (BOException e) {
			logger.error("获取基地节目直播节目单数据文件失败，当前数据文件名为：" + fileName + "！" + e);
			return "获取基地节目直播节目单数据文件失败，当前数据文件名为：" + fileName;
		}
		
		//获取xml文件目录
		Element root = BaseFileTools.loadXML(filePath);
		if(root == null){
			return "基地节目直播节目单数据文件内容为空，文件名称为："+filePath;
		}
		Object object = baseXml.getObject(root);
		// 校验数据正确性，如果校验失败加入邮件信息
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(baseXml.checkData(object,data)))
		{
			// 加入错误信息至邮件中...................
			baseXml.writeErrorToMail(lineNumeber, lineText,
			"校验失败");
			baseXml.setFailureCheckAdd();
			return "校验失败";
		}
		
		List lv  = (List) object; 
		
		String del = baseXml.getDelSqlCode();
		String update = baseXml.getUpdateSqlCode();
		String insert = baseXml.getInsertSqlCode();
		Object[] par = {programId,cmsid};
		//清理该内容下的直播数据
		try {
			DB.getInstance().executeBySQLCode(del, par);
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			logger.error("exe videolive del syn error", e1);
		}
		
		//执行插入
		String[] sql = new String[lv.size()];
		Object[][] paras = new Object[lv.size()][];
		
		for(int i=0;i<lv.size();i++){
			sql[i] = insert;
			LiveVideoVO liveVideoVO =  (LiveVideoVO) lv.get(i);
			paras[i] = new Object[]{programId,cmsid,liveVideoVO.getPlayDay(),liveVideoVO.getPlayName(),liveVideoVO.getStartTime(),liveVideoVO.getEndTime(),liveVideoVO.getRanking(),liveVideoVO.getPlayShellID(),liveVideoVO.getPlayVodID()};
		}
		try {
			DB.getInstance().executeMutiBySQLCodeFaild(sql, paras);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("exe videolive syn error", e);
		}//容错处理，中间一条执行失败不影响后续执行
		logger.debug("liveVideo insert  end!");
		//更新节目详情中的直播处理状态
		try {
			DB.getInstance().executeBySQLCode(update, par);
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			logger.error("exe videolive del syn error", e1);
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/**
	 * 实现类实现把数据存入库中
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	private String exportXmlDataToDBByHotcontent(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}

		String titleID = data[0];
		/*if(!baseXml.keyMap.containsKey(titleID)){
			return "该人热点主题ID对应货架关系不存在，titleID="+titleID;
		}*/
		String fileName = titleID + File.separator  + "hottopic.xml";
		String filePath = null;
		try {
			
			filePath = baseXml.getXMLDataFile(fileName);
			
		} catch (BOException e) {
			logger.error("获取基地视频热点主题XML数据文件失败，当前数据文件名为：" + fileName + "！" + e);
			return "获取基地视频热点主题XML数据文件失败，当前数据文件名为：" + fileName;
		}
		
		//获取xml文件目录
		Element root = BaseFileTools.loadXML(filePath);
		if(root == null){
			return "基地视频热点主题XML数据文件内容为空，文件名称为："+filePath;
		}
		Object object = baseXml.getObject(root);
		
		// 校验数据正确性，如果校验失败加入邮件信息
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(baseXml.checkData(object,data)))
		{
			// 加入错误信息至邮件中...................
			baseXml.writeErrorToMail(lineNumeber, lineText,
					"校验失败");
			baseXml.setFailureCheckAdd();
			return "校验失败";
		}
		
		List lv  = (List) object; 
		
		String update = baseXml.getUpdateSqlCode();
		String insert = baseXml.getInsertSqlCode();
		String delSqlCode = baseXml.getDelSqlCode();
		//清理该热点主题下的商品数据
		try {
			DB.getInstance().executeBySQLCode(delSqlCode, new Object[]{titleID});
		} catch (DAOException e1) {
			logger.error("exe  del syn error", e1);
		}
		
		//执行插入
		String[] sql = new String[lv.size()];
		Object[][] paras = new Object[lv.size()][];
		for(int i=0;i<lv.size();i++){
			sql[i] = insert;
			LiveVideoContentVO vo = (LiveVideoContentVO) lv.get(i);
			StringBuffer sb = new StringBuffer("");
			if(vo.getImageLists() != null && vo.getImageLists().size() != 0)
			for(String str:vo.getImageLists()){
				sb.append(";").append(str);
			}
			paras[i] = new Object[]{vo.getPrdContId(),vo.getContentId(),vo.getNodeId(),vo.getContentType(),vo.getTitle(),vo.getShortTitle(),vo.getDescription(),!"".equals(sb.toString()) ? sb.toString().substring(1):"",vo.getLocation()};
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCodeFaild(sql, paras);
		}
		catch (DAOException e)
		{
			logger.error("执行热点内容xml数据操作失败，当前数据主健为：" + titleID + "！" , e);
			return "执行热点内容xml数据时发生数据库异常";
		}
		
		//更新热点主题中的处理状态为已处理
		/*try {
			DB.getInstance().executeBySQLCode(baseXml.getUpdateSqlCode(), new Object[]{titleID});
		} catch (DAOException e1) {
			logger.error("更新热点主题中的处理状态为已处理 时操作失败，当前数据主健为：" + titleID + "！", e1);
		}*/
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
}
