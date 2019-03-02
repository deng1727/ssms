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
	 * ��ǰ����Ϣ
	 */
	private String lineText;
	
	/**
	 * ��ǰ�ǵڼ���
	 */
	private int lineNumeber;
	
	/**
	 * ��ǰ�еķָ���
	 */
	private String dataSpacers;
	
	/**
	 * ����ִ��������
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
	 * ���ڱ����̵߳��õľ���ִ�з���
	 * 
	 * @param lineText
	 * @param tempFileName
	 */
	public void sysDataByXml()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�����" + lineNumeber + "�����ݡ��������ݣ�" + lineText);
		}
		
		// ��ʱ����
		String[] tempData = lineText.split(dataSpacers, -1);
		String exportDBText;

		//��Ƶ��Ŀ�������ݵ���
		if (baseXml instanceof ProgramExportXMLFile)
		{
			//��ȡ�ʹ����Ŀ����xml�ļ�����
			exportDBText = exportXmlDataToDBByProgram(tempData);
		}else if (baseXml instanceof LiveExportXmlFile){
		//ֱ��xml����
			exportDBText = exportXmlDataToDBByLive(tempData);
		}
		//�ȵ�����xml�ļ�����
		else if(baseXml instanceof HotcontentExportXmlFile)
		{
			exportDBText = exportXmlDataToDBByHotcontent(tempData);
		}else{
			exportDBText = "�Ҳ�����Ӧ��xml�ļ�ʵ����";
		}
		
		// �������ݿ⣬�������ʧ�ܼ����ʼ���Ϣ
		if (!BaseVideoConfig.EXPORT_DATA_SUCCESS.equals(exportDBText))
		{
			// ���������Ϣ���ʼ���...................
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
	 * ʵ����ʵ�ְ����ݴ������
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	private String exportXmlDataToDBByProgram(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��ȡ��Ŀ����xml�ļ����������������У�programId="+data[0]);
		}

		String programId = data[0];
		String cmsID = data[1];
		String fileName = programId.substring(0, 3)+ File.separator + programId.substring(3, 6)+ File.separator + programId.substring(6, 9)+".xml";
		String filePath = null;
		try {
			
			filePath = baseXml.getXMLDataFile(fileName);
			
		} catch (BOException e) {
			logger.error("��ȡ���ؽ�Ŀ���������ļ�ʧ�ܣ���ǰ�����ļ���Ϊ��" + fileName + "��" + e);
			//������Ƶ��Ŀ������Ϊ��ʧ��״̬
			BaseVideoDAO.getInstance().updateSProgramExestatus(programId,cmsID,"2");
			return "��ȡ���ؽ�Ŀ���������ļ�ʧ�ܣ���ǰ�����ļ���Ϊ��" + fileName;
		}
		
		//��ȡxml�ļ�Ŀ¼
		Element root = BaseFileTools.loadXML(filePath);
		if(root == null){
			return "���ؽ�Ŀ���������ļ�����Ϊ�գ��ļ�����Ϊ��"+filePath;
		}
		Object object = baseXml.getObject(root);
		// У��������ȷ�ԣ����У��ʧ�ܼ����ʼ���Ϣ
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(baseXml.checkData(object,data)))
		{
			// ���������Ϣ���ʼ���...................
			baseXml.writeErrorToMail(lineNumeber, lineText,
					"У��ʧ��");
			baseXml.setFailureCheckAdd();
			//������Ƶ��Ŀ������Ϊ��ʧ��״̬
			BaseVideoDAO.getInstance().updateSProgramExestatus(programId,cmsID,"2");
			return "У��ʧ��";
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
			logger.error("ִ�л��ؽ�Ŀ��������������ʧ�ܣ���ǰ��������Ϊ��" + programId + "��" + e);
			//������Ƶ��Ŀ������Ϊ��ʧ��״̬
			BaseVideoDAO.getInstance().updateSProgramExestatus(programId,cmsID,"2");
			return "ִ�л��ؽ�Ŀ��������������ʱ�������ݿ��쳣";
		}
		
		//������Ƶ��Ŀ������Ϊ�Ѵ���״̬
		BaseVideoDAO.getInstance().updateSProgramExestatus(programId,cmsID,"1");
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/**
	 * ʵ����ʵ�ְ����ݴ������
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	private String exportXmlDataToDBByLive(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��ȡֱ����Ŀ��xml�ļ�����������������.");
		}

		String programId = data[0];
		String cmsid = data[1];
		String fileName = cmsid.substring(0, 4)+ File.separator + cmsid.substring(4, 7)+ File.separator + cmsid.substring(7, 10)+File.separator +cmsid+ File.separator +"playbill.xml";
		String filePath = null;
		try {
			
			filePath = baseXml.getXMLDataFile(fileName);
			
		} catch (BOException e) {
			logger.error("��ȡ���ؽ�Ŀֱ����Ŀ�������ļ�ʧ�ܣ���ǰ�����ļ���Ϊ��" + fileName + "��" + e);
			return "��ȡ���ؽ�Ŀֱ����Ŀ�������ļ�ʧ�ܣ���ǰ�����ļ���Ϊ��" + fileName;
		}
		
		//��ȡxml�ļ�Ŀ¼
		Element root = BaseFileTools.loadXML(filePath);
		if(root == null){
			return "���ؽ�Ŀֱ����Ŀ�������ļ�����Ϊ�գ��ļ�����Ϊ��"+filePath;
		}
		Object object = baseXml.getObject(root);
		// У��������ȷ�ԣ����У��ʧ�ܼ����ʼ���Ϣ
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(baseXml.checkData(object,data)))
		{
			// ���������Ϣ���ʼ���...................
			baseXml.writeErrorToMail(lineNumeber, lineText,
			"У��ʧ��");
			baseXml.setFailureCheckAdd();
			return "У��ʧ��";
		}
		
		List lv  = (List) object; 
		
		String del = baseXml.getDelSqlCode();
		String update = baseXml.getUpdateSqlCode();
		String insert = baseXml.getInsertSqlCode();
		Object[] par = {programId,cmsid};
		//����������µ�ֱ������
		try {
			DB.getInstance().executeBySQLCode(del, par);
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			logger.error("exe videolive del syn error", e1);
		}
		
		//ִ�в���
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
		}//�ݴ����м�һ��ִ��ʧ�ܲ�Ӱ�����ִ��
		logger.debug("liveVideo insert  end!");
		//���½�Ŀ�����е�ֱ������״̬
		try {
			DB.getInstance().executeBySQLCode(update, par);
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			logger.error("exe videolive del syn error", e1);
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/**
	 * ʵ����ʵ�ְ����ݴ������
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	private String exportXmlDataToDBByHotcontent(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�洢����������.");
		}

		String titleID = data[0];
		/*if(!baseXml.keyMap.containsKey(titleID)){
			return "�����ȵ�����ID��Ӧ���ܹ�ϵ�����ڣ�titleID="+titleID;
		}*/
		String fileName = titleID + File.separator  + "hottopic.xml";
		String filePath = null;
		try {
			
			filePath = baseXml.getXMLDataFile(fileName);
			
		} catch (BOException e) {
			logger.error("��ȡ������Ƶ�ȵ�����XML�����ļ�ʧ�ܣ���ǰ�����ļ���Ϊ��" + fileName + "��" + e);
			return "��ȡ������Ƶ�ȵ�����XML�����ļ�ʧ�ܣ���ǰ�����ļ���Ϊ��" + fileName;
		}
		
		//��ȡxml�ļ�Ŀ¼
		Element root = BaseFileTools.loadXML(filePath);
		if(root == null){
			return "������Ƶ�ȵ�����XML�����ļ�����Ϊ�գ��ļ�����Ϊ��"+filePath;
		}
		Object object = baseXml.getObject(root);
		
		// У��������ȷ�ԣ����У��ʧ�ܼ����ʼ���Ϣ
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(baseXml.checkData(object,data)))
		{
			// ���������Ϣ���ʼ���...................
			baseXml.writeErrorToMail(lineNumeber, lineText,
					"У��ʧ��");
			baseXml.setFailureCheckAdd();
			return "У��ʧ��";
		}
		
		List lv  = (List) object; 
		
		String update = baseXml.getUpdateSqlCode();
		String insert = baseXml.getInsertSqlCode();
		String delSqlCode = baseXml.getDelSqlCode();
		//������ȵ������µ���Ʒ����
		try {
			DB.getInstance().executeBySQLCode(delSqlCode, new Object[]{titleID});
		} catch (DAOException e1) {
			logger.error("exe  del syn error", e1);
		}
		
		//ִ�в���
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
			logger.error("ִ���ȵ�����xml���ݲ���ʧ�ܣ���ǰ��������Ϊ��" + titleID + "��" , e);
			return "ִ���ȵ�����xml����ʱ�������ݿ��쳣";
		}
		
		//�����ȵ������еĴ���״̬Ϊ�Ѵ���
		/*try {
			DB.getInstance().executeBySQLCode(baseXml.getUpdateSqlCode(), new Object[]{titleID});
		} catch (DAOException e1) {
			logger.error("�����ȵ������еĴ���״̬Ϊ�Ѵ��� ʱ����ʧ�ܣ���ǰ��������Ϊ��" + titleID + "��", e1);
		}*/
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
}
