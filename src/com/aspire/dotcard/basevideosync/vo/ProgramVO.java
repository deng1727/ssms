package com.aspire.dotcard.basevideosync.vo;

import java.util.List;

public class ProgramVO {

	/**
     * ����id
     */
    private String id;
	/**
     * CMS����ID,��Ŀid
     */
    private String programId;
    /**
     * CMS����ID,���ݱ��,10λ����Ψһ�̱��룬ý��IDCMS��2��ͷ��MMS��3��ͷ,�൱����ƵID
     */
    private String CMSID;
    /**
     * ý�ʱ�� 10λ����Ψһ�̱��루������ContentIDһ�£�CMS��2��ͷ��MMS��3��ͷ
     */
    private String assetID;
    /**
     * ���������
     */
    private String cpid;
    /**
     * ��Ŀ����
     */
    private String name;
    /**
     * ��Ŀ����1,Ԥ��
     */
    private String name1;
    /**
     * ��Ŀ����2,Ԥ��
     */
    private String name2;
    /**
     * �ֲ���Ϣ����ʶ��Ŀ��Ҫ��
     */
    private String levelval;
    /**
     * �ּ���Ϣ����ʶ��Ŀ��Ҫ��
     */
    private String rankval;
    /**
     * ����ʱ��
     */
    private String createtimev;
    /**
     * ����ʱ��
     */
    private String updatetimev;
    /**
     * ����ʱ��
     */
    private String publishtimev;
    /**
     * �Ƿ񷢲� 1������ 0��δ����
     */
    private String ispublished;
    /**
     * �Ƿ�WAP ����ԭ���Ż���Ĭ��Ϊ1
     */
    private String issupwap;
    /**
     * �Ƿ�RMS ����ԭ���Ż���Ĭ��Ϊ1
     */
    private String issuprms;
    /**
     * �Ƿ�h264 ����ԭ���Ż���Ĭ��Ϊ1
     */
    private String issuph264;
    /**
     * �Ƿ�www ����ԭ���Ż���Ĭ��Ϊ1
     */
    private String issupwww;
    /**
     * �Ƿ�suprchd ����ԭ���Ż���Ĭ��Ϊ1
     */
    private String issuprchd;
    /**
     * �缯�Ǹ���״̬ 0.������ɣ�1.������
     */
    private String isupdating;
    /**
     * ���ط�ID
     */
    private String bc_id;
    /**
     * ֧�ŷ�ID
     */
    private String sp_id;
    /**
     * ��Ʒ��ID
     */
    private String prdpackId;
    /**
     * ��ƷID,����Ϊ�գ�Ϊ��ʱ���ݼ۸��Բ�Ʒ�����ü۸�Ϊ׼
     */
    private String productId;
    /**
     * ���������ݵ�CP����Ա���
     */
    private String creatorID;
    /**
     * ��Ȩ���ͣ�ö��ֵ��1-ǿ��Ȩ��2-����Ȩ
     */
    private String copyRightType;
    /**
     * ý���ļ������� ý���ļ������� ö��ֵ��1-���壨576p����2-���壨720p����3-ȫ���壨1080p��
     */
    private String mediaLevel;
    /**
     * ���ſͻ�ID 10λ�����ַ�����¼���ſͻ�ID��
     */
    private String ecid;
    /**
     * ��Ȩ��� ����������һ�� Ϊ�˼��ݱ���
     */
    private String copyRightId;
	/**
     * �������ö��ֵ: 1-��Ƶ;2-��Ƶ;3-ͼ��;4-����;5-���ã�
     */
    private String category;
    /**
     * �������ͣ�1-�㲥��2-ֱ����3-���أ�4-�㲥+���أ�
     * 5-ģ��ֱ�� 0-��� 6�����ݼ���Ԥ��ֵ�� 7-������� 8-����ֱ�� 9-����ģ��ֱ��
     */
    private String type;
    /**
     * ���������缯������ID������FormtypeΪ7ʱ��Ч
     */
    private String serialContentID;
    /**
     * �����ھ缯�е���ţ�����FormtypeΪ7ʱ��Ч��1��ʼ����������
     */
    private String serialSequence;
    /**
     * �缯���ܼ���������FormtypeΪ6ʱ��Ч
     */
    private String serialCount;
    /**
     * �缯���ܼ���������FormtypeΪ6ʱ��Ч
     */
    private String subSerialIDS;
    /**
     * �Ƿ�ר���е����ݣ�����FORMTYPE=8ʱ��Ч  1���� �գ���
     */
    private String isSubAlbum;
    /**
     * ר��������ID�б�,����FORMTYPE=9ʱ��Ч  ��ĿID���ԡ������ŷָ�
     */
    private String subAlbum_IDS;
    /**
     * ��������ר����ID�б�����IsSubAlbum=1, FORMTYPE=8ʱ��Ч  ��ĿID���ԡ������ŷָ�
     */
    private String album_IDS;
    /**
     * �缯���ͣ�6���缯���ǣ�,7���缯�ĵ���,8���Ǿ缯
     */
    private String formType;
    /**
     * �û��Զ������ݱ�ʶ
     */
    private String uuid;
    /**
     * ����˵��
     */
    private String detail;
    /**
     * ����ʱ�� ��ʽ��YYYYMMDDHHmmSS
     */
    private String createTime;
    /**
     * ����޸�ʱ�� ��ʽ��YYYYMMDDHHmmSS
     */
    private String modifyTime;
    /**
     * ��Ƶ��������
     */
    private String videoName;
    /**
     * ��Ƶ���ݼ��
     */
    private String vShortName;
    /**
     * ��Ƶ��������
     */
    private String vAuthor;
    /**
     * ֧��ֱ���ط����ԣ�0-iphoneֱ������,1-��֧��ֱ���ط�,2-֧��ֱ���ط�
     * ����������TypeΪ2(ֱ��)ʱ�����ֶ���Ч��
     * ֧��ֱ���طŹ�����Ҫ��ӵ��ֶΣ�֪ͨOMS
     */
    private String directRecFlag;
    /**
     * ��Ȩ������
     */
    private String copyRightObjectID;
    /**
     * ��Ȩ����CP
     */
    private String n_cpid;
    /**
     * ��Ȩ����
     */
    private String copyRightName;
    /**
     * ��Ȩ��Ȩ����
     */
    private String beginDate;
    /**
     * ��Ȩ��������
     */
    private String endDate;
    /**
     * ��Ȩ����Χ���ɶ��������֮���ԡ�|������
     * ö��ֵ��1-�ڵأ�2-��ۣ�3-���ţ�4-̨�壻5-���⣻6-�κ�����
     */
    private String area;
    /**
     * ��Ȩ�նˣ��ɶ�����ն�֮���ԡ�|������
     * ö��ֵ��1-ȫƽ̨��2-�ֻ��նˣ�3-PC��4-IPTV��5-ƽ�壻6-����������
     */
    private String terminal;
    /**
     * ��Ȩ������ ö��ֵ��1-�߱�������Ȩ��֤�����ϣ�2-���ṩ��Ȩ�����һ��֤�����ϣ�3-���ṩ��Ȩ������������
     */
    private String integrity;
    /**
     * ��Ȩϡȱ�� ö��ֵ��1-ȫ�����ң�2-�ƶ����������ң�3-�Ƕ���
     */
    private String scarcity;
    /**
     * ��Ȩʹ�÷�ʽ���ƣ�1-�����ţ�2-�����أ�3-����
     */
    private String way;
    /**
     * �Ƿ��׷���0-�ǣ�1-��
     */
    private String publish;
    /**
     * ���������� ö��ֵ��1-���壨576p����2-���壨720p����3-ȫ���壨1080p��
     */
    private String clarity;
    /**
     * ý��֧�� ��ѡ���ɶ������ý��֮���ԡ�|��������ö��ֵ��1-�������ŷ����᣻2-�ܱ�ʵ��֧�ţ�3-Ƭ��̽�����ר�ã�4-�ֻ���ƵLOGOý��֧��
     */
    private String support;
    /**
     * ��Ȩ��Ȩ��Χ ö��ֵ��1-Ʒ�ƣ�2-ȫҵ��
     */
    private String scope;
    /**
     * ��Ȩ�Ƿ����� ö��ֵ��0-�ǣ�1-��
     */
    private String output;
    /**
     * ��Ȩ��
     */
    private String chain;
    /**
     * �ʷ�����
     */
    private String feeType;
    /**
     * ��Ȩ��ʽ ��Ȩ��ʽ	ö��ֵ��1-��Ƭ��Ȩ��2-������Ȩ
     */
    private String authorizationWay;
    /**
     * �乾���� ö��ֵ��1-�Ƕ��ҷ��׷���2-���һ��׷�
     */
    private String miguPublish;
    /**
     * ������� ö��ֵ��1-��Ժ�߷ǵ���̨��2-Ժ�߻����̨
     */
    private String bcLicense;
    /**
     * ����Ӱ�� ö��ֵ��1-���Ȳ���2-�Ȳ�
     */
    private String influence;
    /**
     * ԭ������ ö��ֵ��1-������ֱǩ��2-������3-��ԭ������
     */
    private String oriPublish;
    /**
     * ��Ȩ�Զ����ʶ
     */
    private String copyRightUDID;
    /**
     * ��Ȩ�ļ� �ɿգ���Ȩ�ļ�·�����ɶ�����ԡ�|������
     */
    private String file;
    /**
     * ���Ű�Ȩ��ʾ ö��ֵ��0-�ǣ�1-��
     */
    private String optimal;
    /**
     * ��Ȩ��ֵ ���ݹ����������(����Ϊ��λ����������,��ʱȡĬ��ֵ000)
     */
    private String score;
    /**
     * �Ƿ���Ҫ֧��DRM ǿ��Ȩ���ݲŻ����������ԣ�ö��ֵ�� 0-�ǣ�1-�� (��ΪĬ��ֵ)�������DRM���ݣ���Ҫ��DRM�������ſ��Խ��в���
     */
    private String needDRM;
    /**
     * �ؼ��֣����ؼ��ְ��չؼ������ȵĹ���ƴ����һ���ֶδ洢��
     * �ǹؼ��־ͷ���ǰ�棬���Ǿͷ�����棬ʹ�����ķֺŷָ�
     */
    private String keyword;
    /**
     * �����ݵĵ㲥�ļ�����ʱ��
     */
    private String cDuration;
    /**
     * ����һ������ID��CMSϵͳ����
     */
    private String displayType;
    /**
     * ����һ����������
     */
    private String displayName;
    /**
     * ����������Ϣ���ַ�����ʽ����0~n��һ������������ɣ��ԡ�|������
     */
    private String assist;
    /**
     * ��Ŀ���ļ����·����ֱ����Ŀ��Ч��
     */
    private String playbillFilePath;
    /**
     * ͼƬ����Ŀ¼��
     */
    private String displayFileLists;
	/**
     * ������ʱ��
     */
    private String lupdate;
    /**
     * ͬ��ʱ��
     */
    private String exetime;
    
