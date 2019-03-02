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
import com.aspire.dotcard.basevideosync.vo.DocFileVO;
import com.aspire.dotcard.basevideosync.vo.LabelsVO;
import com.aspire.dotcard.basevideosync.vo.ProgramVO;
import com.aspire.dotcard.basevideosync.vo.SalesPromotionVO;
import com.aspire.dotcard.basevideosync.vo.SceneFileVO;
import com.aspire.dotcard.basevideosync.vo.VideoMediaVO;
import com.aspire.dotcard.basevideosync.vo.VideoPicVO;
import com.aspire.dotcard.basevideosync.vo.VideosPropertysVO;

public class ProgramExportXMLFile extends BaseExportXmlFile{
	
	/**
	 * 标签组ID与标签组名称key-value列表
	 */
	private Map<String, String> tagGroupIDMap = null;
	
	public ProgramExportXMLFile()
	{
		//this.tableName = "t_v_sprogram";
		//this.fileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PROGRAM.txt";
		//this.verfFileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PROGRAM.verf";
		this.mailTitle = "新基地视频节目详情xml文件数据同步结果";
		this.fileDir = "content";
	}
	
	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		dataList = BaseVideoDAO.getInstance().getProgramIDList();
		keyMap = BaseVideoDAO.getInstance().getdProgramIDMap();
		tagGroupIDMap = BaseVideoDAO.getInstance().getTagGroupIDMap();
	}

	/**
	 * 用于回收数据
	 */
	public void destroy()
	{
		tagGroupIDMap.clear();
	}
	
	protected String checkData(Object object,String[] data) {
		ProgramVO program = (ProgramVO)object;
		String tmp;
		String programID = program.getProgramId();
		String cmsID = program.getCMSID();
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证节目详情xml文件字段格式，programID=" + data[0]);
		}
		//programID
		if(!BaseFileTools.checkFieldLength(programID, 21, true)){
			logger.error("programID=" + programID
					+ ",programID验证出错，该字段是必填字段，长度不超过21个字符！programID="
					+ programID);
			return BaseVideoConfig.CHECK_FAILED;
		}
		if(!programID.equals(data[0])){
			logger.error("programID=" + programID
					+ ",programID验证出错，programID不一致！programID="
					+ data[0]);
			return BaseVideoConfig.CHECK_FAILED;
		}
		//cmsID
		if(!BaseFileTools.checkFieldLength(cmsID, 21, true)){
			logger.error("programID=" + programID
					+ ",cmsID验证出错，该字段是必填字段，长度不超过21个字符！cmsID="
					+ cmsID);
			return BaseVideoConfig.CHECK_FAILED;
		}
		if(!cmsID.equals(data[1])){
			logger.error("programID=" + programID + ",cmsID=" + cmsID
					+ ",cmsID验证出错，cmsID不一致！cmsID="
					+ data[1]);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// name
		tmp = program.getName();
		if (!BaseFileTools.checkFieldLength(tmp, 128, true))
		{
			logger.error("programID=" + programID
					+ ",name验证出错，该字段是必填字段，长度不超过128个字符！name="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// name1
		tmp = program.getName1();
		if (!BaseFileTools.checkFieldLength(tmp, 128, false))
		{
			logger.error("programID=" + programID
					+ ",name1验证出错，该字段是必填字段，长度不超过128个字符！name1="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// name2
		tmp = program.getName2();
		if (!BaseFileTools.checkFieldLength(tmp, 128, false))
		{
			logger.error("programID=" + programID
					+ ",name2验证出错，该字段是必填字段，长度不超过128个字符！name2="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// createtime
		tmp = program.getCreatetimev();
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("programID=" + programID
					+ ",createtime验证出错，该字段是必填字段，长度不超过20个字符！createtime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		// updatetime
		tmp = program.getUpdatetimev();
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("programID=" + programID
					+ ",updatetime验证出错，该字段是必填字段，长度不超过20个字符！updatetime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// publishtime
		tmp = program.getPublishtimev();
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("programID=" + programID
					+ ",publishtime验证出错，该字段是必填字段，长度不超过20个字符！publishtime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// PRDPACK_ID
		tmp = program.getPrdpackId();
		if (!BaseFileTools.checkFieldLength(tmp, 20, false))
		{
			logger.error("programID=" + programID
					+ ",PRDPACK_ID验证出错，该字段是必填字段，长度不超过20个字符！PRDPACK_ID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// PRODUCT_ID
		tmp = program.getProductId();
		if (!BaseFileTools.checkFieldLength(tmp, 20, false))
		{
			logger.error("programID=" + programID
					+ ",PRODUCT_ID验证出错，该字段是必填字段，长度不超过20个字符！PRODUCT_ID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		//CopyRightType
		/*tmp = program.getCopyRightType();
		if (!BaseFileTools.checkFieldLength(tmp, 1, true))
		{
			logger.error("programID=" + programID
					+ ",CopyRightType验证出错，该字段是必填字段，长度不超过1个字符！CopyRightType=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}*/
		// CATEGORY
		tmp = program.getCategory();
		if (!BaseFileTools.checkFieldLength(tmp, 2, true))
		{
			logger.error("programID=" + programID
					+ ",CATEGORY验证出错，该字段是必填字段，长度不超过2个字符！CATEGORY=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// Type
		tmp = program.getType();
		if (!BaseFileTools.checkFieldLength(tmp, 2, true))
		{
			logger.error("programID=" + programID
					+ ",Type验证出错，该字段是必填字段，长度不超过2个字符！Type=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// SerialContentID
		tmp = program.getSerialContentID();
		if (null != tmp && !BaseFileTools.checkFieldLength(tmp, 30, false))
		{
			logger.error("programID=" + programID
					+ ",SerialContentID验证出错，该字段是必填字段，长度不超过30个字符！SerialContentID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// SerialSequence
		tmp = program.getSerialSequence();
		if (null != tmp && !BaseFileTools.checkFieldLength(tmp, 4, false))
		{
			logger.error("programID=" + programID
					+ ",SerialSequence验证出错，该字段是必填字段，长度不超过4个字符！SerialSequence=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// SerialCount
		tmp = program.getSerialCount();
		if (null != tmp && !BaseFileTools.checkFieldLength(tmp, 10, false))
		{
			logger.error("programID=" + programID
					+ ",SerialCount验证出错，该字段是必填字段，长度不超过10个字符！SerialCount=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// FORMTYPE
		tmp = program.getFormType();
		if (!BaseFileTools.checkFieldLength(tmp, 4, false))
		{
			logger.error("programID=" + programID
					+ ",FORMTYPE验证出错，该字段是必填字段，长度不超过4个字符！FORMTYPE=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// videoName
		tmp = program.getVideoName();
		if (!BaseFileTools.checkFieldLength(tmp, 128, false))
		{
			logger.error("programID=" + programID
					+ ",videoName验证出错，该字段是必填字段，长度不超过128个字符！videoName=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// SHORTNAME
		tmp = program.getvShortName();
		if (!BaseFileTools.checkFieldLength(tmp, 100, false))
		{
			logger.error("programID=" + programID
					+ ",SHORTNAME验证出错，该字段是必填字段，长度不超过100个字符！SHORTNAME=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// Author
		tmp = program.getvAuthor();
		if (!BaseFileTools.checkFieldLength(tmp, 200, false))
		{
			logger.error("programID=" + programID
					+ ",Author验证出错，该字段是必填字段，长度不超过200个字符！Author=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// DirectRecFlag
		tmp = program.getDirectRecFlag();
		if (null != tmp && !BaseFileTools.checkFieldLength(tmp, 2, false))
		{
			logger.error("programID=" + programID
					+ ",DirectRecFlag验证出错，该字段是必填字段，长度不超过2个字符！DirectRecFlag=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// Area
		tmp = program.getArea();
		if (!BaseFileTools.checkFieldLength(tmp, 50, false))
		{
			logger.error("programID=" + programID
					+ ",Area验证出错，该字段是必填字段，长度不超过50个字符！Area=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// Terminal
		tmp = program.getTerminal();
		if (!BaseFileTools.checkFieldLength(tmp, 50, false))
		{
			logger.error("programID=" + programID
					+ ",Terminal验证出错，该字段是必填字段，长度不超过50个字符！Terminal=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// Way
		tmp = program.getWay();
		if (!BaseFileTools.checkFieldLength(tmp, 2, false))
		{
			logger.error("programID=" + programID
					+ ",Way验证出错，该字段是必填字段，长度不超过2个字符！Way=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// Publish
		tmp = program.getPublish();
		if (!BaseFileTools.checkFieldLength(tmp, 2, false))
		{
			logger.error("programID=" + programID
					+ ",Publish验证出错，该字段是必填字段，长度不超过2个字符！Publish=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// Cduration
		tmp = program.getcDuration();
		if (!BaseFileTools.checkFieldLength(tmp, 5, false))
		{
			logger.error("programID=" + programID
					+ ",Cduration验证出错，该字段是必填字段，长度不超过5个字符！Cduration=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// DISPLAYTYPE
		tmp = program.getDisplayType();
		if (!BaseFileTools.checkFieldLength(tmp, 10, true))
		{
			logger.error("programID=" + programID
					+ ",DISPLAYTYPE验证出错，该字段是必填字段，长度不超过10个字符！DISPLAYTYPE=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// DisplayName
		tmp = program.getDisplayName();
		if (!BaseFileTools.checkFieldLength(tmp, 100, false))
		{
			logger.error("programID=" + programID
					+ ",DisplayName验证出错，该字段是必填字段，长度不超过100个字符！DisplayName=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// Assist
		tmp = program.getAssist();
		if (null != tmp && !BaseFileTools.checkFieldLength(tmp, 4000, false)) {
			logger.error("programID=" + programID
					+ ",Assist验证出错，该字段是必填字段，长度不超过4000个字符！Assist=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		// Detail
		tmp = program.getDetail();
		if (null != tmp && !BaseFileTools.checkFieldLength(tmp, 3000, false)) {
			logger.error("programID=" + programID
					+ ",Assist验证出错，该字段是必填字段，长度不超过3000个字符！Detail=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if (("7".equals(program.getFormType())||"8".equals(program.getFormType()))&&(program.getVideoMediaList() == null || program.getVideoMediaList().size() == 0)){
			logger.error("programID=" + programID
					+ ",没有媒体文件");
			return BaseVideoConfig.CHECK_FAILED;
		}
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	protected Object getObjectParas(Object object) {
		Map<String,Object> map = new HashMap<String,Object>();
		ProgramVO program = (ProgramVO)object;
		List<String> sqlCodeList = new ArrayList<String>();
		List<Object[]> parasList = new ArrayList<Object[]>();
		
		Object[] data = new Object[38];
		data[0] = program.getName();
		data[1] = program.getName1();
		data[2] = program.getName2();
		data[3] = program.getCreatetimev();
		data[4] = program.getUpdatetimev();
		data[5] = program.getPublishtimev();
		data[6] = program.getPrdpackId();
		data[7] = program.getProductId();
		data[8] = program.getCopyRightType();
		data[9] = program.getCategory();
		data[10] = program.getType();
		data[11] = program.getSerialContentID();
		data[12] = program.getSerialSequence();
		data[13] = program.getSerialCount();
		data[14] = program.getSubSerialIDS();
		data[15] = program.getFormType();
		data[16] = program.getVideoName();
		data[17] = program.getvShortName();
		data[18] = program.getvAuthor();
		data[19] = program.getDirectRecFlag();
		data[20] = program.getArea();
		data[21] = program.getTerminal();
		data[22] = program.getWay();
		data[23] = program.getPublish();
		data[24] = program.getcDuration();
		data[25] = program.getDisplayType();
		data[26] = program.getDisplayName();
		data[27] = program.getAssist();
		data[28] = program.getKeyword();
		data[29] = program.getAuthorizationWay();
		data[30] = program.getMiguPublish();
		data[31] = program.getBcLicense();
		data[32] = program.getInfluence();
		data[33] = program.getOriPublish();
		data[34] = program.getFeeType();
		data[35] = program.getDetail();
		data[36] = program.getCMSID();
		data[37] = program.getProgramId();
		map.put("data", data);
		
		if(null != program.getVideoPicList() && program.getVideoPicList().size() > 0){
			List<VideoPicVO> list = program.getVideoPicList();
			Object[] pic = new Object[11];
			//强版权
			for(int i = 0; i< list.size() ; i++){
				VideoPicVO vo = list.get(i);
				if(vo.getDpFileName().contains("TV_CONTENT")){	
					pic[0] = getImageAllPath(program, vo.getDpFileName());
				}else if(vo.getDpFileName().contains("V_CONTENT")){
					pic[1] = getImageAllPath(program, vo.getDpFileName());
				}else if(vo.getDpFileName().contains("HSJ1080H")){
					pic[2] = getImageAllPath(program, vo.getDpFileName());
				}else if(vo.getDpFileName().contains("HSJ1080V")){
					pic[3] = getImageAllPath(program, vo.getDpFileName());
				}else if(vo.getDpFileName().contains("HSJ720H")){
					pic[4] = getImageAllPath(program, vo.getDpFileName());
				}else if(vo.getDpFileName().contains("HSJ720V")){
					pic[5] = getImageAllPath(program, vo.getDpFileName());
				}else if(vo.getDpFileName().contains("V34_sc")){
					pic[7] = getImageAllPath(program, vo.getDpFileName());
				}else if(vo.getDpFileName().contains("V23_sc")){
					pic[8] = getImageAllPath(program, vo.getDpFileName());
				}else if(vo.getDpFileName().contains("H32_sc")){
					pic[9] = getImageAllPath(program, vo.getDpFileName());
				}else if(vo.getDpFileName().contains("sc")){
					pic[6] = getImageAllPath(program, vo.getDpFileName());
				}
			}
			/*if("1".equals(program.getCopyRightType())){	
			}
			//弱版权
			else if("2".equals(program.getCopyRightType())){
				for(int i = 0; i< list.size() ; i++){
					VideoPicVO vo = list.get(i);
					if(null != vo.getDpUsageCode() && !"".equals(vo.getDpUsageCode().trim())){
						String imageAllPath = getImageAllPath(program, vo.getDpFileName());
						switch(Integer.parseInt(vo.getDpUsageCode().trim())){
						case 0:
							pic[0] = imageAllPath;
							break;
						case 1:
							pic[1] = imageAllPath;
							break;
						case 2:
							pic[2] = imageAllPath;
							break;
						case 3:
							pic[3] = imageAllPath;
							break;
						case 4:
							pic[4] = imageAllPath;
							break;
						case 5:
							pic[5] = imageAllPath;
							break;
						case 6:
							pic[6] = imageAllPath;
							break;
						case 7:
							pic[7] = imageAllPath;
							break;
						case 8:
							pic[8] = imageAllPath;
							break;
						case 99:
							pic[9] = imageAllPath;
							break;
						}
					}
				}
			}*/
			
			pic[10] = program.getProgramId();
			parasList.add(pic);
			sqlCodeList.add(getInsertSqlCodeByVideoPic());
		}
		
		if(null != program.getLabelsList() && program.getLabelsList().size() > 0){
			List<LabelsVO> list = program.getLabelsList();
			for(int i=0; i < list.size();i++){
				Object[] label = new Object[5];
				LabelsVO vo = list.get(i);
				label[0] = vo.getLabelId();
				label[1] = vo.getLabelName();
				//根据标签ID获取标签组名称
				label[2] = tagGroupIDMap.get(vo.getLabelId());
				label[3] = program.getCMSID();
				label[4] = program.getProgramId();
				parasList.add(label);
				sqlCodeList.add(getInsertSqlCodeByLabel());
			}
		}
		
		if(null != program.getVideosPropertysList() && program.getVideosPropertysList().size() > 0){
			List<VideosPropertysVO> list = program.getVideosPropertysList();
			for(int i=0; i < list.size();i++){
				Object[] videosPropertys = new Object[4];
				VideosPropertysVO vo = list.get(i);
				videosPropertys[0] = vo.getPropertyKey();
				videosPropertys[1] = vo.getPropertyValue();
				videosPropertys[2] = program.getCMSID();
				videosPropertys[3] = program.getProgramId();
				parasList.add(videosPropertys);
				sqlCodeList.add(getInsertSqlCodeByVideosPropertys());
			}
		}
		
		if(null != program.getVideoMediaList() && program.getVideoMediaList().size() > 0){
			List<VideoMediaVO> list = program.getVideoMediaList();
			for(int i=0; i < list.size();i++){
				Object[] videoMedia = new Object[21];
				VideoMediaVO vo = list.get(i);
				videoMedia[0] = vo.getMediaFileID();
				videoMedia[1] = vo.getMediaFileName();
				videoMedia[2] = vo.getSourceFileName();
				videoMedia[3] = vo.getVisitPath();
				videoMedia[4] = vo.getMediaFilePath();
				videoMedia[5] = vo.getMediaFilePreviewPath();
				videoMedia[6] = vo.getMediaFileAction();
				videoMedia[7] = vo.getMediaSize();
				videoMedia[8] = vo.getDuration();
				videoMedia[9] = vo.getMediaType();
				videoMedia[10] = vo.getMediaUsageCode();
				videoMedia[11] = vo.getMediaCodeFormat();
				videoMedia[12] = vo.getMediaContainFormat();
				videoMedia[13] = vo.getMediaCodeRate();
				videoMedia[14] = vo.getMediaNetType();
				videoMedia[15] = vo.getMediaMimeType();
				videoMedia[16] = vo.getMediaResolution();
				videoMedia[17] = vo.getMediaProfile();
				videoMedia[18] = vo.getMediaLevel();
				videoMedia[19] = program.getCMSID();
				videoMedia[20] = program.getProgramId();
				parasList.add(videoMedia);
				sqlCodeList.add(getInsertSqlCodeByVideoMedia());
			}
		}
		//视频限免入库
		if(null != program.getSalesPromotionList() && program.getSalesPromotionList().size() > 0){
			List<SalesPromotionVO> list = program.getSalesPromotionList();
			for(int i=0; i < list.size();i++){
				Object[] sales = new Object[6];
				SalesPromotionVO vo = list.get(i);
				sales[0] = vo.getSalesDiscount();
				sales[1] = vo.getSalesCategory();
				sales[2] = vo.getSalesStartTime();
				sales[3] = vo.getSalesEndTime();
				sales[4] = vo.getSalesProductId();
				sales[5] = program.getProgramId();
				parasList.add(sales);
				sqlCodeList.add(getInsertSqlCodeBySales());
			}
		}
		
		Object[][] paras = new Object[parasList.size()][];
		String[] sqlCodes = new String[parasList.size()];
		
		for(int i =0 ; i < parasList.size() ; i ++){
			paras[i] = parasList.get(i);
			sqlCodes[i] = sqlCodeList.get(i);
		}
		map.put("paras", paras);
		map.put("sqlCodes", sqlCodes);
		return map;
	}
	
	protected Object getObject(Element root) {
		ProgramVO program = new ProgramVO();
    	program.setProgramId(root.elementText("contid"));
    	program.setName(root.elementText("name"));
    	program.setName1(root.elementText("name1"));
    	program.setName2(root.elementText("name2"));
    	program.setLevelval(root.elementText("Levelval"));
    	program.setRankval(root.elementText("Rankval"));
    	program.setCreatetimev(root.elementText("createtime"));
    	program.setUpdatetimev(root.elementText("updatetime"));
    	program.setPublishtimev(root.elementText("publishtime"));
    	program.setPrdpackId(root.elementText("PRDPACK_ID"));
    	program.setBc_id(root.elementText("BC_ID"));
    	program.setProductId(root.elementText("PRODUCT_ID"));
    	program.setSp_id(root.elementText("SP_ID"));
    	program.setSalesPromotionList(getSalesPromotionList(root));
    	program.setIspublished(root.elementText("Ispublished"));
    	program.setIssupwap(root.elementText("Issupwap"));
    	program.setIssuprms(root.elementText("Issuprms"));
    	program.setIssuph264(root.elementText("issuph264"));
    	program.setIssupwww(root.elementText("Issupwww"));
    	program.setIssuprchd(root.elementText("Issuprchd"));
    	program.setIsupdating(root.elementText("Isupdating"));
    	Element fields = root.element("fields");
    	if(null != fields){
    		program.setCMSID(fields.elementText("MMS_ID"));
    		program.setAssetID(fields.elementText("AssetID"));
    		program.setCpid(fields.elementText("CPID"));
    		program.setN_cpid(fields.elementText("N_CPID"));
    		program.setCreatorID(fields.elementText("CreatorID"));
    		program.setCopyRightType(fields.elementText("CopyRightType"));
    		program.setMediaLevel(fields.elementText("MediaLevel"));
    		program.setEcid(fields.elementText("ECID"));
    		program.setCopyRightId(fields.elementText("COPYRIGHTID"));
    		program.setCategory(fields.elementText("CATEGORY"));
    		program.setType(fields.elementText("TYPE"));
    		program.setSerialContentID(fields.elementText("SerialContentID"));
    		program.setSerialSequence(fields.elementText("SerialSequence"));
    		program.setSerialCount(fields.elementText("SerialCount"));
    		program.setSubSerialIDS(fields.elementText("SubSerial_IDS"));
    		program.setIsSubAlbum(fields.elementText("IsSubAlbum"));
    		program.setSubAlbum_IDS(fields.elementText("SubAlbum_IDS"));
    		program.setAlbum_IDS(fields.elementText("Album_IDS"));
    		program.setFormType(fields.elementText("FORMTYPE"));
    		program.setVideoName(fields.elementText("Name"));
    		program.setvShortName(fields.elementText("SHORTNAME"));
    		program.setUuid(fields.elementText("UDID"));
    		program.setDetail(fields.elementText("Detail"));
    		program.setvAuthor(fields.elementText("Author"));
    		program.setDisplayFileLists(fields.elementText("DISPLAYFILELISTS"));
    		program.setCreateTime(fields.elementText("CreateTime"));
    		program.setModifyTime(fields.elementText("ModifyTime"));
    		program.setDirectRecFlag(fields.elementText("DirectRecFlag"));
    		program.setcDuration(fields.elementText("CDuration"));
    		program.setDisplayType(fields.elementText("DISPLAYTYPE"));
    		program.setDisplayName(fields.elementText("DisplayName"));
    		program.setAssist(fields.elementText("Assist"));
    		getCopyRight(fields,program);
    		program.setKeyword(getKeyWords(fields));
    		program.setLabelsList(getLables(fields));
    		program.setContentLists(getContentList(fields));
    		program.setSceneFileList(getSceneFileList(fields));
    		program.setVideosPropertysList(getPropertyFileLists(fields));
    		program.setVideoMediaList(getMediaFileLists(fields));
    		program.setVideoPicList(getDisplayFileLists(fields));
    		program.setDocFileList(getDocFileList(fields));
    	}
		return program;
	}

	protected String getInsertSqlCode() {
		// insert into t_v_dprogram (id,name,name1,name2,createtimev,updatetimev,publishtimev,prdpack_id,
		//           product_id,copyrighttype,category,type,serialcontentid,serialsequence,serialcount,
		//          subserial_ids,formtype,videoname,vshortname,vauthor,directrecflag,area,terminal,way,
		//         publish,cduration,displaytype,displayname,assist,keyword,livestatus,lupdate,exetime,authorizationWay,miguPublish,bcLicense,Influence,oriPublish,FEETYPE,detail,cmsid,programid)
		//values (SEQ_T_V_DPROGRAM_ID.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'0',sysdate,sysdate,?,?,?,?,?,?,?,?,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getInsertSqlCode";
	}

	protected String getUpdateSqlCode() {
		// update t_v_dprogram set name=?,name1=?,name2=?,createtimev=?,updatetimev=?,publishtimev=?,prdpack_id=?,
		//           product_id=?,copyrighttype=?,category=?,type=?,serialcontentid=?,serialsequence=?,serialcount=?,
		//          subserial_ids=?,formtype=?,videoname=?,vshortname=?,vauthor=?,directrecflag=?,area=?,terminal=?,way=?,
		//         publish=?,cduration=?,displaytype=?,displayname=?,assist=?,keyword=?,livestatus='0',lupdate=sysdate,authorizationWay=?,
		//        miguPublish=?,bcLicense=?,Influence=?,oriPublish=?,FEETYPE=?,detail=?,cmsid=? where programid = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode() {
		// delete from t_v_dprogram p where p.programid = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getDelSqlCode";
	}
	
	private String getInsertSqlCodeByVideoPic() {
		// insert into T_V_VIDEOPIC_MID (id, pic_00_tv, pic_01_v, pic_02_hsj1080h, pic_03_hsj1080v, pic_04_hsj720h,
		//                pic_05_hsj720v,PIC_06_SC,PIC_07_V34_sc,PIC_08_V23_sc,PIC_09_H32_sc,lupdate,exetime,programid) 
		//values (SEQ_T_V_VIDEOPIC_ID.nextval,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getInsertSqlCodeByVideoPic";
	}
	
	private String getInsertSqlCodeByLabel() {
		// insert into T_V_LABLES_MID (id,labelid,labelname,labelzone,lupdate,exetime,cmsid,programid)
		//  values (SEQ_T_V_LABLES_ID.nextval,?,?,?,sysdate,sysdate,?,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getInsertSqlCodeByLabel";
	}
	
	private String getInsertSqlCodeByVideosPropertys() {
		// insert into T_V_VIDEOSPROPERTYS_MID (id,propertykey,propertyvalue,lupdate,exetime,cms_id,programid)
		//  values (SEQ_T_V_VIDEOSPROP_ID.nextval,?,?,sysdate,sysdate,?,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getInsertSqlCodeByVideosPropertys";
	}
	
	private String getInsertSqlCodeByVideoMedia() {
		// insert into T_V_VIDEOMEDIA_MID (id,mediafileid, mediafilename, sourcefilename, visitpath, mediafilepath,mediafilepreviewpath,
		//            mediafileaction,mediasize,duration,mediatype,mediausagecode,mediacodeformat,mediacontainformat,mediacoderate,
		//            medianettype,mediamimetype, mediaresolution,mediaprofile,medialevel,lupdate,exetime,cms_id,programid)
		//values (SEQ_T_V_VIDEOMEDIA_ID.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate,?,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getInsertSqlCodeByVideoMedia";
	}
	
	private String getInsertSqlCodeBySales() {
		// insert into T_V_LABLES_MID (id,labelid,labelname,labelzone,lupdate,exetime,cmsid,programid)
		//  values (SEQ_T_V_LABLES_ID.nextval,?,?,?,sysdate,sysdate,?,?)

		//insert into T_V_PROGRAM_SALES_MID(id,salesdiscount,salescategory,salesstarttime,salesendtime,lupdate,salesproductid,programid) values(SEQ_T_V_PROGRAM_SALES_ID.nextval,?,?,?,?,sysdate,?,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getInsertSqlCodeBySales";
	}
	
	/**
	 * 解析节目xml版权信息元素
	 * @param e
	 * @return
	 */
	private void getCopyRight(Element e,ProgramVO program){
		
		Element copyRight = e.element("CopyRight");
		if(null != copyRight){
			program.setCopyRightObjectID(copyRight.elementText("CopyRightObjectID"));
			program.setN_cpid(copyRight.elementText("N_CPID"));
			program.setCopyRightName(copyRight.elementText("Name"));
			program.setBeginDate(copyRight.elementText("BeginDate"));
			program.setEndDate(copyRight.elementText("EndDate"));
			program.setArea(copyRight.elementText("Area"));
    		program.setTerminal(copyRight.elementText("Terminal"));
    		program.setIntegrity(copyRight.elementText("Integrity"));
    		program.setClarity(copyRight.elementText("Scarcity"));
    		program.setWay(copyRight.elementText("Way"));
    		program.setPublish(copyRight.elementText("Publish"));
    		program.setClarity(copyRight.elementText("Clarity"));
    		program.setSupport(copyRight.elementText("Support"));
    		program.setScope(copyRight.elementText("Scope"));
    		program.setOutput(copyRight.elementText("Output"));
    		program.setChain(copyRight.elementText("Chain"));
    		program.setFeeType(copyRight.elementText("FeeType"));
    		program.setAuthorizationWay(copyRight.elementText("authorizationWay"));
    		program.setMiguPublish(copyRight.elementText("miguPublish"));
    		program.setBcLicense(copyRight.elementText("bcLicense"));
    		program.setInfluence(copyRight.elementText("Influence"));
    		program.setOriPublish(copyRight.elementText("oriPublish"));
    		program.setCopyRightUDID(copyRight.elementText("CopyRightUDID"));
    		program.setFile(copyRight.elementText("File"));
    		program.setOptimal(copyRight.elementText("Optimal"));
    		program.setScore(copyRight.elementText("Score"));
    		program.setNeedDRM(copyRight.elementText("NeedDRM"));
		}
	}
	
	/**
	 * 解析节目xml关键字元素
	 * @param e
	 * @return
	 */
	private String getKeyWords(Element e){
		String keyWords = null;
		StringBuffer keyWordStr = null;
		StringBuffer nkeyWordStr = null;
		Element keyWordsElement = e.element("KEYWORDS");
		if(null != keyWordsElement){
			List list = keyWordsElement.elements("keyword");
			if(null != list && list.size() > 0){
				
				for(int i = 0 ; i <  list.size() ; i++){
					Element keyWord = (Element)list.get(i);
					String primaryKey = keyWord.elementText("primaryKey");
					String keywordName = keyWord.elementTextTrim("keywordName").replace("/n", "");
					if(null != keywordName){
						if(null != primaryKey  && "1".equals(primaryKey)){
							if(null == keyWordStr)
								keyWordStr = new StringBuffer();
						    keyWordStr.append(keywordName);
							keyWordStr.append("；");	
						}else{
							if(null == nkeyWordStr)
								nkeyWordStr = new StringBuffer();
							nkeyWordStr.append(keywordName);
							nkeyWordStr.append("；");
						}
					}
				}
			}
		}
		if(null != keyWordStr){
			if(null != nkeyWordStr)
				keyWordStr.append(nkeyWordStr.toString());
			keyWords = keyWordStr.toString();
		}else if(null != nkeyWordStr){
			keyWords = nkeyWordStr.toString();
		}
		if(null != keyWords)
			keyWords = keyWords.substring(0,keyWords.length()-1);
		return keyWords;
	}
	
	/**
	 * 解析节目xml标签元素
	 * @param e
	 * @return
	 */
	private List<LabelsVO> getLables(Element e){

		Element labels = e.element("LABELS");
		List<LabelsVO> labelsList = null;
		if(null != labels){
			List list = labels.elements("Label");
			if(null != list && list.size() > 0){
				labelsList = new ArrayList<LabelsVO>();
				for(int i = 0 ; i <  list.size() ; i++){
					LabelsVO vo = new LabelsVO();
					Element label = (Element)list.get(i);
					vo.setLabelId(label.elementText("LabelID"));
					vo.setLabelName(label.elementText("LabelName"));
					labelsList.add(vo);
				}
			}
		}
		return labelsList;
	}
	
	/**
	 * 解析节目xml内容二级分类信息组元素
	 * @param e
	 * @return
	 */
	private List<VideosPropertysVO> getPropertyFileLists(Element e){

		Element propertyFileLists = e.element("propertyFileLists");
		List<VideosPropertysVO> videosPropertysList = null;
		if(null != propertyFileLists){
			List list = propertyFileLists.elements("propertyFile");
			if(null != list && list.size() > 0){
				videosPropertysList = new ArrayList<VideosPropertysVO>();
				for(int i = 0 ; i <  list.size() ; i++){
					VideosPropertysVO vo = new VideosPropertysVO();
					Element propertyFile = (Element)list.get(i);
					vo.setPropertyKey(propertyFile.elementText("propertyKey"));
					vo.setPropertyValue(propertyFile.elementText("propertyValue"));
					videosPropertysList.add(vo);
				}
			}
		}
		return videosPropertysList;
	}
	
	/**
	 * 解析节目xml内容媒体文件元素
	 * @param e
	 * @return
	 */
	private List<VideoMediaVO> getMediaFileLists(Element e){

		Element mediaFileLists = e.element("mediafiles");
		List<VideoMediaVO> videosMediaList = null;
		if(null != mediaFileLists){
			List list = mediaFileLists.elements("mediafile");
			if(null != list && list.size() > 0){
				videosMediaList = new ArrayList<VideoMediaVO>();
				for(int i = 0 ; i <  list.size() ; i++){
					VideoMediaVO vo = new VideoMediaVO();
					Element propertyFile = (Element)list.get(i);
					vo.setMediaFileID(propertyFile.elementText("mediaFileID"));
					vo.setMediaFileName(propertyFile.elementText("mediaFileName"));
					vo.setSourceFileName(propertyFile.elementText("sourceFileName"));
					vo.setVisitPath(propertyFile.elementText("visitPath"));
					vo.setMediaFilePath(propertyFile.elementText("mediaFilePath"));
					vo.setMediaFilePreviewPath(propertyFile.elementText("mediaFilePreviewPath"));
					vo.setMediaFileAction(propertyFile.elementText("mediaFileAction"));
					vo.setMediaSize(propertyFile.elementText("mediaSize"));
					vo.setDuration(propertyFile.elementText("duration"));
					vo.setMediaType(propertyFile.elementText("mediaType"));
					vo.setMediaUsageCode(propertyFile.elementText("mediaUsageCode"));
					vo.setMediaCodeFormat(propertyFile.elementText("mediaCodeFormat"));
					vo.setMediaContainFormat(propertyFile.elementText("mediaContainFormat"));
					vo.setMediaCodeRate(propertyFile.elementText("mediaCodeRate"));
					vo.setMediaNetType(propertyFile.elementText("mediaNetType"));
					vo.setMediaMimeType(propertyFile.elementText("mediaMimeType"));
					vo.setMediaResolution(propertyFile.elementText("mediaResolution"));
					vo.setMediaProfile(propertyFile.elementText("mediaProfile"));
					vo.setMediaLevel(propertyFile.elementText("mediaLevel"));
					videosMediaList.add(vo);
				}
			}
		}
		return videosMediaList;
	}
	
	/**
	 * 解析节目xml内容展现文件集(图片)元素
	 * @param e
	 * @return
	 */
	private List<VideoPicVO> getDisplayFileLists(Element e){

		Element displayFileLists = e.element("displayFileLists");
		List<VideoPicVO> videoPicList = null;
		if(null != displayFileLists){
			List list = displayFileLists.elements("displayFile");
			if(null != list && list.size() > 0){
				videoPicList = new ArrayList<VideoPicVO>();
				for(int i = 0 ; i <  list.size() ; i++){
					VideoPicVO vo = new VideoPicVO();
					Element propertyFile = (Element)list.get(i);					
					vo.setDpFileID(propertyFile.elementText("dpFileID"));
					vo.setDpFileName(propertyFile.elementText("dpFileName"));
					vo.setDpFileDetail(propertyFile.elementText("dpFileDetail"));
					vo.setDpFileType(propertyFile.elementText("dpFileType"));
					vo.setDpUsageCode(propertyFile.elementText("dpUsageCode"));
					vo.setDpUseType(propertyFile.elementText("dpUseType"));
					vo.setDpAdapType(propertyFile.elementText("dpAdapType"));
					vo.setPixel(propertyFile.elementText("Pixel"));
					videoPicList.add(vo);
				}
			}
		}
		return videoPicList;
	}
	
	private String getImageAllPath(ProgramVO program,String image){
		if(null == image || "".equals(image))
			return "";
		StringBuffer imageAllPath = new StringBuffer();
		imageAllPath.append(BaseVideoConfig.ProgramContentImagePath);
		if(null != program.getDisplayFileLists() && !"".equals(program.getDisplayFileLists())){
			imageAllPath.append(program.getDisplayFileLists());
		}else{
			imageAllPath.append("/");
			imageAllPath.append("image");
			imageAllPath.append("/");
			imageAllPath.append(program.getCMSID().substring(0, 4));
			imageAllPath.append("/");
			imageAllPath.append(program.getCMSID().substring(4, 7));
			imageAllPath.append("/");
			imageAllPath.append(program.getCMSID().substring(7, 10));
		}
		if (image.startsWith("/"))
		{
			imageAllPath.append(image);
		}
		else
		{
			imageAllPath.append("/" + image);
		}
		return imageAllPath.toString();
	}
	
	public List<String> getContentList(Element e){
		List<String> list = new ArrayList<String>();
		Element contentLists = e.element("ContentLists");
		if(null != contentLists){
			@SuppressWarnings("unchecked")
			List<Element> contentIdLists = contentLists.elements("SubContentID");
			if(null != contentIdLists && contentIdLists.size() > 0){
				for(int i = 0 ; i <  contentIdLists.size() ; i++){
					list.add(contentIdLists.get(i).getText());
				}
			}
		}
		return list;
	}
	
	public List<SceneFileVO> getSceneFileList(Element e){
		List<SceneFileVO> list = new ArrayList<SceneFileVO>();
		Element scenefile = e.element("SCENEFILES");
		SceneFileVO vo;
		if(null != scenefile){
			List<Element> scenefileList = scenefile.elements("Scenefile");
			if(scenefileList != null && scenefileList.size() > 0){
				for(int i = 0; i < scenefileList.size();i++){
					vo = new SceneFileVO();
					Element scenefileElement = scenefileList.get(i);
					vo.setScFileID(scenefileElement.elementText("scFileID"));
					vo.setScFileName(scenefileElement.elementText("scFileName"));
					vo.setScFileSpec(scenefileElement.elementText("scFileSpec"));
					vo.setScAdapType(scenefileElement.elementText("scAdapType"));
					list.add(vo);
				}
			}
		}
		return list;
	}
	
	public List<DocFileVO> getDocFileList(Element e){
		List<DocFileVO> list = new ArrayList<DocFileVO>();
		DocFileVO vo;
			List<Element> docFileList = e.elements("DocFile");
			if(docFileList != null && docFileList.size() > 0){
				for(int i = 0; i < docFileList.size();i++){
					vo = new DocFileVO();
					Element docFileElement = docFileList.get(i);
					vo.setDocID(docFileElement.elementText("DocID"));
					vo.setDocName(docFileElement.elementText("DocName"));
					vo.setDocDetail(docFileElement.elementText("DocDetail"));
					list.add(vo);
				}
			}
		return list;
	}
	
	public List<SalesPromotionVO> getSalesPromotionList(Element e){
		List<SalesPromotionVO> list = new ArrayList<SalesPromotionVO>();
		Element salesPromotion = e.element("salesPromotions");
		SalesPromotionVO vo;
		if(null != salesPromotion){
			List<Element> salesPromotionList = salesPromotion.elements("salesPromotion");
			if(salesPromotionList != null && salesPromotionList.size() > 0){
				for(int i = 0; i < salesPromotionList.size();i++){
					vo = new SalesPromotionVO();
					Element scenefileElement = salesPromotionList.get(i);
					vo.setSalesProductId(scenefileElement.elementText("salesProductId"));
					vo.setSalesDiscount(scenefileElement.elementText("salesDiscount"));
					vo.setSalesCategory(scenefileElement.elementText("salesCategory"));
					vo.setSalesStartTime(scenefileElement.elementText("salesStartTime"));
					vo.setSalesEndTime(scenefileElement.elementText("salesEndTime"));
					list.add(vo);
				}
			}
		}
		return list;
	}
}
