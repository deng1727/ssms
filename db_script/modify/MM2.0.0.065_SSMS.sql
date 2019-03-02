
-- Create the synonym 
create or replace synonym PPMS_V_T_SERIES_DEVICE
  for V_DATACENTER_T_SERIES_DEVICE@dl_ppms_device;


create table T_SERIES_DEVICE as select * from PPMS_V_T_SERIES_DEVICE where 1=2;
create table T_SERIES_DEVICE_TRA as select * from PPMS_V_T_SERIES_DEVICE where 1=2;

insert into T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK)
values (3, 'ͬ�����������Ϣ', 'PPMS_V_T_SERIES_DEVICE', 'T_SERIES_DEVICE', 'T_SERIES_DEVICE_tra', 'T_SERIES_DEVICE_bak');



insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (21, 'select t.pid,t.device_id,t.device_name,t.contentid,t.contentname,t.resourceid,t.id,t.programsize,t.createdate,t.prosubmitdate,t.match,t.version,t.permission from T_A_CM_DEVICE_RESOURCE t order by t.contentid,t.pid', '��׿���������������', '1', 50000, ',', null, 13, 'i_TACM_DR_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);


insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (22, 'select s_device_id,device_id from T_SERIES_DEVICE t order by t.device_id', '��׿�������������������', '1', 50000, ',', null, 2, 'i_VDTD_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);


insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (23, 'select t.pid,t.device_id,t.contentid from v_dc_cm_device_blacklist t order by t.contentid,t.pid', '��׿�������������', '1', 50000, ',', null, 3, 'i_VDBL_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);




-- Add/modify columns 
alter table T_R_EXPORTSQL add GROUPID NUMBER(5) default 1 not null;
-- Add comments to the columns 
comment on column T_R_EXPORTSQL.GROUPID
  is '���������ͬ������Ϊִͬ������';


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
  is '��������id';
comment on column T_R_EXPORTSQL_GROUP.TOMAIL
  is '��ǰ�����������ʼ���ַ';
comment on column T_R_EXPORTSQL_GROUP.MAILTITLE
  is '��ǰ�����������ʼ�����';
comment on column T_R_EXPORTSQL_GROUP.STARTTIME
  is '��ǰ��������ִ��ʱ��';
comment on column T_R_EXPORTSQL_GROUP.TIMETYPE
  is 'ִ��ʱ������ 1:ÿ�� 2:ÿ��';
comment on column T_R_EXPORTSQL_GROUP.TIMETYPECON
  is '��ִ��ʱ������Ϊ2ʱ����ǰ�ֶζ�Ӧ�����壬Ϊ�ܼ�ִ��';
comment on column T_R_EXPORTSQL_GROUP.FTPID
  is '�Ƿ��ϴ�0�����ϴ� ����ΪFTPID';
comment on column T_R_EXPORTSQL_GROUP.URL
  is '���������url֪ͨ��ַ';



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
  is '���';


--2013��ҵ������ʼ
update t_sync_tactic_cy c set c.categoryid='730274959' where c.appcatename='��Ц';
update t_sync_tactic_cy c set c.categoryid='730274961' where c.appcatename='��Ƶ';
update t_sync_tactic_cy c set c.categoryid='730274962' where c.appcatename='ͨѶ';
update t_sync_tactic_cy c set c.categoryid='730274964' where c.appcatename='����';

update t_sync_tactic_cy c set c.categoryid='730274963' where c.appcatename='����';
update t_sync_tactic_cy c set c.categoryid='730274957' where c.appcatename='���';
update t_sync_tactic_cy c set c.categoryid='730274958' where c.appcatename='��Ϸ';
update t_sync_tactic_cy c set c.categoryid='730274960' where c.appcatename='����';

--��Χ

insert into t_category_carveout_sqlbase(id,basesql,basename) values 
('9','select b.id from t_r_base b, t_r_gcontent g, v_service v,ppms_v_CM_CONTENT_PKAPPS c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid and  g.subtype = ''6'' and g.NAMELETTER=''2013''','2013��ҵ������Χ��Ʒ�������');
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('41','201306��Χ','730274943','0','1','1','c.pkdate=''201306''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('42','201307��Χ','730274944','0','1','1','c.pkdate=''201307''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('43','201308��Χ','730274945','0','1','1','c.pkdate=''201308''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('44','201309��Χ','730274946','0','1','1','c.pkdate=''201309''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('45','201310��Χ','730274947','0','1','1','c.pkdate=''201310''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('46','201311��Χ','730274948','0','1','1','c.pkdate=''201311''','',-1,'','',sysdate-2,0,9);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('47','201312��Χ','730274949','0','1','1','c.pkdate=''201312''','',-1,'','',sysdate-2,0,9);


--תMM
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('48','201306תMM','730274950','0','1','1','c.pkdate=''201306''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('49','201307תMM','730274951','0','1','1','c.pkdate=''201307''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('50','201308תMM','730274952','0','1','1','c.pkdate=''201308''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('51','201309תMM','730274953','0','1','1','c.pkdate=''201309''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('52','201310תMM','730274954','0','1','1','c.pkdate=''201310''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('53','201311תMM','730274955','0','1','1','c.pkdate=''201311''','',-1,'','',sysdate-2,0,5);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('54','201312תMM','730274956','0','1','1','c.pkdate=''201312''','',-1,'','',sysdate-2,0,5);


--������

insert into t_category_carveout_sqlbase(id,basesql,basename) values 
('8','select r.id from  t_r_base b,t_r_reference r,  t_r_gcontent g,v_service v,t_PAS_CY2013_CONT_DL_D d where b.id=g.id and r.categoryid =? and r.refnodeid = g.id and g.subtype = ''6'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.contentid = d.content_id(+)','2013��ҵ����������Ʒ������������');

insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('55','2013��ҵ���������Ц������','730274959','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('56','2013��ҵ����������Ƶ������','730274961','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('57','2013��ҵ��������ͨѶ������','730274962','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('58','2013��ҵ������������������','730274964','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);

insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('59','2013��ҵ������������������','730274963','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('60','2013��ҵ�����������������','730274957','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('61','2013��ҵ����������Ϸ������','730274958','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);
insert into t_category_carveout_rule (id,name,cid,intervaltype,excutetime,excuteinterval,wsql,osql,count,sortid,lastexcutetime,effectivetime,ruletype,sqlbaseid) values('62','2013��ҵ������������������','730274960','0','1','1','','d.ha_dl_cnt desc  nulls  last',-1,'','',sysdate-2,1,8);


create table t_PAS_CY2013_CONT_DL_D as select * from V_PAS_CY2013_CONT_DL_D@report105.oracle.com;
create table T_PAS_CY2013_CONT_DL_D_TRA as select * from t_PAS_CY2013_CONT_DL_D where 1=2;



create or replace procedure p_V_PAS_CY2013_CONT_DL_D as
  v_nindnum number;
  v_nstatus number;
  v_nrecod number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_V_PAS_CY2013_CONT_DL_D',
                                        '���³�ʼ��T_PAS_CY2013_CONT_DL_D��');
  execute immediate 'truncate table T_PAS_CY2013_CONT_DL_D_TRA';
  --��ս����ʷ������

  insert into T_PAS_CY2013_CONT_DL_D_TRA
  select * from v_PAS_CY2013_CONT_DL_D@@report105.oracle.com v where v.stat_time = to_number(to_char(sysdate-1,'yyyymmdd'));
  commit;
  v_nrecod := SQL%ROWCOUNT;

  select count(9) into v_nindnum from T_PAS_CY2013_CONT_DL_D_TRA;

  if v_nindnum > 0 then
    execute immediate 'alter table T_PAS_CY2013_CONT_DL_D rename to T_PAS_CY2013_CONT_DL_D_BAK';
    execute immediate 'alter table T_PAS_CY2013_CONT_DL_D_TRA rename to T_PAS_CY2013_CONT_DL_D';
    execute immediate 'alter table T_PAS_CY2013_CONT_DL_D_BAK rename to T_PAS_CY2013_CONT_DL_D_TRA';
    --����ɹ�����ִ�����д����־

    commit;
  else
    raise_application_error(-20088, '�����ṩ����Ϊ��');
  end if;
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
exception
  when others then
    rollback;
    --���ʧ�ܣ���ִ�����д����־
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


--2013��ҵ��������

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.059_SSMS','MM2.0.0.0.065_SSMS');


commit;




----���������б�
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
  is '������';
comment on column T_M_TONEBOX.ID
  is '�����б�ʶ';
comment on column T_M_TONEBOX.NAME
  is '����������';
comment on column T_M_TONEBOX.DESCRIPTION
  is '�����н���';
comment on column T_M_TONEBOX.CHARGE
  is '�ʷ�';
comment on column T_M_TONEBOX.VALID
  is '��Ч��';
comment on column T_M_TONEBOX.UPDATETIME
  is '����������';
  
  
----���������и�����

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
  is '�����и���';
comment on column T_M_TONEBOX_SONG.BOXID
  is '�����б�ʶ';
comment on column T_M_TONEBOX_SONG.ID
  is '������ʶ';
comment on column T_M_TONEBOX_SONG.SORTID
  is '�������';
comment on column T_M_TONEBOX_SONG.UPDATETIME
  is '����������';



commit;