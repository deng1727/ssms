-- rollback view 

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
   and (c.status = '0006' or c.status = '1006' )
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid;

-----回滚t_sync_tactic_cy表

truncate table  t_sync_tactic_cy;
insert into t_sync_tactic_cy select * from  t_sync_tactic_cy_045;

-----回滚t_cytomm_mapping表

truncate table  t_cytomm_mapping;
insert into t_cytomm_mapping select * from  t_cytomm_mapping_045;

drop table T_TOPLIST;
drop table T_CY_PRODUCTLIST;

comment on column T_R_GCONTENT.ISSUPPORTDOTCARD
  is '是否支持点卡支付，0：不支持 1：支持';

comment on column T_R_GCONTENT.NAMELETTER
  is '铃音名称检索首字母';  
  
  drop table T_CATEGORY_CARVEOUT_SQLBASE;
  truncate table t_category_carveout_rule;
  alter table T_CATEGORY_CARVEOUT_RULE modify NAME VARCHAR2(30);
  
--------------
----回滚139新音乐数据导入
-----------
drop table T_MB_MUSIC_new;
drop table T_MB_REFERENCE_new;
drop table T_MB_CATEGORY_new;
drop table T_MB_KEYWORD_new;

drop sequence SEQ_MB_CATEGORY_NEW_ID;
drop sequence SEQ_MB_KEYWORD_NEW;



delete DBVERSION where PATCHVERSION = 'MM1.0.3.045_SSMS' and LASTDBVERSION = 'MM1.0.3.040_SSMS';
commit;