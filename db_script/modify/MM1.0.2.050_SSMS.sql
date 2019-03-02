--创业大赛自动更新应用
create table T_CATEGORY_CARVEOUT_RULE
(
  ID             VARCHAR2(8) not null,
  NAME           VARCHAR2(30) not null,
  CID            VARCHAR2(30) not null,
  INTERVALTYPE   NUMBER(2) not null,
  EXCUTETIME     NUMBER(5) not null,
  EXCUTEINTERVAL NUMBER(5) not null,
  WSQL           VARCHAR2(1000),
  OSQL           VARCHAR2(1000),
  COUNT          NUMBER(8),
  SORTID         NUMBER(8),
  LASTEXCUTETIME DATE,
  EFFECTIVETIME  DATE
);

-- Add comments to the columns 
comment on column T_CATEGORY_CARVEOUT_RULE.ID
  is 'id';
comment on column T_CATEGORY_CARVEOUT_RULE.NAME
  is '规则名称';
comment on column T_CATEGORY_CARVEOUT_RULE.CID
  is '规则对应的货架的货架内码';
comment on column T_CATEGORY_CARVEOUT_RULE.INTERVALTYPE
  is '执行时间间隔类型 0：天；1：周；2：月';
comment on column T_CATEGORY_CARVEOUT_RULE.EXCUTETIME
  is '在一个时间间隔之内的执行日子。 如果IntervalType=0，本字段无效。 如果IntervalType=1，本字段表示在周几执行。如果IntervalType=2，本字段表示在月的第几天执行。';
comment on column T_CATEGORY_CARVEOUT_RULE.EXCUTEINTERVAL
  is '执行之间间隔，单位为天。';
comment on column T_CATEGORY_CARVEOUT_RULE.WSQL
  is '获取产品的条件sql';
comment on column T_CATEGORY_CARVEOUT_RULE.OSQL
  is '排序sql';
comment on column T_CATEGORY_CARVEOUT_RULE.COUNT
  is '获取的个数';
comment on column T_CATEGORY_CARVEOUT_RULE.SORTID
  is '排序次序，是指一个规则对应条件的之间的执行次序。';
comment on column T_CATEGORY_CARVEOUT_RULE.LASTEXCUTETIME
  is '上次执行时间，需要精确到秒';
comment on column T_CATEGORY_CARVEOUT_RULE.EFFECTIVETIME
  is '货架自动更新时间生效时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CATEGORY_CARVEOUT_RULE
  add primary key (ID);

-- Add/modify columns 
alter table T_CATEGORY_CARVEOUT_RULE add RULETYPE NUMBER(1) not null;
-- Add comments to the columns 
comment on column T_CATEGORY_CARVEOUT_RULE.RULETYPE
  is '规则类型 0：刷新货架下商品；1：货架下商品重排顺序';

-- Add/modify columns 
alter table T_CATEGORY_CARVEOUT_RULE modify COUNT default -1;
-- Add comments to the columns 
comment on column T_CATEGORY_CARVEOUT_RULE.COUNT
  is '获取的个数。不可以为空';

-- Create table MM创业大赛
------根据具体情况同实例采用以下方式
-----以PPMS用户身份赋予货架权限
grant select on OM_DEVELOPER_CONTEST to &ssms;

-- Create the synonym 
create or replace synonym OM_DEVELOPER_CONTEST for &MM_PPMS.OM_DEVELOPER_CONTEST;
------------------
-----不同实例，需要通过DBLINK方式创建同义词-----------
create or replace synonym OM_DEVELOPER_CONTEST        for OM_DEVELOPER_CONTEST@PPMSTOSSMS;
------------------


create table T_SYNCTIME_TMP_CY
(
  ID          NUMBER(10) not null,
  CONTENTID   VARCHAR2(12) not null,
  NAME        VARCHAR2(300) not null,
  STATUS      VARCHAR2(4),
  CONTENTTYPE VARCHAR2(32),
  LUPDDATE    DATE
);

alter table T_SYNCTIME_TMP_CY
  add constraint KEY_SYNCTIME_TMP_CY primary key (ID)
  using index ;
  
  
  -- Create table
