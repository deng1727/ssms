----------------------------------------------------
insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
      values (8, 'dongke@aspirecn.com,lixin_a@aspirecn.com', 'MM货架与WWW门户数据同步', '0100', '0', '7', '0', null);

insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
      values (9, 'dongke@aspirecn.com,lixin_a@aspirecn.com', 'MM货架与WAP门户视频数据同步9', '0830', '1', '7', '0', 'https://10.101.10.44:18022/videoSyncProcServlet.do');
insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
      values (10, 'dongke@aspirecn.com,lixin_a@aspirecn.com', 'MM货架与WAP门户视频数据同步10', '1230', '1', '7', '0', 'https://10.101.10.44:18022/videoSyncProcServlet.do');
insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
      values (11, 'dongke@aspirecn.com,lixin_a@aspirecn.com', 'MM货架与WAP门户视频数据同步11', '1630', '1', '7', '0', 'https://10.101.10.44:18022/videoSyncProcServlet.do');
insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
      values (12, 'dongke@aspirecn.com,lixin_a@aspirecn.com', 'MM货架与WAP门户视频数据同步12', '2030', '1', '7', '0', 'https://10.101.10.44:18022/videoSyncProcServlet.do');


insert into t_r_exportsql(exporttypeother,id, exportsql, exportname,exporttype,exportpagenum,exportline,filename,filepath,encoder,exportbyauto,groupid)
      values(',',40,'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
      c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
      c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
      c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
      c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
      c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,c.pvcid,c.cityid,
      c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'' from t_r_gcontent c where to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and c.plupddate is not null and length(c.plupddate) = 19 UNION ALL select '''', t.contentname, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null,  '''', t.contentid, '''', '''',  '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null, null, null, null, '''', null, '''', null, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''',  to_clob(''''),  null,  to_clob(''''), '''', '''',   to_clob(''''), '''', '''', null, '''', '''', ''D'' from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and t.syntype = ''3'' and t.syntime is not null and length(t.syntime) = 19 order by id',
       '内容信息表增量同步','1',50000,80,'s_CTN_%YYYYMMDDHH24%_%NNN%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','UTF-8','2','8');

insert into t_r_exportsql(exporttypeother,id,exportsql,exportname,exporttype,exportpagenum,exportline,filename, filepath,encoder,exportbyauto,groupid)
     values(',',41,' select v.CONTENTID, v.PRODUCTNAME, v.FEE, v.CHARGETYPE, v.TBTYPE,v.paycode,v.feedesc  from V_CM_CONTENT_TB v,t_r_gcontent c where v.CONTENTID= c.contentid and to_date(c.plupddate,''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''41'') and c.plupddate is not null order by v.CONTENTID',
     '应用内计费信息增量同步','1',50000,7,'s_CTB_%YYYYMMDDHH24%_%NNN%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','UTF-8','2','8');

insert into t_r_exportsql(exporttypeother,id,exportsql,exportname,exporttype,exportpagenum,exportline,filename,filepath,encoder,exportbyauto,groupid)
     values(',',42,'select t.pid, t.device_id, t.device_name, t.contentid, t.contentname, t.resourceid, t.id, t.programsize, t.createdate, t.prosubmitdate, t.match, t.version, t.versionname, t.permission, t.picture1, t.picture2, t.picture3, t.picture4, t.absolutepath, t.iscdn, CDNURL, ISWHITELIST from T_A_CM_DEVICE_RESOURCE t, t_r_gcontent c where t.CONTENTID= c.contentid and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''42'') and c.plupddate is not null and length(c.plupddate) = 19',
     '安卓虚拟机型适配数据增量同步','1',50000,22,'s_TACM_DR_%YYYYMMDDHH24%_%NNN%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','UTF-8','2','8');


-------------------------新增 营销信息表增量同步，业务表增量同步接口----------
insert into t_r_exportsql(exporttypeother,id,exportsql,exportname,exporttype,exportpagenum,exportline,filename, filepath,encoder,exportbyauto,groupid)
     values(',',59,'select t.ID,t.CONTENTID,t.NAME,t.SPNAME,t.TYPE,t.DISCOUNT,t.DATESTART,t.DATEEND,t.TIMESTART,t.TIMEEND,t.LUPDATE,t.USERID,t.MOBILEPRICE,t.EXPPRICE,t.ICPCODE,t.ISRECOMM from T_CONTENT_EXT t where t.LUPDATE>(select e.lasttime from t_r_exportsql e where e.id = ''59'') order by t.ID',
     '营销信息表增量同步','1',50000,16,'a_MKT_%YYYYMMDDHH24%_%NNN%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','UTF-8','2','8');

insert into t_r_exportsql(exporttypeother,id,exportsql,exportname,exporttype,exportpagenum,exportline,filename, filepath,encoder,exportbyauto,groupid)
     values(',',60,'select s.contentid,s.icpcode,s.spname,s.spshortname,s.icpservid,s.servname,s.servstatus,s.umflag,s.servtype,s.chargetype,s.paytype,s.mobileprice,s.dotcardprice,s.chargedesc,s.providertype,s.servattr,s.servdesc,s.pksid,s.lupddate from V_SERVICE s where s.lupddate>(select e.lasttime from t_r_exportsql e where e.id = ''60'') order by s.contentid',
     '业务表增量同步','1',50000,19,'s_CTN_%YYYYMMDDHH24%_%NNN%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','UTF-8','2','8');

--------------------------08:30----------------------------------------------------------
insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   43,
  -- 'select t.PROGRAMID,t.VIDEOID,t.PROGRAMNAME,t.NODEID,t.DESCRIPTION,t.LOGOPATH,t.timelength,t.SHOWTIME,t.LASTUPTIME,t.PROGRAMTYPE,t.EXPORTTIME,t.FTPLOGOPATH,t.TRUELOGOPATH,t.SORTID,t.ISLINK,t.PRODUCTID from t_vo_program t where  t.exporttime > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 20:30:00''),'' yyyy - mm - dd hh24 :mi :ss '')-1 order by t.PROGRAMID',
   'select t.PROGRAMID,t.VIDEOID,t.PROGRAMNAME,t.NODEID,t.DESCRIPTION,t.LOGOPATH,t.timelength,t.SHOWTIME,t.LASTUPTIME,t.PROGRAMTYPE,t.EXPORTTIME,t.FTPLOGOPATH,t.TRUELOGOPATH,t.SORTID,t.ISLINK,t.PRODUCTID from t_vo_program t where  t.exporttime > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 07:00:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.PROGRAMID',
   
   '视频节目详情增量',
   '2',
   50000,
   16,
   'a-v-videodetail_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '9');


insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   44,
   'select t.VIDEOID,t.CODERATEID,t.FILEPATH,t.DOWNLOADFILEPATH,t.UPDATETIME,t.FILESIZE  from T_VO_VIDEO t where  t.UPDATETIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 07:00:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.VIDEOID',
   '视频文件增量',
   '2',
   50000,
   6,
   'a_v-videoCode_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '9');
insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   45,
   --'select t.ID,t.PARENTID,t.BASEID,t.BASETYPE,t.BASENAME,t.SORTID ,t.ISSHOW,t.BASEPARENTID,t.CDESC,t.NODENUM,t.REFNUM,t.UPDATETIME  from t_vo_category t where  t.UPDATETIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 20:30:00''),'' yyyy - mm - dd hh24 :mi :ss '')-1 order by t.ID',
   'select t.ID,t.PARENTID,t.BASEID,t.BASETYPE,t.BASENAME,t.SORTID ,t.ISSHOW,t.BASEPARENTID,t.CDESC,t.NODENUM,t.REFNUM,t.UPDATETIME  from t_vo_category t where  t.UPDATETIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 07:00:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.ID',   
   '视频货架表增量同步',
   '2',
   50000,
   12,
   'a_v_category_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '9');

insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   46,
   'select t.ID,t.CATEGORYID,t.PROGRAMID,t.PROGRAMNAME,t.SORTID,t.EXPORTTIME ,t.BASEID,t.BASETYPE,t.nodeid from t_vo_reference t where  t.EXPORTTIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 07:00:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.id',
   '视频商品表增量同步',
   '2',
   50000,
   9,
   'a_v_refrence_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '9');
 -------------------------12:30--------------------------
insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   47,
   'select t.PROGRAMID,t.VIDEOID,t.PROGRAMNAME,t.NODEID,t.DESCRIPTION,t.LOGOPATH,t.timelength,t.SHOWTIME,t.LASTUPTIME,t.PROGRAMTYPE,t.EXPORTTIME,t.FTPLOGOPATH,t.TRUELOGOPATH,t.SORTID,t.ISLINK,t.PRODUCTID  from t_vo_program t where t.exporttime > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 08:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.PROGRAMID',
   '视频节目详情增量',
   '2',
   50000,
   16,
   'a-v-videodetail_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '10');


insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   48,
   'select t.VIDEOID,t.CODERATEID,t.FILEPATH,t.DOWNLOADFILEPATH,t.UPDATETIME,t.FILESIZE  from T_VO_VIDEO t where  t.UPDATETIME >  to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 08:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.VIDEOID',
   '视频文件增量',
   '2',
   50000,
   6,
   'a_v-videoCode_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '10');
insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   49,
   'select t.ID,t.PARENTID,t.BASEID,t.BASETYPE,t.BASENAME,t.SORTID ,t.ISSHOW,t.BASEPARENTID,t.CDESC,t.NODENUM,t.REFNUM,t.UPDATETIME  from t_vo_category t where  t.UPDATETIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 08:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.ID',
   '视频货架表增量同步',
   '2',
   50000,
   12,
   'a_v_category_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '10');

insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   50,
   'select t.ID,t.CATEGORYID,t.PROGRAMID,t.PROGRAMNAME,t.SORTID,t.EXPORTTIME ,t.BASEID,t.BASETYPE,t.nodeid from t_vo_reference t where  t.EXPORTTIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 08:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.id',
   '视频商品表增量同步',
   '2',
   50000,
   9,
   'a_v_refrence_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '10');
--------------------------16:30-------------------
insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   51,
   'select t.PROGRAMID,t.VIDEOID,t.PROGRAMNAME,t.NODEID,t.DESCRIPTION,t.LOGOPATH,t.timelength,t.SHOWTIME,t.LASTUPTIME,t.PROGRAMTYPE,t.EXPORTTIME,t.FTPLOGOPATH,t.TRUELOGOPATH,t.SORTID,t.ISLINK,t.PRODUCTID from t_vo_program t where  t.exporttime > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 12:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.PROGRAMID',
   '视频节目详情增量',
   '2',
   50000,
   16,
   'a-v-videodetail_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '11');


insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   52,
   'select t.VIDEOID,t.CODERATEID,t.FILEPATH,t.DOWNLOADFILEPATH,t.UPDATETIME,t.FILESIZE  from T_VO_VIDEO t where  t.UPDATETIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 12:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.VIDEOID',
   '视频文件增量',
   '2',
   50000,
   6,
   'a_v-videoCode_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '11');
insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   53,
   'select t.ID,t.PARENTID,t.BASEID,t.BASETYPE,t.BASENAME,t.SORTID ,t.ISSHOW,t.BASEPARENTID,t.CDESC,t.NODENUM,t.REFNUM,t.UPDATETIME  from t_vo_category t where  t.UPDATETIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 12:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.ID',
   '视频货架表增量同步',
   '2',
   50000,
   12,
   'a_v_category_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '11');

insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   54,
   'select t.ID,t.CATEGORYID,t.PROGRAMID,t.PROGRAMNAME,t.SORTID,t.EXPORTTIME ,t.BASEID,t.BASETYPE,t.nodeid from t_vo_reference t where  t.EXPORTTIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 12:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.id',
   '视频商品表增量同步',
   '2',
   50000,
   9,
   'a_v_refrence_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '11');
----------------20:30-------------------------

insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   55,
   'select t.PROGRAMID,t.VIDEOID,t.PROGRAMNAME,t.NODEID,t.DESCRIPTION,t.LOGOPATH,t.timelength,t.SHOWTIME,t.LASTUPTIME,t.PROGRAMTYPE,t.EXPORTTIME,t.FTPLOGOPATH,t.TRUELOGOPATH,t.SORTID,t.ISLINK,t.PRODUCTID  from t_vo_program t where  t.exporttime > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 16:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.PROGRAMID',
   '视频节目详情增量',
   '2',
   50000,
   16,
   'a-v-videodetail_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '12');


insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   56,
   'select t.VIDEOID,t.CODERATEID,t.FILEPATH,t.DOWNLOADFILEPATH,t.UPDATETIME,t.FILESIZE  from T_VO_VIDEO t where  t.UPDATETIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 16:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.VIDEOID',
   '视频文件增量',
   '2',
   50000,
   6,
   'a_v-videoCode_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '12');
insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   57,
   'select t.ID,t.PARENTID,t.BASEID,t.BASETYPE,t.BASENAME,t.SORTID ,t.ISSHOW,t.BASEPARENTID,t.CDESC,t.NODENUM,t.REFNUM,t.UPDATETIME  from t_vo_category t where  t.UPDATETIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 16:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.ID',
   '视频货架表增量同步',
   '2',
   50000,
   12,
   'a_v_category_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '12');

insert into t_r_exportsql
  (exporttypeother,
   id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportline,
   filename,
   filepath,
   encoder,
   exportbyauto,
   groupid)
values
  ('0x1f',
   58,
   'select t.ID,t.CATEGORYID,t.PROGRAMID,t.PROGRAMNAME,t.SORTID,t.EXPORTTIME ,t.BASEID,t.BASETYPE,t.nodeid from t_vo_reference t where  t.EXPORTTIME > to_date((to_char(sysdate,'' yyyy - mm - dd'')||'' 16:30:00''),'' yyyy - mm - dd hh24 :mi :ss '') order by t.id',
   '视频商品表增量同步',
   '2',
   50000,
   9,
   'a_v_refrence_%YYYYMMDDHH24%_%NNN%',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'GB18030',
   '2',
   '12');


commit;

