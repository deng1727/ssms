
--awms����Ǩ�ƿ�ʼ

create table t_r_awms_category as select * from awms.t_r_category where 1=2;

create table t_r_awms_reference as select * from awms.t_r_reference where 1=2;

-- Create table
create table T_AWMS_CATEGORY_MAPPING
(
  AWMSCATEGORYID  VARCHAR2(30),
  AWMSPCATEGORYID VARCHAR2(30),
  CATEGORYID      VARCHAR2(30),
  NAME            VARCHAR2(100)
);
-- Add comments to the columns 
comment on column T_AWMS_CATEGORY_MAPPING.AWMSCATEGORYID
  is 'AWMS�Ļ���ID';
comment on column T_AWMS_CATEGORY_MAPPING.AWMSPCATEGORYID
  is 'AWMS�ĸ�����ID';
comment on column T_AWMS_CATEGORY_MAPPING.CATEGORYID
  is '��Ӧ��SSMS�Ļ���ID';
comment on column T_AWMS_CATEGORY_MAPPING.NAME
  is 'AWMS��SSMS�Ļ�������';
  
  
insert into T_AWMS_CATEGORY_MAPPING (AWMSCATEGORYID,AWMSPCATEGORYID,CATEGORYID,NAME) values('600037366','','100038105','�ն��Ż�������Ӫ�ֿ�');
  
--awms����Ǩ�ƽ���


-- Add/modify columns 
alter table T_CM_CATEGORY add RefNum number(11);
alter table T_CM_CATEGORY add RefCountNum number(11);
-- Add comments to the columns 
comment on column T_CM_CATEGORY.RefNum
  is '��ǰ�����µ���Ʒ��';
comment on column T_CM_CATEGORY.RefCountNum
  is '��ǰ������������ܵ�����Ʒ��';


create or replace function f_update_cm_cate_rnum  return number as
  v_status number;--��־����
    v_ncount      number; --��¼��
--�Զ����������ͣ�
type type_col_id is table of T_CM_CATEGORY.CATEGORYID%type
index by binary_integer;

--�Զ������ͱ���
v_id type_col_id;
begin
   v_status:=pg_log_manage.f_startlog('f_update_cm_cate_rnum','����������������������������Ʒ��' );
--������ID�����Զ������
dbms_stats.gather_table_stats(ownname => 'SSMS',tabname => 'T_CM_CATEGORY',cascade => true);

update T_CM_CATEGORY t set t.refnum=(select count(*) from t_Cm_Reference r where r.CATEGORYID=t.CATEGORYID);

select CATEGORYID bulk collect into v_id from T_CM_CATEGORY;

v_ncount := sql%rowcount;
--ѭ������
for i in 1..sql%rowcount loop

update T_CM_CATEGORY t set t.refcountnum=(
select sum(refnum)
  from (select CATEGORYID, c.refnum
          from T_CM_CATEGORY c
         start with c.CATEGORYID = v_id(i)
        connect by prior c.CATEGORYID = c.PCATEGORYID)
 )  where t.CATEGORYID= v_id(i);
end loop;
 v_status:= pg_log_manage.f_successlog(vn_RECORDNUM =>v_ncount);
 return (0);
exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
      return (1);
 end;

insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
values (3, 'dongke@aspirecn.com,wangminlong@aspirecn.com', '�����ļ���������', '0400', '1', '7', '0', null);



insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (24, 'select a.authorid, a.authorname from t_rb_author_new a', '����ϵͳ�ļ�-���Ķ���������', '2', 50000, '0x01', null, 2, 'breadauthor', '/opt/aspire/product/chroot_panguso/panguso/mo', 'GB18030', '2', '1', null, 3);






---------------------------------������ӪMOͬ��

create or replace synonym PPMS_V_WH_CM_CONTENT_CHANNEL
  for v_wh_cm_content_channel@DL_PPMS_DEVICE;

create table T_MO_OPENCONTENT_TRA as select * from T_MO_OPENCONTENT  where 1=2;  

-- Add/modify columns 
alter table T_R_EXPORTSQL_POR add TO_WHERE VARCHAR2(2000);
-- Add comments to the columns 
comment on column T_R_EXPORTSQL_POR.TO_WHERE
  is 'ͬ����ʱ��Ҫ���������';


