--.1.table

create table T_USER
(
  USERID      VARCHAR2(20) not null,
  NAME        VARCHAR2(30),
  SEX         VARCHAR2(1),
  PASSWORD    VARCHAR2(32) not null,
  BIRTHDAY    VARCHAR2(10),
  CERTTYPE    VARCHAR2(2),
  CERTID      VARCHAR2(30),
  COMPANYNAME VARCHAR2(100),
  COMPANYADDR VARCHAR2(160),
  POSTCODE    VARCHAR2(10),
  PHONE       VARCHAR2(40),
  MOBILE      VARCHAR2(16),
  EMAIL       VARCHAR2(80),
  QQ          VARCHAR2(15),
  MSN         VARCHAR2(80),
  STATE       NUMBER(2),
  CHECKDESC   VARCHAR2(250),
  MISC_ID     VARCHAR2(4),   
  constraint T_USER_PK primary key (USERID)
);

create table T_PAGEURI
(
  PAGEURI VARCHAR2(300) not null,
  RIGHTID VARCHAR2(30) not null,
  DESCS   VARCHAR2(200),
  constraint T_PAGEURI_PK primary key (PAGEURI) 
);


create table T_RIGHT
(
  RIGHTID VARCHAR2(30) not null,
  NAME    VARCHAR2(40) not null,
  DESCS   VARCHAR2(200),
  PARENTID VARCHAR2(30),
  LEVELS   NUMBER(1) default 0 not null,
  constraint T_RIGHT_PK primary key (RIGHTID) 
);


create table T_ROLE
(
  ROLEID NUMBER(10) not null,
  NAME   VARCHAR2(40) not null,
  DESCS  VARCHAR2(200) not null,
  Provinces varchar2(20),
  constraint T_ROLE_PK primary key (ROLEID) 
);


create table T_ROLERIGHT
(
  ROLEID  NUMBER(10) not null,
  RIGHTID VARCHAR2(30) not null,
  constraint T_ROLERIGHT_PK primary key (ROLEID,RIGHTID)
);


create table T_ROLESITERIGHT
(
  ROLEID NUMBER(10) not null,
  NODEID VARCHAR2(30) not null,
  constraint T_ROLESITERIGHT_PK primary key (ROLEID,NODEID)
);

create table T_USERROLE
(
  USERID VARCHAR2(20) not null,
  ROLEID NUMBER(10) not null,
  constraint T_USERROLE_PK primary key (USERID,ROLEID)
);

create table T_ACTIONLOG
(
  LOGID        NUMBER(10) not null,
  ACTIONTIME   VARCHAR2(14) not null,
  USERID       VARCHAR2(20) not null,
  USERNAME     VARCHAR2(30),
  ROLES        VARCHAR2(100),
  IP           VARCHAR2(24) not null,
  ACTIONTYPE   VARCHAR2(40) not null,
  ACTIONTARGET VARCHAR2(300),
  ACTIONRESULT NUMBER(1) not null,
  ACTIONDESC   VARCHAR2(200) ,
  constraint T_ACTIONLOG_PK primary key (LOGID) 
);

create table t_resource
(
  resourceKey varchar2(60),
  resourceValue varchar2(1024),
  remard varchar2(200),
  primary key(resourceKey)
); 

--资源管理_基本信息表
create table T_R_Base 
(
 ID varchar2(30) not null,
 parentID varchar2(30) not null,
 path varchar2(300) not null,
 type varchar2(30) not null
); 
create index INDEX1_T_R_BASE on T_R_BASE (PARENTID);
alter table T_R_BASE add constraint  pk_t_r_base primary key(id);
--资源管理_分类信息表
create table T_R_Category 
(
 ID varchar2(30) not null,
 name varchar2(100) not null,
 descs varchar2(500),
 sortID number(3,0) not null,
 ctype number(2,0) not null,
 CATEGORYID VARCHAR2(20),
 DELFLAG NUMBER(2),
 CHANGEDATE VARCHAR2(30),
 STATE NUMBER(2),
 PARENTCATEGORYID VARCHAR2(20),
 RELATION VARCHAR2(20),
 picurl varchar2(256),
 statistic number(11)
);
comment on column T_R_Category.RELATION  is '关联门店 W:WWW门店 O:终端门店 P:PC门店 注意可以多选。存贮举例：W,P（表示关联WWW和PC门店）';
comment on column T_R_CATEGORY.picurl is '货架预览图';
comment on column T_R_CATEGORY.statistic is '货架统计信息';
alter table t_r_category add constraint  pk_t_r_category primary key(id);
create  index IDX_T_R_CATEGORY_CATEGORYID on T_R_CATEGORY (CATEGORYID);
create  index IDX_T_R_CATEGORY_PCATEGORYID on T_R_CATEGORY (PARENTCATEGORYID);

--资源管理_引用扩展信息表
create table T_R_Reference
(
 ID varchar2(30) not null,
 refNodeID varchar2(30) not null,
 sortID number(8,0),
 goodsID VARCHAR2(50),
 categoryID VARCHAR2(20),
 loaddate VARCHAR2(30),
 variation number(5,0)
); 
comment on column T_R_REFERENCE.variation
  is '排序id的变更。+表示上升，-表示下降。99999 表示新增';
alter table t_r_reference  add constraint  pk_t_r_reference primary key(id);
create index IDX_T_R_REFERENCE_CATEGORYID on T_R_REFERENCE (CATEGORYID);
create unique index INDX_T_R_REF_GOODSID on T_R_REFERENCE (GOODSID);
create index IND_T_R_REFERENCE_REFNODEID on T_R_REFERENCE (REFNODEID);
create unique index IND_T_R_REFERENCE_ID on T_R_REFERENCE (ID);
                          
