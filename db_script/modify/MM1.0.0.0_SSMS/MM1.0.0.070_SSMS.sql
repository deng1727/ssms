-- 本更新依赖于PPMS070版本更新
-- Create table
create table T_GAME_SERVICE
(
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

drop materialized view V_SERVICE;
create materialized view V_SERVICE
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
  from om_company         v1,
       v_om_product       v2,
       OM_PRODUCT_CONTENT p,
       cm_content         c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id
      UNION ALL
 select t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--上线计费
       null,
       null,
       t.chargetype,
       t.mobileprice,
       null,
       t.chargedesc,
       'B',
       'G',--全网业务
       t.servdesc,
       null,
       t.lupddate
  from t_game_service t   ;

create unique index index_v_service_pk on V_SERVICE (icpcode, icpservid, providertype);

--创建同名的第三方业务信息物化视图  
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
  from om_company v1, v_om_product v2
 where v1.companyid = v2.CompanyID
   and v1.icptype = 1
   and v2.ProviderType = 'V';

--在货架管理系统(SSMS)数据库用户&SSMS下授权给终端门户(MOPPS)
grant select on V_THIRD_SERVICE to &portalmo;--终端门户

--- 该视图的创建依赖于PPMS系统的OM_PRODUCT_CONTENT表的substatus字段添加。
--创建CMS的内容视图
create or replace view v_cm_content as
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
              decode(f.substatus, '1', '0006', '0', '0008'),
              '1015',
              decode(f.substatus, '1', '0006', '0', '0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L', c.status, 'G') as ServAttr,
       c.createdate,
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,--全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate) as lupddate,
       f.chargeTime
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       om_company         e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and (d. ProductStatus = '2' or d. ProductStatus = '3') ----产品上线计费or不计费
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate;


-- 基地游戏分类映射表
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


insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('MMORPG(网游)', '其他');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('竞速', '体育竞技');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('动作冒险', '动作格斗');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('文字冒险', '冒险模拟');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('音乐', '休闲趣味');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('动作', '动作格斗');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('棋牌', '棋牌益智');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('策略', '策略回合');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('体育', '体育竞技');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('角色扮演', '角色扮演');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('射击', '射击飞行');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('益智', '棋牌益智');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('动作益智', '动作格斗');

-- 基地游戏UA映射表
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

alter table T_R_GCONTENT add SERVATTR varchar2(1) default 'G';
--mopas 需要重新创建t_r_gcontent 物化视图
--做一次全量同步
delete from t_lastsynctime;


---######################################
---####   货架自动更新脚本    ##################
---######################################

-- 去掉对条件类型的数据检验
alter table T_CATERULE_COND
  drop constraint CKC_CONDTYPE_T_CATERU;

comment on column T_CATERULE_COND.CONDTYPE
  is '条件类型 10：从产品库获取自有业务（软件，游戏，主题）；12：从产品库获取非自有业务；1：从精品库获取。';
  alter table T_CATERULE_COND modify CONDTYPE NUMBER(2);

  comment on column T_CATERULE_COND.CID
  is '获取货架的货架内码，如果CondType=1则本字段无效';
  --修复现网数据。条件类型0变成10

  comment on column T_CATERULE.RULETYPE
  is '规则类型 0：刷新货架下商品；1：货架下商品重排顺序
；5：处理基地图书运营推荐图书。';

  update t_caterule_cond t set t.condtype=10 where  t.condtype=0;

 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1870595', 100, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1826727', 101, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1825622', 102, NULL, SYSDATE);

 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1826847', 103, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1826846', 104, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1826845', 105, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('2017', 106, NULL, SYSDATE);

//2017  基地图书分类货架，表示图书分类推荐（非货架）
---------------

   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(100, 'MM金曲热搜榜', 0, 0, 1,NULL);
   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(101, 'MM音乐点击榜', 0, 0, 1,NULL);
   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(102, '热门推荐', 0, 0, 1,NULL);

    Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(103, '经典畅销排行榜', 0, 0, 1,NULL);
   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(104, '网络原创排行榜', 0, 0, 1,NULL);
   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                       Values(105, '无线图书排行', 0, 0, 1,NULL);
    Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                       Values(106, '图书分类推荐（非货架）', 5, 0, 1,NULL);
------------------------------------------------------------------------------------------------------------


Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(100, '3242950', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(100, null, 12, 'substr(g.id,1,1)=''m''', 'dbms_random.value',20, NULL);
 
 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                 Values(101, '3242951', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                               Values(101, null, 12, 'substr(g.id,1,1)=''m''', 'dbms_random.value',20, NULL);
 
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(102, '3242952', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                               Values(102, null, 12, 'substr(g.id,1,1)=''m''', 'dbms_random.value',10, NULL);

------------------
 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(103, '3242947', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(103, null, 12, 'substr(g.id,1,1)=''r''', 'dbms_random.value',20, NULL);

 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                 Values(104, '3242948', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                               Values(104, null, 12, 'substr(g.id,1,1)=''r''', 'dbms_random.value',20, NULL);
 
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(105, '3242949', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                               Values(105, null, 12, 'substr(g.id,1,1)=''r''', 'dbms_random.value',10, NULL);

create table v_cm_device_resource as
select *
  from ppms_CM_DEVICE_RESOURCE p
 where exists
 (select 1 from t_r_gcontent g where g.contentid = p.contentid);

--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.060_SSMS','MM1.0.0.070_SSMS');
commit;

