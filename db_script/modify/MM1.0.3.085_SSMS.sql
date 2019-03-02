-----在电子流和货架同一数据库实例下执行如下操作：
---在电子流账户下，授权cm_content_college的select权限
grant select on cm_content_college to &ssms;

-- Create the synonym 
create or replace synonym cm_content_college
  for MM_PPMS.cm_content_college;

-- Create the synonym 
create or replace synonym PPMS_V_MTK_CONTENT
  for MM_PPMS.V_CM_CONTENT_MTK;


----不同实例下：
-- Create the synonym 
create or replace synonym cm_content_college
  for cm_content_college@DL_PPMS_DEVICE;

-- Create the synonym 
create or replace synonym PPMS_V_MTK_CONTENT
  for V_CM_CONTENT_MTK@DL_PPMS_DEVICE;




create or replace view ppms_v_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       decode(c.thirdapptype,'10',c.oviappid,'12',c.oviappid,c.contentcode ) ContentCode,
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
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,
  --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       decode( c.thirdapptype,'12', m.developername, decode(c.companyid,'116216','2010MM创业计划优秀应用展示',e.companyname))  as companyname ,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as plupddate,
       c.conlupddate as lupddate,    -----增加应用更新时间
decode(c.thirdapptype,'7','2',f.chargeTime)  chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.citysid as cityid,
       decode(f.chargetime || decode(c.chargetype,'01','02',c.chargetype) || c.contattr ||
              e.operationsmode || c.thirdapptype,
              '102G01',
              '1',
              '102G02',
              '1',
              '102G05',
              '1',
              '102G012',
             '1',
              '0') as othernet
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f,
       s_cm_content_motoext      m
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
      and c.oviappid=m.appid(+) ---- MOTO devpname
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
   and (c.thirdapptype in ('1','2','7','11','12')
       or (c.thirdapptype = '5'
          and c.Jilstatus = '1'));


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
       p.contestgroup as isSupportDotcard,
      greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as plupddate,
        c.conlupddate as lupddate,
       f.chargeTime,
       c.thirdapptype,
       p.province as pvcid,
       p.cityid,
       c.contestcode,
       c.contestyear,
       q.COLLEGE as college,
       q.COLLEGEID as collegeId,
       decode(f.chargetime || decode(c.chargetype,'01','02',c.chargetype) || c.contattr ||
              e.operationsmode || c.thirdapptype,
              '102G01',
              '1',
              '102G02',
              '1',
              '102G05',
              '1',
              '102G012',
             '1',
              '0') as othernet

  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f,
       OM_DEVELOPER_CONTEST p,
       cm_content_college q
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and p.college = q.COLLEGEID(+)
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' )
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   --and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.Status  in ('2','3') ----产品上线计费or不计费or  去掉了下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid;


-- Create table
create table v_mtk_content
(
  APCODE    VARCHAR2(6),
  CONTENTID VARCHAR2(12),
  TBID      VARCHAR2(20) not null,
  LOGINID   VARCHAR2(30) not null,
  PLATFORM  VARCHAR2(8)
);
-- Add comments to the columns 
comment on column v_mtk_content.APCODE
  is 'AP代码';
comment on column v_mtk_content.CONTENTID
  is '应用ID';
comment on column v_mtk_content.TBID
  is 'TBID';
comment on column v_mtk_content.LOGINID
  is 'LOGINID';
comment on column v_mtk_content.PLATFORM
  is '平台ID';


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.080_SSMS','MM1.0.3.085_SSMS');
commit;


