-- add/modify columns 表t_r_gcontent新增logo6字段
alter table t_r_gcontent add logo6 varchar2(256);
-- add comments to the columns 
comment on column t_r_gcontent.logo6
  is 'logo6地址,存放120*120应用大图标';
  
-- add/modify columns 表cm_ct_appgame新增logo6字段
alter table cm_ct_appgame add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_appgame.logo6
  is 'logo6地址,存放120*120应用大图标';

-- add/modify columns 表cm_ct_appsoftware新增logo6字段
alter table cm_ct_appsoftware add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_appsoftware.logo6
  is 'logo6地址,存放120*120应用大图标';

-- add/modify columns 表cm_ct_apptheme新增logo6字段
alter table cm_ct_apptheme add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_apptheme.logo6
  is 'logo6地址,存放120*120应用大图标';

-- add/modify columns 表cm_ct_appgame_tra新增logo6字段
alter table cm_ct_appgame_tra add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_appgame_tra.logo6
  is 'logo6地址,存放120*120应用大图标';
  
-- add/modify columns 表cm_ct_appsoftware_tra新增logo6字段
alter table cm_ct_appsoftware_tra add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_appsoftware_tra.logo6
  is 'logo6地址,存放120*120应用大图标';
  
-- add/modify columns 表cm_ct_apptheme_tra新增logo6字段
alter table cm_ct_apptheme_tra add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_apptheme_tra.logo6
  is 'logo6地址,存放120*120应用大图标';

  -- add/modify columns 表cm_ct_appgame新增PKGEXTRACTICON字段
alter table cm_ct_appgame add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_appgame.PKGEXTRACTICON
  is 'PKGEXTRACTICON地址,存放程序包提取的logo原图';

-- add/modify columns 表cm_ct_appsoftware新增PKGEXTRACTICON字段
alter table cm_ct_appsoftware add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_appsoftware.PKGEXTRACTICON
  is 'PKGEXTRACTICON地址,存放程序包提取的logo原图';

-- add/modify columns 表cm_ct_apptheme新增PKGEXTRACTICON字段
alter table cm_ct_apptheme add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_apptheme.PKGEXTRACTICON
  is 'PKGEXTRACTICON地址,存放程序包提取的logo原图';

-- add/modify columns 表cm_ct_appgame_tra新增PKGEXTRACTICON字段
alter table cm_ct_appgame_tra add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_appgame_tra.PKGEXTRACTICON
  is 'PKGEXTRACTICON地址,存放程序包提取的logo原图';
  
-- add/modify columns 表cm_ct_appsoftware_tra新增PKGEXTRACTICON字段
alter table cm_ct_appsoftware_tra add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_appsoftware_tra.PKGEXTRACTICON
  is 'PKGEXTRACTICON地址,存放程序包提取的logo原图';
  
-- add/modify columns 表cm_ct_apptheme_tra新增PKGEXTRACTICON字段
alter table cm_ct_apptheme_tra add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_apptheme_tra.PKGEXTRACTICON
  is 'PKGEXTRACTICON地址,存放程序包提取的logo原图';
  
-- 文章数据表
CREATE TABLE V_ARTICLE
(
  CONTENTID  NUMBER(10),
  TITLE      VARCHAR2(255 BYTE),
  SUBTITLE   VARCHAR2(255 BYTE),
  AUTHOR     VARCHAR2(25 BYTE),
  SOURCE     NUMBER(1),
  LABEL      VARCHAR2(255 BYTE),
  COVER      VARCHAR2(200),
  BRIEF      VARCHAR2(200 BYTE),
  STAR       NUMBER(2),
  PUBTIME    DATE,
  EDITTIME   DATE,
  TYPE       NUMBER(2),
  APPID      VARCHAR2(255 BYTE),
  CONTENT    CLOB,
  STATUS     NUMBER(1)
);
-- Add comments to the table 
comment on table V_ARTICLE
  is '文章数据表';
-- Add comments to the columns 
comment on column V_ARTICLE.CONTENTID
  is '文章ID';
comment on column V_ARTICLE.TITLE
  is '标题';
comment on column V_ARTICLE.SUBTITLE
  is '短标题';
comment on column V_ARTICLE.AUTHOR
  is '发布者';
comment on column V_ARTICLE.SOURCE
  is '1，原创；2，转载；3，编译；4，官方';
comment on column V_ARTICLE.LABEL
  is '标签，多个标签时使用分号‘;’间隔，';
comment on column V_ARTICLE.COVER
  is '图片相对地址';
comment on column V_ARTICLE.BRIEF
  is '文章简介';
comment on column V_ARTICLE.STAR
  is '商品评分，1~5';
comment on column V_ARTICLE.PUBTIME
  is '发布时间';
comment on column V_ARTICLE.EDITTIME
  is '修改时间';
comment on column V_ARTICLE.TYPE
  is '文章类型，1、游戏测评2、小众软件3、精选专题4、汇众话题';
comment on column V_ARTICLE.APPID
  is '文章对应应用id，多个时使用分号‘;’间隔，';
comment on column V_ARTICLE.CONTENT
  is '文章内容';
comment on column V_ARTICLE.STATUS
  is '0，	编辑；1，	待审；2，	驳回；3，	已审；';

CREATE TABLE V_ARTICLE_REFERENCE
(
  APPID      VARCHAR2(30 BYTE),
  CONTENTID  NUMBER(10),
  STATUS     NUMBER(1)
);
-- Add comments to the table 
comment on table V_ARTICLE_REFERENCE
  is '文章和应用关联表';
COMMENT ON COLUMN V_ARTICLE_REFERENCE.APPID IS '应用ID';
COMMENT ON COLUMN V_ARTICLE_REFERENCE.CONTENTID IS '文章ID';
COMMENT ON COLUMN V_ARTICLE_REFERENCE.STATUS IS '状态0，编辑；1，待审；2，驳回；3，已审；4，上线；5，下线；';

--给导出表添加文章导出记录
insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (29, 'select CONTENTID,TITLE,LABEL,PUBTIME from V_ARTICLE', '搜索系统文件-文章数据', '2', 50000, '0x01', to_date('23-10-2013 17:04:00', 'dd-mm-yyyy hh24:mi:ss'), 4, 'zhuanti', '/opt/aspire/product/chroot_panguso/panguso/mo', 'GB18030', '2', '1', '69', 3);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.105_SSMS','MM2.0.0.0.109_SSMS');

commit;








---------- 
create table T_VO_RECOMMEND
(
  RECOMMENDID   VARCHAR2(60) not null primary key,
  RECOMMENDNAME VARCHAR2(512) not null,
  PROGRAMID     VARCHAR2(60) not null,
  SORTID        NUMBER(10),
  UPDATETIME    DATE default sysdate
);
COMMENT ON COLUMN T_VO_RECOMMEND.RECOMMENDID IS '推荐标识';
COMMENT ON COLUMN T_VO_RECOMMEND.RECOMMENDNAME IS '推荐名称';
COMMENT ON COLUMN T_VO_RECOMMEND.PROGRAMID IS '节目标识';
COMMENT ON COLUMN T_VO_RECOMMEND.SORTID IS '排序序号';
COMMENT ON COLUMN T_VO_RECOMMEND.UPDATETIME IS '最后更新时间';

alter table t_vo_program add SORTID  NUMBER(10);
alter table t_vo_program add isLink VARCHAR2(10);
COMMENT ON COLUMN t_vo_program.SORTID IS '排序序号。从小到大排列';
COMMENT ON COLUMN t_vo_program.isLink IS '1：是链接,2：不是链接';

commit;