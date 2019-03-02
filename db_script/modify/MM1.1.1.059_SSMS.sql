create table T_CB_ADAPTER
(
  ID        VARCHAR2(60),
  CHAPTERID VARCHAR2(60),
  GROUPS    VARCHAR2(4000),
  FILE_SIZE NUMBER(10),
  USE_TYPE  NUMBER(2),
  CLEAR     VARCHAR2(1),
  FLOW_TIME DATE default sysdate,
  TYPE      VARCHAR2(5)
);
-- Add comments to the table 
comment on table T_CB_ADAPTER
  is '呈现表';
-- Add comments to the columns 
comment on column T_CB_ADAPTER.ID
  is '呈现标识';
comment on column T_CB_ADAPTER.CHAPTERID
  is '章节标识';
comment on column T_CB_ADAPTER.GROUPS
  is '机型组标识';
comment on column T_CB_ADAPTER.FILE_SIZE
  is '文件大小';
comment on column T_CB_ADAPTER.USE_TYPE
  is '使用方式';
comment on column T_CB_ADAPTER.CLEAR
  is '视频清晰度（1:流畅
2:清晰
3:高清
只有视频呈现有该值
）';
comment on column T_CB_ADAPTER.FLOW_TIME
  is '流水时间（货架辅助字段）';
comment on column T_CB_ADAPTER.TYPE
  is '内容类型';

  
  
-- Create table
create table T_CB_CATEGORY
(
  CATEGORYID          VARCHAR2(30) not null,
  CATEGORYNAME        VARCHAR2(200) not null,
  CATEGORYVALUE       VARCHAR2(50),
  PARENTCATEGORYID    VARCHAR2(30),
  TYPE                VARCHAR2(10),
  PICTURE             VARCHAR2(512),
  DELFLAG             VARCHAR2(2),
  CATEGORYDESC        VARCHAR2(1000),
  SORTID              NUMBER(8),
  CREATETIME          DATE default sysdate,
  LUPDATE             DATE default sysdate,
  PARENTCATEGORYVALUE VARCHAR2(30),
  MO_REFERENCE_NUM    NUMBER(10),
  WAP_REFERENCE_NUM   NUMBER(10),
  LOGO1               VARCHAR2(512),
  LOGO2               VARCHAR2(512),
  LOGO3               VARCHAR2(512),
  primary key(CATEGORYID)
);

-- Add comments to the table 
comment on table T_CB_CATEGORY
  is '货架表';
-- Add comments to the columns 
comment on column T_CB_CATEGORY.CATEGORYID
  is '货架ID';
comment on column T_CB_CATEGORY.CATEGORYNAME
  is '货架名称';
comment on column T_CB_CATEGORY.CATEGORYVALUE
  is '货架名称对应的VALUE';
comment on column T_CB_CATEGORY.PARENTCATEGORYID
  is '父货架ID';
comment on column T_CB_CATEGORY.TYPE
  is '货架的业务区分（首发：FIRST、排行榜：RANK、品牌店：BRAND）';
comment on column T_CB_CATEGORY.PICTURE
  is '货架图片';
comment on column T_CB_CATEGORY.DELFLAG
  is '是否被删除（0：未删除，1：删除）';
comment on column T_CB_CATEGORY.CATEGORYDESC
  is '货架描述';
comment on column T_CB_CATEGORY.SORTID
  is '排序';
comment on column T_CB_CATEGORY.CREATETIME
  is '创建时间';
comment on column T_CB_CATEGORY.LUPDATE
  is '最后更新时间';
comment on column T_CB_CATEGORY.PARENTCATEGORYVALUE
  is '父货架名称对应的VALUE';
comment on column T_CB_CATEGORY.MO_REFERENCE_NUM
  is '该货架下挂的商品数量（客户端）';
comment on column T_CB_CATEGORY.WAP_REFERENCE_NUM
  is '该货架下挂的商品数量（WAP）';
comment on column T_CB_CATEGORY.LOGO1
  is '品牌馆的logo，大图
';
comment on column T_CB_CATEGORY.LOGO2
  is '品牌馆的logo，中图
';
comment on column T_CB_CATEGORY.LOGO3
  is '品牌馆的logo，小图
';


-- Create table
create table T_CB_CHAPTER
(
  CHAPTERID   VARCHAR2(60) not null,
  CONTENTID   VARCHAR2(60) not null,
  NAME        VARCHAR2(60),
  DESCRIPTION VARCHAR2(512),
  FEE         NUMBER(10),
  UPDATE_FLAG NUMBER(1),
  TYPE        VARCHAR2(5)
);
-- Add comments to the table 
comment on table T_CB_CHAPTER
  is '章节表';
-- Add comments to the columns 
comment on column T_CB_CHAPTER.CHAPTERID
  is '章节（话，集）ID';
comment on column T_CB_CHAPTER.CONTENTID
  is '所属内容标识';
comment on column T_CB_CHAPTER.NAME
  is '章节名称';
comment on column T_CB_CHAPTER.DESCRIPTION
  is '章节描述';
comment on column T_CB_CHAPTER.FEE
  is '资费（资费描述  单位： 分）';
comment on column T_CB_CHAPTER.UPDATE_FLAG
  is '更新标志（0: 不变；1：新增； 2：修改）';
comment on column T_CB_CHAPTER.TYPE
  is '内容类型';
  
  