--创建内容表
create table t_r_gcontent
(
  id                   VARCHAR2(30) not null,
  name                 VARCHAR2(300),
  cateName             VARCHAR2(60),
  appCateName          VARCHAR2(200),
  appCateID            VARCHAR2(7),
  spName               VARCHAR2(100),
  icpCode              VARCHAR2(20),
  icpServId            VARCHAR2(30),
  contentTag           VARCHAR2(50),
  singer               VARCHAR2(200),
  price                VARCHAR2(10),
  expire               VARCHAR2(20),
  auditionUrl          VARCHAR2(400),
  introduction         varchar2(4000),
  NAMELETTER           VARCHAR2(10),
  SINGERLETTER         VARCHAR2(10),
  DOWNLOADTIMES        NUMBER(10),
  SETTIMES             NUMBER(10),
  BIGCATENAME          VARCHAR2(30),
  ContentID	           VARCHAR2(30),
  companyID	           VARCHAR2(20),
  productID	           VARCHAR2(30),
  keywords	           VARCHAR2(3000),
  createDate           VARCHAR2(30),
  marketDate           VARCHAR2(30),
  lupddate             VARCHAR2(30),
  LANGUAGE	           VARCHAR2(1),
  WWWPROPAPICTURE1     Varchar2(256),
  WWWPROPAPICTURE2     Varchar2(256),
  WWWPROPAPICTURE3     Varchar2(256),
  CLIENTPREVIEWPICTURE1 Varchar2(256),
  CLIENTPREVIEWPICTURE2 Varchar2(256),
  CLIENTPREVIEWPICTURE3 Varchar2(256),
  CLIENTPREVIEWPICTURE4 Varchar2(256),
  PROVIDER	           varchar2(200),
  HandBook             varchar2(4000), 
  Manual               Varchar2(256),
  HandBookPicture      Varchar2(256),
  UserGuide            varchar2(4000),
  UserGuidePicture     Varchar2(256),
  GameVideo            Varchar2(256),
  LOGO1		             varchar2(256),
  LOGO2		             varchar2(256),
  LOGO3		             varchar2(256),
  LOGO4		             varchar2(256),
  CartoonPicture	     Varchar2(256),
  devicename	         varchar2(4000),
  devicename02	       varchar2(4000),
  devicename03	       varchar2(4000),
  devicename04	       varchar2(4000),
  devicename05	       varchar2(4000),
  devicename06	       varchar2(4000),
  devicename07	       varchar2(4000),
  devicename08	       varchar2(4000),
  devicename09	       varchar2(4000),
  devicename10	       varchar2(4000),
  devicename11	       varchar2(4000),
  devicename12	       varchar2(4000),
  devicename13	       varchar2(4000),
  devicename14	       varchar2(4000),
  devicename15	       varchar2(4000),
  devicename16	       varchar2(4000),
  devicename17	       varchar2(4000),
  devicename18	       varchar2(4000),
  devicename19	       varchar2(4000),
  devicename20	       varchar2(4000),
  daySearchTimes       number(15) default 0,
  weekSearchTimes      number(15) default 0,
  monthSearchTimes     number(15) default 0,
  searchTimes          number(15) default 0,
  dayScanTimes         number(15) default 0,
  weekScanTimes        number(15) default 0,
  monthScanTimes       number(15) default 0,
  scanTimes            number(15) default 0,
  dayOrderTimes        number(15) default 0,
  weekOrderTimes       number(15) default 0,
  monthOrderTimes      number(15) default 0,
  orderTimes           number(15) default 0,
  dayCommentTimes      number(15) default 0,
  weekCommentTimes     number(15) default 0,
  monthCommentTimes    number(15) default 0,
  commentTimes         number(15) default 0,
  dayMarkTimes         number(15) default 0,
  weekMarkTimes        number(15) default 0,
  monthMarkTimes       number(15) default 0,
  markTimes            number(15) default 0,
  dayCommendTimes      number(15) default 0,
  weekCommendTimes     number(15) default 0,
  monthCommendTimes    number(15) default 0,
  commendTimes         number(15) default 0,
  dayCollectTimes      number(15) default 0,
  weekCollectTimes     number(15) default 0,
  monthCollectTimes    number(15) default 0,
  collectTimes         number(15) default 0,
  averageMark          number(15) default 0,
  isSupportDotcard     varchar2(2),
  programsize          number(8),
  programID            Varchar2(12),
  onlinetype           number(1),
  version              varchar2(30),
  picture1             Varchar2(256),
  picture2             Varchar2(256),
  picture3             Varchar2(256),
  picture4             Varchar2(256),
  picture5             Varchar2(256),
  picture6             Varchar2(256),
  picture7             Varchar2(256),
  picture8             Varchar2(256),
  platform             Varchar2(200),
  chargeTime           Varchar2(1),
  SERVATTR             VARCHAR2(1),
  subtype              varchar2(2) default 0,
  pvcid                 VARCHAR2(10),
  cityid                VARCHAR2(20)
);
alter table T_R_GCONTENT add constraint  pk_t_r_gcontent primary key(id); 
create index INDEX_GCONTENT_CONTENTID on T_R_GCONTENT (CONTENTID);
create index IND_T_R_GCONTENT on T_R_GCONTENT (ICPCODE, ICPSERVID);
comment on column t_r_gcontent.ID is '内容ID，对应资讯内容以dcmp开头，对应彩铃以clr开头，对应全曲以a8开头。';
comment on column t_r_gcontent.NAME is '内容名称，对应资讯内容的媒体名称';
comment on column t_r_gcontent.CATENAME is '业务分类名称，对应全曲的歌曲风格流派分类。';
comment on column t_r_gcontent.appCateName is '内容在产品供应管理系统中的应用分类名称，对应全曲的歌曲语言分类。';
comment on column t_r_gcontent.appCateID is '内容在产品供应管理系统中的应用分类ID';
comment on column t_r_gcontent.SPNAME is '企业名称';
comment on column t_r_gcontent.ICPCODE is '企业代码';
comment on column t_r_gcontent.ICPSERVID is '业务代码';
comment on column t_r_gcontent.CONTENTTAG is '内容外码，外部系统中资源的唯一标识，对应资讯头条id';
comment on column t_r_gcontent.SINGER is '歌手';
comment on column t_r_gcontent.PRICE is '价格，以分为单位';
comment on column t_r_gcontent.EXPIRE is '有效期（适用于彩铃，格式为YYYYMMDD）';
comment on column t_r_gcontent.AUDITIONURL is '彩铃试听地址，对应资讯的媒体链接url，对应全曲的aac播放HTTP URL';
comment on column t_r_gcontent.INTRODUCTION is '介绍，对应资讯的头条内容正文';
comment on column t_r_gcontent.NAMELETTER is '铃音名称检索首字母';
comment on column t_r_gcontent.SINGERLETTER is '歌手名称检索首字母';
comment on column t_r_gcontent.DOWNLOADTIMES is '彩铃下载次数';
comment on column t_r_gcontent.SETTIMES is '彩铃设置次数';
comment on column t_r_gcontent.BIGCATENAME is '彩铃大类名称，对应全曲歌手地区分类';
comment on column t_r_gcontent.CONTENTID is '内容内码，对应彩铃的铃音编码，对应资讯的媒体ID，对应全曲的歌曲ID。';
comment on column t_r_gcontent.COMPANYID is '企业内码';
comment on column t_r_gcontent.PRODUCTID is '业务内码';
comment on column t_r_gcontent.KEYWORDS is '内容标签';
comment on column t_r_gcontent.CREATEDATE is '内容创建日期';
comment on column t_r_gcontent.marketDate is '内容上线时间';
comment on column t_r_gcontent.lupddate is '内容最后更新时间';
comment on column t_r_gcontent.LANGUAGE is '软件支持的语言，1:简体中文、2:繁体中文 3:英文 0:其它；';
comment on column t_r_gcontent.WWWPROPAPICTURE1 is 'WWW大广告图地址，首页广告图';
comment on column t_r_gcontent.WWWPROPAPICTURE2 is 'WWW列表小图标地址，首页展示及列表页展示';
comment on column t_r_gcontent.WWWPROPAPICTURE3 is 'WWW标准展示图3地址，商品详细页';
comment on column t_r_gcontent.ClientPreviewPicture1 is '终端预览图1地址，图片规格：150x160';
comment on column t_r_gcontent.ClientPreviewPicture2 is '终端预览图2地址，图片规格：180x180';
comment on column t_r_gcontent.ClientPreviewPicture3 is '终端预览图3地址，图片规格：210x220';
comment on column t_r_gcontent.ClientPreviewPicture4 is '终端预览图4地址，图片规格：240x300';
comment on column t_r_gcontent.PROVIDER is '内容提供者';
comment on column t_r_gcontent.HandBook is '操作指南，对应资讯的头条标题';
comment on column t_r_gcontent.MANUAL is '使用手册地址，对应资讯的头条内容链接，对应全曲歌词lrcURL';
comment on column t_r_gcontent.HandBookPicture is '操作指南图片地址';
comment on column t_r_gcontent.UserGuide is '功能说明';
comment on column t_r_gcontent.UserGuidePicture is '攻略说明图片地址，对应资讯的头条图片url';
comment on column t_r_gcontent.GameVideo is '游戏视频地址';
comment on column t_r_gcontent.Logo1 is '终端展示LOGO1地址，图片规格：30x30';
comment on column t_r_gcontent.Logo2 is '终端展示LOGO2地址，图片规格：34x34';
comment on column t_r_gcontent.Logo3 is '终端展示LOGO3地址，图片规格：50x50';
comment on column t_r_gcontent.Logo4 is '终端展示LOGO4地址，图片规格：65x65';
comment on column t_r_gcontent.CartoonPicture is '软件动画截图地址，对应资讯的媒体图标url，对应彩铃终端试听地址clientAuditionUrl，对应全曲mp3播放HTTP URL';
comment on column t_r_gcontent.DEVICENAME is '内容适用手机型号，以{}作为边界符，以逗号分隔';
comment on column t_r_gcontent.daysearchtimes is '上日搜索次数';
comment on column t_r_gcontent.weeksearchtimes is '上周搜索次数';
comment on column t_r_gcontent.monthsearchtimes is '上月搜索次数';
comment on column t_r_gcontent.searchtimes is '累计搜索次数';
comment on column t_r_gcontent.dayscantimes is '上日浏览次数';
comment on column t_r_gcontent.weekscantimes is '上周浏览次数';
comment on column t_r_gcontent.monthscantimes is '上月浏览次数';
comment on column t_r_gcontent.scantimes is '累计浏览次数';
comment on column t_r_gcontent.dayordertimes is '上日订购次数';
comment on column t_r_gcontent.weekordertimes is '上周订购次数';
comment on column t_r_gcontent.monthordertimes is '上月订购次数';
comment on column t_r_gcontent.ordertimes is '累计订购次数';
comment on column t_r_gcontent.daycommenttimes is '上日评论次数';
comment on column t_r_gcontent.weekcommenttimes is '上周评论次数';
comment on column t_r_gcontent.monthcommenttimes is '上月评论次数';
comment on column t_r_gcontent.commenttimes is '累计评论次数';
comment on column t_r_gcontent.daymarktimes is '上日评分次数';
comment on column t_r_gcontent.weekmarktimes is '上周评分次数';
comment on column t_r_gcontent.monthmarktimes is '上月评分次数';
comment on column t_r_gcontent.marktimes is '累计评分次数';
comment on column t_r_gcontent.daycommendtimes is '上日推荐次数';
comment on column t_r_gcontent.weekcommendtimes is '上周推荐次数';
comment on column t_r_gcontent.monthcommendtimes is '上月推荐次数';
comment on column t_r_gcontent.commendtimes is '累计推荐次数';
comment on column t_r_gcontent.daycollecttimes is '上日收藏次数';
comment on column t_r_gcontent.weekcollecttimes is '上周收藏次数';
comment on column t_r_gcontent.monthcollecttimes is '上月收藏次数';
comment on column t_r_gcontent.collecttimes is '累计收藏次数';
comment on column t_r_gcontent.averagemark is '内容的加权平均分，对应彩铃、全曲的播放时长（毫秒）';
comment on column t_r_gcontent.isSupportDotcard is '是否支持点卡支付，0：不支持 1：支持';
comment on column t_r_gcontent.programsize is '程序安装包大小，单位为K';
comment on column t_r_gcontent.programID is '程序ID';
comment on column t_r_gcontent.onlinetype is '应用种类，1：离线应用，2：在线应用';
comment on column t_r_gcontent.version is '软件应用版本号';
comment on column t_r_gcontent.picture1 is 'WWW详细大图，软件使用截图1';
comment on column t_r_gcontent.picture2 is 'WWW详细大图，软件使用截图2';
comment on column t_r_gcontent.picture3 is 'WWW详细大图，软件使用截图3';
comment on column t_r_gcontent.picture4 is 'WWW详细大图，软件使用截图4';
comment on column t_r_gcontent.picture5 is 'WWW详细大图，软件使用截图5';
comment on column t_r_gcontent.picture6 is 'WWW详细大图，软件使用截图6';
comment on column t_r_gcontent.picture7 is 'WWW详细大图，软件使用截图7';
comment on column t_r_gcontent.picture8 is 'WWW详细大图，软件使用截图8';
comment on column t_r_gcontent.platform is '程序包的平台类型，取值包括kjava，mobile，symbian等。以{}作为边界符，以逗号分隔';
comment on column t_r_gcontent.chargeTime is '计费时机 1：下载时计费 2：体验后计费';
comment on column T_R_GCONTENT.subtype is '1表示mm普通应用,2表示widget应用,0表示无意义';
comment on column T_R_GCONTENT.pvcid  is '应用归属省的id， 0000表示全国';
comment on column T_R_GCONTENT.cityid  is '应用归属市的id， 0000表示pvcid所指定全省业务';

