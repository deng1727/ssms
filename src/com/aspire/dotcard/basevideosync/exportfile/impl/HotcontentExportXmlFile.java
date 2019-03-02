package com.aspire.dotcard.basevideosync.exportfile.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseExportXmlFile;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileTools;
import com.aspire.dotcard.basevideosync.vo.LiveVideoContentVO;

public class HotcontentExportXmlFile extends BaseExportXmlFile{
	
	public HotcontentExportXmlFile()
	{
		//this.tableName = "t_v_sprogram";
		//this.fileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PROGRAM.txt";
		//this.verfFileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PROGRAM.verf";
		this.mailTitle = "�»�����Ƶ�ȵ���������xml�ļ�����ͬ�����";
		this.fileDir = "hotcontent";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		dataList = BaseVideoDAO.getInstance().getHotcontentIDList();
		//keyMap = BaseVideoDAO.getInstance().getHotcontentIDMap();
	}
	
	/**
	 * ���ڻ�������
	 */
	public void destroy()
	{
		if(dataList!=null) dataList.clear();
	}
	
	protected String checkData(Object object,String[]data) {
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�ȵ�����xml�ļ��ֶθ�ʽ��titleID=" + data[0]);
		}
		
		List<LiveVideoContentVO> list = (List<LiveVideoContentVO>)object;
		if(null == list || list.size() == 0){
			logger.error("titleID=" + data[0]
					+ ",���ȵ�����ID�µ�����Ϊ��");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		for(int i = 0; i < list.size() ; i++){
			LiveVideoContentVO vo =  (LiveVideoContentVO) list.get(i);
			if (!BaseFileTools.checkFieldLength(vo.getPrdContId(), 19, false))
			{
				logger.error("titleID=" + data[0]
						+ ",prdcontid��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����19���ַ���prdcontid="
						+ vo.getPrdContId());
				return BaseVideoConfig.CHECK_FAILED;
			}
			
			if (!BaseFileTools.checkFieldLength(vo.getContentId(), 19, false))
			{
				logger.error("titleID=" + data[0]
						+ ",ContentId��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����19���ַ���ContentId="
						+ vo.getContentId());
				return BaseVideoConfig.CHECK_FAILED;
			}
			
			if (!BaseFileTools.checkFieldLength(vo.getNodeId(), 19, false))
			{
				logger.error("titleID=" + data[0]
						+ ",NodeId��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����19���ַ���NodeId="
						+ vo.getNodeId());
				return BaseVideoConfig.CHECK_FAILED;
			}
			
			if (!BaseFileTools.checkFieldLength(vo.getContentType(), 19, true))
			{
				logger.error("titleID=" + data[0]
						+ ",ContentType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����19���ַ���ContentType="
						+ vo.getContentType());
				return BaseVideoConfig.CHECK_FAILED;
			}
			
			if (!BaseFileTools.checkFieldLength(vo.getTitle(), 2000, true))
			{
				logger.error("titleID=" + data[0]
						+ ",Title��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2000���ַ���Title="
						+ vo.getTitle());
				return BaseVideoConfig.CHECK_FAILED;
			}
			
			if (!BaseFileTools.checkFieldLength(vo.getShortTitle(), 2000, true))
			{
				logger.error("titleID=" + data[0]
						+ ",ShortTitle��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2000���ַ���ShortTitle="
						+ vo.getShortTitle());
				return BaseVideoConfig.CHECK_FAILED;
			}
			
			if (!BaseFileTools.checkFieldLength(vo.getDescription(), 2000, false))
			{
				logger.error("titleID=" + data[0]
						+ ",Description��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2000���ַ���Description="
						+ vo.getDescription());
				return BaseVideoConfig.CHECK_FAILED;
			}
			if(vo.getImageLists() != null && vo.getImageLists().size() != 0){			
				List<String> images = vo.getImageLists();
				for(int j = 0;j < images.size();j++){
					if (!BaseFileTools.checkFieldLength(images.get(j), 2000, true))
					{
						logger.error("titleID=" + data[0]
								+ ",Image��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2000���ַ���Image="
								+ images.get(j));
						return BaseVideoConfig.CHECK_FAILED;
					}
				}
			}
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	protected Object getObject(Element root) {
		List<LiveVideoContentVO> LiveVideoContentList = new ArrayList<LiveVideoContentVO>();
		Element contents = root.element("Contents");
		String location = root.elementText("Location");
		if (null != contents) {
			List<Element> elements = contents.elements("Content");
			if (elements != null && elements.size() > 0) {
				for (int j = 0; j < elements.size(); j++) {
					LiveVideoContentVO vo = new LiveVideoContentVO();
					Element content = elements.get(j);
					vo.setLocation(location);
					vo.setContentId(content.elementText("ContentId"));
					vo.setPrdContId(content.elementText("PrdContId"));
					vo.setContentType(content.elementText("ContentType"));
					vo.setDescription(content.elementText("Description"));
					vo.setNodeId(content.elementText("NodeId"));
					vo.setTitle(content.elementText("Title"));
					vo.setShortTitle(content.elementText("ShortTitle"));
					Element e = content.element("Images");
					if(null != e){					
						List images = e.elements("Image");
						if (images != null && images.size() > 0) {
							List<String> list = new ArrayList<String>();
							for (int i = 0; i < images.size(); i++) {
								StringBuffer sb  = new StringBuffer("");
								sb.append(BaseVideoConfig.ProgramContentImagePath);
								sb.append("/");
								sb.append("hotcontent");
								sb.append("/");
								sb.append(location);
								sb.append("/");
								sb.append("image");
								sb.append("/");
								sb.append(vo.getPrdContId());
								sb.append("/");
								sb.append(((Element) images.get(i)).getTextTrim().replaceAll("\n", ""));
								list.add(sb.toString());
							}
							vo.setImageLists(list);
						}
					}
					LiveVideoContentList.add(vo);
				}
			}
		}
		return LiveVideoContentList;
	}
	
	protected Object getObjectParas(Object object) {
		return null;
	}
	
	protected String getInsertSqlCode() {
		// insert into T_V_HOTCONTENT_PROGRAM(id,prdcontid,contentid,nodeid,contenttype,title,shorttitle,description,image,location,lupdate) values(SEQ_T_V_HOTCONTENTPRO_ID.NEXTVAL,?,?,?,?,?,?,?,?,?,sysdate)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportXmlFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode() {
		// update t_v_hotcontent h set h.exestatus ='1' where h.titleid = ? 
		return "com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportXmlFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode() {
		// delete from T_V_HOTCONTENT_PROGRAM p where p.location = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportXmlFile.getDelSqlCode";
	}
	
}