create table T_CB_CONTENT
(
  ID            VARCHAR2(60) not null,
  NAME          VARCHAR2(60) not null,
  DESCRIPTION   VARCHAR2(4000),
  PROVIDER      VARCHAR2(60),
  PROVIDER_TYPE VARCHAR2(60),
  AUTHODID      VARCHAR2(60),
  TYPE          VARCHAR2(5),
  KEYWORDS      VARCHAR2(4000),
  EXPIRETIME    VARCHAR2(14),
  FEE           NUMBER(10),
  LOCATION      VARCHAR2(5),
  FIRST         VARCHAR2(5),
  URL1          VARCHAR2(512),
  URL2          VARCHAR2(512),
  URL3          VARCHAR2(512),
  URL4          VARCHAR2(512),
  INFO_CONTENT  VARCHAR2(4000),
  INFO_PIC      VARCHAR2(512),
  INFO_SOURCE   VARCHAR2(30),
  FEE_CODE      VARCHAR2(60),
  DETAIL_URL1   VARCHAR2(512),
  DETAIL_URL2   VARCHAR2(512),
  DETAIL_URL3   VARCHAR2(512),
  BOOK_NUM      NUMBER(10),
  CLASSIFY      VARCHAR2(100),
  AUTHODS       VARCHAR2(1024),
  ACTOR         VARCHAR2(1024),
  OTHERS_ACTOR  VARCHAR2(4000),
  BOOK_TYPE     VARCHAR2(50),
  BOOK_STYLE    VARCHAR2(50),
  BOOK_COLOR    VARCHAR2(50),
  AREA          VARCHAR2(50),
  LANGUAGE      VARCHAR2(20),
  YEAR          VARCHAR2(4),
  STATUS        VARCHAR2(4),
  CHAPTER_TYPE  VARCHAR2(4),
  PORTAL        VARCHAR2(1),
  BUSINESSID    VARCHAR2(64),
  DOWNLOAD_NUM  NUMBER(10),
  AVERAGEMARK   NUMBER(1),
  FAVORITES_NUM NUMBER(10),
  BOOKED_NUM    NUMBER(10),
  CREATETIME    VARCHAR2(14),
  FLOW_TIME     DATE,
  USER_TYPE     VARCHAR2(50),
  LUPDATE       VARCHAR2(14),
  COMIC_IMAGE   VARCHAR2(512),
  ADAPTERDESK   VARCHAR2(50),
  primary key(id)
);
-- Add comments to the table 
comment on table T_CB_CONTENT
  is '内容表';
-- Add comments to the columns 
comment on column T_CB_CONTENT.ID
  is '内容标识';
comment on column T_CB_CONTENT.NAME
  is '内容名称';
comment on column T_CB_CONTENT.DESCRIPTION
  is '内容描述';
comment on column T_CB_CONTENT.PROVIDER
  is '内容提供方(CP标识)';
comment on column T_CB_CONTENT.PROVIDER_TYPE
  is '内容提供者类型';
comment on column T_CB_CONTENT.AUTHODID
  is '作者标识';
comment on column T_CB_CONTENT.TYPE
  is '内容类型';
comment on column T_CB_CONTENT.KEYWORDS
  is '内容关键字';
comment on column T_CB_CONTENT.EXPIRETIME
  is '内容超期时间';
comment on column T_CB_CONTENT.FEE
  is '资费';
comment on column T_CB_CONTENT.LOCATION
  is '内容归属地';
comment on column T_CB_CONTENT.FIRST
  is '内容的首字母';
comment on column T_CB_CONTENT.URL1
  is '预览图1';
comment on column T_CB_CONTENT.URL2
  is '预览图2';
comment on column T_CB_CONTENT.URL3
  is '预览图3';
comment on column T_CB_CONTENT.URL4
  is '预览图4';
comment on column T_CB_CONTENT.INFO_CONTENT
  is '资讯内容';
comment on column T_CB_CONTENT.INFO_PIC
  is '资讯配套图片';
comment on column T_CB_CONTENT.INFO_SOURCE
  is '资讯来源';
comment on column T_CB_CONTENT.FEE_CODE
  is '计费代码';
comment on column T_CB_CONTENT.DETAIL_URL1
  is '内容详情页URL1';
comment on column T_CB_CONTENT.DETAIL_URL2
  is '内容详情页URL2';
comment on column T_CB_CONTENT.DETAIL_URL3
  is '内容详情页URL3';
comment on column T_CB_CONTENT.BOOK_NUM
  is '话(集)数';
comment on column T_CB_CONTENT.CLASSIFY
  is '漫（动、主、资）画书分类';
comment on column T_CB_CONTENT.AUTHODS
  is '作者';
comment on column T_CB_CONTENT.ACTOR
  is '主演';
comment on column T_CB_CONTENT.OTHERS_ACTOR
  is '其他演员';
comment on column T_CB_CONTENT.BOOK_TYPE
  is '漫（动）画书类型';
comment on column T_CB_CONTENT.BOOK_STYLE
  is '漫（动）画书风格';
comment on column T_CB_CONTENT.BOOK_COLOR
  is '漫（动）画书颜色';
comment on column T_CB_CONTENT.AREA
  is '出品地区';
comment on column T_CB_CONTENT.LANGUAGE
  is '语种';
comment on column T_CB_CONTENT.YEAR
  is '发行年份';
comment on column T_CB_CONTENT.STATUS
  is '连载状态';
comment on column T_CB_CONTENT.CHAPTER_TYPE
  is '篇章类型';
comment on column T_CB_CONTENT.PORTAL
  is '门户';
comment on column T_CB_CONTENT.BUSINESSID
  is '业务代码';
comment on column T_CB_CONTENT.DOWNLOAD_NUM
  is '下载次数（动漫统计）';
comment on column T_CB_CONTENT.AVERAGEMARK
  is '动漫星级（动漫统计）';
comment on column T_CB_CONTENT.FAVORITES_NUM
  is '收藏人数（动漫统计）';
comment on column T_CB_CONTENT.BOOKED_NUM
  is '预定人数（动漫统计）';
comment on column T_CB_CONTENT.CREATETIME
  is '创建时间';
comment on column T_CB_CONTENT.FLOW_TIME
  is '流水时间';
comment on column T_CB_CONTENT.USER_TYPE
  is '用户类型';
comment on column T_CB_CONTENT.LUPDATE
  is '修改时间';
comment on column T_CB_CONTENT.COMIC_IMAGE
  is '动漫形象';
comment on column T_CB_CONTENT.ADAPTERDESK
  is '适配平台';
  
-- Create table
create table T_CB_CP
(
  CPID    VARCHAR2(60) not null,
  CPNAME  VARCHAR2(60) not null
);
-- Add comments to the table 
comment on table T_CB_CP
  is 'CP表';
-- Add comments to the columns 
comment on column T_CB_CP.CPID
  is 'CPID';
