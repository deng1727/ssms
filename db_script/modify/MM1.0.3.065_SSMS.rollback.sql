---回滚数据
-- Create/Recreate indexes 
drop index pk_GCONTENT_iscpserid_69 ;


-- Add comments to the columns 
comment on column T_TOPLIST.TYPE
  is '榜单类型:1应用人气;2创意孵化人气;3应用星探;4创意孵化星探;';

-- Drop columns 
alter table T_R_GCONTENT drop column OTHERNET;
alter table V_CM_CONTENT drop column othernet;
alter table V_CM_CONTENT_cy drop column othernet;
alter table T_R_GCONTENT drop column RICHAPPDESC ;
alter table T_R_GCONTENT drop column ADVERTPIC ;
  
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
       c.lupddate,    -----增加应用更新时间
decode(c.thirdapptype,'7','2',f.chargeTime)  chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid
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
       c.contestgroup as isSupportDotcard,
      greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as plupddate,
       c.lupddate,
       f.chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid,
       c.contestcode,
       c.contestyear,
       p.college
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
   --and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.Status  in ('2','3') ----产品上线计费or不计费or  去掉了下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid;
   
delete t_right r where r.rightid in ('0_1503_CATEGORY','0_1503_REFERENCE');

delete t_roleright r where r.rightid in ('0_1503_CATEGORY','0_1503_REFERENCE');

-- Drop columns 
alter table t_rb_category drop column PLATFORM;
alter table t_rb_category drop column CITYID;
alter table t_rb_category drop column type;

drop table t_rb_category;

alter table t_rb_category_temp rename to t_rb_category;


-- Add/modify columns 
alter table T_RB_CATEGORY modify CATEGORYID not null;

-- Add/modify columns 
alter table T_RB_REFERENCE modify CATEGORYID not null;

delete t_right r where r.rightid in ('1_0810_RES_SYS_DATA');
delete t_roleright r where r.rightid in ('1_0810_RES_SYS_DATA');

drop table T_ExigenceContent;

-- Add/modify columns 
alter table T_RB_CATEGORY modify PARENTID not null;

update t_rb_category c set c.parentid = '0' where c.parentid = '';

------------------------------------------------------
---------回滚解决跨实例clob字段查询问题   start-------------------
------------------------------------------------------

---修改原来的同义词
--------------------
drop table CM_CT_APPGAME ;
drop table CM_CT_APPSOFTWARE ;
drop table CM_CT_APPTHEME ;

drop synonym s_CM_CT_APPGAME ;
create or replace synonym CM_CT_APPGAME
  for CM_CT_APPGAME@DL_PPMS_DEVICE;

drop synonym s_CM_CT_APPSOFTWARE ;
create or replace synonym CM_CT_APPSOFTWARE
  for CM_CT_APPSOFTWARE@DL_PPMS_DEVICE;

drop synonym s_CM_CT_APPTHEME ;
create or replace synonym CM_CT_APPTHEME
  for CM_CT_APPTHEME@DL_PPMS_DEVICE;

-----回滚存储过程，在本地建立含有clob字段的临时表
---------------------------------
drop procedure p_cm__ct ;




delete DBVERSION where PATCHVERSION = 'MM1.0.3.065_SSMS' and LASTDBVERSION = 'MM1.0.3.060_SSMS';
commit;