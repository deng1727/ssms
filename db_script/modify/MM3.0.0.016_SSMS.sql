
-- Add/modify columns 
alter table T_R_EXPORTSQL modify EXPORTSQL VARCHAR2(4000);



delete T_R_EXPORTSQL where id in (15,16,19);


insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (15, 'select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, '''' as SYNCFLAG from t_r_gcontent c,(select * from t_key_resource t where t.keyid=''435'') k, v_service v where c.contentid = k.tid(+) and c.icpservid = v.icpservid and v.mobileprice = 0 and c.chargetime = 1 and (c.subtype is null or c.subtype <> ''21'') UNION ALL select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, '''' as SYNCFLAG from t_r_gcontent c,(select * from t_key_resource t where t.keyid=''435'') k, v_service v, (select distinct tt.contentid, tt.tbtype from V_CM_CONTENT_TB tt ) tb where c.contentid = k.tid(+) and c.icpservid = v.icpservid and c.contentid = tb.contentid and tb.tbtype=''2'' and (c.subtype is null or c.subtype <> ''21'') order by contentid',  '推广联盟-内容全量数据', '1', 50000, ',', to_date('13-01-2014 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), 29, 'i_CTN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_spread', 'UTF-8', '2', '1', '62896', 2);


insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (16, 'select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, ''A'' as SYNCFLAG from t_r_gcontent c, (select * from t_key_resource t where t.keyid = ''435'') k, v_service v where c.contentid = k.tid(+) and c.icpservid = v.icpservid and v.mobileprice = 0 and c.chargetime = 1 and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''16'') and c.plupddate is not null and length(c.plupddate) = 19 and (c.subtype is null or c.subtype <> ''21'') UNION ALL select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, ''A'' as SYNCFLAG from t_r_gcontent c, (select * from t_key_resource t where t.keyid = ''435'') k, v_service v, (select distinct tt.contentid, tt.tbtype from V_CM_CONTENT_TB tt ) tb where c.contentid = k.tid(+) and c.icpservid = v.icpservid and c.contentid = tb.contentid and tb.tbtype = ''2'' and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''16'') and c.plupddate is not null and length(c.plupddate) = 19 and (c.subtype is null or c.subtype <> ''21'') UNION ALL select distinct t.contentid, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', 0, ''D'' from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''16'') and t.syntype = ''3'' and t.syntime is not null and length(t.syntime) = 19 order by contentid',  '推广联盟-内容增量数据', '1', 50000, ',', to_date('13-01-2014 08:01:00', 'dd-mm-yyyy hh24:mi:ss'), 29, 'a_CTN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_spread', 'UTF-8', '2', '1', '20797', 2);



insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (19, 'select c.contentid, d.order7day_count, d.add_order_count, c.averagemark from t_r_gcontent c, v_serven_sort d ,v_service v where c.icpservid = v.icpservid and v.mobileprice = 0 and c.chargetime = 1 and c.contentid = d.content_id(+) and d.os_id = ''9''  and (c.subtype is null or c.subtype <> ''21'') UNION ALL select c.contentid, d.order7day_count, d.add_order_count, c.averagemark from t_r_gcontent c, v_serven_sort d ,V_CM_CONTENT_TB tb where c.contentid = tb.contentid and tb.tbtype = ''2'' and c.contentid = d.content_id(+) and d.os_id = ''9'' and (c.subtype is null or c.subtype <> ''21'') order by contentid', '推广联盟-运营数据', '1', 50000, ',', to_date('13-01-2014 08:05:00', 'dd-mm-yyyy hh24:mi:ss'), 4, 'i_DRC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_spread', 'UTF-8', '2', '1', '16504', 2);





create or replace view v_android_list as
select g.contentid,

--decode(v.mobileprice, 0, 1, 2)||to_char(trunc(l.createtime),'yyyymmdd')||decode(catename, '软件', 1, '游戏', 1, '主题', 2, 3)
--||to_char(l.createtime, 'hh24miss') as rank_new,

to_char(updatetime,'yymmdd')||decode(v.mobileprice, 0, 1, 0)||(2000000-to_char(l.createtime,'yymmdd'))||decode(catename, '软件', 2, '游戏', 2, '主题', 1, 0)||
(4000-to_char(l.createtime, 'hh24mi')) as rank_new

,(nvl(a.ADD_ORDER_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_all


,(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_fee
,(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_hot
,(1000+nvl(c.scores,-200))*1000||(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymm') as rank_scores

,decode(g.catename,'软件','appSoftWare','游戏','appGame','主题','appTheme','') as catename


,g.name,a.ADD_7DAYS_DOWN_COUNT,a.add_order_count,v.mobileprice
--decode(v.mobileprice, 0, 0, 10) as mobileprice_alias,
--trunc(l.createtime) as createtime_trunc,
--decode(catename, '软件', 1,  '主题', 2, 10) as catename_alias,
--to_char(l.createtime, 'hh24miss') as createtime_tochar,



,c.scores,l.createtime,l.updatetime,g.companyid
,nvl(d.daynum,0) as daynum
  from
       v_datacenter_cm_content g,
       t_a_dc_ppms_service   v,
       t_r_servenday_temp_a a,
       v_content_last    l,
       v_serven_sort    c,
       ( select distinct r.contentid from t_a_cm_device_resource r where r.pid is not null) r,
       (select contentid,sum(downcount) as daynum from t_a_content_downcount where trunc_lupdate=trunc(sysdate) group by contentid) d


 where l.contentid = g.contentid
   and v.icpcode = g.icpcode
   and v.icpservid = g.icpservid
   and g.contentid = a.CONTENT_ID
   and l.osid = '9'
   and g.contentid = c.CONTENT_ID
   and c.os_id=9
   and g.contentid=d.contentid(+)
   and g.contentid=r.contentid
   and (g.thirdapptype is null or g.thirdapptype <> '21');







create or replace view ppms_v_cm_content as
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
              f.SubOnlineDate) as marketdate,
       --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       decode(c.thirdapptype,
              '12',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '12'),
              '16',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '16'),
              decode(c.companyid,
                     '116216',
                     '2010MM创业计划优秀应用展示',
                     e.companyname)) as companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate, -----增加应用更新时间
       decode(c.thirdapptype, '7', '2', f.chargeTime) chargeTime,
       decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype) thirdapptype,
       c.pvcid,
       c.citysid as cityid,
       decode(f.chargetime ||
              decode(c.chargetype, '01', '02', c.chargetype) || c.contattr ||
              e.operationsmode || c.thirdapptype,
              '102G01',
              '1',
              '102G02',
              '1',
              '102G05',
              '1',
              '102G012',
              '1',
              '0') as othernet
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f --
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' or f.status = 5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status = 6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus in ('2', '3', '5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and (c.thirdapptype in ('1', '2', '7', '11', '12', '13', '14','16','21') or
       (c.thirdapptype = '5' and c.Jilstatus = '1'));



create or replace view ppms_v_a_service as
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
       decode(c.thirdapptype,
              '11',
              c.pkgfee||'',
              decode(p.chargetime || v2.paytype,
                     '20',
                     p.feedesc,
                     v2.chargedesc)) as chargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from v_valid_company    v1,
       v_om_product       v2,
       OM_PRODUCT_CONTENT p,
       cm_content         c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id
   and (c.thirdapptype in ('1', '2','6','7','11','12','13','14','16','21') or (c.thirdapptype = '5' and c.Jilstatus = '1') )
   and v2.ServiceCode not in('1800075703','1800075704')--add by aiyan hehe...
     and (v2.paytype = 0 or v2.paytype is null)--add by aiyan 20130513 xuhuibang say....add this
--  UNION ALL
-- select
 /*      t.contentid,
       t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--上线计费
       null,
       --8,
       t.servflag,--新游戏，折扣类型
       '0'||t.chargetype as chargetype,--新游戏，计费类型
       null,
       t.mobileprice,
        t.ptypeid,--新游戏，支付方式
       t.chargedesc,--新游戏，资费类型
       'B',
       'G',--全网业务
       t.servtype,--新游戏，业务类型
       t.firsttype,--新游戏，首发类型
       t.lupddate
  from t_game_service_new t*/;


create or replace view ppms_v_service as
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
       decode(c.thirdapptype,
              '11',
              c.pkgfee||'',
              decode(p.chargetime || v2.paytype,
                     '20',
                     p.feedesc,
                     v2.chargedesc)) as chargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from v_valid_company    v1,
       v_om_product       v2,
       OM_PRODUCT_CONTENT p,
       cm_content         c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id
   and (c.thirdapptype in ('1', '2','6','7','11','12','13','14','16','21') or (c.thirdapptype = '5' and c.Jilstatus = '1') )
  UNION ALL
 select
       t.contentid,
       t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--上线计费
       null,
       --8,
       t.servflag,--新游戏，折扣类型
       '0'||t.chargetype as chargetype,--新游戏，计费类型
       null,
       t.mobileprice*10 as mobileprice,--基地游戏是分，MM是厘
       t.ptypeid,--新游戏，支付方式
       t.chargedesc,--新游戏，资费类型
       'B',
       'G',--全网业务
       t.servtype,--新游戏，业务类型
       t.firsttype,--新游戏，首发类型
       t.lupddate
  from t_game_service_new t;





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.129_SSMS','MM3.0.0.016_SSMS');

commit;

