

insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (31, '从报表提供wap数据中获取', 'select b.id from t_r_base b, t_r_gcontent g, t_portal_down_d d, v_service v where b.id = g.id and d.content_id = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%'' and d.portal_id = 2 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (30, '从报表提供wap数据统计后信息中获取', 'select b.id from t_r_base b, t_r_gcontent g, t_r_down_sort_wap s, t_portal_down_d d, v_service v where b.id = g.id and d.content_id = g.contentid and s.content_id = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%'' and d.portal_id = 2 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');


----p_portal_down_d-----------------
------第一次初始化----------------------------
create table  t_portal_down_d_tra as select * from  t_portal_down_d where 1=2;
create index INDEX_PORTAL_TRA_CONTENTID  on   t_portal_down_d_tra (content_id);

------------------------------------
-----替换存储过程--------------------------
create or replace procedure p_portal_down_d as
 v_nstatus number;--纪录监控包状态
  v_sql_f varchar2(1200);
    v_nindnum    number;--记录数据是否存在
      v_nrecod      number;
begin
v_nstatus:=pg_log_manage.f_startlog('p_portal_down_d','报表分门户7天累计订购量数据同步');
  --清空结果历史表数据
  
  execute immediate 'truncate table t_portal_down_d_tra';
  v_sql_f := 'insert into t_portal_down_d_tra
          select * from REPORT_DOWN_D t where t.stat_time = to_char(sysdate-1,''yyyymmdd'')';
  execute immediate v_sql_f;
  v_nrecod:=SQL%ROWCOUNT;
   select count(9) into v_nindnum from T_PORTAL_DOWN_D_TRA ;
     if v_nindnum>0 then
        --如果不为空，将切换表
   execute  immediate   'alter table T_PORTAL_DOWN_D rename to T_PORTAL_DOWN_D_BAK';
  execute  immediate   'alter table T_PORTAL_DOWN_D_TRA rename to T_PORTAL_DOWN_D';
  execute  immediate   'alter table T_PORTAL_DOWN_D_BAK rename to T_PORTAL_DOWN_D_TRA';
  commit;
  else
     raise_application_error(-20088,'报表提供数据为空');
  end if;
  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
      --如果失败，将执行情况写入日志
      v_nstatus:=pg_log_manage.f_errorlog;
end;

----end-----------------
------------------------


----p_refresh_series_info-----------------
------第一次初始化----------------------------
create table  t_series_info_tra as select * from  t_series_info where 1=2;

------------------------------------
-----替换存储过程--------------------------
create or replace procedure p_refresh_series_info as
 v_nstatus number;--纪录监控包状态
  v_sql_f varchar2(1200);
    v_nindnum    number;--记录数据是否存在
      v_nrecod      number;
begin
v_nstatus:=pg_log_manage.f_startlog('p_refresh_series_info','为wap刷新序列数据数据');
  --清空结果历史表数据
  
  execute immediate 'truncate table t_series_info_tra';
  v_sql_f := 'insert  /*+ append */ into t_series_info_tra   select g.*
                  from  v_series_info g';
  execute immediate v_sql_f;
  commit;
  v_nrecod:=SQL%ROWCOUNT;
   select count(9) into v_nindnum from T_SERIES_INFO_TRA ;
     if v_nindnum>0 then
        --如果不为空，将切换表
   execute  immediate   'alter table T_SERIES_INFO rename to T_SERIES_INFO_BAK';
  execute  immediate   'alter table T_SERIES_INFO_TRA rename to T_SERIES_INFO';
  execute  immediate   'alter table T_SERIES_INFO_BAK rename to T_SERIES_INFO_TRA';
  commit;
  else
     raise_application_error(-20088,'电子流提供数据为空');
  end if;
  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
      --如果失败，将执行情况写入日志
      v_nstatus:=pg_log_manage.f_errorlog;
end;

----end-----------------
------------------------



----P_CM__CT-----------------
------第一次初始化----------------------------
create table  CM_CT_APPGAME_TRA as select * from  CM_CT_APPGAME where 1=2;
alter table CM_CT_APPGAME_TRA
  add constraint PK_CM_CT_APPGAME_TRA primary key (CONTENTID)
  using index ;
create table  CM_CT_APPSoftware_tra as select * from  CM_CT_APPSoftware where 1=2;
alter table CM_CT_APPSoftware_TRA
  add constraint PK_CM_CT_APPSoftware_TRA primary key (CONTENTID)
  using index ;
create table  CM_CT_APPTheme_tra as select * from  CM_CT_APPTheme where 1=2;
alter table CM_CT_APPTheme_TRA
  add constraint PK_CM_CT_APPTheme_TRA primary key (CONTENTID)
  using index ;

------------------------------------
-----替换存储过程--------------------------

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
    if length( v_d_game_error)=0 then
    v_status:= pg_log_manage.f_successlog;  
    end if;


exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
end;



----end-----------------
------------------------


-----------------------------准备工作-----------

--------report_servenday_wap
create or replace view report_servenday_wap as
select  content_id,
     portal_id os_id,
     dense_rank() over(partition by r.portal_id order by r.add_7days_order_count desc) sort_num,
     add_7days_order_count add_7days_down_count,add_order_count from t_portal_down_d r;
     
     
---------T_R_DOWN_SORT_NEW_WAP
create table T_R_DOWN_SORT_NEW_WAP
(
  CONTENT_ID      VARCHAR2(12),
  OS_ID           NUMBER(8),
  SORT_NUM        NUMBER,
  DOWN_COUNT      NUMBER,
  ADD_ORDER_COUNT NUMBER(12)
);

-- Add comments to the table 
comment on table T_R_DOWN_SORT_NEW_WAP
  is '上次应用排序表WAP';
-- Add comments to the columns 
comment on column T_R_DOWN_SORT_NEW_WAP.CONTENT_ID
  is '应用ID';
comment on column T_R_DOWN_SORT_NEW_WAP.OS_ID
  is '平台ID，3表示OPhone OS ，9表是Android';
comment on column T_R_DOWN_SORT_NEW_WAP.SORT_NUM
  is '排序';
comment on column T_R_DOWN_SORT_NEW_WAP.DOWN_COUNT
  is '前7天下载次数';
-- Create/Recreate indexes 
create index IND_DOWNSORTO_CONTENTID_WAP on T_R_DOWN_SORT_NEW_WAP (CONTENT_ID);

-----------------T_R_DOWN_SORT_OLD_WAP
create table T_R_DOWN_SORT_OLD_WAP
(
  CONTENT_ID      VARCHAR2(12),
  OS_ID           NUMBER(8),
  SORT_NUM        NUMBER,
  DOWN_COUNT      NUMBER,
  ADD_ORDER_COUNT NUMBER(12)
);

-- Add comments to the table 
comment on table T_R_DOWN_SORT_OLD_WAP
  is '当前应用排序表WAP';
-- Add comments to the columns 
comment on column T_R_DOWN_SORT_OLD_WAP.CONTENT_ID
  is '应用ID';
comment on column T_R_DOWN_SORT_OLD_WAP.OS_ID
  is '平台ID，3表示OPhone OS ，9表是Android';
comment on column T_R_DOWN_SORT_OLD_WAP.SORT_NUM
  is '排序';
comment on column T_R_DOWN_SORT_OLD_WAP.DOWN_COUNT
  is '前7天下载次数';
-- Create/Recreate indexes 
create index IND_DOWNSORT_CONTENTID_WAP on T_R_DOWN_SORT_OLD_WAP (CONTENT_ID);

----------------T_R_DOWN_SORT_WAP
create table T_R_DOWN_SORT_WAP
(
  CONTENT_ID       VARCHAR2(12),
  OS_ID            NUMBER(8),
  SORT_NUM_NEW     NUMBER,
  SORT_NUM_OLD     NUMBER,
  MINUS_SORT_NULL  NUMBER,
  MINUS_DOWN_COUNT NUMBER,
  ADD_ORDER_COUNT  NUMBER(12),
  STANDARD_SCORE   NUMBER
);

-- Add comments to the table 
comment on table T_R_DOWN_SORT_WAP
  is '上次和当前排序相减的结果表WAP';
-- Add comments to the columns 
comment on column T_R_DOWN_SORT_WAP.CONTENT_ID
  is '应用ID';
comment on column T_R_DOWN_SORT_WAP.OS_ID
  is '平台ID，3表示OPhone OS ，9表是Android';
comment on column T_R_DOWN_SORT_WAP.SORT_NUM_NEW
  is '当前的排序';
comment on column T_R_DOWN_SORT_WAP.SORT_NUM_OLD
  is '前一次排序';
comment on column T_R_DOWN_SORT_WAP.MINUS_SORT_NULL
  is '前一次排序减当前排序';
comment on column T_R_DOWN_SORT_WAP.MINUS_DOWN_COUNT
  is '前一次下载减当前下载';
comment on column T_R_DOWN_SORT_WAP.STANDARD_SCORE
  is '上升最快指标得分';

-------------------------------------------------
create or replace procedure P_BINDING_SORT_FINAL_WAP as
  type type_table_osid is table of number index by binary_integer; --自定义类型
  type type_table_sort is table of number index by binary_integer; --自定义类型
  v_type_nosid   type_table_osid; --OS_ID集合
  v_type_nsort   type_table_sort; --SORT_NUM_OLD集合
  v_nosidi       number; --iphone类型
  v_nosida       number; --Android类型
  v_nosids       number; --S60类型

  v_nsortmaxi    number; --iphone排序最大值
  v_nsortmaxs    number; --S60排序最大值
  v_nsortmaxa    number; --Android排序最大值
  v_vsqlinsert   varchar2(1500); --动态SQL
  v_vsqlalter    varchar2(1500); --动态SQL
  v_vsqltruncate varchar2(1500); --动态SQL
  --v_vconstant  varchar(6):='0006'; --常量
  v_nindnum    number;--记录索引是否存在
  v_nstatus number;--纪录监控包状态
  v_ncount number;--记录report_servenday表的记录数

begin
v_nstatus:=pg_log_manage.f_startlog('P_BINDING_SORT_FINAL_WAP','货架榜单WAP');

--报表没有提供数据流程结束，抛出例外
select count(9) into v_ncount from report_servenday_wap a
     where a.os_id in (2)
      and  rownum<2;
if v_ncount=0 then
  raise_application_error(-20088,'报表提供记录数为0');
  end if;

  --删除索引
    select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID_WAP';
     if v_nindnum>0 then
   execute  immediate ' drop index IND_DOWNSORTO_CONTENTID_WAP';
  end if;

    select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID_WAP';
     if v_nindnum>0 then
 execute  immediate ' drop index IND_DOWNSORT_CONTENTID_WAP';
  end if;

    --删除前次数据
  v_vsqltruncate := 'truncate table t_r_down_sort_new_wap ';
   execute immediate v_vsqltruncate;
   v_vsqltruncate := 'truncate table t_r_down_sort_wap ';
  execute immediate v_vsqltruncate;
  --禁止表写在线日志
  execute immediate ' alter table t_r_down_sort_new_wap  nologging';
  execute immediate ' alter table t_r_down_sort_old_wap  nologging';
  execute immediate ' alter table t_r_down_sort_wap  nologging';

  --插入报表的原始数据到本地
  insert /*+ append parallel(t_r_down_sort_new ,4) */ into t_r_down_sort_new_wap
    (content_id, os_id, SORT_NUM,down_count,add_order_count)
    select
    /*+  parallel(report_servenday,4) */
     content_id,
     os_id,
     dense_rank() over(partition by a.os_id order by a.add_7days_down_count desc) sort_num,
     add_7days_down_count,add_order_count
      from report_servenday_wap a
     where a.os_id in(2);
commit;
  --根据OS_ID分组获取t_r_down_sort.sort_num_old的最大值，用于更新报表前一次未提供conten_id的排序值
  select os_id, max(sort_num) bulk collect
    into v_type_nosid, v_type_nsort
    from t_r_down_sort_new_wap
   group by os_id;

  for i in 1 .. sql%rowcount loop
    if v_type_nosid(i) = 2 then
      v_nosida    := 2;
      v_nsortmaxa := v_type_nsort(i);
    end if;
/*
    if v_type_nosid(i) = 3 then
      v_nosidi    := 3;
      v_nsortmaxi := v_type_nsort(i);
    end if;
      if v_type_nosid(i) = 1 then
      v_nosids    := 1;
      v_nsortmaxs := v_type_nsort(i);
    end if;*/

  end loop;

  --更新、插入报表本次未提供conten_id值的数据
    --插入一条iphone数据
    select count(9) into v_nindnum  from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID_WAP';
    if v_nindnum=0 then
  execute  immediate   ' create index IND_DOWNSORT_CONTENTID_WAP  on   t_r_down_sort_new_wap (content_id) ';
  end if;
  v_vsqlinsert:= 'insert  into t_r_down_sort_new_wap (content_id,os_id,sort_num,down_count)
select /*+ parallel(v,4) */ v.contentid,2,' || v_nsortmaxa  ||
               '+ 1,0  from v_cm_content v
where v.status=''0006'' and  not exists (select 1 from t_r_down_sort_new_wap  n where n.content_id=v.contentid and n.os_id=2)';

execute immediate v_vsqlinsert;

  /*--插入一条s60数据
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select \*+ parallel(v_cm_content,4) *\ v.contentid,1,' || v_nsortmaxs  ||
               '+ 1,0   from V_CONTENT_LAST v
where v.osid=1 and  not exists (select  1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=1)';

execute immediate v_vsqlinsert;

    --插入一条android数据
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select \*+ parallel(v_cm_content,4) *\ v.contentid,9,' || v_nsortmaxa  ||
               '+ 1,0   from V_CONTENT_LAST v
where v.osid=9 and  not exists (select  1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=9)';

execute immediate v_vsqlinsert;*/
  commit;

  /*  --报表本次提供数据，前次没有数据，补充前次数据
  v_vsqlstr:='insert into t_r_down_sort_old
    (content_id, os_id, sort_num)
    select n.content_id,n.os_id,decode(n.os_id,9,'||v_nsortmaxa||',3,'||v_nsortmaxi||')
      from t_r_down_sort_new  n
     where not exists (select 1
              from t_r_down_sort_old  o
             where n.content_id = o.content_id
               and n.os_id = n.os_id)';
     execute immediate   v_vsqlstr;*/
     select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID_WAP';
     if v_nindnum=0 then
  execute  immediate   ' create index IND_DOWNSORTO_CONTENTID_wap  on   t_r_down_sort_old_wap (content_id) parallel';
  end if;
   insert /*+ append parallel(t_r_down_sort,4) */ into t_r_down_sort_wap
    (content_id, os_id, sort_num_new, sort_num_old, minus_sort_null,minus_down_count,add_order_count,Standard_score)
select content_id,
       os_id,
       sort_num_new,
       sort_num_old,
       minus_sort_null,
       minus_down_count,
       add_order_count,

       decode((max(B) over(partition by os_id) - min(B) over( partition by os_id)),0,0, trunc((a -
       abs(a) * (1 - (B - min(B) over(partition by os_id)) / (max(B) over( partition by os_id) - min(B) over( partition by os_id))))*0.6+b*0.4,3)) Standard_score
  from (select n.content_id,
       n.os_id,
       n.sort_num sort_num_new,
       o.sort_num sort_num_old,
       o.sort_num - n.sort_num minus_sort_null,
       n.down_count - o.down_count minus_down_count,

       decode(stddev(o.sort_num - n.sort_num) over(partition by o.os_id),
              0,
              0,
              ((o.sort_num - n.sort_num) - avg(o.sort_num - n.sort_num)
               over(partition by o.os_id)) / stddev(o.sort_num - n.sort_num) over(partition by o.os_id)) A,
       decode(stddev(n.down_count-o.down_count) over(partition by n.os_id),
              0,
              0,
              ((n.down_count - o.down_count) -
              avg(n.down_count - o.down_count) over(partition by o.os_id)) /
              stddev(n.down_count-o.down_count) over(partition by n.os_id)) B,

       n.add_order_count
  from t_r_down_sort_old_wap  o, t_r_down_sort_new_wap  n
 where o.content_id = n.content_id
   and o.os_id = n.os_id)g;
   v_nstatus:=pg_log_manage.f_successlog;
  commit;
/* execute  immediate ' drop index IND_DOWNSORTO_CONTENTID ';
 execute  immediate ' drop index IND_DOWNSORT_CONTENTID ';*/

  --将本次数据变为前次数据
  v_vsqlalter := 'alter table t_r_down_sort_old_wap  rename to t_r_down_sort_old_wap_1 ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_new_wap  rename to t_r_down_sort_old_wap  ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_old_wap_1 rename to t_r_down_sort_new_wap  ';
  execute immediate v_vsqlalter;

  exception
  when others then
      v_nstatus:=pg_log_manage.f_errorlog;

end;
------------------------------------------------------------------------
------------------------------------------------------------------------
------------------------------------------------------------------------

  


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.130_SSMS','MM1.0.3.135_SSMS');
commit;
