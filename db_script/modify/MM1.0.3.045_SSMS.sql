

------修改视图，添加两个字段
create or replace view ppms_v_cm_content_cy as
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
       p.developername  companyname,
       c.contestgroup as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as lupddate,
       f.chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid,
       c.contestcode,
       c.contestyear
      
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f,
       OM_DEVELOPER_CONTEST p
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' )
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid;


------先备份表数据
create table t_cytomm_mapping_045 as select * from t_cytomm_mapping;
truncate table  t_cytomm_mapping;

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('1', '系统', '软件', '1001', '软件', '2001');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('6', '角色', '游戏', '1003', '游戏', '2001');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('2', '生活', '软件', '1001', '生活', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('15', '搞笑', '主题', '1002', '搞笑', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('3', '视频', '软件', '1001', '视频', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('4', '通讯', '软件', '1001', '通讯', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('39', '音乐', '软件', '1001', '音乐', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('29', '新闻', '软件', '1001', '新闻', '2002');


----------------------------------------------------------------------------
--注意：------------ 该数据为现中的CATEGORYID相应的货架ID------
----------------------------------------------------------------------------
------先备份表数据
create table t_sync_tactic_cy_045 as select * from t_sync_tactic_cy;
truncate table  t_sync_tactic_cy;

insert into t_sync_tactic_cy (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (7, '109990032', 'nt:gcontent:appTheme', '0', '', 0, '2009-04-06 17:08:17', '2009-04-06 17:08:17', '搞笑');

insert into t_sync_tactic_cy (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (6, '109990034', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:32', '2009-04-06 17:08:32', '视频');

insert into t_sync_tactic_cy (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (5, '109990035', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:44', '2009-04-06 17:08:44', '通讯');

insert into t_sync_tactic_cy (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (4, '109990037', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:54', '2009-04-06 17:08:54', '音乐');

insert into t_sync_tactic_cy (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (8, '109990036', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:09:04', '2009-04-06 17:09:04', '新闻');

insert into t_sync_tactic_cy (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (1, '109990030', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:07:49', '2009-04-06 17:07:49', '软件');

insert into t_sync_tactic_cy (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (2, '109990031', 'nt:gcontent:appGame', '0', '', 0, '2009-04-06 17:08:00', '2009-04-06 17:08:00', '游戏');

insert into t_sync_tactic_cy (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (3, '109990033', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:10', '2009-04-06 17:08:10', '生活');


-- Create table T_TOPLIST
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

-- Create table T_CY_PRODUCTLIST
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





-- Add comments to the columns 
comment on column T_R_GCONTENT.NAMELETTER
  is ' 铃音名称检索首字母            复用为‘创业大赛年份,2010或者2011’';
comment on column T_R_GCONTENT.ISSUPPORTDOTCARD
  is '是否支持点卡支付，0：不支持 1：支持  复用为‘创业大赛组别信息，1:校园组，2:社会个人组，3:社会企业组’';
  
  
  
  
  -- Add/modify columns 
alter table T_CATEGORY_CARVEOUT_RULE add sqlbaseid VARCHAR2(30) default 1 ;
-- Add comments to the columns 
comment on column T_CATEGORY_CARVEOUT_RULE.sqlbaseid
  is '关联的基础SQL语句ID';
  
  -- Create table
create table T_CATEGORY_CARVEOUT_SQLBASE
(
  ID      varchar2(30),
  BASESQL varchar2(2000)
)
;
-- Add comments to the table 
comment on table T_CATEGORY_CARVEOUT_SQLBASE
  is '2011创业大赛自动更新的SQL基础语句';
-- Add comments to the columns 
comment on column T_CATEGORY_CARVEOUT_SQLBASE.ID
  is '基础ID';
comment on column T_CATEGORY_CARVEOUT_SQLBASE.BASESQL
  is '基础SQL语句体';
  
insert into t_category_carveout_sqlbase (ID, BASESQL)
values ('3', 'select r.id from  t_r_base b,t_r_reference r,  t_r_gcontent g,v_service v,T_CY_PRODUCTLIST c where b.id=g.id and r.categoryid =? and r.refnodeid = g.id and g.subtype = ''6'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.contentid = c.contentid(+)');

insert into t_category_carveout_sqlbase (ID, BASESQL)
values ('1', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,T_CY_PRODUCTLIST c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid(+) and  g.subtype = ''6'' and g.NAMELETTER=''2011''');

insert into t_category_carveout_sqlbase (ID, BASESQL)
values ('2', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,T_TOPLIST c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid and  g.subtype = ''6'' and g.NAMELETTER=''2011''');


-- Add/modify columns 
-----现网相应的货架ID----测试环境修改为相应货架ID------
alter table T_CATEGORY_CARVEOUT_RULE modify NAME VARCHAR2(200);
truncate table t_category_carveout_rule;

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('3', '软件游戏人气综合推荐指数TOP60榜', '109990026', 0, 1, 1, 'c.TYPE=''1''  ', 'c.COUNT  desc', -1, null, to_date('04-05-2011 15:26:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2011 13:56:14', 'dd-mm-yyyy hh24:mi:ss'), 0, '2');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('1', '创意孵化人气综合推荐指数TOP30榜', '109990024', 0, 1, 1, 'c.TYPE=''2''  ', 'c.COUNT  desc', -1, null, to_date('04-05-2011 15:26:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2011 13:56:14', 'dd-mm-yyyy hh24:mi:ss'), 0, '2');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('2', '创意孵化星探推荐得分TOP50榜', '109990025', 0, 1, 1, 'c.TYPE=''4''  ', 'c.COUNT  desc', -1, null, to_date('04-05-2011 15:26:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2011 13:56:14', 'dd-mm-yyyy hh24:mi:ss'), 0, '2');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('4', '软件游戏星探推荐得分TOP50榜', '109990027', 0, 1, 1, 'c.TYPE=''3''  ', 'c.COUNT  desc', -1, null, to_date('04-05-2011 15:26:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2011 13:56:14', 'dd-mm-yyyy hh24:mi:ss'), 0, '2');


  commit;
  
-----------------------------------
----139新音乐数据接入---------------
-----------------------------------
-----------------------------------
-- Create table
create table T_MB_MUSIC_new
(
  MUSICID    VARCHAR2(30) not null,
  SONGNAME   VARCHAR2(200),
  SINGER     VARCHAR2(100),
  VALIDITY   VARCHAR2(512),
  UPDATETIME VARCHAR2(30),
  CREATETIME VARCHAR2(30),
  DELFLAG    NUMBER(4) default 0 not null,
  TAGS       VARCHAR2(400),
  MUSIC_PIC  VARCHAR2(400)
);
-- Create/Recreate indexes 
create index index_new_music_id on T_MB_MUSIC_NEW (musicid);
-- Add comments to the columns 
comment on column T_MB_MUSIC_new.MUSICID
  is '歌曲ID';
comment on column T_MB_MUSIC_new.SONGNAME
  is '歌曲名称';
comment on column T_MB_MUSIC_new.SINGER
  is '歌手名称';
comment on column T_MB_MUSIC_new.VALIDITY
  is '有效期';
comment on column T_MB_MUSIC_new.UPDATETIME
  is '更新时间';
comment on column T_MB_MUSIC_new.CREATETIME
  is '创建时间';
comment on column T_MB_MUSIC_new.DELFLAG
  is '删除标记，0，未删除；1，已删除';
comment on column T_MB_MUSIC_new.TAGS
  is '歌曲标签信息';
comment on column T_MB_MUSIC_new.MUSIC_PIC
  is '歌曲图片地址';
-- Grant/Revoke object privileges 
grant select on T_MB_MUSIC_new to MM_DLS;
grant select on T_MB_MUSIC_new to PORTALMO;



-- Create table
create table T_MB_REFERENCE_new
(
  MUSICID    VARCHAR2(30) not null,
  CATEGORYID VARCHAR2(30) not null,
  MUSICNAME  VARCHAR2(200),
  CREATETIME VARCHAR2(30),
  SORTID     NUMBER(8)
);
-- Add comments to the columns 
comment on column T_MB_REFERENCE_new.MUSICID
  is '音乐ID';
comment on column T_MB_REFERENCE_new.CATEGORYID
  is '货架ID';
comment on column T_MB_REFERENCE_new.MUSICNAME
  is '歌曲名称';
comment on column T_MB_REFERENCE_new.CREATETIME
  is '创建时间';
comment on column T_MB_REFERENCE_new.SORTID
  is '排序';
-- Create/Recreate indexes 
create index INDEX_TB_REFERENCE_new_CAID on T_MB_REFERENCE_new (CATEGORYID);
create unique index PK_MUSIC_new_CATEID on T_MB_REFERENCE_new (MUSICID, CATEGORYID);
-- Grant/Revoke object privileges 
grant select on T_MB_REFERENCE_new to MM_DLS;
grant select on T_MB_REFERENCE_new to MOPPS2 with grant option;



-- Create table
create table T_MB_CATEGORY_new
(
  CATEGORYID       VARCHAR2(30) not null,
  CATEGORYNAME     VARCHAR2(200) not null,
  PARENTCATEGORYID VARCHAR2(30),
  TYPE             VARCHAR2(10),
  DELFLAG          VARCHAR2(2),
  CREATETIME       VARCHAR2(30),
  lupddate       VARCHAR2(30),
  CATEGORYDESC     VARCHAR2(1000),
  SORTID           NUMBER(8),
  SUM              NUMBER(8) default 0 not null,
  ALBUM_ID         VARCHAR2(30),
  ALBUM_PIC        VARCHAR2(400),
  RATE             NUMBER,
  ALBUM_SINGER     VARCHAR2(100)
);
-- Add comments to the columns 
comment on column T_MB_CATEGORY_new.CATEGORYID
  is '货架ID';
comment on column T_MB_CATEGORY_new.CATEGORYNAME
  is '货架名称';
comment on column T_MB_CATEGORY_new.PARENTCATEGORYID
  is '货架父ID';
comment on column T_MB_CATEGORY_new.TYPE
  is '类型';
comment on column T_MB_CATEGORY_new.DELFLAG
  is '删除标记';
comment on column T_MB_CATEGORY_new.CREATETIME
  is '创建时间';
comment on column T_MB_CATEGORY_new.CATEGORYDESC
  is '货架描述';
comment on column T_MB_CATEGORY_new.SORTID
  is '货架排序';
comment on column T_MB_CATEGORY_new.SUM
  is '货架商品数量';
comment on column T_MB_CATEGORY_new.ALBUM_ID
  is '专辑ID';
comment on column T_MB_CATEGORY_new.ALBUM_PIC
  is '专辑名称';
comment on column T_MB_CATEGORY_new.RATE
  is '专辑评分';
comment on column T_MB_CATEGORY_new.ALBUM_SINGER
  is '专辑歌手';
-- Create/Recreate indexes 
create unique index PK_T_MB_CATEGORY_new_ID on T_MB_CATEGORY_new (CATEGORYID);
-- Grant/Revoke object privileges 
grant select on T_MB_CATEGORY_new to MM_DLS;

------初始化基础货架
insert into t_mb_category_new (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME,lupddate, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER)
values ('100000001', '最新', '', '1', '0', '2010-05-10 09:03:00',to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'), '最新', 0, 35, '', '', 1, '');

insert into t_mb_category_new (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME,lupddate, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER)
values ('100000002', '排行', '', '1', '0', '2010-05-10 09:03:00',to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'), '排行', 0, 0, '', '', 1, '');

insert into t_mb_category_new (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME,lupddate, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER)
values ('100000003', '专辑', '', '1', '0', '2010-05-10 09:03:00',to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'), '专辑', 0, 0, '', '', 1, '');

insert into t_mb_category_new (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME,lupddate, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER)
values ('100000004', '热门', '', '1', '0', '2010-05-10 09:03:00',to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'), '热门', 0, 0, '', '', 1, '');



-- Create table
create table T_MB_KEYWORD_new
(
  KEYWORD    VARCHAR2(30) not null,
  KEY_ID     NUMBER(20) not null,
  CREATETIME DATE default sysdate not null
);
-- Add comments to the columns 
comment on column T_MB_KEYWORD_new.KEYWORD
  is '关键字';
comment on column T_MB_KEYWORD_new.KEY_ID
  is '关键字序号';
comment on column T_MB_KEYWORD_new.CREATETIME
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MB_KEYWORD_new
  add constraint PK_T_MB_KEYWORD_new primary key (KEYWORD);

-- Create sequence 
create sequence SEQ_MB_KEYWORD_new
minvalue 1
maxvalue 99999999999999999
start with 5021
increment by 1
cache 20;

-- Create sequence 
create sequence SEQ_MB_CATEGORY_new_ID
minvalue 100000001
maxvalue 999999999
start with 100000100
increment by 1
nocache
cycle;




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.040_SSMS','MM1.0.3.045_SSMS');
commit;