--上次启动任务时间表
create table t_lastsynctime
(
  Lasttime    date not null
);
 
--同步内容临时表
create table t_synctime_tmp
(
  ID    NUMBER(10) not null,
  contentid  VARCHAR2(12) not null,
  name  VARCHAR2(300) not null,
  status  VARCHAR2(4),
  contentType  VARCHAR2(32),
  LUPDDate date,
  constraint KEY_synctime_tmp primary key (id)
);

--创建商品历史信息表
Create table t_goods_his
(
  id          VARCHAR2(30),
  goodsID		  VARCHAR2(50),
  icpcode			VARCHAR2(20),
  icpservid		VARCHAR2(30),
  contentid		VARCHAR2(30),
  categoryID	VARCHAR2(20),
  goodsName		VARCHAR2(100),
  state 			NUMBER(2),
  changeDate 	DATE,
  actionType 	NUMBER(2),
  lastState 	NUMBER(2),
  constraint KEY_t_goods_his primary key (id)
);
create index index_goods_his on t_goods_his(contentid);
--CMS内容同步上架策略表
create table t_sync_tactic
(
  ID           NUMBER(10,0) not null, --数据库序列
  categoryID   VARCHAR2(20) not null, --内容货架分类ID
  contentType  VARCHAR2(30) not null, --内容类型
  umFlag       VARCHAR2(1) not null,  --业务通道  W:WAP，S:SMS，M:MMS，E:WWW，A:CMNET。
  contentTag   VARCHAR2(200), --内容标签
  tagRelation  NUMBER(2,0),   --内容标签逻辑关系 0=空;  1=and;  2=or
  appCateName  VARCHAR2(40), --应用分类名称
  crateTime    varchar2(20),  --创建时间
  lastUpdateTime varchar2(20), --最后修改时间
  constraint pk_t_sync_tactic primary key(ID)
);

--内容导入记录表
create table t_report_import_date 
(
 type number(8,0) primary key,
 lastdate varchar2(12),
 description varchar2(100)
);

--版本信息表
create table DBVERSION
(
  DBSEQ         NUMBER(6),
  DBVERSION     VARCHAR2(20) not null,
  LASTDBVERSION VARCHAR2(100),
  PATCHVERSION  VARCHAR2(100) not null,
  PATCHDATE     DATE default SYSDATE not null,
  DESCRIPTION   VARCHAR2(200),
  primary key (PATCHVERSION)
);
create unique index IDX_UNIQUE_DBVERSION on DBVERSION (LASTDBVERSION, PATCHVERSION);

--生成货架规则策略表
create table T_CATEGORY_RULE
(
  CID            VARCHAR2(30) not null,
  RULEID         NUMBER(8) not null,
  LASTEXCUTETIME DATE,
  EFFECTIVETIME  DATE default sysdate
);
comment on table T_CATEGORY_RULE
  is '货架策略规则表，保存货架的相关规则';
