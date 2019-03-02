
alter table t_a_ppms_receive_change
add stats varchar2(2) default '-1' not null ;



insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
values (17, 'zhanghuan@aspirecn.com,ouyangguangming@aspirecn.com', '精准推荐平台-MM首页应用数据同步', '1910', '0', '7', '0', '');



insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (102, 'select distinct cid from t_d_reference r where r.ctype=''APP'' and exists(select 1 from (select categoryid,state from t_d_category m start with m.categoryid=''x8566'' connect by m.parentid=prior m.categoryid  and m.state=1)b where b.state=1 and b.categoryid=r.categoryid)',
	'精准推荐平台-MM首页应用数据同步', '2', 50000, '0x09', to_date('01-11-2017 17:04:00', 'dd-mm-yyyy hh24:mi:ss'), 1, 
	'i_homepage_app_%YYYYMMDD%_%NNNNNN%', 
	'/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 
	'UTF-8', '2', '1', '90', 17);

	
	commit
	
	
	
	
	
	
	
	
	














	