comment on column T_CB_CP.CPNAME
  is 'CP名称';
  
-- Create table
create table T_CB_DEVICEGROUPITEM
(
  GROUPID   VARCHAR2(60),
  DEVICEID  VARCHAR2(60),
  FLOW_TIME DATE
);

create table T_CB_MASTER
(
  MASTERID VARCHAR2(50) not null,
  NAME     VARCHAR2(50),
  SORTID   NUMBER(5)
);
-- Add comments to the table 
comment on table T_CB_MASTER
  is '名家表';
-- Add comments to the columns 
comment on column T_CB_MASTER.MASTERID
  is '名家目录标识';
comment on column T_CB_MASTER.NAME
  is '名家目录名称';
comment on column T_CB_MASTER.SORTID
  is '排序序号';

-- Create table
create table T_CB_REFERENCE
(
  ID         NUMBER(10) not null,
  CONTENTID  VARCHAR2(50),
  CATEGORYID VARCHAR2(20),
  SORTID     NUMBER(8),
  FLOW_TIME  DATE default sysdate,
  TYPE       VARCHAR2(10),
  PORTAL     VARCHAR2(1)
);
-- Add comments to the table 
comment on table T_CB_REFERENCE
  is '商品表';
-- Add comments to the columns 
comment on column T_CB_REFERENCE.ID
  is '主键（自动生成）';
comment on column T_CB_REFERENCE.CONTENTID
  is '内容ID';
comment on column T_CB_REFERENCE.CATEGORYID
  is '货架ID';
comment on column T_CB_REFERENCE.SORTID
  is '排序';
comment on column T_CB_REFERENCE.FLOW_TIME
  is '流水时间，为了区别今天数据还是以前数据';
comment on column T_CB_REFERENCE.TYPE
  is '区分是那个自定义榜单的商品（FIRST,RANK,BRAND,SYSTEM）';
comment on column T_CB_REFERENCE.PORTAL
  is '1 客户端
2 WAP门户
3 所有
';



----动漫基地数据接入开始
create sequence SEQ_CB_CATEGORY_ID
minvalue 100000001
maxvalue 999999999
start with 100000060
increment by 1
nocache
cycle;

-- Create sequence 
create sequence SEQ_CB_ID
minvalue 1
maxvalue 999999999
start with 1
increment by 1
nocache
cycle;






insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000001', '漫画', null, null, null, null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000002', '动画', null, null, null, null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000003', '主题', null, null, null, null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000004', '资讯', null, null, null, null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000005', '首发', null, null, 'FIRST', null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000006', '排行榜', null, null, 'RANK', null, '0', null, null, null, null, null, null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000007', '品牌馆', '0', null, 'BRAND', null, '0', null, null, null, null, 'no_use...', null, null, null, null, null);
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, CATEGORYVALUE, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, PARENTCATEGORYVALUE, MO_REFERENCE_NUM, WAP_REFERENCE_NUM, LOGO1, LOGO2, LOGO3)
values ('100000008', '专题', '1', null, 'TOPIC', null, '0', null, null, null, null, 'no_user...', null, null, null, null, null);



insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000020', '搞笑', '100000001', null, null, '0', null, null, null, null, '15000');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000021', '冒险', '100000001', null, null, '0', null, null, null, null, '15001');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000022', '运动', '100000001', null, null, '0', null, null, null, null, '15002');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000023', '情感', '100000001', null, null, '0', null, null, null, null, '15003');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000024', '科幻', '100000001', null, null, '0', null, null, null, null, '15004');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000025', '悬疑', '100000001', null, null, '0', null, null, null, null, '15005');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000026', '国学', '100000001', null, null, '0', null, null, null, null, '15006');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000027', '教育', '100000001', null, null, '0', null, null, null, null, '15007');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000028', '时讯', '100000001', null, null, '0', null, null, null, null, '15009');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000029', '连环画', '100000001', null, null, '0', null, null, null, null, '15011');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000031', '其他', '100000001', null, null, '0', null, null, null, null, '16017');



insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000040', '搞笑', '100000002', null, null, '0', null, null, null, null, '15400');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000041', '冒险', '100000002', null, null, '0', null, null, null, null, '15401');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000042', '爱情', '100000002', null, null, '0', null, null, null, null, '15403');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000043', '玄幻', '100000002', null, null, '0', null, null, null, null, '15404');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000044', '悬疑', '100000002', null, null, '0', null, null, null, null, '15405');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000045', '国学', '100000002', null, null, '0', null, null, null, null, '15406');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000046', '亲子', '100000002', null, null, '0', null, null, null, null, '15407');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000047', '新番', '100000002', null, null, '0', null, null, null, null, '15409');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000048', '经典', '100000002', null, null, '0', null, null, null, null, '15410');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000049', '校园', '100000002', null, null, '0', null, null, null, null, '15411');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000050', '励志', '100000002', null, null, '0', null, null, null, null, '15412');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000051', '少女', '100000002', null, null, '0', null, null, null, null, '15413');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000052', '武侠', '100000002', null, null, '0', null, null, null, null, '15414');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000053', '其他', '100000002', null, null, '0', null, null, null, null, '16017');

----动漫基地数据接入结束


------视频数据接入开始

create table T_VO_VIDEO
(
  videoID    varchar2(60) not null,
  codeRateID varchar2(6) not null,
  filePath   varchar2(512) not null,
  fileSize   number(12) not null
)
;
-- Add comments to the columns 
comment on column T_VO_VIDEO.videoID
  is '视频标识';
comment on column T_VO_VIDEO.codeRateID
  is '码率标识';
comment on column T_VO_VIDEO.filePath
  is '物理文件地址';
comment on column T_VO_VIDEO.fileSize
  is '文件大小:单位：kbyte';


-- Create table
create table T_VO_DEVICE
(
  deviceID     varchar2(60) not null,
  UA           varchar2(1024) not null,
  deviceName   varchar2(60) not null,
  codeRateType varchar2(60) not null
)
;
-- Add comments to the columns 
comment on column T_VO_DEVICE.deviceID
  is '机型编号';