    private List<LabelsVO> labelsList;
    
    private List<VideosPropertysVO> videosPropertysList;
    
    private List<VideoMediaVO> videoMediaList;
    
    private List<VideoPicVO> videoPicList;
    
    private List<SalesPromotionVO> salesPromotionList;
    /**
     * ���ݼ������������б� ��0��100�������
     */
    private List<String> contentLists;
    /**
     * ���������ļ����ʺ����������
     */
    private List<SceneFileVO> sceneFileList;
    
    private List<DocFileVO> docFileList;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getCMSID() {
		return CMSID;
	}
	public void setCMSID(String cMSID) {
		CMSID = cMSID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getCreatetimev() {
		return createtimev;
	}
	public void setCreatetimev(String createtimev) {
		this.createtimev = createtimev;
	}
	public String getUpdatetimev() {
		return updatetimev;
	}
	public void setUpdatetimev(String updatetimev) {
		this.updatetimev = updatetimev;
	}
	public String getPublishtimev() {
		return publishtimev;
	}
	public void setPublishtimev(String publishtimev) {
		this.publishtimev = publishtimev;
	}
	public String getPrdpackId() {
		return prdpackId;
	}
	public void setPrdpackId(String prdpackId) {
		this.prdpackId = prdpackId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCopyRightType() {
		return copyRightType;
	}
	public void setCopyRightType(String copyRightType) {
		this.copyRightType = copyRightType;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSerialContentID() {
		return serialContentID;
	}
	public void setSerialContentID(String serialContentID) {
		this.serialContentID = serialContentID;
	}
	public String getSerialSequence() {
		return serialSequence;
	}
	public void setSerialSequence(String serialSequence) {
		this.serialSequence = serialSequence;
	}
	public String getSerialCount() {
		return serialCount;
	}
	public void setSerialCount(String serialCount) {
		this.serialCount = serialCount;
	}
	public String getSubSerialIDS() {
		return subSerialIDS;
	}
	public void setSubSerialIDS(String subSerialIDS) {
		this.subSerialIDS = subSerialIDS;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getvShortName() {
		return vShortName;
	}
	public void setvShortName(String vShortName) {
		this.vShortName = vShortName;
	}
	public String getvAuthor() {
		return vAuthor;
	}
	public void setvAuthor(String vAuthor) {
		this.vAuthor = vAuthor;
	}
	public String getDirectRecFlag() {
		return directRecFlag;
	}
	public void setDirectRecFlag(String directRecFlag) {
		this.directRecFlag = directRecFlag;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getcDuration() {
		return cDuration;
	}
	public void setcDuration(String cDuration) {
		this.cDuration = cDuration;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getAssist() {
		return assist;
	}
	public void setAssist(String assist) {
		this.assist = assist;
	}
	public String getPlaybillFilePath() {
		return playbillFilePath;
	}
	public void setPlaybillFilePath(String playbillFilePath) {
		this.playbillFilePath = playbillFilePath;
	}
	public String getDisplayFileLists() {
		return displayFileLists;
	}
	public void setDisplayFileLists(String displayFileLists) {
		this.displayFileLists = displayFileLists;
	}
	public String getLupdate() {
		return lupdate;
	}
	public void setLupdate(String lupdate) {
		this.lupdate = lupdate;
	}
	public String getExetime() {
		return exetime;
	}
	public void setExetime(String exetime) {
		this.exetime = exetime;
	}
	
	public List<LabelsVO> getLabelsList() {
		return labelsList;
	}
	public void setLabelsList(List<LabelsVO> labelsList) {
		this.labelsList = labelsList;
	}
	public List<VideosPropertysVO> getVideosPropertysList() {
		return videosPropertysList;
	}
	public void setVideosPropertysList(List<VideosPropertysVO> videosPropertysList) {
		this.videosPropertysList = videosPropertysList;
	}
	public List<VideoMediaVO> getVideoMediaList() {
		return videoMediaList;
	}
	public void setVideoMediaList(List<VideoMediaVO> videoMediaList) {
		this.videoMediaList = videoMediaList;
	}
	public List<VideoPicVO> getVideoPicList() {
		return videoPicList;
	}
	public void setVideoPicList(List<VideoPicVO> videoPicList) {
		this.videoPicList = videoPicList;
	}
	public List<SalesPromotionVO> getSalesPromotionList() {
		return salesPromotionList;
	}
	public void setSalesPromotionList(List<SalesPromotionVO> salesPromotionList) {
		this.salesPromotionList = salesPromotionList;
	}
	public String getLevelval() {
		return levelval;
	}
	public void setLevelval(String levelval) {
		this.levelval = levelval;
	}
	public String getRankval() {
		return rankval;
	}
	public void setRankval(String rankval) {
		this.rankval = rankval;
	}
	public String getIspublished() {
		return ispublished;
	}
	public void setIspublished(String ispublished) {
		this.ispublished = ispublished;
	}
	public String getIssupwap() {
		return issupwap;
	}
	public void setIssupwap(String issupwap) {
		this.issupwap = issupwap;
	}
	public String getIssuprms() {
		return issuprms;
	}
	public void setIssuprms(String issuprms) {
		this.issuprms = issuprms;
	}
	public String getIssuph264() {
		return issuph264;
	}
	public void setIssuph264(String issuph264) {
		this.issuph264 = issuph264;
	}
	public String getIssupwww() {
		return issupwww;
	}
	public void setIssupwww(String issupwww) {
		this.issupwww = issupwww;
	}
	public String getIssuprchd() {
		return issuprchd;
	}
	public void setIssuprchd(String issuprchd) {
		this.issuprchd = issuprchd;
	}
	public String getIsupdating() {
		return isupdating;
	}
	public void setIsupdating(String isupdating) {
		this.isupdating = isupdating;
	}
	public String getBc_id() {
		return bc_id;
	}
	public void setBc_id(String bc_id) {
		this.bc_id = bc_id;
	}
	public String getSp_id() {
		return sp_id;
	}
	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}
	public String getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	public String getMediaLevel() {
		return mediaLevel;
	}
	public void setMediaLevel(String mediaLevel) {
		this.mediaLevel = mediaLevel;
	}
	public String getEcid() {
		return ecid;
	}
	public void setEcid(String ecid) {
		this.ecid = ecid;
	}
	public String getCopyRightId() {
		return copyRightId;
	}
	public void setCopyRightId(String copyRightId) {
		this.copyRightId = copyRightId;
	}
	public String getIsSubAlbum() {
		return isSubAlbum;
	}
	public void setIsSubAlbum(String isSubAlbum) {
		this.isSubAlbum = isSubAlbum;
	}
	public String getSubAlbum_IDS() {
		return subAlbum_IDS;
	}
	public void setSubAlbum_IDS(String subAlbum_IDS) {
		this.subAlbum_IDS = subAlbum_IDS;
	}
	public String getAlbum_IDS() {
		return album_IDS;
	}
	public void setAlbum_IDS(String album_IDS) {
		this.album_IDS = album_IDS;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getCopyRightObjectID() {
		return copyRightObjectID;
	}
	public void setCopyRightObjectID(String copyRightObjectID) {
		this.copyRightObjectID = copyRightObjectID;
	}
	public String getN_cpid() {
		return n_cpid;
	}
	public void setN_cpid(String n_cpid) {
		this.n_cpid = n_cpid;
	}
	public String getCopyRightName() {
		return copyRightName;
	}
	public void setCopyRightName(String copyRightName) {
		this.copyRightName = copyRightName;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getIntegrity() {
		return integrity;
	}
	public void setIntegrity(String integrity) {
		this.integrity = integrity;
	}
	public String getScarcity() {
		return scarcity;
	}
	public void setScarcity(String scarcity) {
		this.scarcity = scarcity;
	}
	public String getClarity() {
		return clarity;
	}
	public void setClarity(String clarity) {
		this.clarity = clarity;
	}
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getChain() {
		return chain;
	}
	public void setChain(String chain) {
		this.chain = chain;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getAuthorizationWay() {
		return authorizationWay;
	}
	public void setAuthorizationWay(String authorizationWay) {
		this.authorizationWay = authorizationWay;
	}
	public String getMiguPublish() {
		return miguPublish;
	}
	public void setMiguPublish(String miguPublish) {
		this.miguPublish = miguPublish;
	}
	public String getBcLicense() {
		return bcLicense;
	}
	public void setBcLicense(String bcLicense) {
		this.bcLicense = bcLicense;
	}
	public String getInfluence() {
		return influence;
	}
	public void setInfluence(String influence) {
		this.influence = influence;
	}
	public String getOriPublish() {
		return oriPublish;
	}
	public void setOriPublish(String oriPublish) {
		this.oriPublish = oriPublish;
	}
	public String getCopyRightUDID() {
		return copyRightUDID;
	}
	public void setCopyRightUDID(String copyRightUDID) {
		this.copyRightUDID = copyRightUDID;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getOptimal() {
		return optimal;
	}
	public void setOptimal(String optimal) {
		this.optimal = optimal;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getNeedDRM() {
		return needDRM;
	}
	public void setNeedDRM(String needDRM) {
		this.needDRM = needDRM;
	}
	public List<String> getContentLists() {
		return contentLists;
	}
	public void setContentLists(List<String> contentLists) {
		this.contentLists = contentLists;
	}
	public List<SceneFileVO> getSceneFileList() {
		return sceneFileList;
	}
	public void setSceneFileList(List<SceneFileVO> sceneFileList) {
		this.sceneFileList = sceneFileList;
	}
	public List<DocFileVO> getDocFileList() {
		return docFileList;
	}
	public void setDocFileList(List<DocFileVO> docFileList) {
		this.docFileList = docFileList;
	}
	public String getAssetID() {
		return assetID;
	}
	public void setAssetID(String assetID) {
		this.assetID = assetID;
	}
	public String getCpid() {
		return cpid;
	}
	public void setCpid(String cpid) {
		this.cpid = cpid;
	}
	
}
