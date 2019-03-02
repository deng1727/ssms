
-- Create table
create table T_R_APPCATE_EN
(
  cateid    VARCHAR2(7) not null,
  catename  VARCHAR2(200) not null,
  cateename VARCHAR2(20),
  firstid   VARCHAR2(30) not null
);
-- Add comments to the columns 
comment on column T_R_APPCATE_EN.cateid
  is '��������ID';
comment on column T_R_APPCATE_EN.catename
  is '������������';
comment on column T_R_APPCATE_EN.cateename
  is '��������Ӣ������';
comment on column T_R_APPCATE_EN.firstid
  is 'һ������ID,1001,���;1002,����;1003,��Ϸ';



insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('14', '����', 'xiuxian', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('15', '����', 'qipai', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('16', '����', 'dongzuo', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('17', '����', 'tiyu', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('18', '���', 'sheji', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('19', 'ð��', 'maoxian', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('20', '����', 'celue', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('21', '����', 'saiche', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('22', '����', 'feixing', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('23', '��', 'gedou', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('24', '��ɫ', 'juese', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('25', '����', 'yizhi', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('26', '����', 'qita', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('1', '����', 'gongju', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('10', '��Ӱ', 'sheying', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('11', '����', 'shangwu', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('12', '����', 'jiankang', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('13', '����', 'dongman', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('2', '����', 'yule', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('3', '����', 'shenghuo', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('4', '�鼮', 'shuji', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('5', '����', 'xinwen', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('6', '��Ƶ', 'shipin', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('7', '�罻', 'shejiao', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('8', '����', 'jiaoyu', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('9', '����', 'caiwu', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('27', '����', 'jieri', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('28', '����', 'tiyu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('29', '����', 'dongwu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('30', 'ֲ��', 'zhiwu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('31', '�廭', 'chahua', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('32', '��Ц', 'gaoxiao', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('33', '����', 'jianzhu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('34', '����', 'qiche', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('35', '��ͨ', 'katong', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('36', '�羰', 'fengjing', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('37', '����', 'renwu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('38', '����', 'aiqing', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('39', '����', 'chuangyi', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('40', 'ɫ��', 'secai', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('41', '����', 'junshi', '1002');

-- �������Ѿ���������2012-01-12������ִ���ˣ���ΪMOPPS��39Ҫ��ͨ���ǵĳ����Ū�ġ�
-- alter table t_r_gcontent add mstatus number(1) default 0;
-- comment on column t_r_gcontent.mstatus
--   is '0:��MȯӦ�ã�1:MȯӦ��';

-- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_PROMOTION
  for V_CM_CONTENT_PROMOTION@DL_PPMS_DEVICE;

create or replace procedure proc_V_CM_CONTENT_PROMOTION  authid current_user is
  begin
  execute immediate 'update t_r_gcontent t set  mstatus = 0 where mstatus=1 ';
  execute immediate 'update t_r_gcontent t set mstatus = 1 where exists(select 1 from PPMS_V_CM_CONTENT_PROMOTION p where p.contentid = t.contentid) and  mstatus=0';
  commit;
  end;
/


variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'proc_V_CM_CONTENT_PROMOTION;',
                      next_date => to_date('05-01-2012 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/


-- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_CONLEVEL
  for V_CM_CONTENT_CONLEVEL@DL_PPMS_DEVICE;

create or replace procedure proc_V_CM_CONTENT_CONLEVEL  authid current_user is
  begin
  execute immediate 'update t_r_gcontent t set t.averagemark = (select p.conlevel from PPMS_V_CM_CONTENT_CONLEVEL p  where p.contentid = t.contentid) where exists (select 1 from PPMS_V_CM_CONTENT_CONLEVEL p  where p.contentid = t.contentid)';
  commit;
end;
/



variable job9 number;
begin
  sys.dbms_job.submit(job => :job9,
                      what => 'proc_V_CM_CONTENT_CONLEVEL;',
                      next_date => to_date('05-01-2012 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/

-----------�������ݱ��ػ�
create or replace synonym REPORT_DOWN_D
  for MM_REPORT.V_PPS_PORTAL_DOWN_D;

create table t_portal_down_d as select * from REPORT_DOWN_D t where t.stat_time = to_char(sysdate-1,'yyyymmdd');

create index INDEX_PORTAL_CONTENTID on t_portal_down_d (CONTENT_ID);

create or replace procedure p_portal_down_d as
  v_sql_f varchar2(1200);
begin
  --��ս����ʷ������
  execute immediate 'truncate table t_portal_down_d';
  v_sql_f := 'insert into t_portal_down_d
          select * from REPORT_DOWN_D t where t.stat_time = to_char(sysdate-1,''yyyymmdd'')';
  execute immediate v_sql_f;
  commit;
exception
  when others then
    rollback;
end;

variable job1 number;
begin
  sys.dbms_job.submit(job => :job1,
                     what => 'p_portal_down_d;',
                     next_date => to_date('05-01-2012 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                     interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.120_SSMS','MM1.0.3.125_SSMS');
commit;