comment on column T_VO_DEVICE.UA
  is 'UA';
comment on column T_VO_DEVICE.deviceName
  is '机型名称';
comment on column T_VO_DEVICE.codeRateType
  is '码率格式建议：1: H.264 2: 融创';

create table T_VO_CODERATE
(
  codeRateID         varchar2(60) not null,
  canonicalName      varchar2(512) not null,
  encodeFormat       varchar2(512) not null,
  containerFormat    varchar2(512) not null,
  codeRateLevel      varchar2(512) not null,
  netType            varchar2(512) not null,
  mediaMimeType      varchar2(512) not null,
  resolutionType     varchar2(512) not null,
  fileNameConvention varchar2(512) not null,
  codecName          varchar2(512) not null,
  videoEncode        varchar2(512) not null,
  netMilieu          varchar2(512) not null,
  samplingRate       varchar2(512) not null
)
;
-- Add comments to the columns 
comment on column T_VO_CODERATE.codeRateID
  is '码率标识';
comment on column T_VO_CODERATE.canonicalName
  is '制作规范中名称:精简版_H264_WLAN_400*320';
comment on column T_VO_CODERATE.encodeFormat
  is '媒体文件编码格式:04-H.264/AAC';
comment on column T_VO_CODERATE.containerFormat
  is '容器封装格式:02-.3gp';
comment on column T_VO_CODERATE.codeRateLevel
  is '码率等级:50';
comment on column T_VO_CODERATE.netType
  is '网络类型:03-WLAN';
comment on column T_VO_CODERATE.mediaMimeType
  is '媒体MIME类型:02-video/3gpp';
comment on column T_VO_CODERATE.resolutionType
  is '分辨率类型:03-QVGA 320*240';
comment on column T_VO_CODERATE.fileNameConvention
  is '文件命名规范:UDID_12_jl.mp4';
comment on column T_VO_CODERATE.codecName
  is '转码器名称:sim12';
comment on column T_VO_CODERATE.videoEncode
  is '视频编码方式:TIVC/H.264';
comment on column T_VO_CODERATE.netMilieu
  is '网络环境:90kbps/QVGA/8~12fps/5000ms';
comment on column T_VO_CODERATE.samplingRate
  is '视频码率/分辨率/帧率/关键帧/Level值	音频编码方式/码率/声道/采样率:TIAC-H/14kbps';



-- Create table
create table T_VO_PROGRAM
(
  programID   varchar2(60) not null,
  videoID     varchar2(60) not null,
  programName varchar2(128) not null,
  nodeID      varchar2(21) not null,
  description varchar2(4000) not null,
  logoPath    varchar2(512) not null,
  timeLength  number(12) not null,
  showTime    varchar2(20) not null,
  lastUpTime  varchar2(14) not null,
  programType number(2) not null
)
;
-- Add comments to the columns 
comment on column T_VO_PROGRAM.programID
  is '节目标识';
comment on column T_VO_PROGRAM.videoID
  is '视频标识';
comment on column T_VO_PROGRAM.programName
  is '节目中文名称';
comment on column T_VO_PROGRAM.nodeID
  is '栏目标识';
comment on column T_VO_PROGRAM.description
  is '简介';
comment on column T_VO_PROGRAM.logoPath
  is '根据accessType提供相应LOGO绝对地址.通过mm客户端访问时，图片url只可通过mm客户端访问，走cmwap接入点，后台服务器调用无效';
comment on column T_VO_PROGRAM.timeLength
  is '时长(已由豪秒转为秒)';
comment on column T_VO_PROGRAM.showTime
  is '显示时长：00：00：00';
comment on column T_VO_PROGRAM.lastUpTime
  is '最后修改时间：YYYYMMDD';
comment on column T_VO_PROGRAM.programType
  is '节目类型:1: 直播(直播的视频源) 2：非直播';


create table T_VO_NODE
(
  nodeID       varchar2(60) not null,
  nodeName     varchar2(128) not null,
  description  varchar2(4000) not null,
  parentNodeID varchar2(60),
  logoPath     varchar2(512),
  sortID       number(19),
  productID    varchar2(1024)
)
;
-- Add comments to the columns 
comment on column T_VO_NODE.nodeID
  is '栏目标识';
comment on column T_VO_NODE.nodeName
  is '栏目名称';
comment on column T_VO_NODE.description
  is '简介';
comment on column T_VO_NODE.parentNodeID
  is '父栏目标识:一级（顶级）栏目其父栏目ID为0. 1：品牌 2：专题 3：分类';
comment on column T_VO_NODE.logoPath
  is '图片路径';
comment on column T_VO_NODE.sortID
  is '排序序号:排序序号。从小到大排列。必须是整数，取值范围在-999999到999999之间。';
comment on column T_VO_NODE.productID
  is '产品标识:多字段用|间隔';



-- Create table
create table T_VO_LIVE
(
  nodeID    varchar2(60) not null,
  programID varchar2(60) not null,
  liveName  varchar2(200) not null,
  startTime varchar2(14) not null,
  endTime   varchar2(14) not null
)
;
-- Add comments to the columns 
comment on column T_VO_LIVE.nodeID
  is '栏目标识';
comment on column T_VO_LIVE.programID
  is '节目标识';
comment on column T_VO_LIVE.liveName
  is '直播节目名称';
comment on column T_VO_LIVE.startTime
  is '播放时间:格式：YYYYMMDDHH24MISS';
comment on column T_VO_LIVE.endTime
  is '结束时间:格式：YYYYMMDDHH24MISS';



-- Create table
create table T_VO_RANK
(
  rankID    varchar2(60) not null,
  rankName  varchar2(60) not null,
  programID varchar2(60) not null,
  sortID    number(6)
)
;
-- Add comments to the columns 
comment on column T_VO_RANK.rankID
  is '排行榜标识';
comment on column T_VO_RANK.rankName
  is '排行榜名称:日排行、周排行、月排行';
comment on column T_VO_RANK.programID
  is '节目标识';
comment on column T_VO_RANK.sortID
  is '排序序号:排序序号。从小到大排列。必须是整数，取值范围在-999999到999999之间。';




