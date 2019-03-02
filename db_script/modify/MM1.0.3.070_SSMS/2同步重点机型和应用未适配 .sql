create or replace procedure P_IMPORTANT_NOMATCH is
  v_status  number; --���ٰ�����״̬
  v_maxDate date; --��¼���ʱ��
begin

  v_status := pg_log_manage.f_startlog('P_IMPORTANT_NOMATCH',
                                       'ͬ���ص���ͺ�Ӧ��δ����');
  --��ȡ���һ��ͬ��ʱ��
  select trunc(max(a.lasttime)) into v_maxDate from t_lastsynctime a;
  --����һ��ͬ��ʱ������͵�ǰʱ����ԣ��鴦�ص���ͺ�Ӧ��Ϊƥ�����ݣ������쳣��
  if trunc(sysdate) = v_maxDate then
  
    --���ص���ͺ�Ӧ��δ��������ݲ����T_IMPORTANT_NOMATCH��
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
    --�쳣����
    raise_application_error(-20005, '�����ϵͬ��û�����');
  
  end if;
exception
  when others then
    v_status := pg_log_manage.f_errorlog;
  
end;
