---回滚数据
delete from t_roleright where ROLEID=1 and RIGHTID='1_0807_RES_BLACKLIST';
delete from t_right where RIGHTID='1_0807_RES_BLACKLIST';

alter table T_CONTENT_BACKLIST drop column create_time ;
alter table T_CONTENT_BACKLIST drop column modify_time ;
alter table T_CONTENT_BACKLIST drop column black_type ;

delete from t_resource where resourceKey='WEB_INF_ADD_BLACK_OK';
delete from t_resource where resourceKey='WEB_INF_ADD_BLACK_FAIL';
delete from t_resource where resourceKey='WEB_INF_BLACK_EXIST';
delete from t_resource where resourceKey='WEB_INF_MODIFY_BLACK_OK';
delete from t_resource where resourceKey='WEB_INF_MODIFY_BLACK_FAIL';
delete from t_resource where resourceKey='WEB_INF_DEL_BLACK_OK';
delete from t_resource where resourceKey='WEB_INF_DEL_BLACK_FAIL';

--基地音乐回滚数据
drop table T_MB_MUSIC;
drop table T_MB_CATEGORY;
drop table T_MB_REFERENCE;
drop  sequence SEQ_BM_CATEGORY_ID;

--G+游戏包回滚表结构
alter table T_GAME_BASE drop column STATE;

--小编推荐
alter table T_CONTENT_COUNT drop column RECOMMEND_GRADE;

--回滚变更表结构
alter table T_CONTENT_COUNT modify LATESTCOUNT default 0 not null;
alter table T_CONTENT_COUNT modify RECOMMENDCOUNT default 0 not null;
alter table T_CONTENT_COUNT modify COMPECOUNT default 0 not null;

--删除同义词
drop synonym ppms_v_cm_content_recommend;
---回滚AP过有效期
create or replace view ppms_v_cm_content as
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
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as lupddate,
       f.chargeTime,
       c.thirdapptype
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
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype in ('1','2')--增加widget应用;

drop  view PPMS_V_SERVICE;

drop materialized view V_THIRD_SERVICE;
create materialized view V_THIRD_SERVICE
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
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from om_company v1, v_om_product v2
 where v1.companyid = v2.CompanyID
   and v1.icptype = 1
   and v2.ProviderType = 'V';

-----回滚新加的5条自动更新规则
delete from t_caterule t where  t.ruleid in ('152','153','157');
delete from t_caterule_cond t where  t.ruleid in ('152','153','157');
delete from t_category_rule t where  t.ruleid in ('152','153','157');




delete DBVERSION where PATCHVERSION = 'MM1.0.0.118_SSMS' and LASTDBVERSION = 'MM1.0.0.097_SSMS';
commit;