create table T_VO_PRODUCT
(
  productID   varchar2(60) not null,
  productName varchar2(100) not null,
  fee         number(12) not null,
  CPID        varchar2(20) not null,
  feeType     varchar2(2) not null,
  startDate   varchar2(14) not null
)
;
-- Add comments to the columns 
comment on column T_VO_PRODUCT.productID
  is '产品标识';
comment on column T_VO_PRODUCT.productName
  is '产品名称';
comment on column T_VO_PRODUCT.fee
  is '资费:单位：分';
comment on column T_VO_PRODUCT.CPID
  is 'CP标识';
comment on column T_VO_PRODUCT.feeType
  is '计费类型: 01:免费  02:按次  03:包月  04:大包月 ';
comment on column T_VO_PRODUCT.startDate
  is '开始日期: 格式：YYYYMMDD';


-- Create table
create table T_VO_CATEGORY
(
  ID       varchar2(60) not null,
  parentID varchar2(60),
  baseID   varchar2(60) not null,
  baseType varchar2(2) not null,
  baseName varchar2(128) not null,
  sortID   number(6) default 0 not null,
  isShow   varchar2(2) default 1 not null
)
;
-- Add comments to the columns 
comment on column T_VO_CATEGORY.ID
  is '主健，节点ID';
comment on column T_VO_CATEGORY.parentID
  is '父节点ID';
comment on column T_VO_CATEGORY.baseID
  is '引用id：栏目id，排行榜id，未来要加的各种不知名的id';
comment on column T_VO_CATEGORY.baseType
  is '引用类型：1：栏目ID， 2：排行榜ID 后续在新增枚举';
comment on column T_VO_CATEGORY.baseName
  is '引用名称：栏目名称，排行榜名称，未来要加的各种不知名的名称';
comment on column T_VO_CATEGORY.sortID
  is '排序号';
comment on column T_VO_CATEGORY.isShow
  is '是否在门户显示：1：是 2：否';



-- Create table
create table T_VO_REFERENCE
(
  ID          varchar2(60) not null,
  cateGoryID  varchar2(60) not null,
  PROGRAMID   varchar2(60) not null,
  PROGRAMNAME varchar2(128) not null,
  sortID      number(6) default 0 not null
)
;
-- Add comments to the columns 
comment on column T_VO_REFERENCE.ID
  is '主健：商品id';
comment on column T_VO_REFERENCE.cateGoryID
  is '所属货架';
comment on column T_VO_REFERENCE.PROGRAMID
  is '节目标识';
comment on column T_VO_REFERENCE.PROGRAMNAME
  is '节目名称';
comment on column T_VO_REFERENCE.sortID
  is '排序号';



-- Add/modify columns 
alter table T_VO_REFERENCE add exportTime date default sysdate not null;
-- Add comments to the columns 
comment on column T_VO_REFERENCE.exportTime
  is '导入时间';


-- Add/modify columns 
alter table T_VO_NODE add EXPORTTIME date default sysdate not null;
-- Add comments to the columns 
comment on column T_VO_NODE.EXPORTTIME
  is '导入时间';

-- Add/modify columns 
alter table T_VO_PROGRAM add EXPORTTIME date default sysdate not null;
-- Add comments to the columns 
comment on column T_VO_PROGRAM.EXPORTTIME
  is '导入时间';

-- Add/modify columns 
alter table T_VO_PRODUCT add feeDesc varchar2(1024);
-- Add comments to the columns 
comment on column T_VO_PRODUCT.feeDesc
  is '价格描述';

-- Add/modify columns 
alter table T_VO_CATEGORY add cdesc varchar2(1024);
-- Add comments to the columns 
comment on column T_VO_CATEGORY.cdesc
  is '货架信息描述';


create sequence SEQ_VO_CATEGORY_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;

create sequence SEQ_VO_REFERENCE_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;

-- Add/modify columns 
alter table T_VO_CATEGORY add BASEPARENTID VARCHAR2(60);
-- Add comments to the columns 
comment on column T_VO_CATEGORY.BASEPARENTID
  is '引用父id';

-- Create table
create table T_VO_VIDEODETAIL
(
  programID        varchar2(20) not null,
  dayPlayNum       number(12) not null,
  totalPlayNum     number(20) not null,
  dayDownloadNum   number(12) not null,
  totalDownloadNum number(20) not null,
  updatetime       date default sysdate not null
)
;
-- Add comments to the columns 
comment on column T_VO_VIDEODETAIL.programID
  is '节目id';
comment on column T_VO_VIDEODETAIL.dayPlayNum
  is '日播放总数';
comment on column T_VO_VIDEODETAIL.totalPlayNum
  is '累计播放总数';
comment on column T_VO_VIDEODETAIL.dayDownloadNum
  is '日下载总数';
comment on column T_VO_VIDEODETAIL.totalDownloadNum
  is '累计下载总数';
comment on column T_VO_VIDEODETAIL.updatetime
  is '更新日期';


create index IDX_VO_LIVE on t_vo_live (starttime, endtime);


insert into t_vo_category
  (id, parentid, baseid, baseparentid, basetype, basename)
values
  (101, null, 000, 000, 0, '节目货架');

insert into t_vo_category
  (id, parentid, baseid, baseparentid, basetype, basename)
values
  (202, null, 000, 000, 0, '排行');

------视频数据接入结束




-----------------------
----------重新初始化扩展表，电子流新增字段导致
---------------------------

drop table CM_CT_APPGAME;
create table   CM_CT_APPGAME as select * from s_cm_ct_appgame;
create index IDX_GAME_CONTENTID_1 on CM_CT_APPGAME (CONTENTID);
drop table    CM_CT_APPGAME_TRA;
create table   CM_CT_APPGAME_TRA as select * from CM_CT_APPGAME WHERE 1=2;
create index IDX_GAME_CONTENTID_2 on CM_CT_APPGAME_TRA (CONTENTID);



