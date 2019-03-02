alert table v_cm_device_resource drop column THIRDCOLLECT;
alert table v_cm_device_resource_mid drop column THIRDCOLLECT;
alert table t_a_cm_device_resource drop column THIRDCOLLECT;


create or replace function f_ppms_cm_device_pid(v_type in number, ----1 ����;0��ȫ��
                                                v_desc in varchar2)
  return number as
  ---create or replace procedure p_ppms_cm_device_pid as
  v_sql_f   varchar2(1200);
  v_nindnum number; --��¼�����Ƿ����
  v_nstatus number;
  v_nrecod  number;

  v_seqi      number;
  v_nindnum0  number;
  v_nindnum1  number;
  v_type_self number;

begin


  v_type_self := 0;

  if v_type_self = 1 then
    -----����ͬ��
    select count(9)
      into v_nindnum0
      from user_tables a
     where a.TABLE_NAME = 'T_PPMS_CM_DEVICE_PID_TRA';
           --��ذ���ʼ����
  v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                        '��ʼ����PID����ͬ�������������������������ϵ��');

    if v_nindnum0 = 1 then
      execute immediate 'truncate table T_PPMS_CM_DEVICE_PID_TRA';
    else
      execute immediate 'create table t_ppms_cm_device_pid_TRA as select pid from v_ppms_cm_device_pid where 1=2 ';

   end if;
    --��ս����ʷ������

    insert into t_ppms_cm_device_pid_TRA
      (pid)
      select t.pid from v_ppms_cm_device_pid t;
    v_nrecod := SQL%ROWCOUNT;
    --execute immediate v_sql_f;
    ---  select count(9) into v_nindnum  from t_ppms_cm_device_pid_TRA;
     commit;
      v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    if v_nrecod > 0 then
      execute immediate 'alter table t_ppms_cm_device_pid rename to t_ppms_cm_device_pid_BAK';
      execute immediate 'alter table t_ppms_cm_device_pid_TRA rename to t_ppms_cm_device_pid';
      execute immediate 'alter table t_ppms_cm_device_pid_BAK rename to t_ppms_cm_device_pid_TRA';
      --����ɹ�����ִ�����д����־


      -------�������������ϵ��-----

      --��ذ���ʼ����
      v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                            'ִ����� delete from v_cm_device_resource');

      delete from v_cm_device_resource r
       where exists
       (select 1 from t_ppms_cm_device_pid p where r.pid = p.pid);
      v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
      commit;

      --��ذ���ʼ����
      v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                            'ִ�����   insert into v_cm_device_resource');

      insert into v_cm_device_resource
        select pid,device_id,device_name,contentid,contentname,resourceid,id,servicecode,absolutepath,url,cdnurl,wapurl,mourl,programsize,jarfilepath,jadfilepath,contentuuid,createdate,prosubmitdate,match,version,permission,iscdn,versionname,caflag,cadev,caorg,cavalidatedate,pcurl,wwwurl
          from PPMS_CM_DEVICE_RESOURCE r
         where exists
         (select 1 from v_ppms_cm_device_pid p where r.pid = p.pid);
      commit;
      v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    else
      raise_application_error(-20088, '�������ṩ����Ϊ��');
    end if;
    --else if v_type_self = 0 then
    ---ɶҲ���� test
    -- raise_application_error(-20088, '�������ṩ����Ϊ��');
  else
    -----ȫ��ͬ��

    select SEQ_devid_ID.Nextval into v_seqi from dual;
    select count(9)
      into v_nindnum1
      from user_tables a
     where a.TABLE_NAME = 'V_CM_DEVICE_RESOURCE_MID';
    if v_nindnum1 = 1 then
      execute immediate 'drop table  v_cm_device_resource_mid';
    end if;

    --��ذ���ʼ����
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          'ִ�����create table  v_cm_device_resource_mid');

    execute immediate 'create table  v_cm_device_resource_mid nologging as select *��from PPMS_CM_DEVICE_RESOURCE';
    --��ذ��ɹ�ִ��
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;

    --��ذ���ʼ����
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          'ִ�����create index IDX_V_CM_DEVICE_RESOU');
    execute immediate 'create index IDX_V_CM_DEVICE_RESOU' || v_seqi ||
                      ' on V_CM_DEVICE_RESOURCE_MID (CONTENTID, DEVICE_NAME) parallel (degree 8) ';
        --��ذ��ɹ�ִ��
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;

    --��ذ���ʼ����
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          'ִ�����create index IDX_V_CM_DEVICE_REid');
    execute immediate 'create index IDX_V_CM_DEVICE_REid' || v_seqi ||
                      ' on V_CM_DEVICE_RESOURCE_MID (CONTENTID, DEVICE_ID) parallel (degree 8) ';
       --��ذ��ɹ�ִ��
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;

    --��ذ���ʼ����
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          'ִ�����create index idx_pid_devcie');
   -- execute immediate 'create index idx_pid_devcie' || v_seqi ||
   --                   ' on V_CM_DEVICE_RESOURCE_MID (pid)   ';
      --��ذ��ɹ�ִ��
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;
    --��ذ���ʼ����
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          'ִ�б����V_CM_DEVICE_RESOURCE_MID');
  --  begin
   --   dbms_stats.GATHER_TABLE_STATS('SSMS',
    --                                'V_CM_DEVICE_RESOURCE_MID',
    --                                cascade                   => true,
     --                               degree                    => 4);
  --  end;
     --��ذ��ɹ�ִ��
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;
    --��ذ���ʼ����
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          '��󻻱���');
    execute immediate 'alter table   v_cm_device_resource rename to     v_cm_device_resource_bak';
    execute immediate 'alter table   v_cm_device_resource_mid rename to     v_cm_device_resource';
    execute immediate 'alter table   v_cm_device_resource_bak rename to     v_cm_device_resource_mid';
     --��ذ��ɹ�ִ��
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;
  end if;
  --v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
  return(0);
exception

  when others then
    rollback;
    --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
    return(1);
end;


