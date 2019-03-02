--备份相关数据表
create table t_r_gcontent_019 as select * from t_r_gcontent;
create table t_r_reference_019 as select * from t_r_reference;
create table t_goods_his_019 as select * from t_goods_his;

--更新v_cm_content视图。companycode 更改为apcode赋值为icpcode
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
              c.status) as status,
       c.createdate,
       f.onlinedate as marketdate,
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
   and c.companyid = e.companyid
   and (c.status = '0006' or c.status = '1006' or c.status = '0008')
   and d.AuditStatus = '0003'
   and f.ID = d.ID
   and c.contentid = f.contentid;

--更新V_SERVICE视图

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
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from om_company         v1,
       v_om_product       v2,
       OM_PRODUCT_CONTENT p,
       cm_content         c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id;
--增加索引
create index inx_v_service_code_servid on v_service (icpcode,icpservid);

--更新内容表
update t_r_gcontent t set t.icpcode=(select v.icpcode from v_cm_content v where t.contentid=v.contentid) where ascii(substr(t.id,1,1))>47 and ascii(substr(t.id,1,1))<58;
update t_r_gcontent t set t.icpservid=(select v.icpservid from v_cm_content v where t.contentid=v.contentid) where ascii(substr(t.id,1,1))>47 and ascii(substr(t.id,1,1))<58;
update t_r_gcontent t set t.contenttag=(select v.ContentCode from v_cm_content v where t.contentid=v.contentid) where ascii(substr(t.id,1,1))>47 and ascii(substr(t.id,1,1))<58;
update t_r_gcontent t set t.companyid=(select v.companyid from v_cm_content v where t.contentid=v.contentid) where ascii(substr(t.id,1,1))>47 and ascii(substr(t.id,1,1))<58;
update t_r_gcontent t set t.productid=(select v.productid from v_cm_content v where t.contentid=v.contentid) where ascii(substr(t.id,1,1))>47 and ascii(substr(t.id,1,1))<58;

--更新商品表
update t_r_reference t set t.goodsid=substr(t.goodsid,1,9)||(select g.companyid||g.productid||g.contentid from t_r_gcontent g where t.refnodeid=g.id) where ascii(substr(t.refnodeid,1,1))>47 and ascii(substr(t.refnodeid,1,1))<58;

--更新商品历史表
update t_goods_his t set t.icpcode=(select v.icpcode from v_cm_content v where t.contentid=v.contentid);
update t_goods_his t set t.icpservid=(select v.icpservid from v_cm_content v where t.contentid=v.contentid);
update t_goods_his t set t.goodsid=substr(t.goodsid,1,9)||(select g.companyid||g.productid||g.contentid from t_r_gcontent g where t.contentid=g.contentid);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.015_SSMS','MM1.0.0.019_SSMS');

commit;