-- Add/modify columns 
alter table T_R_EXPORTSQL_POR add TO_COLUMNS VARCHAR2(2000);
-- Add comments to the columns 
comment on column T_R_EXPORTSQL_POR.TO_COLUMNS
  is 'ͬ����ʱ��Ҫ���ֶ� Ĭ��Ϊ*';


create or replace procedure P_EXPORTSQL as
  v_nstatus number; --��¼��ذ�״̬
  v_nindnum number; --��¼�����Ƿ����
  v_nrecod  number;

  v_sql_trun  varchar2(1200);
  v_sql_exe   varchar2(1200);
  v_sql_count varchar2(1200);

  v_sql_from   VARCHAR2(50);
  v_sql_to     VARCHAR2(50);
  v_sql_to_tra VARCHAR2(50);
  v_sql_bak    VARCHAR2(50);
  v_sql_where  VARCHAR2(2000);
  v_sql_columns VARCHAR2(2000);

  cur_res t_r_exportsql_por%Rowtype;
  CURSOR cur IS
    SELECT * FROM t_r_exportsql_por;
begin

  v_nstatus := pg_log_manage.f_startlog('P_EXPORTSQL',
                                        '���ڵ���ͳһ���Ƶ����д�����ͼ');
  for cur_res in cur LOOP
    v_sql_from   := cur_res.from_name;
    v_sql_to     := cur_res.to_name;
    v_sql_to_tra := cur_res.to_name_tra;
    v_sql_bak    := cur_res.to_name_bak;
    v_sql_where  := cur_res.to_where;
    v_sql_columns := cur_res.to_columns;

    --��ս����ʷ������
    v_sql_trun := 'truncate table ' || v_sql_to_tra;
    DBMS_OUTPUT.put_line(v_sql_trun);
    execute immediate v_sql_trun;

    
    DBMS_OUTPUT.put_line(cur_res.to_columns);
    
    if v_sql_columns is null then
       v_sql_exe := 'insert into ' || v_sql_to_tra || ' select * from ' ||
                 v_sql_from;
    else
        v_sql_exe := 'insert into ' || v_sql_to_tra || ' select ' || v_sql_columns || ' from ' ||
                 v_sql_from;
    end if;

    DBMS_OUTPUT.put_line(v_sql_exe);
    DBMS_OUTPUT.put_line(cur_res.to_where);

    if v_sql_where is not null then
        v_sql_exe := v_sql_exe || ' where ' || v_sql_where;
    end if;

    DBMS_OUTPUT.put_line(v_sql_exe);
    
    execute immediate v_sql_exe;

    v_nrecod := SQL%ROWCOUNT;
    v_sql_count := 'select count(9) from '||v_sql_to_tra;
    execute immediate v_sql_count into v_nindnum;
    DBMS_OUTPUT.put_line(v_nindnum);

    if v_nindnum > 0 then
      --�����Ϊ�գ����л���
      execute immediate 'alter table ' || v_sql_to || ' rename to ' ||
                        v_sql_bak;
      execute immediate 'alter table ' || v_sql_to_tra || ' rename to ' ||
                        v_sql_to;
      execute immediate 'alter table ' || v_sql_bak || ' rename to ' ||
                        v_sql_to_tra;
      commit;
    else
      DBMS_OUTPUT.put_line('�������ṩ�Ļ������Ϊ��');
      raise_application_error(-20088, '�������ṩ�Ļ������Ϊ��');
    end if;

  END LOOP;

  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);

exception

  when others then
    rollback;
    --���ʧ�ܣ���ִ�����д����־
    DBMS_OUTPUT.put_line('������');
    v_nstatus := pg_log_manage.f_errorlog;
end;



insert into T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK, TO_WHERE, TO_COLUMNS)
values (4, 'ͬ��������ӪMO����', 'PPMS_V_WH_CM_CONTENT_CHANNEL', 'T_MO_OPENCONTENT', 'T_MO_OPENCONTENT_tra', 'T_MO_OPENCONTENT_bak', null, 'distinct contentid');





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.075_SSMS','MM2.0.0.0.079_SSMS');

commit;