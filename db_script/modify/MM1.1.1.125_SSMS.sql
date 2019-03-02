-- Add/modify columns 
alter table T_R_OPENCRTE_MAP modify COMPANYNAME VARCHAR2(200);

-- Add/modify columns 
alter table T_CATERULE modify RULENAME VARCHAR2(60);


-- Add/modify columns 
alter table T_VO_VIDEO_TRA add updateTime date default sysdate;

-- Add/modify columns 
alter table T_VO_VIDEO add updateTime date default sysdate;










-----------------------------------------------------以下部分不需要执行
-- Create the synonym 
create or replace synonym REPORT_HOTAPP_ORDER_D
  for V_PPS_HOTAPP_ORDER_D@REPORT105.ORACLE.COM;
  
-- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_POPULAR
  for V_CM_CONTENT_POPULAR@DL_PPMS_DEVICE;

create table t_r_hotapp_order as
  select h.content_id
    from REPORT_HOTAPP_ORDER_D h
   where h.stat_time = to_number(to_char(sysdate - 1, 'YYYYMMDD'))
     and h.add_30days_order_count > 0
     UNION ALL
     select p.contentid
     from PPMS_V_CM_CONTENT_POPULAR p;
     
-- Create/Recreate indexes 
create index pk_hotapp_contentid on T_R_HOTAPP_ORDER (content_id);
     
     
delete from t_r_hotapp_order v
 where v.rowid > (select min(vt.rowid)
                    from t_r_hotapp_order vt
                   where vt.content_id = v.content_id);     

create table t_r_hotapp_order_tra as
  select h.content_id
    from REPORT_HOTAPP_ORDER_D h
   where h.stat_time = to_number(to_char(sysdate - 1, 'YYYYMMDD'))
     and h.add_30days_order_count > 0
     UNION ALL
     select p.contentid
     from PPMS_V_CM_CONTENT_POPULAR p;
     
-- Create/Recreate indexes 
create index pk_hotapp_contentid_tra on t_r_hotapp_order_tra (content_id);
     
     
delete from t_r_hotapp_order_tra v
 where v.rowid > (select min(vt.rowid)
                    from t_r_hotapp_order_tra vt
                   where vt.content_id = v.content_id);     


insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (651, '从产品库中获得最新(免费>收费) (HOT)', 'select b.id' || chr(10) || '  from t_r_base             b,' || chr(10) || '       t_r_gcontent         g,' || chr(10) || '       v_service            v,' || chr(10) || '       t_r_servenday_temp_a c,' || chr(10) || '       v_content_last    l, t_r_hotapp_order h' || chr(10) || ' where l.contentid = g.contentid' || chr(10) || ' and g.contentid = h.content_id' || chr(10) || '   and b.id = g.id' || chr(10) || '   and b.type like ''nt:gcontent:app%''' || chr(10) || '   and v.icpcode = g.icpcode' || chr(10) || '   and v.icpservid = g.icpservid' || chr(10) || '   and g.contentid = c.CONTENT_ID' || chr(10) || '   and g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10'')' || chr(10) || '   and l.osid = ''9''');



insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (624, '从产品库MM2.2上升最快_android(HOT)', 'select b.id' || chr(10) || '  from t_r_base         b,' || chr(10) || '       t_r_gcontent     g,' || chr(10) || '       v_service        v,' || chr(10) || '       v_serven_sort    c,' || chr(10) || '       v_content_last   s,' || chr(10) || '       t_r_hotapp_order h' || chr(10) || ' where s.CONTENTID = g.contentid' || chr(10) || '   and h.content_id = g.contentid' || chr(10) || '   and b.id = g.id' || chr(10) || '   and b.type like ''nt:gcontent:app%''' || chr(10) || '   and v.icpcode = g.icpcode' || chr(10) || '   and v.icpservid = g.icpservid' || chr(10) || '   and g.contentid = c.CONTENT_ID' || chr(10) || '   and g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10'')' || chr(10) || '   and s.contentid = g.contentid' || chr(10) || '   and s.osid = 9' || chr(10) || '   and c.os_id = 9');


insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (620, '从产品库中获得免费、付费榜单_android(HOT)', 'select b.id' || chr(10) || '  from t_r_base             b,' || chr(10) || '       t_r_gcontent         g,' || chr(10) || '       v_service            v,' || chr(10) || '       t_r_servenday_temp_a c,' || chr(10) || '       v_content_last    l, t_r_hotapp_order h' || chr(10) || ' where l.contentid = g.contentid' || chr(10) || ' and g.contentid = h.content_id' || chr(10) || '   and b.id = g.id' || chr(10) || '   and b.type like ''nt:gcontent:app%''' || chr(10) || '   and v.icpcode = g.icpcode' || chr(10) || '   and v.icpservid = g.icpservid' || chr(10) || '   and g.contentid = c.CONTENT_ID' || chr(10) || '   and g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10'')' || chr(10) || '   and l.osid = ''9''');


----------------------------存储过程
create or replace procedure P_REPORT_HOTAPP as
  v_nstatus number; --纪录监控包状态
  v_sql_f   varchar2(1200);
  v_nindnum number; --记录数据是否存在
  v_nrecod  number;
begin
  v_nstatus := pg_log_manage.f_startlog('P_REPORT_HOTAPP',
                                        '报表与电子流提供的热门应用数据同步');
  --清空结果历史表数据

  execute immediate 'truncate table t_r_hotapp_order_tra';

  v_sql_f := 'insert into t_r_hotapp_order_tra
          select h.content_id
    from REPORT_HOTAPP_ORDER_D h
   where h.stat_time = to_number(to_char(sysdate - 1, ''YYYYMMDD''))
     and h.add_30days_order_count > 0
  UNION ALL
  select p.contentid from PPMS_V_CM_CONTENT_POPULAR p';

  execute immediate v_sql_f;
  
  execute immediate 'delete from t_r_hotapp_order_tra v
 where v.rowid > (select min(vt.rowid)
                    from t_r_hotapp_order_tra vt
                   where vt.content_id = v.content_id)';

  v_nrecod := SQL%ROWCOUNT;
  
  select count(9) into v_nindnum from t_r_hotapp_order_tra;

  if v_nindnum > 0 then
    --如果不为空，将切换表
    execute immediate 'alter table table t_r_hotapp_order rename to table t_r_hotapp_order_bak';
    execute immediate 'alter table table t_r_hotapp_order_tra rename to table t_r_hotapp_order';
    execute immediate 'alter table table t_r_hotapp_order_bak rename to table t_r_hotapp_order_tra';
    commit;
  else
    raise_application_error(-20088, '报表与电子流提供的混合数据为空');
  end if;

  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;

----------------------------------------以上部分不需执行






----------------------------------------job定时任务 

variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'P_REPORT_HOTAPP;',
                      next_date => to_date('10-12-2012 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 23:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.119_SSMS','MM1.1.1.125_SSMS');


commit;