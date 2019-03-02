package com.aspire.dotcard.basevideosync.vo;

import java.util.List;

public class ProgramVO {

	/**
     * 主键id
     */
    private String id;
	/**
     * CMS内容ID,节目id
     */
    private String programId;
    /**
     * CMS内容ID,内容编号,10位内容唯一短编码，媒资IDCMS以2开头，MMS以3开头,相当于视频ID
     */
    private String CMSID;
    /**
     * 媒资编号 10位内容唯一短编码（保持与ContentID一致）CMS以2开头，MMS以3开头
     */
    private String assetID;
    /**
     * 合作伙伴编号
     */
    private String cpid;
    /**
     * 节目名称
     */
    private String name;
    /**
     * 节目名称1,预留
     */
    private String name1;
    /**
     * 节目名称2,预留
     */
    private String name2;
    /**
     * 分层信息，标识节目重要度
     */
    private String levelval;
    /**
     * 分级信息，标识节目重要度
     */
    private String rankval;
    /**
     * 创建时间
     */
    private String createtimev;
    /**
     * 更新时间
     */
    private String updatetimev;
    /**
     * 发布时间
     */
    private String publishtimev;
    /**
     * 是否发布 1：发布 0：未发布
     */
    private String ispublished;
    /**
     * 是否WAP 兼容原有门户，默认为1
     */
    private String issupwap;
    /**
     * 是否RMS 兼容原有门户，默认为1
     */
    private String issuprms;
    /**
     * 是否h264 兼容原有门户，默认为1
     */
    private String issuph264;
    /**
     * 是否www 兼容原有门户，默认为1
     */
    private String issupwww;
    /**
     * 是否suprchd 兼容原有门户，默认为1
     */
    private String issuprchd;
    /**
     * 剧集壳更新状态 0.更新完成，1.更新中
     */
    private String isupdating;
    /**
     * 播控方ID
     */
    private String bc_id;
    /**
     * 支撑方ID
     */
    private String sp_id;
    /**
     * 产品包ID
     */
    private String prdpackId;
    /**
     * 产品ID,可以为空，为空时内容价格以产品包配置价格为准
     */
    private String productId;
    /**
     * 发布该内容的CP操作员编号
     */
    private String creatorID;
    /**
     * 版权类型，枚举值：1-强版权；2-弱版权
     */
    private String copyRightType;
    /**
     * 媒体文件清晰度 媒体文件清晰度 枚举值：1-标清（576p）；2-高清（720p）；3-全高清（1080p）
     */
    private String mediaLevel;
    /**
     * 集团客户ID 10位数字字符，记录集团客户ID号
     */
    private String ecid;
    /**
     * 版权编号 与现网保持一致 为了兼容保留
     */
    private String copyRightId;
	/**
     * 内容类别枚举值: 1-视频;2-音频;3-图文;4-弃用;5-弃用；
     */
    private String category;
    /**
     * 服务类型：1-点播；2-直播；3-下载；4-点播+下载；
     * 5-模拟直播 0-浏览 6－内容集（预留值） 7-精简编码 8-精简直播 9-精简模拟直播
     */
    private String type;
    /**
     * 单集所属剧集的内容ID，仅当Formtype为7时有效
     */
    private String serialContentID;
    /**
     * 单集在剧集中的序号，仅当Formtype为7时有效从1开始的连续数字
     */
    private String serialSequence;
    /**
     * 剧集的总集数，仅当Formtype为6时有效
     */
    private String serialCount;
    /**
     * 剧集的总集数，仅当Formtype为6时有效
     */
    private String subSerialIDS;
    /**
     * 是否专辑中的内容，仅当FORMTYPE=8时有效  1：是 空：否
     */
    private String isSubAlbum;
    /**
     * 专辑中内容ID列表,仅当FORMTYPE=9时有效  节目ID，以“，”号分割
     */
    private String subAlbum_IDS;
    /**
     * 内容属于专辑的ID列表，仅当IsSubAlbum=1, FORMTYPE=8时有效  节目ID，以“，”号分割
     */
    private String album_IDS;
    /**
     * 剧集类型：6：剧集（壳）,7：剧集的单集,8：非剧集
     */
    private String formType;
    /**
     * 用户自定义内容标识
     */
    private String uuid;
    /**
     * 内容说明
     */
    private String detail;
    /**
     * 创建时间 格式：YYYYMMDDHHmmSS
     */
    private String createTime;
    /**
     * 最后修改时间 格式：YYYYMMDDHHmmSS
     */
    private String modifyTime;
    /**
     * 视频内容名称
     */
    private String videoName;
    /**
     * 视频内容简称
     */
    private String vShortName;
    /**
     * 视频内容作者
     */
    private String vAuthor;
    /**
     * 支持直播回放属性：0-iphone直播内容,1-不支持直播回放,2-支持直播回放
     * 当内容类型Type为2(直播)时，该字段有效。
     * 支持直播回放功能需要添加的字段，通知OMS
     */
    private String directRecFlag;
    /**
     * 版权对象编号
     */
    private String copyRightObjectID;
    /**
     * 版权归属CP
     */
    private String n_cpid;
    /**
     * 版权名称
     */
    private String copyRightName;
    /**
     * 版权授权日期
     */
    private String beginDate;
    /**
     * 版权到期日期
     */
    private String endDate;
    /**
     * 版权地域范围：可多个，地域之间以“|”隔开
     * 枚举值：1-内地；2-香港；3-澳门；4-台湾；5-海外；6-任何区域
     */
    private String area;
    /**
     * 版权终端：可多个，终端之间以“|”隔开
     * 枚举值：1-全平台；2-手机终端；3-PC；4-IPTV；5-平板；6-数字有线网
     */
    private String terminal;
    /**
     * 版权完整度 枚举值：1-具备完整版权链证明材料；2-仅提供版权链最后一环证明材料；3-仅提供版权免责声明材料
     */
    private String integrity;
    /**
     * 版权稀缺性 枚举值：1-全网独家；2-移动互联网独家；3-非独家
     */
    private String scarcity;
    /**
     * 版权使用方式限制：1-仅播放；2-仅下载；3-不限
     */
    private String way;
    /**
     * 是否首发：0-是；1-否
     */
    private String publish;
    /**
     * 介质清晰度 枚举值：1-标清（576p）；2-高清（720p）；3-全高清（1080p）
     */
    private String clarity;
    /**
     * 媒体支持 可选，可多个，各媒体之间以“|”隔开；枚举值：1-联合新闻发布会；2-周边实物支撑；3-片场探班进棚专访；4-手机视频LOGO媒体支持
     */
    private String support;
    /**
     * 版权授权范围 枚举值：1-品牌；2-全业务
     */
    private String scope;
    /**
     * 版权是否可输出 枚举值：0-是；1-否
     */
    private String output;
    /**
     * 版权链
     */
    private String chain;
    /**
     * 资费类型
     */
    private String feeType;
    /**
     * 授权方式 授权方式	枚举值：1-单片授权；2-集体授权
     */
    private String authorizationWay;
    /**
     * 咪咕发行 枚举值：1-非独家非首发；2-独家或首发
     */
    private String miguPublish;
    /**
     * 播出许可 枚举值：1-非院线非电视台；2-院线或电视台
     */
    private String bcLicense;
    /**
     * 受众影响 枚举值：1-非热播；2-热播
     */
    private String influence;
    /**
     * 原创发行 枚举值：1-工作室直签；2-代理发行3-非原创发行
     */
    private String oriPublish;
    /**
     * 版权自定义标识
     */
    private String copyRightUDID;
    /**
     * 版权文件 可空，版权文件路径，可多个，以“|”隔开
     */
    private String file;
    /**
     * 最优版权标示 枚举值：0-是；1-否
     */
    private String optimal;
    /**
     * 版权分值 根据规则算出分数(长度为三位阿拉伯数字,暂时取默认值000)
     */
    private String score;
    /**
     * 是否需要支持DRM 强版权内容才会存在这个属性，枚举值： 0-是；1-否 (否为默认值)，如果是DRM内容，需要用DRM播放器才可以进行播放
     */
    private String needDRM;
    /**
     * 关键字：将关键字按照关键字优先的规则拼接在一个字段存储，
     * 是关键字就放最前面，不是就放最后面，使用中文分号分隔
     */
    private String keyword;
    /**
     * 该内容的点播文件播放时长
     */
    private String cDuration;
    /**
     * 内容一级分类ID，CMS系统生成
     */
    private String displayType;
    /**
     * 内容一级分类名称
     */
    private String displayName;
    /**
     * 辅助分类信息，字符串形式，由0~n个一级分类名称组成，以“|”隔开
     */
    private String assist;
    /**
     * 节目单文件存放路径，直播节目有效。
     */
    private String playbillFilePath;
    /**
     * 图片访问目录。
     */
    private String displayFileLists;
	/**
     * 最后更新时间
     */
    private String lupdate;
    /**
     * 同步时间
     */
    private String exetime;
    
    private List<LabelsVO> labelsList;
    
    private List<VideosPropertysVO> videosPropertysList;
    
    private List<VideoMediaVO> videoMediaList;
    
    private List<VideoPicVO> videoPicList;
    
    private List<SalesPromotionVO> salesPromotionList;
    /**
     * 内容集关联的内容列表 由0～100内容组成
     */
    private List<String> contentLists;
    /**
     * 场景描述文件，适合与符合内容
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
