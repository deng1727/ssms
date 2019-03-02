
-- Create the synonym 
create or replace synonym PPMS_V_T_SERIES_DEVICE
  for V_DATACENTER_T_SERIES_DEVICE@dl_ppms_device;


create table T_SERIES_DEVICE as select * from PPMS_V_T_SERIES_DEVICE where 1=2;
create table T_SERIES_DEVICE_TRA as select * from PPMS_V_T_SERIES_DEVICE where 1=2;

insert into T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK)
values (3, '同步虚拟机型信息', 'PPMS_V_T_SERIES_DEVICE', 'T_SERIES_DEVICE', 'T_SERIES_DEVICE_tra', 'T_SERIES_DEVICE_bak');



insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (21, 'select t.pid,t.device_id,t.device_name,t.contentid,t.contentname,t.resourceid,t.id,t.programsize,t.createdate,t.prosubmitdate,t.match,t.version,t.permission from T_A_CM_DEVICE_RESOURCE t order by t.contentid,t.pid', '安卓虚拟机型适配数据', '1', 50000, ',', null, 13, 'i_TACM_DR_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);


insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (22, 'select s_device_id,device_id from T_SERIES_DEVICE t order by t.device_id', '安卓虚拟机型与具体机型数据', '1', 50000, ',', null, 2, 'i_VDTD_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);


insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (23, 'select t.pid,t.device_id,t.contentid from v_dc_cm_device_blacklist t order by t.contentid,t.pid', '安卓适配黑名单数据', '1', 50000, ',', null, 3, 'i_VDBL_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);




-- Add/modify columns 
alter table T_R_EXPORTSQL add GROUPID NUMBER(5) default 1 not null;
-- Add comments to the columns 
comment on column T_R_EXPORTSQL.GROUPID
  is '分组任务号同组任务为同执行批次';


-- Create table
create table T_R_EXPORTSQL_GROUP
(
  GROUPID     NUMBER(5) not null,
  TOMAIL      VARCHAR2(200) not null,
  MAILTITLE   VARCHAR2(200) not null,
  STARTTIME   VARCHAR2(20) not null,
  TIMETYPE    VARCHAR2(5) default 1 not null,
  TIMETYPECON VARCHAR2(20) default 7,
  FTPID       VARCHAR2(5) default 0,
  URL         VARCHAR2(300)
);
-- Add comments to the columns 
comment on column T_R_EXPORTSQL_GROUP.GROUPID
  is '分组任务id';
comment on column T_R_EXPORTSQL_GROUP.TOMAIL
  is '当前分组任务发送邮件地址';
comment on column T_R_EXPORTSQL_GROUP.MAILTITLE
  is '当前分组任务发送邮件标题';
comment on column T_R_EXPORTSQL_GROUP.STARTTIME
  is '当前分组任务执行时间';
comment on column T_R_EXPORTSQL_GROUP.TIMETYPE
  is '执行时间类型 1:每天 2:每周';
comment on column T_R_EXPORTSQL_GROUP.TIMETYPECON
  is '当执行时间类型为2时，当前字段对应有意义，为周几执行';
comment on column T_R_EXPORTSQL_GROUP.FTPID
  is '是否上传0：否上传 其它为FTPID';
comment on column T_R_EXPORTSQL_GROUP.URL
  is '生成完后发起url通知地址';



-- Create table
create table T_R_EXPORTSQL_FTP
(
  FTPIP   VARCHAR2(25) not null,
  FTPPORT VARCHAR2(5) not null,
  FTPNAME VARCHAR2(25) not null,
  FTPKEY  VARCHAR2(25) not null,
  FTPPATH VARCHAR2(100) not null,
  FTPID   VARCHAR2(5) not null
);
-- Add comments to the columns 
comment on column T_R_EXPORTSQL_FTP.FTPIP
  is 'FTPIP';
comment on column T_R_EXPORTSQL_FTP.FTPPORT
  is 'FTPPort';
comment on column T_R_EXPORTSQL_FTP.FTPNAME
  is 'FTPName';
comment on column T_R_EXPORTSQL_FTP.FTPKEY
  is 'FTPKEY';
comment on column T_R_EXPORTSQL_FTP.FTPPATH
  is 'FTPPath';
comment on column T_R_EXPORTSQL_FTP.FTPID
  is '编号';


--2013创业大赛开始
update t_sync_tactic_cy c set c.categoryid='730274959' where c.appcatename='搞笑';
update t_sync_tactic_cy c set c.categoryid='730274961' where c.appcatename='视频';
update t_sync_tactic_cy c set c.categoryid='730274962' where c.appcatename='通讯';
update t_sync_tactic_cy c set c.categoryid='730274964' where c.appcatename='音乐';

update t_sync_tactic_cy c set c.categoryid='730274963' where c.appcatename='新闻';
update t_sync_tactic_cy c set c.categoryid='730274957' where c.appcatename='软件';
update t_sync_tactic_cy c set c.categoryid='730274958' where c.appcatename='游戏';
update t_sync_tactic_cy c set c.categoryid='730274960' where c.appcatename='生活';

--入围

insert into t_category_carveout_sqlbase(id,basesql,basename) values 
('9','select b.id from t_r_base b, t_r_gcontent g, v_service v,ppms_v_CM_CONTENT_PKAPPS c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid and  g.subtype = ''6'' and g.NAMELETTER=''2013''','2013创业大赛入围作品基础语句');
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('41','201306入围','730274943','0','1','1','c.pkdate=''201306''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('42','201307入围','730274944','0','1','1','c.pkdate=''201307''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('43','201308入围','730274945','0','1','1','c.pkdate=''201308''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('44','201309入围','730274946','0','1','1','c.pkdate=''201309''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('45','201310入围','730274947','0','1','1','c.pkdate=''201310''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('46','201311入围','730274948','0','1','1','c.pkdate=''201311''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('47','201312入围','730274949','0','1','1','c.pkdate=''201312''','',-1,'','',sysdate-2,0,9);


--转MM
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('48','201306转MM','730274950','0','1','1','c.pkdate=''201306''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('49','201307转MM','730274951','0','1','1','c.pkdate=''201307''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('50','201308转MM','730274952','0','1','1','c.pkdate=''201308''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('51','201309转MM','730274953','0','1','1','c.pkdate=''201309''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('52','201310转MM','730274954','0','1','1','c.pkdate=''201310''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('53','201311转MM','730274955','0','1','1','c.pkdate=''201311''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('54','201312转MM','730274956','0','1','1','c.pkdate=''201312''','',-1,'','',sysdate-2,0,5);


--重排序

insert into t_category_carveout_sqlbase(id,basesql,basename) values 
('8','select r.id from  t_r_base b,t_r_reference r,  t_r_gcontent g,v_service v,t_PAS_CY2013_CONT_DL_D d where b.id=g.id and r.categoryid =? and r.refnodeid = g.id and g.subtype = ''6'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.contentid = d.content_id(+)','2013创业大赛货架商品重排序基础语句');

insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('55','2013创业大赛分类搞笑重排序','730274959','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('56','2013创业大赛分类视频重排序','730274961','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('57','2013创业大赛分类通讯重排序','730274962','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('58','2013创业大赛分类音乐重排序','730274964','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);

insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('59','2013创业大赛分类新闻重排序','730274963','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('60','2013创业大赛分类软件重排序','730274957','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('61','2013创业大赛分类游戏重排序','730274958','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('62','2013创业大赛分类生活重排序','730274960','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);


create table t_PAS_CY2013_CONT_DL_D as select * from V_PAS_CY2013_CONT_DL_D@report105.oracle.com;
create table T_PAS_CY2013_CONT_DL_D_TRA as select * from t_PAS_CY2013_CONT_DL_D where 1=2;



create or replace procedure p_V_PAS_CY2013_CONT_DL_D as
  v_nindnum number;
  v_nstatus number;
  v_nrecod number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_V_PAS_CY2013_CONT_DL_D',
                                        '重新初始化T_PAS_CY2013_CONT_DL_D表');
  execute immediate 'truncate table T_PAS_CY2013_CONT_DL_D_TRA';
  --清空结果历史表数据

  insert into T_PAS_CY2013_CONT_DL_D_TRA
  select * from v_PAS_CY2013_CONT_DL_D@@report105.oracle.com v where v.stat_time = to_number(to_char(sysdate-1,'yyyymmdd'));
  commit;
  v_nrecod := SQL%ROWCOUNT;

  select count(9) into v_nindnum from T_PAS_CY2013_CONT_DL_D_TRA;

  if v_nindnum > 0 then
    execute immediate 'alter table T_PAS_CY2013_CONT_DL_D rename to T_PAS_CY2013_CONT_DL_D_BAK';
    execute immediate 'alter table T_PAS_CY2013_CONT_DL_D_TRA rename to T_PAS_CY2013_CONT_DL_D';
    execute immediate 'alter table T_PAS_CY2013_CONT_DL_D_BAK rename to T_PAS_CY2013_CONT_DL_D_TRA';
    --如果成功，将执行情况写入日志

    commit;
  else
    raise_application_error(-20088, '报表提供数据为空');
  end if;
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;
/

begin
  sys.dbms_job.submit(job => :job,
                      what => 'p_V_PAS_CY2013_CONT_DL_D;',
                      next_date => to_date('10-06-2013 09:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 09:00:00'',''yyyy/mm/dd

hh24:mi:ss'')');
  commit;
end;
/


--2013创业大赛结束

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.059_SSMS','MM2.0.0.0.065_SSMS');


commit;




----创建铃音盒表
create table T_M_TONEBOX
(
  ID          VARCHAR2(64) not null,
  NAME        VARCHAR2(50) not null,
  DESCRIPTION VARCHAR2(4000),
  CHARGE      NUMBER(12) not null,
  VALID       VARCHAR2(14) not null,
  UPDATETIME  DATE
);

comment on table T_M_TONEBOX
  is '铃音盒';
comment on column T_M_TONEBOX.ID
  is '铃音盒标识';
comment on column T_M_TONEBOX.NAME
  is '铃音盒名称';
comment on column T_M_TONEBOX.DESCRIPTION
  is '铃音盒介绍';
comment on column T_M_TONEBOX.CHARGE
  is '资费';
comment on column T_M_TONEBOX.VALID
  is '有效期';
comment on column T_M_TONEBOX.UPDATETIME
  is '最后更新日期';
  
  
----创建铃音盒歌曲表

create table T_M_TONEBOX_SONG
(
  BOXID      VARCHAR2(64) not null,
  ID         VARCHAR2(64) not null,
  SORTID     NUMBER(2),
  UPDATETIME DATE
);

alter table T_M_TONEBOX_SONG
  add constraint KEYS_T_M_TONEBOX_SONG_ID primary key (BOXID, ID);
  
comment on table T_M_TONEBOX_SONG
  is '铃音盒歌曲';
comment on column T_M_TONEBOX_SONG.BOXID
  is '铃音盒标识';
comment on column T_M_TONEBOX_SONG.ID
  is '歌曲标识';
comment on column T_M_TONEBOX_SONG.SORTID
  is '排列序号';
comment on column T_M_TONEBOX_SONG.UPDATETIME
  is '最后更新日期';



commit;