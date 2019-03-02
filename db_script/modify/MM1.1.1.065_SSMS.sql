drop table t_cb_chapter;
create table T_CB_CHAPTER
(
  CHAPTERID   VARCHAR2(60) not null,
  CONTENTID   VARCHAR2(60) not null,
  NAME        VARCHAR2(60),
  DESCRIPTION VARCHAR2(4000),
  FEE         NUMBER(10),
  UPDATE_FLAG NUMBER(1),
  TYPE        VARCHAR2(5),
  primary key(CHAPTERID)
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
  
  
drop table t_cb_content;
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



insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000032', '传记', '100000001', null, null, '0', null, null, null, null, '15008');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000033', '经典', '100000001', null, null, '0', null, null, null, null, '15010');


-- Add/modify columns 
alter table T_VO_NODE modify LOGOPATH null;
alter table T_VO_NODE modify SORTID NUMBER(19);
alter table T_VO_NODE modify PRODUCTID null;


-- Add/modify columns 
alter table T_VO_VIDEO add downloadfilepath VARCHAR2(512) not null;
-- Add comments to the columns 
comment on column T_VO_VIDEO.FILEPATH
  is '物理播放文件地址';
comment on column T_VO_VIDEO.downloadfilepath
  is '物理下载文件地址';

alter table T_VO_VIDEO
  add constraint T_KEY_VO_VIDEOID primary key (VIDEOID, CODERATEID);

-- Add/modify columns 
alter table T_VO_CODERATE modify CODERATELEVEL number(6);


update t_vo_category c set c.parentid='0' where c.id='101' or c.id='202';

-----音乐-----
    create or replace view v_ssms_music_category as 
      select t.categoryid ,t.categoryname,t.categorydesc,t.sortid,t.delflag,t.createtime from T_MB_CATEGORY_NEW t;
      
  create or replace view   v_ssms_music_reference as  
         select t.musicid,t.categoryid,t.sortid,t.musicname,t.createtime from T_MB_REFERENCE_NEW t;    
      
       -----阅读-----
      create or replace view  v_ssms_book_category    as
    select t.categoryid,t.categoryname,t.decrisption,t.sortid,t.type,t.parentcategoryid,t.lupdate from t_rb_category_new t;
    
    create or replace view   v_ssms_book_reference  as 
        select t.bookid,t.cid,t.categoryid,t.sortnumber as sortid from t_rb_reference_new t;
        
        
     -----动漫-----   
        create or replace view v_ssms_cat_category  as
 select t.categoryid,t.categoryname,t.categorydesc,t.sortid,t.type,t.delflag,t.parentcategoryid,t.lupdate from t_cb_category t;
 
  create or replace view  v_ssms_cat_reference as 
   select t.contentid,t.categoryid,t.sortid,t.portal from t_cb_reference t;
   
      -----视频-----   
     create or replace view  v_ssms_video_category as 
         select t.id,t.basename,t.cdesc,t.sortid,t.isshow,t.baseparentid from  t_vo_category t;
 
 
    create or replace view v_ssms_video_reference as 
          select t.programid,t.categoryid,t.sortid,t.exporttime from t_vo_reference t;
    
    

