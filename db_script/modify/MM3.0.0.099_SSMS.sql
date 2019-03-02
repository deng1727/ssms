create or replace view rebuil_ppms.v_datacenter_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       decode(c.thirdapptype,
              '10',
              c.oviappid,
              '12',
              c.oviappid,
        '16',
              c.oviappid,
              c.contentcode) ContentCode,
       c.Keywords,
       decode(c.status, '0015', 'L', '1015', 'L', d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,
              '0006',
              f.onlinedate,
              '1006',
              f.onlinedate,
              f.SubOnlineDate) as marketdate,
       --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       decode(c.thirdapptype,
              '12',
              (select max(m.developername)
                 from cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '12'),
              '16',
              (select max(m.developername)
                 from cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '16'),
              decode(c.companyid,
                     '116216',
                     '2010MM创业计划优秀应用展示',
                     e.companyname)) as companyname,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate, -----增加应用更新时间
       decode(c.thirdapptype, '7', '2', f.chargeTime) chargeTime,
       c.citysid as cityid,
       i.aliasid as appCateID,
       i.aliasname as appCateName,
       h.appdesc,
       (select t.url from cm_image_properties t where t.resourceid=h.wwwpropapicture1 and t.contentid=c.contentid and rownum<2) as wwwpropapicture1,
       (select t.url from cm_image_properties t where t.resourceid=h.wwwpropapicture2 and t.contentid=c.contentid and rownum<2) as wwwpropapicture2,
       (select t.url from cm_image_properties t where t.resourceid=h.wwwpropapicture3 and t.contentid=c.contentid and rownum<2) as wwwpropapicture3,
       h.language,
       (select t.url from cm_image_properties t where t.resourceid=h.logo1 and t.contentid=c.contentid and rownum<2) as logo1,
       (select t.url from cm_image_properties t where t.resourceid=h.logo2 and t.contentid=c.contentid and rownum<2) as logo2,
       (select t.url from cm_image_properties t where t.resourceid=h.logo3 and t.contentid=c.contentid and rownum<2) as logo3,
       (select t.url from cm_image_properties t where t.resourceid=h.logo4 and t.contentid=c.contentid and rownum<2) as logo4,
       (select t.url from cm_image_properties t where t.resourceid=h.logo5 and t.contentid=c.contentid and rownum<2) as logo5,
       (select t.url from cm_image_properties t where t.resourceid=h.logo6 and t.contentid=c.contentid and rownum<2) as logo6,
       h.onlinetype,
       h.version,
       (select t.url from cm_image_properties t where t.resourceid=h.picture1 and t.contentid=c.contentid and rownum<2) as picture1,
       (select t.url from cm_image_properties t where t.resourceid=h.picture2 and t.contentid=c.contentid and rownum<2) as picture2,
       (select t.url from cm_image_properties t where t.resourceid=h.picture3 and t.contentid=c.contentid and rownum<2) as picture3,
       (select t.url from cm_image_properties t where t.resourceid=h.picture4 and t.contentid=c.contentid and rownum<2) as picture4,
       decode(c.secondarytype,'2','21',decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype)) thirdapptype,
       c.ismmtoevent,
       c.copyrightflag,
       c.pvcid
       
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       om_company    e,
       OM_PRODUCT_CONTENT f,
       cm_ct_appsoftware h,
       v_om_dictionary i
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and ((c.status = '0006' or c.status = '1006') and
       f.status='2' and f.onlinestatus<>'5')
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003'
   and f.ID = d.ID
   and c.contentid = f.contentid
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and (c.thirdapptype in ('1', '2', '7', '11', '12', '13', '14','16','22') or
       (c.thirdapptype = '5' and c.Jilstatus = '1'))
   and c.contentid=h.contentid
   and h.type=i.id
   and e.companyid not in('297985','304419','304410')
   and exists (select 1 from cm_ct_program g where g.contentid=c.contentid and g.platform in('3','9'))
   --and c.SECONDARYTYPE!='2'
   union all
   select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       decode(c.thirdapptype,
              '10',
              c.oviappid,
              '12',
              c.oviappid,
        '16',
              c.oviappid,
              c.contentcode) ContentCode,
       c.Keywords,
       decode(c.status, '0015', 'L', '1015', 'L', d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,
              '0006',
              f.onlinedate,
              '1006',
              f.onlinedate,
              f.SubOnlineDate) as marketdate,
       --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       decode(c.thirdapptype,
              '12',
              (select max(m.developername)
                 from cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '12'),
              '16',
              (select max(m.developername)
                 from cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '16'),
              decode(c.companyid,
                     '116216',
                     '2010MM创业计划优秀应用展示',
                     e.companyname)) as companyname,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate, -----增加应用更新时间
       decode(c.thirdapptype, '7', '2', f.chargeTime) chargeTime,
       c.citysid as cityid,
       i.aliasid as appCateID,
       i.aliasname as appCateName,
       h.appdesc,
       (select t.url from cm_image_properties t where t.resourceid=h.wwwpropapicture1 and t.contentid=c.contentid and rownum<2) as wwwpropapicture1,
       (select t.url from cm_image_properties t where t.resourceid=h.wwwpropapicture2 and t.contentid=c.contentid and rownum<2) as wwwpropapicture2,
       (select t.url from cm_image_properties t where t.resourceid=h.wwwpropapicture3 and t.contentid=c.contentid and rownum<2) as wwwpropapicture3,
       h.language,
       (select t.url from cm_image_properties t where t.resourceid=h.logo1 and t.contentid=c.contentid and rownum<2) as logo1,
       (select t.url from cm_image_properties t where t.resourceid=h.logo2 and t.contentid=c.contentid and rownum<2) as logo2,
       (select t.url from cm_image_properties t where t.resourceid=h.logo3 and t.contentid=c.contentid and rownum<2) as logo3,
       (select t.url from cm_image_properties t where t.resourceid=h.logo4 and t.contentid=c.contentid and rownum<2) as logo4,
       (select t.url from cm_image_properties t where t.resourceid=h.logo5 and t.contentid=c.contentid and rownum<2) as logo5,
       (select t.url from cm_image_properties t where t.resourceid=h.logo6 and t.contentid=c.contentid and rownum<2) as logo6,
       h.onlinetype,
       h.version,
       (select t.url from cm_image_properties t where t.resourceid=h.picture1 and t.contentid=c.contentid and rownum<2) as picture1,
       (select t.url from cm_image_properties t where t.resourceid=h.picture2 and t.contentid=c.contentid and rownum<2) as picture2,
       (select t.url from cm_image_properties t where t.resourceid=h.picture3 and t.contentid=c.contentid and rownum<2) as picture3,
       (select t.url from cm_image_properties t where t.resourceid=h.picture4 and t.contentid=c.contentid and rownum<2) as picture4,
       decode(c.secondarytype,'2','21',decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype)) thirdapptype,
       c.ismmtoevent,
       c.copyrightflag,
       c.pvcid
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       om_company    e,
       OM_PRODUCT_CONTENT f,
       cm_ct_apptheme h,
       v_om_dictionary i
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and ((c.status = '0006' or c.status = '1006') and
       f.status='2' and f.onlinestatus<>'5')
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003'
   and f.ID = d.ID
   and c.contentid = f.contentid
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and (c.thirdapptype in ('1', '2', '7', '11', '12', '13', '14','16','22') or
       (c.thirdapptype = '5' and c.Jilstatus = '1'))
   and c.contentid=h.contentid
   and h.type=i.id
   and e.companyid not in('297985','304419','304410')
   and exists (select 1 from cm_ct_program g where g.contentid=c.contentid and g.platform in('3','9'))
   --and c.SECONDARYTYPE!='2'
   union all
   select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       decode(c.thirdapptype,
              '10',
              c.oviappid,
              '12',
              c.oviappid,
        '16',
              c.oviappid,
              c.contentcode) ContentCode,
       c.Keywords,
       decode(c.status, '0015', 'L', '1015', 'L', d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,
              '0006',
              f.onlinedate,
              '1006',
              f.onlinedate,
              f.SubOnlineDate) as marketdate,
       --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       decode(c.thirdapptype,
              '12',
              (select max(m.developername)
                 from cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '12'),
              '16',
              (select max(m.developername)
                 from cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '16'),
              decode(c.companyid,
                     '116216',
                     '2010MM创业计划优秀应用展示',
                     e.companyname)) as companyname,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate, -----增加应用更新时间
       decode(c.thirdapptype, '7', '2', f.chargeTime) chargeTime,
       c.citysid as cityid,
       i.aliasid as appCateID,
       i.aliasname as appCateName,
       h.appdesc,
       (select t.url from cm_image_properties t where t.resourceid=h.wwwpropapicture1 and t.contentid=c.contentid and rownum<2) as wwwpropapicture1,
       (select t.url from cm_image_properties t where t.resourceid=h.wwwpropapicture2 and t.contentid=c.contentid and rownum<2) as wwwpropapicture2,
       (select t.url from cm_image_properties t where t.resourceid=h.wwwpropapicture3 and t.contentid=c.contentid and rownum<2) as wwwpropapicture3,
       h.language,
       (select t.url from cm_image_properties t where t.resourceid=h.logo1 and t.contentid=c.contentid and rownum<2) as logo1,
       (select t.url from cm_image_properties t where t.resourceid=h.logo2 and t.contentid=c.contentid and rownum<2) as logo2,
       (select t.url from cm_image_properties t where t.resourceid=h.logo3 and t.contentid=c.contentid and rownum<2) as logo3,
       (select t.url from cm_image_properties t where t.resourceid=h.logo4 and t.contentid=c.contentid and rownum<2) as logo4,
       (select t.url from cm_image_properties t where t.resourceid=h.logo5 and t.contentid=c.contentid and rownum<2) as logo5,
       (select t.url from cm_image_properties t where t.resourceid=h.logo6 and t.contentid=c.contentid and rownum<2) as logo6,
       h.onlinetype,
       h.version,
       (select t.url from cm_image_properties t where t.resourceid=h.picture1 and t.contentid=c.contentid and rownum<2) as picture1,
       (select t.url from cm_image_properties t where t.resourceid=h.picture2 and t.contentid=c.contentid and rownum<2) as picture2,
       (select t.url from cm_image_properties t where t.resourceid=h.picture3 and t.contentid=c.contentid and rownum<2) as picture3,
       (select t.url from cm_image_properties t where t.resourceid=h.picture4 and t.contentid=c.contentid and rownum<2) as picture4,
       decode(c.secondarytype,'2','21',decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype)) thirdapptype,
       c.ismmtoevent,
       c.copyrightflag,
       c.pvcid
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       om_company    e,
       OM_PRODUCT_CONTENT f,
       cm_ct_appgame h,
       v_om_dictionary i
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and ((c.status = '0006' or c.status = '1006') and
       f.status='2' and f.onlinestatus<>'5')
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003'
   and f.ID = d.ID
   and c.contentid = f.contentid
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and (c.thirdapptype in ('1', '2', '7', '11', '12', '13', '14','16','22') or
       (c.thirdapptype = '5' and c.Jilstatus = '1'))
   and c.contentid=h.contentid
   and h.type=i.id
   and e.companyid not in('297985','304419','304410')
   and exists (select 1 from cm_ct_program g where g.contentid=c.contentid and g.platform in('3','9'))
  -- and c.SECONDARYTYPE!='2'
   with read only;