-- Add comments to the columns 
comment on column T_CATEGORY_RULE.CID
  is '规则对应的货架的货架内码';
comment on column T_CATEGORY_RULE.RULEID
  is '货架对应的规则ID';
comment on column T_CATEGORY_RULE.LASTEXCUTETIME
  is '上次执行时间，需要精确到秒';
comment on column T_CATEGORY_RULE.EFFECTIVETIME
  is '规则生效时间';
alter table T_CATEGORY_RULE
  add constraint PK_T_CATEGORY_RULE primary key (CID);

  create table T_CATERULE
(
  RULEID         NUMBER(8) not null,
  RULENAME       VARCHAR2(30),
  RULETYPE       NUMBER(1) default 0 not null,
  INTERVALTYPE   NUMBER(2) default 0 not null,
  EXCUTEINTERVAL NUMBER(5) not null,
  EXCUTETIME     NUMBER(5),
  randomfactor number(3)	 
);
comment on table T_CATERULE
  is '策略规则表';
-- Add comments to the columns 
comment on column T_CATERULE.RULEID
  is '规则唯一的ID';
comment on column T_CATERULE.RULENAME
  is '规则名称';
comment on column T_CATERULE.RULETYPE
  is '规则类型 0：刷新货架下商品；1：货架下商品重排顺序
；5：处理基地图书运营推荐图书。6:处理运营三级货架类型';
comment on column T_CATERULE.INTERVALTYPE
  is '执行时间间隔类型 0：天；1：周；2：月
';
comment on column T_CATERULE.EXCUTEINTERVAL
  is '执行之间间隔，单位为天';
comment on column T_CATERULE.EXCUTETIME
  is '在一个时间间隔之内的执行日子。
如果IntervalType=0，本字段无效。
如果IntervalType=1，本字段表示在周几执行。
如果IntervalType=2，本字段表示在月的第几天执行。';
comment on column T_CATERULE.randomfactor
  is '随机上架因子。产品库上架前是否需要随即排序。0不随机，100大随即，1~99 机型货架小随机';

alter table T_CATERULE
  add constraint PK_T_CATERULE primary key (RULEID);

create table T_CATERULE_COND
(
  id           NUMBER(10) not null,
  RULEID   NUMBER(8) not null,
  CID      VARCHAR2(30),
  CONDTYPE NUMBER(2) default 0 not null,
  WSQL     VARCHAR2(1000),
  OSQL     VARCHAR2(1000),
  COUNT    NUMBER(8) default -1,
  SORTID   NUMBER(8) default 1
);
comment on table T_CATERULE_COND
  is '策略规则条件表';
-- Add comments to the columns 
comment on column T_CATERULE_COND.id
  is '条件唯一的ID';
comment on column T_CATERULE_COND.RULEID
  is '规则唯一的ID';
comment on column T_CATERULE_COND.CID
  is '获取货架的货架内码.空则本字段无效';
comment on column T_CATERULE_COND.CONDTYPE
  is '条件类型 10：从产品库获取自有业务（软件，游戏，主题）不包含基地游戏；11：从产品库中取基地游戏业务；12：从产品库获取非自有业务；1：从精品库获取。';
comment on column T_CATERULE_COND.WSQL
  is '获取的条件sql';
comment on column T_CATERULE_COND.OSQL
  is '获取的排序sql';
comment on column T_CATERULE_COND.COUNT
  is '获取的商品数量';
comment on column T_CATERULE_COND.SORTID
  is '排序次序，是指一个规则对应条件的之间的执行次序';
-- Create/Recreate indexes 
create index IDX_COND_RULEID on T_CATERULE_COND (RULEID);

create table v_cm_device_resource as
select *
  from ppms_CM_DEVICE_RESOURCE p
 where exists
 (select 1 from t_r_gcontent g where g.contentid = p.contentid);

--.2.constraint


