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
   v_status number;--��־����

 v_g_nindnum  number;---������Ƿ�Ϊ��
  v_s_nindnum  number;---������Ƿ�Ϊ��
   v_t_nindnum  number;---������Ƿ�Ϊ��



begin
  P_CONTENT_LABEL;---����ͬ��Ʒ�Ʊ�ǩ����
v_d_game_error := '0';
   v_status:=pg_log_manage.f_startlog('P_CM__CT','��ʼ���������İ���clob�ֶ������Ϸ������չ��' );

   execute  immediate   'truncate table       CM_CT_APPGAME_TRA';
   insert  /*+ append */ into CM_CT_APPGAME_TRA   select g.*

                  from s_CM_CT_APPGAME g, v_OM_DICTIONARY d
                   where  g.type = d.id;
                   commit;
     select count(9) into v_g_nindnum from CM_CT_APPGAME_TRA ;
     if v_g_nindnum>0 then
        --�����Ϊ�գ����л���

   execute  immediate   'alter table CM_CT_APPGAME rename to CM_CT_APPGAME_BAKA';
  execute  immediate   'alter table CM_CT_APPGAME_TRA rename to CM_CT_APPGAME';
  execute  immediate   'alter table CM_CT_APPGAME_BAKA rename to CM_CT_APPGAME_TRA';

  else
     ---raise_application_error(-20088,'�������ṩ����Ϊ��');
     v_d_game_error := '�ӵ�����ͬ����չ���ݱ�CM_CT_APPGAMEΪ�ջ�ʧ��';
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
        --�����Ϊ�գ����л���

   execute  immediate   'alter table CM_CT_APPSoftware rename to CM_CT_APPSoftware_BAKA';
  execute  immediate   'alter table CM_CT_appSoftware_TRA rename to CM_CT_APPSoftware';
  execute  immediate   'alter table CM_CT_APPSoftware_BAKA rename to CM_CT_appSoftware_TRA';

  else
     ---raise_application_error(-20088,'�������ṩ����Ϊ��');

     v_d_Software_error := '�ӵ�����ͬ����չ���ݱ�CM_CT_SoftwareΪ�ջ�ʧ��';
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
        --�����Ϊ�գ����л���

   execute  immediate   'alter table CM_CT_APPTheme rename to CM_CT_APPTheme_BAKA';
  execute  immediate   'alter table CM_CT_APPTheme_TRA rename to CM_CT_APPTheme';
  execute  immediate   'alter table CM_CT_APPTheme_BAKA rename to CM_CT_APPTheme_TRA';

  else
     ---raise_application_error(-20088,'�������ṩ����Ϊ��');
     v_d_Theme_error := '�ӵ�����ͬ����չ���ݱ�CM_CT_ThemeΪ�ջ�ʧ��';
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
/




alter table cm_ct_appgame_tra add  SOFTWAREWORKDOCS      VARCHAR2(20);
alter table cm_ct_appgame_tra add   SOFTWAREWORKNO        VARCHAR2(60);
alter table cm_ct_appgame_tra add   VERSIONREPLYDOCS      VARCHAR2(20);
alter table cm_ct_appgame_tra add   VERSIONREPLYNO        VARCHAR2(60);
alter table cm_ct_appgame_tra add   FILINGNOTICENO        VARCHAR2(60);
alter table cm_ct_appgame_tra add   FILINGNOTICEURL       VARCHAR2(200);
alter table cm_ct_appgame_tra add   FILINGNOTICEDOCS      VARCHAR2(20);
  
comment on column cm_ct_appgame_tra.SOFTWAREWORKDOCS
  is '����ɨ���';
comment on column cm_ct_appgame_tra.SOFTWAREWORKNO
  is '�����ǼǺ�';
comment on column cm_ct_appgame_tra.VERSIONREPLYDOCS
  is '��Ȩ�����ļ�ɨ���';
comment on column cm_ct_appgame_tra.VERSIONREPLYNO
  is '��Ϸ�����(ISBN)';
comment on column cm_ct_appgame_tra.FILINGNOTICENO
  is '����֪ͨ�ļ���';
comment on column cm_ct_appgame_tra.FILINGNOTICEURL
  is '�Ļ�����Ϸ��������ǩ����';
comment on column cm_ct_appgame_tra.FILINGNOTICEDOCS
  is '��Ϸ������ͼ';
  
alter table cm_ct_appgame add  SOFTWAREWORKDOCS      VARCHAR2(20);
alter table cm_ct_appgame add   SOFTWAREWORKNO        VARCHAR2(60);
alter table cm_ct_appgame add   VERSIONREPLYDOCS      VARCHAR2(20);
alter table cm_ct_appgame add   VERSIONREPLYNO        VARCHAR2(60);
alter table cm_ct_appgame add   FILINGNOTICENO        VARCHAR2(60);
alter table cm_ct_appgame add   FILINGNOTICEURL       VARCHAR2(200);
alter table cm_ct_appgame add   FILINGNOTICEDOCS      VARCHAR2(20);
  
comment on column cm_ct_appgame.SOFTWAREWORKDOCS
  is '����ɨ���';
comment on column cm_ct_appgame.SOFTWAREWORKNO
  is '�����ǼǺ�';
comment on column cm_ct_appgame.VERSIONREPLYDOCS
  is '��Ȩ�����ļ�ɨ���';
comment on column cm_ct_appgame.VERSIONREPLYNO
  is '��Ϸ�����(ISBN)';
comment on column cm_ct_appgame.FILINGNOTICENO
  is '����֪ͨ�ļ���';
comment on column cm_ct_appgame.FILINGNOTICEURL
  is '�Ļ�����Ϸ��������ǩ����';
comment on column cm_ct_appgame.FILINGNOTICEDOCS
  is '��Ϸ������ͼ';
  
  
  
 



 alter table CM_CT_APPSoftware_TRA add   SOFTWAREWORKDOCS      VARCHAR2(20);
 alter table CM_CT_APPSoftware_TRA add   SOFTWAREWORKNO        VARCHAR2(60);
  
  comment on column CM_CT_APPSoftware_TRA.SOFTWAREWORKDOCS
  is '�����ǼǺ�';
  comment on column CM_CT_APPSoftware_TRA.SOFTWAREWORKNO
  is '����ɨ���';
  
  
  
  alter table CM_CT_APPSoftware add   SOFTWAREWORKDOCS      VARCHAR2(20);
 alter table CM_CT_APPSoftware add   SOFTWAREWORKNO        VARCHAR2(60);
  
  comment on column CM_CT_APPSoftware.SOFTWAREWORKDOCS
  is '�����ǼǺ�';
  comment on column CM_CT_APPSoftware.SOFTWAREWORKNO
  is '����ɨ���';
  
  
  
  

 alter table CM_CT_APPTheme_TRA add   SOFTWAREWORKDOCS      VARCHAR2(20);
 alter table CM_CT_APPTheme_TRA add   SOFTWAREWORKNO        VARCHAR2(60);
  
  comment on column CM_CT_APPTheme_TRA.SOFTWAREWORKDOCS
  is '�����ǼǺ�';
  comment on column CM_CT_APPTheme_TRA.SOFTWAREWORKNO
  is '����ɨ���';
  
   alter table CM_CT_APPTheme add   SOFTWAREWORKDOCS      VARCHAR2(20);
 alter table CM_CT_APPTheme add   SOFTWAREWORKNO        VARCHAR2(60);
  
  comment on column CM_CT_APPTheme.SOFTWAREWORKDOCS
  is '�����ǼǺ�';
  comment on column CM_CT_APPTheme.SOFTWAREWORKNO
  is '����ɨ���';


  
  
