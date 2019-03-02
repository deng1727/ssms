
-- Drop columns 
alter table t_caterule drop column maxGoodsNum;
--最新榜单语句开始 




drop table V_CONTENT_LAST;

delete from t_caterule_cond_base where base_id = 19;

delete from t_caterule where ruleid = 299;

delete from  t_caterule_cond where ruleid = 299;

delete from t_category_rule where cid='218637127' and ruleid = 299;


delete from t_caterule where ruleid = 300;

delete from  t_caterule_cond where ruleid = 300;

delete from t_category_rule where cid='218637128' and ruleid = 300;



--最新榜单语句结束


--免费、付费榜、总榜开始
delete from t_caterule_cond_base where base_id = 20;

delete from t_caterule where ruleid = 301;

delete from  t_caterule_cond where ruleid = 301;

delete from t_category_rule where cid='217896797' and ruleid = 301;


delete from t_caterule where ruleid = 302;

delete from  t_caterule_cond where ruleid = 302;

delete from t_category_rule where cid='217896798' and ruleid = 302;



delete from t_caterule where ruleid = 303;

delete from  t_caterule_cond where ruleid = 303;

delete from t_category_rule where cid='248230768' and ruleid = 303;




delete from t_caterule_cond_base where base_id = 21;

delete from t_caterule where ruleid = 304;

delete from  t_caterule_cond where ruleid = 304;

delete from t_category_rule where cid='217896800' and ruleid = 304;


delete from t_caterule where ruleid = 305;

delete from  t_caterule_cond where ruleid = 305;

delete from t_category_rule where cid='217896801' and ruleid = 305;


delete from t_caterule where ruleid = 306;

delete from  t_caterule_cond where ruleid = 306;

delete from t_category_rule where cid='248230769' and ruleid = 306;


--免费、付费榜、总榜结束



drop synonym PPMS_V_CM_CONTENT_program;


create or replace view t_r_servenday_temp_a as
select "OS_ID","CONTENT_ID",t.down_count as ADD_7DAYS_DOWN_COUNT from 
t_r_down_sort_old t where t.os_id=9;

create or replace view t_r_servenday_temp_o as
select "OS_ID","CONTENT_ID",t.down_count as ADD_7DAYS_DOWN_COUNT from 
t_r_down_sort_old t where t.os_id=3;



-- Drop columns 
alter table T_R_DOWN_SORT drop column add_order_count;
alter table T_R_DOWN_SORT drop column standard_score;
alter table T_R_DOWN_SORT_NEW drop column add_order_count;
alter table T_R_DOWN_SORT_OLD drop column add_order_count;


--------------------------------------------------------------
-----------存储过程要一并执行------------------------------
--------------------------------------------------------------
create or replace procedure p_Binding_sort as
  type type_table_osid is table of number index by binary_integer; --自定义类型
  type type_table_sort is table of number index by binary_integer; --自定义类型
  v_type_nosid   type_table_osid; --OS_ID集合
  v_type_nsort   type_table_sort; --SORT_NUM_OLD集合
  v_nosidi       number; --iphone类型
  v_nosida       number; --Android类型
  v_nsortmaxi    number; --iphone排序最大值
  v_nsortmaxa    number; --Android排序最大值
  v_vsqlinsert   varchar2(1500); --动态SQL
  v_vsqlalter    varchar2(1500); --动态SQL
  v_vsqltruncate varchar2(1500); --动态SQL
  v_vconstant  varchar(6):='0006'; --常量

