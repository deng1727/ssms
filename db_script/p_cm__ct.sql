create or replace procedure p_cm__ct as

  v_dsql_GAME        varchar2(1200); ---
  v_csql_GAME        varchar2(1200); ---
  v_d_game_error   varchar2(300); ---
  ---  v_csql_GAME1        varchar2(1200); ---

   v_dsql_Software        varchar2(1200); ---
  v_csql_Software        varchar2(1200); ---
   v_d_Software_error   varchar2(300); ---
  ---v_csql_Software1        varchar2(1200); ---

    v_dsql_Theme        varchar2(1200); ---
   v_csql_Theme        varchar2(1200); ---
    v_d_Theme_error   varchar2(300); ---
  -- v_csql_Theme1        varchar2(1200); ---
   v_status number;--日志返回

 v_g_nindnum  number;---计算表是否为空
  v_s_nindnum  number;---计算表是否为空
   v_t_nindnum  number;---计算表是否为空



begin
  P_CONTENT_LABEL;---重新同步品牌标签数据
v_d_game_error := '0';
   v_status:=pg_log_manage.f_startlog('P_CM__CT','初始化电子流的包含clob字段软件游戏主题扩展表' );

   execute  immediate   'truncate table       CM_CT_APPGAME_TRA';
   insert  /*+ append */ into CM_CT_APPGAME_TRA   select g.*

                  from s_CM_CT_APPGAME g, v_OM_DICTIONARY d
                   where  g.type = d.id;
                   commit;
     select count(9) into v_g_nindnum from CM_CT_APPGAME_TRA ;
     if v_g_nindnum>0 then
        --如果不为空，将切换表

   execute  immediate   'alter table CM_CT_APPGAME rename to CM_CT_APPGAME_BAKA';
  execute  immediate   'alter table CM_CT_APPGAME_TRA rename to CM_CT_APPGAME';
  execute  immediate   'alter table CM_CT_APPGAME_BAKA rename to CM_CT_APPGAME_TRA';

  else
     ---raise_application_error(-20088,'电子流提供数据为空');
     v_d_game_error := '从电子流同步扩展数据表CM_CT_APPGAME为空或失败';
       v_status:=pg_log_manage.f_errorlog(vc_remark =>v_d_game_error );
  end if;
 /*v_csql_GAME := 'insert  \*+ append *\ into CM_CT_APPGAME  nologging select g.*
                from s_CM_CT_APPGAME g, ppms_v_cm_content p, v_OM_DICTIONARY d
                 where g.contentid = p.contentid
                   and g.type = d.id';*/

   /*v_csql_GAME1 := 'insert  \*+ append *\ into CM_CT_APPGAME  nologging select g.*
                    from s_CM_CT_APPGAME g, ppms_v_cm_content_cy p, v_OM_DICTIONARY d
                     where g.contentid = p.contentid
                       and g.type = d.id';*/



  execute  immediate   'truncate table   CM_CT_APPSoftware_TRA';
insert  /*+ append */ into CM_CT_APPSoftware_TRA  select g.*
                        from s_CM_CT_APPSoftware g, v_OM_DICTIONARY d
                        where  g.type = d.id;
        commit;
       select count(9) into v_s_nindnum from CM_CT_appSoftware_TRA ;
     if v_s_nindnum>0 then
        --如果不为空，将切换表

   execute  immediate   'alter table CM_CT_APPSoftware rename to CM_CT_APPSoftware_BAKA';
  execute  immediate   'alter table CM_CT_appSoftware_TRA rename to CM_CT_APPSoftware';
  execute  immediate   'alter table CM_CT_APPSoftware_BAKA rename to CM_CT_appSoftware_TRA';

  else
     ---raise_application_error(-20088,'电子流提供数据为空');

     v_d_Software_error := '从电子流同步扩展数据表CM_CT_Software为空或失败';
      v_status:=pg_log_manage.f_errorlog(vc_remark =>v_d_Software_error );
  end if;
   --v_csql_Software := 'insert  /*+ append */ into CM_CT_APPSoftware nologging select g.*
    --                    from s_CM_CT_APPSoftware g, ppms_v_cm_content p, v_OM_DICTIONARY d
    --                    where g.contentid = p.contentid
    --                           and g.type = d.id';

 --v_csql_Software1 := 'insert  /*+ append */ into CM_CT_APPSoftware nologging select g.*
 --                       from s_CM_CT_APPSoftware g, ppms_v_cm_content_cy p, v_OM_DICTIONARY d
  --                      where g.contentid = p.contentid
  --                             and g.type = d.id';


 execute  immediate   'truncate table   CM_CT_APPTheme_TRA';
insert /*+ append */  into CM_CT_APPTheme_TRA  select g.*
   from s_CM_CT_APPTheme g,  v_OM_DICTIONARY d
  where  g.type = d.id;
  commit;

   select count(9) into v_t_nindnum from CM_CT_APPTheme_TRA ;
     if v_t_nindnum>0 then
        --如果不为空，将切换表

   execute  immediate   'alter table CM_CT_APPTheme rename to CM_CT_APPTheme_BAKA';
  execute  immediate   'alter table CM_CT_APPTheme_TRA rename to CM_CT_APPTheme';
  execute  immediate   'alter table CM_CT_APPTheme_BAKA rename to CM_CT_APPTheme_TRA';

  else
     ---raise_application_error(-20088,'电子流提供数据为空');
     v_d_Theme_error := '从电子流同步扩展数据表CM_CT_Theme为空或失败';
     v_status:=pg_log_manage.f_errorlog(vc_remark =>v_d_Theme_error );
  end if;
  -- v_csql_Theme := 'insert /*+ append */  into CM_CT_APPTheme nologging select g.*
  -- from s_CM_CT_APPTheme g, ppms_v_cm_content p, v_OM_DICTIONARY d
 -- where g.contentid = p.contentid
 --   and g.type = d.id';
    -- v_csql_Theme1 := 'insert /*+ append */  into CM_CT_APPTheme nologging select g.*
  -- from s_CM_CT_APPTheme g, ppms_v_cm_content_cy p, v_OM_DICTIONARY d
 -- where g.contentid = p.contentid
  --  and g.type = d.id';


  /*  execute immediate v_dsql_GAME;
    execute immediate v_csql_GAME;
    commit;*/

    --- execute immediate v_csql_GAME1;

  /*  execute immediate v_dsql_Software;
    execute immediate v_csql_Software;
    commit;*/

    ---execute immediate v_csql_Software1;

   /* execute immediate v_dsql_Theme;
    execute immediate v_csql_Theme;
         commit;*/

    --- execute immediate v_csql_Theme1;
    v_d_game_error := v_d_game_error || v_d_Software_error || v_d_Theme_error;
    if length( v_d_game_error)=1 then
    v_status:= pg_log_manage.f_successlog;
    end if;


exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
end;



----end-----------------
------------------------
