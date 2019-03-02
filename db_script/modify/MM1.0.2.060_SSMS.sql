--创建基地游戏视图
create or replace view v_base_game as
select t.pkgid, t.pkgname, t.pkgdesc, '0' as gameType--0为G+游戏包、1精品单机、2单机转激活
  from t_game_base t where t.state <> 3;

--创建基地数据临时表
create table T_BASE_TEMP
(
  BASEID   VARCHAR2(30) not null,
  BASETYPE VARCHAR2(30) not null
);

comment on column T_BASE_TEMP.BASEID
  is '编号，当类型为游戏时，为游戏编号，当类型为音乐时为musicid，当类型为图书时为图书id，当类型为视频时为内容id';
comment on column T_BASE_TEMP.BASETYPE
  is '基地类型';

create table T_BASE_VIDEO
(
  ID        NUMBER not null,
  VIDEONAME VARCHAR2(40),
  VIDEOURL  VARCHAR2(200)
);

-- Add comments to the columns 
comment on column T_BASE_VIDEO.ID
  is '编号';
comment on column T_BASE_VIDEO.VIDEONAME
  is '专题名称';
comment on column T_BASE_VIDEO.VIDEOURL
  is '专题url链接';


alter table T_BASE_VIDEO
  add primary key (ID);

-- Create sequence 
create sequence SEQ_BASEVIDEO_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;


insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('2_1501_GAME', '基地游戏管理', '基地游戏管理', null, 2);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('2_1502_MUSIC', '基地音乐管理', '基地音乐管理', null, 2);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('2_1503_BOOK', '基地图书管理', '基地图书管理', null, 2);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('2_1504_VIDEO', '基地视频管理', '基地视频管理', null, 2);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('0_1501_DATA', '游戏推荐', '游戏推荐', '2_1501_GAME', 0);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('0_1501_TEMP', '推荐导出文件', '推荐导出文件', '2_1501_GAME', 0);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('0_1502_TEMP', '推荐导出文件', '推荐导出文件', '2_1502_MUSIC', 0);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('0_1502_DATA', '音乐推荐', '音乐推荐', '2_1502_MUSIC', 0);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('0_1503_TEMP', '推荐导出文件', '推荐导出文件', '2_1503_BOOK', 0);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('0_1503_DATA', '图书推荐', '图书推荐', '2_1503_BOOK', 0);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('0_1504_DATA', '视频推荐', '视频推荐', '2_1504_VIDEO', 0);
insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('0_1504_TEMP', '推荐导出文件', '推荐导出文件', '2_1504_VIDEO', 0);
commit;

insert into T_ROLE (ROLEID, NAME, DESCS, PROVINCES)
values (111, '基地游戏', '基地游戏', null);
insert into T_ROLE (ROLEID, NAME, DESCS, PROVINCES)
values (222, '基地音乐', '基地音乐', null);
insert into T_ROLE (ROLEID, NAME, DESCS, PROVINCES)
values (333, '基地图书', '基地图书', null);
insert into T_ROLE (ROLEID, NAME, DESCS, PROVINCES)
values (444, '基地视频', '基地视频', null);
commit;

insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '2_1501_GAME');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '0_1501_DATA');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '0_1501_TEMP');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '2_1502_MUSIC');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '0_1502_TEMP');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '0_1502_DATA');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '2_1503_BOOK');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '0_1503_TEMP');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '0_1503_DATA');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '2_1504_VIDEO');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '0_1504_DATA');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (1, '0_1504_TEMP');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (111, '2_1501_GAME');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (111, '0_1501_DATA');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (111, '0_1501_TEMP');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (222, '2_1502_MUSIC');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (222, '0_1502_TEMP');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (222, '0_1502_DATA');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (333, '2_1503_BOOK');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (333, '0_1503_TEMP');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (333, '0_1503_DATA');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (444, '2_1504_VIDEO');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (444, '0_1504_DATA');
insert into T_ROLERIGHT (ROLEID, RIGHTID)
values (444, '0_1504_TEMP');


alter table T_BASE_VIDEO modify VIDEONAME VARCHAR2(100);

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
decode(c.thirdapptype,'7','2',f.chargeTime)  chargeTime,
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
   and c.thirdapptype in ('1','2','5','7');--增加widget应用--增加JIL应用--增加孵化应用7

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
   and c.thirdapptype in ('1', '2','6','5','7')--增加孵化应用7
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

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.055_SSMS','MM1.0.2.060_SSMS');
commit;