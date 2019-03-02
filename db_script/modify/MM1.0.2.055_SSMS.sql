create table T_MB_MUSIC_DESC
(
  MUSICID     VARCHAR2(30) not null,
  SONGNAME    VARCHAR2(200) not null,
  SINGER      VARCHAR2(100) not null,
  SPECIALNAME VARCHAR2(100) not null,
  SPECIALDESC VARCHAR2(400) not null,
  IMAGENAME   VARCHAR2(200) not null,
  CONTENTID   VARCHAR2(30) not null,
  CONTENTNAME VARCHAR2(300) not null,
  CREATEDATE  DATE default sysdate not null,
  EDITDATE    DATE default sysdate not null
);
-- Add comments to the columns 
comment on column T_MB_MUSIC_DESC.MUSICID
  is '音乐id';
comment on column T_MB_MUSIC_DESC.SONGNAME
  is '歌曲名称';
comment on column T_MB_MUSIC_DESC.SINGER
  is '歌手名称';
comment on column T_MB_MUSIC_DESC.SPECIALNAME
  is '专辑名称';
comment on column T_MB_MUSIC_DESC.SPECIALDESC
  is '专辑介绍';
comment on column T_MB_MUSIC_DESC.IMAGENAME
  is '图片文件名';
comment on column T_MB_MUSIC_DESC.CONTENTID
  is '应用ContentID';
comment on column T_MB_MUSIC_DESC.CONTENTNAME
  is '应用名称';
comment on column T_MB_MUSIC_DESC.CREATEDATE
  is '创建时间';
comment on column T_MB_MUSIC_DESC.EDITDATE
  is '修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MB_MUSIC_DESC
  add primary key (MUSICID);

grant select on T_MB_MUSIC_DESC to &portalmo;--终端门户

-- Add/modify columns 体验营销数据导出榜单初始化
alter table T_CATEGORY_TRAIN add COUNT number(8) default 100 not null;

-- Add comments to the columns 
comment on column T_CATEGORY_TRAIN.CID
  is '货架categoryid';
comment on column T_CATEGORY_TRAIN.CNAME
  is '货架榜单名称';
comment on column T_CATEGORY_TRAIN.COUNT
  is '允许最大数量';
  
  

------初始化货架
insert into T_R_BASE (ID, PARENTID, PATH, TYPE)
values ('14301299', '701', '{100}.{701}.{14301299}', 'nt:category');
insert into T_R_CATEGORY (ID, NAME, DESCS, SORTID, CTYPE, CATEGORYID, DELFLAG, CHANGEDATE, STATE, PARENTCATEGORYID, RELATION, PICURL, STATISTIC)
values ('14301299', 'www门户分类精品库', 'www门户分类精品库', 0, 0, '400000991', 0, '2010-08-10 13:32:27', 1, null, 'W', null, 0);


insert into T_R_BASE (ID, PARENTID, PATH, TYPE)
values ('14301300', '701', '{100}.{701}.{14301300}', 'nt:category');
insert into T_R_CATEGORY (ID, NAME, DESCS, SORTID, CTYPE, CATEGORYID, DELFLAG, CHANGEDATE, STATE, PARENTCATEGORYID, RELATION, PICURL, STATISTIC)
values ('14301300', 'wap门户分类精品库', 'wap门户分类精品库', 0, 0, '400000992', 0, '2010-08-10 13:33:59', 1, null, 'A', null, null);

insert into T_R_BASE (ID, PARENTID, PATH, TYPE)
values ('14301301', '701', '{100}.{701}.{14301301}', 'nt:category');
insert into T_R_CATEGORY (ID, NAME, DESCS, SORTID, CTYPE, CATEGORYID, DELFLAG, CHANGEDATE, STATE, PARENTCATEGORYID, RELATION, PICURL, STATISTIC)
values ('14301301', 'mo门户分类精品库', 'mo门户分类精品库', 0, 0, '400000993', 0, '2010-08-10 13:34:14', 1, null, 'O', null, null);


--增加JIL应用
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
   and c.thirdapptype in ('1','2','5');--增加widget应用--增加JIL应用

--增加JIL应用
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
   and c.thirdapptype in ('1', '2','6','5')
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

--------创业大赛资讯修改为新媒体------
update  t_cytomm_mapping t  set t.CYAPPCATENAME='新媒体'  where t.APPCATEID='29';
update  t_cytomm_mapping t  set t.CYAPPCATENAME='移动视频'  where t.APPCATEID='3';
update  t_sync_tactic_cy t  set t.appcatename='移动视频'  where t.id in ('6','14','22');
update  t_sync_tactic_cy t  set t.appcatename='新媒体'  where t.id in ('9','17','25');



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.050_SSMS','MM1.0.2.055_SSMS');
commit;