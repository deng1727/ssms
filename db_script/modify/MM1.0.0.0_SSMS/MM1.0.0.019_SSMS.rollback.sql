--恢复备份的数据
delete from t_r_reference;
delete from t_r_gcontent;
delete from t_goods_his;

insert into t_r_reference select * from t_r_reference_019;
insert into t_r_gcontent select * from t_r_gcontent_019;
insert into t_goods_his select * from t_goods_his_019;

--还原v_cm_content
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
       e.companycode as icpcode,
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
   and f.ProductID = d.ProductID
   and c.contentid = f.contentid
   ;

--还原V_SERVICE视图

drop materialized view V_SERVICE;

create materialized view V_SERVICE
refresh force on demand
as
select v1.companycode as icpcode,
       v1.CompanyName as spname,
       v1.ShortName as spshortname,
       v2.ServiceCode as icpservid,
       v2.ProductName as servname,
       decode(v2.ProductStatus, '2', 'A', '3', 'B', '4', 'P', '5', 'E') as SERVSTATUS,
       decode(v2.ACCESSMODEID,'00','S','01','W','02','M','10','A','05','E') as umflag,
       decode(v2.ServiceType, 1, 8, 2, 9) as servtype,
       v2.ChargeType as ChargeType,
       v2.Fee as mobileprice,
       V2.PayMode_card as dotcardprice,
       v2.ChargeDesc,
       v2.Description as servdesc,
       v1.companycode||'_'||v2.ServiceCode as pksid,
       v2.LUPDDate
  from om_company v1,
       v_om_product v2,
       (select v1.companycode, v2.ServiceCode
          from OM_Company         v1,
               OM_PRODUCT_CONTENT p,
               cm_content         c,
               v_om_product       v2
         where p.contentid = c.contentid
           and c.companyid = v1.companyid
           and p.productid = v2.productid
         group by (v1.companycode, v2.ServiceCode)) t
 where v1.companycode = t.companycode
   and v2.ServiceCode = t.ServiceCode;

 --删除版本信息
delete DBVERSION where PATCHVERSION = 'MM1.0.0.019_SSMS' and LASTDBVERSION = 'MM1.0.0.015_SSMS';

commit;