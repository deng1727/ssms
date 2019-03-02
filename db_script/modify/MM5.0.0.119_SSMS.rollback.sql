alert table v_cm_device_resource drop column THIRDCOLLECT;
alert table v_cm_device_resource_mid drop column THIRDCOLLECT;
alert table t_a_cm_device_resource drop column THIRDCOLLECT;


create or replace function f_ppms_cm_device_pid(v_type in number, ----1 增量;0，全量
                                                v_desc in varchar2)
  return number as
  ---create or replace procedure p_ppms_cm_device_pid as
  v_sql_f   varchar2(1200);
  v_nindnum number; --记录数据是否存在
  v_nstatus number;
  v_nrecod  number;

  v_seqi      number;
  v_nindnum0  number;
  v_nindnum1  number;
  v_type_self number;

begin


  v_type_self := 0;

  if v_type_self = 1 then
    -----增量同步
    select count(9)
      into v_nindnum0
      from user_tables a
     where a.TABLE_NAME = 'T_PPMS_CM_DEVICE_PID_TRA';
           --监控包开始运行
  v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                        '开始插入PID重新同步变更程序包并增量更新适配关系表');

    if v_nindnum0 = 1 then
      execute immediate 'truncate table T_PPMS_CM_DEVICE_PID_TRA';
    else
      execute immediate 'create table t_ppms_cm_device_pid_TRA as select pid from v_ppms_cm_device_pid where 1=2 ';

   end if;
    --清空结果历史表数据

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
      --如果成功，将执行情况写入日志


      -------增量更新适配关系表-----

      --监控包开始运行
      v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                            '执行语句 delete from v_cm_device_resource');

      delete from v_cm_device_resource r
       where exists
       (select 1 from t_ppms_cm_device_pid p where r.pid = p.pid);
      v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
      commit;

      --监控包开始运行
      v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                            '执行语句   insert into v_cm_device_resource');

      insert into v_cm_device_resource
        select pid,device_id,device_name,contentid,contentname,resourceid,id,servicecode,absolutepath,url,cdnurl,wapurl,mourl,programsize,jarfilepath,jadfilepath,contentuuid,createdate,prosubmitdate,match,version,permission,iscdn,versionname,caflag,cadev,caorg,cavalidatedate,pcurl,wwwurl
          from PPMS_CM_DEVICE_RESOURCE r
         where exists
         (select 1 from v_ppms_cm_device_pid p where r.pid = p.pid);
      commit;
      v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    else
      raise_application_error(-20088, '电子流提供数据为空');
    end if;
    --else if v_type_self = 0 then
    ---啥也不做 test
    -- raise_application_error(-20088, '电子流提供数据为空');
  else
    -----全量同步

    select SEQ_devid_ID.Nextval into v_seqi from dual;
    select count(9)
      into v_nindnum1
      from user_tables a
     where a.TABLE_NAME = 'V_CM_DEVICE_RESOURCE_MID';
    if v_nindnum1 = 1 then
      execute immediate 'drop table  v_cm_device_resource_mid';
    end if;

    --监控包开始运行
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          '执行语句create table  v_cm_device_resource_mid');

    execute immediate 'create table  v_cm_device_resource_mid nologging as select *　from PPMS_CM_DEVICE_RESOURCE';
    --监控包成功执行
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;

    --监控包开始运行
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          '执行语句create index IDX_V_CM_DEVICE_RESOU');
    execute immediate 'create index IDX_V_CM_DEVICE_RESOU' || v_seqi ||
                      ' on V_CM_DEVICE_RESOURCE_MID (CONTENTID, DEVICE_NAME) parallel (degree 8) ';
        --监控包成功执行
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;

    --监控包开始运行
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          '执行语句create index IDX_V_CM_DEVICE_REid');
    execute immediate 'create index IDX_V_CM_DEVICE_REid' || v_seqi ||
                      ' on V_CM_DEVICE_RESOURCE_MID (CONTENTID, DEVICE_ID) parallel (degree 8) ';
       --监控包成功执行
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;

    --监控包开始运行
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          '执行语句create index idx_pid_devcie');
   -- execute immediate 'create index idx_pid_devcie' || v_seqi ||
   --                   ' on V_CM_DEVICE_RESOURCE_MID (pid)   ';
      --监控包成功执行
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;
    --监控包开始运行
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          '执行表分析V_CM_DEVICE_RESOURCE_MID');
  --  begin
   --   dbms_stats.GATHER_TABLE_STATS('SSMS',
    --                                'V_CM_DEVICE_RESOURCE_MID',
    --                                cascade                   => true,
     --                               degree                    => 4);
  --  end;
     --监控包成功执行
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;
    --监控包开始运行
    v_nstatus := pg_log_manage.f_startlog('f_ppms_cm_device_pid',
                                          '最后换表名');
    execute immediate 'alter table   v_cm_device_resource rename to     v_cm_device_resource_bak';
    execute immediate 'alter table   v_cm_device_resource_mid rename to     v_cm_device_resource';
    execute immediate 'alter table   v_cm_device_resource_bak rename to     v_cm_device_resource_mid';
     --监控包成功执行
    v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    commit;
  end if;
  --v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
  return(0);
exception

  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
    return(1);
end;