--.3.view
--创建CMS的内容视图
create or replace view ppms_v_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       c.ContentCode,
       c.Keywords,
       decode(c.status,
              '0006',
              decode(f.status, 2, '0006', 5, '0008'),
              '1006',
              decode(f.status, 2, '0006', 5, '0008'),
              '0015',
              decode(f.status||f.substatus, '61', '0006','0008'),
              '1015',
              decode(f.status||f.substatus, '61', '0006','0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L',d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,--全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as lupddate,
       f.chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' or  f.status=5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype in ('1','2')--增加widget应用
   ;

create table v_cm_content as select * from ppms_v_cm_content;

-- Create table
create table T_GAME_SERVICE
(
  CONTENTID VARCHAR2(30),
  ICPCODE     VARCHAR2(16),
  SPNAME      VARCHAR2(200) not null,
  ICPSERVID   VARCHAR2(16) not null,
  SERVNAME    VARCHAR2(50) not null,
  SERVDESC    VARCHAR2(200),
  CHARGETYPE  VARCHAR2(2),
  CHARGEDESC  VARCHAR2(200),
  MOBILEPRICE NUMBER(8),
  LUPDDATE    DATE not null,
  SERVTYPE    NUMBER(2) not null,
  SERVFLAG    NUMBER(1),
  PTYPEID     NUMBER(2) not null
);
-- Add comments to the columns 
comment on column T_GAME_SERVICE.CONTENTID
  is '内容id';
comment on column T_GAME_SERVICE.ICPCODE
  is 'CP的合作代码';
comment on column T_GAME_SERVICE.SPNAME
  is 'CP名称';
comment on column T_GAME_SERVICE.ICPSERVID
  is '产品的业务代码';
comment on column T_GAME_SERVICE.SERVNAME
  is '产品名称';
comment on column T_GAME_SERVICE.SERVDESC
  is '产品简介';
comment on column T_GAME_SERVICE.CHARGETYPE
  is '计费类型

01 －免费
02 －按次
03 －包月
04 －包天
05 －包次
06 －首次使用包月
07－按内容计费
08－按次对内容计费
';
comment on column T_GAME_SERVICE.CHARGEDESC
  is '计费描述';
comment on column T_GAME_SERVICE.MOBILEPRICE
  is '资费，单位厘';
comment on column T_GAME_SERVICE.LUPDDATE
  is '业务最后更新时间';
comment on column T_GAME_SERVICE.SERVTYPE
  is '业务类型。1:客户端单机,2:客户端网游,3:WAP网游,4:WAP单机
只给1客户端单机的。
';
comment on column T_GAME_SERVICE.SERVFLAG
  is '业务标识。0:普通业务,1:套餐内业务';
comment on column T_GAME_SERVICE.PTYPEID
  is '业务推广方式。1	话费购买客户端单机游戏
2	客户端网游，点数购买道具
3	点数按次购买WAP游戏
4	免费WAP游戏
5	WAP网游，点数购买道具
6	试玩转激活，话费购买客户端单机游戏
7	渠道版试玩转激活，话费购买客户端单机游戏
8	渠道版，话费购买客户端单机游戏
9	话费按次购买WAP游戏
10	短信计费网游
11	试玩转激活，短信计费单机游戏
12	渠道折扣版，话费购买客户端单机游戏
13	试玩转激活渠道版，短信计费单机游戏
14	5元包月套餐内免费客户端单机游戏
15	5元包月套餐内打折客户端单机游戏
16	TD业务，话费购买客户端单机游戏
';

-- Create/Recreate indexes 
create unique index INDEXT_T_GAME_SERVICE_KEY on T_GAME_SERVICE (ICPCODE, ICPSERVID);

----创建ppms_v_service 视图 添加基地游戏表业务数据关联
create or replace view ppms_v_service as
select p.contentid,
       v1.apcode as icpcode,
       v1.CompanyName as spname,
       v1.ShortName as spshortname,
       v2.ServiceCode as icpservid,
       v2.ProductName as servname,
       decode(v2.ProductStatus, '2', 'A', '3', 'B', '4', 'P', '5', 'E') as SERVSTATUS,
       decode(v2.ACCESSMODEID,
              '00',
              'S',
              '01',
              'W',
              '02',
              'M',
              '10',
              'A',
              '05',
              'E') as umflag,
       decode(v2.ServiceType, 1, 8, 2, 9) as servtype,
       v2.ChargeType as ChargeType,
       v2.paytype,
       v2.Fee as mobileprice,
       V2.PayMode_card as dotcardprice,
       decode(p.chargetime || v2.paytype, '20', p.feedesc, v2.chargedesc) as chargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from v_valid_company    v1,
       v_om_product       v2,
       OM_PRODUCT_CONTENT p,
       cm_content         c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id
   and c.thirdapptype in ('1', '2')
  UNION ALL
 select
       t.contentid,
       t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--上线计费
       null,
       8,
       t.chargetype,
       null,
       t.mobileprice,
       null,
       t.chargedesc,
       'B',
       'G',--全网业务
       t.servdesc,
       null,
       t.lupddate
  from t_game_service t
  ;



create materialized view V_THIRD_SERVICE
refresh force on demand
as
select v1.apcode as icpcode,
       v1.CompanyName as spname,
       v1.ShortName as spshortname,
       v2.ServiceCode as icpservid,
       v2.ProductName as servname,
       decode(v2.ProductStatus, '2', 'A', '3', 'B', '4', 'P', '5', 'E') as SERVSTATUS,
       decode(v2.ACCESSMODEID,
              '00',
              'S',
              '01',
              'W',
              '02',
              'M',
              '10',
              'A',
              '05',
              'E') as umflag,
       decode(v2.ServiceType, 1, 8, 2, 9) as servtype,
       v2.ChargeType as ChargeType,
       v2.Fee as mobileprice,
       V2.PayMode_card as dotcardprice,
       v2.ChargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from v_valid_company v1, v_om_product v2
 where v1.companyid = v2.CompanyID
   and v1.icptype = 1
   and v2.ProviderType = 'V';
   
   

--上架商品信息视图
create or replace view vr_goods
(goodsid, spcode, servicecode, spid, serviceid, contentid, categoryid, goodsname,umflag)
as
select t1.goodsid,t2.icpcode,t2.icpservid,t2.companyid,t2.productid,t2.contentid,t1.categoryid,t2.name, s.umflag  
from t_r_reference t1 , t_r_gcontent t2 LEFT OUTER JOIN  v_service s on(t2.icpcode= s.icpcode and t2.icpservid=s.icpservid)
where t2.id = t1.refnodeid and ascii(substr(t2.id,1,1))>47 and ascii(substr(t2.id,1,1))<58;

--货架信息视图
 create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
  select categoryID,name,parentCategoryID,decode(delflag,1,9,0,1) state,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation
from t_r_category
where id not in('701','702');     

--历史商品信息视图
create or replace view vr_goods_his 
(id,goodsid,spCode,serviceCode,spID,serviceID,contentid,categoryid,goodsName,state,changeDate,actionType,lastState)
as 
select to_number(t1.id),t1.goodsid,t1.icpcode,t1.icpservid,t2.companyid,t2.productid,t1.contentid,t1.categoryid,t1.goodsName,t1.state,t1.changeDate,t1.actionType,t1.lastState 
from t_goods_his t1,t_r_gcontent t2
where t1.contentid = t2.contentid;

---a8音乐商品信息表
create or replace view vr_a8_goods
(goodsid, contentid, categoryid, goodsname,singer,singerzone)
as
select t1.goodsid,
       t2.contentid,
       t1.categoryid,
       t2.name,
       t2.singer,
       t2.bigcatename
  from t_r_reference t1, t_r_gcontent t2
 where t2.id = t1.refnodeid
   and t2.id like 'a8%';

-- Create  T_SINGER table
create table T_SINGER
(
  ID          VARCHAR2(25) not null,
  NAME        VARCHAR2(50) not null,
  REGION      VARCHAR2(40),
  TYPE        VARCHAR2(40),
  FIRSTLETTER VARCHAR2(50),
  primary key (ID)
);
-- Add comments to the columns 
comment on column T_SINGER.ID
  is '歌手ID';
comment on column T_SINGER.NAME
  is '	歌手名称';
comment on column T_SINGER.REGION
  is '歌手地区';
comment on column T_SINGER.TYPE
  is '歌手类型';
comment on column T_SINGER.FIRSTLETTER
  is '	歌手首字母';
-- Create/Recreate indexes 
create index T_SINGER_INITIAL on T_SINGER (FIRSTLETTER);
create index T_SINGER_REGIN on T_SINGER (REGION);
create index T_SINGER_TYPE on T_SINGER (TYPE);




create table t_bookcate_mapping
(
  bookCateid varchar2(25) not null,
  id         varchar2(30) not null,
  primary key (bookCateid)
)
;
-- Add comments to the columns 
comment on column t_bookcate_mapping.bookCateid
  is '基地图书分类ID';
comment on column t_bookcate_mapping.id
  is '对应货架分类id';




create table t_book_commend
(
  ID      VARCHAR2(30) not null,
  YBOOKID VARCHAR2(30),
  JBOOKID VARCHAR2(30)
)
;
-- Add comments to the columns 
comment on column T_BOOK_COMMEND.ID
  is '图书货架分类id';
comment on column T_BOOK_COMMEND.YBOOKID
  is '运营推荐图书id';
comment on column T_BOOK_COMMEND.JBOOKID
  is '基地图书id';
-- Create/Recreate indexes 
create index t_book_commend_id on t_book_commend (ID);

-- Create table
create table T_BOOK_AUTHOR
(
  AUTHORID   VARCHAR2(25) not null,
  AUTHORNAME VARCHAR2(50),
  AUTHORDESC VARCHAR2(1024),
  primary key (AUTHORID)
);
-- Add comments to the columns 
comment on column T_BOOK_AUTHOR.AUTHORID
  is '作者id';
comment on column T_BOOK_AUTHOR.AUTHORNAME
  is '作者姓名';
comment on column T_BOOK_AUTHOR.AUTHORDESC
  is '作者描述';
create index T_BOOK_AUTHOR_NAME on T_BOOK_AUTHOR (AUTHORNAME);
--.4.sequence

----增加动漫支持平台表

-- Create table
create table T_COMIC_PLATFORM
(
  PLATFORMID VARCHAR2(20),
  PLATFORM   VARCHAR2(20)
);
comment on column T_COMIC_PLATFORM.PLATFORMID  is '支持平台ID,取值情况，100，101，102，200，300，400';
comment on column T_COMIC_PLATFORM.PLATFORM    is '支持平台描述取值情况S602nd, S603rd, S605th , WM , Kjava ,OMS';

--新增需要定时导出货架商品的货架表
-- Create table
create table T_CATEGORY_EXPORT
(
  CID VARCHAR2(30) not null
);
comment on column T_CATEGORY_EXPORT.CID  is '需要定时导出货架商品的货架ID';

-- Create table T_GAME_CATE_MAPPING
create table T_GAME_CATE_MAPPING
(
  BASECATENAME VARCHAR2(100) not null,
  MMCATENAME   VARCHAR2(100)
);
-- Add comments to the columns 
comment on column T_GAME_CATE_MAPPING.BASECATENAME
  is '基地分类名称';
comment on column T_GAME_CATE_MAPPING.MMCATENAME
  is 'MM分类名称';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_CATE_MAPPING
  add primary key (BASECATENAME);

-- Create table
create table T_GAME_UA_MAPPING
(
  BASEUA     VARCHAR2(500) not null,
  DEVICENAME VARCHAR2(500)
);
-- Add comments to the columns 
comment on column T_GAME_UA_MAPPING.BASEUA
  is '基地UA信息';
comment on column T_GAME_UA_MAPPING.DEVICENAME
  is 'MM对应基地UA的devicename';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_UA_MAPPING
  add primary key (BASEUA);
 

 create table T_CONTENT_COUNT
(
  CONTENTID      NVARCHAR2(30) not null,
  LATESTCOUNT    NUMBER default 0 not null,
  RECOMMENDCOUNT NUMBER default 0 not null,
  COUNTTIME      NVARCHAR2(30),
  COMPECOUNT     NUMBER default 0 not null
);

---创建同步 模糊适配信息给门户的 视图090patch
create or replace view v_match_device_resource 
(device_id, device_name, contentid, contentname, match)
as
select t.device_id,t.device_name,t.contentid,t.contentname,decode(match,1,1,2,2,3,2) match from v_cm_device_resource t;



--修改提供给报表的视图 vr_category
 create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
  select categoryID,name,parentCategoryID,decode(delflag,1,9,0,1) state,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation
from t_r_category
where id not in('701','702');     

-----修改了T_R_CATEGORY字段含义

comment on column T_R_CATEGORY.STATE is '货架是否在门户展示，1，展示；0，不展示';


--修改提供给报表的视图 vr_category
 create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
  select categoryID,name,parentCategoryID,decode(delflag,1,9,0,1) state,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation
from t_r_category
where id not in('701','702');     

-----创建ZCOM_ID自动生成序列
-- Create sequence 
create sequence SEQ_ZCOM_ID
minvalue 1
maxvalue 99999999
start with 1087
increment by 1
nocache
cycle;


-- 创建Zcom内容临时表，每次drop该表并将PPMS视图V_CM_CONTENT_ZCOM取过来
create table V_CM_CONTENT_ZCOM
(
  CONTENTID     VARCHAR2(12),
  CONTENTCODE   VARCHAR2(10),
  NAME          VARCHAR2(60),
  APPDESC       VARCHAR2(4000),
  CONTENTFEE    NUMBER(8),
  ONLINEDATE    DATE,
  PRODUCTID     VARCHAR2(12),
  CHARGETIME    NUMBER(1),
  CARTOONPICURL VARCHAR2(256),
  LOGO1URL      VARCHAR2(256),
  LOGO2URL      VARCHAR2(256),
  LOGO3URL      VARCHAR2(256),
  LOGO4URL      VARCHAR2(256),
  ICPSERVID     VARCHAR2(10),
  ICPCODE       VARCHAR2(6),
  LUPDDATE      DATE
);
-- 创建Zcom内容适配关系临时表，每次drop该表并将PPMS视图V_CM_DEVICE_RESOURCE取过来
create table V_CM_DEVICE_RESOURCE_ZCOM
(
  PID           VARCHAR2(12),
  DEVICE_ID     NUMBER(8),
  DEVICE_NAME   VARCHAR2(100),
  CONTENTID     VARCHAR2(12),
  CONTENTNAME   VARCHAR2(60),
  RESOURCEID    VARCHAR2(10),
  ID            VARCHAR2(12),
  SERVICECODE   VARCHAR2(10),
  ABSOLUTEPATH  VARCHAR2(256),
  URL           VARCHAR2(256),
  PROGRAMSIZE   NUMBER(8),
  JARFILEPATH   VARCHAR2(256),
  JADFILEPATH   VARCHAR2(256),
  CONTENTUUID   VARCHAR2(50),
  CREATEDATE    DATE,
  PROSUBMITDATE DATE,
  MATCH         NUMBER(1),
  PLATFORM      VARCHAR2(20),
  OSNAME        VARCHAR2(100)
);
----- 创建Zcom 同步临时表
create table T_SYNCTIME_TMP_ZCOM
(
  NAME      VARCHAR2(60),
  CONTENTID VARCHAR2(12),
  LUPDDATE  DATE
);



-- 创建Zcom同步历史时间表
create table T_LASTSYNCTIME_ZCOM
(
  LASTTIME DATE not null
);

-- 创建Zcom分类信息表
create table Z_PPS_MAGA
(
  ID    NUMBER not null,
  NAME  VARCHAR2(200) not null,
  LOGO  VARCHAR2(100),
  DESCS VARCHAR2(2000)
);

-- 创建Zcom内容详情表
create table Z_PPS_MAGA_LS
(
  ID             NUMBER,
  MAGA_NAME      VARCHAR2(100),
  MAGA_PERIODS   VARCHAR2(10),
  MAGA_OFFICE    VARCHAR2(50),
  MAGA_DATE      VARCHAR2(50),
  PERIOD         VARCHAR2(10),
  PRICE          NUMBER,
  CONTENTID      VARCHAR2(30),
  CHARGETYPE     VARCHAR2(2),
  UPTIME         VARCHAR2(50),
  CARTOONPIC     VARCHAR2(50),
  LOG1           VARCHAR2(50),
  LOG2           VARCHAR2(50),
  LOG3           VARCHAR2(50),
  LOG4           VARCHAR2(50),
  PARENT_ID      NUMBER,
  MAGA_FULL_NAME VARCHAR2(100),
  FULL_DEVICE_ID VARCHAR2(4000),
  ICPCODE        VARCHAR2(100),
  ICPSERVID      VARCHAR2(100),
  SIZES          VARCHAR2(20),
  PERFIX         VARCHAR2(50),
  PLATFORM       VARCHAR2(50),
  LUPDDATE        DATE
);
create index IND_V_DEVICEID on z_pps_maga_ls(full_device_id) indextype is ctxsys.context parameters('lexer chinese_lexer');
----增加AP应用黑名单
create table T_CONTENT_BACKLIST
(
  CONTENTID VARCHAR2(30),
  INDATE    VARCHAR2(30)
);
comment on column T_CONTENT_BACKLIST.CONTENTID
  is '应用内容ID';
comment on column T_CONTENT_BACKLIST.INDATE
  is '黑名单有效期';
-- Create/Recreate indexes 
create index T_CONTENT_BLACKLIST_CID on T_CONTENT_BACKLIST (CONTENTID);

-- 基地游戏包信息表
-- Create table
create table T_GAME_BASE
(
  PKGID       VARCHAR2(25) not null,
  PKGNAME     VARCHAR2(64) not null,
  PKGDESC     VARCHAR2(512) not null,
  CPNAME      VARCHAR2(64),
  SERVICECODE VARCHAR2(30),
  FEE         NUMBER not null,
  PKGURL      VARCHAR2(300) not null,
  PICURL1     VARCHAR2(255) not null,
  PICURL2     VARCHAR2(255) not null,
  PICURL3     VARCHAR2(255) not null,
  PICURL4     VARCHAR2(255) not null,
  UPDATETIME  DATE default sysdate not null,
  CREATETIME  DATE default sysdate not null
);
-- Add comments to the columns 
comment on column T_GAME_BASE.PKGID
  is '游戏包id';
comment on column T_GAME_BASE.PKGNAME
  is '游戏包名称';
comment on column T_GAME_BASE.PKGDESC
  is '游戏包介绍';
comment on column T_GAME_BASE.CPNAME
  is '发行商';
comment on column T_GAME_BASE.SERVICECODE
  is '业务代码';
comment on column T_GAME_BASE.FEE
  is '费率';
comment on column T_GAME_BASE.PKGURL
  is '游戏包入口URL';
comment on column T_GAME_BASE.PICURL1
  is '规格 30x30 picture1';
comment on column T_GAME_BASE.PICURL2
  is '规格 34x34 picture2';
comment on column T_GAME_BASE.PICURL3
  is '规格 50x50 picture3';
comment on column T_GAME_BASE.PICURL4
  is '规格 65x65 picture4';
comment on column T_GAME_BASE.UPDATETIME
  is '最近操作时间';
comment on column T_GAME_BASE.CREATETIME
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_BASE
  add primary key (PKGID);
  
  -- Create table创建货架监控基础货架表
create table T_CATEGORY_MONITOR
(
  ID               VARCHAR2(30),
  NAME             VARCHAR2(100),
  CATEGORYID       VARCHAR2(20),
  PARENTCATEGORYID VARCHAR2(20),
  CATEGORYTYPE     VARCHAR2(2)
);
create index INDEX_MONITOR_CATEGORYID on T_CATEGORY_MONITOR (CATEGORYID);
-- Add comments to the columns 
comment on column T_CATEGORY_MONITOR.ID
  is '货架内码ID';
comment on column T_CATEGORY_MONITOR.NAME
  is '货架名称';
comment on column T_CATEGORY_MONITOR.CATEGORYID
  is '货架ID';
comment on column T_CATEGORY_MONITOR.PARENTCATEGORYID
  is '货架父ID';
comment on column T_CATEGORY_MONITOR.CATEGORYTYPE
  is '搜索类型：0,只取本货架信息;1,取本货架信息以及一级子货架信息;9,取本货架信息以及所有子孙货架信息';
  
  ---基地音乐分离
-- Create table
create table T_MB_MUSIC
(
  MUSICID    VARCHAR2(30) not null,
  SONGNAME   VARCHAR2(200),
  SINGER     VARCHAR2(100),
  VALIDITY   VARCHAR2(512),
  UPDATETIME VARCHAR2(30),
  CREATETIME VARCHAR2(30),
  DELFLAG    NUMBER(4) default 0 not null
);
-- Add comments to the columns 
comment on column T_MB_MUSIC.MUSICID
  is '歌曲ID';
comment on column T_MB_MUSIC.SONGNAME
  is '歌曲名称';
comment on column T_MB_MUSIC.SINGER
  is '歌手名称';
comment on column T_MB_MUSIC.VALIDITY
  is '有效期';
comment on column T_MB_MUSIC.UPDATETIME
  is '更新时间';
comment on column T_MB_MUSIC.CREATETIME
  is '创建时间';
    comment on column T_MB_MUSIC.DELFLAG
  is '删除标记，0，未删除；1，已删除';
-- Create/Recreate indexes 
create unique index PK_MB_MUSIC_ID on T_MB_MUSIC (MUSICID);

- Create table
create table T_MB_CATEGORY
(
  CATEGORYID       VARCHAR2(30) not null,
  CATEGORYNAME     VARCHAR2(200) not null,
  PARENTCATEGORYID VARCHAR2(30),
  TYPE             VARCHAR2(10),
  DELFLAG          VARCHAR2(2),
  CREATETIME       VARCHAR2(30),
  CATEGORYDESC     VARCHAR2(500),
  SORTID           NUMBER(8),
  SUM              NUMBER(8) default 0 not null
);
-- Add comments to the columns 
comment on column T_MB_CATEGORY.CATEGORYID
  is '货架ID';
comment on column T_MB_CATEGORY.CATEGORYNAME
  is '货架名称';
comment on column T_MB_CATEGORY.PARENTCATEGORYID
  is '货架父ID';
comment on column T_MB_CATEGORY.TYPE
  is '类型';
comment on column T_MB_CATEGORY.DELFLAG
  is '删除标记';
comment on column T_MB_CATEGORY.CREATETIME
  is '创建时间';
comment on column T_MB_CATEGORY.CATEGORYDESC
  is '货架描述';
comment on column T_MB_CATEGORY.SORTID
  is '货架排序';
comment on column T_MB_CATEGORY.SUM
  is '货架商品数量';
-- Create/Recreate indexes 
create unique index PK_T_MB_CATEGORY_ID on T_MB_CATEGORY (CATEGORYID);

-- Create table
create table T_MB_REFERENCE
(
  MUSICID    VARCHAR2(30) not null,
  CATEGORYID VARCHAR2(30) not null,
  MUSICNAME  VARCHAR2(200),
  CREATETIME VARCHAR2(30),
  SORTID     NUMBER(8)
  
);
-- Add comments to the columns 
comment on column T_MB_REFERENCE.MUSICID
  is '音乐ID';
comment on column T_MB_REFERENCE.CATEGORYID
  is '货架ID';
comment on column T_MB_REFERENCE.CREATETIME
  is '创建时间';
comment on column T_MB_REFERENCE.SORTID
  is '排序';
comment on column T_MB_REFERENCE.MUSICNAME
  is '歌曲名称';
-- Create/Recreate indexes 
create index INDEX_TB_REFERENCE_CAID on T_MB_REFERENCE (CATEGORYID);
create unique index PK_MUSIC_CATEID on T_MB_REFERENCE (MUSICID, CATEGORYID);

---创建序列
-- Create sequence 
create sequence SEQ_BM_CATEGORY_ID  minvalue 100002167  maxvalue 999999999  start with 100004206  increment by 1  nocache  cycle;

--G+游戏包修改表结构
alter table T_GAME_BASE add state varchar2(2) default 1 not null;
comment on column T_GAME_BASE.state
  is '当前状态1:新建,2:更新,3:删除';

--小编推荐
alter table T_CONTENT_COUNT add recommend_grade number(6,2);
comment on column T_CONTENT_COUNT.recommend_grade
  is '推荐评分';
  
  
----  AP应用刷榜榜单前60商品监控预警，提供给报表使用 根据现网榜单货架ID配置，
----- 测试环境需要修改相应货架ID
----  终端门户
create or replace view v_mo_top60reference as
select q.day,q.goodsid,q.contentid,q.sortid,q.subname, y.name
    from t_r_category y,

         (select to_char(sysdate, 'yyyyMMdd') day,
                 r.goodsid,
                 g.contentid,
                 r.sortid,
                 c.name subname,
                 c.parentcategoryid
            from t_r_reference r, t_r_Category c, t_r_gcontent g
           where r.categoryid = c.categoryid
             and r.refnodeid = g.id
             and c.id in ('1257460', '1257459', '1257457', '1257458',
        '1257462', '1257463', '1257464', '1257466',
                          '1257467', '1257468', '1257469', '1257471')
             and r.sortid > 940) q
   where y.categoryid = q.parentcategoryid;
-----WWW门户   
create or replace view v_www_top60reference as
select q.day,q.goodsid,q.contentid,q.sortid,q.subname, y.name
    from t_r_category y,

         (select to_char(sysdate, 'yyyyMMdd') day,
                 r.goodsid,
                 g.contentid,
                 r.sortid,
                 c.name subname,
                 c.parentcategoryid
            from t_r_reference r, t_r_Category c, t_r_gcontent g
           where r.categoryid = c.categoryid
             and r.refnodeid = g.id
             and c.id in ('1810803', '1810805', '1815681', '1814487')
             and r.sortid > 940) q
   where y.categoryid = q.parentcategoryid;

-----添加视频基地元数据表
-- Create table
create table T_VB_VIDEO
(
  PKGID      VARCHAR2(10),
  PKGNAME    VARCHAR2(100),
  FEE        NUMBER,
  CREATEDATE DATE,
  LUPDATE    DATE
);
-- Add comments to the columns 
comment on column T_VB_VIDEO.PKGID
  is '视频包ID';
comment on column T_VB_VIDEO.PKGNAME
  is '视频包名称';
comment on column T_VB_VIDEO.FEE
  is '该视频产品包的资费';
comment on column T_VB_VIDEO.CREATEDATE
  is '创建时间';
comment on column T_VB_VIDEO.LUPDATE
  is '最后更新时间';
create unique index PKGID_PK on T_VB_VIDEO (PKGID);

--添加人工干预应用----
create table T_INTERVENOR
(
  ID          VARCHAR2(30) not null,
  NAME        VARCHAR2(300) not null,
  STARTDATE   DATE not null,
  ENDDATE     DATE not null,
  STARTSORTID NUMBER(8) not null,
  ENDSORTID   NUMBER(8) not null
);

comment on column T_INTERVENOR.ID
  is '干预容器编号';
comment on column T_INTERVENOR.NAME
  is '干预容器名称';
comment on column T_INTERVENOR.STARTDATE
  is '干预开始时间';
comment on column T_INTERVENOR.ENDDATE
  is '干预结束时间';
comment on column T_INTERVENOR.STARTSORTID
  is '干预开始排名';
comment on column T_INTERVENOR.ENDSORTID
  is '干预结束排名，如与开始排名相同为固定排名，否则为浮动排名';
  
alter table T_INTERVENOR
  add primary key (ID);  

create table T_INTERVENOR_CATEGORY_MAP
(
  INTERVENORID VARCHAR2(30) not null,
  CATEGORYID   VARCHAR2(30) not null,
  SORTID       NUMBER(3) default 1
);

comment on column T_INTERVENOR_CATEGORY_MAP.INTERVENORID
  is '容器id';
comment on column T_INTERVENOR_CATEGORY_MAP.CATEGORYID
  is '榜单id';
comment on column T_INTERVENOR_CATEGORY_MAP.SORTID
  is '容器排序号';


create table T_INTERVENOR_GCONTENT_MAP
(
  INTERVENORID VARCHAR2(30) not null,
  GCONTENTID   VARCHAR2(30) not null,
  SORTID       NUMBER(8) default 1
);

comment on column T_INTERVENOR_GCONTENT_MAP.INTERVENORID
  is '容器id';
comment on column T_INTERVENOR_GCONTENT_MAP.GCONTENTID
  is '内容id';
comment on column T_INTERVENOR_GCONTENT_MAP.SORTID
  is '内容在容器中的排序';
  
  create table T_EXPORT_TOPLIST
(
  ID         NUMBER(2) not null,
  NAME       VARCHAR2(50),
  CATEGORYID VARCHAR2(30) not null,
  COUNT      NUMBER(5) default 30 not null,
  CONDITION  VARCHAR2(200)
);
-- Add comments to the columns 
comment on column T_EXPORT_TOPLIST.ID
  is '榜单专区ID';
comment on column T_EXPORT_TOPLIST.NAME
  is '榜单或专区性质';
comment on column T_EXPORT_TOPLIST.CATEGORYID
  is '货架编码';
comment on column T_EXPORT_TOPLIST.COUNT
  is '获取榜单下最大商品个数';
comment on column T_EXPORT_TOPLIST.CONDITION
  is '榜单获取条件';


create sequence SEQ_ACTIONLOG_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache
cycle;


create sequence SEQ_ROLE_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache cycle;


--创建货架编码序列（9位）
create sequence SEQ_CATEGORY_ID
minvalue 100000000
maxvalue 999999999
start with 100000000
increment by 1
nocache cycle;

--创建同步时间临时序列
create sequence SEQ_SYNCTIME_TMP_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache cycle;


--创建商品历史信息表ID序列
create sequence SEQ_GOODS_HIS_ID
minvalue 1
maxvalue 999999999999999
start with 1
increment by 1
nocache cycle;

--创建同步策略ID序列
create sequence SEQ_SYNC_TACTIC_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache cycle;

--数据版本序列
create sequence SEQ_DB_VERSION
minvalue 1000
maxvalue 999999
start with 1000
increment by 1
nocache cycle;

-- 用于货架规则表id
create sequence SEQ_CATERULE_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;

-- 用于规则条件表id
create sequence SEQ_CATERULE_COND_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;



create table T_TOPLIST
(
  TIME       VARCHAR2(8),
  CONTENTID  VARCHAR2(12) not null,
  COUNT      VARCHAR2(11),
  TYPE       VARCHAR2(2) not null,
  UPDATETIME DATE default SYSDATE not null,
  constraint T_TOPLIST_PK primary key (CONTENTID,TYPE)
);
-- Add comments to the columns 
comment on column T_TOPLIST.TIME
  is '统计日期,格式：YYYYMMDD';
comment on column T_TOPLIST.CONTENTID
  is '应用标识';
comment on column T_TOPLIST.COUNT
  is 'Type为1|2时是人气综合推荐指数; 为3|4时是星探推荐得分;';
comment on column T_TOPLIST.TYPE
  is '榜单类型:1应用人气;2创意孵化人气;3应用星探;4创意孵化星探';
comment on column T_TOPLIST.UPDATETIME
  is '最后一次更新的时间';

create table T_CY_PRODUCTLIST
(
  STATTIME        VARCHAR2(8),
  CONTENTID        VARCHAR2(12) not null primary key  ,
  CONTENTNAME      VARCHAR2(60),
  DOWNLOADUSERNUM  NUMBER(12),
  TESTUSERNUM      NUMBER(12),
  TESTSTAR         NUMBER(12),
  STARSCORECOUNT   NUMBER(12),
  GLOBALSCORECOUNT NUMBER(12),
  UPDATETIME       DATE default SYSDATE not null
);
-- Add comments to the columns 
comment on column T_CY_PRODUCTLIST.STATTIME
  is '统计日期,格式：YYYYMMDD';
comment on column T_CY_PRODUCTLIST.CONTENTID
  is '内容ID';
comment on column T_CY_PRODUCTLIST.CONTENTNAME
  is '内容名称';
comment on column T_CY_PRODUCTLIST.DOWNLOADUSERNUM
  is '下载用户数';
comment on column T_CY_PRODUCTLIST.TESTUSERNUM
  is '测评用户数';
comment on column T_CY_PRODUCTLIST.TESTSTAR
  is '测评星级';
comment on column T_CY_PRODUCTLIST.STARSCORECOUNT
  is '星探推荐得分';
comment on column T_CY_PRODUCTLIST.GLOBALSCORECOUNT
  is '人气综合推荐指数';
comment on column T_CY_PRODUCTLIST.UPDATETIME
  is '最后一次更新的时间';

--.5.trigger

--.6.function

--.7.procedure

--8.job

--人工干预容器id
create sequence SEQ_INTERVENOR_ID
minvalue 1
maxvalue 9999999999
start with 200
increment by 1
nocache
cycle;







