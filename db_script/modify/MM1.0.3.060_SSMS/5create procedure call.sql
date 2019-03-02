create or replace procedure p_call_sortby is
--子定义变量
  type type_table_categoryid is table of varchar2(20) index by binary_integer;
  v_tcategoryid type_table_categoryid;
  v_nstatus     number;
begin
--监控存储过程情况的包
  v_nstatus := pg_log_manage.f_startlog('T_CONFIG_PARENTCATEGORYID',
                                        '读取配置表信息，调用p_shelf_sortby');
  select categoryid bulk collect
    into v_tcategoryid
    from t_config_parentcategoryid;
    --获取配置表的根货架，循环调用p_shelf_sortby
  for i in 1 .. sql%rowcount loop
    p_shelf_sortby(v_tcategoryid(i));
  end loop;
  --如果成功，将执行情况写入日志
  v_nstatus := pg_log_manage.f_successlog;
  commit;
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;