begin
    --删除前次数据
  v_vsqltruncate := 'truncate table t_r_down_sort_new';
   execute immediate v_vsqltruncate;
   v_vsqltruncate := 'truncate table t_r_down_sort';
  execute immediate v_vsqltruncate;
  --禁止表写在线日志
  execute immediate ' alter table t_r_down_sort_new nologging';
  execute immediate ' alter table t_r_down_sort_old nologging';
  execute immediate ' alter table t_r_down_sort nologging';

  --插入报表的原始数据到本地
  insert /*+ append parallel(t_r_down_sort_new,4) */ into t_r_down_sort_new
    (content_id, os_id, SORT_NUM,down_count)
    select
    /*+  parallel(report_servenday,4) */
     content_id,
     os_id,
     dense_rank() over(partition by a.os_id order by a.add_7days_down_count desc) sort_num,
     add_7days_down_count
      from report_servenday a
     where a.os_id in (9, 3)
       and stat_time = to_char(sysdate-1,'yyyymmdd') ;
commit;
  --根据OS_ID分组获取t_r_down_sort.sort_num_old的最大值，用于更新报表前一次未提供conten_id的排序值
  select os_id, max(sort_num) bulk collect
    into v_type_nosid, v_type_nsort
    from t_r_down_sort_new
   group by os_id;

  for i in 1 .. sql%rowcount loop
    if v_type_nosid(i) = 9 then
      v_nosida    := 9;
      v_nsortmaxa := v_type_nsort(i);
    end if;

    if v_type_nosid(i) = 3 then
      v_nosidi    := 3;
      v_nsortmaxi := v_type_nsort(i);
    end if;
  end loop;

  --更新、插入报表本次未提供conten_id值的数据
    --插入一条iphone数据
  execute  immediate   ' create index ind_downsort_contentid on   t_r_down_sort_new(content_id) ';
  v_vsqlinsert:= 'insert  into t_r_down_sort_new(content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,3,' || v_nsortmaxi  ||
               '+ 1,0  from v_cm_content v
where v.status='||v_vconstant||'  and  not exists (select 1 from t_r_down_sort_new n where n.content_id=v.contentid and n.os_id=3)';
execute immediate v_vsqlinsert;

    --插入一条android数据
  v_vsqlinsert:= 'insert  into t_r_down_sort_new(content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,9,' || v_nsortmaxa  ||
               '+ 1,0   from v_cm_content v
where v.status='||v_vconstant||'   and  not exists (select  1 from t_r_down_sort_new n where n.content_id=v.contentid and n.os_id=9)';

execute immediate v_vsqlinsert;
  commit;

  /*  --报表本次提供数据，前次没有数据，补充前次数据
  v_vsqlstr:='insert into t_r_down_sort_old
    (content_id, os_id, sort_num)
    select n.content_id,n.os_id,decode(n.os_id,9,'||v_nsortmaxa||',3,'||v_nsortmaxi||')
      from t_r_down_sort_new n
     where not exists (select 1
              from t_r_down_sort_old o
             where n.content_id = o.content_id
               and n.os_id = n.os_id)';
     execute immediate   v_vsqlstr;*/
  execute  immediate   ' create index ind_downsorto_contentid on   t_r_down_sort_old(content_id) parallel';
  insert /*+ append parallel(t_r_down_sort,4) */ into t_r_down_sort
    (content_id, os_id, sort_num_new, sort_num_old, minus_sort_null,minus_down_count)
    select n.content_id,
           n.os_id,
           n.sort_num,
           o.sort_num,
           o.sort_num-n.sort_num,
           n.down_count - o.down_count

      from t_r_down_sort_old o, t_r_down_sort_new n
     where o.content_id = n.content_id
       and o.os_id = n.os_id;
  commit;
 execute  immediate ' drop index ind_downsort_contentid';
 execute  immediate ' drop index ind_downsorto_contentid';


  --将本次数据变为前次数据
  v_vsqlalter := 'alter table t_r_down_sort_old rename to t_r_down_sort_old1 ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_new rename to t_r_down_sort_old ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_old1 rename to t_r_down_sort_new ';
  execute immediate v_vsqlalter;

end;

---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------



delete DBVERSION where PATCHVERSION = 'MM1.0.3.120_SSMS' and LASTDBVERSION = 'MM1.0.3.115_SSMS';
commit;