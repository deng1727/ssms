
---------------��t_r_gcontent�����ֶ�mapname---------------------------
alter  table t_r_gcontent add mapname VARCHAR2(100);

comment on column t_r_gcontent.mapname
  is 'MAP����������';
  

--------------------------------------------------------------------

--------------------��v_cm_content�����ֶ�devcompanyname----------------
alter  table v_cm_content add devcompanyname VARCHAR2(100);

comment on column v_cm_content.devcompanyname
  is 'MAP����������';
-----------------------------------------------------------------------
  
 update t_r_exportsql set EXPORTSQL = 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
 c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
 c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
 c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
 c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
 c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,p.pvcid,c.cityid,
 c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,
 decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),'''',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT,r.DOWN_COUNT,r.ADD_7DAYS_DOWN_COUNT,c.mapname 
 from s_v_event_pvc p,t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,v_report_servenday r,(select * from T_R_DOWN_SORT_NEW where os_id=''9'') s 
 where  c.contentid=p.contentid(+) and c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = r.content_id(+) and c.contentid = s.content_id(+)  
 and C.SUBTYPE IN (''1'',''2'',''5'',''6'',''7'',''8'',''11'')  order by c.id',EXPORTLINE = 88 where ID = 2;

update t_r_exportsql set EXPORTSQL = 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
 c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
 c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
 c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
 c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
 c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,p.pvcid,c.cityid,
 c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,
 decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT,r.DOWN_COUNT,r.ADD_7DAYS_DOWN_COUNT,c.mapname  
 from s_v_event_pvc p,t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,v_report_servenday r,(select * from T_R_DOWN_SORT_NEW  where os_id=''9'') s 
 where  c.contentid=p.contentid(+) and c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = r.content_id(+) and c.contentid = s.content_id(+)  
 and  to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''6'') 
 and c.plupddate is not null and length(c.plupddate) = 19  AND C.SUBTYPE IN (''1'',''2'',''5'',''6'',''7'',''8'',''11'') 
 UNION ALL select '''', t.contentname, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null,  '''', 
 t.contentid, '''', '''',  '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''',
 '''', '''', '''', '''', '''', '''', '''', null, null, null, null, null, '''', null, '''', null, '''', '''', '''', '''',
 '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''',  to_clob(''''),  null,  to_clob(''''), 
 '''', '''',   to_clob(''''), '''', '''', null, '''', '''',''D'', '''', '''','''','''',null,null,null,''''  from t_syn_result t 
 where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''6'') 
 and t.syntype = ''3'' and t.syntime is not null and length(t.syntime) = 19 order by id',EXPORTLINE = 88 where ID = 6;


update t_r_exportsql set EXPORTSQL = 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
 c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
 c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
 c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
 c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
 c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,p.pvcid,c.cityid,
 c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,
 decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT ,r.DOWN_COUNT,r.ADD_7DAYS_DOWN_COUNT,c.mapname 
 from s_v_event_pvc p,t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,v_report_servenday r,(select * from T_R_DOWN_SORT_NEW where os_id=''9'') s
 where   c.contentid=p.contentid(+) and c.provider = ''O'' and c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = r.content_id(+) and c.contentid = s.content_id(+)
 and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and c.plupddate is not null and length(c.plupddate) = 19 AND C.SUBTYPE <> ''22''
 UNION ALL select '''', t.contentname, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null,  '''', t.contentid, '''', '''',  '''', '''', 
 '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null,
 null, null, null, null, '''', null, '''', null, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', 
 '''', '''', '''',  to_clob(''''),  null,  to_clob(''''), '''', '''',   to_clob(''''), '''', '''', null, '''', '''', ''D'','''', '''','''','''',null,null,null,''''   
 from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and t.syntype = ''3'' 
 and t.syntime is not null and length(t.syntime) = 19 order by id',EXPORTLINE = 88 where ID = 40;
 
 
 --------------------------��ʼ ����ͬ��soft_company---------------------------------------------
 create or replace synonym soft_company
  for soft_company@dl_mm_ppms_new;
--------------------------���� ����ͬ��soft_company----------------------------------------------


----------------------------------��ʼ ��ppms_v_cm_content��Ӵ������ֶ�-----------------------------------------------------------
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
       --ȫ��ȡonlinedate��ʡ��ȡSubOnlineDate
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
                     '2010MM��ҵ�ƻ�����Ӧ��չʾ',
                     decode(c.devcompanyid,
                            null,
                            e.companyname,
                            (select so.companyname
                               from soft_company so
                              where so.companyid = c.devcompanyid)))) as companyname, --�ṩ��
       decode(c.mapcompanyid,
              null,
              '',
              (select so1.companyname
                 from om_company so1
                where c.mapcompanyid = so1.companyid)) devcompanyname, --������
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate, -----����Ӧ�ø���ʱ��
       decode(c.thirdapptype, '7', '2', f.chargeTime) chargeTime,
       --decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype) thirdapptype, ����APP����
       decode(c.secondarytype,
              '2',
              '21',
              decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype)) thirdapptype,
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
              '0') as othernet,
       c.ismmtoevent,
       c.COPYRIGHTFLAG
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f --
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap����
   and (c.status = '0006' or c.status = '1006' or f.status = 5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status = 6)))
      ----Ӧ�����û��������û���ʡ������/����
   and d.AuditStatus = '0003' ----��Ʒ���ͨ��
   and d. ProductStatus in ('2', '3', '5') ----��Ʒ���߼Ʒ�or���Ʒ�or ����
   and f.ID = d.ID ----���ɲ�Ʒ
   and c.contentid = f.contentid ----��Ʒ��ID���������ݱ���
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and (c.thirdapptype in
       ('1', '2', '7', '11', '12', '13', '14', '16', '21') or
       (c.thirdapptype = '5' and c.Jilstatus = '1'))
--------------------------------------------���� ��ppms_v_cm_content��Ӵ������ֶ�-------------------------------------------------

----------------------------------------------------------------------------------------------------------------
alter  table T_R_REFERENCE add VERIFY_STATUS VARCHAR2(10) DEFAULT '1';
alter  table T_R_REFERENCE add VERIFY_DATE DATE DEFAULT SYSDATE;
comment on column T_R_REFERENCE.VERIFY_STATUS
  is '���״̬-- 0 ����У�1 ���ͨ����2 ��˲�ͨ��';
  comment on column T_R_REFERENCE.T_R_REFERENCE
  is '���ʱ��';
-----------------------------------------------------------------------------------------------------------
  
  
  insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.069_SSMS','MM4.0.0.0.075_SSMS');

 
commit;