

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
              decode(f.status || f.substatus, '61', '0006', '0008'),
              '1015',
              decode(f.status || f.substatus, '61', '0006', '0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L', d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,
              '0006',
              f.onlinedate,
              '1006',
              f.onlinedate,
              f.SubOnlineDate) as marketdate, --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       p.developername companyname,
       p.contestgroup as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate,
       f.chargeTime,
       c.thirdapptype,
       p.province as pvcid,
       p.cityid,
       c.contestcode,
       c.contestyear,
       q.COLLEGE as college,
       q.COLLEGEID as collegeId,
       c.contestchannel,
       decode(c.hatchappid, null, decode(c.contenttype, '1002', '0', '1'), '0') as othernet

  from cm_content_type      a,
       cm_catalog           b,
       cm_content           c,
       v_om_product         d,
       v_valid_company      e,
       OM_PRODUCT_CONTENT   f,
       OM_DEVELOPER_CONTEST p,
       cm_content_college   q
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and p.college = q.COLLEGEID(+)
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006')
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
      --and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.Status in ('2', '3') ----产品上线计费or不计费or  去掉了下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid;







alter table V_CM_CONTENT_CY drop column wpid;



delete DBVERSION where PATCHVERSION = 'MM2.0.0.0.075_SSMS' and LASTDBVERSION = 'MM2.0.0.0.069_SSMS';
commit;