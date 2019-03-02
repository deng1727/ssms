---新增动漫呈现数据同步接口----------
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
  ('0x01',
   62,
   'select a.id,c.chapterid,c.contentid,a.flow_time from t_cb_adapter a,t_cb_chapter c where a.chapterid=c.chapterid',
   '动漫呈现数据同步',
   '2',
   50000,
   4,
   'comic_adapter',
   '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www',
   'UTF-8',
   '2',
   '13');
   
   insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
      values (13, 'dongke@aspirecn.com', '动漫呈现数据同步', '0300', '1', '7', '0', null);