drop table CM_CT_APPSOFTWARE;
create table   CM_CT_APPSOFTWARE as select * from s_cm_ct_APPSOFTWARE;
create index IDX_SOFT_CONTENTID_1 on CM_CT_APPSOFTWARE (CONTENTID);
drop table    CM_CT_APPSOFTWARE_TRA;
create table   CM_CT_APPSOFTWARE_TRA as select * from CM_CT_APPSOFTWARE WHERE 1=2;
create index IDX_SOFT_CONTENTID_2 on CM_CT_APPSOFTWARE_TRA (CONTENTID);


drop table CM_CT_APPTHEME;
create table   CM_CT_APPTHEME as select * from s_cm_ct_APPTHEME;
create index IDX_THEME_CONTENTID_1 on CM_CT_APPTHEME (CONTENTID);
drop table    CM_CT_APPTHEME_TRA;
create table   CM_CT_APPTHEME_TRA as select * from CM_CT_APPTHEME WHERE 1=2;
create index IDX_THEME_CONTENTID_2 on CM_CT_APPTHEME_TRA (CONTENTID);


-- Add/modify columns 
alter table T_R_GCONTENT add logo5 VARCHAR2(256);
-- Add comments to the columns 
comment on column T_R_GCONTENT.logo5
  is '终端Pad展示LOGO5地址，图片规格：140x140';
  
  

------------------------------基地阅读开始
-- Create table
create table T_RB_TYPE_NEW
(
  TYPEID   VARCHAR2(20) not null,
  TYPENAME VARCHAR2(100) not null
);
-- Add comments to the columns 
comment on column T_RB_TYPE_NEW.TYPEID
  is '图书分类ID';
comment on column T_RB_TYPE_NEW.TYPENAME
  is '图书分类名称';

-- Create table
create table T_RB_AUTHOR_NEW
(
  AUTHORID   VARCHAR2(25) not null,
  AUTHORNAME VARCHAR2(50) not null,
  AUTHORDESC VARCHAR2(1024),
  NAMELETTER VARCHAR2(3),
  ISORIGINAL VARCHAR2(3) default 0 not null,
  ISPUBLISH  VARCHAR2(3) default 0 not null,
  AUTHORPIC  VARCHAR2(512)
)
;
-- Add comments to the columns 
comment on column T_RB_AUTHOR_NEW.AUTHORID
  is '作者ID';
comment on column T_RB_AUTHOR_NEW.AUTHORNAME
  is '作者';
comment on column T_RB_AUTHOR_NEW.AUTHORDESC
  is '描述';
comment on column T_RB_AUTHOR_NEW.NAMELETTER
  is '作者首字母';
comment on column T_RB_AUTHOR_NEW.ISORIGINAL
  is '是否原创大神:1,是;0,否';
comment on column T_RB_AUTHOR_NEW.ISPUBLISH
  is '是否出版作家:1,是;0,否';
comment on column T_RB_AUTHOR_NEW.AUTHORPIC
  is '作者图片';

-- Create table
create table T_RB_BOOK_NEW
(
  BOOKID         VARCHAR2(20) not null,
  BOOKNAME       VARCHAR2(100) not null,
  KEYWORD        VARCHAR2(1024),
  LONGRECOMMEND  VARCHAR2(200) not null,
  SHORTRECOMMEND VARCHAR2(100),
  DESCRIPTION    VARCHAR2(2048),
  AUTHORID       VARCHAR2(25) not null,
  TYPEID         VARCHAR2(20),
  INTIME         VARCHAR2(14) not null,
  WORDCOUNT      NUMBER(12) default 0 not null,
  CHAPTERCOUNT   NUMBER(12) default 0 not null,
  CHARGETYPE     VARCHAR2(2) not null,
  FEE            VARCHAR2(12) not null,
  ISFINISH       VARCHAR2(2) not null,
  DELFLAG        NUMBER(1) default 0 not null,
  LUPDATE        DATE default SYSDATE not null
)
;
-- Add comments to the columns 
comment on column T_RB_BOOK_NEW.BOOKID
  is '图书ID';
comment on column T_RB_BOOK_NEW.BOOKNAME
  is '图书名称';
comment on column T_RB_BOOK_NEW.KEYWORD
  is '图书关键字';
comment on column T_RB_BOOK_NEW.LONGRECOMMEND
  is '长推荐语';
comment on column T_RB_BOOK_NEW.SHORTRECOMMEND
  is '短推荐语';
comment on column T_RB_BOOK_NEW.DESCRIPTION
  is '图书简介';
comment on column T_RB_BOOK_NEW.AUTHORID
  is '作者ID';
comment on column T_RB_BOOK_NEW.TYPEID
  is '图书分类ID 分类';
comment on column T_RB_BOOK_NEW.INTIME
  is '入库时间 YYYYMMDDHH24MISS';
comment on column T_RB_BOOK_NEW.WORDCOUNT
  is '图书字数';
comment on column T_RB_BOOK_NEW.CHAPTERCOUNT
  is '图书章节数';
comment on column T_RB_BOOK_NEW.CHARGETYPE
  is '费用类型 费用类型：0免费；1按本计费；2、按章计费；3、按字计费';
comment on column T_RB_BOOK_NEW.FEE
  is '费率，单位：厘
当chargeType = 0时，fee必须为0';
comment on column T_RB_BOOK_NEW.ISFINISH
  is '是否完本';
comment on column T_RB_BOOK_NEW.DELFLAG
  is '是否删除 0：未删除  1：已删除';
comment on column T_RB_BOOK_NEW.LUPDATE
  is '最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_BOOK_NEW
  add constraint PK_T_RB_BOOK_NEW primary key (BOOKID)
;


-- Create table
create table T_RB_RECOMMEND_NEW
(
  RECOMMENDID VARCHAR2(20) not null,
  TYPEID      VARCHAR2(20),
  BOOKID      VARCHAR2(20) not null,
  CREATETIME  DATE default SYSDATE not null
);
-- Add comments to the columns 
comment on column T_RB_RECOMMEND_NEW.RECOMMENDID
  is '推荐ID';
comment on column T_RB_RECOMMEND_NEW.TYPEID
  is '图书分类ID';
comment on column T_RB_RECOMMEND_NEW.BOOKID
  is '图书ID';

