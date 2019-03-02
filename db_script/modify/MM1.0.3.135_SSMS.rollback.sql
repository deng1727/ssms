delete T_CATERULE_COND_BASE where base_id in (30,31);

--delete T_CATERULE where ruleid in (1027,1028,1029,1030,1031);

--delete T_CATERULE_COND where ruleid in (1027,1028,1029,1030,1031);

------------------------------------
-----替换存储过程--------------------------
create or replace procedure p_portal_down_d as
 v_nstatus number;--纪录监控包状态
  v_sql_f varchar2(1200);
    v_nindnum    number;--记录索引是否存在
begin
v_nstatus:=pg_log_manage.f_startlog('p_portal_down_d','报表分门户7天累计订购量数据同步');
  --清空结果历史表数据
   select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'INDEX_PORTAL_CONTENTID';
     if v_nindnum>0 then
   execute  immediate ' drop index INDEX_PORTAL_CONTENTID';
  end if;
  execute immediate 'truncate table t_portal_down_d';
  v_sql_f := 'insert into t_portal_down_d
          select * from REPORT_DOWN_D t where t.stat_time = to_char(sysdate-1,''yyyymmdd'')';
  execute immediate v_sql_f;
   select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'INDEX_PORTAL_CONTENTID';
     if v_nindnum=0 then
  execute  immediate   ' create index INDEX_PORTAL_CONTENTID  on   t_portal_down_d (content_id) parallel';
  end if;
   v_nstatus:= pg_log_manage.f_successlog;
  commit;
 
exception
  --when others then
   -- rollback;
     when others then
      v_nstatus:=pg_log_manage.f_errorlog;
end;

----end-----------------
------------------------

------------------------------------
-----替换存储过程--------------------------
create or replace procedure p_refresh_series_info as
  v_dsql        varchar2(1200); ---
 v_csql        varchar2(1200); ---
   v_status number;--日志返回
begin
   v_status:=pg_log_manage.f_startlog('p_refresh_series_info','刷新序列数据数据' );

   v_dsql := 'truncate table       t_series_info';
   v_csql := 'insert  /*+ append */ into t_series_info  nologging select g.*
                  from  v_series_info g ';
    execute immediate v_dsql;
    execute immediate v_csql;
    v_status:= pg_log_manage.f_successlog;

  commit;
exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
end ;

----end-----------------
------------------------


------------------------------------
-----替换存储过程--------------------------
create or replace procedure p_cm__ct as

  v_dsql_GAME        varchar2(1200); ---
  v_csql_GAME        varchar2(1200); ---
  ---  v_csql_GAME1        varchar2(1200); ---
  
   v_dsql_Software        varchar2(1200); ---
  v_csql_Software        varchar2(1200); ---
  ---v_csql_Software1        varchar2(1200); ---
  
    v_dsql_Theme        varchar2(1200); ---
   v_csql_Theme        varchar2(1200); ---
  -- v_csql_Theme1        varchar2(1200); ---
   v_status number;--日志返回

begin
   v_status:=pg_log_manage.f_startlog('P_CM__CT','初始化电子流的包含clob字段软件游戏主题扩展表' );

   v_dsql_GAME := 'truncate table       CM_CT_APPGAME';
    v_csql_GAME := 'insert  /*+ append */ into CM_CT_APPGAME  nologging select g.*
                  from s_CM_CT_APPGAME g, v_OM_DICTIONARY d
                   where  g.type = d.id';
 /*v_csql_GAME := 'insert  \*+ append *\ into CM_CT_APPGAME  nologging select g.*
                from s_CM_CT_APPGAME g, ppms_v_cm_content p, v_OM_DICTIONARY d
                 where g.contentid = p.contentid
                   and g.type = d.id';*/

   /*v_csql_GAME1 := 'insert  \*+ append *\ into CM_CT_APPGAME  nologging select g.*
                    from s_CM_CT_APPGAME g, ppms_v_cm_content_cy p, v_OM_DICTIONARY d
                     where g.contentid = p.contentid
                       and g.type = d.id';*/
                     
                     

   v_dsql_Software := 'truncate table   CM_CT_APPSoftware';
    v_csql_Software := 'insert  /*+ append */ into CM_CT_APPSoftware nologging select g.*
                        from s_CM_CT_APPSoftware g, v_OM_DICTIONARY d
                        where  g.type = d.id';
   --v_csql_Software := 'insert  /*+ append */ into CM_CT_APPSoftware nologging select g.*
    --                    from s_CM_CT_APPSoftware g, ppms_v_cm_content p, v_OM_DICTIONARY d
    --                    where g.contentid = p.contentid
    --                           and g.type = d.id';

 --v_csql_Software1 := 'insert  /*+ append */ into CM_CT_APPSoftware nologging select g.*
 --                       from s_CM_CT_APPSoftware g, ppms_v_cm_content_cy p, v_OM_DICTIONARY d
  --                      where g.contentid = p.contentid
  --                             and g.type = d.id';
                               
                               
   v_dsql_Theme := 'truncate table   CM_CT_APPTheme';
   v_csql_Theme := 'insert /*+ append */  into CM_CT_APPTheme nologging select g.*
   from s_CM_CT_APPTheme g,  v_OM_DICTIONARY d
  where  g.type = d.id';
  -- v_csql_Theme := 'insert /*+ append */  into CM_CT_APPTheme nologging select g.*
  -- from s_CM_CT_APPTheme g, ppms_v_cm_content p, v_OM_DICTIONARY d
 -- where g.contentid = p.contentid
 --   and g.type = d.id';
    -- v_csql_Theme1 := 'insert /*+ append */  into CM_CT_APPTheme nologging select g.*
  -- from s_CM_CT_APPTheme g, ppms_v_cm_content_cy p, v_OM_DICTIONARY d
 -- where g.contentid = p.contentid
  --  and g.type = d.id';


    execute immediate v_dsql_GAME;
    execute immediate v_csql_GAME;
    --- execute immediate v_csql_GAME1;

    execute immediate v_dsql_Software;
    execute immediate v_csql_Software;
    ---execute immediate v_csql_Software1;

    execute immediate v_dsql_Theme;
    execute immediate v_csql_Theme;
    --- execute immediate v_csql_Theme1;
    v_status:= pg_log_manage.f_successlog;  

  commit;
exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
end;

----end-----------------
------------------------

drop table t_r_down_sort_new_wap;

drop table t_r_down_sort_old_wap;

drop table t_r_down_sort_wap;

drop view report_servenday_wap;


delete DBVERSION where PATCHVERSION = 'MM1.0.3.135_SSMS' and LASTDBVERSION = 'MM1.0.3.130_SSMS';
commit;