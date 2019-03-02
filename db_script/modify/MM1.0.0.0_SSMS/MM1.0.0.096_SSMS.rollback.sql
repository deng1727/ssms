---回滚数据
drop sequence SEQ_ZCOM_ID;
drop  table V_CM_CONTENT_ZCOM;

drop  table T_SYNCTIME_TMP_ZCOM;
drop  table T_LASTSYNCTIME_ZCOM;
drop  table Z_PPS_MAGA;
drop  table Z_PPS_MAGA_LS;

drop table v_cm_content;
RENAME  ppms_v_cm_content  TO v_cm_content;
---回滚v_service
drop table V_SERVICE;
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
   and p.id = v2.id  ;

drop table t_content_backlist;
----回滚 全部 榜单商品排序规则按照精品评分降序排列
update t_caterule set RANDOMFACTOR='100' where ruleid='1';
update t_caterule_cond set osql='dayOrderTimes desc,createdate desc,mobilePrice asc,name asc'  where ruleid='1';




delete DBVERSION where PATCHVERSION = 'MM1.0.0.096_SSMS' and LASTDBVERSION = 'MM1.0.0.095_SSMS';
commit;