-- Create table
create table T_RB_BOOKCONTENT_NEW
(
  BOOKBAGID VARCHAR2(20) not null,
  BOOKID    VARCHAR2(20) not null,
  SORTID    NUMBER(6)
);
-- Add comments to the columns 
comment on column T_RB_BOOKCONTENT_NEW.BOOKBAGID
  is '书包标识';
comment on column T_RB_BOOKCONTENT_NEW.BOOKID
  is '图书标识';
comment on column T_RB_BOOKCONTENT_NEW.SORTID
  is '排序序号 仅当内容为首发时为必须。排序序号。从大到小排列。必须是整数，取值范围在-999999到999999之间。';



-- Create table
create table T_RB_UPDATEBOOK_NEW
(
  CONTENTID  VARCHAR2(60) not null,
  UPDATETIME VARCHAR2(14) not null
);
-- Add comments to the columns 
comment on column T_RB_UPDATEBOOK_NEW.CONTENTID
  is '内容标识';
comment on column T_RB_UPDATEBOOK_NEW.UPDATETIME
  is '更新时间 格式为YYYYMMDDHHMM';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_UPDATEBOOK_NEW
  add constraint T_KEY_UPDATEBOOK_ID primary key (CONTENTID);

-- Create table
create table T_RB_BOOKBAG_NEW
(
  BOOKBAGID    VARCHAR2(20) not null,
  BOOKBAGNAME  VARCHAR2(64) not null,
  BOOKBAGDESC  VARCHAR2(256) not null,
  BOOKBAGIMAGE VARCHAR2(512) not null,
  FEE          NUMBER(12) not null,
  ONLINETIME   VARCHAR2(14) not null,
  SORTID       NUMBER(6)
);
-- Add comments to the columns 
comment on column T_RB_BOOKBAG_NEW.BOOKBAGID
  is '书包id';
comment on column T_RB_BOOKBAG_NEW.BOOKBAGNAME
  is '书包名称';
comment on column T_RB_BOOKBAG_NEW.BOOKBAGDESC
  is '书包简介';
comment on column T_RB_BOOKBAG_NEW.BOOKBAGIMAGE
  is '书包图片';
comment on column T_RB_BOOKBAG_NEW.FEE
  is '单位：分，只有包月书包才有资费';
comment on column T_RB_BOOKBAG_NEW.ONLINETIME
  is '上线时间 格式：YYYYMMDDHH24MISS';
comment on column T_RB_BOOKBAG_NEW.SORTID
  is '排序序号 排序序号。从大到小排列。必须是整数，取值范围在-999999到999999之间。';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_BOOKBAG_NEW
  add constraint T_KEY_BOOKBAG_ID primary key (BOOKBAGID);


-- Create table
create table T_RB_BOOKSCHEDULED_NEW
(
  CONTENTID   VARCHAR2(60) not null,
  SECTIONID   VARCHAR2(64) not null,
  SECTIONNAME VARCHAR2(64) not null
);
-- Add comments to the columns 
comment on column T_RB_BOOKSCHEDULED_NEW.CONTENTID
  is '内容标识';
comment on column T_RB_BOOKSCHEDULED_NEW.SECTIONID
  is '章节标识';
comment on column T_RB_BOOKSCHEDULED_NEW.SECTIONNAME
  is '章节名称';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_BOOKSCHEDULED_NEW
  add constraint T_KEY_BOOKSCHEDULED primary key (CONTENTID, SECTIONID);


-- Create table
create table T_RB_CATEGORY_NEW
(
  ID               NUMBER(10) not null,
  CATEGORYID       VARCHAR2(20),
  CATEGORYNAME     VARCHAR2(200) not null,
  CATALOGTYPE      VARCHAR2(20) not null,
  DECRISPTION      VARCHAR2(256),
  PARENTID         VARCHAR2(20),
  PICURL           VARCHAR2(1024),
  LUPDATE          DATE default SYSDATE not null,
  TOTAL            NUMBER(10),
  PLATFORM         VARCHAR2(200) default '{0000}',
  CITYID           VARCHAR2(4000) default '{0000}',
  TYPE             VARCHAR2(2) default 1,
  SORTID           NUMBER(8) default 0,
  PARENTCATEGORYID VARCHAR2(20)
) ;
-- Add comments to the columns 
comment on column T_RB_CATEGORY_NEW.ID
  is '主ID';
comment on column T_RB_CATEGORY_NEW.CATEGORYID
  is '货架ID 对应专区、排行
';
comment on column T_RB_CATEGORY_NEW.CATEGORYNAME
  is '货架名称';
comment on column T_RB_CATEGORY_NEW.CATALOGTYPE
  is '货架类型 1：推荐专区（热门推荐作为推荐的一种）
2：最新，改为最新栏目，只能有一个最新专区
4：特惠专区（一毛促销街，及其子专区晨间购等， wap的精彩图书推荐）：保留待确认
5:名人馆专区（介绍为作者介绍）
6：专区（包括：最新专题（下面还包含子货架专区），最新活动，往期专题，往期活动）
7：免费专区（免费阅读人气榜）
 8:排行';
comment on column T_RB_CATEGORY_NEW.DECRISPTION
  is '简介';
comment on column T_RB_CATEGORY_NEW.PARENTID
  is '父货架ID';
comment on column T_RB_CATEGORY_NEW.PICURL
  is '专区图片，初期不提供';
comment on column T_RB_CATEGORY_NEW.LUPDATE
  is '最后更新时间';
comment on column T_RB_CATEGORY_NEW.TOTAL
  is '该货架下所有的商品总数';
comment on column T_RB_CATEGORY_NEW.PLATFORM
  is '平台适配关系';
comment on column T_RB_CATEGORY_NEW.CITYID
  is '地市适配关系';
comment on column T_RB_CATEGORY_NEW.TYPE
  is '是否在门户展示 1：是 0：否';
comment on column T_RB_CATEGORY_NEW.SORTID
  is '货架排序';
comment on column T_RB_CATEGORY_NEW.PARENTCATEGORYID
  is '货架父ID 对应专区、排行
