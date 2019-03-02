
-- Add/modify columns 
alter table T_R_REFERENCE add variation number(5,0);
comment on column T_R_REFERENCE.variation
  is '排序id的变更。+表示上升，-表示下降。99999 表示新增';


--修改ctype的值。 11 表示 a8货架类型。
alter table T_R_CATEGORY modify CTYPE NUMBER(2);

--货架表新增两个字段
alter table T_R_CATEGORY add picurl varchar2(256);
alter table T_R_CATEGORY add statistic number(11);
comment on column T_R_CATEGORY.picurl is '货架预览图';
comment on column T_R_CATEGORY.statistic is '货架统计信息';

--需要修改终端音乐推荐以及其子货架的ctype类型
update t_r_category c set c.ctype=11 where c.name='终端音乐推荐' and c.parentcategoryid is null;
update t_r_category c set c.ctype=11 where c.parentcategoryid= (select c.categoryid from t_r_category c where c.name='终端音乐推荐' and c.parentcategoryid is null);

--新增提示语
insert into t_resource values('RESOURCE_CATE_BO_UPLOAD_1010','图片上传失败','上传图片预览图出错');

---先体验后付费
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
   and (c.status = '0006' or c.status = '1006' or  f.status=5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null);
--null 表示非先体验后付费业务，0表示先体验后付费下载时计费

 alter table T_GAME_SERVICE add contentid varchar2(30);
comment on column T_GAME_SERVICE.contentid is '内容id';

drop materialized view v_service;
create materialized view V_SERVICE
refresh force on demand
as
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
       decode(p.chargetime||v2.paytype,'20',p.feedesc,v2.chargedesc) as chargeDesc,
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
 select
       t.contentid,
       t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--上线计费
       null,
       t.servflag,
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
  from t_game_service t   ;
create unique index index_v_service_pk on V_SERVICE (icpcode, icpservid, providertype);
create index index_goods_his on t_goods_his(contentid);
create index index_v_service on v_service(contentid);
--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.077_SSMS','MM1.0.0.080_SSMS');
commit;