--回滚 创建同名的业务信息物化视图  
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

--回滚第三方业务视图 V_THIRD_SERVICE 
drop materialized view V_THIRD_SERVICE;


--回滚CMS的内容视图
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

alter table T_R_GCONTENT drop column SERVATTR;

drop table T_GAME_SERVICE;
drop table T_GAME_CATE_MAPPING;
drop table T_GAME_UA_MAPPING;


---######################################
---####   货架自动更新回滚脚本    ##################
---######################################
--修复现网数据
update t_caterule_cond t set t.condtype=0 where  t.condtype=10;
delete from t_category_rule t where t.cid in ('1870595','1826727','1825622','1826847','1826846','1826845','2017');
delete from t_caterule t where t.ruleid in ('100','101','102','103','104','105','106');
delete from t_caterule_cond t where t.ruleid in ('100','101','102','103','104','105','106');
commit;
  alter table T_CATERULE_COND
  add constraint CKC_CONDTYPE_T_CATERU
  check (CONDTYPE in (0,1));
comment on column T_CATERULE_COND.CONDTYPE is
'条件类型 0：从产品库获取；1：从精品库获取';

comment on column T_CATERULE_COND.CID
  is '获取货架的货架内码，如果CondType=1则本字段无效';

comment on column T_CATERULE.RULETYPE
  is '规则类型 0：刷新货架下商品；1：货架下商品重排顺序';



--回滚版本----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.070_SSMS' and LASTDBVERSION = 'MM1.0.0.060_SSMS';

commit;