';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_CATEGORY_NEW
  add constraint PK_T_RB_CATEGORY_NEW primary key (ID) ;
-- Create/Recreate indexes 
create index IDX_T_RB_CATEGORY_NEW_ID on T_RB_CATEGORY_NEW (CATEGORYID) ;


-- Create table
create table T_RB_CATE
(
  CATEID       VARCHAR2(20) not null,
  CATENAME     VARCHAR2(64) not null,
  CATEDESC     VARCHAR2(256) not null,
  PARENTCATEID VARCHAR2(20),
  CATEPIC      VARCHAR2(512) not null,
  CATETYPE     VARCHAR2(20) not null,
  SORTID       NUMBER(6),
  BUSINESSTIME VARCHAR2(14) not null
);
-- Add comments to the columns 
comment on column T_RB_CATE.CATEID
  is '专区标识';
comment on column T_RB_CATE.CATENAME
  is '专区名称';
comment on column T_RB_CATE.CATEDESC
  is '专区简介';
comment on column T_RB_CATE.PARENTCATEID
  is '专区父id';
comment on column T_RB_CATE.CATEPIC
  is '专区图片';
comment on column T_RB_CATE.CATETYPE
  is '专区类型 1：推荐专区（热门推荐作为推荐的一种）
2：最新，改为最新栏目，只能有一个最新专区
4：特惠专区（一毛促销街，及其子专区晨间购等， wap的精彩图书推荐）：保留待确认
5:名人馆专区（介绍为作者介绍）
6：专区（包括：最新专题（下面还包含子货架专区），最新活动，往期专题，往期活动）
7：免费专区（免费阅读人气榜）
';
comment on column T_RB_CATE.SORTID
  is '排序序号。从大到小排列。必须是整数，取值范围在-999999到999999之间。';
comment on column T_RB_CATE.BUSINESSTIME
  is '上线时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_CATE
  add constraint T_KEY_CATA_ID primary key (CATEID);

-- Create table
create table T_RB_REFERENCE_NEW
(
  CID        NUMBER(10) not null,
  CATEGORYID VARCHAR2(20) not null,
  BOOKID     VARCHAR2(60) not null,
  SORTNUMBER NUMBER(6),
  RANKVALUE  NUMBER(10)
) ;
-- Add comments to the columns 
comment on column T_RB_REFERENCE_NEW.CID
  is '货架ID';
comment on column T_RB_REFERENCE_NEW.CATEGORYID
  is '对应包月、专区或排行id';
comment on column T_RB_REFERENCE_NEW.BOOKID
  is '图书ID';
comment on column T_RB_REFERENCE_NEW.SORTNUMBER
  is '排序序号。从小到大排列。必须是整数，取值范围在-999999到999999之间';
comment on column T_RB_REFERENCE_NEW.RANKVALUE
  is '排行依据值';
-- Create/Recreate indexes 
create index IDX_T_RB_REFERENCE_NEW_BOOK_ID on T_RB_REFERENCE_NEW (BOOKID) ;
create index IDX_T_RB_REFERENCE_NEW_CATE_ID on T_RB_REFERENCE_NEW (CATEGORYID) ;



-- Drop columns 
-- Create table
create table T_RB_RANK_NEW
(
  RANKID     VARCHAR2(20) not null,
  RANKNAME   VARCHAR2(60) not null,
  BOOKID     VARCHAR2(20) not null,
  SORTNUMBER NUMBER(6) default 0
) ;
-- Add comments to the columns 
comment on column T_RB_RANK_NEW.RANKID
  is '榜单id';
comment on column T_RB_RANK_NEW.RANKNAME
  is '榜单名称';
comment on column T_RB_RANK_NEW.BOOKID
  is '图书id';
comment on column T_RB_RANK_NEW.SORTNUMBER
  is '排序号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_RANK_NEW
  add constraint T_KEY_RANK_ID primary key (RANKID, BOOKID) ;


-- Create sequence 
create sequence SEQ_BR_CATEGORY_NEW_ID
minvalue 1
maxvalue 9999999999
start with 10000
increment by 1
nocache
cycle;


insert into T_RB_CATEGORY_NEW (ID, CATEGORYNAME, CATALOGTYPE)
values (101,'专区根', '0');
insert into T_RB_CATEGORY_NEW (ID, CATEGORYNAME, CATALOGTYPE)
values (202, '排行根', '0');




-- Create table
create table T_RB_STATISTICS_NEW
(
  CONTENTID    VARCHAR2(60) not null,
  READERNUM    NUMBER(12) not null,
  FLOWERSNUM   NUMBER(12) not null,
  CLICKNUM     NUMBER(12) not null,
  FAVORITESNUM NUMBER(12) not null,
  ORDERNUM     NUMBER(12) not null,
  VOTENUM      NUMBER(12) not null
) ;
-- Add comments to the columns 
comment on column T_RB_STATISTICS_NEW.CONTENTID
  is '内容标识';
comment on column T_RB_STATISTICS_NEW.READERNUM
  is '读者';
comment on column T_RB_STATISTICS_NEW.FLOWERSNUM
  is '鲜花';
comment on column T_RB_STATISTICS_NEW.CLICKNUM
  is '点击';
comment on column T_RB_STATISTICS_NEW.FAVORITESNUM
  is '收藏数';
comment on column T_RB_STATISTICS_NEW.ORDERNUM
  is '预订数';
comment on column T_RB_STATISTICS_NEW.VOTENUM
  is '投票数';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_STATISTICS_NEW
  add constraint T_KEY_STATISTICS_ID primary key (CONTENTID) ;


-- Add/modify columns 
alter table T_VO_VIDEO add downloadfilepath VARCHAR2(512) not null;
-- Add comments to the columns 
comment on column T_VO_VIDEO.FILEPATH
  is '物理播放文件地址';
comment on column T_VO_VIDEO.downloadfilepath
  is '物理下载文件地址';

alter table T_VO_VIDEO
  add constraint T_KEY_VO_VIDEOID primary key (VIDEOID, CODERATEID);


---------------------基地阅读结束





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.049_SSMS','MM1.1.1.059_SSMS');


commit;