-- Create view

--创建v_report_servenday视图----开始-----------
create or replace view v_report_servenday as select
  a.* from report_servenday a
     where a.os_id in (9, 3,1,20)  and stat_time = to_char(sysdate-1,'yyyymmdd');

--创建v_report_servenday视图----结束-----------



update t_r_exportsql set EXPORTSQL = 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
 c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
 c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
 c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
 c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
 c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,c.pvcid,c.cityid,
 c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,
 decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),'''',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT,r.DOWN_COUNT,r.ADD_7DAYS_DOWN_COUNT
 from t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,v_report_servenday r,(select * from T_R_DOWN_SORT_NEW where os_id=''9'') s 
 where  c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = r.content_id(+) and c.contentid = s.content_id(+)  
 and C.SUBTYPE IN (''1'',''2'',''5'',''6'',''7'',''8'',''11'')  order by c.id',EXPORTLINE = 87 where ID = 2;

update t_r_exportsql set EXPORTSQL = 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
 c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
 c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
 c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
 c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
 c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,c.pvcid,c.cityid,
 c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,
 decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT,r.DOWN_COUNT,r.ADD_7DAYS_DOWN_COUNT 
 from t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,v_report_servenday r,(select * from T_R_DOWN_SORT_NEW  where os_id=''9'') s 
 where  c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = r.content_id(+) and c.contentid = s.content_id(+)  
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
 c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,c.pvcid,c.cityid,
 c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,
 decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT ,r.DOWN_COUNT,r.ADD_7DAYS_DOWN_COUNT
 from t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,v_report_servenday r,(select * from T_R_DOWN_SORT_NEW where os_id=''9'') s
 where   c.provider = ''O'' and c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = r.content_id(+) and c.contentid = s.content_id(+)
 and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and c.plupddate is not null and length(c.plupddate) = 19 AND C.SUBTYPE <> ''22''
 UNION ALL select '''', t.contentname, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null,  '''', t.contentid, '''', '''',  '''', '''', 
 '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null,
 null, null, null, null, '''', null, '''', null, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', 
 '''', '''', '''',  to_clob(''''),  null,  to_clob(''''), '''', '''',   to_clob(''''), '''', '''', null, '''', '''', ''D'','''', '''','''','''',null,null,null  
 from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and t.syntype = ''3'' 
 and t.syntime is not null and length(t.syntime) = 19 order by id',EXPORTLINE = 87 where ID = 40;


alter table t_a_android_list add hotlist number(12,6);
 comment on column t_a_android_list.hotlist
  is '最热指标';
  
alter table t_a_android_list add riselist number(12,6);
 comment on column t_a_android_list.riselist
  is '飙升指标';


--修改v_android_list视图---
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
,nvl(d.daynum,0) as daynum,
decode(n.hotscore,null,0,n.hotscore) as hotscore,
decode(n.newRANK_HOT,null,0,n.newRANK_HOT) as newRANK_HOT,
decode(n.souar,null,0,n.souar) as souar,
decode(m.hotlist,null,0,m.hotlist) as hotlist,
decode(m.riselist,null,0,m.riselist) as riselist
  from
       v_datacenter_cm_content g,
       t_a_dc_ppms_service   v,
       t_r_servenday_temp_a a,
       v_content_last    l,
       v_serven_sort    c,
       v_content_newscore n,
       mid_table m,
       ( select distinct r.contentid from t_a_cm_device_resource r where r.pid is not null) r,
       (select contentid,sum(downcount) as daynum from t_a_content_downcount where trunc_lupdate=trunc(sysdate) group by contentid) d


 where l.contentid = g.contentid
   and v.icpcode = g.icpcode
   and v.icpservid = g.icpservid
   and g.contentid = a.CONTENT_ID
   and l.osid = '9'
   and g.contentid = c.CONTENT_ID
   and c.os_id=9
   and g.thirdapptype !='16'
   and g.contentid = n.content_id(+)
   and g.contentid = m.appid(+)
   and g.contentid=d.contentid(+)
   and g.contentid=r.contentid;
   
--修改v_android_list视图--结束-----

-- 增加直播栏目表是否支持直播回放 
alter table T_VO_LIVENODE add playback number default 0;
-- Add comments to the columns 
comment on column T_VO_LIVENODE.playback
  is '0,(默认)不支持回放;1,支持回放';
  
  update T_VO_LIVENODE t set t.playback=1   where id in('20','12','32','34','36','71','41','73','75','77','22');
  
  
  

commit;