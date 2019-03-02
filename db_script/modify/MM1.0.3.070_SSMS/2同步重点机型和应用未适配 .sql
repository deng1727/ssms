create or replace procedure P_IMPORTANT_NOMATCH is
  v_status  number; --跟踪包返回状态
  v_maxDate date; --记录最大时间
begin

  v_status := pg_log_manage.f_startlog('P_IMPORTANT_NOMATCH',
                                       '同步重点机型和应用未适配');
  --获取最后一次同步时间
  select trunc(max(a.lasttime)) into v_maxDate from t_lastsynctime a;
  --最有一次同步时间如果和当前时间相对，查处重点机型和应用为匹配数据，否则异常。
  if trunc(sysdate) = v_maxDate then
  
    --将重点机型和应用未适配的数据插入表T_IMPORTANT_NOMATCH。
  delete from t_Important_NOMatch ;
    insert /*+ append */
    into t_Important_NOMatch nologging
      select *
        from v_Important_Match a
       where not exists (select 'X'
                from v_cm_device_resource b
               where a.content_id = b.contentid
                 and a.device_id = b.device_id);
    v_status := pg_log_manage.f_successlog;
  else
    --异常函数
    raise_application_error(-20005, '适配关系同步没有完成');
  
  end if;
exception
  when others then
    v_status := pg_log_manage.f_errorlog;
  
end;
