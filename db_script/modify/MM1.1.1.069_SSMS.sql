

insert into t_right values ('2_1505_COMIC','基地动漫管理','基地动漫管理','','2');
insert into t_roleright values ('1','2_1505_COMIC');

drop table T_CB_CHAPTER;

create table T_CB_CHAPTER
(
  CHAPTERID   VARCHAR2(60) not null,
  CONTENTID   VARCHAR2(60) not null,
  NAME        VARCHAR2(60),
  DESCRIPTION VARCHAR2(4000),
  FEE         NUMBER(10),
  UPDATE_FLAG NUMBER(1),
  TYPE        VARCHAR2(5),
  SORTID      NUMBER(5),
  FEE_CODE    VARCHAR2(60),
  SMALL       VARCHAR2(5),
  MEDIUM      VARCHAR2(5),
  LARGE       VARCHAR2(5),
  FLOW_TIME   DATE default sysdate,
  STATUS      NUMBER(1) default 1
)

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
comment on column T_CB_CHAPTER.SORTID
  is '排序序号';
comment on column T_CB_CHAPTER.FEE_CODE
  is '计费代码';
comment on column T_CB_CHAPTER.SMALL
  is '小图片';
comment on column T_CB_CHAPTER.MEDIUM
  is '中图片';
comment on column T_CB_CHAPTER.LARGE
  is '大图片';
comment on column T_CB_CHAPTER.FLOW_TIME
  is '流水时间（辅助字段）';
comment on column T_CB_CHAPTER.STATUS
  is '新同步的数据（辅助字段）';


drop table T_CB_ADAPTER;
create table T_CB_ADAPTER
(
  ID        VARCHAR2(60) not null,
  CHAPTERID VARCHAR2(60),
  GROUPS    VARCHAR2(4000),
  FILE_SIZE NUMBER(10),
  USE_TYPE  NUMBER(2),
  CLEAR     VARCHAR2(1),
  FLOW_TIME DATE default sysdate,
  URL       VARCHAR2(512),
  TYPE      VARCHAR2(5),
  STATUS    NUMBER(1) default 1
)

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
  is '流水时间（辅助字段）';
comment on column T_CB_ADAPTER.URL
  is '使用URL';
comment on column T_CB_ADAPTER.TYPE
  is '内容类型';
comment on column T_CB_ADAPTER.STATUS
  is '记录状态（辅助字段）';


drop table t_cb_content;
-- Create table
create table T_CB_CONTENT
(
  ID            VARCHAR2(60) not null,
  NAME          VARCHAR2(256) not null,
  DESCRIPTION   VARCHAR2(4000),
  PROVIDER      VARCHAR2(60),
  PROVIDER_TYPE VARCHAR2(60),
  AUTHODID      VARCHAR2(60),
  TYPE          VARCHAR2(5),
  KEYWORDS      VARCHAR2(4000),
  EXPIRETIME    VARCHAR2(14),
  FEE           NUMBER(10),
  LOCATION      VARCHAR2(100),
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
  LANGUAGE      VARCHAR2(50),
  YEAR          VARCHAR2(14),
  STATUS        VARCHAR2(4),
  CHAPTER_TYPE  VARCHAR2(4),
  PORTAL        VARCHAR2(1) default '0',
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
  SYNC_STATUS   NUMBER(1) default 1
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
comment on column T_CB_CONTENT.SYNC_STATUS
  is '记录状态（辅助字段）';
  

create index id_index on t_cb_content(id);



create table t_sync_tactic_base(
id number(10),
sql varchar2(1024),
categoryId varchar2(50),
effectiveTime date,
lupTime date,
time_consuming number(12),

primary key(id));

-- Add/modify columns 
alter table T_VO_REFERENCE add BASEID VARCHAR2(60);
alter table T_VO_REFERENCE add BASETYPE VARCHAR2(2);
-- Add comments to the columns 
comment on column T_VO_REFERENCE.BASEID
  is '引用id：栏目id，排行榜id，未来要加的各种不知名的id';
comment on column T_VO_REFERENCE.BASETYPE
  is '引用类型：1：栏目ID， 2：排行榜ID 后续在新增枚举';

comment on column T_CONTENT_EXT.type
  is '内容类型：1折扣，2秒杀(暂未使用);3,限时免费;4,特约限免';




-----应用促销功能开发-------
-----------------------------
alter  table  t_content_ext rename to t_content_ext_local;

  create or replace  synonym  ppms_v_t_content_ext for v_t_content_ext@dl_ppms_device;
 
  create or replace  view  t_content_ext as select * from  t_content_ext_local union all 
                     select * from ppms_v_t_content_ext;
                     
  create or replace  synonym  ppms_V_T_CON_EXT_SPECIALFREE for  V_T_CONTENT_EXT_SPECIALFREE@dl_ppms_device;


create  or replace view  v_content_pkapps as  select * from ppms_v_cm_content_pkapps t;



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.065_SSMS','MM1.1.1.069_SSMS');


commit;