create table T_SYNC_TACTIC_CY
(
  ID             NUMBER(10) not null,
  CATEGORYID     VARCHAR2(20) not null,
  CONTENTTYPE    VARCHAR2(30) not null,
  UMFLAG         VARCHAR2(1) not null,
  CONTENTTAG     VARCHAR2(200),
  TAGRELATION    NUMBER(2),
  CRATETIME      VARCHAR2(20) not null,
  LASTUPDATETIME VARCHAR2(20) not null,
  APPCATENAME    VARCHAR2(400)
);
  alter table T_SYNC_TACTIC_CY
  add constraint PK_T_SYNC_TACTIC_CY primary key (ID)
  using index ;
  
  -- Create table
create table T_LASTSYNCTIME_CY
(
  LASTTIME DATE not null
);

-- Create table
create table T_CYTOMM_MAPPING
(
  APPCATEID     VARCHAR2(30),
  APPCATENAME   VARCHAR2(200),
  CATENAME      VARCHAR2(200),
  CATEID        VARCHAR2(30),
  CYAPPCATENAME VARCHAR2(200),
  CYCATEID      VARCHAR2(200)
);
-- Add comments to the columns 
comment on column T_CYTOMM_MAPPING.APPCATEID
  is 'MM二级分类ID';
comment on column T_CYTOMM_MAPPING.APPCATENAME
  is 'MM二级分类';
comment on column T_CYTOMM_MAPPING.CATENAME
  is 'MM一级分类';
comment on column T_CYTOMM_MAPPING.CATEID
  is 'MM一级分类ID';
comment on column T_CYTOMM_MAPPING.CYAPPCATENAME
  is '百万创业大赛二级分类';
comment on column T_CYTOMM_MAPPING.CYCATEID
  is '百万创业大赛一级分类ID，2001，应用类；2002，文艺类';


insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('1', '系统', '软件', '1001', '软件', '2001');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('6', '角色', '游戏', '1003', '游戏', '2001');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('2', '生活', '软件', '1001', '电子书', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('15', '搞笑', '主题', '1002', '主题', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('3', '视频', '软件', '1001', '视频', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('4', '通讯', '软件', '1001', '动漫', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('39', '音乐', '软件', '1001', '音乐', '2002');

insert into t_cytomm_mapping (APPCATEID, APPCATENAME, CATENAME, CATEID, CYAPPCATENAME, CYCATEID)
values ('29', '资讯', '软件', '1001', '资讯', '2002');

------修改v_service视图


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
   and c.thirdapptype in ('1', '2','6')
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
  from t_game_service t;





----创建创业大赛专用内容同步视图
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
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as lupddate,
       f.chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid,
       c.contestcode

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
   and c.thirdapptype = '6' --增加MM创业大赛应用;
   and p.developerid = c.developerid;


create table v_cm_content_CY  as select * from ppms_v_cm_content_CY;

create index INDEX_v_cm_content_CY_cID on v_cm_content_CY (contentid);
 
 grant select on v_cm_content_CY to &portalMO;   ----授权给MO
 grant select on v_cm_content_CY to &portalWWW;  ----授权给www
 grant select on v_cm_content_CY to &portalWAP;  ----授权给wap
 
  


------初始化货架
insert into t_r_base(id,parentid,path,type) values('1021','701','{100}.{701}.{1021}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1021','百万创业大赛根货架','百万创业大赛根货架',0,1,1,0,'W,O,A',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


insert into t_r_base(id,parentid,path,type) values('1022','1021','{100}.{701}.{1021}.{1022}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1022','www应用','www应用',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));



insert into t_r_base(id,parentid,path,type) values('1023','1021','{100}.{701}.{1021}.{1023}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1023','www文艺','www文艺',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


insert into t_r_base(id,parentid,path,type) values('1024','1021','{100}.{701}.{1021}.{1024}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1024','MO应用','MO应用',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));



insert into t_r_base(id,parentid,path,type) values('1025','1021','{100}.{701}.{1021}.{1025}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1025','MO文艺','MO文艺',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1026','1023','{100}.{701}.{1021}.{1023}.{1026}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1026','电子书','电子书',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

 insert into t_r_base(id,parentid,path,type) values('1027','1025','{100}.{701}.{1021}.{1025}.{1027}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1027','电子书','电子书',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1028','1023','{100}.{701}.{1021}.{1023}.{1028}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1028','主题','主题',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

 insert into t_r_base(id,parentid,path,type) values('1029','1025','{100}.{701}.{1021}.{1025}.{1029}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1029','主题','主题',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

 insert into t_r_base(id,parentid,path,type) values('1030','1023','{100}.{701}.{1021}.{1023}.{1030}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1030','视频','视频',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

 insert into t_r_base(id,parentid,path,type) values('1031','1025','{100}.{701}.{1021}.{1025}.{1031}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1031','视频','视频',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


 insert into t_r_base(id,parentid,path,type) values('1032','1023','{100}.{701}.{1021}.{1023}.{1032}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1032','动漫','动漫',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

 insert into t_r_base(id,parentid,path,type) values('1033','1025','{100}.{701}.{1021}.{1025}.{1033}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1033','动漫','动漫',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

 insert into t_r_base(id,parentid,path,type) values('1034','1023','{100}.{701}.{1021}.{1023}.{1034}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1034','音乐','音乐',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

 insert into t_r_base(id,parentid,path,type) values('1035','1025','{100}.{701}.{1021}.{1025}.{1035}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1035','音乐','音乐',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

 insert into t_r_base(id,parentid,path,type) values('1036','1023','{100}.{701}.{1021}.{1023}.{1036}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1036','资讯','资讯',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

 insert into t_r_base(id,parentid,path,type) values('1037','1025','{100}.{701}.{1021}.{1025}.{1037}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1037','资讯','资讯',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

----------------------

insert into t_r_base(id,parentid,path,type) values('1038','1022','{100}.{701}.{1021}.{1022}.{1038}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1038','软件','软件',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1039','1022','{100}.{701}.{1021}.{1022}.{1039}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1039','游戏','游戏',0,0,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1040','1024','{100}.{701}.{1021}.{1024}.{1040}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1040','软件','软件',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1041','1024','{100}.{701}.{1021}.{1024}.{1041}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1041','游戏','游戏',0,0,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));



----------初始化内容同步上架策略

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (2, '1040', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:07:49', '2009-04-06 17:07:49', '软件');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (3, '1041', 'nt:gcontent:appGame', '0', '', 0, '2009-04-06 17:08:00', '2009-04-06 17:08:00', '游戏');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (4, '1027', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:10', '2009-04-06 17:08:10', '电子书');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (5, '1029', 'nt:gcontent:appTheme', '0', '', 0, '2009-04-06 17:08:17', '2009-04-06 17:08:17', '主题');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (6, '1031', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:32', '2009-04-06 17:08:32', '视频');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (7, '1033', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:44', '2009-04-06 17:08:44', '动漫');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (8, '1035', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:54', '2009-04-06 17:08:54', '音乐');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (9, '1037', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:09:04', '2009-04-06 17:09:04', '资讯');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (10, '1038', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:07:49', '2009-04-06 17:07:49', '软件');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (11, '1039', 'nt:gcontent:appGame', '0', '', 0, '2009-04-06 17:08:00', '2009-04-06 17:08:00', '游戏');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (12, '1026', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:10', '2009-04-06 17:08:10', '电子书');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (13, '1028', 'nt:gcontent:appTheme', '0', '', 0, '2009-04-06 17:08:17', '2009-04-06 17:08:17', '主题');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (14, '1030', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:32', '2009-04-06 17:08:32', '视频');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (15, '1032', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:44', '2009-04-06 17:08:44', '动漫');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (16, '1034', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:08:54', '2009-04-06 17:08:54', '音乐');

insert into t_sync_tactic_CY (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, CRATETIME, LASTUPDATETIME, APPCATENAME)
values (17, '1036', 'nt:gcontent:appSoftWare', '0', '', 0, '2009-04-06 17:09:04', '2009-04-06 17:09:04', '资讯');






insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.045_SSMS','MM1.0.2.050_SSMS');
commit;