--T_KEY_RESOURCE表联合主键
alter table T_KEY_RESOURCE add constraint T_KEY_RESOURCE_PK primary key (TID,KEYID);
--初始化扩展表数据
insert into T_KEY_BASE (KEYID, KEYNAME, KEYTABLE, KEYDESC)
values (SEQ_key_ID.nextval, 'begin_time', 't_r_gcontent', '活动开始时间');
insert into T_KEY_BASE (KEYID, KEYNAME, KEYTABLE, KEYDESC)
values (SEQ_key_ID.nextval, 'end_time', 't_r_gcontent', '活动结束时间');
insert into T_KEY_BASE (KEYID, KEYNAME, KEYTABLE, KEYDESC)
values (SEQ_key_ID.nextval, 'recommend_name', 't_r_gcontent', '推荐游戏名称');
                     
---新增数据导出菜单权限
insert into t_right r values('1_0808_RES_KEYBASELIST','扩展属性管理','扩展属性管理','2_0801_RESOURCE',0);
insert into t_roleright(ROLEID,RIGHTID) values(1,'1_0808_RES_KEYBASELIST');
insert into t_right r values('1_0809_RES_CONTENTLIST','应用扩展属性管理','应用扩展属性管理','2_0801_RESOURCE',0);
insert into t_roleright(ROLEID,RIGHTID) values(1,'1_0809_RES_CONTENTLIST');

alter table t_mb_category_new add PLATFORM VARCHAR2(200) default '{0000}';
alter table t_mb_category_new add CITYID VARCHAR2(4000) default '{0000}';
 
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW.TYPE
  is '是否在门户展示 1：是 0：否';
comment on column T_MB_CATEGORY_NEW.PLATFORM
  is '平台适配关系';
comment on column T_MB_CATEGORY_NEW.CITYID
  is '地市适配关系';

alter table T_R_GCONTENT modify PVCID default 0000;
alter table T_R_GCONTENT modify CITYID default 0000;


-- Add/modify columns 
alter table T_R_GCONTENT add pLUPDDATE varchar2(30);
-- Add comments to the columns 
comment on column T_R_GCONTENT.pLUPDDATE
  is '电子流同步最后更新时间';
  
 -- Add/modify columns 
alter table T_SYNCTIME_TMP rename column LUPDDATE to pLUPDDATE;
 
 -- Add/modify columns 
alter table T_SYNCTIME_TMP_CY rename column LUPDDATE to pLUPDDATE;  
  
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
       c.lupddate,		-----增加应用更新时间
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

-- Create the synonym 
create or replace synonym PPMS_PKAPPS_DETAIL
  for MM_PPMS.V_CM_CONTENT_PKAPPS_DETAIL;
  
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.055_SSMS','MM1.0.3.060_SSMS');
commit;