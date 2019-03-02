alter table t_vo_collect add collectvid VARCHAR2(60);
comment on column t_vo_collect.collectvid
  is '内容集虚拟ID:用以MM侧向AAA发起内容集订购时填写内容集ID参数';


create or replace synonym s_v_event_pvc
  for v_event_pvc @DL_PPMS_DEVICE;
  
  update t_r_exportsql set EXPORTSQL = 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
 c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
 c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
 c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
 c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
 c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,p.pvcid,c.cityid,
 c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,
 decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),'''',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT,r.DOWN_COUNT,r.ADD_7DAYS_DOWN_COUNT
 from s_v_event_pvc p,t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,v_report_servenday r,(select * from T_R_DOWN_SORT_NEW where os_id=''9'') s 
 where  c.contentid=p.contentid(+) and c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = r.content_id(+) and c.contentid = s.content_id(+)  
 and C.SUBTYPE IN (''1'',''2'',''5'',''6'',''7'',''8'',''11'')  order by c.id',EXPORTLINE = 87 where ID = 2;

update t_r_exportsql set EXPORTSQL = 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
 c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
 c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
 c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
 c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
 c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,p.pvcid,c.cityid,
 c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,
 decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT,r.DOWN_COUNT,r.ADD_7DAYS_DOWN_COUNT 
 from s_v_event_pvc p,t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,v_report_servenday r,(select * from T_R_DOWN_SORT_NEW  where os_id=''9'') s 
 where  c.contentid=p.contentid(+) and c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = r.content_id(+) and c.contentid = s.content_id(+)  
 and  to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''6'') 
 and c.plupddate is not null and length(c.plupddate) = 19  AND C.SUBTYPE IN (''1'',''2'',''5'',''6'',''7'',''8'',''11'') 
 UNION ALL select '''', t.contentname, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null,  '''', 
 t.contentid, '''', '''',  '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''',
 '''', '''', '''', '''', '''', '''', '''', null, null, null, null, null, '''', null, '''', null, '''', '''', '''', '''',
 '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''',  to_clob(''''),  null,  to_clob(''''), 
 '''', '''',   to_clob(''''), '''', '''', null, '''', '''',''D'', '''', '''','''','''',null,null,null  from t_syn_result t 
 where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''6'') 
 and t.syntype = ''3'' and t.syntime is not null and length(t.syntime) = 19 order by id',EXPORTLINE = 87 where ID = 6;


update t_r_exportsql set EXPORTSQL = 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
 c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
 c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
 c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
 c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
 c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,p.pvcid,c.cityid,
 c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,
 decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT ,r.DOWN_COUNT,r.ADD_7DAYS_DOWN_COUNT
 from s_v_event_pvc p,t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,v_report_servenday r,(select * from T_R_DOWN_SORT_NEW where os_id=''9'') s
 where   c.contentid=p.contentid(+) and c.provider = ''O'' and c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = r.content_id(+) and c.contentid = s.content_id(+)
 and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and c.plupddate is not null and length(c.plupddate) = 19 AND C.SUBTYPE <> ''22''
 UNION ALL select '''', t.contentname, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null,  '''', t.contentid, '''', '''',  '''', '''', 
 '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null,
 null, null, null, null, '''', null, '''', null, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', 
 '''', '''', '''',  to_clob(''''),  null,  to_clob(''''), '''', '''',   to_clob(''''), '''', '''', null, '''', '''', ''D'','''', '''','''','''',null,null,null  
 from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and t.syntype = ''3'' 
 and t.syntime is not null and length(t.syntime) = 19 order by id',EXPORTLINE = 87 where ID = 40;

  