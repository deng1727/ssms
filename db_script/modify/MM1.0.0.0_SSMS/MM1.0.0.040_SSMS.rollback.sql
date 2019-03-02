drop index IDX_COND_RULEID;
drop table T_CATERULE_COND cascade constraints;

drop table T_CATERULE cascade constraints;

drop table T_CATEGORY_RULE cascade constraints;
--恢复037版本T_CATEGORY_RULE表及数据
create table T_CATEGORY_RULE as select * from t_category_rule_ssms037_bak;


--还原视图
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

 --在wwwpas，mopas，pcpas用户下执行，删除。
 drop synonym v_content_device;


 --在货架下删除视图

drop view v_content_device;

drop synonym T_DEVICE_BRAND;

--如果本次升级失败。需要对终端门户mopas的t_r_gcontent物化视图重新创建。
--请分别以上面三个用户执行以下脚本
---------------start--------------------------------------------------
drop materialized view t_r_gcontent;

---mopas 下执行创建物化视图脚本
create materialized view t_r_gcontent as select * from s_r_gcontent;
--更改其他对其他物化视图的影响

alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;

--t_r_gcontent
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);

----------------end-----------------------------------------------------

-- 删除“数据导出”菜单权限
delete from t_right r where r.rightid='0_0801_RES_DATA_EXPORT';
 --删除版本信息
delete DBVERSION where PATCHVERSION = 'MM1.0.0.040_SSMS' and LASTDBVERSION = 'MM1.0.0.037_SSMS';
commit;